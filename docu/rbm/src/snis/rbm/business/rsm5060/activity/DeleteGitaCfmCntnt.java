/*================================================================================
 * �ý���			: ��Ÿ�ҵ漼 ���� Ȯ��
 * �ҽ����� �̸�	: snis.rbm.business.rsm5010.activity.SaveCfmCntnt
 * ���ϼ���		: ��Ÿ�ҵ漼 Ȯ������ ����
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-10-15
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rsm5060.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class DeleteGitaCfmCntnt extends SnisActivity {
	
	public DeleteGitaCfmCntnt(){}

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
        
        String sPayYear = (String)ctx.get("PAY_YEAR");	//���޳⵵
        String sPayMm   = (String)ctx.get("PAY_MM");	//���޿�
        
	    nTempCnt = deleteGitaCfmCntnt(sPayYear,sPayMm);
		
	    
		Util.setDeleteCount(ctx, nTempCnt);
    }

    /**
     * <p> ��Ÿ�ҵ漼 Ȯ������ ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteGitaCfmCntnt(String strYear, String strMon) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, strYear );	//��
        param.setValueParamter(i++, strMon	);	//��
                
        int dmlcount = this.getDao("rbmdao").update("rsm5060_d02", param);

        return dmlcount;
    }    
}
