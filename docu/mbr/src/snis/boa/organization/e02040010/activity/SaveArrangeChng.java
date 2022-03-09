/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02030030.activity.SaveArrangeChng.java
 * ���ϼ���		: ����/��񺯰���
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02040010.activity;

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
* �����Ͽ� ����/��񺯰渦 ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SaveArrangeChng extends SnisActivity
{    
	private String sStndYear = "";
	private String sMbrCd    = "";
	private String sTms      = "";
	private String sDayOrd   = "";
	private int    nSeq      = 0;
	
    public SaveArrangeChng()
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
    	
        PosDataset ds;
        int        nSize   = 0;
        String     sDsName = "";
        
        sDsName = "dsOutArrange";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ )
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
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
    	int    nSaveCount   = 0; 
    	int    nDeleteCount = 0; 
        String sDsName      = "";

    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        sStndYear = Util.nullToStr((String) ctx.get("STND_YEAR".trim()));
        sMbrCd    = Util.nullToStr((String) ctx.get("MBR_CD   ".trim()));
        sTms      = Util.nullToStr((String) ctx.get("TMS      ".trim()));
        sDayOrd   = Util.nullToStr((String) ctx.get("DAY_ORD  ".trim()));
        sDayOrd   = "0";
        
        // ����/��񺯰� ����
        sDsName = "dsOutArrange";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
	        nSeq = getArrangeChngSeq();
            // ����
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
            	nTempCnt = updateArrange(record, ctx);
            	if (nTempCnt == 0) {
            		// ������ �����ϴ� �ڷᰡ ����(��������� �ٽ� �ϰų� ������ �����)
	    			throw new RuntimeException("����/��Ʈ ������ �����Ǿ����ϴ�.\n�ٽ� ��ȸ�� �۾��ϼ���!");
            	}
            	nTempCnt = updateRacerRaceAlloc(record);
            	nTempCnt = insertArrangeChg(record);
            	nTempCnt = updateEquipLot(record);

            	nSaveCount = nSaveCount + nTempCnt;
	        }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> �����̷� �Ϸù�ȣ ��ȸ </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int getArrangeChngSeq()
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sMbrCd   );
        param.setWhereClauseParameter(i++, sTms     );
        param.setWhereClauseParameter(i++, sDayOrd  );
        PosRowSet rowset = this.getDao("boadao").find("tbeb_arrange_chg_fb001", param);

        BigDecimal nSeq = null;
        if (rowset.hasNext())
        {
            PosRow row = rowset.next();
            nSeq = (BigDecimal) row.getAttribute("SEQ".trim());
        }
            
        return nSeq.intValue();
    }

    /**
     * <p> �ּ����� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateArrange(PosRecord record, PosContext ctx)
    {
    	//
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MOT_NO  ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("BOAT_NO ".trim())));
        
        if ( record.getAttribute("SPEC").equals("1")) {
        	param.setValueParamter(i++, ctx.get("SPEC_CD"));
        } else {
        	param.setValueParamter(i++, null);
        }
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, Util.trim(record.getAttribute("ONLINE_YN ".trim())));

        i = 0;
        param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sMbrCd   );
        param.setWhereClauseParameter(i++, sTms     );
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ORG_RACER_NO".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ORG_MOT_NO".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ORG_BOAT_NO".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_arrange_ub002", param);

        return dmlcount;
    }

    /**
     * <p> �ּ���������Ƚ�� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRacerRaceAlloc(PosRecord record)
    {
    	int nUpdate = 0;
    	for ( int j = 1; j <= 7; j++ ) {
    		String sOnlineYn = (String)Util.trim(record.getAttribute("ONLINE_YN"));
    		String sRaceCnt  = (Util.trim(record.getAttribute("RACE_CNT_" + j))).toString();
    		String sOnlineCnt  = "0";
    		if("1".equals(sOnlineYn))	//�¶����� üũ�Ǿ��� ��� 
    			sOnlineCnt = sRaceCnt;

    		PosParameter paramAlloc = new PosParameter();
            int i = 0;
            paramAlloc.setValueParamter(i++, sRaceCnt);
            paramAlloc.setValueParamter(i++, sStndYear);
            paramAlloc.setValueParamter(i++, sMbrCd   );
            paramAlloc.setValueParamter(i++, sTms     );
            paramAlloc.setValueParamter(i++, Integer.toString(j));
            paramAlloc.setValueParamter(i++, Util.trim(record.getAttribute("ORG_RACER_NO".trim())));
            
            paramAlloc.setValueParamter(i++, sOnlineCnt);
            paramAlloc.setValueParamter(i++, sStndYear);
            paramAlloc.setValueParamter(i++, sMbrCd   );
            paramAlloc.setValueParamter(i++, sTms     );
            paramAlloc.setValueParamter(i++, Integer.toString(j));
            paramAlloc.setValueParamter(i++, Util.trim(record.getAttribute("ORG_RACER_NO".trim())));

            paramAlloc.setValueParamter(i++, SESSION_USER_ID );
            i = 0;
            paramAlloc.setWhereClauseParameter(i++, sStndYear);
            paramAlloc.setWhereClauseParameter(i++, sMbrCd   );
            paramAlloc.setWhereClauseParameter(i++, sTms     );
            paramAlloc.setWhereClauseParameter(i++, Integer.toString(j));
            paramAlloc.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ORG_RACER_NO".trim())));

            nUpdate += this.getDao("boadao").update("tbeb_racer_race_alloc_ub003", paramAlloc);
    	}
    	
    	return nUpdate;
/*
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sMbrCd   );
        param.setWhereClauseParameter(i++, sTms     );
        param.setWhereClauseParameter(i++, null     );
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ORG_RACER_NO".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_racer_race_alloc_ub001", param);
        
        return dmlcount;
 */
    }

    /**
     * <p> �����̷� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertArrangeChg(PosRecord record) 
    {
    	int dmlcount = 0;
    	if ( record.getAttribute("ORG_RACER_NO") != null && record.getAttribute("RACER_NO") != null ) {
	    	if ( !record.getAttribute("ORG_RACER_NO").equals(record.getAttribute("RACER_NO")) ) {
		        PosParameter param = new PosParameter();
		        int i = 0;
		        param.setValueParamter(i++, sStndYear);
		        param.setValueParamter(i++, sMbrCd   );
		        param.setValueParamter(i++, sTms     );
		        param.setValueParamter(i++, sDayOrd  );
		        param.setValueParamter(i++, Integer.toString(nSeq++));
		        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO       ".trim())));
		        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_REG_NO   ".trim())));
		        param.setValueParamter(i++, "R"                                                    );
		        param.setValueParamter(i++, Util.trim(record.getAttribute("ORG_RACER_NO  ".trim())));
		        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO      ".trim())));
		        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_RSN     ".trim())));
		        param.setValueParamter(i++, SESSION_USER_ID );
		        param.setValueParamter(i++, SESSION_USER_ID );
		
		        dmlcount = this.getDao("boadao").update("tbeb_arrange_chg_ib001", param);
		        nSeq++;
	    	}
    	}
    	
    	if ( record.getAttribute("ORG_MOT_NO") != null && record.getAttribute("MOT_NO") != null ) {
        	if ( !record.getAttribute("ORG_MOT_NO").equals(record.getAttribute("MOT_NO")) ) {
		        PosParameter param = new PosParameter();
		        int i = 0;
		        param.setValueParamter(i++, sStndYear);
		        param.setValueParamter(i++, sMbrCd   );
		        param.setValueParamter(i++, sTms     );
		        param.setValueParamter(i++, sDayOrd  );
		        param.setValueParamter(i++, Integer.toString(nSeq++));
		        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO       ".trim())));
		        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_REG_NO   ".trim())));
		        param.setValueParamter(i++, "M"                                                    );
		        param.setValueParamter(i++, Util.trim(record.getAttribute("ORG_MOT_NO    ".trim())));
		        param.setValueParamter(i++, Util.trim(record.getAttribute("MOT_NO        ".trim())));
		        param.setValueParamter(i++, Util.trim(record.getAttribute("MOT_RSN       ".trim())));
		        param.setValueParamter(i++, SESSION_USER_ID );
		        param.setValueParamter(i++, SESSION_USER_ID );
		
		        dmlcount = this.getDao("boadao").update("tbeb_arrange_chg_ib001", param);
		        nSeq++;
	    	}
    	}

    	if ( record.getAttribute("ORG_BOAT_NO") != null && record.getAttribute("BOAT_NO") != null ) {
        	if ( !record.getAttribute("ORG_BOAT_NO").equals(record.getAttribute("BOAT_NO")) ) {
		        PosParameter param = new PosParameter();
		        int i = 0;
		        param.setValueParamter(i++, sStndYear);
		        param.setValueParamter(i++, sMbrCd   );
		        param.setValueParamter(i++, sTms     );
		        param.setValueParamter(i++, sDayOrd  );
		        param.setValueParamter(i++, Integer.toString(nSeq++));
		        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO       ".trim())));
		        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_REG_NO   ".trim())));
		        param.setValueParamter(i++, "B"                                                    );
		        param.setValueParamter(i++, Util.trim(record.getAttribute("ORG_BOAT_NO   ".trim())));
		        param.setValueParamter(i++, Util.trim(record.getAttribute("BOAT_NO       ".trim())));
		        param.setValueParamter(i++, Util.trim(record.getAttribute("BOAT_RSN      ".trim())));
		        param.setValueParamter(i++, SESSION_USER_ID );
		        param.setValueParamter(i++, SESSION_USER_ID );
		
		        dmlcount = this.getDao("boadao").update("tbeb_arrange_chg_ib001", param);
		        nSeq++;
	    	}
    	}
		
    	return dmlcount;
    }

    /**
     * <p> ��񺯰� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateEquipLot(PosRecord record) 
    {
    	int dmlcount = 0;
    	// ���� ���� ����
    	if ( record.getAttribute("ORG_MOT_NO") != null && record.getAttribute("MOT_NO") != null ) {
	    	if ( !record.getAttribute("ORG_MOT_NO").equals(record.getAttribute("MOT_NO")) ) {
		        PosParameter param = new PosParameter();
		        int i = 0;
		        param.setValueParamter(i++, SESSION_USER_ID );
	
		        i = 0;
		        param.setWhereClauseParameter(i++, sStndYear);
		        param.setWhereClauseParameter(i++, sMbrCd   );
		        param.setWhereClauseParameter(i++, sTms     );
		        param.setWhereClauseParameter(i++, "M"      );
		        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("MOT_NO      ".trim())));
	
		        dmlcount = this.getDao("boadao").update("tbef_equip_lot_ub001", param);
	    	}
    	}

    	// ��Ʈ ���� ����
    	if ( record.getAttribute("ORG_BOAT_NO") != null && record.getAttribute("BOAT_NO") != null ) {
	    	if ( !record.getAttribute("ORG_BOAT_NO").equals(record.getAttribute("BOAT_NO")) ) {
		        PosParameter param = new PosParameter();
		        int i = 0;
		        param.setValueParamter(i++, SESSION_USER_ID );
	
		        i = 0;
		        param.setWhereClauseParameter(i++, sStndYear);
		        param.setWhereClauseParameter(i++, sMbrCd   );
		        param.setWhereClauseParameter(i++, sTms     );
		        param.setWhereClauseParameter(i++, "B"      );
		        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("BOAT_NO     ".trim())));
	
		        dmlcount = this.getDao("boadao").update("tbef_equip_lot_ub001", param);
	    	}
    	}

    	return dmlcount;
    }
}
