/*================================================================================
 * �ý���			: ����(����)����
 * �ҽ����� �̸�	: snis.boa.racer.e03010010.activity.SaveRacerInfoFair.java
 * ���ϼ���		: ���������������
 * ��������		: 2009-01-22
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.racer.e03010010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ���������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @version 1.0
*/
public class SaveRacerInfoFair extends SnisActivity
{    
	protected String sRacerNo      = "";
	protected String sSessionUserId = "";
	
    public SaveRacerInfoFair()
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

        sRacerNo = (String) ctx.get("RACER_NO");
        ds    = (PosDataset) ctx.get("dsOutRacerFair");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	// update
            	nSaveCount = nSaveCount + saveRacer(record);
            }
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
    }

    /**
     * <p> ���������� Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveRacer(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt =updateRacer(record);

        return effectedRowCnt;    	
    }

    /**
     * <p> ������_�������� ��� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRacer(PosRecord record) 
    {
    	int dmlcount   = 0; 
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sRacerNo);
        param.setValueParamter(i++, Util.trim(record.getAttribute("SPEC_INFO")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("FORTUNE")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HELMET_PATH1")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HELMET_PATH2")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HELMET_PATH3")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HELMET_PATH4")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HELMET_PATH5")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("CLUB")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TWO_JOB")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("INSURANCE")));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, sRacerNo);
        param.setValueParamter(i++, Util.trim(record.getAttribute("SPEC_INFO")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("FORTUNE")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HELMET_PATH1")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HELMET_PATH2")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HELMET_PATH3")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HELMET_PATH4")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("HELMET_PATH5")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("CLUB")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TWO_JOB")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("INSURANCE")));
        param.setValueParamter(i++, SESSION_USER_ID);
        	
     	//������_�������� ���
        dmlcount = this.getDao("boadao").update("tbee_racer_detail_fair_ie001", param);
        
        return dmlcount;  
    }
   
}