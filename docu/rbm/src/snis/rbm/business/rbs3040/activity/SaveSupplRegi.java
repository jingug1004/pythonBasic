/*================================================================================
 * �ý���			: �Ҹ�ǰ����  ����
 * �ҽ����� �̸�	: snis.rbm.business.rbs3040.activity.SaveSupplRegi.java
 * ���ϼ���		: �Ҹ�ǰ���� ����
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-10-01
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
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

public class SaveSupplRegi extends SnisActivity {
	
	public SaveSupplRegi(){}

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
    	
    	String sReqDt      = (String)ctx.get("REQ_DT");	   //��û����
        String sAppliId    = (String)ctx.get("REQ_ID");  //��û�ڻ��
        String sSeq        = (String)ctx.get("SEQ");	   //����
        String sSupplCd    = (String)ctx.get("SUPPL_CD");  //�Ҹ�ǰ�ڵ�
        String sSignDt     = (String)ctx.get("SIGN_DT");   //��������
        String sRealReciId = (String)ctx.get("USER_ID");   //������ID
    	
        int nSignDtCheck = selectSignDt(sReqDt, sAppliId, sSeq, sSupplCd);
        
        if( nSignDtCheck == 1 ) {
        	//�������ڰ� ���� ���
        	nSaveCount = saveSupplRegi(sReqDt, sAppliId, sSeq, sSupplCd, sSignDt, sRealReciId);           
        } else {
        	//�������ڰ� �̹� ������ ���
        	Util.setSvcMsgCode(ctx, "SNIS_RBM_E011");     
        }
        
        Util.setSaveCount  (ctx, nSaveCount);
    }

    /**
     * <p> �Ҹ�ǰ���� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveSupplRegi(String sReqDt,String sAppliId,String sSeq,String sSupplCd,String sSignDt,String sRealReciId) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, sRealReciId);		//�Ǽ����λ��
        param.setValueParamter(i++, sSignDt);			//��������
        param.setValueParamter(i++, SESSION_USER_ID);	//�����ID(������)
        
        i = 0;
        param.setWhereClauseParameter(i++, sReqDt);			//��û����
        param.setWhereClauseParameter(i++, sAppliId);			//��û�ڻ��
        param.setWhereClauseParameter(i++, sSeq);				//����
        param.setWhereClauseParameter(i++, sSupplCd);			//�Ҹ�ǰ�ڵ�
        		
        int dmlcount = this.getDao("rbmdao").update("rbs3040_u01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> �Ҹ�ǰ���� �������� ��ȸ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int selectSignDt(String sReqDt, String sAppliId, String sSeq, String sSupplCd) 
    {	
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, sReqDt);    //��û����
        param.setWhereClauseParameter(i++, sAppliId);  //��û��ID
        param.setWhereClauseParameter(i++, sSeq);      //����
        param.setWhereClauseParameter(i++, sSupplCd);  //�Ҹ�ǰ�ڵ�
        
        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbs3040_s02", param);  
        PosRow pr[] = rtnRecord.getAllRow();        
        
        String rtnQty = String.valueOf(pr[0].getAttribute("CNT"));
        
        if( rtnQty == null )	rtnQty = "-1";
        
        return Integer.valueOf(rtnQty).intValue();
    }
}