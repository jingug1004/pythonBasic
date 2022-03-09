package com.beauty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beauty.entity.Event;
import com.beauty.service.EventService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value="/api/event")
public class EventController {
	public final String TAG = "이벤트";
	
	@Autowired
	private EventService eventService;
	
	@ApiOperation(value = "이벤트 상세", notes = "이벤트 상세", response = Event.class, responseContainer="List", tags={TAG, })
    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody Event getEvent(@ApiParam(name="eid", value="이벤트ID", required = true) @RequestParam Long eid) {
        return eventService.getEvent(eid);
    }
	
    /**
     * 이벤트 목록
     * @return
     */
    @ApiOperation(value = "이벤트 목록", notes = "이벤트 목록", response = Event.class, responseContainer="List", tags={TAG, })
    @RequestMapping(value="/{page}", method=RequestMethod.GET)
    public @ResponseBody Page<Event> getEventList(@ApiParam(name="page", value="조회할 페이지  0부터 시작", required = true) @PathVariable int page) {
        return eventService.getEventList(page);
    }
    
    
}
