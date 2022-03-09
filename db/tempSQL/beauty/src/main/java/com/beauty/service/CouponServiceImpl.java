package com.beauty.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beauty.common.DateUtil;
import com.beauty.entity.Brand;
import com.beauty.entity.Category;
import com.beauty.entity.Coupon;
import com.beauty.entity.CouponUser;
import com.beauty.entity.Event;
import com.beauty.entity.EventCoupon;
import com.beauty.entity.Product;
import com.beauty.entity.ProductCategory;
import com.beauty.entity.SpecialCoupon;
import com.beauty.entity.User;
import com.beauty.repository.BrandRepository;
import com.beauty.repository.CategoryRepository;
import com.beauty.repository.CouponRepository;
import com.beauty.repository.CouponUserRepository;
import com.beauty.repository.EventCouponRepository;
import com.beauty.repository.EventRepository;
import com.beauty.repository.ProductCategoryRepository;
import com.beauty.repository.ProductRepository;
import com.beauty.repository.SpecialCouponRepository;
import com.beauty.repository.UserRepository;
import com.beauty.response.CouponUserResponse;
import com.beauty.response.EventCouponResponse;
import com.beauty.response.result.CouponResultCode;
import com.beauty.response.result.ResultCode;
import com.beauty.security.auth.JwtAuthenticationToken;
import com.beauty.security.model.UserContext;

