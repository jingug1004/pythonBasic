package com.hanaph.gw.co.common.vo;

import java.util.List;
/**
 * <pre>
 * Class Name : CommonVO.java
 * 설명 : 공통 VO 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 8.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2015. 1. 8.
 */
public class CommonVO {
	
	private String create_no;	//작성자
	private String modify_no;	//수정자
	private String use_yn;		//사용여부
	private String delete_yn;	//삭제YN
	private String create_dt;	//작성일
	private String modify_dt;	//수정일
	private List attacheFile;	//첨부파일
	private int result;			//리턴값
	private String resultMsg;	//리턴메세지
	
	/**
	 * @return the create_no
	 */
	public String getCreate_no() {
		return create_no;
	}
	/**
	 * @param create_no the create_no to set
	 */
	public void setCreate_no(String create_no) {
		this.create_no = create_no;
	}
	/**
	 * @return the modify_no
	 */
	public String getModify_no() {
		return modify_no;
	}
	/**
	 * @param modify_no the modify_no to set
	 */
	public void setModify_no(String modify_no) {
		this.modify_no = modify_no;
	}
	/**
	 * @return the use_yn
	 */
	public String getUse_yn() {
		return use_yn;
	}
	/**
	 * @param use_yn the use_yn to set
	 */
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	/**
	 * @return the delete_yn
	 */
	public String getDelete_yn() {
		return delete_yn;
	}
	/**
	 * @param delete_yn the delete_yn to set
	 */
	public void setDelete_yn(String delete_yn) {
		this.delete_yn = delete_yn;
	}
	/**
	 * @return the create_dt
	 */
	public String getCreate_dt() {
		return create_dt;
	}
	/**
	 * @param create_dt the create_dt to set
	 */
	public void setCreate_dt(String create_dt) {
		this.create_dt = create_dt;
	}
	/**
	 * @return the modify_dt
	 */
	public String getModify_dt() {
		return modify_dt;
	}
	/**
	 * @param modify_dt the modify_dt to set
	 */
	public void setModify_dt(String modify_dt) {
		this.modify_dt = modify_dt;
	}
	/**
	 * @return the attacheFile
	 */
	public List getAttacheFile() {
		return attacheFile;
	}
	/**
	 * @param attacheFile the attacheFile to set
	 */
	public void setAttacheFile(List attacheFile) {
		this.attacheFile = attacheFile;
	}
	/**
	 * @return the result
	 */
	public int getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(int result) {
		this.result = result;
	}
	/**
	 * @return the resultMsg
	 */
	public String getResultMsg() {
		return resultMsg;
	}
	/**
	 * @param resultMsg the resultMsg to set
	 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
}
