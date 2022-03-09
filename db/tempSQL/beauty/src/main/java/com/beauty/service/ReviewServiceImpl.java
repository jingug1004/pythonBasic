package com.beauty.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.beauty.BeautyConstants;
import com.beauty.entity.Product;
import com.beauty.entity.Review;
import com.beauty.entity.ReviewLike;
import com.beauty.entity.ReviewPhoto;
import com.beauty.entity.ReviewWait;
import com.beauty.entity.User;
import com.beauty.repository.ProductRepository;
import com.beauty.repository.ReviewLikeRepository;
import com.beauty.repository.ReviewPhotoRepository;
import com.beauty.repository.ReviewRepository;
import com.beauty.repository.ReviewWaitRepository;
import com.beauty.repository.UserRepository;
import com.beauty.response.CommonResponse;
import com.beauty.response.MyReviewResponse;
import com.beauty.response.result.ResultCode;
import com.beauty.security.auth.JwtAuthenticationToken;
import com.beauty.security.model.UserContext;

@Service
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private ReviewPhotoRepository reviewPhotoRepository;

	@Autowired
	private ReviewLikeRepository reviewLikeRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ReviewWaitRepository reviewWaitRepository;

	@Override
	public Page<Review> list(Long product_id, int photo, int page, int sort, JwtAuthenticationToken token) {
		Product product = productRepository.findOne(product_id);
		String sortStr = "regDate";
		if(sort == 2) {  // 추천순
			sortStr = "star";	
		}

		PageRequest pageRequest = new PageRequest(page, BeautyConstants.PAGE_SIZE_PRODUCT, new Sort(Direction.DESC, sortStr)); //현재페이지, 조회할 페이지수, 정렬정보

		Page<Review> pageReview = null;
		if(photo == 0) {
			pageReview = reviewRepository.findByProduct(product, pageRequest);
		} else {
			pageReview = reviewRepository.findByProductAndReviewType(product, 1, pageRequest);
		}

		if(token != null) {
			UserContext userContext = (UserContext)token.getPrincipal();
			User user = userRepository.findOne(userContext.getUserId());
			List<ReviewLike> myLike = reviewLikeRepository.findByUser(user);
			List<Review> reviews = pageReview.getContent();
			for(Review review:reviews) {
				for(ReviewLike like:myLike) {
					if(review.getReviewId() == like.getReview().getReviewId()) {
						review.setLike(true);
						break;
					}
				}
			}
		}


		return pageReview;

	}

	//	@Override
	//	public String uploadFile(MultipartFile uploadFile, JwtAuthenticationToken token) {
	//
	//		UserContext userContext = (UserContext)token.getPrincipal();
	//		User user = userRepository.findOne(userContext.getUserId());
	//
	//		if(user != null) {
	//			String ftpDirectory = BeautyConstants.FTP_REVIEW + "/" + user.getUserId() + "/" + DateUtil.getDateString("yyyyMMdd");
	//			String filePath = uploadFile(uploadFile, ftpDirectory, "review");
	//			return filePath;
	//		}
	//		return null;
	//	}

	@Override
	@Transactional
	public CommonResponse add(Long wait_id, String[] files, String content, int star, int skin_type, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			ReviewWait rw = reviewWaitRepository.findOne(wait_id);
			if(rw == null || rw.getReviewWrite().equals("Y")) {
				return CommonResponse.of("Order Not Found", ResultCode.FAIL);
			} else {
				Review review = new Review();
				review.setContent(content);
				review.setProduct(rw.getProduct());
				review.setStar(star);
				review.setUser(user);
				review.setSkinType(skin_type);
				if(files == null || files.length == 0) {
					review.setReviewType(0);
					review = reviewRepository.save(review);
					Product product = rw.getProduct();
					product.setReviewCount(product.getReviewCount()+1);
					product.setReviewStar(product.getStarAvg());
					product.setScore(product.getScore() + (star - 3L));
					
					productRepository.save(product);

				} else {
					review.setReviewType(1);
					review = reviewRepository.save(review);
					Product product = rw.getProduct();
					product.setReviewCount(product.getReviewCount()+1);
					product.setReviewStar(product.getStarAvg());
					product.setScore(product.getScore() + (star - 3L));
					productRepository.save(product);

					ReviewPhoto reviewPhoto = null;
					for(String file:files) {
						reviewPhoto = new ReviewPhoto();
						reviewPhoto.setReviewId(review);
						reviewPhoto.setPhotoPath(file);
						reviewPhotoRepository.save(reviewPhoto);
					}
				}

				rw.setReviewWrite("Y");
				rw.setReviewId(review.getReviewId());
				reviewWaitRepository.save(rw);

			}

			//			List<ReviewWaitItem> rwi = reviewWaitItemRepository.findByReviewWait(rw);
			//reviewWaitItemRepository.delete(rwi);

			return CommonResponse.of("success", ResultCode.SUCCESS);
		}
		return CommonResponse.of("User Not Found", ResultCode.FAIL);
	}

	@Override
	public CommonResponse delete(Long reviewId, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			
//			Product product = productRepository.findOne(review.getProduct().getProductId());
//			product.setReviewCount(product.getReviewCount()-1);
//			product.setReviewStar(product.getStarAvg());
//			product.setScore(product.getScore() - (review.getStar() - 3L));

			Review review = reviewRepository.findByReviewIdAndUser(reviewId, user);
			Long productId = review.getProduct().getProductId();
			Long score = review.getStar() - 3L;
			reviewDelete(review, user);
			
			product(productId, score);
			return CommonResponse.of("success", ResultCode.SUCCESS);
		}
		return CommonResponse.of("User Not Found", ResultCode.FAIL);
	}
	
	@Transactional
	public void reviewDelete(Review review, User user) {
		List<ReviewLike> likes = reviewLikeRepository.findByReview(review);
		List<ReviewPhoto> photos = reviewPhotoRepository.findByReviewId(review);
		if(likes != null && likes.size() > 0) {
			reviewLikeRepository.delete(likes);
		}
		if(photos != null && photos.size() > 0) {
			reviewPhotoRepository.delete(photos);
		}

		ReviewWait rw = reviewWaitRepository.findByUserAndReviewId(user, review.getReviewId());
		if(rw != null) {
			rw.setReviewWrite("N");
			rw.setReviewId(0L);
			reviewWaitRepository.save(rw);
		}

		reviewRepository.delete(review.getReviewId());
	}

	public void product(Long productId, Long score) {
		Product product = productRepository.findOne(productId);
		product.setReviewCount(product.getReviewCount()-1);
		product.setReviewStar(product.getStarAvg());
		product.setScore(product.getScore() - score);
		
		productRepository.save(product);
	}
	
	
	@Override
	public CommonResponse like(Long reviewId, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			Review review = reviewRepository.findOne(reviewId);
			if(review != null) {
				ReviewLike like = reviewLikeRepository.findByReviewAndUser(review, user);
				if(like == null) {
					like = new ReviewLike();
					like.setReview(review);
					like.setUser(user);

					reviewLikeRepository.save(like);
					review.setLikeCount(review.getLikeCount()+1);
					reviewRepository.save(review);
					List<ReviewLike> likes = reviewLikeRepository.findByReview(review);
					return CommonResponse.of(""+likes, ResultCode.SUCCESS);
				} else {
					return CommonResponse.of("이미 추천하였습니다.", ResultCode.FAIL);
				}
			}
			return CommonResponse.of("해당 리뷰는 존재하지 않습니다.", ResultCode.FAIL);
		}
		return CommonResponse.of("User Not Found", ResultCode.FAIL);
	}

	@Override
	public MyReviewResponse myReview(JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			List<Review> myReview = reviewRepository.findByUser(user);

			List<ReviewWait> reviewWait = reviewWaitRepository.findByUserAndReviewWrite(user, "N");

			return MyReviewResponse.of("success", ResultCode.SUCCESS, reviewWait, myReview);

		}
		return MyReviewResponse.of("User Not Found", ResultCode.FAIL, null, null);
	}

}
