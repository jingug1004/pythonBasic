/*================================================================================
 * �ý���			: �Ҹ�ǰ ��û��Ȳ
 * �ҽ����� �̸�	: snis.rbm.business.rbr4010.activity.SaveEvntMana.java
 * ���ϼ���		: �Ҹ�ǰ ������ ���ΰ� �ݷ��� �����ϰ� ���� ��, �Ҹ�ǰ ���忡 �߰�����
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-09-28
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbs3030.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

public class SaveSupplReqRegi extends SnisActivity {
	
	public SaveSupplReqRegi(){}

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
        String sSeq      = "";
        String sTonerCd  = "002";        
        sDsName = "dsSupplReq";
        
		String sBizGbn = (String) ctx.get("BIZ_GBN".trim());
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            	
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		           	nTempCnt   = saveSupplReq(record);
			        nSaveCount = nSaveCount + nTempCnt;
			        
			        if( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	sSeq = selectSeq(record);
			        }
			        continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	//����ǰ�� ����� ���� X
	            	if (selectRegiCnt(record) > 0 ) {
	            		try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsg(ctx, "���ε� �Ҹ�ǰ�� �����ϴ� ��û��  �����Ͻ� �� �����ϴ�");
		            		
		            		return;
		            	}
	            	}
	            	
	            	//����Ǿ��� �� ���� X
	            	if( selectSignCnt(record) > 0 ) {
	            		try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsg(ctx, "������ �� �Ҹ�ǰ�� �����Ͻ� �� �����ϴ�");
		            		
		            		return;
		            	}
	            	}
	            	
	            	nDeleteCount = nDeleteCount + deleteSupplReq(record);
	            }		        
	        }	        
        }
        
        //�Ҹ�ǰ���� ����
        sDsName = "dsSupplRegi";        
        if ( ctx.get(sDsName) != null ) {          	
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();

	        int nRtnKey, nSuppStkQty, nOldQty, nInputQty;
	        Double dInputQty;	//������Է°�
	        Double dQty;
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);            
	         
	            nSuppStkQty = selectSuppStkCnt(record);	 	   		   //���
	            dInputQty   = (Double)record.getAttribute("REQ_QTY");
	        	nInputQty   = dInputQty.intValue();	                   //������Է°�(��û����)
	        	
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	nRtnKey = selectRegiKey(record);	//�⺻Ű ���� ��ȸ
		        	
		        	if( nRtnKey == 0) {
		        		//�Ҹ�ǰ ���忡 �⺻Ű �ߺ��Ǵ� ���� ������ Insert
		        		if( nSuppStkQty - nInputQty >= 0 || sTonerCd.equals(sBizGbn)) {
		        			dQty = new Double(nSuppStkQty - nInputQty);
		        			record.setAttribute("SUM", dQty);
		        			saveSupplRegi(record, sSeq);	//�Ҹ�ǰ����
		        			saveSuppStk(record);	        //�Ҹ�ǰ�������
		        		} else {
			        			//rollback => ��� ����
			        		try { 
		            			throw new Exception(); 
			            	} catch(Exception e) {       		
			            		this.rollbackTransaction("tx_rbm");
			            		Util.setSvcMsg(ctx, "��� ����");
			            		
			            		return;
			            	}
		        		}
		 
		        	} else {
		        		//rollback => �⺻Ű �ߺ�
		        		try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsg(ctx, "�⺻Ű �ߺ�");
		            		
		            		return;
		            	}
		        	}
		        }
		        
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
		        	//��� + ������ �� - �Էµ� ��
		        	
		        	nOldQty = selectSupplRegiCnt(record); //������ ��
		        	
		        	if( nSuppStkQty + nOldQty - nInputQty >= 0 || sTonerCd.equals(sBizGbn) ) {
		        		dQty = new Double(nSuppStkQty + nOldQty - nInputQty);
		        		record.setAttribute("SUM", dQty);
		        		
		        		saveSupplRegi(record, sSeq);	//�Ҹ�ǰ����
	        			saveSuppStk(record);			//�Ҹ�ǰ�������
		        	} else {
		        		//rollback => ��� 0���� �۾���
		        		try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsg(ctx, "��� ����");
		            		
		            		return;
		            	}
		        	}
		        }

	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {		            	
	            	//��� + ������ �� 
	            	if( nSuppStkQty + nInputQty >= 0 || sTonerCd.equals(sBizGbn) ) {
	            		dQty = new Double(nSuppStkQty + nInputQty);
		        		record.setAttribute("SUM", dQty);
		        		
		        		deleteSupplRegi(record);  //�Ҹ�ǰ�������
	        			saveSuppStk(record);	  //�Ҹ�ǰ�������
	            	} else {
	            		//rollback => ��� 0���� �۾���
		        		try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsg(ctx, "��� ����");
		            		
		            		return;
		            	}
	            	}
	            }		        
	        }	        
        }
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
    }

    /**
     * <p> �Ҹ�ǰ��û �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveSupplReq(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("REQ_DT"));	       //��û����
        param.setValueParamter(i++, record.getAttribute("REQ_ID"));	   //��û�ڻ��
        param.setValueParamter(i++, record.getAttribute("SEQ"));		   //����
        param.setValueParamter(i++, record.getAttribute("REQ_CNTNT"));	   //��û����
        param.setValueParamter(i++, record.getAttribute("PROG_STAT_CD"));  //���ο���
        
        param.setValueParamter(i++, record.getAttribute("REPLY"));		   //�亯       
        param.setValueParamter(i++, record.getAttribute("MNG_REQ_YN"));    //�����ڽ�û����
        param.setValueParamter(i++, record.getAttribute("APRV_DT"));       //��������
        param.setValueParamter(i++, record.getAttribute("BIZ_GBN"));       //�Ҹ�ǰ �з�
        param.setValueParamter(i++, SESSION_USER_ID);					   //�����ID(�ۼ���)      
        param.setValueParamter(i++, SESSION_USER_ID);					   //�����ID(������)
                		
        param.setValueParamter(i++, record.getAttribute("REQ_DT"));	       //��û����
        param.setValueParamter(i++, record.getAttribute("REQ_ID"));	   //��û�ڻ��

        int dmlcount = this.getDao("rbmdao").update("rbs3020_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> �Ҹ�ǰ��û ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteSupplReq(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("REQ_DT"));	    //��û����
        param.setValueParamter(i++, record.getAttribute("REQ_ID"));   //��û�ڻ��
        param.setValueParamter(i++, record.getAttribute("SEQ"));        //����
        
        int dmlcount = this.getDao("rbmdao").update("rbs3020_d01", param);

        return dmlcount;
    }
    
    /**
     * <p> �Ҹ�ǰ ���� �⺻Ű ���� ��ȸ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int selectRegiKey(PosRecord record) 
    {
    	if( record.getAttribute("SEQ") == null ) {
    		return 0;
    	}
    	
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("REQ_DT"));    //��û����
        param.setWhereClauseParameter(i++, record.getAttribute("REQ_ID"));  //��û�ڻ��
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));       //����
        param.setWhereClauseParameter(i++, record.getAttribute("SUPPL_CD"));  //�Ҹ�ǰ�ڵ�        

        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbs3030_s03", param);  
    	
        PosRow pr[] = rtnRecord.getAllRow();
        
        String rtnCnt = String.valueOf(pr[0].getAttribute("CNT"));
        
        return Integer.valueOf(rtnCnt).intValue();
    }
    
    /**
     * <p> �Ҹ�ǰ���� �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveSupplRegi(PosRecord record, String sSeq) 
    {
        PosParameter param = new PosParameter();   
        String sMng = "";
        int i = 0;
        Date today = new Date();
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
        String sToday = formater.format(today);
        
        
        if( record.getAttribute("SEQ") == null ) {
        	record.setAttribute("SEQ", sSeq);
        }
        
        param.setValueParamter(i++, record.getAttribute("REQ_DT"));	       //��û����
        param.setValueParamter(i++, record.getAttribute("REQ_ID"));      //��û�ڻ��
        param.setValueParamter(i++, record.getAttribute("SEQ"));		   //����      
        param.setValueParamter(i++, record.getAttribute("SUPPL_CD"));	   //�Ҹ�ǰ�ڵ�
        param.setValueParamter(i++, record.getAttribute("REQ_QTY"));  	   //����
        
        sMng = selectMng(record);
        
        if( sMng.equals("Y")) {
        	param.setValueParamter(i++, record.getAttribute("REQ_ID"));  	//�Ǽ����λ��
        	//param.setValueParamter(i++, record.getAttribute("REQ_DT"));   //��������
        	param.setValueParamter(i++, sToday);    						//��������            
        } else {
        	param.setValueParamter(i++, null);  //�Ǽ����λ��
            param.setValueParamter(i++, null);  //��������
        }
        param.setValueParamter(i++, SESSION_USER_ID);					   //�����ID(�ۼ���)
        param.setValueParamter(i++, SESSION_USER_ID);					   //�����ID(������)
						
        int dmlcount = this.getDao("rbmdao").update("rbs3030_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> �Ҹ�ǰ���� ���� ��ȸ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int selectSupplRegiCnt(PosRecord record) 
    {	
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("REQ_DT"));    //��û����
        param.setWhereClauseParameter(i++, record.getAttribute("REQ_ID"));  //��û�ڻ��
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));  	  //����
        param.setWhereClauseParameter(i++, record.getAttribute("SUPPL_CD"));  //�Ҹ�ǰ�ڵ�
        
        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbs3030_s04", param);  
        PosRow pr[] = rtnRecord.getAllRow();        
        String rtnQty = String.valueOf(pr[0].getAttribute("QTY"));
        
        if( rtnQty == null )	rtnQty = "-1";
        
        return Integer.valueOf(rtnQty).intValue();
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
        String rtnQty = "0";
        if (pr.length > 0) { 
        	rtnQty = String.valueOf(pr[0].getAttribute("QTY"));
        }
        
        if( rtnQty == null )	rtnQty = "-1";
        
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
     * <p> �Ҹ�ǰ���� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteSupplRegi(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("REQ_DT"));	   //��û����
        param.setValueParamter(i++, record.getAttribute("REQ_ID"));  //��û�ڻ��
        param.setValueParamter(i++, record.getAttribute("SEQ"));       //����
        param.setValueParamter(i++, record.getAttribute("SUPPL_CD"));  //�Ҹ�ǰ�ڵ�
        
        int dmlcount = this.getDao("rbmdao").update("rbs3030_d01", param);

        return dmlcount;
    }
    
    /**
     * <p> �Ҹ�ǰ��û ���� ��ȸ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected String selectSeq(PosRecord record) 
    {	
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("REQ_DT"));   //�Ҹ�ǰ�ڵ�
        param.setWhereClauseParameter(i++, record.getAttribute("REQ_ID")); //��û�ڻ��
        
        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbs3030_s06", param);  
        PosRow pr[] = rtnRecord.getAllRow();        
        String rtnQty = String.valueOf(pr[0].getAttribute("SEQ"));

        return rtnQty;
    }
    
    /**
     * <p> �����ڽ��ο��� ��ȸ  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  rtnKey	int			
     * @throws  none
     */
    protected String selectMng(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String rtnKey = "";
        
        param.setWhereClauseParameter(i++, record.getAttribute("REQ_DT"));	    //��û����
        param.setWhereClauseParameter(i++, record.getAttribute("REQ_ID"));	//��û�ڻ��
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));	        //����
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rbs3030_s07", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        rtnKey = String.valueOf(pr[0].getAttribute("MNG"));

        return rtnKey;
    }
    
    /**
     * <p> ���� ���� ��ȸ  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  rtnKey	int			
     * @throws  none
     */
    protected int selectSignCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String rtnKey = "";
        
        param.setWhereClauseParameter(i++, record.getAttribute("REQ_DT"));	    //��û����
        param.setWhereClauseParameter(i++, record.getAttribute("REQ_ID"));	//��û�ڻ��
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));	        //����
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rbs3030_s08", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.valueOf(rtnKey).intValue();
    }
    
    /**
     * <p> �⺻Ű�� ���� �Ҹ�ǰ ���� �Ǽ� ��ȸ  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  rtnKey	int			
     * @throws  none
     */
    protected int selectRegiCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String rtnKey = "";
        
        param.setWhereClauseParameter(i++, record.getAttribute("REQ_DT"));	    //��û����
        param.setWhereClauseParameter(i++, record.getAttribute("REQ_ID"));	//��û�ڻ��
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));	        //����
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rbs3030_s09", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.valueOf(rtnKey).intValue();
    }
}