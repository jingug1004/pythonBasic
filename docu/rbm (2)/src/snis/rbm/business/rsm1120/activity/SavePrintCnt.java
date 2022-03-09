/*
 * ================================================================================
 * �ý��� : 
 * ���� �ҽ����� �̸� : snis.rbm.system.rsm1070.activity.SaveSalesRefund.java ����
 * ���� : ȯ�Ҿ� ����
 * �ۼ��� : ������
 * ���� : 1.0.0 
 * �������� : 2011- 
 * ������������ : 
 * ���������� : 
 * ������������ :
 * =================================================================================
 */
package snis.rbm.business.rsm1120.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SavePrintCnt extends SnisActivity {

	public SavePrintCnt(){
		
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

        String sEventDt = (String)ctx.get("EVENT_DT");
        String sGrnCardNo = (String)ctx.get("CARD_NO");
        String sGrnCardSeq = (String)ctx.get("CARD_SEQ");
        String sMinSlipSeq = (String)ctx.get("MIN_SLIP_SEQ");
        
        nTempCnt = savePrintCnt(sEventDt, sGrnCardNo, sGrnCardSeq, sMinSlipSeq);
		        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> �����ƿ�Ȱ�����  �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int savePrintCnt(String sEventDt, String sGrnCardNo, String sGrnCardSeq, String sMinSlipSeq) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, SESSION_USER_ID);	// �����ID 
        param.setValueParamter(i++, sEventDt);			// ��������
        param.setValueParamter(i++, sGrnCardNo);		// �׸�ī���ȣ
        param.setValueParamter(i++, sGrnCardSeq);		// �׸�ī�� ����
                
        int dmlcount = this.getDao("rbmdao").update("rsm1120_u01", param);
        
        return dmlcount;
    }
    

}
