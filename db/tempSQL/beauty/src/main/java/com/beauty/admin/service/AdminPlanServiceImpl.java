package com.beauty.admin.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.beauty.BeautyConstants;
import com.beauty.common.Base64;
import com.beauty.common.DateUtil;
import com.beauty.common.FtpClientUtil;
import com.beauty.entity.DataTables;
import com.beauty.entity.Plan;
import com.beauty.entity.PlanGroup;
import com.beauty.entity.PlanProduct;
import com.beauty.entity.Product;
import com.beauty.repository.PlanGroupRepository;
import com.beauty.repository.PlanProductRepository;
import com.beauty.repository.PlanRepository;
import com.beauty.repository.ProductRepository;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;
import com.beauty.response.result.ResultCode;

import lombok.Getter;
import lombok.Setter;

@Service
public class AdminPlanServiceImpl implements AdminPlanService {
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
	private PlanRepository planRepository;

	@Autowired
	private PlanGroupRepository planGroupRepository;

	@Autowired
	private PlanProductRepository planProductRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	@Transactional
	public DataTablesResult list(DataTables input ) {
		PageRequest pageRequest = new PageRequest(input.getStart(), input.getLength(), input.getSort("regDate")); //현재페이지, 조회할 페이지수, 정렬정보
		Page<Plan> plan = planRepository.findAll(pageRequest);
		DataTablesResult result = new DataTablesResult();
		result.setDraw(input.getDraw());
		result.setRecordsTotal(plan.getContent().size());
		result.setRecordsFiltered(plan.getTotalElements());
		result.setData(plan.getContent());

		return result;
	}

	@Override
	@Transactional
	public Plan getPlan(Long pid) {
		return planRepository.findOne(pid);
	}

	@Override
	public CommonResponse planStat(List<Plan> plans,  int stat) {
		for(Plan plan:plans) {
			plan.setStopPlan(stat);
		}
		planRepository.save(plans);
		if(stat == 0) {
			return CommonResponse.of("선택한 기획전을 진행 하였습니다.", ResultCode.SUCCESS);
		} else {
			return CommonResponse.of("선택한 기획전이 종료 되었습니다.", ResultCode.SUCCESS);	
		}

	}

