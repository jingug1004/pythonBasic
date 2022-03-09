/*================================================================================
 * �ý���			: �򰡹��ǥ   ��
 * �ҽ����� �̸�	: snis.rbm.business.rev1040.activity.SaveDistribution.java
 * ���ϼ���		: �򰡹��ǥ ����
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2011-09-14
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rev2010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SavePrmRslt extends SnisActivity {
		public SavePrmRslt(){}
		
		
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
	    	
	    	String sDsNamePrm   = "";
	    	String sDsNameEmpRpm = "";
	    	
	    	PosDataset ds1;
	    	PosDataset ds2;
	    	
	    	int nSizePrm        = 0;
	    	int nSizeEmpRpm        = 0;
	        int nTempCnt     = 0;
	        	        
	        String sEvalYear  = (String)ctx.get("ESTM_YEAR");
	        String sEvalNum   = (String)ctx.get("ESTM_NUM");
	        String sEstmDept  = (String)ctx.get("ESTM_DEPT");
	        String sEstmEmp   = (String)ctx.get("ESTM_EMP");
	        String sFstSndGbn = (String)ctx.get("FST_SND_GBN");
	        
	        if( !getStartYn(sEvalYear, sEvalNum).equals("Y") ) {
	        	try { 
        			throw new Exception(); 
            	} catch(Exception e) {       		
            		this.rollbackTransaction("tx_rbm");
            		Util.setSvcMsg(ctx, "�򰡰��ð� ���� �ʾ� �����Ͻ� �� �����ϴ�.");
            		
            		return;
            	}
	        }
	        
	        if( getEndYn(sEvalYear, sEvalNum).equals("Y") ) {
	        	try { 
        			throw new Exception(); 
            	} catch(Exception e) {       		
            		this.rollbackTransaction("tx_rbm");
            		Util.setSvcMsg(ctx, "�򰡸����� �Ϸ�Ǿ� �����Ͻ� �� �����ϴ�.");
            		
            		return;
            	}
	        }
	        
	        // ����������
	        for (int dcnt=1;dcnt<=5;dcnt++) {
		        sDsNamePrm = "dsPrmDt_" + String.valueOf(dcnt);
		        if ( ctx.get(sDsNamePrm) != null ) {
			        ds1   		 = (PosDataset) ctx.get(sDsNamePrm);
			        nSizePrm 	 = ds1.getRecordCount();
	
			        for ( int i = 0; i < nSizePrm; i++ ) {
			            PosRecord recordPrmDt = ds1.getRecord(i);
	
				        if ( recordPrmDt.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
				        	
			        		 updatePrmDt(recordPrmDt);
			        		 updatePrmType(recordPrmDt);
				        	 continue;
				        } 
			        }	        
		        }
		    }
	        //���κ� �ջ� �ڷ� ����
	        sDsNamePrm = "dsPrmDt";
	        if ( ctx.get(sDsNamePrm) != null ) {
		        ds1   		 = (PosDataset) ctx.get(sDsNamePrm);
		        nSizePrm 	 = ds1.getRecordCount();

		        for ( int i = 0; i < nSizePrm; i++ ) {
		            PosRecord recordPrmDt = ds1.getRecord(i);
			        if ( recordPrmDt.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {			        	
				        updatePrm(recordPrmDt, sEstmEmp);
			        } 
		        }	        
	        }
	        
	        
	        // �߸Ž��� 2016.11.05 �߸Ž����� ���� ����⿩���� �ϰ��������� ����
	        /*
	        sDsNameEmpRpm = "dsEmpRpm";
	        if ( ctx.get(sDsNameEmpRpm) != null ) {
		        ds2   		 = (PosDataset) ctx.get(sDsNameEmpRpm);
		        nSizeEmpRpm 		 = ds2.getRecordCount();

		        for ( int i = 0; i < nSizeEmpRpm; i++ ) {
		            PosRecord recordEmpRpm = ds2.getRecord(i);
			        updateEmpRpm(recordEmpRpm);
		        }	        
	        }
	        */
	        Util.setSaveCount  (ctx, nSaveCount     );
	    }
	    
	    /**
	     * <p> �򰡹��ǥ ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected void updatePrmDt(PosRecord recordPrmDt)
	    {			
			if( ((String)recordPrmDt.getAttribute("ESTM_LITEM_CD_1")).length() > 0)
			{
				updatePrmDt1(recordPrmDt);
				if( ((String)recordPrmDt.getAttribute("ESTM_LITEM_CD_2")).length() > 0 )
				{
					updatePrmDt2(recordPrmDt);
					if( ((String)recordPrmDt.getAttribute("ESTM_LITEM_CD_3")).length() > 0 )
					{
						updatePrmDt3(recordPrmDt);
						if( ((String)recordPrmDt.getAttribute("ESTM_LITEM_CD_4")).length() > 0 )
						{
							updatePrmDt4(recordPrmDt);
							if( ((String)recordPrmDt.getAttribute("ESTM_LITEM_CD_5")).length() > 0)
							{
								updatePrmDt5(recordPrmDt);
							}
						}
					}
				}
			}
	    }

	    protected int updatePrmDt1(PosRecord recordPrmDt)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setValueParamter(i++, recordPrmDt.getAttribute("S_GRD_1"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("A_GRD_1"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("B_GRD_1"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("C_GRD_1"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("D_GRD_1"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, SESSION_USER_ID);
	        		
	        i = 0;
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("ESTM_NUM"));     
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("ESTM_DEPT"));      
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("ESTM_EMP"));      
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("EMP_NO"));      
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("ESTM_LITEM_CD_1"));   
	        
	        int dmlcount = this.getDao("rbmdao").update("rev2010_u01", param);
	        return dmlcount;
	    }
	    
	    protected int updatePrmDt2(PosRecord recordPrmDt)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setValueParamter(i++, recordPrmDt.getAttribute("S_GRD_2"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("A_GRD_2"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("B_GRD_2"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("C_GRD_2"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("D_GRD_2"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, SESSION_USER_ID);
	        
	        i = 0;
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("ESTM_NUM"));     
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("ESTM_DEPT"));      
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("ESTM_EMP"));      
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("EMP_NO"));      
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("ESTM_LITEM_CD_2"));   
	        
	        int dmlcount = this.getDao("rbmdao").update("rev2010_u01", param);
	        return dmlcount;
	    }
	    
	    protected int updatePrmDt3(PosRecord recordPrmDt)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setValueParamter(i++, recordPrmDt.getAttribute("S_GRD_3"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("A_GRD_3"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("B_GRD_3"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("C_GRD_3"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("D_GRD_3"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, SESSION_USER_ID);
	  
	        i = 0;
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("ESTM_NUM"));     
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("ESTM_DEPT"));      
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("ESTM_EMP"));      
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("EMP_NO"));      
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("ESTM_LITEM_CD_3"));   
	        
	        int dmlcount = this.getDao("rbmdao").update("rev2010_u01", param);
	        return dmlcount;
	    }
	    
	    protected int updatePrmDt4(PosRecord recordPrmDt)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setValueParamter(i++, recordPrmDt.getAttribute("S_GRD_4"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("A_GRD_4"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("B_GRD_4"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("C_GRD_4"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("D_GRD_4"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, SESSION_USER_ID);
	        
	        i = 0;
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("ESTM_NUM"));     
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("ESTM_DEPT"));      
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("ESTM_EMP"));      
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("EMP_NO"));      
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("ESTM_LITEM_CD_4"));   
	        
	        int dmlcount = this.getDao("rbmdao").update("rev2010_u01", param);
	        return dmlcount;
	    }
	    
	    protected int updatePrmDt5(PosRecord recordPrmDt)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setValueParamter(i++, recordPrmDt.getAttribute("S_GRD_5"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("A_GRD_5"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("B_GRD_5"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("C_GRD_5"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("D_GRD_5"));
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, SESSION_USER_ID);
	        
	        i = 0;
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("ESTM_YEAR"));     
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("ESTM_NUM"));     
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("ESTM_DEPT"));      
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("ESTM_EMP"));      
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("EMP_NO"));      
	        param.setWhereClauseParameter(i++, recordPrmDt.getAttribute("ESTM_LITEM_CD_5"));   
	        
	        int dmlcount = this.getDao("rbmdao").update("rev2010_u01", param);
	        return dmlcount;
	    }

	    protected int updatePrmType(PosRecord recordPrmDt)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_YEAR")); 
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_NUM")); 
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_DEPT")); 
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_EMP")); 
	        param.setValueParamter(i++, recordPrmDt.getAttribute("EMP_NO")); 
	        param.setValueParamter(i++, recordPrmDt.getAttribute("ESTM_LITEM_TYPE")); 
	        param.setValueParamter(i++, recordPrmDt.getAttribute("DISTR_CD")); 
	        param.setValueParamter(i++, recordPrmDt.getAttribute("RATE")); 
	        param.setValueParamter(i++, recordPrmDt.getAttribute("FST_SND_GBN"));
	        param.setValueParamter(i++, SESSION_USER_ID);
	        
	        int dmlcount = 0;
	        
	        dmlcount = this.getDao("rbmdao").update("rev2010_u05", param);
	        
	        
	        return dmlcount;
	    }
	    
	    protected int updatePrm(PosRecord recordPrm, String sEstmEmp)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setValueParamter(i++, recordPrm.getAttribute("DISTR_CD")); 
	        param.setValueParamter(i++, recordPrm.getAttribute("ESTM_YEAR"));     
	        param.setValueParamter(i++, recordPrm.getAttribute("ESTM_NUM"));     
	        param.setValueParamter(i++, recordPrm.getAttribute("ESTM_DEPT"));      
	        param.setValueParamter(i++, recordPrm.getAttribute("EMP_NO"));
	        param.setValueParamter(i++, sEstmEmp);
	        param.setValueParamter(i++, SESSION_USER_ID);
	        
	        i = 0;
	        param.setWhereClauseParameter(i++, recordPrm.getAttribute("ESTM_YEAR"));     
	        param.setWhereClauseParameter(i++, recordPrm.getAttribute("ESTM_NUM"));     
	        param.setWhereClauseParameter(i++, recordPrm.getAttribute("ESTM_DEPT"));      
	        param.setWhereClauseParameter(i++, recordPrm.getAttribute("EMP_NO"));
	        param.setWhereClauseParameter(i++, sEstmEmp);
	        	        
	        int dmlcount = 0;
	        
	        if (recordPrm.getAttribute("FST_SND_GBN").equals("001"))
	        {
	        	dmlcount = this.getDao("rbmdao").update("rev2010_u02", param);
	        	
	        } else if (recordPrm.getAttribute("FST_SND_GBN").equals("002")) {
	        	dmlcount = this.getDao("rbmdao").update("rev2010_u03", param);
	        }
	        
	        return dmlcount;
	    }
	    
	    /*
	     * 2016.11.05 �߸Ž����� �μ�������⿩���� ����, ���� ���� ������ ���̻� ������� ����
	     */
	    protected int updateEmpRpm(PosRecord recordEmpRpm)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setValueParamter(i++, recordEmpRpm.getAttribute("GRD"));
	        param.setValueParamter(i++, recordEmpRpm.getAttribute("RELEA_RATE"));
	        param.setValueParamter(i++, recordEmpRpm.getAttribute("RELEA_DISTR")); 
	        param.setValueParamter(i++, SESSION_USER_ID);
	        
	        i = 0;
	        param.setWhereClauseParameter(i++, recordEmpRpm.getAttribute("ESTM_YEAR"));     
	        param.setWhereClauseParameter(i++, recordEmpRpm.getAttribute("ESTM_NUM"));     
	        param.setWhereClauseParameter(i++, recordEmpRpm.getAttribute("ESTM_DEPT"));      
	        param.setWhereClauseParameter(i++, recordEmpRpm.getAttribute("EMP_NO"));    
	        param.setWhereClauseParameter(i++, recordEmpRpm.getAttribute("ESTM_YEAR"));     
	        param.setWhereClauseParameter(i++, recordEmpRpm.getAttribute("ESTM_NUM"));     
	        
	        int dmlcount = this.getDao("rbmdao").update("rev2010_u04", param);
	        	
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �򰡰��� ���� ��ȸ </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    public String getStartYn(String sEvalYear, String sEvalNum) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        String rtnKey = "";
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev2010_s13", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        rtnKey = String.valueOf(pr[0].getAttribute("ESTM_OPN_YN"));
	        
	        return rtnKey;
	    }
	    
	    /**
	     * <p> �򰡸��� ���� ��ȸ </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    public String getEndYn(String sEvalYear, String sEvalNum) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        String rtnKey = "";
	        
	        param.setWhereClauseParameter(i++, sEvalYear);
	        param.setWhereClauseParameter(i++, sEvalNum);
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev2010_s12", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        rtnKey = String.valueOf(pr[0].getAttribute("ESTM_END_YN"));
	        
	        return rtnKey;
	    }
}
