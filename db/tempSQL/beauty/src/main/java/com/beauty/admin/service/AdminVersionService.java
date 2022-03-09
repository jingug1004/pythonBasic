package com.beauty.admin.service;

import com.beauty.entity.Version;
import com.beauty.response.CommonResponse;

public interface AdminVersionService {
	/**
	 * ↓↓↓↓↓↓ 버전 ↓↓↓↓↓↓
	 */
	public Version findByVersion(String osType);
	public CommonResponse versionSave(Version version);
}
