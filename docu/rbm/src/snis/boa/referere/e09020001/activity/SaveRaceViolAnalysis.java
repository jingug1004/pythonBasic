/*================================================================================
 * �ý���			: ���� ���� 
 * �ҽ����� �̸�	: snis.boa.referere.e09020001.activity.SaveRaceViolAnalysis.java
 * ���ϼ���		: �������ֺм� ���� 
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2016-08-06
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.referere.e09020001.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util; 

/**
* ���ְ�� ������ ���� �Ѵ�.
* @auther ������
* @version 1.0
*/ 
public class SaveRaceViolAnalysis  extends SnisActivity
{    
	
    public SaveRaceViolAnalysis ()
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
        ds    = (PosDataset) ctx.get("dsRaceViolAnalysis");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
           	nSaveCount = nSaveCount + mergeViolAnalysis(record);
        }
    }

    /**
     * <p> �������ֺм� ���̺� insert </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		insert ���ڵ� ����
     * @throws  none
     */
    
     protected int mergeViolAnalysis(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TMS".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("DAY_ORD".trim())));        
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ANALYSIS_CONT_001".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ANALYSIS_CONT_002".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ANALYSIS_CONT_003".trim())));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").update("tbed_race_voil_analysis_m01", param);
        
    }
}