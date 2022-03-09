/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.person.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hanaph.gw.ea.person.service.PersonLineService;

/**
 * <pre>
 * Class Name : PersonLineController.java
 * 설명 : 개인결재라인 Master Controller
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 12. 30.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 12. 30.
 */
@Controller
public class PersonLineController {
	
	@Autowired
	private PersonLineService personLineService;
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 개인결재라인 Master 저장 폼.
	 * 2. 처리내용 : 개인결재라인 Master 저장 폼.
	 * </pre>
	 * @Method Name : getApprovalPopup
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/person/personLineForm.do")
	public ModelAndView personLineForm(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("ea/person/personLineForm");
		
		return mav;
	}
}
