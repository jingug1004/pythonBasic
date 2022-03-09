package com.beauty.admin.service;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.beauty.entity.DataTables;
import com.beauty.entity.Event;
import com.beauty.entity.ProductTimeSale;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;

public interface AdminEventService {
	
	public List<Event> eventList();
	public DataTablesResult list(DataTables input );
	
	public Event getEvent(Long eid);
	public String getData(int eType);
	
	public CommonResponse eventStat(List<Event> events,  int stat);
	public String uploadImage(MultipartFile image);
	public CommonResponse uploadThumb(MultipartFile image);
	public CommonResponse save(JSONObject jsonObj);
	public CommonResponse update(JSONObject jsonObj);
	
	public String reorder(Long eid, int oldorder, int neworder);
	public CommonResponse delete(List<Event> events);
}
