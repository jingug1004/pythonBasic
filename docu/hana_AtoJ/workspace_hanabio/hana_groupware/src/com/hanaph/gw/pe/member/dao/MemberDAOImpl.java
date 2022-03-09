/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.pe.member.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : MemberDAOImpl.java
 * 설명 : 회원정보 DAO 구현한 class
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
@Repository("memberDao")
public class MemberDAOImpl extends SqlSessionDaoSupport implements MemberDAO {

	/* (non-Javadoc)
	 * @see com.hanaph.gw.myPage.dao.MemberDAO#getDepartmentList(java.util.Map)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<MemberVO> getMemberList(Map<String, String> paramMap) {
		return (List<MemberVO>)getSqlSession().selectList("member.getMemberList", paramMap);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.member.dao.MemberDAO#getMemberDetail(java.util.Map)
	 */
	@Override
	public MemberVO getMemberDetail(Map<String, String> paramMap) {
		return (MemberVO)getSqlSession().selectOne("member.getMemberDetail", paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.member.dao.MemberDAO#getMemberPhoto(java.util.Map)
	 */
	@Override
	public MemberVO getMemberPhoto(Map<String, String> paramMap) {
		return (MemberVO)getSqlSession().selectOne("member.getMemberPhoto", paramMap);
	}
}
