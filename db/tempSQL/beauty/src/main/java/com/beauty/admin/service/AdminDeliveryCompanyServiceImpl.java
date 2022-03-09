package com.beauty.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.beauty.entity.DataTables;
import com.beauty.entity.DeliveryCompany;
import com.beauty.repository.DeliveryCompanyRepository;
import com.beauty.repository.specification.DeliveryCompanySpecification;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;
import com.beauty.response.result.ResultCode;

@Service
public class AdminDeliveryCompanyServiceImpl implements AdminDeliveryCompanyService {
	
	@Autowired
	private DeliveryCompanyRepository deliveryCompanyRepository;


	@Override
	public List<DeliveryCompany> companyList() {
		return deliveryCompanyRepository.findAll();
	}
	
	@Override
	public DataTablesResult list(DataTables input ) {
		Specifications<DeliveryCompany> specs = Specifications.where(DeliveryCompanySpecification.like("name",input.getSearch().get("value")));
		PageRequest pageRequest = new PageRequest(input.getStart(), input.getLength(), input.getSort("name")); //현재페이지, 조회할 페이지수, 정렬정보
		Page<DeliveryCompany> brand = deliveryCompanyRepository.findAll(specs, pageRequest);
		
		DataTablesResult result = new DataTablesResult();
		result.setDraw(input.getDraw());
		result.setRecordsTotal(brand.getContent().size());
		result.setRecordsFiltered(brand.getTotalElements());
		result.setData(brand.getContent());
		
		return result;
	}

	@Override
	public CommonResponse add(String name, String code) {
		
		if(deliveryCompanyRepository.findOne(code) != null) {
			return CommonResponse.of("택배사 코드가 존재합니다.", ResultCode.FAIL);
		}
		
		DeliveryCompany deliveryCompany = new DeliveryCompany();
		deliveryCompany.setCode(code);
		deliveryCompany.setName(name);
		deliveryCompanyRepository.save(deliveryCompany);
		
		return CommonResponse.of("등록 되었습니다.", ResultCode.SUCCESS);
	}

	@Override
	public CommonResponse delete(List<DeliveryCompany> deliveryCompanys) {
		deliveryCompanyRepository.delete(deliveryCompanys);
		return CommonResponse.of("삭제 되었습니다.", ResultCode.SUCCESS);
	}

	@Override
	public CommonResponse update(DeliveryCompany deliveryCompany) {
		deliveryCompanyRepository.save(deliveryCompany);
		
		return CommonResponse.of("수정 되었습니다.", ResultCode.SUCCESS);
	}

}
