/*================================================================================
 * �ý���			: �ý��� ���� 
 * �ҽ����� �̸�	: snis.boa.system.e01010140.activity.SaveRaceMovie.java
 * ���ϼ���		: ���ֵ����� ���
 * �ۼ���			: ���μ� 
 * ����			: 1.0.0
 * ��������		: 2008-08-29
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.system.e01010170.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* ����Ȯ������ ��� ���� ���� ���, ���� �Ѵ�.
* @auther �ѿ��� 
* @version 1.0
*/
public class SaveRaceMovie extends SnisActivity
{    
    public SaveRaceMovie()
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
        
        ds    = (PosDataset) ctx.get("dsOutRaceMovie");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            
            nSaveCount = saveRaceMovie(record);
        }

        Util.setSaveCount  (ctx, nSaveCount     );
    }


    /**
     * <p> ����Ȯ������ ��� ����  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveRaceMovie(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("INTRO_MOVIE"));
        param.setValueParamter(i++, record.getAttribute("RACE_MOVIE"));
        
        return this.getDao("boadao").update("tbea_race_movie_ma001", param);
    }
}
