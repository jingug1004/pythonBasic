package com.beauty.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
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
import com.beauty.entity.DataTables;
import com.beauty.entity.Event;
import com.beauty.entity.MainFirstBanner;
import com.beauty.entity.MainMenu;
import com.beauty.entity.MainMenuItem;
import com.beauty.entity.MainSecondBanner;
import com.beauty.entity.MainTenMenu;
import com.beauty.entity.Plan;
import com.beauty.entity.Product;
import com.beauty.repository.BrandRepository;
import com.beauty.repository.EventRepository;
import com.beauty.repository.MainFirstBannerRepository;
import com.beauty.repository.MainMenuItemRepository;
import com.beauty.repository.MainMenuRepository;
import com.beauty.repository.MainSecondBannerRepository;
import com.beauty.repository.MainTenMenuRepository;
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
public class AdminMainServiceImpl implements AdminMainService {
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
	private MainFirstBannerRepository mainFirstBannerRepository;

	@Autowired
	private MainSecondBannerRepository mainSecondBannerRepository;

	@Autowired
	private MainTenMenuRepository mainTenMenuRepository;

	@Autowired
	private MainMenuRepository mainMenuRepository;

	@Autowired
	private MainMenuItemRepository mainMenuItemRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private PlanRepository planRepository;

	@Autowired
	private EventRepository eventRepository;

	@Override
	public DataTablesResult first(DataTables input, int showType ) {
		PageRequest pageRequest = new PageRequest(input.getStart(), input.getLength(), input.getSort("regDate")); //현재페이지, 조회할 페이지수, 정렬정보

		Page<MainFirstBanner> first = mainFirstBannerRepository.findByShowType(showType, pageRequest);

		DataTablesResult result = new DataTablesResult();
		result.setDraw(input.getDraw());
		result.setRecordsTotal(first.getContent().size());
		result.setRecordsFiltered(first.getTotalElements());
		result.setData(first.getContent());

		return result;
	}

	@Override
	public DataTablesResult second(DataTables input ) {
		PageRequest pageRequest = new PageRequest(input.getStart(), input.getLength(), input.getSort("regDate")); //현재페이지, 조회할 페이지수, 정렬정보
		Page<MainSecondBanner> second = mainSecondBannerRepository.findAll(pageRequest);

		DataTablesResult result = new DataTablesResult();
		result.setDraw(input.getDraw());
		result.setRecordsTotal(second.getContent().size());
		result.setRecordsFiltered(second.getTotalElements());
		result.setData(second.getContent());

		return result;
	}

	@Override
	public DataTablesResult menu(DataTables input ) {
		PageRequest pageRequest = new PageRequest(input.getStart(), input.getLength(), input.getSort("regDate")); //현재페이지, 조회할 페이지수, 정렬정보
		Page<MainTenMenu> ten = mainTenMenuRepository.findAll(pageRequest);

		DataTablesResult result = new DataTablesResult();
		result.setDraw(input.getDraw());
		result.setRecordsTotal(ten.getContent().size());
		result.setRecordsFiltered(ten.getTotalElements());
		result.setData(ten.getContent());

		return result;
	}

	@Override
	public DataTablesResult list(DataTables input ) {
		PageRequest pageRequest = new PageRequest(input.getStart(), input.getLength(), input.getSort("regDate")); //현재페이지, 조회할 페이지수, 정렬정보
		Page<MainMenu> menu = mainMenuRepository.findAll(pageRequest);

		DataTablesResult result = new DataTablesResult();
		result.setDraw(input.getDraw());
		result.setRecordsTotal(menu.getContent().size());
		result.setRecordsFiltered(menu.getTotalElements());
		result.setData(menu.getContent());

		return result;
	}

