package com.beauty.admin.service;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.beauty.BeautyConstants;
import com.beauty.common.DateUtil;
import com.beauty.common.FtpClientUtil;
import com.beauty.entity.Brand;
import com.beauty.entity.DataTables;
import com.beauty.repository.BrandRepository;
import com.beauty.repository.specification.BrandSpecification;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;
import com.beauty.response.result.ResultCode;

import lombok.Getter;
import lombok.Setter;

@Service
public class AdminBrandServiceImpl implements AdminBrandService {

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
	private BrandRepository brandRepository;


	@Override
	public List<Brand> brandList() {
		return brandRepository.findAll();
	}
	
	@Override
	public DataTablesResult list(DataTables input ) {
		Specifications<Brand> specs = Specifications.where(BrandSpecification.nameLike(input.getSearch().get("value")));
		PageRequest pageRequest = new PageRequest(input.getStart(), input.getLength(), input.getSort("brandName")); //현재페이지, 조회할 페이지수, 정렬정보
		Page<Brand> brand = brandRepository.findAll(specs, pageRequest);
		
		DataTablesResult result = new DataTablesResult();
		result.setDraw(input.getDraw());
		result.setRecordsTotal(brand.getContent().size());
		result.setRecordsFiltered(brand.getTotalElements());
		result.setData(brand.getContent());
		
		return result;
	}

	@Override
	public CommonResponse add(String brandName) {
		if(brandRepository.countByBrandName(brandName) > 0) {
			return CommonResponse.of("브랜드 명이 존재합니다.", ResultCode.FAIL);
		}
		
		Brand brand = new Brand();
		brand.setBrandName(brandName);
		
		brandRepository.save(brand);
		
		return CommonResponse.of("등록 되었습니다.", ResultCode.SUCCESS);
	}

	@Override
	public CommonResponse delete(List<Brand> brands, String flag) {
		for(Brand brand:brands) {
			brand.setBrandVisible(flag);
		}
		brandRepository.save(brands);
		return CommonResponse.of("완료 되었습니다.", ResultCode.SUCCESS);
	}

	@Override
	public CommonResponse update(Brand brand) {
		brandRepository.save(brand);
		
		return CommonResponse.of("수정 되었습니다.", ResultCode.SUCCESS);
	}

	@Override
	public Brand getBrand(Long brandId) {
		return brandRepository.findOne(brandId);
	}

	@Override
	public CommonResponse save(JSONObject jsonObj) {
		Brand brand = null;
		String action = jsonObj.get("action").toString();
		if(action.equals("write")) {
			brand = new Brand();
		} else {
			brand = brandRepository.findOne(Long.parseLong(jsonObj.get("brandId").toString()));
		}

		brand.setBanner(jsonObj.get("thumb_path").toString());
		brand.setBrandName(jsonObj.get("brandName").toString());

		brandRepository.save(brand);
		return CommonResponse.of("저장 되었습니다.", ResultCode.SUCCESS);
	}

	@Override
	public CommonResponse uploadThumb(MultipartFile image) {
		
		String ftpDirectory = BeautyConstants.FTP_BRAND + "/" + DateUtil.getDateString("yyyyMMdd");
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
