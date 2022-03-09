package com.hanaph.gw.co.fileAttach.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaph.gw.co.fileAttach.dao.FileAttachDAO;
import com.hanaph.gw.co.fileAttach.vo.FileAttachVO;
import com.hanaph.gw.of.board.vo.BoardVO;

/**
 * <pre>
 * Class Name : FileAttachServiceImpl.java
 * 설명 : 첨부파일 ServiceImpl
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
@Service(value="fileAttachService")
public class FileAttachServiceImpl implements FileAttachService {

	@Autowired
	private FileAttachDAO fileAttachDAO;

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.fileAttach.service.FileAttachService#insertFileAttach(com.hanaph.gw.co.fileAttach.vo.FileAttachVO)
	 */
	@Override
	public int insertFileAttach(FileAttachVO paramVO) {
		return fileAttachDAO.insertFileAttach(paramVO);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.fileAttach.service.FileAttachService#updateFileAttach(java.util.Map)
	 */
	@Override
	public void updateFileAttach(Map<String, String> paramMap) {
		
		if(paramMap.get("fileNum").indexOf(",") > -1){
			String fileNum = paramMap.get("fileNum");
			String[] fileNums = fileNum.split("\\,");
			
			for(int i= 0; i<fileNums.length; i++){
				paramMap.put("file_seq",fileNums[i]);
				fileAttachDAO.updateFileAttach(paramMap);
			}
		}else{
			fileAttachDAO.updateFileAttach(paramMap);
		}
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.fileAttach.service.FileAttachService#getAttachList(java.util.Map)
	 */
	@Override
	public List<FileAttachVO> getAttachList(Map<String, String> paramMap) {
		
		return fileAttachDAO.getAttachList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.fileAttach.service.FileAttachService#deleteAttach(java.lang.String)
	 */
	@Override
	public void deleteAttach(String delFileSeq) {
		
		if (!delFileSeq.equals("")) {
			String arrSeq[] = delFileSeq.split(",");
			for (int i = 0; i < arrSeq.length; i++) {
				FileAttachVO fileParamVO = new FileAttachVO();
				fileParamVO.setFile_seq(Integer.parseInt(arrSeq[i]));
				fileParamVO.setDelete_yn("Y");
				fileAttachDAO.deleteAttach(fileParamVO);
			}

		}
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.fileAttach.service.FileAttachService#getOriginFileNm(java.lang.String)
	 */
	@Override
	public String getOriginFileNm(String file_seq) {
		return fileAttachDAO.getOriginFileNm(file_seq);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.fileAttach.service.FileAttachService#deleteAttachAll(com.hanaph.gw.of.board.vo.BoardVO)
	 */
	@Override
	public void deleteAttachAll(FileAttachVO fileAttachVO) {
		fileAttachDAO.deleteAttachAll(fileAttachVO);
	}

	
}
