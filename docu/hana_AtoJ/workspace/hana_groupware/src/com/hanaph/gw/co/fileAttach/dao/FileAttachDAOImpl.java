package com.hanaph.gw.co.fileAttach.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.co.fileAttach.vo.FileAttachVO;

/**
 * <pre>
 * Class Name : FileAttachDAOImpl.java
 * 설명 : 첨부파일 DAOImpl
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 3. 4.      jung jin muk
 * </pre>
 * 
 * @version : 
 * @author  : jung jin muk(pc123pc@irush.co.kr)
 * @since   : 2015. 3. 4.
 */
@Repository("fileAttachDAO")
public class FileAttachDAOImpl extends SqlSessionDaoSupport implements FileAttachDAO {

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.fileAttach.dao.FileAttachDAO#insertFileAttach(com.hanaph.gw.co.fileAttach.vo.FileAttachVO)
	 */
	@Override
	public int insertFileAttach(FileAttachVO paramVO) {
		getSqlSession().insert("fileAttach.insertFileAttach", paramVO);
		return paramVO.getFile_seq();
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.fileAttach.dao.FileAttachDAO#updateFileAttach(java.util.Map)
	 */
	@Override
	public void updateFileAttach(Map<String, String> paramMap) {
		getSqlSession().update("fileAttach.updateFileAttach", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.fileAttach.dao.FileAttachDAO#getAttachList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FileAttachVO> getAttachList(Map<String, String> paramMap) {
		
		return getSqlSession().selectList("fileAttach.getAttachList",paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.fileAttach.dao.FileAttachDAO#deleteAttach(com.hanaph.gw.co.fileAttach.vo.FileAttachVO)
	 */
	@Override
	public int deleteAttach(FileAttachVO fileParamVO) {
		return getSqlSession().update("fileAttach.deleteAttach", fileParamVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.fileAttach.dao.FileAttachDAO#getOriginFileNm(java.lang.String)
	 */
	@Override
	public String getOriginFileNm(String file_seq) {
		return (String) getSqlSession().selectOne("fileAttach.getOriginFileNm", file_seq);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.fileAttach.dao.FileAttachDAO#deleteAttachAll(com.hanaph.gw.co.fileAttach.vo.FileAttachVO)
	 */
	@Override
	public void deleteAttachAll(FileAttachVO fileAttachVO) {
		getSqlSession().update("fileAttach.deleteAttachAll", fileAttachVO);
	}
	
}
