package com.beauty.admin.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.beauty.common.DateUtil;
import com.beauty.common.RandomStringBuilder;
import com.beauty.entity.Brand;
import com.beauty.entity.Category;
import com.beauty.entity.Coupon;
import com.beauty.entity.CouponUser;
import com.beauty.entity.DataTables;
import com.beauty.entity.Product;
import com.beauty.entity.SpecialCoupon;
import com.beauty.entity.User;
import com.beauty.repository.BrandRepository;
import com.beauty.repository.CategoryRepository;
import com.beauty.repository.CouponRepository;
import com.beauty.repository.CouponUserRepository;
import com.beauty.repository.ProductRepository;
import com.beauty.repository.SpecialCouponRepository;
import com.beauty.repository.UserRepository;
import com.beauty.repository.specification.CouponSpecification;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;
import com.beauty.response.result.ResultCode;

import lombok.Getter;
import lombok.Setter;

@Service
public class AdminCouponServiceImpl implements AdminCouponService {

	@Value("${ftp.host}")
	@Getter @Setter
	String ftpHost;

	@Value("${ftp.username}")
	@Getter @Setter
	String ftpUsername;

	@Value("${ftp.userpassword}")
	@Getter @Setter
	String ftpPassword;

	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private SpecialCouponRepository specialCouponRepository;
	
	@Autowired
	private CouponUserRepository couponUserRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public DataTablesResult list(DataTables input ) {
		Specifications<Coupon> specs = Specifications.where(CouponSpecification.nameLike(input.getSearch().get("value")));
		PageRequest pageRequest = new PageRequest(input.getStart(), input.getLength(), input.getSort("cpName")); //현재페이지, 조회할 페이지수, 정렬정보
		Page<Coupon> coupon = couponRepository.findAll(specs, pageRequest);

		DataTablesResult result = new DataTablesResult();
		result.setDraw(input.getDraw());
		result.setRecordsTotal(coupon.getContent().size());
		result.setRecordsFiltered(coupon.getTotalElements());
		result.setData(coupon.getContent());

		return result;
	}


