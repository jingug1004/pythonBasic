package com.beauty.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.beauty.BeautyConstants;
import com.beauty.common.DateUtil;
import com.beauty.common.FtpClientUtil;
import com.beauty.entity.Brand;
import com.beauty.entity.Coupon;
import com.beauty.entity.DataTables;
import com.beauty.entity.Event;
import com.beauty.entity.Plan;
import com.beauty.entity.Product;
import com.beauty.entity.ProductTimeSale;
import com.beauty.repository.BrandRepository;
import com.beauty.repository.CouponRepository;
import com.beauty.repository.EventRepository;
import com.beauty.repository.PlanRepository;
import com.beauty.repository.ProductRepository;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;
import com.beauty.response.result.ResultCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

@Service
public class AdminEventServiceImpl implements AdminEventService {
	@Value("${server.image.url}")
	@Getter @Setter
	String image_url;
	
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
	private EventRepository eventRepository;
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private PlanRepository planRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CouponRepository couponRepository;
	
	@Override
	public DataTablesResult list(DataTables input ) {
		PageRequest pageRequest = new PageRequest(input.getStart(), input.getLength(), input.getSort("regDate")); //현재페이지, 조회할 페이지수, 정렬정보
		Page<Event> event = eventRepository.findAll(pageRequest);
		
		DataTablesResult result = new DataTablesResult();
		result.setDraw(input.getDraw());
		result.setRecordsTotal(event.getContent().size());
		result.setRecordsFiltered(event.getTotalElements());
		result.setData(event.getContent());
		
		return result;
	}

	@Override
	public CommonResponse eventStat(List<Event> events,  int stat) {
		for(Event event:events) {
			event.setStopEvent(stat);
		}
		eventRepository.save(events);
		if(stat == 0) {
			return CommonResponse.of("선택한 이벤트를 진행 하였습니다.", ResultCode.SUCCESS);
		} else {
			return CommonResponse.of("선택한 이벤트가 종료 되었습니다.", ResultCode.SUCCESS);	
		}
		
	}
	
	@Override
	public String uploadImage(MultipartFile image) {
		
		String subname = "event";
		String ftpDirectory = BeautyConstants.FTP_EVENT + "/" + DateUtil.getDateString("yyyyMMdd");
		return uploadFile(image, ftpDirectory, subname);
	}
	
	@Override
	public CommonResponse uploadThumb(MultipartFile image) {
		
		String ftpDirectory = BeautyConstants.FTP_EVENT + "/" + DateUtil.getDateString("yyyyMMdd");
		return uploadFile(image, ftpDirectory);
	}
	
	
	@Transactional
	@Override
	public CommonResponse save(JSONObject jsonObj) {
		Event event = new Event();
		
		event.setThumbnail(jsonObj.get("thumb_path").toString());
		int eType = Integer.parseInt(jsonObj.get("etype").toString());
		event.setEtype(eType);
		Event lastEvent = eventRepository.findFirstByOrderBySortDesc();
		if(lastEvent == null) {
			event.setSort(1);
		} else {
			event.setSort(lastEvent.getSort() + 1);
		}
		event.setStopEvent(1);
		
		switch (eType) {
		case 0:
			event.setContent(jsonObj.get("content").toString());
			event.setTid(0L);
			break;
		case 1:
			event.setTid(Long.parseLong(jsonObj.get("tid").toString()));
			break;
		case 2:
			event.setTid(Long.parseLong(jsonObj.get("tid").toString()));
			break;
		case 4:
			event.setContent(jsonObj.get("content").toString());
			event.setTid(0L);
			break;
		case 5:
			event.setContent(jsonObj.get("content").toString());
			event.setTid(0L);
			break;
		default:
			event.setTid(0L);
			break;
		}

		eventRepository.save(event);
		return CommonResponse.of("저장 되었습니다.", ResultCode.SUCCESS);
	}

