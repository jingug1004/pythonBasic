/**
  * Hana Project
  * Copyright 2015.,
  * CHOE @since   : 2015. 3. 27.
  */
package com.hanaph.gw.ea.newReport.service;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.NewMadicineVO;


/**
 * <pre>
 * Class Name : NewReportE01013Service.java
 * 설명 : 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 3. 27.      shchoe          
 * </pre>
 * 
 * @version : 
 * @author  : shchoe
 * @since   : 2015. 3. 27.
 */
public interface NewReportE01013Service { 

	public String insertNewMadicine(ApprovalMasterVO paramAmVO, NewMadicineVO paramVrVO);
	
	public NewMadicineVO NewMadicineDetail(String approval_seq);
	
	void updateNewMadicine(ApprovalMasterVO paramAmVO, NewMadicineVO paramVrVO) ;
}
