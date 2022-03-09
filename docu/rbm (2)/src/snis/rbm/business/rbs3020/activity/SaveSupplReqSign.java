package snis.rbm.business.rbs3020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;


import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveSupplReqSign extends SnisActivity {
	public SaveSupplReqSign(){
		
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
         String sChk 	  = "";
         
         int nSignDtCheck = -1;
         
         String sSignDt     = (String)ctx.get("SIGN_DT");   //��������
         String sRealReciId = (String)ctx.get("USER_ID");   //������ID
         
         //�Ҹ�ǰ���� ����
         sDsName = "dsSupplRegi";        
         if ( ctx.get(sDsName) != null ) {          	
 	        ds    = (PosDataset) ctx.get(sDsName);
 	        nSize = ds.getRecordCount();

 	        
 	        for ( int i = 0; i < nSize; i++ ) {
 	            PosRecord record = ds.getRecord(i);            
 	            sChk = (String)record.getAttribute("CHK");
 	            
 	          
 		        if ( sChk.equals("1") ) {
	        		
	 		           nSignDtCheck = selectSignDt(record);
	 		          
	 		          if( nSignDtCheck == 1 ) {
	 		          	//�������ڰ� ���� ���
	 		        	record.setAttribute("SIGN_DT", sSignDt);
	 		        	record.setAttribute("USER_ID", sRealReciId);
	 		        	
	 		          	nSaveCount += saveSupplRegi(record);           
	 		          } else {
	 		          	//�������ڰ� �̹� ������ ���
	 		          	Util.setSvcMsgCode(ctx, "SNIS_RBM_E011");     
	 		          }
 		          

 		        }

 	           
 	        }	        
         }
         Util.setSaveCount  (ctx, nSaveCount  );
         Util.setDeleteCount(ctx, nDeleteCount);
         

        Util.setSaveCount  (ctx, nSaveCount);
    }

    /**
     * <p> �Ҹ�ǰ���� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveSupplRegi(PosRecord record) 
    {

        
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("USER_ID"));		//�Ǽ����λ��
        param.setValueParamter(i++, record.getAttribute("SIGN_DT"));			//��������
        param.setValueParamter(i++, SESSION_USER_ID);	//�����ID(������)
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("REQ_DT"));			//��û����
        param.setWhereClauseParameter(i++, record.getAttribute("REQ_ID" ));			//��û�ڻ��
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ" ));				//����
        param.setWhereClauseParameter(i++, record.getAttribute("SUPPL_CD" ));			//�Ҹ�ǰ�ڵ�
        		
        int dmlcount = this.getDao("rbmdao").update("rbs3040_u01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> �Ҹ�ǰ���� �������� ��ȸ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int selectSignDt(PosRecord record) 
    {	
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("REQ_DT"));    //��û����
        param.setWhereClauseParameter(i++, record.getAttribute("REQ_ID" ));  //��û��ID
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ" ));      //����
        param.setWhereClauseParameter(i++, record.getAttribute("SUPPL_CD" ));  //�Ҹ�ǰ�ڵ�
        
        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbs3040_s02", param);  
        PosRow pr[] = rtnRecord.getAllRow();        
        
        String rtnQty = String.valueOf(pr[0].getAttribute("CNT"));
        
        if( rtnQty == null )	rtnQty = "-1";
        
        return Integer.valueOf(rtnQty).intValue();
    }
}
