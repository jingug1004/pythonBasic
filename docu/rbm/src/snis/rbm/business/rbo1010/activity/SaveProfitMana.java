/*================================================================================
 * �ý���			: �����ο����
 * �ҽ����� �̸�	: snis.rbm.business.rbo1010.activity.saveProfitMana
 * ���ϼ���		: �����ο����
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-09-16
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbo1010.activity;  

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveProfitMana extends SnisActivity {
	
	public SaveProfitMana(){}

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
        
        sDsName = "dsProfitMana";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	//�⺻Ű �ߺ�üũ
		        	if( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        		if( selectProfitCnt(record) > 0 ) {
		        			try { 
		            			throw new Exception(); 
			            	} catch(Exception e) {       		
			            		this.rollbackTransaction("tx_rbm");
			            		
			            		String sRaceDt = (String)record.getAttribute("RACE_DT");
			            		String sGbn    = (String)record.getAttribute("GBN");
			            		
			            		Util.setSvcMsg(ctx, "[ " + sGbn                    + " ]" +
			            							"[ " + sRaceDt.substring(0, 4) + "-"  + 
					     				                   sRaceDt.substring(4, 6) + "-"  +
					     				                   sRaceDt.substring(6, 8) + " ](��)�� �ߺ����� �����մϴ�.");
			            		return;
			            	}
		        		}
		        	}

		        	if( selectTmsChk(record) == 0 ) {
		        		try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		
		            		String sRaceDt = (String)record.getAttribute("RACE_DT");
		            		String sGbn    = (String)record.getAttribute("GBN");
		            		
		            		Util.setSvcMsg(ctx, "[ " + sGbn                    + " ]" +
		            							"[ " + sRaceDt.substring(0, 4) + "-"  + 
				     				                   sRaceDt.substring(4, 6) + "-"  +
				     				                   sRaceDt.substring(6, 8) + " ]���� [ ȸ�� ]�� �����ϴ�.");
		            		return;
		            	}
		        	} else {
		        		Double dTms = new Double(selectTms(record));
		        		record.setAttribute("TMS", dTms);
		        	}
		        	
		        	nTempCnt = saveProfitMana(record);
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteProfitMana(record);	            	
	            }		        
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> �����ο� �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveProfitMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        //pk set 
        param.setValueParamter(i++, record.getAttribute("GBN"));				//����
        param.setValueParamter(i++, record.getAttribute("RACE_DT"));			//��������
        param.setValueParamter(i++, record.getAttribute("TMS"));				//ȸ��
        param.setValueParamter(i++, record.getAttribute("PAY_PRSN_NUM"));		//�����ο���
        param.setValueParamter(i++, record.getAttribute("FREE_PRSN_NUM"));		//�����ο���
        
        param.setValueParamter(i++, record.getAttribute("ISSU_AMT"));			//�߱Ǳݾ�
        param.setValueParamter(i++, record.getAttribute("SPC_TAX"));			//Ư�Ҽ�         
        param.setValueParamter(i++, record.getAttribute("EDU_TAX"));			//������
        param.setValueParamter(i++, record.getAttribute("SUPP_AMT"));			//���ް���
        param.setValueParamter(i++, record.getAttribute("ADD_TAX"));			//�ΰ���
        
        param.setValueParamter(i++, record.getAttribute("RMK"));				//���
        param.setValueParamter(i++, SESSION_USER_ID);							//�����ID(�ۼ���)
        param.setValueParamter(i++, SESSION_USER_ID);							//�����ID(������)

        int dmlcount = this.getDao("rbmdao").update("rbo1010_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> �����ο� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteProfitMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("GBN"));			//����
        param.setValueParamter(i++, record.getAttribute("RACE_DT"));        //��������
        
        int dmlcount = this.getDao("rbmdao").update("rbo1010_d01", param);

        return dmlcount;
    }
    
    /**
     * <p> ���Ͱ��� �⺻Ű ���� ��ȸ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int selectProfitCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("GBN"));      //����
        param.setWhereClauseParameter(i++, record.getAttribute("RACE_DT"));  //��������
                		
        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbo1010_s02", param);  
        
        PosRow pr[] = rtnRecord.getAllRow();
        
        String rtnCnt = String.valueOf(pr[0].getAttribute("CNT"));
        
        return Integer.valueOf(rtnCnt).intValue();
    }
    
    /**
     * <p> ���Ͱ��� ȸ�� ���翩�� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int selectTmsChk(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("RACE_DT"));  //��������
                		
        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbo1010_s04", param);  
        
        PosRow pr[] = rtnRecord.getAllRow();
        
        String rtnCnt = String.valueOf(pr[0].getAttribute("CNT"));        
        
        return Integer.valueOf(rtnCnt).intValue();
    }
    
    /**
     * <p> ���Ͱ��� ȸ�� ��ȸ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int selectTms(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("RACE_DT"));  //��������
                		
        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbo1010_s03", param);  
        
        PosRow pr[] = rtnRecord.getAllRow();
        
        String rtnCnt = String.valueOf(pr[0].getAttribute("TMS"));        
        
        return Integer.valueOf(rtnCnt).intValue();
    }
}
