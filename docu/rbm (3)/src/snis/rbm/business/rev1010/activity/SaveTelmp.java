/*================================================================================
 * �ý���			: �߸Ž���
 * �ҽ����� �̸�	: snis.rbm.business.rev1010.activity.SaveTelmp.java
 * ���ϼ���		: �߸Ž���
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-10-19
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rev1010.activity;

import java.sql.Connection;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.business.rev1070.activity.*;
import snis.rbm.business.rev1010.activity.SaveData;

public class SaveTelmp extends SnisActivity {
	
	public SaveTelmp(){}

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
    	
    	PosDataset ds;
        int nSize        = 0;
        Connection conn = null;        
        
        conn = getDao("rbmdao").getDBConnection();
        
        String sEvalYear = (String)ctx.get("ESTM_YEAR");	//�򰡳⵵
        String sEvalNum  = (String)ctx.get("ESTM_NUM");		//��ȸ��

        SaveEmpEstm SaveEmpEstm = new SaveEmpEstm();
        
        //�򰡰��õǾ��ٸ� ���� �Ұ���
        if( SaveEmpEstm.getUpdateYn(sEvalYear, sEvalNum).equals("Y")) {
        	try { 
    			throw new Exception(); 
        	} catch(Exception e) {       		
        		this.rollbackTransaction("tx_rbm");
        		Util.setSvcMsg(ctx, "�� ���ð� ���۵Ǿ��� ������ ���� �Ұ����մϴ�.");
        		
        		return;
        	}
        }
        
        SaveData sd = new SaveData();
        sd.deleteTelmp(sEvalYear, sEvalNum);	//�߸Ž�������
        sd.deleteComm (sEvalYear, sEvalNum);	//��ǥ�һ���
        
        //�߸Ž��� Insert
        sDsName = "dsDwTelmp";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            record.setAttribute("ESTM_YEAR", sEvalYear);
	            record.setAttribute("ESTM_NUM" , sEvalNum);

		        //nSaveCount = nSaveCount + sd.insertTelmp(record); 
	        }	

	        nSaveCount += sd.InsertAllTelmp(conn, ctx, ds);
        }
        
        //��ǥ�� Insert
        sDsName = "dsDwComm";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            record.setAttribute("ESTM_YEAR", sEvalYear);
	            record.setAttribute("ESTM_NUM" , sEvalNum);

		        //nSaveCount = nSaveCount + sd.insertComm(record); 
	        }	  
	        nSaveCount = nSaveCount + sd.InsertAllComm(conn, ctx, ds);
        }
        
        sd.updateByPass(sEvalYear, sEvalNum); //�Ⱓ �߸Ž��� �����ڴ�  TOTAL_CNT = 99999, WK_DAY_CNT = 99999�� ����
        sd.updateTeamAvg(sEvalYear, sEvalNum); //�Ⱓ �߸Ž��� �������� ��� �߸ŰǼ��� ����� ����
        
        Util.setReturnParam(ctx, "RESULT", "0");
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
    }  
}