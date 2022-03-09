/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.saleon.order.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanaph.saleon.common.utils.StringUtil;
import com.hanaph.saleon.order.vo.ItemVO;
import com.hanaph.saleon.order.vo.OrderVO;

/**
 * 
 * <pre>
 * Class Name : OrderDAOImpl.java
 * 설명 : 온라인 발주 주문 DaoImpl
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
@Repository("orderDAO")
public class OrderDAOImpl extends SqlSessionDaoSupport implements OrderDAO{
	
	@Autowired	
	SqlSessionFactory sqlSessionFactory;
	
	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getStoreGridList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVO> getBaesongjiGridList(Map<String, String> paramMap) {
		return (List<OrderVO>)getSqlSession().selectList("order.getBaesongjiGridList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getJupsuAmt(java.util.Map)
	 */
	@Override
	public OrderVO getJupsuAmt(Map<String, String> paramMap) {
		return (OrderVO)getSqlSession().selectOne("order.getJupsuAmt", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getStoreGridList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVO> getStoreGridList(Map<String, String> paramMap) {
		return (List<OrderVO>)getSqlSession().selectList("order.getStoreGridList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getCreditRate(java.util.Map)
	 */
	@Override
	public OrderVO getCreditRate(Map<String, String> paramMap) {
		return (OrderVO)getSqlSession().selectOne("order.getCreditRate", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getGuarantor(java.util.Map)
	 */
	@Override
	public OrderVO getGuarantor(Map<String, String> paramMap) {
		return (OrderVO)getSqlSession().selectOne("order.getGuarantor", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getCreditCount(java.util.Map)
	 */
	@Override
	public OrderVO getCreditCount(Map<String, String> paramMap) {
		return (OrderVO)getSqlSession().selectOne("order.getCreditCount", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getCreditLimit(java.util.Map)
	 */
	@Override
	public OrderVO getCreditLimit(Map<String, String> paramMap) {
		return (OrderVO)getSqlSession().selectOne("order.getCreditLimit", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getSawonInfo(java.util.Map)
	 */
	@Override
	public OrderVO getSawonInfo(Map<String, String> paramMap) {
		return (OrderVO)getSqlSession().selectOne("order.getSawonInfo", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getCreditDamboInfo(java.util.Map)
	 */
	@Override
	public OrderVO getCreditDamboInfo(Map<String, String> paramMap) {
		return (OrderVO)getSqlSession().selectOne("order.getCreditDamboInfo", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getSaleTotCredit(java.util.Map)
	 */
	@Override
	public OrderVO getSaleTotCredit(Map<String, String> paramMap) {
		return (OrderVO)getSqlSession().selectOne("order.getSaleTotCredit", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getBudongYn(java.util.Map)
	 */
	@Override
	public OrderVO getBudongYn(Map<String, String> paramMap) {
		return (OrderVO)getSqlSession().selectOne("order.getBudongYn", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getJumunLimit(java.util.Map)
	 */
	@Override
	public OrderVO getJumunLimit(Map<String, String> paramMap) {
		return (OrderVO)getSqlSession().selectOne("order.getJumunLimit", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getStoreYn(java.util.Map)
	 */
	@Override
	public OrderVO getStoreYn(Map<String, String> paramMap) {
		return (OrderVO)getSqlSession().selectOne("order.getStoreYn", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getStoreSawonInfo(java.util.Map)
	 */
	@Override
	public OrderVO getStoreSawonInfo(Map<String, String> paramMap) {
		return (OrderVO)getSqlSession().selectOne("order.getStoreSawonInfo", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getRcustBudongYn(java.util.Map)
	 */
	@Override
	public OrderVO getRcustBudongYn(Map<String, String> paramMap) {
		return (OrderVO)getSqlSession().selectOne("order.getRcustBudongYn", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getItemGridList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ItemVO> getItemGridList(Map<String, String> paramMap) {
		return (List<ItemVO>)getSqlSession().selectList("order.getItemGridList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getItemYn(java.util.Map)
	 */
	@Override
	public ItemVO getItemYn(Map<String, String> paramMap) {
		return (ItemVO)getSqlSession().selectOne("order.getItemYn", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getItemGb(java.util.Map)
	 */
	@Override
	public ItemVO getItemGb(Map<String, String> paramMap) {
		return (ItemVO)getSqlSession().selectOne("order.getItemGb", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getQtyCnt(java.util.Map)
	 */
	@Override
	public ItemVO getQtyCnt(Map<String, String> paramMap) {
		return (ItemVO)getSqlSession().selectOne("order.getQtyCnt", paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getItemChk(java.util.Map)
	 */
	@Override
	public ItemVO getItemChk(Map<String, String> paramMap) {
		return (ItemVO)getSqlSession().selectOne("order.getItemChk", paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getJaego(java.util.Map)
	 */
	@Override
	public ItemVO getJaego(Map<String, String> paramMap) {
		return (ItemVO)getSqlSession().selectOne("order.getJaego", paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getInvjaego(java.util.Map)
	 */
	@Override
	public ItemVO getInvjaego(Map<String, String> paramMap) {
		return (ItemVO)getSqlSession().selectOne("order.getInvjaego", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getLiCnt(java.util.Map)
	 */
	@Override
	public ItemVO getLiCnt(Map<String, String> paramMap) {
		return (ItemVO)getSqlSession().selectOne("order.getLiCnt", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getProcedureCall(java.util.Map)
	 */
	@Override
	public String getProcedureCall(Map<String, Object> paramMap) {
		
		Map<String, String> procedureParamMap = new HashMap<String, String>();
		procedureParamMap.put("tableType", "SALE0203");
		procedureParamMap.put("ldt_req_date", paramMap.get("ldt_req_date").toString());
		getSqlSession().selectOne("order.getProcedureCall", procedureParamMap);
		return procedureParamMap.get("ll_max");
		
		/*
		HashMap getMap = (HashMap<String, String>)getSqlSession().selectOne("order.getProcedureCall", procedureParamMap);
		
		int getOrderNo = Integer.parseInt( StringUtil.nvl2(getMap.get("NEWORDERNO").toString(), "0")    );
		
		return getOrderNo;
		*/
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#insertDetailOrder(com.hanaph.saleon.order.vo.ItemVO)
	 */
	@Override
	public int insertDetailOrder(ItemVO detailItemVO) {
		return (Integer)getSqlSession().insert("order.insertDetailOrder", detailItemVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#insertMasterOrder(com.hanaph.saleon.order.vo.ItemVO)
	 */
	@Override
	public int insertMasterOrder(ItemVO masterItemVO) {
		return (Integer)getSqlSession().insert("order.insertMasterOrder", masterItemVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#updateChulgoQty(com.hanaph.saleon.order.vo.ItemVO)
	 */
	@Override
	public int updateChulgoQty(ItemVO detailItemVO) {
		return (Integer)getSqlSession().update("order.updateChulgoQty", detailItemVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getMasterGridList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ItemVO> getMasterGridList(Map<String, String> paramMap) {
		return (List<ItemVO>)getSqlSession().selectList("order.getMasterGridList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getDetailGridList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ItemVO> getDetailGridList(Map<String, String> paramMap) {
		return (List<ItemVO>)getSqlSession().selectList("order.getDetailGridList", paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getBasDanga(java.util.Map)
	 */
	@Override
	public ItemVO getBasDanga(Map<String, String> paramMap) {
		return (ItemVO)getSqlSession().selectOne("order.getBasDanga", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getReceiptChk(java.util.Map)
	 */
	@Override
	public ItemVO getReceiptChk(Map<String, String> paramMap) {
		return (ItemVO)getSqlSession().selectOne("order.getReceiptChk", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#updateDetailOrder(com.hanaph.saleon.order.vo.ItemVO)
	 */
	@Override
	public int updateDetailOrder(ItemVO detailItemVO) {
		return (Integer)getSqlSession().update("order.updateDetailOrder", detailItemVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#updateMasterOrder(com.hanaph.saleon.order.vo.ItemVO)
	 */
	@Override
	public int updateMasterOrder(ItemVO masterItemVO) {
		return (Integer)getSqlSession().update("order.updateMasterOrder", masterItemVO);
	}
	
	@Override
	public int updateMasterAmt(ItemVO masterItemVO) {
		return (Integer)getSqlSession().update("order.updateMasterAmt", masterItemVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#deleteDetailOrder(com.hanaph.saleon.order.vo.ItemVO)
	 */
	@Override
	public int deleteDetailOrder(ItemVO detailItemVO) {
		return (Integer)getSqlSession().delete("order.deleteDetailOrder", detailItemVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#deleteMasterOrder(com.hanaph.saleon.order.vo.ItemVO)
	 */
	@Override
	public int deleteMasterOrder(ItemVO masterItemVO) {
		return (Integer)getSqlSession().delete("order.deleteMasterOrder", masterItemVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getOrderStatusGridList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ItemVO> getOrderStatusGridList(Map<String, Object> paramMap) {
		return (List<ItemVO>)getSqlSession().selectList("order.getOrderStatusGridList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.order.dao.OrderDAO#getRateDay(java.util.Map)
	 */
	@Override
	public OrderVO getRateDay(Map<String, String> paramMap) {
		return (OrderVO)getSqlSession().selectOne("order.getRateDay", paramMap);
	}
	

	@Override
	/**
	 * 주문 등록 전 거래처 상태 체크
	 */
	public HashMap<String, String> getBeforeCheck(Map<String, String> paramMap){
		return (HashMap<String, String>)getSqlSession().selectOne("order.getCustBeforeChk", paramMap);
	}
	
	@Override
	/**
	 * 판매처 검색 후 and 저장 전 거래처,간납처 체크
	 */
	public HashMap<String, String> getBeforeCheck2(Map<String, String> paramMap){
		
		return (HashMap<String, String>)getSqlSession().selectOne("order.getCustBeforeChk2", paramMap);
	}

	@Override
	public ArrayList<String> getItemChkFnc(Map<String, String> paramMap){
		
		return (ArrayList<String>)getSqlSession().selectList("order.getItemChkFnc", paramMap);
	}
	
	@Override
	public void insertOrderExcelUpload(List<ItemVO> list) {
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);	//batch insert
		try {

			for (ItemVO itemVO : list) {
				sqlSession.update("order.insertOrderExcelUpload", itemVO);
			}
			
			sqlSession.flushStatements();
			sqlSession.commit();

		}finally {
			sqlSession.close();
		}
	}

	@Override
	public int deleteOrderExcelUpload(ItemVO excelItemVO) {
		return (Integer)getSqlSession().delete("order.deleteOrderExcelUpload", excelItemVO);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ItemVO> getExcelStoreGridList(Map<String, String> paramMap) {
		return (List<ItemVO>)getSqlSession().selectList("order.getExcelStoreGridList", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemVO> getExcelUploadItemGridList(Map<String, String> paramMap) {
		return (List<ItemVO>)getSqlSession().selectList("order.getExcelUploadItemGridList", paramMap);
	}
	
	public ItemVO getItemChk2(Map<String, String> paramMap){
		
		return (ItemVO)getSqlSession().selectOne("order.getItemChkFnc1", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemVO> getExcelNoStoreList(Map<String, String> paramMap) {
		return (List<ItemVO>)getSqlSession().selectList("order.getExcelNoStoreList", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemVO> getExcelNoItemList(Map<String, String> paramMap) {
		return (List<ItemVO>)getSqlSession().selectList("order.getExcelNoItemList", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int insertAccountInfo(ItemVO masterItem102VO){
		return (Integer)getSqlSession().insert("order.insertAccountInfo", masterItem102VO);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int insertAccountInfo2(ItemVO masterItem102VO){
		return (Integer)getSqlSession().insert("order.insertAccountInfo2", masterItem102VO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public OrderVO getDefaultDelivery(Map<String, String> paramMap){
		return (OrderVO)getSqlSession().selectOne("order.getDefaultDelivery", paramMap);
	}
}
