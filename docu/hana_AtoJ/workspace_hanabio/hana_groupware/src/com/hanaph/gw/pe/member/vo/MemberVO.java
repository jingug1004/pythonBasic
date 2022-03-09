/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.pe.member.vo;

/**
 * <pre>
 * Class Name : MyPageVO.java
 * 설명 : 회원정보VO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 16.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 10. 16.
 */
public class MemberVO {
	private String emp_no; // 사번
	private String engag_div; // 재직구분(70010-재직,70020-퇴직,70030-휴직)
	private String emp_ko_nm; // 이름
	private String emp_ch_nm; // 한자명
	private String emp_en_nm; // 영어명
	private String resid_no; // 주민번호
	private String birth_div; // 양,음력구분
	private String birth_dt; // 생일
	private String step_cd; // 급호코드
	private String grad_cd; // 직급코드
	private String grad_ko_nm; // 직급명
	private String dept_cd; // 부서코드
	private String dept_ko_nm; // 부서명
	private String oprt_cd; // 조직코드
	private String pos_cd; // 직위코드
	private String assgn_cd; // 직책(보직)코드Select * from hrCoCommon_0 where comnCd like '27%'
	private String encmpy_dt; // 입사일자
	private String encmpy_cd; // 채용구분_공통코드06(발령에서넘어옴)
	private String occpt_cd; // 직종코드_공통코드03
	private String duty_cd; // 직무코드_공통코드04
	private String stat_cd; // 신분
	private String pager_no; // 호출번호(이동통신)
	private String addr1; // 주소
	private String addr2; // 상세주소
	private String zip_cd; // 우편번호
	private String tel_no; // 전화번호
	private String e_mail; // 이메일
	private String e_mail_dmngb; // 이메일도메인구분
	private String e_mail_full; // 이메일명
	private String in_tel; // 사내전화번호
	private String last_sclar; // 최종학력
	private String sclar_nm; // 출신교명
	private String forigen_div; // 외국인구분
	private String nation_cd; // 국가코드
	private String res_div; // 거주구분
	private String resnatn_cd; // 거주지국가코드
	private String branch_cd; // 계열사코드
	private String npromt_step_dt; // 차기승호일자
	private String promt_step_dt; // 승호일자=현호봉발령일
	private String promt_grad_dt; // 승진일자=현직급발령일
	private String retir_dt; // 퇴직일자
	private String retir_reason; // 퇴직사유
	private String susp_fr_date; // 휴직시작일
	private String susp_end_dt; // 휴직종료일
	private String susp_reason; // 휴직사유
	private int susp_rate; // 휴직율
	private String retir_mid_dt; // 퇴직금중간정산일
	private String re_encmpy_dt; // 재입사일 (재입사여부컬럼있으면 입사일과동일함)
	private String elas_wk; // 탄력근무유형
	private String kt_yn; // KT재입사자여부-사용안함 : 20120104 1-비생산2-생산
	private String media_cd; // 원가구분
	private String emp_div; // 사원구분(G:일반사원,C:협력사원)
	private String lbun_yn; // 노조가입여부
	private String night_div; // 철야조여부
	private String car_reg_yn; // 차량등록증 보유여부
	private String contt_yn; // 계약서 유무
	private String first_emp_no; // 최초작성자사번
	private String first_regdate; // 최초등록일자
	private String last_emp_no; // 최종수정자사번
	private String last_regdate; // 최종수정일자
	private String last_ip; // 최종작성IP
	private String addr_en; // 영문주소
	private String emp_kind; // 정규직구분(G:정규직,C:계약직)
	private String emp_excp; // 협력사원예외자
	private String re_encmpy_yn; // 재입사여부
	private String bef_emp_no; // 이전사번
	private String retir_div; // 퇴직구분(발령구분)
	private String promt_dept_dt; // 부서변경일자
	
	//HR_HC_EMPBAS_1 사진
	private byte[] photo1;//사원사진
	private String photo1_nm;//사원사진명
	
	//CO_US_MEMBER_0
	private String pass_word;//패스워드
	private String auth_nm;//권한명
	
