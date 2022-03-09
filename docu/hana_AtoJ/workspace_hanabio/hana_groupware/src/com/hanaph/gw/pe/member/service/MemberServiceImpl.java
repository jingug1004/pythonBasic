/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.pe.member.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaph.gw.pe.member.dao.MemberDAO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : MemberServiceImpl.java
 * 설명 : 회원정보 Service 구현한 class
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 16.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 10. 16.
 */
@Service(value="memberService")
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO memberDao; 
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.myPage.service.MemberService#getMemberList(java.util.Map)
	 */
	@Override
	public List<MemberVO> getMemberList(Map<String, String> paramMap) {
		return memberDao.getMemberList(paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.member.service.MemberService#getMemberDetail(java.util.Map)
	 */
	@Override
	public MemberVO getMemberDetail(Map<String, String> paramMap) {
		return memberDao.getMemberDetail(paramMap);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.member.service.MemberService#getMemberPhoto(java.util.Map)
	 */
	@Override
	public MemberVO getMemberPhoto(Map<String, String> paramMap) {
		return memberDao.getMemberPhoto(paramMap);
	}

}
