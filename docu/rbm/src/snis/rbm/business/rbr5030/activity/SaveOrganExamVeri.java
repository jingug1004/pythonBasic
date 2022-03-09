/*================================================================================
 * �ý���		: ����ǥ �˼����� ����
 * �ҽ����� �̸�: snis.rbm.business.rbr5030.activity.SaveOrganExamBrnc.java
 * ���ϼ���		: SaveOrganExamBrnc
 * �ۼ���		: ������
 * ����			: 1.0.0
 * ��������		: 2016-03-24
 * ������������	: 
 * ����������	: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbr5030.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.RbmJdbcDao;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveOrganExamVeri extends SnisActivity {
	
	public SaveOrganExamVeri(){}
 
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
    	int nTempCnt	 = 0;
    	String sDsName   = "";
    	
    	PosDataset ds;
        
        sDsName = "dsOrganExamVeri";
       
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);		        	
		        	nTempCnt = saveOrganExamVeri(record);
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;		        
	        }	        
        }

        Util.setSaveCount  (ctx, nSaveCount  );

    }
    
    /**
     * <p> ����ǥ �˼� Ȯ�� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveOrganExamVeri(PosRecord record) 
    {
    	PosParameter param = new PosParameter();   
        
        int i = 0;

        String sCfmValue = (String)record.getAttribute("CFM_VALUE");
        String sCfmType  = (String)record.getAttribute("CFM_TYPE");
        String sStndyear = (String)record.getAttribute("STND_YEAR");
        String sStndMm   = (String)record.getAttribute("STND_MM");
        
        param.setValueParamter(i++, sStndyear);		//���س⵵
        param.setValueParamter(i++, sStndMm);		//���ؿ�                
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//�����ڵ�
        param.setValueParamter(i++, SESSION_USER_ID);						//�����ID(�ۼ���)
        param.setValueParamter(i++, sCfmValue);								//Ȯ������
      
        
        int dmlcount = 0;
        
        if("VERI".equals(sCfmType)) {
        	dmlcount = this.getDao("rbmdao").update("rbr5030_m02", param); //Ȯ����
        } else {
        	dmlcount = this.getDao("rbmdao").update("rbr5030_m01", param);	//�����
        	if(dmlcount > 0 && "Y".equals(sCfmValue)) sendSMS(SESSION_USER_ID, sStndyear, sStndMm);
        }
        
        return dmlcount;
    }
    
    protected void sendSMS(String sUserId, String sStndyear, String sStndMm) 
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        
        param.setWhereClauseParameter(i++, SESSION_USER_ID);						//�����ID(�ۼ���)
        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbr5030_s04", param);  
        
        PosRow pr[] = rtnRecord.getAllRow();
        String sRcvrId = String.valueOf(pr[0].getAttribute("USER_ID"));  
        
    	String sTitle = "���ֻ������ " + sStndyear+"�� " + sStndMm +"�� ����ǥ�μ⹰ �˼���û";
    	String sMesg  = "���ֻ������ [�������� > ����ǥ�˼� > �˼����� �Է�] ȭ�鿡�� ������ �ֽʽÿ�";
    	String sUrl = "http://rbm.kcycle.or.kr/";
    	
    	Util.sendMessenger(sUserId, sRcvrId, sTitle, sMesg, sUrl);
    }
}