	//헤더 카운트
	private int messageReceiveCount;// 읽지않은 받은쪽지카운트
	private int receiveCount; //문서결재
	private int implementOngoingCount; //시행중문서함
	private int implementCompleteCount; //시행완료문서함
	private int noticeCount; //공지카운트
	private int noticeCountNoread; //읽지않은 공지카운트
	
	public int getNoticeCountNoread() {
		return noticeCountNoread;
	}
	public void setNoticeCountNoread(int noticeCountNoread) {
		this.noticeCountNoread = noticeCountNoread;
	}
	//HR_HC_EMPANN_0 최종발령일
	private String procm_dt;//발령일자
	
	//GW_EA_SHARE_TARGET 최종발령일
	private String read_yn;//열람여부
	private String read_dt;//열람시간
	private String create_dt;//등록시간
	
	private String message_seq;//메세지시퀀스
	/**
	 * @return the emp_no
	 */
	public String getEmp_no() {
		return emp_no;
	}
	/**
	 * @param emp_no the emp_no to set
	 */
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}
	/**
	 * @return the engag_div
	 */
	public String getEngag_div() {
		return engag_div;
	}
	/**
	 * @param engag_div the engag_div to set
	 */
	public void setEngag_div(String engag_div) {
		this.engag_div = engag_div;
	}
	/**
	 * @return the emp_ko_nm
	 */
	public String getEmp_ko_nm() {
		return emp_ko_nm;
	}
	/**
	 * @param emp_ko_nm the emp_ko_nm to set
	 */
	public void setEmp_ko_nm(String emp_ko_nm) {
		this.emp_ko_nm = emp_ko_nm;
	}
	/**
	 * @return the emp_ch_nm
	 */
	public String getEmp_ch_nm() {
		return emp_ch_nm;
	}
	/**
	 * @param emp_ch_nm the emp_ch_nm to set
	 */
	public void setEmp_ch_nm(String emp_ch_nm) {
		this.emp_ch_nm = emp_ch_nm;
	}
	/**
	 * @return the emp_en_nm
	 */
	public String getEmp_en_nm() {
		return emp_en_nm;
	}
	/**
	 * @param emp_en_nm the emp_en_nm to set
	 */
	public void setEmp_en_nm(String emp_en_nm) {
		this.emp_en_nm = emp_en_nm;
	}
	/**
	 * @return the resid_no
	 */
	public String getResid_no() {
		return resid_no;
	}
	/**
	 * @param resid_no the resid_no to set
	 */
	public void setResid_no(String resid_no) {
		this.resid_no = resid_no;
	}
	/**
	 * @return the birth_div
	 */
	public String getBirth_div() {
		return birth_div;
	}
	/**
	 * @param birth_div the birth_div to set
	 */
	public void setBirth_div(String birth_div) {
		this.birth_div = birth_div;
	}
	/**
	 * @return the birth_dt
	 */
	public String getBirth_dt() {
		return birth_dt;
	}
	/**
	 * @param birth_dt the birth_dt to set
	 */
	public void setBirth_dt(String birth_dt) {
		this.birth_dt = birth_dt;
	}
	/**
	 * @return the step_cd
	 */
	public String getStep_cd() {
		return step_cd;
	}
	/**
	 * @param step_cd the step_cd to set
	 */
	public void setStep_cd(String step_cd) {
		this.step_cd = step_cd;
	}
	/**
	 * @return the grad_cd
	 */
	public String getGrad_cd() {
		return grad_cd;
	}
	/**
	 * @param grad_cd the grad_cd to set
	 */
	public void setGrad_cd(String grad_cd) {
		this.grad_cd = grad_cd;
	}
	/**
	 * @return the grad_ko_nm
	 */
	public String getGrad_ko_nm() {
		return grad_ko_nm;
	}
	/**
	 * @param grad_ko_nm the grad_ko_nm to set
	 */
	public void setGrad_ko_nm(String grad_ko_nm) {
		this.grad_ko_nm = grad_ko_nm;
	}
	/**
	 * @return the dept_cd
	 */
	public String getDept_cd() {
		return dept_cd;
	}
	/**
	 * @param dept_cd the dept_cd to set
	 */
	public void setDept_cd(String dept_cd) {
		this.dept_cd = dept_cd;
	}
	/**
	 * @return the dept_ko_nm
	 */
	public String getDept_ko_nm() {
		return dept_ko_nm;
	}
	/**
	 * @param dept_ko_nm the dept_ko_nm to set
	 */
	public void setDept_ko_nm(String dept_ko_nm) {
		this.dept_ko_nm = dept_ko_nm;
	}
	/**
	 * @return the oprt_cd
	 */
	public String getOprt_cd() {
		return oprt_cd;
	}
	/**
	 * @param oprt_cd the oprt_cd to set
	 */
	public void setOprt_cd(String oprt_cd) {
		this.oprt_cd = oprt_cd;
	}
	/**
	 * @return the pos_cd
	 */
	public String getPos_cd() {
		return pos_cd;
	}
	/**
	 * @param pos_cd the pos_cd to set
	 */
	public void setPos_cd(String pos_cd) {
		this.pos_cd = pos_cd;
	}
	/**
	 * @return the assgn_cd
	 */
	public String getAssgn_cd() {
		return assgn_cd;
	}
	/**
	 * @param assgn_cd the assgn_cd to set
	 */
	public void setAssgn_cd(String assgn_cd) {
		this.assgn_cd = assgn_cd;
	}
	/**
	 * @return the encmpy_dt
	 */
	public String getEncmpy_dt() {
		return encmpy_dt;
	}
	/**
	 * @param encmpy_dt the encmpy_dt to set
	 */
	public void setEncmpy_dt(String encmpy_dt) {
		this.encmpy_dt = encmpy_dt;
	}
	/**
	 * @return the encmpy_cd
	 */
	public String getEncmpy_cd() {
		return encmpy_cd;
	}
	/**
	 * @param encmpy_cd the encmpy_cd to set
	 */
	public void setEncmpy_cd(String encmpy_cd) {
		this.encmpy_cd = encmpy_cd;
	}
	/**
	 * @return the occpt_cd
	 */
	public String getOccpt_cd() {
		return occpt_cd;
	}
	/**
	 * @param occpt_cd the occpt_cd to set
	 */
	public void setOccpt_cd(String occpt_cd) {
		this.occpt_cd = occpt_cd;
	}
	/**
	 * @return the duty_cd
	 */
	public String getDuty_cd() {
		return duty_cd;
	}
	/**
	 * @param duty_cd the duty_cd to set
	 */
	public void setDuty_cd(String duty_cd) {
		this.duty_cd = duty_cd;
	}
	/**
	 * @return the stat_cd
	 */
	public String getStat_cd() {
		return stat_cd;
	}
	/**
	 * @param stat_cd the stat_cd to set
	 */
	public void setStat_cd(String stat_cd) {
		this.stat_cd = stat_cd;
	}
	/**
	 * @return the pager_no
	 */
	public String getPager_no() {
		return pager_no;
	}
	/**
	 * @param pager_no the pager_no to set
	 */
	public void setPager_no(String pager_no) {
		this.pager_no = pager_no;
	}
	/**
	 * @return the addr1
	 */
	public String getAddr1() {
		return addr1;
	}
	/**
	 * @param addr1 the addr1 to set
	 */
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	/**
	 * @return the addr2
	 */
	public String getAddr2() {
		return addr2;
	}
	/**
	 * @param addr2 the addr2 to set
	 */
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	/**
	 * @return the zip_cd
	 */
	public String getZip_cd() {
		return zip_cd;
	}
	/**
	 * @param zip_cd the zip_cd to set
	 */
	public void setZip_cd(String zip_cd) {
		this.zip_cd = zip_cd;
	}
	/**
	 * @return the tel_no
	 */
	public String getTel_no() {
		return tel_no;
	}
	/**
	 * @param tel_no the tel_no to set
	 */
	public void setTel_no(String tel_no) {
		this.tel_no = tel_no;
	}
	/**
	 * @return the e_mail
	 */
	public String getE_mail() {
		return e_mail;
	}
	/**
	 * @param e_mail the e_mail to set
	 */
	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}
	/**
	 * @return the e_mail_dmngb
	 */
	public String getE_mail_dmngb() {
		return e_mail_dmngb;
	}
	/**
	 * @param e_mail_dmngb the e_mail_dmngb to set
	 */
	public void setE_mail_dmngb(String e_mail_dmngb) {
		this.e_mail_dmngb = e_mail_dmngb;
	}
	/**
	 * @return the e_mail_full
	 */
	public String getE_mail_full() {
		return e_mail_full;
	}
	/**
	 * @param e_mail_full the e_mail_full to set
	 */
	public void setE_mail_full(String e_mail_full) {
		this.e_mail_full = e_mail_full;
	}
	/**
	 * @return the in_tel
	 */
	public String getIn_tel() {
		return in_tel;
	}
	/**
	 * @param in_tel the in_tel to set
	 */
	public void setIn_tel(String in_tel) {
		this.in_tel = in_tel;
	}
	/**
	 * @return the last_sclar
	 */
	public String getLast_sclar() {
		return last_sclar;
	}
	/**
	 * @param last_sclar the last_sclar to set
	 */
	public void setLast_sclar(String last_sclar) {
		this.last_sclar = last_sclar;
	}
	/**
	 * @return the sclar_nm
	 */
	public String getSclar_nm() {
		return sclar_nm;
	}
	/**
	 * @param sclar_nm the sclar_nm to set
	 */
	public void setSclar_nm(String sclar_nm) {
		this.sclar_nm = sclar_nm;
	}
	/**
	 * @return the forigen_div
	 */
	public String getForigen_div() {
		return forigen_div;
	}
	/**
	 * @param forigen_div the forigen_div to set
	 */
	public void setForigen_div(String forigen_div) {
		this.forigen_div = forigen_div;
	}
	/**
	 * @return the nation_cd
	 */
	public String getNation_cd() {
		return nation_cd;
	}
	/**
	 * @param nation_cd the nation_cd to set
	 */
	public void setNation_cd(String nation_cd) {
		this.nation_cd = nation_cd;
	}
	/**
	 * @return the res_div
	 */
	public String getRes_div() {
		return res_div;
	}
	/**
	 * @param res_div the res_div to set
	 */
	public void setRes_div(String res_div) {
		this.res_div = res_div;
	}
	/**
	 * @return the resnatn_cd
	 */
	public String getResnatn_cd() {
		return resnatn_cd;
	}
	/**
	 * @param resnatn_cd the resnatn_cd to set
	 */
	public void setResnatn_cd(String resnatn_cd) {
		this.resnatn_cd = resnatn_cd;
	}
	/**
	 * @return the branch_cd
	 */
	public String getBranch_cd() {
		return branch_cd;
	}
	/**
	 * @param branch_cd the branch_cd to set
	 */
	public void setBranch_cd(String branch_cd) {
		this.branch_cd = branch_cd;
	}
	/**
	 * @return the npromt_step_dt
	 */
	public String getNpromt_step_dt() {
		return npromt_step_dt;
	}
	/**
	 * @param npromt_step_dt the npromt_step_dt to set
	 */
	public void setNpromt_step_dt(String npromt_step_dt) {
		this.npromt_step_dt = npromt_step_dt;
	}
	/**
	 * @return the promt_step_dt
	 */
	public String getPromt_step_dt() {
		return promt_step_dt;
	}
	/**
	 * @param promt_step_dt the promt_step_dt to set
	 */
	public void setPromt_step_dt(String promt_step_dt) {
		this.promt_step_dt = promt_step_dt;
	}
	/**
	 * @return the promt_grad_dt
	 */
	public String getPromt_grad_dt() {
		return promt_grad_dt;
	}
	/**
	 * @param promt_grad_dt the promt_grad_dt to set
	 */
	public void setPromt_grad_dt(String promt_grad_dt) {
		this.promt_grad_dt = promt_grad_dt;
	}
	/**
	 * @return the retir_dt
	 */
	public String getRetir_dt() {
		return retir_dt;
	}
	/**
	 * @param retir_dt the retir_dt to set
	 */
	public void setRetir_dt(String retir_dt) {
		this.retir_dt = retir_dt;
	}
	/**
	 * @return the retir_reason
	 */
	public String getRetir_reason() {
		return retir_reason;
	}
	/**
	 * @param retir_reason the retir_reason to set
	 */
	public void setRetir_reason(String retir_reason) {
		this.retir_reason = retir_reason;
	}
	/**
	 * @return the susp_fr_date
	 */
	public String getSusp_fr_date() {
		return susp_fr_date;
	}
	/**
	 * @param susp_fr_date the susp_fr_date to set
	 */
	public void setSusp_fr_date(String susp_fr_date) {
		this.susp_fr_date = susp_fr_date;
	}
	/**
	 * @return the susp_end_dt
	 */
	public String getSusp_end_dt() {
		return susp_end_dt;
	}
	/**
	 * @param susp_end_dt the susp_end_dt to set
	 */
	public void setSusp_end_dt(String susp_end_dt) {
		this.susp_end_dt = susp_end_dt;
	}
	/**
	 * @return the susp_reason
	 */
	public String getSusp_reason() {
		return susp_reason;
	}
	/**
	 * @param susp_reason the susp_reason to set
	 */
	public void setSusp_reason(String susp_reason) {
		this.susp_reason = susp_reason;
	}
	/**
	 * @return the susp_rate
	 */
	public int getSusp_rate() {
		return susp_rate;
	}
	/**
	 * @param susp_rate the susp_rate to set
	 */
	public void setSusp_rate(int susp_rate) {
		this.susp_rate = susp_rate;
	}
	/**
	 * @return the retir_mid_dt
	 */
	public String getRetir_mid_dt() {
		return retir_mid_dt;
	}
	/**
	 * @param retir_mid_dt the retir_mid_dt to set
	 */
	public void setRetir_mid_dt(String retir_mid_dt) {
		this.retir_mid_dt = retir_mid_dt;
	}
	/**
	 * @return the re_encmpy_dt
	 */
	public String getRe_encmpy_dt() {
		return re_encmpy_dt;
	}
	/**
	 * @param re_encmpy_dt the re_encmpy_dt to set
	 */
	public void setRe_encmpy_dt(String re_encmpy_dt) {
		this.re_encmpy_dt = re_encmpy_dt;
	}
	/**
	 * @return the elas_wk
	 */
	public String getElas_wk() {
		return elas_wk;
	}
	/**
	 * @param elas_wk the elas_wk to set
	 */
	public void setElas_wk(String elas_wk) {
		this.elas_wk = elas_wk;
	}
	/**
	 * @return the kt_yn
	 */
	public String getKt_yn() {
		return kt_yn;
	}
	/**
	 * @param kt_yn the kt_yn to set
	 */
	public void setKt_yn(String kt_yn) {
		this.kt_yn = kt_yn;
	}
	/**
	 * @return the media_cd
	 */
	public String getMedia_cd() {
		return media_cd;
	}
	/**
	 * @param media_cd the media_cd to set
	 */
	public void setMedia_cd(String media_cd) {
		this.media_cd = media_cd;
	}
	/**
	 * @return the emp_div
	 */
	public String getEmp_div() {
		return emp_div;
	}
	/**
	 * @param emp_div the emp_div to set
	 */
	public void setEmp_div(String emp_div) {
		this.emp_div = emp_div;
	}
	/**
	 * @return the lbun_yn
	 */
	public String getLbun_yn() {
		return lbun_yn;
	}
	/**
	 * @param lbun_yn the lbun_yn to set
	 */
	public void setLbun_yn(String lbun_yn) {
		this.lbun_yn = lbun_yn;
	}
	/**
	 * @return the night_div
	 */
	public String getNight_div() {
		return night_div;
	}
	/**
	 * @param night_div the night_div to set
	 */
	public void setNight_div(String night_div) {
		this.night_div = night_div;
	}
	/**
	 * @return the car_reg_yn
	 */
	public String getCar_reg_yn() {
		return car_reg_yn;
	}
	/**
	 * @param car_reg_yn the car_reg_yn to set
	 */
	public void setCar_reg_yn(String car_reg_yn) {
		this.car_reg_yn = car_reg_yn;
	}
	/**
	 * @return the contt_yn
	 */
	public String getContt_yn() {
		return contt_yn;
	}
	/**
	 * @param contt_yn the contt_yn to set
	 */
	public void setContt_yn(String contt_yn) {
		this.contt_yn = contt_yn;
	}
	/**
	 * @return the first_emp_no
	 */
	public String getFirst_emp_no() {
		return first_emp_no;
	}
	/**
	 * @param first_emp_no the first_emp_no to set
	 */
	public void setFirst_emp_no(String first_emp_no) {
		this.first_emp_no = first_emp_no;
	}
	/**
	 * @return the first_regdate
	 */
	public String getFirst_regdate() {
		return first_regdate;
	}
	/**
	 * @param first_regdate the first_regdate to set
	 */
	public void setFirst_regdate(String first_regdate) {
		this.first_regdate = first_regdate;
	}
	/**
	 * @return the last_emp_no
	 */
	public String getLast_emp_no() {
		return last_emp_no;
	}
	/**
	 * @param last_emp_no the last_emp_no to set
	 */
	public void setLast_emp_no(String last_emp_no) {
		this.last_emp_no = last_emp_no;
	}
	/**
	 * @return the last_regdate
	 */
	public String getLast_regdate() {
		return last_regdate;
	}
	/**
	 * @param last_regdate the last_regdate to set
	 */
	public void setLast_regdate(String last_regdate) {
		this.last_regdate = last_regdate;
	}
	/**
	 * @return the last_ip
	 */
	public String getLast_ip() {
		return last_ip;
	}
	/**
	 * @param last_ip the last_ip to set
	 */
	public void setLast_ip(String last_ip) {
		this.last_ip = last_ip;
	}
	/**
	 * @return the addr_en
	 */
	public String getAddr_en() {
		return addr_en;
	}
	/**
	 * @param addr_en the addr_en to set
	 */
	public void setAddr_en(String addr_en) {
		this.addr_en = addr_en;
	}
	/**
	 * @return the emp_kind
	 */
	public String getEmp_kind() {
		return emp_kind;
	}
	/**
	 * @param emp_kind the emp_kind to set
	 */
	public void setEmp_kind(String emp_kind) {
		this.emp_kind = emp_kind;
	}
	/**
	 * @return the emp_excp
	 */
	public String getEmp_excp() {
		return emp_excp;
	}
	/**
	 * @param emp_excp the emp_excp to set
	 */
	public void setEmp_excp(String emp_excp) {
		this.emp_excp = emp_excp;
	}
	/**
	 * @return the re_encmpy_yn
	 */
	public String getRe_encmpy_yn() {
		return re_encmpy_yn;
	}
	/**
	 * @param re_encmpy_yn the re_encmpy_yn to set
	 */
	public void setRe_encmpy_yn(String re_encmpy_yn) {
		this.re_encmpy_yn = re_encmpy_yn;
	}
	/**
	 * @return the bef_emp_no
	 */
	public String getBef_emp_no() {
		return bef_emp_no;
	}
	/**
	 * @param bef_emp_no the bef_emp_no to set
	 */
	public void setBef_emp_no(String bef_emp_no) {
		this.bef_emp_no = bef_emp_no;
	}
	/**
	 * @return the retir_div
	 */
	public String getRetir_div() {
		return retir_div;
	}
	/**
	 * @param retir_div the retir_div to set
	 */
	public void setRetir_div(String retir_div) {
		this.retir_div = retir_div;
	}
	/**
	 * @return the promt_dept_dt
	 */
	public String getPromt_dept_dt() {
		return promt_dept_dt;
	}
	/**
	 * @param promt_dept_dt the promt_dept_dt to set
	 */
	public void setPromt_dept_dt(String promt_dept_dt) {
		this.promt_dept_dt = promt_dept_dt;
	}
	/**
	 * @return the photo1
	 */
	public byte[] getPhoto1() {
		return photo1;
	}
	/**
	 * @param photo1 the photo1 to set
	 */
	public void setPhoto1(byte[] photo1) {
		this.photo1 = photo1;
	}
	/**
	 * @return the photo1_nm
	 */
	public String getPhoto1_nm() {
		return photo1_nm;
	}
	/**
	 * @param photo1_nm the photo1_nm to set
	 */
	public void setPhoto1_nm(String photo1_nm) {
		this.photo1_nm = photo1_nm;
	}
	/**
	 * @return the pass_word
	 */
	public String getPass_word() {
		return pass_word;
	}
	/**
	 * @param pass_word the pass_word to set
	 */
	public void setPass_word(String pass_word) {
		this.pass_word = pass_word;
	}
	/**
	 * @return the auth_nm
	 */
	public String getAuth_nm() {
		return auth_nm;
	}
	/**
	 * @param auth_nm the auth_nm to set
	 */
	public void setAuth_nm(String auth_nm) {
		this.auth_nm = auth_nm;
	}
	/**
	 * @return the messageReceiveCount
	 */
	public int getMessageReceiveCount() {
		return messageReceiveCount;
	}
	/**
	 * @param messageReceiveCount the messageReceiveCount to set
	 */
	public void setMessageReceiveCount(int messageReceiveCount) {
		this.messageReceiveCount = messageReceiveCount;
	}
	/**
	 * @return the receiveCount
	 */
	public int getReceiveCount() {
		return receiveCount;
	}
	/**
	 * @param receiveCount the receiveCount to set
	 */
	public void setReceiveCount(int receiveCount) {
		this.receiveCount = receiveCount;
	}
	/**
	 * @return the implementOngoingCount
	 */
	public int getImplementOngoingCount() {
		return implementOngoingCount;
	}
	/**
	 * @param implementOngoingCount the implementOngoingCount to set
	 */
	public void setImplementOngoingCount(int implementOngoingCount) {
		this.implementOngoingCount = implementOngoingCount;
	}
	/**
	 * @return the implementCompleteCount
	 */
	public int getImplementCompleteCount() {
		return implementCompleteCount;
	}
	/**
	 * @param implementCompleteCount the implementCompleteCount to set
	 */
	public void setImplementCompleteCount(int implementCompleteCount) {
		this.implementCompleteCount = implementCompleteCount;
	}
	/**
	 * @return the noticeCount
	 */
	public int getNoticeCount() {
		return noticeCount;
	}
	/**
	 * @param noticeCount the noticeCount to set
	 */
	public void setNoticeCount(int noticeCount) {
		this.noticeCount = noticeCount;
	}
	/**
	 * @return the procm_dt
	 */
	public String getProcm_dt() {
		return procm_dt;
	}
	/**
	 * @param procm_dt the procm_dt to set
	 */
	public void setProcm_dt(String procm_dt) {
		this.procm_dt = procm_dt;
	}
	/**
	 * @return the read_yn
	 */
	public String getRead_yn() {
		return read_yn;
	}
	/**
	 * @param read_yn the read_yn to set
	 */
	public void setRead_yn(String read_yn) {
		this.read_yn = read_yn;
	}
	/**
	 * @return the read_dt
	 */
	public String getRead_dt() {
		return read_dt;
	}
	/**
	 * @param read_dt the read_dt to set
	 */
	public void setRead_dt(String read_dt) {
		this.read_dt = read_dt;
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
	 * @return the message_seq
	 */
	public String getMessage_seq() {
		return message_seq;
	}
	/**
	 * @param message_seq the message_seq to set
	 */
	public void setMessage_seq(String message_seq) {
		this.message_seq = message_seq;
	}
	
	
	
}
