/*
 * ================================================================================
 * �ý��� : �нǽŰ� ����
 * ���� �̸� : snis.rbm.business.rsm6040.activity.SaveLossReturnsReceipt.java 
 * ���ϼ��� :  
 * �ۼ��� : 
 * ���� : 1.0.0 �������� : 2011- 10 - 26
 * ������������ : 
 * ���������� : 
 * ������������ :
 * =================================================================================
 */
package snis.rbm.business.rsm2080.activity;

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


public class SaveSpecDesc extends SnisActivity {
	
	public SaveSpecDesc(){}

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
        int nTempCnt     = 0;
        int nSize		 = 0;
        int nSize2		 = 0;
        
    	String sDsName   = "";
    	PosDataset ds;
        
        sDsName = "dsWeather";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   	 = (PosDataset) ctx.get(sDsName);
	        nSize 	 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
		  		    nTempCnt  = deleteWeather(record);	// ������� ����
		        } else {
	            	                	
		        	nTempCnt  = updateWeather(record);	// ������� ����
		        }
	  		    nSaveCount = nSaveCount + nTempCnt;
		        continue;
	        }	        
        }

        sDsName = "dsSpecDesc";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   	 = (PosDataset) ctx.get(sDsName);
	        nSize 	 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            	                	
	  		    nTempCnt  = updateSpecDesc(record);	// ��Ȳ����, Ư�̻���, ��������  ����
	  		    
	  		    nSaveCount = nSaveCount + nTempCnt;
		        continue;
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount);     
       
    }

    /**
     * <p> ������� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateWeather(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
     			
        param.setValueParamter(i++, record.getAttribute("RACE_DAY")	);		// ������   
        param.setValueParamter(i++, record.getAttribute("WEATHER")	);		// ����        
        param.setValueParamter(i++, record.getAttribute("TEMP")	    );		// �µ�
        param.setValueParamter(i++, record.getAttribute("WIN_DIRT")	);		// ǳ��
        param.setValueParamter(i++, record.getAttribute("WIN_SPEED"));		// ǳ��        
        param.setValueParamter(i++, record.getAttribute("WATR_TEMP"));		// ����
        param.setValueParamter(i++, record.getAttribute("INPT_TIME"));		// �Է½ð� 
        param.setValueParamter(i++, SESSION_USER_ID					);		// �����ID(�ۼ���)
		
        int dmlcount = this.getDao("rbmdao").update("rsm2080_u01", param);
        
        return dmlcount;
    }    

    /**
     * <p> ������� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteWeather(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
     			
        param.setWhereClauseParameter(i++, record.getAttribute("RACE_DAY")	);		// ������   
		
        int dmlcount = this.getDao("rbmdao").update("rsm2080_d01", param);
        
        return dmlcount;
    }    
    

    /**
     * <p> Ư�̻���, ��Ȳ���� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateSpecDesc(PosRecord record) 
    {
    	String sParkingCnt = "";
        PosParameter param = new PosParameter();   
        
        int i = 0;
     			
        param.setValueParamter(i++, record.getAttribute("MEET_CD")	  );		// ������ڵ�        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")  );		// ���س⵵
        param.setValueParamter(i++, record.getAttribute("TMS")	      );		// ȸ��
        param.setValueParamter(i++, record.getAttribute("DAY_ORD")    );		// ����        
        param.setValueParamter(i++, record.getAttribute("SPEC_DESC")  );		// Ư�̻���
        param.setValueParamter(i++, record.getAttribute("RACE_CNTNT") );		// ���ֻ�Ȳ 
        
        sParkingCnt = record.getAttribute("PARKING_CNT").toString();
        if (sParkingCnt != null) {
        	sParkingCnt = sParkingCnt.replaceAll(",", "");
        }
        param.setValueParamter(i++, sParkingCnt);								// ���� ������� 
        param.setValueParamter(i++, SESSION_USER_ID					  );		// �����ID(�ۼ���)
		
        int dmlcount = this.getDao("rbmdao").update("rsm2080_u02", param);
        
        return dmlcount;
    }   
    
    
}