	@Override
	public String getData(int eType) {
		if(eType == 0 || eType == 4) {//출석체크
			List<Event> data = eventRepository.findByStopEvent(0);
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
		} else if(eType == 1) { // 브랜드
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
			List<Plan> data = planRepository.findAll();


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

	@Override
	public CommonResponse firstDelete(List<MainFirstBanner> datas) {
		mainFirstBannerRepository.delete(datas);
		return CommonResponse.of("삭제 되었습니다.", ResultCode.SUCCESS);
	}

	@Override
	public CommonResponse secondDelete(List<MainSecondBanner> datas) {
		mainSecondBannerRepository.delete(datas);
		return CommonResponse.of("삭제 되었습니다.", ResultCode.SUCCESS);
	}

	@Override
	public CommonResponse menuDelete(List<MainTenMenu> datas) {
		mainTenMenuRepository.delete(datas);
		return CommonResponse.of("삭제 되었습니다.", ResultCode.SUCCESS);
	}

	@Override
	public CommonResponse listDelete(List<MainMenu> datas) {
		mainMenuItemRepository.deleteByMainMenu(datas);
		mainMenuRepository.delete(datas);
		return CommonResponse.of("삭제 되었습니다.", ResultCode.SUCCESS);
	}

	@Override
	public CommonResponse firstSave(JSONObject jsonObj) {
		MainFirstBanner mfb = null;
		String action = jsonObj.get("action").toString();
		int showType = Integer.parseInt(jsonObj.get("showType").toString());
		if(action.equals("write")) {
			mfb = new MainFirstBanner();	
			MainFirstBanner last = mainFirstBannerRepository.findFirstByShowTypeOrderBySortDesc(showType);
			if(last == null) {
				mfb.setSort(1);
			} else {
				mfb.setSort(last.getSort() + 1);
			}

			mfb.setShowYn("Y");
		} else {
			Long id = Long.parseLong(jsonObj.get("dId").toString());
			mfb = mainFirstBannerRepository.findOne(id);
		}

		mfb.setShowType(showType);
		int sbtype = Integer.parseInt(jsonObj.get("sbtype").toString());
		mfb.setSbType(sbtype);
		//		if(sbtype == 0 || sbtype == 4) {
		//			msb.setTid(0L);	
		//		} else {
		Long tid = Long.parseLong(jsonObj.get("prod").toString());
		mfb.setTid(tid);
		//			}
		mfb.setThumbnail(jsonObj.get("thumb_path").toString());

		mainFirstBannerRepository.save(mfb);
		return CommonResponse.of("저장 되었습니다.", ResultCode.SUCCESS);
	}

	@Override
	public CommonResponse secondSave(JSONObject jsonObj) {
		MainSecondBanner msb = null;
		String action = jsonObj.get("action").toString();
		if(action.equals("write")) {
			msb = new MainSecondBanner();
			MainSecondBanner last = mainSecondBannerRepository.findFirstByOrderBySortDesc();
			if(last == null) {
				msb.setSort(1);
			} else {
				msb.setSort(last.getSort() + 1);
			}
			msb.setShowYn("Y");
		} else {
			Long id = Long.parseLong(jsonObj.get("dId").toString());
			msb = mainSecondBannerRepository.findOne(id);
		}

		int sbtype = Integer.parseInt(jsonObj.get("sbtype").toString());
		msb.setSbType(sbtype);
		//		if(sbtype == 0 || sbtype == 4) {
		//			msb.setTid(0L);	
		//		} else {
		Long tid = Long.parseLong(jsonObj.get("prod").toString());
		msb.setTid(tid);
		//			}

		msb.setThumbnail(jsonObj.get("thumb_path").toString());
		mainSecondBannerRepository.save(msb);
		return CommonResponse.of("저장 되었습니다.", ResultCode.SUCCESS);
	}

	@Override
	public CommonResponse menuSave(JSONObject jsonObj) {
		MainTenMenu mtm = null;
		String action = jsonObj.get("action").toString();
		if(action.equals("write")) {
			mtm = new MainTenMenu();	
		} else {
			Long id = Long.parseLong(jsonObj.get("dId").toString());
			mtm = mainTenMenuRepository.findOne(id);
		}

		mainTenMenuRepository.save(mtm);
		return CommonResponse.of("저장 되었습니다.", ResultCode.SUCCESS);
	}

	@Override
	public CommonResponse listSave(JSONObject jsonObj) {
		MainMenu mm = null;
		String action = jsonObj.get("action").toString();
		if(action.equals("write")) {
			mm = new MainMenu();	
			MainMenu last = mainMenuRepository.findFirstByOrderBySortDesc();
			if(last == null) {
				mm.setSort(1);
			} else {
				mm.setSort(last.getSort() + 1);
			}

			mm.setShowYn("Y");
		} else {
			Long id = Long.parseLong(jsonObj.get("dId").toString());
			mm = mainMenuRepository.findOne(id);
		}
		String name = jsonObj.get("subject").toString();
		mm.setName(name);

		mm = mainMenuRepository.save(mm);


		JSONArray prod =  (JSONArray) jsonObj.get("prod");
		List<Long> list = new ArrayList<>();
		for (int i=0; i<prod.size(); i++) {
			list.add( Long.parseLong(prod.get(i).toString()) );
		}

		List<Product> product = productRepository.findAll(list);
		mainMenuItemRepository.deleteByMainMenu(mm);
		List<MainMenuItem> mmil = new ArrayList<>();
		MainMenuItem mmi = null;
		for(Product p:product) {
			mmi = new MainMenuItem();
			mmi.setProduct(p);
			mmi.setMainMenu(mm);

			mmil.add(mmi);
		}

		mainMenuItemRepository.save(mmil);
		return CommonResponse.of("저장 되었습니다.", ResultCode.SUCCESS);
	}


	@Override
	public String reorder(Long sbid, int oldorder, int neworder) {
		List<MainSecondBanner> data =new ArrayList<>();
		// 5 > 6
		if(neworder > oldorder) {
			// pro <= new And old < pro 
			//neworder 보다 작거나 같고 oldorder 보다 큰 순위 - 1 findByProductOrdLessThanEqualAndProductOrdGreaterThan
			List<MainSecondBanner> bList = mainSecondBannerRepository.findBySortLessThanEqualAndSortGreaterThan(neworder, oldorder);
			if(bList != null) {
				for(MainSecondBanner e:bList) {
					e.setSort(e.getSort()-1);
					data.add(e);
				}
			}

		} else {
			//neworder 보다 크거나 같고 oldorder 보다 작은 순위 + 1  findByProductOrdLessThanProductOrdGreaterThanEqual
			List<MainSecondBanner> bList = mainSecondBannerRepository.findBySortLessThanAndSortGreaterThanEqual(oldorder, neworder);
			if(bList != null) {
				for(MainSecondBanner e:bList) {
					e.setSort(e.getSort()+1);
					data.add(e);
				}
			}
		}


		MainSecondBanner msb = mainSecondBannerRepository.findOne(sbid);
		msb.setSort(neworder);
		data.add(msb);

		mainSecondBannerRepository.save(data);
		return "success";
	}

	@Override
	public CommonResponse uploadThumb(MultipartFile image) {

		String ftpDirectory = BeautyConstants.FTP_FIRST + "/" + DateUtil.getDateString("yyyyMMdd");
		return uploadFile(image, ftpDirectory);
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

}
