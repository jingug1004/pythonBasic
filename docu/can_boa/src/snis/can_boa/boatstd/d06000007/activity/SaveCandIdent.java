
/*================================================================================
 * �ý���			: �ĺ��� �Ż� ����
 * �ҽ����� �̸�	: snis.can.system.d06000007.activity.SaveCandIdent.java
 * ���ϼ���		: �ĺ��� �Ż� ����
 * �ۼ���			: ���μ�
 * ����			: 1.0.0
 * ��������		: 2008-03-05
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can_boa.boatstd.d06000007.activity;

import java.text.SimpleDateFormat;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;

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
    	//����� ���� Ȯ��
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
     * <p> �ĺ��� �Ż�  �Է�  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertSaveCandIdent(PosRecord record) 
    {
   	    PosParameter param = new PosParameter();
        int i = 0;
        int dmlcount = 0;      
       	
        	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO"));
        	param.setValueParamter(i++, record.getAttribute("CAND_NO"));
	       	param.setValueParamter(i++, record.getAttribute("NM_KOR"));
	       	param.setValueParamter(i++, record.getAttribute("NM_CHN"));
	       	param.setValueParamter(i++, record.getAttribute("NM_ENG"));	       	
	       	param.setValueParamter(i++, record.getAttribute("BIRTH"));
	       	param.setValueParamter(i++, record.getAttribute("SEX"));
	       	param.setValueParamter(i++, record.getAttribute("PHONE1"));
	       	param.setValueParamter(i++, record.getAttribute("PHONE2"));
	       	param.setValueParamter(i++, record.getAttribute("PHONE3"));
	       	param.setValueParamter(i++, record.getAttribute("CELLPHONE1"));
	       	param.setValueParamter(i++, record.getAttribute("CELLPHONE2"));
	       	param.setValueParamter(i++, record.getAttribute("CELLPHONE3"));
	       	param.setValueParamter(i++, record.getAttribute("EMAIL"));
	       	param.setValueParamter(i++, record.getAttribute("FMLY_HEAD_NM_KOR"));
	       	param.setValueParamter(i++, record.getAttribute("FMLY_HEAD_RELN"));
	       	param.setValueParamter(i++, record.getAttribute("TH_STAT"));
	       	param.setValueParamter(i++, record.getAttribute("POST_NO"));	       	
	       	param.setValueParamter(i++, record.getAttribute("ADDR1"));
	       	param.setValueParamter(i++, record.getAttribute("ADDR2"));
	       	param.setValueParamter(i++, record.getAttribute("PERMNT_POST_NO"));
	       	param.setValueParamter(i++, record.getAttribute("PERMNT1"));
	       	param.setValueParamter(i++, record.getAttribute("PERMNT2"));	       	
	       	param.setValueParamter(i++, record.getAttribute("MRRG_YN"));
	       	param.setValueParamter(i++, record.getAttribute("MTARY_GBN"));
	       	param.setValueParamter(i++, record.getAttribute("ARMY_TPE"));
	       	param.setValueParamter(i++, record.getAttribute("BRNCH"));
	       	param.setValueParamter(i++, record.getAttribute("MLT_CLS"));	       	
	       	param.setValueParamter(i++, record.getAttribute("ARMY_NO"));
	       	param.setValueParamter(i++, record.getAttribute("UNFED_RSN"));
	       	param.setValueParamter(i++, record.getAttribute("ENTR_ARMY_DD"));
	       	param.setValueParamter(i++, record.getAttribute("LEV_ARMY_DD"));
	       	param.setValueParamter(i++, record.getAttribute("SVCE_TERM"));	       	
	       	param.setValueParamter(i++, record.getAttribute("HGHT"));
	       	param.setValueParamter(i++, record.getAttribute("WGHT"));
	       	param.setValueParamter(i++, record.getAttribute("SIGHT_LEFT"));
	       	param.setValueParamter(i++, record.getAttribute("SIGHT_RIGHT"));
	       	param.setValueParamter(i++, record.getAttribute("COLOR_BLIND"));	       	
	       	param.setValueParamter(i++, record.getAttribute("BLOD_TPE"));
	       	param.setValueParamter(i++, record.getAttribute("HEALTH_STAT"));
	       	param.setValueParamter(i++, record.getAttribute("SMOKE_YN"));
	       	param.setValueParamter(i++, record.getAttribute("DRINK"));
	       	param.setValueParamter(i++, record.getAttribute("RELGN"));	       	
	       	param.setValueParamter(i++, record.getAttribute("HOBY"));
	       	param.setValueParamter(i++, record.getAttribute("SPA_ABTY"));
	       	param.setValueParamter(i++, record.getAttribute("PRPT_MOVA"));
	       	param.setValueParamter(i++, record.getAttribute("PRPT_REAL"));
	       	param.setValueParamter(i++, record.getAttribute("HOUSE"));	       	
	       	param.setValueParamter(i++, record.getAttribute("SDE_JOB_NM"));
	       	param.setValueParamter(i++, record.getAttribute("SDE_JOB_MM_IM"));
	       	param.setValueParamter(i++, record.getAttribute("PROP_TOT_AMT"));
	       	param.setValueParamter(i++, record.getAttribute("GROUP_NM"));
	       	param.setValueParamter(i++, record.getAttribute("DUTY"));	       	
	       	param.setValueParamter(i++, record.getAttribute("ENT_DT"));
	       	param.setValueParamter(i++, record.getAttribute("LEV_DT"));
	       	param.setValueParamter(i++, record.getAttribute("PTO_FILE_VIEW_NM"));
	       	param.setValueParamter(i++, record.getAttribute("PTO_FILE_REAL_NM"));
			param.setValueParamter(i++, SESSION_USER_ID);
			 	
			dmlcount += this.getDao("candao").insert("d06000007_ib001", param);
     
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
 		
	       	param.setValueParamter(i++, record.getAttribute("NM_KOR"));
	       	param.setValueParamter(i++, record.getAttribute("NM_CHN"));
	       	param.setValueParamter(i++, record.getAttribute("NM_ENG"));
	       	param.setValueParamter(i++, record.getAttribute("BIRTH"));
	       	param.setValueParamter(i++, record.getAttribute("SEX"));
	    	param.setValueParamter(i++, record.getAttribute("PHONE1"));
	       	param.setValueParamter(i++, record.getAttribute("PHONE2"));
	       	param.setValueParamter(i++, record.getAttribute("PHONE3"));
	       	param.setValueParamter(i++, record.getAttribute("CELLPHONE1"));
	       	param.setValueParamter(i++, record.getAttribute("CELLPHONE2"));
	       	param.setValueParamter(i++, record.getAttribute("CELLPHONE3"));
	       	param.setValueParamter(i++, record.getAttribute("EMAIL"));
	       	param.setValueParamter(i++, record.getAttribute("FMLY_HEAD_NM_KOR"));
	       	param.setValueParamter(i++, record.getAttribute("FMLY_HEAD_RELN"));
	       	param.setValueParamter(i++, record.getAttribute("TH_STAT"));
	       	param.setValueParamter(i++, record.getAttribute("POST_NO"));
	       	param.setValueParamter(i++, record.getAttribute("ADDR1"));
	       	param.setValueParamter(i++, record.getAttribute("ADDR2"));
	       	param.setValueParamter(i++, record.getAttribute("PERMNT_POST_NO"));
	       	param.setValueParamter(i++, record.getAttribute("PERMNT1"));
	       	param.setValueParamter(i++, record.getAttribute("PERMNT2"));
	       	param.setValueParamter(i++, record.getAttribute("MRRG_YN"));
	       	param.setValueParamter(i++, record.getAttribute("MTARY_GBN"));
	       	param.setValueParamter(i++, record.getAttribute("ARMY_TPE"));
	       	param.setValueParamter(i++, record.getAttribute("BRNCH"));
	       	param.setValueParamter(i++, record.getAttribute("MLT_CLS"));
	       	param.setValueParamter(i++, record.getAttribute("ARMY_NO"));
	       	param.setValueParamter(i++, record.getAttribute("UNFED_RSN"));
	       	param.setValueParamter(i++, record.getAttribute("ENTR_ARMY_DD"));
	       	param.setValueParamter(i++, record.getAttribute("LEV_ARMY_DD"));
	       	param.setValueParamter(i++, record.getAttribute("SVCE_TERM"));
	       	param.setValueParamter(i++, record.getAttribute("HGHT"));
	       	param.setValueParamter(i++, record.getAttribute("WGHT"));
	       	param.setValueParamter(i++, record.getAttribute("SIGHT_LEFT"));
	       	param.setValueParamter(i++, record.getAttribute("SIGHT_RIGHT"));
	       	param.setValueParamter(i++, record.getAttribute("COLOR_BLIND"));
	       	param.setValueParamter(i++, record.getAttribute("BLOD_TPE"));
	       	param.setValueParamter(i++, record.getAttribute("HEALTH_STAT"));
	       	param.setValueParamter(i++, record.getAttribute("SMOKE_YN"));
	       	param.setValueParamter(i++, record.getAttribute("DRINK"));
	       	param.setValueParamter(i++, record.getAttribute("RELGN"));
	       	param.setValueParamter(i++, record.getAttribute("HOBY"));
	       	param.setValueParamter(i++, record.getAttribute("SPA_ABTY"));
	       	param.setValueParamter(i++, record.getAttribute("PRPT_MOVA"));
	       	param.setValueParamter(i++, record.getAttribute("PRPT_REAL"));
	       	param.setValueParamter(i++, record.getAttribute("HOUSE"));
	       	param.setValueParamter(i++, record.getAttribute("SDE_JOB_NM"));
	       	param.setValueParamter(i++, record.getAttribute("SDE_JOB_MM_IM"));
	       	param.setValueParamter(i++, record.getAttribute("PROP_TOT_AMT"));
	       	param.setValueParamter(i++, record.getAttribute("GROUP_NM"));
	       	param.setValueParamter(i++, record.getAttribute("DUTY"));
	       	param.setValueParamter(i++, record.getAttribute("ENT_DT"));
	       	param.setValueParamter(i++, record.getAttribute("LEV_DT"));
	       	param.setValueParamter(i++, record.getAttribute("PTO_FILE_VIEW_NM"));
	       	param.setValueParamter(i++, record.getAttribute("PTO_FILE_REAL_NM"));	
			param.setValueParamter(i++, SESSION_USER_ID);
			param.setValueParamter(i++, record.getAttribute("DEL_YN"));
        	
			i = 0; 
			
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
			
			dmlcount += this.getDao("candao").update("d06000007_ub001", param);    
       
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
        
        int dmlcount  = this.getDao("candao").delete("d06000007_db001", param);
        	
        
        return dmlcount;
    }    
}
