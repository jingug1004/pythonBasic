/*================================================================================
 * �ý���			: ��� ���� 
 * �ҽ����� �̸�	: snis.boa.RacePrizment.e06010010.activity.CalcRacePriz.java
 * ���ϼ���		: ���� ���ޱ��� 
 * �ۼ���			: �輺�� 
 * ����			: 1.0.0
 * ��������		: 2007-11-22
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.financial.e07020050.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
 * ȸ���� ���� ��� ��� �Ѵ�.
 * @auther �輺�� 
 * @version 1.0
 */
public class CalcRacePriz extends SnisActivity
{    
	public CalcRacePriz()
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
    	calcRacePriz(ctx);
        return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    protected void calcRacePriz(PosContext ctx) 
    {
    	String stndYear = (String)ctx.get("STND_YEAR");
    	String mbrCd    = (String)ctx.get("MBR_CD");
    	Integer tms     = new Integer((String)ctx.get("TMS"));
    	
    	deleteRacePriz(stndYear, mbrCd, tms);
    	calcRacePriz(stndYear, mbrCd, tms) ;
    	
    	//ó�������� Update
    	new SaveRacePriz().updateTmsRacePrizStat(stndYear, mbrCd, tms, "N");
        
    }

    /**
     * <p> ȸ�� �� ������� ���   Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int calcRacePriz(String stndYear, String mbrCd, Integer tms) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, stndYear);  
        param.setValueParamter(i++, mbrCd);  
        param.setValueParamter(i++, tms);  
        param.setValueParamter(i++, stndYear);  
        param.setValueParamter(i++, mbrCd);  
        param.setValueParamter(i++, tms);  
        param.setValueParamter(i++, stndYear);  
        param.setValueParamter(i++, mbrCd);  
        param.setValueParamter(i++, tms);  
        param.setValueParamter(i++, stndYear);  
        param.setValueParamter(i++, stndYear);  
        
        return this.getDao("boadao").update("tbeg_race_priz_if001", param);
    }

    /**
     * <p>ȸ���� ���� ��� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int deleteRacePriz(String stndYear, String mbrCd, Integer tms) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, stndYear);  
        param.setValueParamter(i++, mbrCd);  
        param.setValueParamter(i++, tms);  
        return this.getDao("boadao").update("tbeg_race_priz_df001", param);
    }
}
