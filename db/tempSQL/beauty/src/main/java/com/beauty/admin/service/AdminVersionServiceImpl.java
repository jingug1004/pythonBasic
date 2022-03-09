package com.beauty.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beauty.entity.Version;
import com.beauty.repository.VersionRepository;
import com.beauty.response.CommonResponse;
import com.beauty.response.result.ResultCode;

@Service
public class AdminVersionServiceImpl implements AdminVersionService {
	
	@Autowired
	VersionRepository versionRepository;

	@Override
	public Version findByVersion(String osType) {
		return versionRepository.findByOsType(osType);
	}
	
	@Override
	public CommonResponse versionSave(Version version) {
		versionRepository.save(version);
		return CommonResponse.of("수정 되었습니다.", ResultCode.SUCCESS);
	}
}