	@Transactional
	@Override
	public CommonResponse save(JSONObject jsonObj) {
		Plan plan = null;
		String action = jsonObj.get("action").toString();
		if(action.equals("write")) {
			plan = new Plan();

			Plan lastPlan = planRepository.findFirstByOrderBySortDesc();
			if(lastPlan == null) {
				plan.setSort(1);
			} else {
				plan.setSort(lastPlan.getSort() + 1);
			}
			plan.setStopPlan(1);

		} else {
			plan = planRepository.findOne(Long.parseLong(jsonObj.get("pid").toString()));
		}


		if(jsonObj.get("thumbUrl") != null && !jsonObj.get("thumbUrl").equals("")) {
			Optional<InputStream> inputStream;
			try {
				inputStream = Base64.Base64InputStream(jsonObj.get("thumbUrl").toString());
				String ftpDirectory = BeautyConstants.FTP_EVENT + "/" + DateUtil.getDateString("yyyyMMdd");
				String uploadFile = uploadFile(inputStream, ftpDirectory, "thumb");

				plan.setThumbnail(uploadFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		plan.setTitle(jsonObj.get("title").toString());

		planRepository.save(plan);
		return CommonResponse.of("저장 되었습니다.", ResultCode.SUCCESS);
	}


	public String uploadFile( Optional<InputStream> localDirectoryAndFileName, String ftpDirectory, String sub_name) {
		FtpClientUtil f = new FtpClientUtil(ftpHost, 21, ftpUsername, ftpPassword);
		String result = null;
		try {
			if (f.open()) {
				result = f.put(localDirectoryAndFileName, ftpDirectory, sub_name);
				f.close();
				f = null;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return result;
	}

	//  ------------- GROUP ----------------- //

	@Override
	@Transactional
	public DataTablesResult groupList(DataTables input, Long pid) {
		Plan plan = planRepository.findOne(pid);
		List<PlanGroup> group = planGroupRepository.findByPlanOrderBySortAsc(plan);

		DataTablesResult result = new DataTablesResult();
		result.setDraw(input.getDraw());
		result.setRecordsTotal(group.size());
		result.setRecordsFiltered(Long.valueOf(((Integer)group.size()).longValue()));
		result.setData(group);

		return result;
	}

	@Override
	public CommonResponse groupStat(List<PlanGroup> groups, int stat, Long pid) {
		Plan plan = planRepository.findOne(pid);
		for(PlanGroup group:groups) {
			group.setStopGroup(stat);
			group.setPlan(plan);
		}
		planGroupRepository.save(groups);
		if(stat == 0) {
			return CommonResponse.of("선택한 아이템을 진행 하였습니다.", ResultCode.SUCCESS);
		} else {
			return CommonResponse.of("선택한 아이템이 종료 되었습니다.", ResultCode.SUCCESS);	
		}

	}

	@Override
	public CommonResponse groupSave(JSONObject jsonObj) {

		PlanGroup group = new PlanGroup();
		String action = jsonObj.get("action").toString();

		if(action.equals("write")) {
			group = new PlanGroup();
			group.setStopGroup(0);
			PlanGroup last = planGroupRepository.findFirstByOrderBySortDesc();
			if(last == null) {
				group.setSort(1);
			} else {
				group.setSort(last.getSort() + 1);
			}
		} else {
			Long pgid = Long.parseLong(jsonObj.get("pgId").toString());
			group = planGroupRepository.findOne(pgid);
		}

		Long pid = Long.parseLong(jsonObj.get("pid").toString());
		Plan plan = planRepository.findOne(pid);
		group.setPlan(plan);

		if(jsonObj.get("thumbUrl") != null && !jsonObj.get("thumbUrl").equals("")) {
			Optional<InputStream> inputStream;
			try {
				inputStream = Base64.Base64InputStream(jsonObj.get("thumbUrl").toString());
				String ftpDirectory = BeautyConstants.FTP_EVENT + "/" + DateUtil.getDateString("yyyyMMdd");
				String uploadFile = uploadFile(inputStream, ftpDirectory, "group");

				group.setThumbnail(uploadFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		planGroupRepository.save(group);

		return CommonResponse.of("저장 되었습니다.", ResultCode.SUCCESS);
	}

	@Override
	public DataTablesResult itemList(DataTables input, Long pgid) {
		PlanGroup planGroup = planGroupRepository.findOne(pgid);
		List<PlanProduct> planProduct = planProductRepository.findByPlanGroup(planGroup);

		DataTablesResult result = new DataTablesResult();
		result.setDraw(input.getDraw());
		result.setRecordsTotal(planProduct.size());
		result.setRecordsFiltered(Long.valueOf(((Integer)planProduct.size()).longValue()));
		result.setData(planProduct);

		return result;
	}

	@Override
	public List<Product> getProduct(Long pgid) {
		PlanGroup planGroup = planGroupRepository.findOne(pgid);
		List<PlanProduct> planProduct = planProductRepository.findByPlanGroup(planGroup);
		List<Long> ids = new ArrayList<>();
		for(PlanProduct pp : planProduct) {
			ids.add(pp.getProduct().getProductId());
		}
		List<Product> product = null;
		if(ids.size() > 0) {
			product = productRepository.findByStopSellingAndProductIdNotIn(0, ids);
		} else {
			product = productRepository.findByStopSelling(0);	
		}
		
		return product;
	}

	@Override
	public CommonResponse itemSave(JSONObject jsonObj) {
		Long pgid = Long.parseLong(jsonObj.get("pgId").toString());
		
		PlanGroup planGroup = planGroupRepository.findOne(pgid);
		
		JSONArray prod =  (JSONArray) jsonObj.get("prod");
		List<Long> list = new ArrayList<>();
		for (int i=0; i<prod.size(); i++) {
		    list.add( Long.parseLong(prod.get(i).toString()) );
		}
		
		List<Product> product = productRepository.findAll(list);
		
		List<PlanProduct> ppl = new ArrayList<>();
		PlanProduct pp = null;
		for(Product p:product) {
			pp = new PlanProduct();
			pp.setProduct(p);
			pp.setPlanGroup(planGroup);
			
			ppl.add(pp);
		}
		planProductRepository.save(ppl);
		return CommonResponse.of("저장 되었습니다.", ResultCode.SUCCESS);
	}

	@Override
	public CommonResponse deleteProd(List<PlanProduct> products) {
		planProductRepository.delete(products);
		return CommonResponse.of("선택한 상품이 삭제되었습니다.", ResultCode.SUCCESS);
	}
	
	@Override
	public CommonResponse deleteItem(List<PlanGroup> products) {
		planGroupRepository.delete(products);
	//	planProductRepository.delete(products);
		return CommonResponse.of("선택한 상품이 삭제되었습니다.", ResultCode.SUCCESS);
	}

	
	@Override
	public String reorder(Long pid, int oldorder, int neworder) {
		List<Plan> plan =new ArrayList<>();
		// 5 > 6
		if(neworder > oldorder) {
			// pro <= new And old < pro 
			//neworder 보다 작거나 같고 oldorder 보다 큰 순위 - 1 findByProductOrdLessThanEqualAndProductOrdGreaterThan
			List<Plan> planList = planRepository.findBySortLessThanEqualAndSortGreaterThan(neworder, oldorder);
			if(planList != null) {
				for(Plan p:planList) {
					p.setSort(p.getSort()-1);
					plan.add(p);
				}
			}
			
		} else {
			//neworder 보다 크거나 같고 oldorder 보다 작은 순위 + 1  findByProductOrdLessThanProductOrdGreaterThanEqual
			List<Plan> planList = planRepository.findBySortLessThanAndSortGreaterThanEqual(oldorder, neworder);
			if(planList != null) {
				for(Plan p:planList) {
					p.setSort(p.getSort()+1);
					plan.add(p);
				}
			}
		}
		
		
		Plan p = planRepository.findOne(pid);
		p.setSort(neworder);
		plan.add(p);
		
		planRepository.save(plan);
		return "success";
	}
}
