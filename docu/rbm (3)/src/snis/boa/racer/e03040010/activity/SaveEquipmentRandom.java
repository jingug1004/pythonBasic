/*================================================================================
 * �ý���		: ��������
 * �ҽ����� �̸�: snis.boa.racer.e03040010.activity.SaveEquipmentRandom.java
 * ���ϼ���		: ��÷��������  ���
 * �ۼ���		: ���ȭ
 * ����			: 1.0.0
 * ��������		: 2008-02-27
 * ������������	: 
 * ����������	: 
 * ������������	: 
=================================================================================*/
package snis.boa.racer.e03040010.activity;

import java.math.BigDecimal;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ��÷����������  ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ���ȭ
* @version 1.0
*/
public class SaveEquipmentRandom extends SnisActivity
{    
	
	public SaveEquipmentRandom()
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
    	String sStndYear = "";
    	String sMbrCd    = "";
    	String sTms      = "";
    	String sPrtType  = "";
    	String sExclYn   = "";
    	
    	int nSaveCount   = 0; 

    	PosDataset ds;
        int nSize        = 0;
        
        sStndYear = Util.nullToStr((String) ctx.get("STND_YEAR ".trim()));
        sMbrCd    = Util.nullToStr((String) ctx.get("MBR_CD    ".trim()));
        sTms      = Util.nullToStr((String) ctx.get("TMS       ".trim()));
        sPrtType  = Util.nullToStr((String) ctx.get("PRT_TYPE  ".trim()));
        sExclYn   = Util.nullToStr((String) ctx.get("EXCL_YN   ".trim()));
        

        if("001".equals(sPrtType)) { //������������
        	nSaveCount = saveEquipRandomRacer(sStndYear, sMbrCd, sTms, sPrtType, sExclYn);
        }
        if("002".equals(sPrtType) || "003".equals(sPrtType)) { // ���ͺ�Ʈ ��������
        	nSaveCount = saveEquipRandomMtBt(sStndYear, sMbrCd, sTms, sPrtType, sExclYn);
        }
        
        int iSeq      	= getSeq(ctx, sStndYear, sMbrCd, sTms, sPrtType, sExclYn);
        
        Util.setSaveCount  (ctx, nSaveCount     );
    }

    /**
     * <p> ��÷���� Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveEquipRandomRacer(String sStndYear, String sMbrCd, String sTms, String sPrtType, String sExclYn)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, sPrtType);
        param.setValueParamter(i++, sPrtType);
        //param.setValueParamter(i++, sPrtType);
        param.setValueParamter(i++, sStndYear);
        param.setValueParamter(i++, sMbrCd);
        param.setValueParamter(i++, sTms);
        
        return this.getDao("boadao").update("tbec_equip_prt_ic002", param);
    }


    /**
     * <p> ��÷���� Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveEquipRandomMtBt(String sStndYear, String sMbrCd, String sTms, String sPrtType, String sExclYn)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, sPrtType);
        //param.setValueParamter(i++, sPrtType);
        param.setValueParamter(i++, sStndYear);
        param.setValueParamter(i++, sMbrCd);
        param.setValueParamter(i++, sTms);
        param.setValueParamter(i++, sExclYn);
        param.setValueParamter(i++, sPrtType);
        
        return this.getDao("boadao").update("tbec_equip_prt_ic003", param);
    }
    
      
    /**
     * <p> ������������</p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int getSeq(PosContext ctx, String sStndYear, String sMbrCd, String sTms, String sPrtType, String sExclYn)
    {
    	PosParameter param = new PosParameter();    
    	int i = 0;
    	param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sMbrCd);
        param.setWhereClauseParameter(i++, sTms);
    	param.setWhereClauseParameter(i++, sPrtType);
    	param.setWhereClauseParameter(i++, sExclYn);
    	param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sMbrCd);
        param.setWhereClauseParameter(i++, sTms);
        param.setWhereClauseParameter(i++, sPrtType);
        
        PosRowSet rowset = this.getDao("boadao").find("tbec_equip_prt_fc001", param);
        
        BigDecimal nCnt = null;
        if (rowset.hasNext())
        {
            PosRow row = rowset.next();
            nCnt = (BigDecimal) row.getAttribute("PRT_SEQ".trim());
        }
            
        String sResultKey = "dsPrtSeq";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);  
        
        return nCnt.intValue();
    }    
}