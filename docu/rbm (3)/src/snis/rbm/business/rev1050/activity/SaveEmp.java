/*================================================================================
 * �ý���			: �򰡴������
 * �ҽ����� �̸�	: snis.rbm.business.rev1040.activity.SaveDistribution.java
 * ���ϼ���		: �򰡹��ǥ ����
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2011-09-14
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rev1050.activity;

import java.sql.Connection;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.business.rev1010.activity.SaveEVMistr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaveEmp extends SnisActivity {
		public SaveEmp(){}
		
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
	        int nTempCnt     = 0;
	        Connection conn = null;        
	        
	        conn = getDao("rbmdao").getDBConnection();
	        
	        String sEvalYear = (String)ctx.get("ESTM_YEAR");	//�򰡳⵵
	        String sEvalNum  = (String)ctx.get("ESTM_NUM");		//��ȸ��
	        
	        SaveEVMistr SaveEVMistr = new SaveEVMistr();

	        if( !SaveEVMistr.getUpdateYn(sEvalYear, sEvalNum).equals("Y") ) {
	        	try { 
	    			throw new Exception(); 
	        	} catch(Exception e) {       		
	        		this.rollbackTransaction("tx_rbm");
	        		Util.setSvcMsg(ctx, "�μ�������ڸ� Ȯ���� �μ��� �����ϹǷ� �����Ͻ� �� �����ϴ�.");
	        		
	        		return;
	        	}
	        }
	        
	        sDsName = "dsEmp";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        //nTempCnt = updateEmp(record);
				    //nSaveCount = nSaveCount + nTempCnt;
		        }
		        nSaveCount += UpdateAllEmp(conn, ctx, ds);
	        }
	        
	        
	        updateAprvInit(sEvalYear, sEvalNum, "2");	//�ٹ��µ��� �����ڵ� �ʱ�ȭ
	        updateAprvCompleted(sEvalYear, sEvalNum, "2", "2");	//�ٹ��µ��� ���� �ʴ� �μ��� �����ڵ带 ���οϷ�(003)���� ����
	        
	        updateAprvInit(sEvalYear, sEvalNum, "3");	//������ �����ڵ� �ʱ�ȭ
	        updateAprvCompleted(sEvalYear, sEvalNum, "3", "3"); //�ٹ��µ��� ���� �ʴ� �μ��� �����ڵ带 ���οϷ�(003)���� ����
	        
	        Util.setSaveCount(ctx, nSaveCount);
	    }

	    /**
	     * <p> �򰡴�� ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateEmp(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("ESTM_DEPT"));     // 1. �򰡺μ� 
	        param.setValueParamter(i++, record.getAttribute("ESTM_ESC_GBN"));  // 2. �򰡴�������ڱ���
	        param.setValueParamter(i++, record.getAttribute("APT_DT"));      // 3. �߷�����
	        param.setValueParamter(i++, record.getAttribute("AVG_IN")); 	 // 4. ���������
	        param.setValueParamter(i++, SESSION_USER_ID);                    // 5. ����� ID      
	  
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));  // 6. �򰡳⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));   // 7. ��ȸ��
	        param.setWhereClauseParameter(i++, record.getAttribute("EMP_NO"));     // 8. �����ȣ
	        param.setWhereClauseParameter(i++, record.getAttribute("WRK_GBN"));    // 9. ����ڱ���(001:�Ϲ��� 002:�İ���:

	        int dmlcount = this.getDao("rbmdao").update("rev1050_u01", param);
	        return dmlcount;
	    }

	    /**
	     * <p> �򰡴�� �ϰ� ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int UpdateAllEmp(Connection conn, PosContext ctx, PosDataset ds)  
	    {    	
    		int dmlcount = 0;
	        PreparedStatement stmt = null;
	        String sqlStr = Util.getQuery(ctx, "rev1050_u01");
	        PosParameter param = new PosParameter();   
	
	        try {
	        	int iparam = 1;
				stmt = conn.prepareStatement(sqlStr);
				for ( int i = 0; i < ds.getRecordCount(); i++ ) {
				    PosRecord record = ds.getRecord(i);
					stmt.clearParameters();	
					iparam = 1;				
					stmt.setString(iparam++,  record.getAttribute("ESTM_DEPT").toString());     // 1. �򰡺μ� 
			        stmt.setString(iparam++,  record.getAttribute("ESTM_ESC_GBN").toString());  // 2. �򰡴�������ڱ���
			        stmt.setString(iparam++,  record.getAttribute("APT_DT").toString());      // 3. �߷�����
			        stmt.setString(iparam++,  record.getAttribute("AVG_IN").toString()); 	 // 4. ���������
			        stmt.setString(iparam++,  SESSION_USER_ID);                    // 5. ����� ID      
			  
			        stmt.setString(iparam++,  record.getAttribute("ESTM_YEAR").toString());  // 6. �򰡳⵵
			        stmt.setString(iparam++,  record.getAttribute("ESTM_NUM").toString());   // 7. ��ȸ��
			        stmt.setString(iparam++,  record.getAttribute("EMP_NO").toString());     // 8. �����ȣ
			        stmt.setString(iparam++,  record.getAttribute("WRK_GBN").toString());    // 9. ����ڱ���(001:�Ϲ��� 002:�İ���:
			        
		    		stmt.addBatch();
				}
				stmt.executeBatch();
				dmlcount = stmt.getUpdateCount();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
	        return dmlcount;
	    }

	    /**
	     * <p> �򰡽����ڵ� �ʱ�ȭ </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateAprvInit(String sEvalYear, String sEvalNum, String sAprvSeq)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;

	        param.setValueParamter(i++, SESSION_USER_ID);   // 1. ����� ID      
	  
	        i = 0;
	        param.setWhereClauseParameter(i++, sEvalYear);  // 2. �򰡳⵵
	        param.setWhereClauseParameter(i++, sEvalNum);   // 3. ��ȸ��
	        param.setWhereClauseParameter(i++, sAprvSeq);   // 4. ���ι�ȣ

	        int dmlcount = this.getDao("rbmdao").update("rev1050_u03", param);
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �ٹ��µ���/������ ���� �ʴ� �μ��� �����ڵ带 ���οϷ�(003)���� ���� </p>
	     * @ sItemCd     2:�ٹ��µ���   3:������
	     */
	    protected int updateAprvCompleted(String sEvalYear, String sEvalNum, String sItemCd, String sAprvSeq)
	    {	
	    	PosParameter param  = new PosParameter();

	    	int i = 0;
	    	int j = 0;
	    	int nUpdateCnt = 0;
	        
	        param.setWhereClauseParameter(i++, sEvalYear);  // 1. �򰡳⵵
	        param.setWhereClauseParameter(i++, sEvalNum);   // 2. ��ȸ��
	        param.setWhereClauseParameter(i++, sEvalYear);  // 3. �򰡳⵵
	        param.setWhereClauseParameter(i++, sEvalNum);   // 4. ��ȸ��
	        param.setWhereClauseParameter(i++, sItemCd);    // 5. �򰡱���(1:���������� 2:�ٹ��µ��� 3:������)
	        
	    	PosRowSet pAprvDept = this.getDao("rbmdao").find("rev1050_s06", param);
	    	
	    	if( pAprvDept.count() == 0) return 0;
	    	
	    	PosRow pr[] = pAprvDept.getAllRow();
	    	
	    	for(int nRow = 0; nRow <  pAprvDept.count(); nRow++) {
	    		PosParameter param2 = new PosParameter();
	    		j = 0;
	    		param2.setValueParamter(j++, SESSION_USER_ID);
	    		
	    		j = 0;
	    		param2.setWhereClauseParameter(j++, sEvalYear);
	    		param2.setWhereClauseParameter(j++, sEvalNum);
	    		param2.setWhereClauseParameter(j++, String.valueOf(pr[nRow].getAttribute("DEPT_CD")));
	    		param2.setWhereClauseParameter(j++, sAprvSeq);
	    		
	    		nUpdateCnt += this.getDao("rbmdao").update("rev1050_u04", param2);
	        }
	    	
	    	return nUpdateCnt;
	    }
}