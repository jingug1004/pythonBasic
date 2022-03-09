/*================================================================================
 * �ý���			: ������ ����
 * �ҽ����� �̸�	: snis.rbm.business.rbb5110.activity.SaveEstmRslt.java
 * ���ϼ���		: �Ի󳻿� ����
 * �ۼ���			: ���缱
 * ����			: 1.0.0
 * ��������		: 2012-11-10
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rev4020.activity;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

public class sendEstmRsltToHUM extends SnisActivity {
	
	public sendEstmRsltToHUM(){}

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
    	String estmTermNm="";
        
    	PosDataset ds;
        int nSize        = 0;

        // ����� ���ޱ���  ����
        sDsName = "dsEvEmp";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        

	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            if (i == 0) {
		            estmTermNm = getEstmTerm(record);   
	            }
	            nSaveCount += saveEstmRslt(record, estmTermNm);
		        
	        }	  
        }
        Util.setSaveCount  (ctx, nSaveCount  );
    }

    /**
     * <p> �Ի󳻿� �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveEstmRslt(PosRecord record, String estmTermNm) 
    {    	
        PosParameter param = new PosParameter();   
        int i = 0;
                
        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));		//�򰡳⵵
        param.setValueParamter(i++, estmTermNm);							//����, ��ݱ�, �Ϲݱ� 
        param.setValueParamter(i++, record.getAttribute("EMP_NO"));			//���
        param.setValueParamter(i++, record.getAttribute("TOT_GRD"));		//�򰡰��
        param.setValueParamter(i++, SESSION_USER_ID);					   	//�����ID(������)
                		
        int dmlcount = this.getDao("venisrbmdao").update("rev4020_m01", param);
                
        return dmlcount;
    }
    
    protected String getEstmTerm(PosRecord record) 
    {
        String rtnKey = "";
        
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));		//�򰡳⵵
        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));		//������
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rev4020_s03",param);        
        	
        PosRow pr[] = keyRecord.getAllRow();     
       
        rtnKey = String.valueOf(pr[0].getAttribute("ESTM_TERM_NM"));

        return rtnKey;
    }


}