/*
 * ================================================================================
 * �ý��� : ���� 
 * �ҽ����� �̸� : snis.rbm.business.rem4040.activity.SavePeriod.java 
 * ���ϼ��� : ���������ֱ� ���� 
 * �ۼ��� : ���缱
 * ���� : 1.0.0 
 * �������� : 2012.12.29
 * ������������ :
 * ���������� : 
 * ������������ :
 * =================================================================================
 */

package snis.rbm.business.rem4040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class SavePeriod extends SnisActivity {

	public SavePeriod(){		
		
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
    	String ImportYn = "N";
    	ImportYn = (String) ctx.get("IMPORT_YN");
    	if ("Y".equals(ImportYn)) {
    		ImportPeriod(ctx);
    	} else {   	
    		saveState(ctx);
    	}

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

        sDsName = "dsPeriod";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	// ���������ֱ�  ����
	            	nDeleteCount = nDeleteCount + deletePeriod(record);
	            } else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {

		        	nTempCnt = updatePeriod(record);
		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }    
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    

    
    /**
     * <p> ���������ֱ�  ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updatePeriod(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("TRAN_DT"));		//��������
        param.setValueParamter(i++, record.getAttribute("TERM"));			//�����ֱ�
        param.setValueParamter(i++, record.getAttribute("START_TM"));		//���۽ð�
        param.setValueParamter(i++, record.getAttribute("END_TM"));			//����ð�
        param.setValueParamter(i++, record.getAttribute("RMK"));			//���
        param.setValueParamter(i++, SESSION_USER_ID);						//������

        int dmlcount = this.getDao("tmoneydao").update("rem4040_m01", param);
        return dmlcount;
    }


    
    /**
     * <p> ���������ֱ�  ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int deletePeriod(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("TRAN_DT" ));		//��������

        int dmlcount = this.getDao("tmoneydao").update("rem4040_d01", param);
        return dmlcount;
    }


    /**
    * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
    * @param   ctx		PosContext	�����
    * @return  none
     * @throws SQLException 
    * @throws  none
    */
    protected void ImportPeriod(PosContext ctx) 
    {
    	Connection conn = null; 
    	CallableStatement proc =  null;

        try {
        	conn = this.getDao("rbmdao").getDBConnection();
        	proc = conn.prepareCall("{call SP_IMPORT_TMONEY_TRADE}");
        	proc.setQueryTimeout(120);
        	proc.execute();
        	//proc.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}
