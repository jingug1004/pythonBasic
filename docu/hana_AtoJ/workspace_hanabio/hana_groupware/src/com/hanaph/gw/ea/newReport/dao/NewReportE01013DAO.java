
package com.hanaph.gw.ea.newReport.dao;

import com.hanaph.gw.ea.newReport.vo.NewMadicineVO;

/**
 * <pre>
 * Class Name : NewReportE01013DAO.java
 * 설명 : 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 3. 26.      shchoe
 * </pre>
 * 
 * @version :
 * @author : shchoe
 * @since : 2015. 3. 26.
 */

public interface NewReportE01013DAO {	
			
	public void insertNewMadicine(NewMadicineVO paramVrVO);
	
	NewMadicineVO NewMadicineDetail(String approval_seq);
	
	void updateNewMadicine(NewMadicineVO paramVrVO) ;
	
}
