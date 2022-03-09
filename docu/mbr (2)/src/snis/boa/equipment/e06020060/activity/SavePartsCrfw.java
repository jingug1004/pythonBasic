/*================================================================================
 * 시스템			: 부품관리 
 * 소스파일 이름	: snis.boa.equipment.e06020060.activity.SavePartsCrfw.java
 * 파일설명		: 부품을 이월한다.  
 * 작성자			: 유재은 
 * 버전			: 1.0.0
 * 생성일자		: 2008-01-11
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06020060.activity;


import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.Util;

/**
* 부품을 이월 한다.
* @auther 유재은 
* @version 1.0
*/
public class SavePartsCrfw extends SnisActivity
{    
    public SavePartsCrfw()
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
    	//사용자 정보 확인
    	if ( setUserInfo(ctx)== null || !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
        saveState(ctx);
        return PosBizControlConstants.SUCCESS;
    }

   
    
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 

    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutParts");
        String	stnd_year   = (String) ctx.get("STND_YEAR");
        int year = Integer.parseInt(stnd_year)+1; 
        String crfw_year  = String.valueOf(year);
        String	mbr_cd    = (String) ctx.get("MBR_CD");
        String	parts_gbn = (String) ctx.get("PARTS_GBN");
        String	sCancel_yn = (String) ctx.get("CANCEL_YN");
        
        if ("Y".equals(sCancel_yn)) {
        	nDeleteCount = nDeleteCount + deletePartsAll(crfw_year,mbr_cd, parts_gbn);
        } else {
	        nSize = ds.getRecordCount();
	        
	        //값이 있으면 먼저 삭제한다.
	        if(nSize >0){
	        	nDeleteCount = nDeleteCount + deletePartsAll(crfw_year,mbr_cd, parts_gbn);
	        }
	        
	        //데이터 저장
	        for ( int i = 0; i < nSize; i++ ){
	            PosRecord record = ds.getRecord(i);
	            if(record.getAttribute("PRICE_STND_YEAR") != null && !"".equals(record.getAttribute("PRICE_STND_YEAR"))){
	            	nSaveCount = nSaveCount + saveParts(record,crfw_year,mbr_cd);
	                //ctx.put("test!!!", record.getAttribute("PRICE_STND_YEAR"));
	            }
	            	
	        }
        }
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
  
    /**
     * <p> 착순점 Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveParts(PosRecord record, String stnd_year, String mbr_cd)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt =insertParts(record, stnd_year, mbr_cd);
        return effectedRowCnt;
    }
   
    
    /**
     * 이월 대상 데이터를 저장한다.
     * @param record
     * @param stnd_year
     * @param mbr_cd
     * @return
     */
    protected int insertParts(PosRecord record, String stnd_year, String mbr_cd) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, stnd_year);
        param.setValueParamter(i++, mbr_cd);
        param.setValueParamter(i++, record.getAttribute("PARTS_CD"));
        param.setValueParamter(i++, record.getAttribute("PRICE_STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("NOW_UNIT_CNT"));
        param.setValueParamter(i++, record.getAttribute("NOW_UNIT_CNT"));
        /*
        param.setValueParamter(i++, "Y");
        */
    	param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").update("tbef_parts_mf001", param);
    }
    
    
    /**
     * 이월 년도에 데이터가 있으면 삭제 한다.
     * @param stnd_year
     * @param mbr_cd
     * @return
     */
    protected int deletePartsAll(String stnd_year, String mbr_cd, String parts_gbn) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, stnd_year);
        param.setValueParamter(i++, mbr_cd);
        //param.setValueParamter(i++, parts_gbn);
        return  this.getDao("boadao").update("tbef_parts_df002", param);
    }
}
