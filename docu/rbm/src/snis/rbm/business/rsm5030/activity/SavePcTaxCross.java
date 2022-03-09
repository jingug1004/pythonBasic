/*================================================================================
 * �ý���			: ������������
 * �ҽ����� �̸�	: snis.rbm.business.rsm5010.activity.SavePayCntnt
 * ���ϼ���		: ������������_�󼼳��� ����
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-10-07
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rsm5030.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SavePcTaxCross extends SnisActivity {
	
	public SavePcTaxCross(){}

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
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
        
        sDsName = "dsPcTax";
        
        String sPayYear = (String)ctx.get("PAY_YEAR");
        String sMeetCd = (String)ctx.get("MEET_CD");
        String sSellCd = (String)ctx.get("SELL_CD");
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        //verifyPcTax(ds);	//������ ����
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            // ���� ���� ����
	            if(i==0)	deletePcTaxAll(record, sPayYear, sMeetCd, sSellCd);
	            
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {
		        	
		        	nTempCnt = savePcTax(record);	//��÷���� ����
		        	saveDetlCntnt(record);			//�󼼳��� ����
		        	saveCfmCntnt(record);			//Ȯ������ ����
		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }		        
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount);
    }
    
    /**
     * <p> ������������_Ȯ������ ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int savePcTax(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("MEET_CD"));	//������ڵ�
        param.setValueParamter(i++, record.getAttribute("SELL_CD"));	//�ó�ڵ�
        param.setValueParamter(i++, record.getAttribute("TSN"));	    //���ֱǹ�ȣ
        param.setValueParamter(i++, record.getAttribute("PAY_YEAR"));	//���޳⵵
        param.setValueParamter(i++, record.getAttribute("CUST_SSN"));   //�ֹε�Ϲ�ȣ
        
        param.setValueParamter(i++, record.getAttribute("REFUND"));  	//ȯ�ޱݾ�
        param.setValueParamter(i++, record.getAttribute("SELL_AMT"));	//�Ǹűݾ�
        param.setValueParamter(i++, record.getAttribute("JIGEUP_AMT"));	//���ޱݾ�
        param.setValueParamter(i++, record.getAttribute("COST"));		//�ʿ���
        param.setValueParamter(i++, record.getAttribute("GITA1"));		//�ҵ漼
        
        param.setValueParamter(i++, record.getAttribute("GITA2"));		//�ֹμ�
        param.setValueParamter(i++, record.getAttribute("GITA_PAY"));	//�������ݾ�
        param.setValueParamter(i++, record.getAttribute("JIGEUP_DT"));	//��������
        param.setValueParamter(i++, record.getAttribute("F_SSN"));		//�ܱ��ε�Ϲ�
        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(�ۼ���)

        int dmlcount = this.getDao("rbmdao").update("rsm5030_i01", param);
	
        return dmlcount;
    }
    
    /**
     * <p> ������������_Ȯ������ ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveCfmCntnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("MEET_CD"));	//������ڵ�
        param.setValueParamter(i++, record.getAttribute("SELL_CD"));	//�ó�ڵ�
        param.setValueParamter(i++, record.getAttribute("TSN"));	    //���ֱǹ�ȣ
        param.setValueParamter(i++, record.getAttribute("PAY_YEAR"));	//���޳⵵
        param.setValueParamter(i++, record.getAttribute("CUST_SSN"));   //�ֹε�Ϲ�ȣ
        
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));  	//�����ڵ�
        param.setValueParamter(i++, "002");								//1��Ȯ���ڵ�
        param.setValueParamter(i++, record.getAttribute("SND_CFM_CD"));	//2��Ȯ���ڵ�
        param.setValueParamter(i++, record.getAttribute("THR_CFM_CD"));	//3��Ȯ���ڵ�
        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(�ۼ���)
        
        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(������)
                		
        int dmlcount = this.getDao("rbmdao").update("rsm5010_m02", param);
	
        return dmlcount;
    }
    
    
    /**
     * <p> ������������_�󼼳��� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveDetlCntnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("MEET_CD"));	//������ڵ�
        param.setValueParamter(i++, record.getAttribute("SELL_CD"));	//�ó�ڵ�
        param.setValueParamter(i++, record.getAttribute("TSN"));	    //���ֱǹ�ȣ
        param.setValueParamter(i++, record.getAttribute("PAY_YEAR"));	//���޳⵵
        param.setValueParamter(i++, record.getAttribute("CUST_SSN"));   //�ֹε�Ϲ�ȣ
        
        param.setValueParamter(i++, record.getAttribute("CUST_NM"));	//������
        param.setValueParamter(i++, record.getAttribute("CUST_ADDR"));	//���ּ�
        param.setValueParamter(i++, record.getAttribute("FORE_NO"));	//�ܱ��ε�Ϲ�ȣ
        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(�ۼ���)
        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(������)

        int dmlcount = this.getDao("rbmdao").update("rsm5010_m01", param);
	
        return dmlcount;
    }
    
    /**
     * <p> ��÷���޳��� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deletePcTaxAll(PosRecord record, String sPayYear, String sMeetCd, String SellCd) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, sPayYear);	//���޳⵵
        param.setValueParamter(i++, sMeetCd);	//����ó
        param.setValueParamter(i++, SellCd);	//�Ǹ�ó
        
        int dmlcount = this.getDao("rbmdao").update("rsm5030_d01", param);
        this.getDao("rbmdao").update("rsm5030_d02", param);
        this.getDao("rbmdao").update("rsm5030_d03", param);

        return dmlcount;
    }
    
    
    /**
     * <p> ������������_������ ���� </p>
     * @param   PosDataset	ds	  ����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  int	        nRtn  0:�̻���� -1:�̻�
     * @throws  none
     */
    protected String verifyPcTax(PosDataset ds) {
    	String sMeetCd, sSellCd, sTns, sCustSsn, sJigeupDt, sJigeupYear, sCheckJigeup = "";
    	String sRtn = "";
    	
    	for ( int i = 0; i < ds.getRecordCount(); i++ ) {
    		PosRecord record = ds.getRecord(i);
    		
    		sMeetCd     = (String)record.getAttribute("MEET_CD");
			sSellCd     = (String)record.getAttribute("SELL_CD");
			sTns        = (String)record.getAttribute("TNS");
			sCustSsn    = (String)record.getAttribute("CUST_SSN");
			sJigeupDt   = (String)record.getAttribute("JIGEUP_DT");
			sJigeupYear = sJigeupDt.substring(0, 4);
			
			if(i == 0)	sCheckJigeup = sJigeupYear;
			
			if( !sCheckJigeup.equals(sJigeupYear) ) {
				//�߸��� ���޳⵵
			}
			
			if(sMeetCd.equals("001") || sMeetCd.equals("002") ||
			   sMeetCd.equals("003") || sMeetCd.equals("004") ) {
			} else {
				//�߸��� MEET_CD
			}
			
			if(sSellCd.equals("02") || sSellCd.equals("04")) {	
			} else {
				//�߸��� SELL_CD
			}
			
			if( sTns.length() != 14) {
				//�߸��� TSN
			}
			
			if( sCustSsn.length() != 14) {
				//�߸��� �ֹι�ȣ
			}
			
			if( !Util.checkDate(sJigeupDt) ) {
				//�߸��� ��������
			}
    	}
    	return sRtn;
    }
}
