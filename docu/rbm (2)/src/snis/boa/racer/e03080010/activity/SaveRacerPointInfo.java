/*================================================================================
 * 시스템			: 선수관리
 * 소스파일 이름	: snis.can.racer.e03080010.activity.SaveRacerPointInfo.java
 * 파일설명		: 선수 상벌점 정보등록
 * 작성자			: 김경화
 * 버전			: 1.0.0
 * 생성일자		: 2007-01-06
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.racer.e03080010.activity;

import java.math.BigDecimal;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 상벌점 기준정보를 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 김경화 
* @version 1.0
*/ 
public class SaveRacerPointInfo extends SnisActivity
{    
    public SaveRacerPointInfo()
    {
    }

    /**
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String		sucess 문자열
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
    	// 사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
        
    	saveState(ctx);
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
    	String sRacerNo	 = "";
    	PosRowSet rowset = null; 
    	
    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutRacerPoint");
        nSize = ds.getRecordCount();


        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){            	
            	nDeleteCount = nDeleteCount + deleteRacerPoint(record);
            }
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
                    || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	//회차전원부여일때.
            	if (record.getAttribute("RACER_NO").equals("ALL")) {
            			rowset = searchArrangeRacer(record);            	        
            	        PosRow datarows[] = rowset.getAllRow();
            		    for ( int j = 0; j < datarows.length; j++ ) {
            		    	sRacerNo 	= String.valueOf(datarows[j].getAttribute("RACER_NO"));                            
                            nSaveCount 	= nSaveCount + saveRacerPoint(record,sRacerNo);
            		    }
            	}else{
            		nSaveCount = nSaveCount + saveRacerPoint(record, "");
            	}
            }
        }
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 선수 상벌점 Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveRacerPoint(PosRecord record,String sRacerNo)
    {
    	int effectedRowCnt = 0;
   		effectedRowCnt =insertRacerPoint(record,sRacerNo);

        return effectedRowCnt;
    }

    /**
     * <p>선수 상벌점 입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertRacerPoint(PosRecord record, String sRacerNo) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        int iSeq      	= getSeq(record);
        String tmpSeq = "0";  
        if (sRacerNo == "" || sRacerNo == null)	sRacerNo = Util.trim(record.getAttribute("RACER_NO"));
        String gbnAll = Util.trim(record.getAttribute("ALLRACER_YN"));
        
	        if (record.getAttribute("SEQ".trim()) != null)	tmpSeq = String.valueOf(record.getAttribute("SEQ".trim()));
	
	        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR")));
	        param.setValueParamter(i++, String.valueOf(tmpSeq));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD")));
	        param.setValueParamter(i++, record.getAttribute("TMS"));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RWNPT_CD")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GRNT_DT")));
	        param.setValueParamter(i++, SESSION_USER_ID);       
	        param.setValueParamter(i++, String.valueOf(iSeq));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD")));
	        param.setValueParamter(i++, record.getAttribute("TMS"));
	        param.setValueParamter(i++, sRacerNo);
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RWNPT_CD")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GRNT_DT")));
	        if (Util.trim(record.getAttribute("ALLRACER_YN")) == null || Util.trim(record.getAttribute("ALLRACER_YN")).equals("")){
	        	gbnAll = "0";
	        }
	        param.setValueParamter(i++, gbnAll);
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, SESSION_USER_ID);
	        
	       
	        return this.getDao("boadao").update("tbec_racer_rwnpt_point_ic001", param);
    }

    /**
     * <p>선수 상벌점 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		delete 레코드 개수
     * @throws  none
     */
    protected int deleteRacerPoint(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
        
        return  this.getDao("boadao").update("tbec_racer_rwnpt_point_dc001", param);
    }

    /**
     * <p> 순번가져오기</p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int getSeq(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;        
        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));      
        PosRowSet rowset = this.getDao("boadao").find("tbec_racer_rwnpt_point_fc003", param);

        BigDecimal nCnt = null;
        if (rowset.hasNext())
        {
            PosRow row = rowset.next();
            nCnt = (BigDecimal) row.getAttribute("SEQ".trim());
        }
            
        return nCnt.intValue();
    } 

    protected PosRowSet searchArrangeRacer(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
    	
    	param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));
    	param.setWhereClauseParameter(i++, record.getAttribute("MBR_CD"));
    	param.setWhereClauseParameter(i++, record.getAttribute("TMS"    ));
                
        return this.getDao("boadao").find("tbec_appo_exerc_organ_fc002", param);
    }
}