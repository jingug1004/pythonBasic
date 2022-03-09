
/*================================================================================
 * �ý���			: �ĺ��� �Ż� ����
 * �ҽ����� �̸�	: snis.can.system.d02000009.activity.SaveCandIdent.java
 * ���ϼ���		: �ĺ��� �Ż� ����
 * �ۼ���			: ���μ�
 * ����			: 1.0.0
 * ��������		: 2008-02-25
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.cyclestd.d02000009.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �ĺ��� �Ż� ������ ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ���μ�
* @version 1.0
*/
public class SaveCandIdent  extends SnisActivity
{    
	
    public SaveCandIdent()
    {
    }

    /**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
   	
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}  
        PosDataset ds;
        int        nSize        = 0;
        String     sDsName      = "";
        
        sDsName = "dsCandIdent";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo("dsCandIdent============>"+record);
	        }
        }
	        
        saveState(ctx);
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
    * @param   ctx		PosContext	�����
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
        sDsName = "dsCandIdent";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        PosDataset ptoDs = (PosDataset) ctx.get("dsUploadFile");

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
	            	nTempCnt = updateSaveCandIdent(record);
	            }
	            else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {
	                try {
	                	nTempCnt = insertSaveCandIdent(record);
	                } catch ( Exception e ) {
	                	Util.setSvcMsg(ctx, e.getMessage());
	                	return;
	                }
               }
	            else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
	                      // delete
              	 nDeleteCount = nDeleteCount + deleteSaveCandIdent(record);
               }      
	            nSaveCount += nTempCnt;
	           		         
	        } // end for
        }     // end if

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    
    /**
     * <p> ������ �Ż�  �Է�  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertSaveCandIdent(PosRecord record) 
    {
   	    logger.logInfo("==========================  �ĺ��� �Ż�   �Է�   ============================");        
        PosParameter param = new PosParameter();
        int i = 0;
        int dmlcount = 0;      
       
       
        	logger.logInfo("CandIdent======>");
        	
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("NM_KOR")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("NM_CHN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("RES_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("BIRTH_DT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("LUNSOL")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("FMLY_HEAD_NM_KOR")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("FMLY_HEAD_NM_CHN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("FMLY_HEAD_RELN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("TEL_HOME1")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("TEL_HOME2")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("TEL_HOME3")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("CELPON_1")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("CELPON_2")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("CELPON_3")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("LIFE_BASE")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("HOBY")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SPA_ABTY")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("RELGN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("MTARY_GBN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ARMY_GBN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ADDR1")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ADDR2")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("POST_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("PERMNT1")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("PERMNT2")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("PERMNT_POST_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("NATY_TEAM")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("BUS_TEAM")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("DRINK")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SMOKE")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("GRDU_GBN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("LEAVE_DT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("LEAVE_RSN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("HGHT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("WGHT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SIT_HGHT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("BREAST_SIZE")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("BELLY_CIR")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("HEAP_SIZE")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("FEM_RIGHT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("FEM_LEFT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("LEG_RIGHT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("LEG_LEFT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("GLS_YN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SIGHT_LEFT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SIGHT_RIGHT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("BLOD_TPE")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_TPE")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("MAIN_ITEM")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("STRATEGY")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("LEG_TPE")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SUMM_PLC")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("WIN_PLC")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("TRNG_MAN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("STNT_OFFC_ER")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ROOM_CHIEF_YN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("PTO_FILE")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("PTO_URL")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("EMAIL")));
			param.setValueParamter(i++, SESSION_USER_ID);
			 	
			dmlcount += this.getDao("candao").insert("d02000009_ib001", param);
     
        return dmlcount;
    }
    
    
    /**
     * <p> �ĺ��� �Ż�  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateSaveCandIdent(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;        
        int dmlcount = 0;
 		
            logger.logInfo("CandIdent ������Ʈ======>");        	
        
        	param.setValueParamter(i++, Util.trim(record.getAttribute("NM_KOR")));
        	param.setValueParamter(i++, Util.trim(record.getAttribute("NM_CHN")));
        	param.setValueParamter(i++, Util.trim(record.getAttribute("RES_NO")));
        	param.setValueParamter(i++, Util.trim(record.getAttribute("BIRTH_DT")));
        	param.setValueParamter(i++, Util.trim(record.getAttribute("LUNSOL")));
        	param.setValueParamter(i++, Util.trim(record.getAttribute("FMLY_HEAD_NM_KOR")));
        	param.setValueParamter(i++, Util.trim(record.getAttribute("FMLY_HEAD_NM_CHN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("FMLY_HEAD_RELN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("TEL_HOME1")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("TEL_HOME2")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("TEL_HOME3")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("CELPON_1")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("CELPON_2")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("CELPON_3")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("LIFE_BASE")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("HOBY")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SPA_ABTY")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("RELGN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("MTARY_GBN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ARMY_GBN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ADDR1")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ADDR2")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("POST_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("PERMNT1")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("PERMNT2")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("PERMNT_POST_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("NATY_TEAM")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("BUS_TEAM")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("TRNG_PLC")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("DRINK")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SMOKE")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("GRDU_GBN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("LEAVE_DT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("LEAVE_RSN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("HGHT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("WGHT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SIT_HGHT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("BREAST_SIZE")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("BELLY_CIR")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("HEAP_SIZE")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("FEM_RIGHT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("FEM_LEFT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("LEG_RIGHT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("LEG_LEFT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("GLS_YN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SIGHT_LEFT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SIGHT_RIGHT")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("BLOD_TPE")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_TPE")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("MAIN_ITEM")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("STRATEGY")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("LEG_TPE")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SUMM_PLC")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("WIN_PLC")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("TRNG_MAN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("STNT_OFFC_ER")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ROOM_CHIEF_YN")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("PTO_FILE")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("PTO_URL")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("EMAIL")));
			param.setValueParamter(i++, SESSION_USER_ID);	
			param.setValueParamter(i++, Util.trim(record.getAttribute("DEL_YN")));
        	
			i = 0; 
			
			param.setWhereClauseParameter(i++, record.getAttribute("RACER_PERIO_NO"));
			param.setWhereClauseParameter(i++, record.getAttribute("CAND_NO"));
			
			dmlcount += this.getDao("candao").update("d02000009_ub001", param);    
       
        return dmlcount;
    }

    
    
    /**
     * <p> �ĺ��� �Ż�  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteSaveCandIdent(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACER_PERIO_NO" ));
        param.setWhereClauseParameter(i++, record.getAttribute("CAND_NO" ));
        
        int dmlcount  = this.getDao("candao").delete("d02000009_db001", param);
        	
        
        return dmlcount;
    }    
}