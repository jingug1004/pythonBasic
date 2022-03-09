/*================================================================================
 * �ý���			: ��� ���� 
 * �ҽ����� �̸�	: snis.boa.PrizDepsBacment.e06010010.activity.SavePrizDepsBac.java
 * ���ϼ���		: ���/���� �������ѱ��� 
 * �ۼ���			: �輺�� 
 * ����			: 1.0.0
 * ��������		: 2007-11-22
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.financial.e07010070.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* ���/���� �������ѱ��� ���� ������ ���, ����, ���� �Ѵ�.
* @auther �輺�� 
* @version 1.0
*/
public class SavePrizDepsBac extends SnisActivity
{    
    public SavePrizDepsBac()
    {
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

    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutPrizDepsBac");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
           	nSaveCount = nSaveCount + savePrizDepsBac(record);
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ���/���� �������ѱ���Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int savePrizDepsBac(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updatePrizDepsBac(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertPrizDepsBac(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> ���/���� �������ѱ���  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updatePrizDepsBac(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
         
        param.setValueParamter(i++, record.getAttribute("PRIZ_AMT_RATE")); 
        param.setValueParamter(i++, record.getAttribute("ALLOW_AMT_RATE"));
        param.setValueParamter(i++, record.getAttribute("DESQUIL_YN").equals("1") ? "Y" : "N"); 
        param.setValueParamter(i++, record.getAttribute("RMK")); 
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("VOIL_CD"));
        return this.getDao("boadao").update("tbeg_priz_deps_bac_uf001", param);
    }
    
    

    /**
     * <p>���/���� �������ѱ��� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertPrizDepsBac(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
        param.setValueParamter(i++, record.getAttribute("VOIL_CD")); 
        param.setValueParamter(i++, record.getAttribute("PRIZ_AMT_RATE")); 
        param.setValueParamter(i++, record.getAttribute("ALLOW_AMT_RATE"));
        param.setValueParamter(i++, record.getAttribute("DESQUIL_YN").equals("1") ? "Y" : "N"); 
        param.setValueParamter(i++, record.getAttribute("RMK")); 
        param.setValueParamter(i++, SESSION_USER_ID);
        return this.getDao("boadao").update("tbeg_priz_deps_bac_if001", param);
    }
}
