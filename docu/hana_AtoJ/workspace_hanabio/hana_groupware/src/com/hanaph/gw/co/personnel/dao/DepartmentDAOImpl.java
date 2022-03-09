/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.co.personnel.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.co.personnel.vo.DepartmentVO;

/**
 * <pre>
 * Class Name : DepartmentDAOImpl.java
 * 설명 : 설명 : 부서관련 정보 가져오는 DAO 구현한 클래스
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 15.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 10. 15.
 */
@Repository("departmentDao")
public class DepartmentDAOImpl extends SqlSessionDaoSupport implements DepartmentDAO {

	/* (non-Javadoc)
	 * @see com.hanaph.gw.personnel.dao.DepartmentDAO#getDepartmentList(java.util.Map)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<DepartmentVO> getDepartmentList(Map<String, String> paramMap) {
		return (List<DepartmentVO>)getSqlSession().selectList("mgrPersonnel.getDepartmentList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.personnel.dao.DepartmentDAO#getDepartment(java.util.Map)
	 */
	@Override
	public DepartmentVO getDepartmentDetail(Map<String, String> paramMap) {
		return (DepartmentVO)getSqlSession().selectOne("mgrPersonnel.getDepartmentDetail", paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.personnel.dao.DepartmentDAO#getSubDepartmentList(java.util.Map)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<DepartmentVO> getDepartmentSubList(Map<String, String> paramMap) {
		return (List<DepartmentVO>)getSqlSession().selectList("mgrPersonnel.getDepartmentSubList", paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.personnel.dao.DepartmentDAO#getDepartmentTerminalList(java.util.Map)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<DepartmentVO> getDepartmentTerminalList(Map<String, String> paramMap) {
		return (List<DepartmentVO>)getSqlSession().selectList("mgrPersonnel.getDepartmentTerminalList", paramMap);
	}
	

}
