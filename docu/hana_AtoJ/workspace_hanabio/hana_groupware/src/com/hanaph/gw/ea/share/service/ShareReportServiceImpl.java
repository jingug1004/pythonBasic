/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.share.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaph.gw.ea.share.dao.ShareReportDAO;
import com.hanaph.gw.ea.share.vo.ShareReportVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : ShareReportServiceImpl.java
 * 설명 : 공유문서 조회 ServiceImpl
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 21.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 21.
 */
@Service(value="shareReportService")
public class ShareReportServiceImpl implements ShareReportService {
	@Autowired
	private ShareReportDAO shareDao;

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.share.service.ShareReportService#getShareList(java.util.Map)
	 */
	@Override
	public List<ShareReportVO> getShareList(Map<String, String> paramMap) {
		return shareDao.getShareList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.share.service.ShareReportService#getShareCount(java.util.Map)
	 */
	@Override
	public int getShareCount(Map<String, String> paramMap) {
		return shareDao.getShareCount(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.share.service.ShareReportService#getShareTargetList(java.util.Map)
	 */
	@Override
	public List<MemberVO> getShareTargetList(Map<String, String> paramMap) {
		return shareDao.getShareTargetList(paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.share.service.ShareReportService#insertShare(com.hanaph.gw.ea.share.vo.ShareVO)
	 */
	@Override
	public int insertShareTarget(ShareReportVO shareVO) {
		return shareDao.insertShareTarget(shareVO);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.share.service.ShareReportService#deleteShareTarget(java.util.Map)
	 */
	@Override
	public boolean deleteShareTarget(Map<String, String> paramMap) {
		return shareDao.deleteShareTarget(paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.share.service.ShareReportService#getShareTargetDetail(java.util.Map)
	 */
	@Override
	public ShareReportVO getShareTargetDetail(Map<String, String> paramMap) {
		return shareDao.getShareTargetDetail(paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.share.service.ShareReportService#updateShareTargetReadYn(com.hanaph.gw.ea.share.vo.ShareReportVO)
	 */
	@Override
	public boolean updateShareTargetReadYn(ShareReportVO shareReportVO) {
		return shareDao.updateShareTargetReadYn(shareReportVO);
	}

}
