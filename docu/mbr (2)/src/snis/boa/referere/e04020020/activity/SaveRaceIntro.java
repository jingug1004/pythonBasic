/*================================================================================
 * �ý���			: ���� ���� 
 * �ҽ����� �̸�	: snis.boa.referere.e04020020.activity.SaveRaceIntro.java
 * ���ϼ���		: ���� ���� 
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2007-12-22
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.referere.e04020020.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util; 

/**
* �Ұ����� Ȩ������ �����Ѵ�.
* @auther ������
* @version 1.0
*/ 
public class SaveRaceIntro  extends SnisActivity
{    
	
    public SaveRaceIntro ()
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

    	PosDataset ds;
        int nSize        = 0;
        
        //�������̺� ����	
        ds    = (PosDataset) ctx.get("dsOutRace");
        nSize = ds.getRecordCount();
        
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
              
           	nSaveCount = nSaveCount + SaveRaceIntro(record);
        }
    }
    /**
     * <p> �������̺� Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int SaveRaceIntro(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateIntro(record);
    	return effectedRowCnt;
    }
    
    /**
     * <p> �������̺�  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    
     protected int updateIntro(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, SESSION_USER_ID);
		
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TMS".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("DAY_ORD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO".trim())));
        
        
        return this.getDao("boadao").update("tbec_appo_race_result_detail_ud008", param);
        
    }
     
}