/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaph.gw.co.fileAttach.vo.FileAttachVO;
import com.hanaph.gw.ea.newReport.dao.OpinionDAO;
import com.hanaph.gw.ea.newReport.vo.OpinionVO;

/**
 * <pre>
 * Class Name : OpinionServiceImpl.java
 * 설명 : 의견 ServiceImpl
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 20.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 20.
 */
@Service(value="opinionService")
public class OpinionServiceImpl implements OpinionService {

	@Autowired
	private OpinionDAO opinionDao;
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.OpinionService#getOpinionList(java.util.Map)
	 */
	@Override
	public List<OpinionVO> getOpinionList(Map<String, String> paramMap) {
		return opinionDao.getOpinionList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.OpinionService#insertOpinion(com.hanaph.gw.ea.newReport.vo.OpinionVO)
	 */
	@Override
	public int insertOpinion(OpinionVO opinionVO) {
		return opinionDao.insertOpinion(opinionVO);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.OpinionService#deleteOpinion(java.util.Map)
	 */
	@Override
	public int deleteOpinion(Map<String, String> paramMap) {
		return opinionDao.deleteOpinion(paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.OpinionService#deleteOpinionAll(java.util.Map)
	 */
	@Override
	public int deleteOpinionAll(Map<String, String> paramMap) {
		return opinionDao.deleteOpinionAll(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.OpinionService#insertFileOpinion(com.hanaph.gw.co.fileAttach.vo.FileAttachVO)
	 */
	@Override
	public int insertFileOpinion(FileAttachVO uploadVO) {		
		return opinionDao.insertFileOpinion(uploadVO);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.OpinionService#updateFileOpinion(java.util.Map)
	 */
	@Override
	public void updateFileOpinion(Map<String, String> paramMap) {
		
		if(paramMap.get("fileNum").indexOf(",") > -1){
			String fileNum = paramMap.get("fileNum");
			String[] fileNums = fileNum.split("\\,");
			
			for(int i= 0; i<fileNums.length; i++){
				paramMap.put("file_seq",fileNums[i]);
				opinionDao.updateFileOpinion(paramMap);
			}
		}else{
			opinionDao.updateFileOpinion(paramMap);
		}
	}
		
	
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.OpinionService#getFileOpinionList(java.util.Map)
	 */	
	@Override
	public List<FileAttachVO> getFileOpinionList(Map<String, String> paramMap) {
		return opinionDao.getFileOpinionList(paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.OpinionService#deleteFileOpinion(java.lang.String)
	 */
	@Override
	public void deleteFileOpinion(String delFileSeq) {
		if (!delFileSeq.equals("")) {
			String arrSeq[] = delFileSeq.split(",");
			for (int i = 0; i < arrSeq.length; i++) {
				//System.out.println("---------- opinionServiceImpl deleteFileOpinion opinion_seq["+ i + "]" + arrSeq[i]);
				FileAttachVO fileParamVO = new FileAttachVO();
				fileParamVO.setFile_seq(Integer.parseInt(arrSeq[i]));
				fileParamVO.setDelete_yn("Y");
				opinionDao.deleteFileOpinion(fileParamVO);
			}  
		}
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.OpinionService#getOriginFileOpinionNm(java.lang.String)
	 */
	@Override 
	public String getOriginFileOpinionNm(String file_seq) {
		return opinionDao.getOriginFileOpinionNm(file_seq);

	}
	
}