	@Transactional
	@Override
	public CommonResponse update(JSONObject jsonObj) {
		Long eid = Long.parseLong(jsonObj.get("eid").toString());
		Event event = eventRepository.findOne(eid);
		event.setThumbnail(jsonObj.get("thumb_path").toString());
		
		System.out.println(event.getEtype());
		switch (event.getEtype()) {
		case 4:
			event.setContent(jsonObj.get("content").toString());
			break;
		case 5:
			event.setContent(jsonObj.get("content").toString());
			break;
		default:
			break;
		}

		eventRepository.save(event);
		return CommonResponse.of("저장 되었습니다.", ResultCode.SUCCESS);
	}
	
	@Override
	public Event getEvent(Long eid) {
		return eventRepository.findOne(eid);
	}

	@Override
	public String getData(int eType) {
		if(eType == 1) { // 브랜드
			List<Brand> data = brandRepository.findByBrandVisibleOrderByBrandNameAsc("Y");


			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("data", data);

			ObjectMapper objectMapper = new ObjectMapper();
			try {
				return objectMapper.writeValueAsString(map);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} else if(eType == 2) { // 상품
			List<Product> data = productRepository.findByStopSelling(0);


			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("data", data);

			ObjectMapper objectMapper = new ObjectMapper();
			try {
				return objectMapper.writeValueAsString(map);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} else if(eType == 3) { // 기획전
			List<Plan> data = planRepository.findByStopPlan(0);


			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("data", data);

			ObjectMapper objectMapper = new ObjectMapper();
			try {
				return objectMapper.writeValueAsString(map);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} else if(eType == 4) { // 쿠폰
			List<Coupon> data = couponRepository.findAll();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("data", data);

			ObjectMapper objectMapper = new ObjectMapper();
			try {
				return objectMapper.writeValueAsString(map);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	public String uploadFile( MultipartFile localDirectoryAndFileName, String ftpDirectory, String sub_name) {
		FtpClientUtil f = new FtpClientUtil(ftpHost, 21, ftpUsername, ftpPassword);
		String result = null;
		try {
			if (f.open()) {
				result = f.put(localDirectoryAndFileName, ftpDirectory, sub_name);
				f.close();
				f = null;
			}
		} catch (Exception e) {
			return "";
		}

		return image_url + result;
	}

	public CommonResponse uploadFile( MultipartFile localDirectoryAndFileName, String ftpDirectory) {
		FtpClientUtil f = new FtpClientUtil(ftpHost, 21, ftpUsername, ftpPassword);
		String result = null;
		try {
			if (f.open()) {
				result = f.put(localDirectoryAndFileName, ftpDirectory, "thumb");
				f.close();
				f = null;
			}
		} catch (Exception e) {
			return CommonResponse.of("fail", ResultCode.FAIL);
		}

		return CommonResponse.of(result, ResultCode.SUCCESS);
	}

	@Override
	public String reorder(Long eid, int oldorder, int neworder) {
		List<Event> event =new ArrayList<>();
		// 5 > 6
		if(neworder > oldorder) {
			// pro <= new And old < pro 
			//neworder 보다 작거나 같고 oldorder 보다 큰 순위 - 1 findByProductOrdLessThanEqualAndProductOrdGreaterThan
			List<Event> eventList = eventRepository.findBySortLessThanEqualAndSortGreaterThan(neworder, oldorder);
			if(eventList != null) {
				for(Event e:eventList) {
					e.setSort(e.getSort()-1);
					event.add(e);
				}
			}
			
		} else {
			//neworder 보다 크거나 같고 oldorder 보다 작은 순위 + 1  findByProductOrdLessThanProductOrdGreaterThanEqual
			List<Event> eventList = eventRepository.findBySortLessThanAndSortGreaterThanEqual(oldorder, neworder);
			if(eventList != null) {
				for(Event e:eventList) {
					e.setSort(e.getSort()+1);
					event.add(e);
				}
			}
		}
		
		
		Event e = eventRepository.findOne(eid);
		e.setSort(neworder);
		event.add(e);
		
		eventRepository.save(event);
		return "success";
	}

	@Override
	public List<Event> eventList() {
		return eventRepository.findByStopEvent(0);
	}
	
	@Override
	public CommonResponse delete(List<Event> event) {
		// DELETE FROM APP_EVENT WHERE e_id in ()
		eventRepository.delete(event);
		
		return CommonResponse.of("삭제 되었습니다.", ResultCode.SUCCESS);
	}
	
}
