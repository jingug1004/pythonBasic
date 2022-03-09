/*
 * ================================================================================
 * �ý��� : �������� ���ϸ�ǥ ���� 
 * �ҽ����� �̸� : snis.rbm.business.rbo2010.activity.SaveAppoSeatMana.java 
 * ���ϼ��� : ��������  ���ϸ�ǥ���� 
 * �ۼ��� : ������
 * ���� : 1.0.0 
 * �������� : 2011- 09 - 21
 * ������������ : 
 * ���������� : 
 * ������������ :
 * =================================================================================
 */

package snis.rbm.business.rbo2010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveAppoSeatMana extends SnisActivity {

	public SaveAppoSeatMana(){
		
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
        
        String sJobGbn   = (String) ctx.get("jobGbn");
        if(sJobGbn == null) sJobGbn = "";	// 01:�Է�/����, 02:���� , 03: ȯ��ó�� , 04: �¼��ڵ庯��

        String sSalesNo = "";

        
    	sDsName = "dsSalesMana";
    	
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

	            if(sJobGbn.equals("01")){	// 01 �ű�,����
			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nTempCnt = updateAppoSeatMana(record)) == 0 ) {
			        		
			        		if(sSalesNo == null || sSalesNo.equals("")){
			        			sSalesNo = getMaxSalesNo(record);
			        		}
			        		
			        		//�ǸŹ�ȣ ���� 
			        		record.setAttribute("SALES_NO", sSalesNo);
			        		
			        		nTempCnt = insertAppoSeatMana(record);
			        	}
				       


			        	ctx.put("SALES_DT", record.getAttribute("SALES_DT"));
			        	ctx.put("BRNC_CD", record.getAttribute("BRNC_CD"));
			        	ctx.put("SALES_NO", record.getAttribute("SALES_NO"));
			        	
			        	nSaveCount = nSaveCount + nTempCnt;
			        	
			        	continue;
			        }
	            }else if(sJobGbn.equals("02")){	// 02 ���� 
	            	 
	            	nTempCnt = deleteAppoSeatMana(record); 
 	            	
 	            	nDeleteCount = nDeleteCount + nTempCnt;
	 	            
	            	
	            }else if(sJobGbn.equals("03")){	// 03 ȯ�� 
	            	 record.setAttribute("REFUND_AMT", record.getAttribute("SAVE_AMT"));
	            	
	            	 nTempCnt = updateAppoSeatRetn(record);
	            	 
	            	 nSaveCount = nSaveCount + nTempCnt;
	            }

	            
		        
	        }	        
        }

        
        if(sJobGbn.equals("01")){	// 01 �ű�,����
        	sDsName = "dsAppoSeatMana";

        	  if ( ctx.get(sDsName) != null ) {
      	        ds   		 = (PosDataset) ctx.get(sDsName);
      	        nSize 		 = ds.getRecordCount();

	      	        for ( int i = 0; i < nSize; i++ ) {
	      	            PosRecord record = ds.getRecord(i);
	      			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
	      			        	
	      			        	nTempCnt = updateAppoSeat(record);
	      			        	nSaveCount = nSaveCount + nTempCnt;
	      			        	
	      			        	continue;
	      			        }
	      	        }
              }
        
        }
        
        if(sJobGbn.equals("04")){	// 04: �¼��������� 
        	sDsName = "dsSeatGbnCd";

        	  if ( ctx.get(sDsName) != null ) {
      	        ds   		 = (PosDataset) ctx.get(sDsName);
      	        nSize 		 = ds.getRecordCount();

	      	        for ( int i = 0; i < nSize; i++ ) {
	      	            PosRecord record = ds.getRecord(i);
	      			        if ( record.getType() == PosRecord.UPDATE
	      			        			|| record.getType() == PosRecord.INSERT ) {
	      			        	
	      			        	nTempCnt = updateSeatCd(record);
	      			        	nSaveCount = nSaveCount + nTempCnt;
	      			        	
	      			        	continue;
	      			        }
	      	        }
              }
        
        }


        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> ���ϸ�ǥ �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertAppoSeatMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("SALES_DT"));	//�Ǹ�����
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("SALES_NO"));	//�ǸŹ�ȣ
        param.setValueParamter(i++, record.getAttribute("AREA"));		//����
        param.setValueParamter(i++, record.getAttribute("SEAT_NO"));	//�¼���ȣ


        param.setValueParamter(i++, record.getAttribute("UNIT_PRICE"));	//�ܰ�
        param.setValueParamter(i++, record.getAttribute("CUST_KD_CD"));	//������
        param.setValueParamter(i++, record.getAttribute("SAVE_AMT"));	//�Աݾ�
        param.setValueParamter(i++, record.getAttribute("RMK"));		//���
        param.setValueParamter(i++, SESSION_USER_ID);					//�ۼ�

        
        int dmlcount = this.getDao("rbmdao").update("rbo2010_i01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> ���ϸ�ǥ ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateAppoSeatMana(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("UNIT_PRICE"));	//�ܰ�
        param.setValueParamter(i++, record.getAttribute("CUST_KD_CD"));	//������
        param.setValueParamter(i++, record.getAttribute("SAVE_AMT"));	//�Աݾ�
        param.setValueParamter(i++, record.getAttribute("RMK"));		//���
        param.setValueParamter(i++, SESSION_USER_ID);      				//������
  
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("SALES_DT"));	//�Ǹ�����
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));		//�����ڵ�
        param.setWhereClauseParameter(i++, record.getAttribute("SALES_NO"));	//�ǸŹ�ȣ	
        param.setWhereClauseParameter(i++, record.getAttribute("AREA"));		//����
        param.setWhereClauseParameter(i++, record.getAttribute("SEAT_NO"));		//�¼���ȣ

        int dmlcount = this.getDao("rbmdao").update("rbo2010_u01", param);
        return dmlcount;
    }
  
    
    /**
     * <p> ���ϸ�ǥ ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateAppoSeat(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("RMK"));		//���
        param.setValueParamter(i++, SESSION_USER_ID);      				//������
  
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("SALES_DT"));	//�Ǹ�����
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));		//�����ڵ�
        param.setWhereClauseParameter(i++, record.getAttribute("SALES_NO"));	//�ǸŹ�ȣ	
        param.setWhereClauseParameter(i++, record.getAttribute("AREA"));		//����
        param.setWhereClauseParameter(i++, record.getAttribute("SEAT_NO"));		//�¼���ȣ

        int dmlcount = this.getDao("rbmdao").update("rbo2010_u03", param);
        return dmlcount;
    }
    
    
    /**
     * <p> ���ϸ�ǥ ȯ������(����) </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateAppoSeatRetn(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("REFUND_AMT"));		//ȯ�Ҿ�
        param.setValueParamter(i++, SESSION_USER_ID);      					//������
  
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("SALES_DT"));	//�Ǹ�����
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));		//�����ڵ�
        param.setWhereClauseParameter(i++, record.getAttribute("SALES_NO"));	//�ǸŹ�ȣ
        param.setWhereClauseParameter(i++, record.getAttribute("AREA"));		//����
        param.setWhereClauseParameter(i++, record.getAttribute("SEAT_NO"));		//�¼���

        int dmlcount = this.getDao("rbmdao").update("rbo2010_u02", param);
        return dmlcount;
    }

    
    
    /**
     * <p> ���ϸ�ǥ  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteAppoSeatMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("SALES_DT"));	//�Ǹ�����
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("SALES_NO"));	//�ǸŹ�ȣ
        param.setValueParamter(i++, record.getAttribute("AREA"));		//����
        param.setValueParamter(i++, record.getAttribute("SEAT_NO"));	//�¼���

        
        
        int dmlcount = this.getDao("rbmdao").update("rbo2010_d01", param);

        return dmlcount;
    }
    
    
    /**
     * <p> �ǸŹ�ȣ ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected String getMaxSalesNo(PosRecord record) 
    {
        String rtnKey = "";
        
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));	//�����ڵ�
        

        PosRowSet keyRecord = this.getDao("rbmdao").find("rbo2010_s04",param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
     
       
        rtnKey = String.valueOf(pr[0].getAttribute("SALES_NO"));

        return rtnKey;
    }
    
    
    /**
     * <p> �¼����� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateSeatCd(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("CD_TERM2"));		//�¼�����
        param.setValueParamter(i++, SESSION_USER_ID);      					//������
  
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("GRP_CD"));	//�׷��ȣ
        param.setWhereClauseParameter(i++, record.getAttribute("CD"));		//�ڵ�

        int dmlcount = this.getDao("rbmdao").update("rbo2010_u04", param);
        return dmlcount;
    }
    

}
