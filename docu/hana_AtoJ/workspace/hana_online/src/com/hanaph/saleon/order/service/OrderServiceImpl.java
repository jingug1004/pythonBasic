/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.saleon.order.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hanaph.saleon.common.utils.StringUtil;
import com.hanaph.saleon.order.dao.OrderDAO;
import com.hanaph.saleon.order.vo.ItemVO;
import com.hanaph.saleon.order.vo.OrderVO;

/**
 * 
 * <pre>
 * Class Name : OrderServiceImpl.java
 * 설명 : 온라인 주문 ServiceImpl
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 27.      jung a Woo          
 * </pre>
 * 
 * @version : 
 * @author  : jung a Woo(wja@irush.co.kr)
 * @since   : 2014. 10. 27.
 */
@Service(value="orderService")
public class OrderServiceImpl implements OrderService {
	
	/**
	 * OrderDAO
	 */
	@Autowired
	private OrderDAO orderDAO;
	
	@Override
	public List<OrderVO> getBaesongjiGridList(Map<String, String> paramMap) {
		return orderDAO.getBaesongjiGridList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.service.OrderService#getStoreGridList(java.util.Map)
	 */
	@Override
	public List<OrderVO> getStoreGridList(Map<String, String> paramMap) {
		return orderDAO.getStoreGridList(paramMap);
	}

	
	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.service.OrderService#getOrderInit(java.util.Map)
	 */
	@Override
	public OrderVO getOrderInit(Map<String, String> paramMap) {

		/*
		 * returnVO
		 */
		OrderVO returnOrderVO = new OrderVO();
		returnOrderVO.setResult(true);
		
		/*
		 * 여신규정 data get, returnOrderVO에 set
		 */
		OrderVO creditRate = orderDAO.getCreditRate(paramMap);		
		returnOrderVO.setLd_dambo_rate(0);
		returnOrderVO.setLd_credit_amt(0);
		returnOrderVO.setLd_credit_dambo(0);
		
		if(creditRate != null){
			returnOrderVO.setLd_dambo_rate(creditRate.getLd_dambo_rate());		//여신규정 담보율
			returnOrderVO.setLd_credit_amt(creditRate.getLd_credit_amt());		//신용액
			returnOrderVO.setLd_credit_dambo(creditRate.getLd_credit_dambo());	//신용담보	
		}else{
			returnOrderVO.setResult(false);
		}
		
		/*
		 * 담당자, 보증인 data get, returnOrderVO에 set
		 */
		OrderVO guarantor = orderDAO.getGuarantor(paramMap);
		if(guarantor != null){
			returnOrderVO.setLs_sawon_id(guarantor.getLs_sawon_id());		//사원 코드 
			returnOrderVO.setLs_yeondae(guarantor.getLs_yeondae());     	//연대 보증 
			returnOrderVO.setIs_yeondae3(guarantor.getIs_yeondae3());   	//연대 보증3
			returnOrderVO.setPre_deposit(guarantor.getPre_deposit());;   	//선입금처
			returnOrderVO.setSecurityexyn(guarantor.getSecurityexyn());		//담보예외 구분
			
			/*
			 * 사원정보 data(위 담당자, 보증인에서 select row가 있어야지 실행)
			 */
			
			if(!"".equals(   StringUtil.nvl( returnOrderVO.getLs_sawon_id() )    )){
			
				paramMap.put("ls_sawon_id",returnOrderVO.getLs_sawon_id());		//ls_sawon_id parameter set
				OrderVO sawonInfo = orderDAO.getSawonInfo(paramMap);			//사원정보 조회
				
				if(sawonInfo != null ){
					returnOrderVO.setLs_sawon_info(sawonInfo.getLs_sawon_info());	//사원정보
				}
			}
			
			
		}

		
		/*
		 * 거래처의 여신 갯수 data get, returnOrderVO에 set
		 */
		OrderVO creditCount = orderDAO.getCreditCount(paramMap);
		returnOrderVO.setLl_count(0);
		if(creditCount != null){
			returnOrderVO.setLl_count(creditCount.getLl_count());
		}	
		/*
		 * 거래처등록의 여신한도 data get, returnOrderVO에 set
		 */
		/*
		OrderVO creditLimit= orderDAO.getCreditLimit(paramMap);
		returnOrderVO.setLd_credit_limit_amt(0);
		if(creditLimit != null){
			returnOrderVO.setLd_credit_limit_amt(creditLimit.getLd_credit_limit_amt());		//거래처등록의 여신한도 
		}
		*/

		
		/*
		 * 온라인주문상여신/담보정보 data get, returnOrderVO에 set
		 */
		OrderVO creditDamboInfo = orderDAO.getCreditDamboInfo(paramMap);
		returnOrderVO.setLd_tot_credit(0);
		returnOrderVO.setLd_tot_dambo(0);
		returnOrderVO.setLd_credit_limit_amt(0);
		if(creditDamboInfo != null){
			returnOrderVO.setLd_tot_credit(creditDamboInfo.getLd_tot_credit());					// 총 여신액
			returnOrderVO.setLd_tot_dambo(creditDamboInfo.getLd_tot_dambo());       			// 총 담보액
			returnOrderVO.setLd_credit_limit_amt(creditDamboInfo.getLd_credit_limit_amt());		// 여신한도액		
		}

		
		/*
		 * ERP상 주문 총여신 data get, returnOrderVO에 set
		 */
		OrderVO saleTotCredit = orderDAO.getSaleTotCredit(paramMap);
		if(saleTotCredit != null){
			returnOrderVO.setLd_sale_tot_credit(saleTotCredit.getLd_sale_tot_credit());   //ERP상 주문 총여신
		}
		
		/*
		 * 접수상태의 주문금액 data get, returnOrderVO에 set
		 */
		OrderVO jupsuAmt = orderDAO.getJupsuAmt(paramMap);
		if (jupsuAmt != null) {
			returnOrderVO.setJupsu_amt(jupsuAmt.getJupsu_amt());
			
		}
	
		
		/*
		 * 거래처별 신용/담보/연대보증 정보를 이용해서 신용담보/담보 정보를 구한다. 
		 */
		String ls_yeondae = StringUtil.nvl(returnOrderVO.getLs_yeondae(),"");    //연대 보증
		double ld_tot_dambo = returnOrderVO.getLd_tot_dambo();                   //총 담보액
		double ld_dambo_rate = returnOrderVO.getLd_dambo_rate();                 //여신규정 담보율 
		double ld_tot_credit = returnOrderVO.getLd_tot_credit();                 //총 여신액
		double ld_credit_amt = returnOrderVO.getLd_credit_amt();                 //신용액 
		double ld_credit_dambo = returnOrderVO.getLd_credit_dambo();			 //신용담보	
		double ld_jupsu_order_amt = returnOrderVO.getJupsu_amt();			 	 //접수상태의주문금액
		
		double credit_dambo = 0; 												 //신용담보	
		double rem_dambo = 0;													 //주문가능액
		double ld_rem_dambo = 0;												 //담보(계산결과)
		
		/**
		 *	구조상  ld_tot_dambo > 0 경우가 3가지가 있는데 if/elseif 구조상 첫번째 if로만 들어가게 됨. 
		 *	기존 파워빌더와 같은 로직으로 개발했음.
		 *  2015-01-28. 김태안팀장님이랑 윤홍주팀장님이 기존 주문가능액을 구하는 로직 대신 아래처럼 정리함. 
		 *  여신한도액 : 설정 --> 여신한도액 - 총여신
		 *  여신한도액 : 0 --> ERP상 거래처 담보액 * (여신규정 담보율/100) - 총여신
		 *  > 총여신     : 메인화면의 여신현황의 총여신 가져오는 로직과 동일
         *	> 여신한도액 : sale.sale0003 에 credit_limit_amt (여신한도액)
		 */
		/*
		 * 거래처별담보등록에 자료 있는 경우 
		 */

		if(ld_tot_dambo > 0){
			ld_rem_dambo = Math.round(ld_tot_dambo * (ld_dambo_rate/100)) - (ld_tot_credit);
			if( ld_rem_dambo > ld_credit_amt + ld_credit_dambo && ls_yeondae.equals("Y")){
				credit_dambo = ld_rem_dambo;
				//rem_dambo = ld_rem_dambo;
			}else if(ld_rem_dambo > ld_credit_amt && ls_yeondae.equals("N")){
				//담보 (계산결과) > 신용액  && 연대보증이 아니면
				credit_dambo = ld_rem_dambo;
				//rem_dambo = ld_rem_dambo;
			}else if(ls_yeondae.equals("Y")){   
				credit_dambo = ld_credit_amt + ld_credit_dambo;
				//rem_dambo = ld_credit_amt + ld_credit_dambo;
			}else if(ls_yeondae.equals("N")){
				credit_dambo = ld_credit_amt;
				//rem_dambo = ld_credit_amt;
			}
		
		 /*
		  * * 거래처별담보등록에 자료없고 거래처등록에 연대보증 있는경우*/   		
		 
		}else if(ls_yeondae.equals("Y") && ld_tot_dambo == 0){		 
			credit_dambo = ld_credit_amt + ld_credit_dambo - ld_tot_credit;
			//rem_dambo   = ld_credit_amt + ld_credit_dambo - ld_tot_credit;
		}else if(ls_yeondae.equals("Y") && ld_tot_dambo == 0 && ld_tot_credit == 0){		   	
			credit_dambo = ld_credit_amt + ld_credit_dambo;
			//rem_dambo = ld_credit_amt + ld_credit_dambo;
		}else if(ls_yeondae.equals("Y") && ld_tot_dambo > 0 && ld_tot_credit > 0){			
			credit_dambo = ld_credit_amt + ld_credit_dambo - ld_tot_dambo - ld_tot_credit;
			//rem_dambo = ld_credit_amt + ld_credit_dambo - ld_tot_dambo - ld_tot_credit;
			
		}else if(ls_yeondae.equals("N") && ld_tot_dambo == 0){
			credit_dambo = ld_credit_amt - ld_tot_credit;
			//rem_dambo = ld_credit_amt - ld_tot_credit;
			
		}else if(ls_yeondae.equals("N") && ld_tot_dambo == 0 && ld_tot_credit == 0){
			credit_dambo = ld_credit_amt;
			//rem_dambo = ld_credit_amt;
			
		}else if(ls_yeondae.equals("N") && ld_tot_dambo > 0 && ld_tot_credit > 0){
			ld_rem_dambo = Math.round(ld_tot_dambo * (ld_dambo_rate/100)) - ld_tot_credit;
			if(ld_rem_dambo > ld_credit_amt + ld_credit_dambo && ls_yeondae.equals("Y")){
				credit_dambo = ld_rem_dambo;
				//rem_dambo = ld_rem_dambo;

			}else if(ld_rem_dambo > ld_credit_amt && ls_yeondae.equals("N")){
				credit_dambo = ld_rem_dambo;
				//rem_dambo = ld_rem_dambo;
				

			}else if(ls_yeondae.equals("Y")){
				credit_dambo = ld_credit_amt + ld_credit_dambo;
				//rem_dambo = ld_credit_amt + ld_credit_dambo;
				

			}else if(ls_yeondae.equals("N")){
				credit_dambo = ld_credit_amt;
				//rem_dambo = ld_credit_amt;

			}
		}
		
		/*
		 * 여신한도액 : 설정 --> 여신한도액 - 총여신 - 현재접수상태의주문
		 * 여신한도액 : 0 --> ERP상 거래처 담보액 * (여신규정 담보율/100) - 총여신 - 현재접수상태의주문 
		 * */
		if(returnOrderVO.getLd_credit_limit_amt() > 0) {
			rem_dambo = returnOrderVO.getLd_credit_limit_amt() - ld_tot_credit - ld_jupsu_order_amt;
		} else {
			rem_dambo = Math.round(ld_tot_dambo * (ld_dambo_rate/100)) - (ld_tot_credit) - ld_jupsu_order_amt;
		}
		
		returnOrderVO.setCredit_dambo(credit_dambo);		//신용담보금액
		returnOrderVO.setRem_dambo(rem_dambo);				//주문가능액 
		returnOrderVO.setLd_rem_dambo(rem_dambo);			//주문가능액   
		
		/*
		 * 출하중지 체크 data get, returnOrderVO에 set
		 */
		OrderVO budongYn = orderDAO.getBudongYn(paramMap);
		if(budongYn != null){
			returnOrderVO.setLs_budong_yn(budongYn.getLs_budong_yn());
			returnOrderVO.setLs_email(budongYn.getLs_email());
			returnOrderVO.setLs_room_cnt(budongYn.getLs_room_cnt());
		}	
		
		/*
		 * (평균수량=직전3개월평균 * ' + LS_JUMUN_LIMIT + '%)(주문한도=평균수량-해당월) - 주문 등록 화면 상단 파란색 글씨
		 */
		OrderVO jumunLimit = orderDAO.getJumunLimit(paramMap);
		if(jumunLimit != null){
			returnOrderVO.setLs_jumun_limit(jumunLimit.getLs_jumun_limit());
		}
		
		/*
		 * 회전일
		 */
		OrderVO rateDay = orderDAO.getRateDay(paramMap);
		if (rateDay != null) {
			returnOrderVO.setControl_rate_day(rateDay.getControl_rate_day());
			returnOrderVO.setRate_day(rateDay.getRate_day());
		}

		HashMap getCheckMap = orderDAO.getBeforeCheck(paramMap);
		
        returnOrderVO.setBeforeChk(getCheckMap);

        
        OrderVO defaultDeliveryMap = orderDAO.getDefaultDelivery(paramMap);
        
        if(null != defaultDeliveryMap){
            returnOrderVO.setSeq(defaultDeliveryMap.getSeq());
            returnOrderVO.setAddr1(defaultDeliveryMap.getAddr1());
        }
        
		return returnOrderVO;
		
	}


	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.service.OrderService#getOrderUpInit(java.util.Map)
	 */
	@Override
	public OrderVO getOrderUpInit(Map<String, String> paramMap) {

		/*
		 * returnVO
		 */
		OrderVO returnOrderVO = new OrderVO();
		returnOrderVO.setResult(true);
		
		/**
		 * 파워빌더 wf_credit_chk ()
		 */
		
		/*
		 * 여신규정 data get, returnOrderVO에 set
		 */
		OrderVO creditRate = orderDAO.getCreditRate(paramMap);		
		returnOrderVO.setLd_dambo_rate(0);
		returnOrderVO.setLd_credit_amt(0);
		returnOrderVO.setLd_credit_dambo(0);
		
		if(creditRate != null){
			returnOrderVO.setLd_dambo_rate(creditRate.getLd_dambo_rate());          //여신규정 담보율
			returnOrderVO.setLd_credit_amt(creditRate.getLd_credit_amt());          //신용액     
			returnOrderVO.setLd_credit_dambo(creditRate.getLd_credit_dambo());	    //신용담보	  
		}else{
			returnOrderVO.setResult(false);
		}
		
		/*
		 * 담당자, 보증인 data get, returnOrderVO에 set
		 */
		OrderVO guarantor = orderDAO.getGuarantor(paramMap);
		if(guarantor != null){
			returnOrderVO.setLs_sawon_id(guarantor.getLs_sawon_id());					//사원 코드
			returnOrderVO.setLs_yeondae(guarantor.getLs_yeondae());						//연대 보증 
			returnOrderVO.setIs_yeondae3(guarantor.getIs_yeondae3());					//연대 보증 		
			
			/*
			 * 사원정보 data(위 담당자, 보증인에서 select row가 있어야지 실행)
			 */
			
			if(!"".equals(   StringUtil.nvl( returnOrderVO.getLs_sawon_id() )    )){
				
				paramMap.put("ls_sawon_id",returnOrderVO.getLs_sawon_id());		//ls_sawon_id parameter set
				OrderVO sawonInfo = orderDAO.getSawonInfo(paramMap);			//사원정보 조회
				
				if(sawonInfo != null ){
					returnOrderVO.setLs_sawon_info(sawonInfo.getLs_sawon_info());	//사원정보
				}
			}
//			paramMap.put("ls_sawon_id",returnOrderVO.getLs_sawon_id());		//ls_sawon_id parameter set
//			OrderVO sawonInfo = orderDAO.getSawonInfo(paramMap);			//사원정보 조회
//			returnOrderVO.setLs_sawon_info(sawonInfo.getLs_sawon_info());	//사원정보
			
		}

		
		/*
		 * 온라인주문상여신/담보정보 data get, returnOrderVO에 set
		 * pb)	dw_1.Object.tot_dambo[1] = ld_tot_dambo
		 */
		OrderVO creditDamboInfo = orderDAO.getCreditDamboInfo(paramMap);
		returnOrderVO.setLd_tot_credit(0);
		returnOrderVO.setLd_tot_dambo(0);
		if(creditDamboInfo != null){
			returnOrderVO.setLd_tot_credit(creditDamboInfo.getLd_tot_credit());	//총 여신액
			returnOrderVO.setLd_tot_dambo(creditDamboInfo.getLd_tot_dambo());	//총 담보액
			//2018-01-08 주문등록 화면의 주문가능금액 과 소스 동기화 하기 위하여 위치 변경
			returnOrderVO.setLd_credit_limit_amt(creditDamboInfo.getLd_credit_limit_amt());	//거래처등록의 여신한도
		}
		
		/*
		 * 접수상태의 주문금액 data get, returnOrderVO에 set
		 * 2018-01-08 주문 가능금액 조회 추가
		 */
		OrderVO jupsuAmt = orderDAO.getJupsuAmt(paramMap);
		if (jupsuAmt != null) {
			returnOrderVO.setJupsu_amt(jupsuAmt.getJupsu_amt());
			
		}
		
		/*
		 * 거래처별 신용/담보/연대보증 정보를 이용해서 신용담보/담보 정보를 구한다. 
		 */
		String ls_yeondae = StringUtil.nvl(returnOrderVO.getLs_yeondae(),"");	//연대 보증
		double ld_credit_limit_amt = returnOrderVO.getLd_credit_limit_amt();	//거래처등록의 여신한도
		
		double ld_sale_tot_credit = 0;											//ERP상 주문 총여신
		double ld_tot_dambo = returnOrderVO.getLd_tot_dambo();					//총 담보액  
		double ld_rem_dambo = 0;												//담보액(계산결과)
		double ld_dambo_rate = returnOrderVO.getLd_dambo_rate();				//여신규정 담보율
		double ld_tot_credit = returnOrderVO.getLd_tot_credit();				//총 여신액
		double ld_credit_amt = returnOrderVO.getLd_credit_amt();				//신용담보
		double credit_dambo = 0; 												//신용담보
		double rem_dambo = 0;													//주문가능액
		double ld_credit_dambo = returnOrderVO.getLd_credit_dambo();			//거래처등록의 여신한도
		double ld_jupsu_order_amt = returnOrderVO.getJupsu_amt();			 	 //접수상태의주문금액

		
		if(ld_credit_limit_amt > 0){
			/*
			 * ERP상 주문 총여신 data get, returnOrderVO에 set
			 */
			
			OrderVO saleTotCredit = orderDAO.getSaleTotCredit(paramMap);
			if(saleTotCredit != null){
				ld_sale_tot_credit = saleTotCredit.getLd_sale_tot_credit();
				returnOrderVO.setCredit_dambo(ld_credit_limit_amt - ld_sale_tot_credit); //거래처여신한도
				returnOrderVO.setRem_dambo(ld_credit_limit_amt - ld_sale_tot_credit);  //거래처여신한도
				
			}
			if (ld_credit_limit_amt - ld_sale_tot_credit < 0 ){
				returnOrderVO.setResult(false);
			}
		}else{
			
			/*
			 * 거래처별담보등록에 자료 있는 경우 
			 * 2018-01-08 주문 등록 화면의 주문가능금액과 소스 동기화 추가(rem_dambo 주석)

			 */
			if(ld_tot_dambo > 0){  
				ld_rem_dambo = Math.round(ld_tot_dambo / (ld_dambo_rate/100) ) - (ld_tot_credit);
				
				if( ld_rem_dambo > ld_credit_amt + ld_credit_dambo && ls_yeondae.equals("Y")){
					// ( 담보액   > 신용담보 + 거래처등록의 여신한도 ) && 연대보증 이면
					credit_dambo = ld_rem_dambo;
					//rem_dambo = ld_rem_dambo;
				}else if(ld_rem_dambo > ld_credit_amt && ls_yeondae.equals("N")){
					
					// 담보액 > 신용담보 && 연대보증이 아니면
					credit_dambo = ld_rem_dambo;
					//rem_dambo = ld_rem_dambo;
				}else if(ls_yeondae.equals("Y")){
					credit_dambo = ld_credit_amt + ld_credit_dambo;
					//rem_dambo = ld_credit_amt + ld_credit_dambo;
				}else if(ls_yeondae.equals("N")){
					credit_dambo = ld_credit_amt;
					//rem_dambo = ld_credit_amt;
				}
			
			 /*
			  * 거래처별담보등록에 자료없고 거래처등록에 연대보증 있는경우 
			  */
			}else if(ls_yeondae.equals("Y") && ld_tot_dambo == 0){
				credit_dambo = ld_credit_amt + ld_credit_dambo - ld_tot_credit;
				//rem_dambo   = ld_credit_amt + ld_credit_dambo - ld_tot_credit;
			}else if(ls_yeondae.equals("Y") && ld_tot_dambo == 0 && ld_tot_credit == 0){
				credit_dambo = ld_credit_amt + ld_credit_dambo;
				//rem_dambo = ld_credit_amt + ld_credit_dambo;
			}else if(ls_yeondae.equals("Y") && ld_tot_dambo > 0 && ld_tot_credit > 0){
				credit_dambo = ld_credit_amt + ld_credit_dambo - ld_tot_dambo - ld_tot_credit;
				//rem_dambo = ld_credit_amt + ld_credit_dambo - ld_tot_dambo - ld_tot_credit;
			}else if(ls_yeondae.equals("N") && ld_tot_dambo == 0){
				credit_dambo = ld_credit_amt - ld_tot_credit;
				//rem_dambo = ld_credit_amt - ld_tot_credit;
			}else if(ls_yeondae.equals("N") && ld_tot_dambo == 0 && ld_tot_credit == 0){
				credit_dambo = ld_credit_amt;
				//rem_dambo = ld_credit_amt;
			}else if(ls_yeondae.equals("N") && ld_tot_dambo > 0 && ld_tot_credit > 0){
				ld_rem_dambo = Math.round(ld_tot_dambo / (ld_dambo_rate/100)) - ld_tot_credit;
				if(ld_rem_dambo > ld_credit_amt + ld_credit_dambo && ls_yeondae.equals("Y")){
					credit_dambo = ld_rem_dambo;
					//rem_dambo = ld_rem_dambo;
				}else if(ld_rem_dambo > ld_credit_amt && ls_yeondae.equals("N")){
					credit_dambo = ld_rem_dambo;
					//rem_dambo = ld_rem_dambo;
				}else if(ls_yeondae.equals("Y")){
					credit_dambo = ld_credit_amt + ld_credit_dambo;
					//rem_dambo = ld_credit_amt + ld_credit_dambo;
				}else if(ls_yeondae.equals("N")){
					credit_dambo = ld_credit_amt;
					//rem_dambo = ld_credit_amt;
				}
			}

			
		}
		/*
		 * 2018-01-08 주문 등록 화면의 주문가능금액과 소스 동기화
		 * 여신한도액 : 설정 --> 여신한도액 - 총여신 - 현재접수상태의주문
		 * 여신한도액 : 0 --> ERP상 거래처 담보액 * (여신규정 담보율/100) - 총여신 - 현재접수상태의주문 
		 * */
		if(returnOrderVO.getLd_credit_limit_amt() > 0) {
			rem_dambo = returnOrderVO.getLd_credit_limit_amt() - ld_tot_credit - ld_jupsu_order_amt;
			
		} else {
			rem_dambo = Math.round(ld_tot_dambo * (ld_dambo_rate/100)) - (ld_tot_credit) - ld_jupsu_order_amt;
			
			
		}
		
		returnOrderVO.setCredit_dambo(credit_dambo);		//신용담보금액
		returnOrderVO.setRem_dambo(rem_dambo);				//주문가능액
		returnOrderVO.setLd_rem_dambo(ld_rem_dambo);		//주문가능액
		
		//2017-12-18 수정 폼 진입시 로그인 한 업체의 상태를 체크
		
		HashMap getCheckMap = orderDAO.getBeforeCheck(paramMap);
		
        returnOrderVO.setBeforeChk(getCheckMap);

		return returnOrderVO;
		
	}
	
	
	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.service.OrderService#getstoreChk(java.util.Map)
	 */
	@Override
	public OrderVO getstoreChk(Map<String, String> paramMap) {

		/*
		 * returnVO
		 */
		OrderVO returnVO = new OrderVO();
		
		
		/*
		 * 출하중지 체크 data get, returnOrderVO에 set
		 */
		OrderVO budongYn = orderDAO.getRcustBudongYn(paramMap);
		if(budongYn != null){
			returnVO.setLs_budong_yn(budongYn.getLs_budong_yn());
		}

		
		/*
		 * 판매처 확인 data get, returnOrderVO에 set
		 */
		OrderVO storeYn = orderDAO.getStoreYn(paramMap);
		if(storeYn != null){
			returnVO.setLs_cust_nm(storeYn.getLs_cust_nm());
			returnVO.setLs_sawon_id(storeYn.getLs_sawon_id());
			
			/*
			 * 판매처 확인에서 select row가 있어야지 실행.
			 * 판매처 사원정보 data get, returnOrderVO에 set
			 */
			paramMap.put("ls_sawon_id",returnVO.getLs_sawon_id());	
			OrderVO storeSawonInfo = orderDAO.getStoreSawonInfo(paramMap);
			if(storeSawonInfo != null){
				returnVO.setLs_sawon_info(storeSawonInfo.getLs_sawon_info());
			}
		}

		HashMap getCheckMap = orderDAO.getBeforeCheck2(paramMap);

        returnVO.setBeforeChk(getCheckMap);

		
		return returnVO;
	}


	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.service.OrderService#getItemGridList(java.util.Map)
	 */
	@Override
	public List<ItemVO> getItemGridList(Map<String, String> paramMap) {
		
		List<ItemVO> getItemGridList = null;
		
		String excelUpload = (String) paramMap.get("excelUpload");
		if("Y".equals(excelUpload)){	//엑셀주문일경우
			getItemGridList = orderDAO.getExcelUploadItemGridList(paramMap);
		}else{
			getItemGridList = orderDAO.getItemGridList(paramMap);
		}
		
		return getItemGridList;
	}


	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.service.OrderService#getItemGbYn(java.util.Map)
	 */
	@Override
	public ItemVO getItemGbYn(Map<String, String> paramMap) {
		
		/*
		 * returnVO
		 */
		ItemVO returnVO = new ItemVO();
				
		/*
		 * 예전 참고용 - 제품 구분코드 ('010' - 마약류, '020' - 향정신성의약품)
		 * 171230 (토) 01 일반 02 마약 03 의약외품 04 향정신성 09 냉동
		 */
		if(paramMap.get("gs_empCode").startsWith("1")){
			returnVO = orderDAO.getItemYn(paramMap);  // 일단 01 일반 02 마약 03 의약외품 04 향정신성 09 냉동 구분없이 전부 가져옴 From CMITEMM
			if(paramMap.get("rcust_id")!=""){
				String ls_item_gb1 = returnVO.getLs_item_gb1();
				if("010".equals(ls_item_gb1) || "020".equals(ls_item_gb1)){
					paramMap.put("ls_item_gb1", ls_item_gb1);
					returnVO = orderDAO.getItemGb(paramMap);
				}
			}
		}
		
		/*
		 * 주문 가능 수량
		 */
		ItemVO qtyVO = orderDAO.getQtyCnt(paramMap);
		if(qtyVO != null){
			returnVO.setLl_mavg_qty(qtyVO.getLl_mavg_qty());
			returnVO.setLl_mqty(qtyVO.getLl_mqty());
			returnVO.setLl_psb_qty(qtyVO.getLl_psb_qty());
		}else{
			returnVO.setLl_mavg_qty(0);
			returnVO.setLl_mqty(0);
			returnVO.setLl_psb_qty(0);
		}
		
		return returnVO;
	}


	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.service.OrderService#getItemChkReg(java.util.Map)
	 */
	@Override
	public ItemVO getItemChkReg(Map<String, String> paramMap) {
		/*
		 * returnVO
		 */
		ItemVO returnVO = new ItemVO();
		
		/*
		 * 제품 체크
		 */
		ItemVO itemChk = new ItemVO();
		itemChk = orderDAO.getItemChk(paramMap);
		if(itemChk != null){
			returnVO.setLs_chul_yn(itemChk.getLs_chul_yn());
			returnVO.setLs_use_yn(itemChk.getLs_use_yn());
		}
		
		/*
		 * 창고재고
		 */
		ItemVO jaego = new ItemVO();
		jaego = orderDAO.getJaego(paramMap);
		if(jaego != null){
			returnVO.setLd_jaego_qty(jaego.getLd_jaego_qty());
		}else{
			returnVO.setLd_jaego_qty("0");
		}
		
		
		/*
		 * 창고재고 : 제조번호별 재고의 합계
		 */
		ItemVO invjaego = new ItemVO();
		invjaego = orderDAO.getInvjaego(paramMap);
		if(invjaego != null){
			returnVO.setLd_invjaego_qty(invjaego.getLd_invjaego_qty());
		}else{
			returnVO.setLd_invjaego_qty("0");
		}
		
		/*
		 * 개시일자부터 3개월간은 주문수량 통과
		 */
		ItemVO liCnt = new ItemVO();
		liCnt = orderDAO.getLiCnt(paramMap);
		if(liCnt != null){
			returnVO.setLi_cnt(liCnt.getLi_cnt());
		}

		
		ItemVO itemChk2 = new ItemVO();
		itemChk2 = orderDAO.getItemChk2(paramMap);
		if(itemChk2 != null){
			
			returnVO.setPieceyn(itemChk2.getPieceyn());
			returnVO.setProductdiv(itemChk2.getProductdiv());
		}

		
		return returnVO;
	}


	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.service.OrderService#insertOrder(java.util.Map)
	 */
	@Transactional
	@Override
	public boolean insertOrder(Map<String, Object> paramMap) {

		String ldt_req_date = (String) paramMap.get("ldt_req_date");		
		String[] item_id = (String[]) paramMap.get("item_id");
		String[] qty = (String[])paramMap.get("qty");
		String[] bal_amt = (String[])paramMap.get("bal_amt");
		String[] supply_net = (String[])paramMap.get("supply_net");
		String[] supply_vat = (String[])paramMap.get("supply_vat");
		String[] percent = (String[])paramMap.get("percent");
		String[] dc_amt = (String[])paramMap.get("dc_amt");
		String[] dc_danga = (String[])paramMap.get("dc_danga");
		String[] saupjang_cd = (String[])paramMap.get("saupjang_cd");
		String[] threeavgck = (String[])paramMap.get("threeavgck");
		
		
		String ls_sawon_id = (String)paramMap.get("ls_sawon_id");
		String gs_empCode = (String)paramMap.get("gs_empCode");
		String ls_rsawon_id = (String)paramMap.get("ls_rsawon_id");
		String ls_rcust_cd = (String)paramMap.get("ls_rcust_cd");
		String ls_bigo = (String)paramMap.get("ls_bigo");
		String ls_limit_yn = (String)paramMap.get("ls_limit_yn");
		String ls_pro_date = (String)paramMap.get("ls_pro_date");
		String ls_pro_bigo = (String)paramMap.get("ls_pro_bigo");
		String addrseq = (String)paramMap.get("addrseq");
        
		String gumae_no102 = "";
		String gumae_no103 = "";
		double ld_supply_net_sum102 = 0;
		double ld_supply_net_sum103 = 0;
		double ld_supply_vat_sum102 = 0;
		double ld_supply_vat_sum103 = 0;
		
		
		String gumae_no102_threeavgck = "N";
		String gumae_no103_threeavgck = "N";
		
		/*
		 * 회계사업장코드(saupjangcd)에 따라 주문 master를 만든다.(102-도매지점)
		 */
		for(int i = 0; saupjang_cd.length > i; i++){
			if(saupjang_cd[i].equals("2002")){
				if("".equals(gumae_no102)){
					/*
					 * 접수번호 생성
					 */
					gumae_no102 = orderDAO.getProcedureCall(paramMap);
				}
				
				ItemVO detailItemVO = new ItemVO();
				
				
				detailItemVO.setLdt_req_date(ldt_req_date);
				detailItemVO.setLs_gumae_no(gumae_no102);
				ld_supply_net_sum102 += Integer.parseInt(supply_net[i]);
				ld_supply_vat_sum102 += Integer.parseInt(supply_vat[i]);
				detailItemVO.setLs_input_seq(String.format("%04d", (i+1)));
				detailItemVO.setItem_id(item_id[i]);
				detailItemVO.setQty(Integer.parseInt(qty[i])+"");	//필수값이라서 유효성 검증을 인해 int형으로 변환해보고 다시 문자열로 담는다.
				detailItemVO.setBal_amt(Integer.parseInt(bal_amt[i]));
				detailItemVO.setSupply_net(Integer.parseInt(supply_net[i]));
				detailItemVO.setSupply_vat(Integer.parseInt(supply_vat[i]));
				detailItemVO.setPercent(Integer.parseInt(percent[i]));
				detailItemVO.setDc_amt(dc_amt[i]);
				detailItemVO.setDc_danga(Integer.parseInt(dc_danga[i]));
				detailItemVO.setSaupjang_cd(saupjang_cd[i]);
				detailItemVO.setLs_rcust_cd(ls_rcust_cd);
				detailItemVO.setGs_empCode(gs_empCode);
				if("*".equals(threeavgck[i])){
					gumae_no102_threeavgck = "*"; 	
				};
				detailItemVO.setThreeavgck(threeavgck[i]);
				
				/*2017-14 oragmp에 맞게 수정*/
				detailItemVO.setLs_rcust_cd(ls_rcust_cd);	//실거래처 코드
				detailItemVO.setLs_sawon_id(ls_sawon_id);	//사원코드(거래처 코드)
				
				/*
				 * 주문 detail insert, 재고 변경 update
				 */
				orderDAO.insertDetailOrder(detailItemVO);	
				
				/* 2017-12-14 신 ERP 에서는 해당 사항 없음.*/
				//orderDAO.updateChulgoQty(detailItemVO);

			}
		}
		
		if(!"".equals(gumae_no102)){
			
			/*
			 * 주문 master insert
			 */
			ItemVO masterItem102VO = new ItemVO();
			masterItem102VO.setLs_gumae_no(gumae_no102);
			masterItem102VO.setLdt_req_date(ldt_req_date);
			masterItem102VO.setLs_sawon_id(ls_sawon_id);
			masterItem102VO.setGs_empCode(gs_empCode);
			masterItem102VO.setLs_rsawon_id(ls_rsawon_id);
			masterItem102VO.setLs_rcust_cd(ls_rcust_cd);
			masterItem102VO.setLd_supply_net_sum(ld_supply_net_sum102);
			masterItem102VO.setLd_supply_vat_sum(ld_supply_vat_sum102);
			masterItem102VO.setLs_bigo(ls_bigo);
			masterItem102VO.setLs_limit_yn(ls_limit_yn);
			masterItem102VO.setLs_pro_date(ls_pro_date);
			masterItem102VO.setLs_pro_bigo(ls_pro_bigo);
			masterItem102VO.setAddrseq(addrseq);
			masterItem102VO.setThreeavgck(gumae_no102_threeavgck);

			
			/*
			 * 주문 master insert
			 */
			orderDAO.insertMasterOrder(masterItem102VO);
			
			//결재정보 insert
			
			orderDAO.insertAccountInfo(masterItem102VO);
			orderDAO.insertAccountInfo2(masterItem102VO);
		}

		

		
		
		
		
		
		
		
		
		
		
		
		for(int i = 0; saupjang_cd.length > i; i++){
			if(!saupjang_cd[i].equals("2002")){
				if("".equals(gumae_no103)){
					/*
					 * 접수번호 생성
					 */
					gumae_no103 = orderDAO.getProcedureCall(paramMap);
				}
				
				ItemVO detailItemVO = new ItemVO();
				
				
				detailItemVO.setLdt_req_date(ldt_req_date);
				detailItemVO.setLs_gumae_no(gumae_no103);
				ld_supply_net_sum102 += Integer.parseInt(supply_net[i]);
				ld_supply_vat_sum102 += Integer.parseInt(supply_vat[i]);
				detailItemVO.setLs_input_seq(String.format("%04d", (i+1)));
				detailItemVO.setItem_id(item_id[i]);
				detailItemVO.setQty(Integer.parseInt(qty[i])+"");	//필수값이라서 유효성 검증을 인해 int형으로 변환해보고 다시 문자열로 담는다.
				detailItemVO.setBal_amt(Integer.parseInt(bal_amt[i]));
				detailItemVO.setSupply_net(Integer.parseInt(supply_net[i]));
				detailItemVO.setSupply_vat(Integer.parseInt(supply_vat[i]));
				detailItemVO.setPercent(Integer.parseInt(percent[i]));
				detailItemVO.setDc_amt(dc_amt[i]);
				detailItemVO.setDc_danga(Integer.parseInt(dc_danga[i]));
				detailItemVO.setSaupjang_cd(saupjang_cd[i]);
				detailItemVO.setLs_rcust_cd(ls_rcust_cd);
				detailItemVO.setGs_empCode(gs_empCode);
				
				if("*".equals(threeavgck[i])){
					gumae_no103_threeavgck = "*"; 	
				};
				detailItemVO.setThreeavgck(threeavgck[i]);

				
				/*2017-14 oragmp에 맞게 수정*/
				detailItemVO.setLs_rcust_cd(ls_rcust_cd);	//실거래처 코드
				detailItemVO.setLs_sawon_id(ls_sawon_id);	//사원코드(거래처 코드)
				
				/*
				 * 주문 detail insert, 재고 변경 update
				 */
				orderDAO.insertDetailOrder(detailItemVO);	
				
				/* 2017-12-14 신 ERP 에서는 해당 사항 없음.*/
				//orderDAO.updateChulgoQty(detailItemVO);

			}
		}
		
		if(!"".equals(gumae_no103)){
			
			/*
			 * 주문 master insert
			 */
			ItemVO masterItem102VO = new ItemVO();
			masterItem102VO.setLs_gumae_no(gumae_no103);
			masterItem102VO.setLdt_req_date(ldt_req_date);
			masterItem102VO.setLs_sawon_id(ls_sawon_id);
			masterItem102VO.setGs_empCode(gs_empCode);
			masterItem102VO.setLs_rsawon_id(ls_rsawon_id);
			masterItem102VO.setLs_rcust_cd(ls_rcust_cd);
			masterItem102VO.setLd_supply_net_sum(ld_supply_net_sum102);
			masterItem102VO.setLd_supply_vat_sum(ld_supply_vat_sum102);
			masterItem102VO.setLs_bigo(ls_bigo);
			masterItem102VO.setLs_limit_yn(ls_limit_yn);
			masterItem102VO.setLs_pro_date(ls_pro_date);
			masterItem102VO.setLs_pro_bigo(ls_pro_bigo);
			masterItem102VO.setAddrseq(addrseq);
			masterItem102VO.setThreeavgck(gumae_no103_threeavgck);
			
			/*
			 * 주문 master insert
			 */
			orderDAO.insertMasterOrder(masterItem102VO);

			//결재정보
			
			orderDAO.insertAccountInfo(masterItem102VO);
			orderDAO.insertAccountInfo2(masterItem102VO);
		}

/*		
		
		 * 회계사업장코드(saupjangcd)에 따라 주문 master를 만든다.(103-향정신성의약품)
		 
		for(int i = 0; saupjang_cd.length > i; i++){
			if(!saupjang_cd[i].equals("2002")){
				if(serial103==0){
					
					 * 접수번호 생성
					 
					serial103 = orderDAO.getProcedureCall(paramMap);
					gumae_no103 = ldt_req_date+String.format("%04d", serial103);
				}
				
				ItemVO detailItemVO = new ItemVO();
				
				detailItemVO.setLdt_req_date(ldt_req_date);
				detailItemVO.setLs_gumae_no(gumae_no103);
				ld_supply_net_sum103 += Integer.parseInt(supply_net[i]);
				ld_supply_vat_sum103 += Integer.parseInt(supply_vat[i]);
				detailItemVO.setLs_input_seq(String.format("%04d", (i+1)));
				detailItemVO.setItem_id(item_id[i]);
				detailItemVO.setQty(Integer.parseInt(qty[i])+"");	//필수값이라서 유효성 검증을 인해 int형으로 변환해보고 다시 문자열로 담는다.
				detailItemVO.setBal_amt(Integer.parseInt(bal_amt[i]));
				detailItemVO.setSupply_net(Integer.parseInt(supply_net[i]));
				detailItemVO.setSupply_vat(Integer.parseInt(supply_vat[i]));
				detailItemVO.setPercent(Integer.parseInt(percent[i]));
				detailItemVO.setDc_amt(dc_amt[i]);
				detailItemVO.setDc_danga(Integer.parseInt(dc_danga[i]));
				detailItemVO.setSaupjang_cd(saupjang_cd[i]);
				
				
				 * 주문 detail insert, 재고 변경 update
				 
				orderDAO.insertDetailOrder(detailItemVO);	
				
				
				 2017-12-14 신 ERP 에서는 해당 사항 없음.
				//orderDAO.updateChulgoQty(detailItemVO);

			}
		}
		
		if(serial103 != 0){
			
			
			 * 주문 master insert
			 
			ItemVO masterItem103VO = new ItemVO();
			masterItem103VO.setLs_gumae_no(gumae_no103);
			masterItem103VO.setLdt_req_date(ldt_req_date);
			masterItem103VO.setLs_sawon_id(ls_sawon_id);
			masterItem103VO.setGs_empCode(gs_empCode);
			masterItem103VO.setLs_rsawon_id(ls_rsawon_id);
			masterItem103VO .setLs_rcust_cd(ls_rcust_cd);
			masterItem103VO.setLd_supply_net_sum(ld_supply_net_sum103);
			masterItem103VO.setLd_supply_vat_sum(ld_supply_vat_sum103);
			masterItem103VO.setLs_bigo(ls_bigo);
			masterItem103VO.setLs_limit_yn(ls_limit_yn);
			masterItem103VO.setLs_pro_date(ls_pro_date);
			masterItem103VO.setLs_pro_bigo(ls_pro_bigo);
			masterItem103VO.setAddrseq(addrseq);
			
			
			 * 주문 master insert
			 
			orderDAO.insertMasterOrder(masterItem103VO);
		}*/
		return true;
	}


	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.service.OrderService#getMasterGridList(java.util.Map)
	 */
	@Override
	public List<ItemVO> getMasterGridList(Map<String, String> paramMap) {
		return orderDAO.getMasterGridList(paramMap);
	}


	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.service.OrderService#getDetailGridList(java.util.Map)
	 */
	@Override
	public List<ItemVO> getDetailGridList(Map<String, String> paramMap) {
		return orderDAO.getDetailGridList(paramMap);
	}


	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.service.OrderService#getBasDanga(java.util.Map)
	 */
	@Override
	public ItemVO getBasDanga(Map<String, String> paramMap) {
		
		ItemVO returnVO = new ItemVO();					//returnVO
		
		returnVO = orderDAO.getBasDanga(paramMap);		//제품별 bas_amt값을 가져온다
		if(returnVO == null){
			returnVO = new ItemVO();
		}
		return returnVO;
	}


	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.service.OrderService#getItemChkEdit(java.util.Map)
	 */
	@Override
	public ItemVO getItemChkEdit(Map<String, String> paramMap) {
		
		ItemVO returnVO = new ItemVO();	//returnVO
		
		/*
		 * 제품 체크
		 */
		ItemVO itemChk = new ItemVO();
		itemChk = orderDAO.getItemChk(paramMap);
		if(itemChk != null){
			returnVO.setLs_chul_yn(itemChk.getLs_chul_yn());
			returnVO.setLs_use_yn(itemChk.getLs_use_yn());
		}
		

		/*
		 * 창고재고
		 */
		ItemVO jaego = new ItemVO();
		jaego = orderDAO.getJaego(paramMap);
		if(jaego != null){
			returnVO.setLd_jaego_qty(jaego.getLd_jaego_qty());
		}else{
			returnVO.setLd_jaego_qty("0");
		}
		
		/*
		 * 창고재고 : 제조번호별 재고의 합계
		 */
		ItemVO invjaego = new ItemVO();
		invjaego = orderDAO.getInvjaego(paramMap);
		if(invjaego != null){
			returnVO.setLd_invjaego_qty(invjaego.getLd_invjaego_qty());
		}else{
			returnVO.setLd_invjaego_qty("0");
		}
		
		ItemVO itemChk2 = new ItemVO();
		paramMap.put("ls_item_id", paramMap.get("ls_item_cd"));
		itemChk2 = orderDAO.getItemChk2(paramMap);
		if(itemChk2 != null){
			
			returnVO.setPieceyn(itemChk2.getPieceyn());
			returnVO.setProductdiv(itemChk2.getProductdiv());
		}


		
		return returnVO;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.service.OrderService#getReceiptChk(java.util.Map)
	 */
	public ItemVO getReceiptChk(Map<String, String> paramMap) {
		ItemVO returnVO = new ItemVO();	//returnVO
		
		/*
		 * 주문승인 체크
		 */
		ItemVO approvalChk = new ItemVO();
		approvalChk = orderDAO.getReceiptChk(paramMap);
		if(approvalChk != null){
			returnVO.setLs_receipt_gb(approvalChk.getLs_receipt_gb());
		}
		return returnVO;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.service.OrderService#updateOrder(java.util.Map)
	 */
	@Transactional
	@Override
	public boolean updateOrder(Map<String, Object> paramMap) {
		
		String ldt_req_date = (String) paramMap.get("ldt_req_date");		//주문요청일
		String ls_gumae_no = (String) paramMap.get("ls_gumae_no");			//구매번호
		String ls_bigo = (String) paramMap.get("ls_bigo");					//비고
		String ld_amt_sum = (String) paramMap.get("ld_amt_sum");			//총금액
		String ld_vat_sum = (String) paramMap.get("ld_vat_sum");			//총부가세
		String ls_limit_yn = (String) paramMap.get("ls_limit_yn");			//제한여부
		
		String[] ld_item_id = (String[]) paramMap.get("ld_item_id");		//제품 코드
		String[] input_seq = (String[]) paramMap.get("input_seq");			//입력순서
		String[] qty = (String[]) paramMap.get("qty");						//수량
		String[] amend_qty = (String[]) paramMap.get("amend_qty");			//수량
		String[] amend_amt = (String[]) paramMap.get("amend_amt");			//금액
		String[] amend_vat = (String[]) paramMap.get("amend_vat");			//부가세
		String[] dc_amt = (String[]) paramMap.get("dc_amt");				//할인 금액
		String[] danga = (String[]) paramMap.get("danga");				//단가
        
		/*
		 * 등록 제품이 있을 경우 등록 제품 갯수만큼 루프를 돌면서 
		 * 주문 detail update, 재고 변경 update
		 */
		if(ld_item_id != null){
			for(int i = 0; ld_item_id.length > i; i++){
				
				ItemVO detailItemVO = new ItemVO();
			
				detailItemVO.setLdt_req_date(ldt_req_date);
				detailItemVO.setLs_gumae_no(ls_gumae_no);
				detailItemVO.setInput_seq(input_seq[i]);
				detailItemVO.setItem_id(ld_item_id[i]);
				detailItemVO.setQty(qty[i]);
				detailItemVO.setAmend_qty(amend_qty[i]);
				detailItemVO.setAmend_amt(Integer.parseInt(amend_amt[i]));
				detailItemVO.setAmend_vat(Integer.parseInt(amend_vat[i]));
				detailItemVO.setDc_amt(dc_amt[i]);
				detailItemVO.setDanga(  Double.parseDouble(danga[i]) );
				
				orderDAO.updateDetailOrder(detailItemVO);	//주문 detail update,
				
				//신 ERP 에서는 관리 안함
				//detailItemVO.setQty(""+(Integer.parseInt(amend_qty[i]) - Integer.parseInt(qty[i])));	//변경수량 - 기존수량만큼 변경
				//orderDAO.updateChulgoQty(detailItemVO);		//재고 변경 update
			}
			
		}
		
		/*
		 * 주문 master update
		 */
		ItemVO masterItemVO = new ItemVO();
		masterItemVO.setLs_gumae_no(ls_gumae_no);
		masterItemVO.setLs_bigo(ls_bigo);
		masterItemVO.setLd_amt_sum(ld_amt_sum);
		masterItemVO.setLd_vat_sum(ld_vat_sum);
		masterItemVO.setLs_limit_yn(ls_limit_yn);
		
		/*
		 * 주문 master update 해당 없음(신규 ERP에 합계 마스터 없음)
		 */
		orderDAO.updateMasterOrder(masterItemVO);
		
		return true;
	}

	
	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.service.OrderService#deleteOrder(java.util.Map)
	 */
	@Transactional
	@Override
	public boolean deleteOrder(Map<String, Object> paramMap) {
		
		String ldt_req_date = (String) paramMap.get("ldt_req_date");		//주문요청일
		String ls_gumae_no = (String) paramMap.get("ls_gumae_no");			//구매번호
		String masterDelYn = (String) paramMap.get("masterDelYn");			//마스터 테이블 데이터 삭제 여부
		
		String[] ld_item_id = (String[]) paramMap.get("ld_item_id");		//제품코드
		String[] input_seq = (String[]) paramMap.get("input_seq");			//입력순서
		String[] qty = (String[]) paramMap.get("qty");						//수량
		
		/*
		 * 등록 제품이 있을 경우 등록 제품 갯수만큼 루프를 돌면서 
		 * 주문 detail delete, 재고 변경 update
		 */
		if(ld_item_id != null){
			
			for(int i = 0; ld_item_id.length > i; i++){
				
				ItemVO detailItemVO = new ItemVO();
			
				detailItemVO.setLdt_req_date(ldt_req_date);
				detailItemVO.setLs_gumae_no(ls_gumae_no);
				detailItemVO.setInput_seq(input_seq[i]);
				detailItemVO.setItem_id(ld_item_id[i]);
				detailItemVO.setQty("-"+qty[i]);
		
				/*
				 * 주문 detail delete, 재고 변경 update
				 */
				orderDAO.deleteDetailOrder(detailItemVO);	
				//orderDAO.updateChulgoQty(detailItemVO);
			}
			
		}
		
		/*
		 * 주문 master delete or 삭제 후 변경된 금액 update
		 */
		ItemVO masterItemVO = new ItemVO();
		masterItemVO.setLs_gumae_no(ls_gumae_no);
		
		if(masterDelYn.equals("Y")){
			orderDAO.deleteMasterOrder(masterItemVO);		//주문 master delete		
		}

		// 해당 없음(신규 ERP에 합계 마스터 없음), 비지니스 로직 변경 필요
		/*
		else {
			orderDAO.updateMasterAmt(masterItemVO);		//주문 master 변경된 금액 update
		}
		*/
		return true;
	}


	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.service.OrderService#getOrderStatusGridList(java.util.Map)
	 */
	@Override
	public List<ItemVO> getOrderStatusGridList(Map<String, Object> paramMap) {
		return orderDAO.getOrderStatusGridList(paramMap);
	}

	@Override
	public boolean insertOrderExcelUpload(MultipartFile multipartFile, Map<String, String> paramMap) {

		try{
			//1. excel 생성
			HSSFWorkbook wb = new HSSFWorkbook(multipartFile.getInputStream());
			HSSFSheet sheet = wb.getSheetAt(0);
			
			//2. 사이즈 구해오기
			int last = sheet.getLastRowNum();
			
			DataFormatter formatter = new DataFormatter();
			
	        String custcode = (String) paramMap.get("custcode");	//거래처코드
	        
	        ItemVO excelItemVO = new ItemVO();
	        excelItemVO.setCust_id(custcode);
			
	        /*
			 * 엑셀업로드 주문 delete
			 */
	        orderDAO.deleteOrderExcelUpload(excelItemVO);
	        
	        List<ItemVO> list = new ArrayList<ItemVO>();
	        
			for(int i=1 ; i<=last ; i++){	//2번째줄부터 ( 첫째줄은 타이틀 )
				HSSFRow row = sheet.getRow(i);
	            String cell1 = formatter.formatCellValue(row.getCell(0));	//판매처코드
	            String cell2 = formatter.formatCellValue(row.getCell(1));	//배송지코드
	            String cell3 = formatter.formatCellValue(row.getCell(2));	//식약처표준코드
	            String cell4 = formatter.formatCellValue(row.getCell(3));	//구매수량
	            
	            if(StringUtils.isNotEmpty(cell1) && StringUtils.isNotEmpty(cell2) && StringUtils.isNumeric(cell2)
	            		&& StringUtils.isNotEmpty(cell3) && StringUtils.isNotEmpty(cell4) && StringUtils.isNumeric(cell4)){
	            	excelItemVO = new ItemVO();
	            	excelItemVO.setCust_id(custcode);
	    	        excelItemVO.setRcust_id(cell1);
	    	        excelItemVO.setAddrseq(cell2);
	    	        excelItemVO.setItem_id(cell3);
	    	        excelItemVO.setQty(cell4);
	    	        
	    	        //리스트에 vo 담는다
	    	        list.add(excelItemVO);
	            }
			}
			
			//엑셀 내용 등록
			orderDAO.insertOrderExcelUpload(list);
			
		}catch(Exception e){
			//e.printStackTrace();
			return false;
		}

		return true;
	}
	
	@Override
	/**
	 * 1. [' || v_itemname || '] 제품은 사용중지 품목입니다.
	 * 2. [' || v_itemname || '] 제품은 출하중지 품목입니다.
	 * 3. 퇴장방지품목 [' || v_itemname || '] 의 할인율은 9%를 넘을 수 없습니다.
	 * 4. [' || v_itemname || '] 제품은 ' || v_ordqtybsunit || '의 배수단위로만 주문가능합니다.
	 * 5. 비급여품목 [' || v_itemname || '] 에 대한 계약단가 내역이 존재하지않습니다.
	 * 6. 해당 거래처는 경구제 품목 [' || v_itemname || '] 에 대한 등록이 제한됩니다.' ;
	 * 7-1. 해당 도매업체는 마약제품을 등록할 수 없습니다.
	 * 7-2. 직거래처에는 마약제품을 등록할 수 없습니다.
	 * 7-3. 온라인주문과 SFA에서는 마약제품을 등록할 수 없습니다.
	 * 8. 해당 도매업체는 향정제품을 등록할 수 없습니다.
	 * 9. 해당 거래처에 [' || v_itemname || '] 제품에 대한 계약단가 내역이 존재하지 않습니다.
	 * 10. [' || v_itemname || '] 제품의 재고가 부족합니다.

	 * 20-1. [' || v_itemname || '] 의 이달 주문합계수량('|| v_thatmonth || ')이 10개를 초과하였습니다
	 * 20-2. [' || v_itemname || '] 의 ' || v_month || '개월 평균주문수량의 '|| v_threeavglmt || '배인 ' || v_threeavgcnt ||' 개를 초과하였습니다.  
	 * 20-3. [' || v_itemname || ']의 이달 주문합계금액('|| v_tahtmonthamt || ')이 '|| v_thatmonth ||' 개월평균주문금액의 ' || v_threeavglmt || '배인 '|| v_amtlmt ||' 원을 초과하였습니다.
	 * 20-4. [' || v_itemname || '] 의 ' || v_month || '개월 평균주문수량의 '|| v_threeavglmt || '배인 ' || v_threeavgcnt ||' 개를 초과하였습니다.  
	 * 20-5. [' || v_itemname || '] 의 이달 주문합계수량('|| v_thatmonth || ')이 3개월평균주문수량의' || v_threeavglmt || '배인 ' || v_threeavgcnt ||' 개를 초과하였습니다.    
	 * 20-6. [' || v_itemname || '] 의 이달 주문합계금액('|| v_tahtmonthamt || ')이 3개월평균주문금액의' || v_threeavglmt || '인 '|| v_amtlmt ||' 원을 초과하였습니다(개시3개월이상)
	 */
	public HashMap<String, String> getItemChkFnc(Map<String, String> paramMap){
		HashMap<String,String> rtnMto = new HashMap<String,String>();
		if(null == paramMap.get("orderno")){
			paramMap.put("orderno", "");
		}
		ArrayList ary = orderDAO.getItemChkFnc(paramMap);
		try{
			if(ary.size() != 0 && ary != null){
				for(int i = 0 ; i < ary.size() ; i++){
					String msg = StringUtil.nvl(ary.get(i).toString());
					if(msg.indexOf(".") > -1){
						String key = msg.substring(0,msg.indexOf("."));
						String value = msg.substring(msg.indexOf(".")+1);
						rtnMto.put(key, value);
					}
				}
			}
			
		}catch(Exception e){
			
		}
		return rtnMto;
	}

	@Override
	public List<ItemVO> getExcelStoreGridList(Map<String, String> paramMap) {
		return orderDAO.getExcelStoreGridList(paramMap);
	}

	@Override
	public List<ItemVO> getExcelNoStoreList(Map<String, String> paramMap) {
		return orderDAO.getExcelNoStoreList(paramMap);
	}

	@Override
	public List<ItemVO> getExcelNoItemList(Map<String, String> paramMap) {
		return orderDAO.getExcelNoItemList(paramMap);
	}
	
}
