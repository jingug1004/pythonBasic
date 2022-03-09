/*
 * ================================================================================
 * �ý��� : �߸ſ� ����ó��
 * ���� �̸� : snis.rbm.business.rsm9010.activity.SaveTeller.java 
 * ���ϼ��� :  
 * �ۼ��� : 
 * ���� : 1.0.0 �������� : 2011- 12 - 17
 * ������������ : 
 * ���������� : 
 * ������������ :
 * =================================================================================
 */
package snis.rbm.business.rsm9010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;


public class SaveTeller extends SnisActivity {
	
	public SaveTeller(){}

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
    	int nSize        = 0;
        
    	String sDsName   = "";
    	PosDataset ds;
        

    	String sLevel    = (String)ctx.get("LEVEL");
    	String sAprvStat = (String)ctx.get("APRV_STAT");
    	
    	sDsName = "dsPcTeller";
    	
    	if ( ctx.get(sDsName) != null ) {
    		ds    = (PosDataset) ctx.get(sDsName);
        	nSize = ds.getRecordCount();
    	} else {
    		return;
    	}
    	
    	// LEVEL = 1�� ������
    	if( sLevel.equals("1") ) {
    		if( sAprvStat.equals("ARPV")) {	//����(����� ����) 
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
		            
		            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE  ) {
		            	//deletePcTellerAprv(record);
		            	deletePcTellerAprvFlag(record);
		            }
		            
		            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE  ) {
		            	//Check �� ��쿡�� ó��
		            	if( record.getAttribute("CHK").equals("1") ) {
		            		if( getTellerCnt(record) == 0) {
		            			updatePcTellerAprv(record);
		            		} else {
		            			//�ߺ�Ű �߻�
		            			try { 
		        	    			throw new Exception(); 
		        	        	} catch(Exception e) {       		
		        	        		this.rollbackTransaction("tx_rbm");
		        	        		Util.setSvcMsg(ctx, "������ ����,TELLER_ID, ����� ���� �����Ͱ� �����մϴ�.");
		        	        		
		        	        		Util.setReturnParam(ctx, "RESULT", "F");
		        	        		return;
		        	        	}
		            		}
		            	}	//CHK if �� ��
			        }	//UPDATE if�� ���� 
	            }	//for�� ����
    		} else if(sAprvStat.equals("CANCEL")){ //�ݷ�
    			for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
		            
		            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE  ) {
		            	//Check �� ��쿡�� ó��
		            	if( record.getAttribute("CHK").equals("1") ) {
		            		if( getTellerCnt(record) == 0) {
		            			updatePcTellerCancel(record);
		            		} 
		            	}
			        }
	            }
    		}
    	}

    	// LEVEL = 2�� ��û��
    	if( sLevel.equals("2")) {	//���ο�û
    		if ( ctx.get(sDsName) != null ) {
            	ds    = (PosDataset) ctx.get(sDsName);
            	nSize = ds.getRecordCount();
    	        
    	        for ( int i = 0; i < nSize; i++ ) {
    	            PosRecord record = ds.getRecord(i);
    	            
    	    		if( sAprvStat.equals("RECORM")) {	//Ȯ��
    	    			updatePcTellerReConfirm(record);
    	    		} else {
	    	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT  ) {
	    	            	insertPcTellerReq(record);
	    	            }
	    	            
	    	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE  ) {
	    	            	updatePcTellerReq(record);
	    		        }
    	    		}
    	        }	 
            }
    	}
    	
    	Util.setReturnParam(ctx, "RESULT", "S");
        Util.setSaveCount  (ctx, nSaveCount);
    }
    
    /**
     * <p> �߸ſ� �߰� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertPcTellerReq(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("COMM_NO"));		//1.�߸ſ��Ҽ�
        param.setValueParamter(i++, record.getAttribute("EMP_NO"));			//2.���
        param.setValueParamter(i++, record.getAttribute("TELLER_NM"));		//3.����
        param.setValueParamter(i++, record.getAttribute("SELL_TYPE_CD"));	//4.��ǥ����
        param.setValueParamter(i++, SESSION_USER_ID);						//5.���ο�û��
        param.setValueParamter(i++, record.getAttribute("PROC_GBN"));		//6.ó������
        param.setValueParamter(i++, record.getAttribute("TELLER_GBN"));		//7.�߸ſ�����
        param.setValueParamter(i++, record.getAttribute("ETC"));			//8.���
        param.setValueParamter(i++, SESSION_USER_ID);						//9.�ۼ���
					
        int dmlcount = this.getDao("rbmdao").update("rsm9010_i01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> �߸ſ� ����(���ο�û) </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updatePcTellerReq(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("PROC_GBN"));	//ó������
        param.setValueParamter(i++, record.getAttribute("ETC"));		//���
        param.setValueParamter(i++, SESSION_USER_ID);					//���ο�û��
        param.setValueParamter(i++, SESSION_USER_ID);					//������
        i = 0;	
        param.setWhereClauseParameter(i++, record.getAttribute("COMM_NO"));	
        param.setWhereClauseParameter(i++, record.getAttribute("TELLER_ID"));	
        param.setWhereClauseParameter(i++, record.getAttribute("EMP_NO"));						
      
        int dmlcount = this.getDao("rbmdao").update("rsm9010_u01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> ��û�� Ȯ�� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updatePcTellerReConfirm(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, SESSION_USER_ID);					//������
        i = 0;	
        param.setWhereClauseParameter(i++, record.getAttribute("COMM_NO"));	
        param.setWhereClauseParameter(i++, record.getAttribute("TELLER_ID"));	
        param.setWhereClauseParameter(i++, record.getAttribute("EMP_NO"));						
      
        int dmlcount = this.getDao("rbmdao").update("rsm9010_u05", param);
        
        return dmlcount;
    }    
    /**
     * <p> �߸ſ� ����(����) </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updatePcTellerAprv(PosRecord record) 
    {
        PosParameter param = new PosParameter();   

        if ("004".equals(record.getAttribute("PROC_GBN"))) {		// ó�����ΰ� ������ ���
        	int delcount = deletePcTellerAprvFlag(record);
        	return delcount;
        }
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));		//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("TELLER_ID"));		//�߸ſ�ID
        param.setValueParamter(i++, record.getAttribute("EMP_NO"));			//���
        param.setValueParamter(i++, record.getAttribute("TELLER_NM"));		//����
        param.setValueParamter(i++, record.getAttribute("SELL_TYPE_CD"));	//��ǥ����
        param.setValueParamter(i++, record.getAttribute("TELLER_GBN"));		//�߸ſ�����
        param.setValueParamter(i++, record.getAttribute("ETC"));			//���
        param.setValueParamter(i++, record.getAttribute("PASSWD"));			//���
        param.setValueParamter(i++, SESSION_USER_ID);						//������
        param.setValueParamter(i++, SESSION_USER_ID);						//������ID
        
        i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("ORG_COMM_NO"));	
        param.setWhereClauseParameter(i++, record.getAttribute("ORG_TELLER_ID"));	
        param.setWhereClauseParameter(i++, record.getAttribute("ORG_EMP_NO"));						
      
        int dmlcount = this.getDao("rbmdao").update("rsm9010_u02", param);
        
        return dmlcount;
    }
    
    /**
     * <p> �߸ſ� ����(�ݷ�) </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updatePcTellerCancel(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ETC"));		//���
        param.setValueParamter(i++, SESSION_USER_ID);	
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("ORG_COMM_NO"));	
        param.setWhereClauseParameter(i++, record.getAttribute("ORG_TELLER_ID"));	
        param.setWhereClauseParameter(i++, record.getAttribute("ORG_EMP_NO"));						
      
        int dmlcount = this.getDao("rbmdao").update("rsm9010_u03", param);
        
        return dmlcount;
    }
    
    /**
     * <p> �߸ſ� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deletePcTellerAprv(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("ORG_COMM_NO"));	
        param.setWhereClauseParameter(i++, record.getAttribute("ORG_TELLER_ID"));	
        param.setWhereClauseParameter(i++, record.getAttribute("ORG_EMP_NO"));						
      
        int dmlcount = this.getDao("rbmdao").update("rsm9010_d01", param);
        return dmlcount;
    }
    
    /**
     * <p> �߸ſ� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deletePcTellerAprvFlag(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("ETC"));		//���
        param.setValueParamter(i++, SESSION_USER_ID);	
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("ORG_COMM_NO"));	
        param.setWhereClauseParameter(i++, record.getAttribute("ORG_TELLER_ID"));	
        param.setWhereClauseParameter(i++, record.getAttribute("ORG_EMP_NO"));						
      
        int dmlcount = this.getDao("rbmdao").update("rsm9010_u04", param);
        return dmlcount;
    }
    
    /**
     * <p> �⺻Ű ��ȸ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int getTellerCnt(PosRecord record) 
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        String rtnKey = "";
        
        String sCommNo      = (String)record.getAttribute("COMM_NO");
        String sOrgCommon   = (String)record.getAttribute("ORG_COMM_NO");
        String sTellerId    = (String)record.getAttribute("TELLER_ID");
        String sOrgTellerId = (String)record.getAttribute("ORG_TELLER_ID");
        String sEmpNo       = (String)record.getAttribute("EMP_NO");
        String sOrgEmpNo    = (String)record.getAttribute("ORG_EMP_NO");

        //�⺻Ű�� ��ȭ�� ���ٸ�
        if( sCommNo  .equals(sOrgCommon)   &&
        	sTellerId.equals(sOrgTellerId) &&	
        	sEmpNo   .equals(sOrgEmpNo)    ) {
        	return 0;
        }
        
        param.setWhereClauseParameter(i++, record.getAttribute("COMM_NO"));
        param.setWhereClauseParameter(i++, record.getAttribute("TELLER_ID"));
        param.setWhereClauseParameter(i++, record.getAttribute("EMP_NO"));

        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm9010_s02", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        rtnKey = String.valueOf(pr[0].getAttribute("CNT"));
        
        return Integer.parseInt(rtnKey);
    }
}