@Service
public class CouponServiceImpl implements CouponService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private EventCouponRepository eventCouponRepository;

	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private CouponUserRepository couponUserRepository;

	@Autowired
	private SpecialCouponRepository specialCouponRepository;
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	
	@Override
	public EventCouponResponse getList(Long eId) {
		Event event = eventRepository.findOne(eId);
		if(event == null) {
			return EventCouponResponse.of("Event Not Found", ResultCode.FAIL, null);
		}

		if(event.getEtype() == 4) {
			List<EventCoupon> coupon = eventCouponRepository.findByEvent(event);
			return EventCouponResponse.of("success", ResultCode.SUCCESS, coupon);
		}

		return EventCouponResponse.of("Event Not Found", ResultCode.FAIL, null);
	}

	@Override
	public CouponUserResponse myCoupon(JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			List<CouponUser> couponUser;
			try {
				couponUser = couponUserRepository.findByUserAndEndDateAfter(user, DateUtil.stringToDate(DateUtil.getBeforeDays(15, "yyyy-MM-dd 00:00:00"), "yyyy-MM-dd HH:mm:ss"));
				Coupon cp = null;
				List<SpecialCoupon> spList = null;
				List<Long> ids = new ArrayList<>();
				List<Product> prodList = null;
				for(CouponUser cu:couponUser) {
					cp = cu.getCoupon();
					//2:특별쿠폰-브랜드, 3:특별쿠폰-카테고리, 4:특별쿠폰-제품, 5:제품교환권
					if(cp.getCpType() > 1) {
						spList = specialCouponRepository.findByCpId(cp.getCpId());
						for(SpecialCoupon sc:spList) {
							ids.add(sc.getCtId());
						}
						// 브랜드
						if(cp.getCpType() == 2) {
							List<Brand> brand = brandRepository.findAll(ids);
							prodList = productRepository.findByBrandIn(brand);
							cu.setProductList(prodList);
						} else if(cp.getCpType() == 3) {
							List<Category> category = categoryRepository.findAll(ids);
							List<ProductCategory> pc = productCategoryRepository.findByCategoryIn(category);
							prodList = new ArrayList<>();
							for(ProductCategory p:pc) {
								prodList.add(p.getProduct());
							}
							
							cu.setProductList(prodList);
						} else if(cp.getCpType() == 4 || cp.getCpType() == 5) {
							prodList = productRepository.findAll(ids);
							cu.setProductList(prodList);
						}
					}
					
				}
				

				
				return CouponUserResponse.of("success", CouponResultCode.SUCCESS, couponUser);
			} catch (ParseException e) {}

		}
		return CouponUserResponse.of("User Not Found", CouponResultCode.FAIL, null);
	}

	@Override
	public CouponUserResponse coupon(JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			List<CouponUser> couponUser = couponUserRepository.findByUserAndEndDateAfterAndUseYn(user, new Date(), "N");
			
			Coupon cp = null;
			List<SpecialCoupon> spList = null;
			List<Long> ids = new ArrayList<>();
			List<Product> prodList = null;
			for(CouponUser cu:couponUser) {
				cp = cu.getCoupon();
				//2:특별쿠폰-브랜드, 3:특별쿠폰-카테고리, 4:특별쿠폰-제품, 5:제품교환권
				if(cp.getCpType() > 1) {
					spList = specialCouponRepository.findByCpId(cp.getCpId());
					for(SpecialCoupon sc:spList) {
						ids.add(sc.getCtId());
					}
					// 브랜드
					if(cp.getCpType() == 2) {
						List<Brand> brand = brandRepository.findAll(ids);
						prodList = productRepository.findByBrandIn(brand);
						cu.setProductList(prodList);
					} else if(cp.getCpType() == 3) {
						List<Category> category = categoryRepository.findAll(ids);
						List<ProductCategory> pc = productCategoryRepository.findByCategoryIn(category);
						prodList = new ArrayList<>();
						for(ProductCategory p:pc) {
							prodList.add(p.getProduct());
						}
						
						cu.setProductList(prodList);
					} else if(cp.getCpType() == 4 || cp.getCpType() == 5) {
						prodList = productRepository.findAll(ids);
						cu.setProductList(prodList);
					}
				}
			}
			return CouponUserResponse.of("success", CouponResultCode.SUCCESS, couponUser);

		}
		return CouponUserResponse.of("User Not Found", CouponResultCode.FAIL, null);
	}

	@Override
	public CouponUserResponse couponDown(String couponId, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			Coupon coupon = couponRepository.findOne(couponId);
			if(coupon == null) {
				return CouponUserResponse.of("Coupon Not Found", CouponResultCode.FAIL, null);
			}

			if(coupon.getCpCount() == 0) {
				return CouponUserResponse.of("쿠폰이 모두 소진되었습니다.", CouponResultCode.CP_EMPTY, null);
			}

			if(coupon.getDownload() == 0) {
				return CouponUserResponse.of("종료된 쿠폰입니다.", CouponResultCode.CP_END, null);
			}

			List<CouponUser> couponUser = couponUserRepository.findByUserAndCouponAndUseYn(user, coupon, "N");
			if(couponUser == null || couponUser.size() == 0) {
				downCoupon(coupon, user);
				return CouponUserResponse.of("success", CouponResultCode.SUCCESS, null);	
			} else {
				return CouponUserResponse.of("이미 다운받은 쿠폰입니다.", CouponResultCode.DOWN_CP, null);
			}

		}
		return CouponUserResponse.of("User Not Found", CouponResultCode.FAIL, null);
	}

	@Override
	public boolean downCoupon(Coupon coupon, User user) {
		CouponUser couponUser = new CouponUser();
		couponUser.setUser(user);
		couponUser.setCoupon(coupon);
		couponUser.setUseYn("N");

		if(coupon.getEndType() == 0) {
			couponUser.setStartDate(new Date());
			try {
				couponUser.setEndDate(DateUtil.stringToDate(DateUtil.getAfterDays(coupon.getEndAfter()), "yyyyMMddHHmm"));
			} catch (ParseException e) {}
		} else if(coupon.getEndType() == 1) {
			couponUser.setStartDate(coupon.getRegDate());
			couponUser.setEndDate(coupon.getEndDate());
		} else if(coupon.getEndType() == 2) {
			String startDate = DateUtil.getDateToString("yyyy-MM-dd 00:00:00");
			String endDate = DateUtil.getDateToString("yyyy-MM-dd 23:59:59");
			try {
				couponUser.setStartDate(DateUtil.stringToDate(startDate, "yyyy-MM-dd 00:00:00"));
				couponUser.setEndDate(DateUtil.stringToDate(endDate, "yyyy-MM-dd 00:00:00"));
			} catch (ParseException e) {}
		}

		couponUserRepository.save(couponUser);
		coupon.setCpCount(coupon.getCpCount() - 1);
		couponRepository.save(coupon);
		return true;
	}

	@Override
	public void autoCoupon(User user, int auto) {
		List<Coupon> ac = couponRepository.findByDownloadAndAuto(1, auto);
		if(ac != null) {
			for(Coupon coupon:ac) {
				downCoupon(coupon, user);
			}
		}
	}


}
