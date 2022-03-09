/*================================================================================
 * �ý���			: �ý��۰���
 * �ҽ����� �̸�	: snis.boa.system.e01010220.activity.SaveProcess.java
 * ���ϼ���		: �����Ȳ���
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.system.e01010220.activity;

import java.math.BigDecimal;
import java.util.HashMap;

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
* �����Ͽ� ������/������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SaveProcess extends SnisActivity
{    
    public SaveProcess()
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

        // ����������Ȳ��� ����
        ds    = (PosDataset)ctx.get("dsOutProcess");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE)
            {
                // update
            	nSaveCount = nSaveCount + updateProcess(record);
            }
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
    }

    /**
     * <p> update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    public int updateProcess(PosRecord record)
    {
        PosParameter param = new PosParameter();
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("PROG_DESC"));

        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("MBR_CD"   ));
        param.setValueParamter(i++, record.getAttribute("SEQ_NO"   ));

    	int dmlcount = this.getDao("boadao").insert("tbea_duty_prs_hst_ua001", param);
        return dmlcount;
    }

    /**
     * <p> Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    public int saveProcess(String sStndYear
				        	, String sMbrCd   
				        	, String sMonth     
				        	, String sTms     
				        	, String sDayOrd  
				        	, String sRaceNo  
				        	, String sDutyCd  
				        	, String sWorkCd  
				        	, String sProgStat
				        	, String sProgDesc
				        	, String sUserId)
    {
        PosParameter paramSeq = new PosParameter();
    	
        int i = 0;
        paramSeq.setWhereClauseParameter(i++, Util.trim(sStndYear));
        paramSeq.setWhereClauseParameter(i++, Util.trim(sMbrCd   ));
    	
        PosRowSet rowsetSeq = this.getDao("boadao").find("tbea_duty_prs_hst_fa000", paramSeq);
        PosRow row = (PosRow) rowsetSeq.next();
        BigDecimal nSeqNo = (BigDecimal) row.getAttribute("SEQ_NO");
        
        PosParameter param = new PosParameter();
        
        i = 0;
        param.setValueParamter(i++, Util.trim(sStndYear));
        param.setValueParamter(i++, Util.trim(sMbrCd   ));
        param.setValueParamter(i++, nSeqNo              );
        param.setValueParamter(i++, Util.trim(sMonth   ));
        param.setValueParamter(i++, Util.trim(sTms     ));
        param.setValueParamter(i++, Util.trim(sDayOrd  ));
        param.setValueParamter(i++, Util.trim(sRaceNo  ));
        param.setValueParamter(i++, Util.trim(sDutyCd  ));
        param.setValueParamter(i++, Util.trim(sWorkCd  ));
        param.setValueParamter(i++, Util.trim(sProgStat));
        param.setValueParamter(i++, Util.trim(sProgDesc));
        param.setValueParamter(i++, Util.NVL(sUserId, SESSION_USER_ID) );

        int dmlcount = this.getDao("boadao").insert("tbea_duty_prs_hst_ia001", param);
        return dmlcount;
    }


    /**
     * <p> Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    public int saveProcess(HashMap hmProcess)
    {
    	return saveProcess(   Util.trim(hmProcess.get("STND_YEAR".trim()))
			                , Util.trim(hmProcess.get("MBR_CD   ".trim()))
			                , Util.trim(hmProcess.get("MONTH    ".trim()))
			                , Util.trim(hmProcess.get("TMS      ".trim()))
			                , Util.trim(hmProcess.get("DAY_ORD  ".trim()))
			                , Util.trim(hmProcess.get("RACE_NO  ".trim()))
			                , Util.trim(hmProcess.get("DUTY_CD  ".trim()))
			                , Util.trim(hmProcess.get("WORK_CD  ".trim()))
			                , Util.trim(hmProcess.get("PROG_STAT".trim()))
			                , Util.trim(hmProcess.get("PROG_DESC".trim()))
			                , Util.trim(hmProcess.get("USER_ID  ".trim())));
    }

	/**
     * <p> ����ȸ�� ���� ��ȸ   </p>
     * @param   PosGenericDao dao			DB Connect ����
     *          String        sStndYear		���ֳ⵵
     *          String        sMbrCd		�������ڵ�
     *          String        sTms			ȸ��
     * @return  sRaceTmsSTatCd	String		ȸ������ 
     * @throws  none
     */
    public String getRaceTmsStatCd(String sStndYear, String sMbrCd, String sTms)
    {
        String sRaceTmsSTatCd = "";
        PosParameter param = new PosParameter();

        int i = 0;
		param.setWhereClauseParameter(i++, sStndYear); 	// ���ֳ⵵
        param.setWhereClauseParameter(i++, sMbrCd); 	// �������ڵ�
        param.setWhereClauseParameter(i++, sTms); 		// ȸ��
        
        PosRowSet keyRecord = this.getDao("boadao").find("tbeb_race_tms_fb003", param);        

        PosRow pr[] = keyRecord.getAllRow();        
        sRaceTmsSTatCd = String.valueOf(pr[0].getAttribute("RACE_TMS_STAT_CD"));
        
        return sRaceTmsSTatCd;
    }

	/**
     * <p> ����ȸ�� ������÷��� ���� ��ȸ   </p>
     * @param   PosGenericDao dao			DB Connect ����
     *          String        sStndYear		���ֳ⵵
     *          String        sMbrCd		�������ڵ�
     *          String        sTms			ȸ��
     * @return  sRaceTmsSTatCd	String		ȸ������ 
     * @throws  none
     */
    public String getEquipDrwltCmplYn(String sStndYear, String sMbrCd, String sTms)
    {
        String sRaceTmsSTatCd = "";
        PosParameter param = new PosParameter();

        int i = 0;
		param.setWhereClauseParameter(i++, sStndYear); 	// ���ֳ⵵
        param.setWhereClauseParameter(i++, sMbrCd); 	// �������ڵ�
        param.setWhereClauseParameter(i++, sTms); 		// ȸ��
        
        PosRowSet keyRecord = this.getDao("boadao").find("tbeb_race_tms_fb004", param);        

        PosRow pr[] = keyRecord.getAllRow();        
        sRaceTmsSTatCd = String.valueOf(pr[0].getAttribute("EQUIP_DRWLT_CMPL_YN"));
        
        return sRaceTmsSTatCd;
    }
}
