/*================================================================================
 * �ý���			: �Ҹ�ǰ�̷� ����
 * �ҽ����� �̸�	: snis.rbm.business.rbr4010.activity.SaveEvntMana.java
 * ���ϼ���		: �Ҹ�ǰ�� �԰����� ���, ����, ������ ���� ó�� 
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-09-24
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbs3010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveSupplStk extends SnisActivity {
	
	public SaveSupplStk(){}

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
        

        // ��ǰ ǰ�� ����
        sDsName = "dsSupplStk";        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        for ( int i = 0; i < ds.getRecordCount(); i++ ) {
        	
	        	PosRecord record = ds.getRecord(i);
    			
	        	if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {		        	
	        		nSaveCount += insertSupplCode(record);
	        	} else if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {		        	
	        		nSaveCount += updateSupplCode(record);
	        	} else if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {		        	
	        		nDeleteCount += deleteSupplCode(record);
	        	}	        	
	        }        	
        }
        
        
        sDsName = "dsSupplHist";        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        int nSuppStkQty, nInputQty, nOldQty;
	        Double dInputQty;	//������Է°�
	        
	        //��� ����
	        for ( int i = 0; i < nSize; i++ ) {
        	
	        	PosRecord record = ds.getRecord(i);
	        	
	        	dInputQty   = (Double)record.getAttribute("QTY");
	        	nInputQty   = dInputQty.intValue();					//������Է°�	        	
    			
	        	if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {
	        		
	        		//�Ҹ�ǰ�̷� �⺻Ű �ߺ�üũ
	        		if( selectSupplKeyCnt(record) > 0 ) {
	        			try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		
		            		String sDate = (String)record.getAttribute("PRHS_DT");
		            		Util.setSvcMsg(ctx, "[ " + sDate.substring(0, 4) + "-" + sDate.substring(4, 6) + "-" +
		            				            sDate.substring(6, 8) + " ]�� [ " + selectSupplName(record) + 
		            				            " ]�� �ߺ��� ���̶� �߰��Ͻ� �� �����ϴ�.");
		            		return;
		            	}
	        		}
	        		
	        		if( selectSupplStkChk(record) == 0 ) {
	        			//��� ���̺� ���� ������ insert       			
	        			record.setAttribute("SUM", dInputQty);
	        			saveSuppStk(record);
	        		} else {
	        			//��� ���̺� ���� �ִٸ� update(����� + �Էµ� �� )
	        			nSuppStkQty = selectSuppStkCnt(record);		//���
	        			Double dQty = new Double(nSuppStkQty + nInputQty);
	        			record.setAttribute("SUM", dQty);
	        			
	        			saveSuppStk(record);
	        		}
	        	}
	        	
	        	nSuppStkQty = selectSuppStkCnt(record);				//���
	        	
	        	if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
	        		//��� + �Էµ� �� - ������ ��
	        		nOldQty = selectSuppHistCnt(record);			//������ �ԷµǾ� �ִ� ��;
	        		
	        		if( nSuppStkQty + nInputQty - nOldQty >= 0) {
		        		Double dQty = new Double(nSuppStkQty + nInputQty - nOldQty);      		
		        		record.setAttribute("SUM", dQty);
	
	        			saveSuppStk(record);
	        		} else {
	        			//Exception �߻�
	        			try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		
		            		String sDate = (String)record.getAttribute("PRHS_DT");
		            		Util.setSvcMsg(ctx, "[ " + sDate.substring(0, 4) + "-" + sDate.substring(4, 6) + "-" +
		            				            sDate.substring(6, 8) + " ]�� [ " + selectSupplName(record) + 
		            				            " ]�� ���� �� ������ ��� 0���� �۾����Ƿ� �����Ͻ� �� �����ϴ�.");
		            		return;
		            	}
	        		}
	        	}
	        	
	        	if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
	        		//��� - ������ ��
	        		
	        		if( nSuppStkQty - nInputQty >= 0) {
	        			Double nQty = new Double(nSuppStkQty - nInputQty);      		
		        		record.setAttribute("SUM", nQty);
	
	        			saveSuppStk(record);
	        		} else {
	        			//Exception �߻�
	        			try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		String sDate = (String)record.getAttribute("PRHS_DT");
		            		Util.setSvcMsg(ctx, "[ " + sDate.substring(0, 4) + "-" + sDate.substring(4, 6) + "-" +
		            				            sDate.substring(6, 8) + " ]�� [ " + selectSupplName(record) + 
		            				            " ]�� �����ϸ� ��� 0���� �۾����Ƿ� �����Ͻ� �� �����ϴ�.");
		            		return;
		            	}
	        		}
	        	}

	        }   
	        
	        //Dataset ����
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	nTempCnt = saveSupplHist(record);
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {          		
	            	nDeleteCount = nDeleteCount + deleteSupplHist(record);
	            }
	        }	        
        }

        
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
    }

    /**
     * <p> �Ҹ�ǰ�̷� �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveSupplHist(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("SUPPL_CD"));	//�Ҹ�ǰ�ڵ�
        param.setValueParamter(i++, record.getAttribute("PRHS_DT"));	//��������
        param.setValueParamter(i++, record.getAttribute("QTY"));		//����
        param.setValueParamter(i++, record.getAttribute("RMK"));		//���
        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(�ۼ���)
        
        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(������)
        
        int dmlcount = this.getDao("rbmdao").update("rbs3010_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> �Ҹ�ǰ�̷� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteSupplHist(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("SUPPL_CD"));  //�Ҹ�ǰ�ڵ�
        param.setValueParamter(i++, record.getAttribute("PRHS_DT"));   //��������

        int dmlcount = this.getDao("rbmdao").update("rbs3010_d01", param);

        return dmlcount;
    }
    
    /**
     * <p> �Ҹ�ǰ��� ���� ��ȸ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int selectSuppStkCnt(PosRecord record) 
    {	
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("SUPPL_CD"));  //�Ҹ�ǰ�ڵ�
        
        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbs3010_s03", param);  
        PosRow pr[] = rtnRecord.getAllRow();        
        String rtnQty = String.valueOf(pr[0].getAttribute("QTY"));
        
        if( rtnQty == null )	rtnQty = "-1";
        
        return Integer.valueOf(rtnQty).intValue();
    }
    
    /**
     * <p> �Ҹ�ǰ�̷� ���� ��ȸ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int selectSuppHistCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("SUPPL_CD"));  //�Ҹ�ǰ�ڵ�
        param.setWhereClauseParameter(i++, record.getAttribute("PRHS_DT"));   //��������

        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbs3010_s04", param);  
        
        PosRow pr[] = rtnRecord.getAllRow();
        
        String rtnQty = String.valueOf(pr[0].getAttribute("QTY"));
        
        return Integer.valueOf(rtnQty).intValue();
    }
    
    /**
     * <p> �Ҹ�ǰ��� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveSuppStk(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("SUPPL_CD"));  //�Ҹ�ǰ�ڵ�
        param.setValueParamter(i++, record.getAttribute("SUM"));   	   //����
        param.setValueParamter(i++, SESSION_USER_ID);				   //�����ID

        int dmlcount = this.getDao("rbmdao").update("rbs3010_m02", param);

        return dmlcount;
    }
    
    /**
     * <p> �Ҹ�ǰ��� ���̺� ��� ���� ���� ��ȸ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int selectSupplStkChk(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("SUPPL_CD"));  //�Ҹ�ǰ�ڵ�

        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbs3010_s05", param);  
        
        PosRow pr[] = rtnRecord.getAllRow();
        
        String rtnQty = String.valueOf(pr[0].getAttribute("CNT"));
        
        return Integer.valueOf(rtnQty).intValue();
    }
    
    /**
     * <p> �Ҹ�ǰ�̸� ��ȸ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected String selectSupplName(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("SUPPL_CD"));  //�Ҹ�ǰ�ڵ�

        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbs3010_s06", param);  
        
        PosRow pr[] = rtnRecord.getAllRow();

        return String.valueOf(pr[0].getAttribute("CD_NM"));
    }
    
    /**
     * <p> �Ҹ�ǰ�̷� �⺻Ű ��ȸ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int selectSupplKeyCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("SUPPL_CD"));  //�Ҹ�ǰ�ڵ�
        param.setWhereClauseParameter(i++, record.getAttribute("PRHS_DT"));   //��������

        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbs3010_s07", param);  
        
        PosRow pr[] = rtnRecord.getAllRow();
        
        String rtnQty = String.valueOf(pr[0].getAttribute("CNT"));
        
        return Integer.valueOf(rtnQty).intValue();
    }

    /**
     * <p> �Ҹ�ǰ�̷� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertSupplCode(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, "031");									//�Ҹ�ǰ�׷��ڵ�
        param.setValueParamter(i++, record.getAttribute("SUPPL_CD_NM"));	//�Ҹ�ǰ �ڵ��
        param.setValueParamter(i++, record.getAttribute("MANUF_NM"));		// �������
        param.setValueParamter(i++, record.getAttribute("BIZ_GBN"));		//(�ϹݼҸ�ǰ, �����ͼҸ�ǰ ����)
        param.setValueParamter(i++, SESSION_USER_ID);						//�����ID(�ۼ���)
        
        int dmlcount = this.getDao("rbmdao").update("rsy1010_i03", param);
        
        return dmlcount;
    }

    /**
     * <p> �Ҹ�ǰ�̷� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateSupplCode(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("SUPPL_CD_NM"));	//�Ҹ�ǰ �ڵ��
        param.setValueParamter(i++, record.getAttribute("MANUF_NM"));									//�߰��ڵ�(�ϹݼҸ�ǰ, �����ͼҸ�ǰ ����)
        param.setValueParamter(i++, record.getAttribute("BIZ_GBN"));		//������� ����
        param.setValueParamter(i++, null);									//�߰��ڵ�(�ϹݼҸ�ǰ, �����ͼҸ�ǰ ����)
        param.setValueParamter(i++, null);									//�߰��ڵ�(�ϹݼҸ�ǰ, �����ͼҸ�ǰ ����)
        param.setValueParamter(i++, null);									//�߰��ڵ�(�ϹݼҸ�ǰ, �����ͼҸ�ǰ ����)
        param.setValueParamter(i++, null);									//�߰��ڵ�(�ϹݼҸ�ǰ, �����ͼҸ�ǰ ����)
        param.setValueParamter(i++, null);									//�߰��ڵ�(�ϹݼҸ�ǰ, �����ͼҸ�ǰ ����)
        param.setValueParamter(i++, "N");						//�����ID(�ۼ���)        
        param.setValueParamter(i++, SESSION_USER_ID);						//�����ID(������)
        param.setValueParamter(i++, null);									//�߰��ڵ�(�ϹݼҸ�ǰ, �����ͼҸ�ǰ ����)
        
        param.setValueParamter(i++, "031");									//�Ҹ�ǰ�׷��ڵ�
        param.setValueParamter(i++, record.getAttribute("SUPPL_CD"));		//�Ҹ�ǰ �ڵ�
        int dmlcount = this.getDao("rbmdao").update("rsy1010_u02", param);
        
        return dmlcount;
    }
        
    
    /**
     * <p> �Ҹ�ǰ�̷� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteSupplCode(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, "031");									//�Ҹ�ǰ�׷��ڵ�
        param.setValueParamter(i++, record.getAttribute("SUPPL_CD"));		//�Ҹ�ǰ �ڵ�

        int dmlcount = this.getDao("rbmdao").update("rsy1010_d02", param);

        return dmlcount;
    }
}