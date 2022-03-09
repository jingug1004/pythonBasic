package com.beauty.admin.service;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.beauty.entity.DataTables;
import com.beauty.entity.MainFirstBanner;
import com.beauty.entity.MainMenu;
import com.beauty.entity.MainSecondBanner;
import com.beauty.entity.MainTenMenu;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;

public interface AdminMainService {

	public DataTablesResult first(DataTables input, int showType );
	public DataTablesResult second(DataTables input );
	public DataTablesResult menu(DataTables input );
	public DataTablesResult list(DataTables input );
	
	public String getData(int sbType);
	
	public CommonResponse firstDelete(List<MainFirstBanner> datas);
	public CommonResponse secondDelete(List<MainSecondBanner> datas);
	public CommonResponse menuDelete(List<MainTenMenu> datas);
	public CommonResponse listDelete(List<MainMenu> datas);
	
	public CommonResponse firstSave(JSONObject jsonObj);
	public CommonResponse secondSave(JSONObject jsonObj);
	public CommonResponse menuSave(JSONObject jsonObj);
	public CommonResponse listSave(JSONObject jsonObj);
	
	public String reorder(Long sbid, int oldorder, int neworder);
	
	public CommonResponse uploadThumb(MultipartFile image);
}
