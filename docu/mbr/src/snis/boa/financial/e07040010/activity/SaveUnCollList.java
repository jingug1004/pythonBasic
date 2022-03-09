/*================================================================================
 * 시스템			: 상금관리 
 * 소스파일 이름	: snis.boa.financial.e07040010.activity.SaveUnCollList.java
 * 파일설명		: 미수금내역을 저장한다.  
 * 작성자			: 유재은 
 * 버전			: 1.0.0
 * 생성일자		: 2008-01-25
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.financial.e07040010.activity;


import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.Util;

/**
* 미수금내역에 대한 정보를 등록, 수정, 삭제 한다.
* @auther 유재은 
* @version 1.0
*/
public class SaveUnCollList extends SnisActivity
{    
    public SaveUnCollList()
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
        
        ds    = (PosDataset) ctx.get("dsOutUnColList");
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	payMonth   = (String) ctx.get("PAY_MONTH");
        String	count   = (String) ctx.get("COUNT");
        String	nmKor =  (String) ctx.get("NM_KOR");
        String	collTpe =  (String) ctx.get("COLL_TPE");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            //if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
           // 	nDeleteCount = nDeleteCount + deleteOrder(record);            	
           // }else{
            	nSaveCount = nSaveCount + saveOrder(record,stndYear,payMonth,count);            	
           // }
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
    protected int saveOrder(PosRecord record, String stndYear, String payMonth, String count)
    {
    	int effectedRowCnt = 0;
    	if(logger.isDebugEnabled())logger.logDebug("=======================in saveOrder ");
    	if(logger.isDebugEnabled())logger.logDebug("count = " + count);
    	if(count.equals("0")){
    		effectedRowCnt =insertOrder(record);
    	
    	}else{
    		effectedRowCnt = updateOrder(record);
    	}
    	
        return effectedRowCnt;
    }

    /**
     * <p> 미수금내역등록   Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateOrder(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
    	param.setValueParamter(i++, record.getAttribute("COLL_AMT")); 
    	param.setValueParamter(i++, record.getAttribute("COLL_TPE")); 
    	param.setValueParamter(i++, record.getAttribute("COLL_DT")); 
    	param.setValueParamter(i++, record.getAttribute("RMK")); 
    	param.setValueParamter(i++, SESSION_USER_ID);
    	param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
    	param.setValueParamter(i++, record.getAttribute("PAY_MONTH")); 
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, record.getAttribute("PAY_TPE"));
    	
    	//logger.logDebug("OLD.STND_YEAR =" +record.getAttribute("OLD.STND_YEAR") );
	        
        return this.getDao("boadao").update("tbeg_uncollected_uf001", param);
    }

    /**
     * <p>미수금내역 입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertOrder(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;        
        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
	    param.setValueParamter(i++, record.getAttribute("PAY_MONTH")); 
	    param.setValueParamter(i++, record.getAttribute("RACER_NO"));
	    param.setValueParamter(i++, record.getAttribute("PAY_TPE")); 
    	param.setValueParamter(i++, record.getAttribute("COLL_AMT")); 
    	param.setValueParamter(i++, record.getAttribute("COLL_TPE"));
    	param.setValueParamter(i++, record.getAttribute("COLL_TPE"));
    	param.setValueParamter(i++, record.getAttribute("COLL_DT"));
    	param.setValueParamter(i++, record.getAttribute("RMK")); 
    	param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").update("tbeg_uncollected_if001", param);
    }

    /**
     * <p>미수금내역 삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		delete 레코드 개수
     * @throws  none
     */
    protected int deleteOrder(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("PAY_MONTH")); 
        param.setValueParamter(i++, record.getAttribute("RACER_NO"));
        param.setValueParamter(i++, record.getAttribute("PAY_TPE"));
        
        return  this.getDao("boadao").update("tbeg_uncollected_df001", param);
    }
}