	@Override
	public CommonResponse save(JSONObject jsonObj) {
		//		{
		//		"thumb_path":"\/beauty\/coupon\/20170215\/thumb_143058384.png",
		//		"cpType":"0",
		//		"cpId":"",
		//		"cpName":"테스트",
		//		"endType":"0",
		//		"saleType":"1",
		//		"box":"",
		//		"cpCount":"100",
		//		"deliveryId":"",
		//		"price":"",
		//		"maximum":"1000",
		//		"after":"10",
		//		"minimum":"25000",
		//		"detail_path":""
		//		}

		Coupon code = null;
		String cpCode = "";
		do {
			cpCode =  new RandomStringBuilder().
					putLimitedChar(RandomStringBuilder.ALPHABET).
					setLength(16).build();
			code = couponRepository.findOne(cpCode);
		} while (code != null);
		
		Coupon coupon = new Coupon();
		coupon.setCpId(cpCode);
	
		
		coupon.setCpName(jsonObj.get("cpName").toString());

		// 0:일반쿠폰, 1:중복쿠폰, 2:특별쿠폰-브랜드, 3:특별쿠폰-카테고리, 4:제품교환권
		int cpType = Integer.parseInt(jsonObj.get("cpType").toString());
		coupon.setCpType(cpType);

		// 0:다운로드 후, 1:특별날짜, 2:매일자정
		int endType = Integer.parseInt(jsonObj.get("endType").toString());
		coupon.setEndType(endType);

		if(endType == 0) {
			int after = Integer.parseInt(jsonObj.get("after").toString());	
			coupon.setEndAfter(after);
		} else if(endType == 1) {
			String endDate = jsonObj.get("selectDate").toString();
			try {
				coupon.setEndDate(DateUtil.stringToDate(endDate, "yyyy-MM-dd HH:mm:ss"));
			} catch (ParseException e) {}
		}

		if(cpType == 0 || cpType == 1) {
			//			할인타입 0 할인율 1할인금액
			int saleType = Integer.parseInt(jsonObj.get("saleType").toString());
			coupon.setSaleType(saleType);
			//			최대 할인금액
			Long maximum = Long.parseLong(jsonObj.get("maximum").toString());
			//			최소 상품금액
			Long minimum = Long.parseLong(jsonObj.get("minimum").toString());
			//			수량	
			int cpCount = Integer.parseInt(jsonObj.get("cpCount").toString());

			if(saleType == 0) {
				//				할인율
				coupon.setPrice(Long.parseLong(jsonObj.get("price").toString()));
			} else {
				coupon.setPrice(maximum);
			}

			coupon.setMaximum(maximum);
			coupon.setMinimum(minimum);
			coupon.setCpCount(cpCount);

			coupon = couponRepository.save(coupon);
			
		}  else if(cpType == 2 || cpType == 3 || cpType == 4) {
			
			// 할인타입 0 할인율 1할인금액
			int saleType = Integer.parseInt(jsonObj.get("saleType").toString());
			coupon.setSaleType(saleType);
			//			최대 할인금액
			Long maximum = Long.parseLong(jsonObj.get("maximum").toString());
			//			최소 상품금액
			Long minimum = Long.parseLong(jsonObj.get("minimum").toString());
			//			수량	
			int cpCount = Integer.parseInt(jsonObj.get("cpCount").toString());

			if(saleType == 0) {
				//				할인율
				coupon.setPrice(Long.parseLong(jsonObj.get("price").toString()));
			} else {
				coupon.setPrice(maximum);
			}

			coupon.setMaximum(maximum);
			coupon.setMinimum(minimum);
			coupon.setCpCount(cpCount);

			coupon = couponRepository.save(coupon);
			
//			쿠폰대상
			JSONArray cprange =  (JSONArray) jsonObj.get("cpRange");
			SpecialCoupon spCoupon = null;
			List<SpecialCoupon> list = new ArrayList<>();
			for (int i=0; i<cprange.size(); i++) {
				spCoupon = new SpecialCoupon();
				spCoupon.setCpId(coupon.getCpId());
				spCoupon.setCtId(Long.parseLong(cprange.get(i).toString()));
				list.add( spCoupon );
			}
			
			specialCouponRepository.save(list);
			
		} else if(cpType == 5) {

			coupon = couponRepository.save(coupon);
			
//			쿠폰대상
			JSONArray cprange =  (JSONArray) jsonObj.get("cpRange");
			SpecialCoupon spCoupon = null;
			List<SpecialCoupon> list = new ArrayList<>();
			for (int i=0; i<cprange.size(); i++) {
				spCoupon = new SpecialCoupon();
				spCoupon.setCpId(coupon.getCpId());
				spCoupon.setCtId(Long.parseLong(cprange.get(i).toString()));
				list.add( spCoupon );
			}
			
			specialCouponRepository.save(list);

			//			적용회원		
			JSONArray rangeUser =  (JSONArray) jsonObj.get("rangeUser");
			List<String> uIds = new ArrayList<>();
			for (int i=0; i<rangeUser.size(); i++) {
				uIds.add(rangeUser.get(i).toString());
			}
			
			CouponUser cu = null;
			List<User> userList = userRepository.findAll(uIds);
			
			List<CouponUser> cuList = new ArrayList<>(); 
			for(User user:userList) {
				cu = new CouponUser();
				cu.setUser(user);
				cu.setCoupon(coupon);
				cu.setUseYn("N");
				
				if(coupon.getEndType() == 0) {
					cu.setStartDate(new Date());
					try {
						cu.setEndDate(DateUtil.stringToDate(DateUtil.getAfterDays(coupon.getEndAfter()), "yyyyMMddHHmm"));
					} catch (ParseException e) {}
				} else if(coupon.getEndType() == 1) {
					cu.setStartDate(coupon.getRegDate());
					cu.setEndDate(coupon.getEndDate());
				} else if(coupon.getEndType() == 2) {
					String startDate = DateUtil.getDateToString("yyyy-MM-dd 00:00:00");
					String endDate = DateUtil.getDateToString("yyyy-MM-dd 23:59:59");
					try {
						cu.setStartDate(DateUtil.stringToDate(startDate, "yyyy-MM-dd 00:00:00"));
						cu.setEndDate(DateUtil.stringToDate(endDate, "yyyy-MM-dd 00:00:00"));
					} catch (ParseException e) {}
				}
				cuList.add(cu);
			}
			
			couponUserRepository.save(cuList);
		}

		return CommonResponse.of("저장 되었습니다.", ResultCode.SUCCESS);
	}

