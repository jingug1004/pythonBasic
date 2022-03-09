/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.co.code.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaph.gw.co.code.dao.CodeDAO;
import com.hanaph.gw.co.code.vo.CodeVO;

/**
 * <pre>
 * Class Name : CodeServiceImpl.java
 * 설명 : 코드 관리 ServiceImpl
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 14.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2014. 10. 14.
 */
@Service(value="CodeService")
public class CodeServiceImpl implements CodeService {

	@Autowired
	private CodeDAO codeDao;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.code.service.CodeService#getCodeList(java.util.Map)
	 */
	public List<CodeVO> getCodeList(Map<String, String> paramMap) {
		return codeDao.getCodeList(paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.code.service.CodeService#getCodeCount(java.util.Map)
	 */
	public int getCodeCount(Map<String, String> paramMap) {
		return codeDao.getCodeCount(paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.code.service.CodeService#getScodeList(java.util.Map)
	 */
	public List<CodeVO> getScodeList(Map<String, String> paramMap) {
		return codeDao.getScodeList(paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.code.service.CodeService#detailCode(java.util.Map)
	 */
	public CodeVO detailCode(Map<String, String> paramMap) {
		return codeDao.detailCode(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.code.service.CodeService#insertCode(com.hanaph.gw.co.code.vo.CodeVO)
	 */
	public boolean insertCode(CodeVO paramCodeVO) {
		//상태값이 0이면 저장 
		//상태값이 1이면 수정 
		if("0".equals(paramCodeVO.getStatus())){ 		
			return codeDao.insertCode(paramCodeVO);
		}else if("1".equals(paramCodeVO.getStatus()) || "2".equals(paramCodeVO.getStatus())){	
			return codeDao.updateCode(paramCodeVO);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.code.service.CodeService#deleteCode(com.hanaph.gw.co.code.vo.CodeVO)
	 */
	public boolean deleteCode(CodeVO paramCodeVO) {
		//서브코드가 000이면 메인코드를 넘겨 서브코드를 전부 삭제한다.
		//서브코드가 000이 아니면 m_cd + s_cd 붙혀 코드를 넘겨 하나만 삭제 시킨다.
		if(!"000".equals(paramCodeVO.getS_cd())){	
			paramCodeVO.setCd(paramCodeVO.getM_cd() + paramCodeVO.getS_cd());
		}
		return codeDao.deleteCode(paramCodeVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.code.service.CodeService#checkCode(java.lang.String)
	 */
	public CodeVO checkCode(String cd) {
		return codeDao.checkCode(cd);
	}
}
