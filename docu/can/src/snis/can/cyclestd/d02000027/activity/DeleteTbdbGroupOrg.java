
/*================================================================================
 * 시스템			: 경륜경주기록측정마스타 관리
 * 소스파일 이름	: snis.can.system.d02000032.activity.SaveTbdbRaceRecMst.java
 * 파일설명		: 경륜경주기록측정마스타 관리
 * 작성자			: 임지헌
 * 버전			: 1.0.0
 * 생성일자		: 2008-03-21 
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/


package snis.can.cyclestd.d02000027.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 경륜경주기록측정마스타 내역을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 임지헌
* @version 1.0
*/
public class DeleteTbdbGroupOrg  extends SnisActivity
{    
	
    public DeleteTbdbGroupOrg()
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
    	
        PosDataset ds;
        int        nSize   = 0;
        String     sDsName = "";

	        sDsName = "dsTbdbGroupOrg1" ;
	    	
	        if ( ctx.get(sDsName) != null ) {
		        ds    = (PosDataset)ctx.get(sDsName);
		        nSize = ds.getRecordCount();
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
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

	        sDsName = "dsTbdbGroupOrg1";
	    	
	        if ( ctx.get(sDsName) != null ) {
		        ds    = (PosDataset) ctx.get(sDsName);
		        nSize = ds.getRecordCount();

	            PosRecord record = ds.getRecord(0);
            	nTempCnt = deleteTbdbRaceRecMst(record);
            	nTempCnt = deleteTbdbRaceRecDetl(record);
              
		        nSaveCount = nSaveCount + nTempCnt;
	        }
    

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    
    /**
     * <p> 경륜경주기록측정마스타  입력  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int deleteTbdbRaceRecMst(PosRecord record) 
    {
   	    logger.logInfo("==========================  경륜경주기록측정마스타   삭제   ============================");        
        PosParameter param = new PosParameter();
        int i = 0;
        int dmlcount = 0;      
       
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
	        param.setValueParamter(i++, record.getAttribute("ROUND"));
	        			 	
			dmlcount += this.getDao("candao").insert("tbdb_group_org_020", param);
     
        return dmlcount;
    } 
    

    /**
     * <p> 경륜경주기록측정Detail  입력  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int deleteTbdbRaceRecDetl(PosRecord record) 
    {
   	    logger.logInfo("==========================  경륜경주기록측정Detail   삭제   ============================");        
        PosParameter param = new PosParameter();
        int i = 0;
        int dmlcount = 0;      
        	
        	param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
	        param.setValueParamter(i++, record.getAttribute("ROUND"));
			 	
			dmlcount += this.getDao("candao").insert("tbdb_group_org_021", param);
     
        return dmlcount;
    }
    
    
}
