/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.saleon.order.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hanaph.saleon.order.vo.DeliveryVO;

/**
 * 
 * <pre>
 * Class Name : DeliveryDAOImpl.java
 * 설명 :  원장조회 DaoImpl
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015.08.07   김태안           최초작성                
 * </pre>
 * 
 * @version : 
 * @author  : 김태안
 * @since   : 2015. 08. 07.
 */
@Repository("deliveryDAO")
public class DeliveryDAOImpl extends SqlSessionDaoSupport implements DeliveryDAO{

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.DeliveryDAO#getDeliveryGridList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryVO> getDeliveryGridList(Map<String, String> paramMap) {
		return (List<DeliveryVO>)getSqlSession().selectList("delivery.getDeliveryGridList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.DeliveryDAO#getSumDelivery(java.util.Map)
	 */
	@Override
	public DeliveryVO getSumDelivery(Map<String, String> paramMap) {
		return (DeliveryVO)getSqlSession().selectOne("delivery.getSumDelivery", paramMap);
	}

;
}
