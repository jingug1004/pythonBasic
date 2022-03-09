/*================================================================================
 * �ý���			: �ٸ��򰡱׷����
 * �ҽ����� �̸�	: snis.rbm.business.rev1040.activity.SaveDistribution.java
 * ���ϼ���		: �ٸ��򰡱׷� ����
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2011-09-14
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rev1080.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.business.rev2010.activity.SavePrmRslt;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

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
	        String sEvalYear = (String)ctx.get("ESTM_YEAR");
	        String sEvalNum  = (String)ctx.get("ESTM_NUM");
	        String sGrant    = (String)ctx.get("GRANT");
	        String sTotGrpYN = (String)ctx.get("TOT_GRP_YN");
	        
	        //���ο�û ��, ���� �Ұ���
	        if( sGrant.equals("N") ) {	//REV1080(�ٸ��򰡱׷켱��)���� �Ѿ�Դٸ� 
		        if( getCfmYn(sEvalYear, sEvalNum).equals("N") ) {
		        	try { 
	        			throw new Exception(); 
	            	} catch(Exception e) {       		
	            		this.rollbackTransaction("tx_rbm");
	            		Util.setSvcMsg(ctx, "���ο�û�� ���� ������ �����Ͻ� �� �����ϴ�.");
	            		Util.setReturnParam(ctx, "RESULT", "F");
	            		return;
	            	}
		        }
	        }
	        
	        //�򰡸����� �Ǿ��ٸ� ���� �Ұ���
    		SavePrmRslt SavePrmRslt = new SavePrmRslt();
	        
	        if( SavePrmRslt.getEndYn(sEvalYear, sEvalNum).equals("Y") ) {
	        	try { 
        			throw new Exception(); 
            	} catch(Exception e) {       		
            		this.rollbackTransaction("tx_rbm");
            		Util.setReturnParam(ctx, "RESULT", "F");
            		Util.setSvcMsg(ctx, "�򰡸����� �Ϸ�Ǿ� �����Ͻ� �� �����ϴ�.");
            		
            		return;
            	}
	        }
	        
	        if ("Y".equals(sTotGrpYN)) {
		        sDsName = "dsDeptTotGrp";
		        if ( ctx.get(sDsName) != null ) {
			        ds   		 = (PosDataset) ctx.get(sDsName);
			        nSize 		 = ds.getRecordCount();
	
			        for ( int i = 0; i < nSize; i++ ) {
			            PosRecord record = ds.getRecord(i);
				        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
				        	nTempCnt = updateDeptTotGrp(record);
					        nSaveCount = nSaveCount + nTempCnt;
				        	continue;
				        }	        
			        }
		        }
	        	
	        } else {	        	
		        sDsName = "dsEmp";
	
		        if ( ctx.get(sDsName) != null ) {
			        ds   		 = (PosDataset) ctx.get(sDsName);
			        nSize 		 = ds.getRecordCount();
	
			        for ( int i = 0; i < nSize; i++ ) {
			            PosRecord record = ds.getRecord(i);
				        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
			        		nTempCnt = updateEmp(record);
					        nSaveCount = nSaveCount + nTempCnt;
				        	continue;
				        } 
			        }	        
		        }
	        }

	        Util.setReturnParam(ctx, "RESULT", "S");
	        Util.setSaveCount  (ctx, nSaveCount);
	    }

	    /**
	     * <p> �򰡹��ǥ ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateEmp(PosRecord record)
	    {			
	    	
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setValueParamter(i++, record.getAttribute("MULT_ESTM_GRP"));        // 1. �ٸ��򰡱׷�
	        param.setValueParamter(i++, SESSION_USER_ID);                             // 2. ����� ID      
	  
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));     // 3. �򰡳⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));      // 4. ��ȸ��
	        param.setWhereClauseParameter(i++, record.getAttribute("EMP_NO"));        // 5. �����ȣ
	        param.setWhereClauseParameter(i++, record.getAttribute("DEPT_CD"));      

	        int dmlcount = this.getDao("rbmdao").update("rev1080_u01", param);
	        return dmlcount;
	    }


	    /**
	     * <p> �򰡹��ǥ ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateDeptTotGrp(PosRecord record)
	    {			
	    	
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setValueParamter(i++, record.getAttribute("TOT_ESTM_GRP"));        // 1. �����򰡱׷�
	        param.setValueParamter(i++, SESSION_USER_ID);                             // 2. ����� ID      
	  
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));     // 3. �򰡳⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));      // 4. ��ȸ��
	        param.setWhereClauseParameter(i++, record.getAttribute("DEPT_CD"));      

	        int dmlcount = this.getDao("rbmdao").update("rev1080_u02", param);
	        return dmlcount;
	    }

	    /**
	     * <p> ���� ���� ��ȸ </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    public String getCfmYn(String sEvalYear, String sEvalNum) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        String rtnKey = "";
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev1080_s04", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        rtnKey = String.valueOf(pr[0].getAttribute("UPDATE_YN"));
	        
	        return rtnKey;
	    }
}
