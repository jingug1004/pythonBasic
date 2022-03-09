package snis.rbm.business.rbs3040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveSupplReturn extends SnisActivity {
	public SaveSupplReturn(){
		
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
     	
     	PosDataset ds;
         int nSize        = 0;
         int nTempCnt     = 0;
         String sSeq      = "";
         
         
         //�Ҹ�ǰ���� ����
         sDsName = "dsSupplRegi";        
         if ( ctx.get(sDsName) != null ) {          	
 	        ds    = (PosDataset) ctx.get(sDsName);
 	        nSize = ds.getRecordCount();

 	        int nRtnKey, nSuppStkQty, nOldQty, nInputQty;
 	        Double dInputQty;	//������Է°�
 	        
 	        for ( int i = 0; i < nSize; i++ ) {
 	            PosRecord record = ds.getRecord(i);            
 	         
 	            nSuppStkQty = selectSuppStkCnt(record);	 	   		   //���
 	            dInputQty   = (Double)record.getAttribute("RETN_QTY");
 	        	nInputQty   = dInputQty.intValue();	                   //������Է°�(��û����)
 	        	
 	        	nOldQty = selectSupplRegiCnt(record); //������ ��
 	        	
 		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
 		        
 		        	//��� + ������ �� - �Էµ� ��
 		        	Double dQty = new Double(nSuppStkQty - nOldQty + nInputQty);
        		
	        		record.setAttribute("SUM", dQty);
	        		
	        		saveSupplRegi(record, sSeq);	//�Ҹ�ǰ����
        			saveSuppStk(record);			//�Ҹ�ǰ�������
 		        }

 	           
 	        }	        
         }
         Util.setSaveCount  (ctx, nSaveCount  );
         Util.setDeleteCount(ctx, nDeleteCount);
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
      * <p> �Ҹ�ǰ���� �ݳ� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int saveSupplRegi(PosRecord record, String sSeq) 
     {

         PosParameter param = new PosParameter();   
         
         int i = 0;
         
         param.setValueParamter(i++, 	record.getAttribute("RETN_QTY"));		//�ݳ�����
         param.setValueParamter(i++,	record.getAttribute("RETN_ID"));		//�ݳ��� ���
         param.setValueParamter(i++, 	SESSION_USER_ID);		//������ID
         
         
         i = 0;
         param.setWhereClauseParameter(i++, record.getAttribute("REQ_DT"));			//��û����
         param.setWhereClauseParameter(i++, record.getAttribute("REQ_ID"));			//��û�ڻ��
         param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));			//����
         param.setWhereClauseParameter(i++, record.getAttribute("SUPPL_CD"));		//�Ҹ�ǰ�ڵ�
         		
         int dmlcount = this.getDao("rbmdao").update("rbs3040_u02", param);
         
         return dmlcount;
         
  
     }
     
     
     
     /**
      * <p> �Ҹ�ǰ��� �ݳ� ���� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int saveSuppStk(PosRecord record) 
     {
         PosParameter param = new PosParameter();   
         
         int i = 0;
         
         param.setValueParamter(i++, 	record.getAttribute("SUM"));		//�ݳ�����
         param.setValueParamter(i++,	SESSION_USER_ID);		//�ݳ��� ���

         
         
         i = 0;
         param.setWhereClauseParameter(i++, record.getAttribute("SUPPL_CD"));		//�Ҹ�ǰ�ڵ�
         		
         int dmlcount = this.getDao("rbmdao").update("rbs3040_u03", param);
         
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
         
         PosRowSet rtnRecord = this.getDao("rbmdao").find("rbs3040_s05", param);  
         PosRow pr[] = rtnRecord.getAllRow();        
         String rtnQty = String.valueOf(pr[0].getAttribute("RETN_QTY"));
         
         if( rtnQty == null )	rtnQty = "-1";
         
         return Integer.valueOf(rtnQty).intValue();
     }
}




