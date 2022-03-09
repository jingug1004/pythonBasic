/*================================================================================
 * �ý���			: �����¼��� ����
 * �ҽ����� �̸�	: snis.rbm.business.rem5040.activity.SaveUnGoodStor.java
 * ���ϼ���		: �����¼��� ��ǰ ��� ����
 * �ۼ���			: ���缱
 * ����			: 1.0.0
 * ��������		: 2013-08-16
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rem5040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;


public class SaveUnStorStat extends SnisActivity {
	
	public SaveUnStorStat(){
		
		
	}

	
	
	/**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
   	
    	// ����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
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
    	String sUnStorDt = "";
        String StatCd    = "";
        String OldStatCd = "";
        String BrncCd    = "";
        String sAprvId   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        sDsName = "dsGoodUnStor";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        sUnStorDt    = (String)ctx.get("UNSTOR_DT");
	        OldStatCd    = (String)ctx.get("OLD_STAT_CD");
	        StatCd       = (String)ctx.get("NEW_STAT_CD");
	        String brncCd = (String)ctx.get("BRNC_CD");
	        String teamCd = ctx.get("TEAM_CD").toString().substring(2);
	        
			if ("002".equals(StatCd)) {
		        PosParameter param = new PosParameter();
	        	PosRowSet rowset;
	    		int i = 0;            
	    		
	    		param.setWhereClauseParameter(i++, teamCd);			//���ڵ�            
	    		rowset = this.getDao("rbmdao").find("rbs6020_s03", param);
	    		if (rowset.hasNext()) {				// ������ ������ ��ȸ(�� �μ�(��,��)�� �μ���)
	    			PosRow row = rowset.next();
					sAprvId = (String)row.getAttribute("CAPTAIN_ID");
				} else {
	    			Util.setSvcMsgCode(ctx, "SNIS_RBM_E027");
	    			return;
	    		}	    		
    		}
    		
    		/*
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);  

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
	        		nSaveCount += updateUnGoodStorStat(record, sUnStorDt, StatCd, sAprvId);
		        }		        
	        }
	        */
			updateUnGoodStorStat(brncCd, sUnStorDt, StatCd, sAprvId);
        }

        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }


    /**
     * <p> IP �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateUnGoodStorStat(String brncCd, String UnStorDt, String StatCd, String AprvId) 
    {	
    	PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, StatCd);								//����
        param.setValueParamter(i++, StatCd);								//������ ���
    	param.setValueParamter(i++, AprvId);								//������ ���
        param.setValueParamter(i++, StatCd);								//������ ���
        param.setValueParamter(i++, SESSION_USER_ID);						//�Է���/������ ���
        param.setValueParamter(i++, brncCd);		//�����ڵ�
        //param.setValueParamter(i++, record.getAttribute("STOR_DT"));		//�԰�����
        //param.setValueParamter(i++, record.getAttribute("SEQ_NO"));			//����
        param.setValueParamter(i++, UnStorDt);								//�������
        
        int dmlcount = this.getDao("rbmdao").update("rem5040_u02", param);
        
        return dmlcount;
    }
    
    
    
}