	@Override
	public Coupon getCoupon(String coupon) {
		return couponRepository.findOne(coupon);
	}


	@Override
	public DataTablesResult dataList(DataTables input, String cpId) {
		List<SpecialCoupon> group = specialCouponRepository.findByCpId(cpId);
		List<Long> ids = new ArrayList<>();
		for(SpecialCoupon sc:group) {
			ids.add(sc.getCtId());
		}
		Coupon coupon = couponRepository.findOne(cpId);
//		/0:일반쿠폰, 1:중복쿠폰, 2:특별쿠폰-브랜드, 3:특별쿠폰-카테고리, 4:특별쿠폰-카테고리, 5:제품교환권
		if(coupon.getCpType() == 2) {
			List<Brand> data = brandRepository.findAll(ids);
			DataTablesResult result = new DataTablesResult();
			result.setDraw(input.getDraw());
			result.setRecordsTotal(data.size());
			result.setRecordsFiltered(Long.valueOf(((Integer)data.size()).longValue()));
			result.setData(data);
			
			
			return result;
		}else if(coupon.getCpType() == 3) {
			List<Category> data = categoryRepository.findAll(ids);
			DataTablesResult result = new DataTablesResult();
			result.setDraw(input.getDraw());
			result.setRecordsTotal(data.size());
			result.setRecordsFiltered(Long.valueOf(((Integer)data.size()).longValue()));
			result.setData(data);
			
			return result;
		}else if(coupon.getCpType() == 4) {
			List<Product> data = productRepository.findAll(ids);
			DataTablesResult result = new DataTablesResult();
			result.setDraw(input.getDraw());
			result.setRecordsTotal(data.size());
			result.setRecordsFiltered(Long.valueOf(((Integer)data.size()).longValue()));
			result.setData(data);
			
			return result;
		}else if(coupon.getCpType() == 5) {
			List<Product> data = productRepository.findAll(ids);
			DataTablesResult result = new DataTablesResult();
			result.setDraw(input.getDraw());
			result.setRecordsTotal(data.size());
			result.setRecordsFiltered(Long.valueOf(((Integer)data.size()).longValue()));
			result.setData(data);
			
			return result;
		}
		

		return null;
	}

	@Override
	public CommonResponse download(List<Coupon> coupons, int flag) {
		for(Coupon coupon:coupons) {
			coupon.setDownload(flag);
		}
		couponRepository.save(coupons);
		return CommonResponse.of("저장 되었습니다.", ResultCode.SUCCESS);
	}


	@Override
	public CommonResponse auto(List<Coupon> coupons, int flag) {
		List<Coupon> cp = couponRepository.findByAuto(flag);
		for(Coupon coupon:cp) {
			coupon.setAuto(0);
		}
		couponRepository.save(cp);
		for(Coupon coupon:coupons) {
			coupon.setAuto(flag);
		}
		couponRepository.save(coupons);
		return CommonResponse.of("수정 되었습니다.", ResultCode.SUCCESS);
	}
}
