package com.beauty.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beauty.entity.Qna;
import com.beauty.entity.QnaPhoto;
import com.beauty.entity.User;
import com.beauty.repository.QnaPhotoRepository;
import com.beauty.repository.QnaRepository;
import com.beauty.repository.UserRepository;
import com.beauty.response.CommonResponse;
import com.beauty.response.QnaListResponse;
import com.beauty.response.result.ResultCode;
import com.beauty.security.auth.JwtAuthenticationToken;
import com.beauty.security.model.UserContext;

@Service
public class QnaServiceImpl implements QnaService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private QnaRepository qnaRepository;

	@Autowired
	private QnaPhotoRepository qnaPhotoRepository;

	@Override
	public QnaListResponse qnaList(JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			List<Qna> result = qnaRepository.findByUserOrderByRegDateDesc(user);

			return QnaListResponse.of("success", ResultCode.SUCCESS, result);
		}
		return QnaListResponse.of("User Not Found", ResultCode.FAIL, null);
	}


	@Override
	public CommonResponse saveQna(Long orderId, String subject, String content,String qnaClass1, String qnaClass2,String[] files, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			Qna qna = new Qna();
			qna.setSubject(subject);
			qna.setContent(content);
			qna.setQnaClass1(qnaClass1);
			qna.setQnaClass2(qnaClass2);
			qna.setUser(user);

//			if(orderId != null) {
//				ProductOrder order = productOrderRepository.findOne(orderId);
//				if(order != null) { 
//					qna.setOrderId(order.getOrderId());
//				}
//			}


			qna = qnaRepository.save(qna);
			if(files != null) {
				QnaPhoto qnaPhoto = new QnaPhoto();
				for(String file:files) {
					qnaPhoto.setQnaId(qna.getId());
					qnaPhoto.setPhotoPath(file);
					qnaPhotoRepository.save(qnaPhoto);
				}
			}

			return CommonResponse.of("success", ResultCode.SUCCESS);
		}
		return CommonResponse.of("User Not Found", ResultCode.FAIL);
	}

	@Override
	public QnaListResponse delete(Long qnaId, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			Qna qna = qnaRepository.findByIdAndUser(qnaId, user);

			qnaPhotoRepository.deleteByQnaId(qna.getId());

			qnaRepository.delete(qna);

			List<Qna> result = qnaRepository.findByUserOrderByRegDateDesc(user);
			return QnaListResponse.of("success", ResultCode.SUCCESS, result);
		}
		return QnaListResponse.of("User Not Found", ResultCode.FAIL, null);
	}


}
