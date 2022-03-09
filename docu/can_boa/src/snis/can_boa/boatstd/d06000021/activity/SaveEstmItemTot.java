
/*================================================================================
 * 시스템			: 후보생 
 * 소스파일 이름	: snis.can.system.d06000021.activity.SaveViolCd.java
 * 파일설명		: 후보생 총괄평가표 저장
 * 작성자			: 조성문
 * 버전			: 1.0.0
 * 생성일자		: 2011-05-25
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/


package snis.can_boa.boatstd.d06000021.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 후보생 위반코드 내역을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 노인수
* @version 1.0
*/
public class SaveEstmItemTot  extends SnisActivity
{    
	
    public SaveEstmItemTot()
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
    	
    	PosDataset ds;
        
    	nDeleteCount = deleteEstmItemTo(ctx);
    	nSaveCount = insertEstmItemTo(ctx);
        
        Util.setDeleteCount(ctx, nDeleteCount   );
        Util.setSaveCount  (ctx, nSaveCount     );

    }
    
    
    /**
     * <p> 총괄평가표  입력  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertEstmItemTo(PosContext ctx) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        int dmlcount = 0;      
       
		param.setValueParamter(i++, SESSION_USER_ID);       
//    	param.setValueParamter(i++, ctx.get("racerPerioNo"));
//    	param.setValueParamter(i++, ctx.get("round"));
    	param.setValueParamter(i++, ctx.get("racerPerioNo"));
    	param.setValueParamter(i++, ctx.get("round"));
    	param.setValueParamter(i++, ctx.get("racerPerioNo"));
    	param.setValueParamter(i++, ctx.get("round"));
    	param.setValueParamter(i++, ctx.get("racerPerioNo"));
    	param.setValueParamter(i++, ctx.get("round"));
    	param.setValueParamter(i++, ctx.get("racerPerioNo"));
    	param.setValueParamter(i++, ctx.get("round"));
    	param.setValueParamter(i++, ctx.get("racerPerioNo"));
    	param.setValueParamter(i++, ctx.get("round"));
    	param.setValueParamter(i++, ctx.get("racerPerioNo"));
    	param.setValueParamter(i++, ctx.get("round"));
    	param.setValueParamter(i++, ctx.get("racerPerioNo"));
    	param.setValueParamter(i++, ctx.get("round"));
    	param.setValueParamter(i++, ctx.get("racerPerioNo"));
    	param.setValueParamter(i++, ctx.get("round"));
			 	
		dmlcount = this.getDao("candao").insert("tbdn_total_d06000021_in001", param);
     
        return dmlcount;
    }
    
    
    /**
     * <p>총괄평가표  삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		delete 레코드 개수
     * @throws  none
     */
    protected int deleteEstmItemTo(PosContext ctx) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setWhereClauseParameter(i++, ctx.get("racerPerioNo"));    
        param.setWhereClauseParameter(i++, ctx.get("round"));
        int dmlcount  = this.getDao("candao").update("tbdn_total_d06000021_dn001", param);
        	
        
        return dmlcount;
    }    
}



