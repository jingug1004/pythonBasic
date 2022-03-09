/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02040050.activity.SaveRaceConfirm.java
 * ���ϼ���		: ��������
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02040050.activity;

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

import snis.boa.system.e01010220.activity.SaveProcess;
/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SaveRaceConfirm extends SnisActivity
{    
	private int    nSeq      = 0;
    public SaveRaceConfirm()
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
        
        for ( int j = 1; j <= 18; j++ ) {
	        sDsName = "dsOutRace_" + j;
	        if ( ctx.get(sDsName) != null ) {
		        ds    = (PosDataset)ctx.get(sDsName);
		        nSize = ds.getRecordCount();
		        for ( int i = 0; i < nSize; i++ ) 
		        {
		            PosRecord record = ds.getRecord(i);
		            logger.logInfo(record);
		        }
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
        
        
        //ORGAN_TYPE - NEW REAL TEMP
        //SAVE_TYPE - CONFIRM SAVE_TO_ORGAN SAVE

        String organType = (String)ctx.get("ORGAN_TYPE");
        String saveType = (String)ctx.get("SAVE_TYPE");
        
        if("REAL".equals(organType)){
        	// �����̷� �Ϸù�ȣ ��ȸ
            nSeq = getArrangeChngSeq(ctx);
        }
        
        // ���� ��ȸ
    	PosParameter param = new PosParameter();
    	int index = 0;
        param.setWhereClauseParameter(index++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(index++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(index++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(index++, ctx.get("DAY_ORD"  ));
        
        PosRowSet rowset = this.getDao("boadao").find("tbeb_race_fb002", param);

        PosRow rows[] = rowset.getAllRow();        

        // ������
        for ( int j = 1; j <= rows.length; j++ ) {
	        sDsName = "dsOutRace_" + j;
	        if ( ctx.get(sDsName) != null ) {
		        ds    = (PosDataset) ctx.get(sDsName);
		        nSize = ds.getRecordCount();
		        for ( int i = 0; i < nSize; i++ ) 
		        {
		            PosRecord record = ds.getRecord(i);
		            String sRaceNo = Util.getFormat("00", Integer.toString(j));
		            record.setAttribute("RACE_NO", sRaceNo);
		            
		            // �ű� ���� ����
		            if("NEW".equals(organType)){
			            if ( (nTempCnt = updateTempOrgan(record, ctx)) == 0 ) {
		                	nTempCnt = insertTempOrgan(record, ctx);
		                }	
			        // Ȯ��, ��Ȯ�� ����
		            }else if("REAL".equals(organType)){
		            	
			            if ( (nTempCnt = updateOrgan(record, ctx)) == 0 ) {
		                	nTempCnt = insertOrgan(record, ctx);
		                }
		            	nTempCnt = insertArrangeChg(record, ctx);
		            	nTempCnt = updateEquipLot(record, ctx);
		            	
		            }else if("TEMP".equals(organType)){
		            	
		            	// ��Ȯ������
			            if("SAVE_TO_ORGAN".equals(saveType)){
			            	
				            if ( (nTempCnt = updateOrgan(record, ctx)) == 0 ) {
			                	nTempCnt = insertOrgan(record, ctx);
			                }			            	
				        // ���� ����
			            }else if("SAVE".equals(saveType)){
			            	
				            if ( (nTempCnt = updateTempOrgan(record, ctx)) == 0 ) {
			                	nTempCnt = insertTempOrgan(record, ctx);
			                }
			            	
			            }
		            }
		        }

		        nSaveCount = nSaveCount + nTempCnt;
	        }
        }
        
        if("REAL".equals(organType) || ("TEMP".equals(organType) && "SAVE_TO_ORGAN".equals(saveType)) ){
        	updateRaceDayOrd(ctx);
        }
       
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> �� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateOrgan(PosRecord record, PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO     ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_GRD_CD ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MOT_NO       ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("BOAT_NO      ".trim())));
        param.setValueParamter(i++, ctx.get("RACE_DAY     ".trim()));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ABSE_CD      ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RMK          ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, Util.trim(record.getAttribute("ST_MTHD_CD   ".trim())));

        i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR    ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD       ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS          ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD      ".trim()));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACE_NO      ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACE_REG_NO  ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_organ_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> �� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertOrgan(PosRecord record, PosContext ctx) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, ctx.get("STND_YEAR    ".trim()));
        param.setValueParamter(i++, ctx.get("MBR_CD       ".trim()));
        param.setValueParamter(i++, ctx.get("TMS          ".trim()));
        param.setValueParamter(i++, ctx.get("DAY_ORD      ".trim()));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO      ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_REG_NO  ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO     ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_GRD_CD ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MOT_NO       ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("BOAT_NO      ".trim())));
        param.setValueParamter(i++, ctx.get("RACE_DAY     ".trim()));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ABSE_CD      ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RMK          ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, Util.trim(record.getAttribute("ST_MTHD_CD   ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_organ_ib001", param);
        return dmlcount;
    }

    /**
     * <p> �����̷� �Ϸù�ȣ ��ȸ </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int getArrangeChngSeq(PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR    ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD       ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS          ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD      ".trim()));

        PosRowSet rowset = this.getDao("boadao").find("tbeb_arrange_chg_fb001", param);

        BigDecimal nSeq = null;
        if (rowset.hasNext())
        {
            PosRow row = rowset.next();
            nSeq = (BigDecimal) row.getAttribute("SEQ".trim());
            logger.logInfo("tbeb_arrange_chg_sb001.SEQ : " + nSeq);
        }

        return nSeq.intValue();
    }

    
    /**
     * <p> �����̷� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertArrangeChg(PosRecord record, PosContext ctx) 
    {
    	int dmlcount = 0;
    	// ���� ���� ����
    	String sOrgRacerNo = Util.trim(record.getAttribute("ORG_RACER_NO"));
    	String sRacerNo    = Util.trim(record.getAttribute("RACER_NO"));

    	if ( sOrgRacerNo.length() != 0 && sRacerNo.length() != 0 ) {
	    	if ( !sOrgRacerNo.equals(sRacerNo) ) {
		        // �������̷� ����
		        PosParameter param = new PosParameter();
		        int i = 0;
		        param.setValueParamter(i++, ctx.get("STND_YEAR    ".trim()));
		        param.setValueParamter(i++, ctx.get("MBR_CD       ".trim()));
		        param.setValueParamter(i++, ctx.get("TMS          ".trim()));
		        param.setValueParamter(i++, ctx.get("DAY_ORD      ".trim()));
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
	
		        // ������ ��  ���̺� ����
		        /*
		        PosParameter paramOrgan = new PosParameter();
		        i = 0;
		        paramOrgan.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO".trim())));
		        paramOrgan.setValueParamter(i++, Util.trim(record.getAttribute("RACER_GRD_CD ".trim())));
		        paramOrgan.setValueParamter(i++, SESSION_USER_ID );
	
		        i = 0;
		        paramOrgan.setWhereClauseParameter(i++, ctx.get("STND_YEAR    ".trim()));
		        paramOrgan.setWhereClauseParameter(i++, ctx.get("MBR_CD       ".trim()));
		        paramOrgan.setWhereClauseParameter(i++, ctx.get("TMS          ".trim()));
		        paramOrgan.setWhereClauseParameter(i++, ctx.get("DAY_ORD      ".trim()));
		        paramOrgan.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ORG_RACER_NO".trim())));
		
		        dmlcount = this.getDao("boadao").update("tbeb_organ_ub002", paramOrgan);

		        // ���� ���ֹ��� ���̺� ����
		        PosParameter paramAlloc = new PosParameter();
		        i = 0;
		        paramAlloc.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO  ".trim())));
		        paramAlloc.setValueParamter(i++, SESSION_USER_ID );
		
		        i = 0;
		        paramAlloc.setWhereClauseParameter(i++, ctx.get("STND_YEAR    ".trim()));
		        paramAlloc.setWhereClauseParameter(i++, ctx.get("MBR_CD       ".trim()));
		        paramAlloc.setWhereClauseParameter(i++, ctx.get("TMS          ".trim()));
		        paramAlloc.setWhereClauseParameter(i++, ctx.get("DAY_ORD      ".trim()));
		        paramAlloc.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ORG_RACER_NO".trim())));
		
		        dmlcount = this.getDao("boadao").update("tbeb_racer_race_alloc_ub002", paramAlloc);
				*/
	    	}
    	}
    	
    	// ���� ���� ����
    	String sOrgMotNo = Util.trim(record.getAttribute("ORG_MOT_NO"));
    	String sMotNo    = Util.trim(record.getAttribute("MOT_NO"));

    	if ( sOrgMotNo.length() != 0 && sMotNo.length() != 0 ) {
	    	if ( !sOrgMotNo.equals(sMotNo) ) {
		        // �����̷� ���̺� ����
		        PosParameter param = new PosParameter();
		        int i = 0;
		        param.setValueParamter(i++, ctx.get("STND_YEAR    ".trim()));
		        param.setValueParamter(i++, ctx.get("MBR_CD       ".trim()));
		        param.setValueParamter(i++, ctx.get("TMS          ".trim()));
		        param.setValueParamter(i++, ctx.get("DAY_ORD      ".trim()));
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
	
		        // ������ ��  ���̺� ����
		        PosParameter paramOrgan = new PosParameter();
		        i = 0;
		        paramOrgan.setValueParamter(i++, Util.trim(record.getAttribute("MOT_NO".trim())));
		        paramOrgan.setValueParamter(i++, SESSION_USER_ID );
	
		        i = 0;
		        paramOrgan.setWhereClauseParameter(i++, ctx.get("STND_YEAR    ".trim()));
		        paramOrgan.setWhereClauseParameter(i++, ctx.get("MBR_CD       ".trim()));
		        paramOrgan.setWhereClauseParameter(i++, ctx.get("TMS          ".trim()));
		        paramOrgan.setWhereClauseParameter(i++, ctx.get("DAY_ORD      ".trim()));
		        paramOrgan.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ORG_MOT_NO".trim())));
		
		        dmlcount = this.getDao("boadao").update("tbeb_organ_ub003", paramOrgan);
		        
		        // ���ӽ����� ���̺� ����
		        PosParameter paramOrganTemp = new PosParameter();
		        i = 0;
		        paramOrganTemp.setValueParamter(i++, Util.trim(record.getAttribute("MOT_NO".trim())));
		        paramOrganTemp.setValueParamter(i++, SESSION_USER_ID );
	
		        i = 0;
		        paramOrganTemp.setWhereClauseParameter(i++, ctx.get("STND_YEAR    ".trim()));
		        paramOrganTemp.setWhereClauseParameter(i++, ctx.get("MBR_CD       ".trim()));
		        paramOrganTemp.setWhereClauseParameter(i++, ctx.get("TMS          ".trim()));
		        paramOrganTemp.setWhereClauseParameter(i++, ctx.get("DAY_ORD      ".trim()));
		        paramOrganTemp.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACE_NO".trim())));
		        paramOrganTemp.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ORG_MOT_NO".trim())));
		
		        dmlcount = this.getDao("boadao").update("tbeb_organ_ub005", paramOrganTemp);
		        
	    	}
    	}

    	// ��Ʈ ���� ����
    	String sOrgBoatNo = Util.trim(record.getAttribute("ORG_BOAT_NO"));
    	String sBoatNo    = Util.trim(record.getAttribute("BOAT_NO"));

    	if ( sOrgBoatNo.length() != 0 && sBoatNo.length() != 0 ) {
	    	if ( !sOrgBoatNo.equals(sBoatNo) ) {
		        // �� �����̷� ���̺� ����
		        PosParameter param = new PosParameter();
		        int i = 0;
		        param.setValueParamter(i++, ctx.get("STND_YEAR    ".trim()));
		        param.setValueParamter(i++, ctx.get("MBR_CD       ".trim()));
		        param.setValueParamter(i++, ctx.get("TMS          ".trim()));
		        param.setValueParamter(i++, ctx.get("DAY_ORD      ".trim()));
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
	
		        // ������ ��  ���̺� ����
		        PosParameter paramOrgan = new PosParameter();
		        i = 0;
		        paramOrgan.setValueParamter(i++, Util.trim(record.getAttribute("BOAT_NO".trim())));
		        paramOrgan.setValueParamter(i++, SESSION_USER_ID );
	
		        i = 0;
		        paramOrgan.setWhereClauseParameter(i++, ctx.get("STND_YEAR    ".trim()));
		        paramOrgan.setWhereClauseParameter(i++, ctx.get("MBR_CD       ".trim()));
		        paramOrgan.setWhereClauseParameter(i++, ctx.get("TMS          ".trim()));
		        paramOrgan.setWhereClauseParameter(i++, ctx.get("DAY_ORD      ".trim()));
		        paramOrgan.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ORG_BOAT_NO".trim())));
		
		        dmlcount = this.getDao("boadao").update("tbeb_organ_ub004", paramOrgan);
		        
		        // ���ӽ����� ���̺� ����
		        PosParameter paramOrganTemp = new PosParameter();
		        i = 0;
		        paramOrganTemp.setValueParamter(i++, Util.trim(record.getAttribute("BOAT_NO".trim())));
		        paramOrganTemp.setValueParamter(i++, SESSION_USER_ID );
	
		        i = 0;
		        paramOrganTemp.setWhereClauseParameter(i++, ctx.get("STND_YEAR    ".trim()));
		        paramOrganTemp.setWhereClauseParameter(i++, ctx.get("MBR_CD       ".trim()));
		        paramOrganTemp.setWhereClauseParameter(i++, ctx.get("TMS          ".trim()));
		        paramOrganTemp.setWhereClauseParameter(i++, ctx.get("DAY_ORD      ".trim()));
		        paramOrganTemp.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACE_NO")));
		        paramOrganTemp.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ORG_BOAT_NO".trim())));
		
		        dmlcount = this.getDao("boadao").update("tbeb_organ_ub006", paramOrganTemp);
		        
	    	}
    	}
		
    	// ����� �ּ����̺� ����
    	if ( !sOrgRacerNo.equals(sRacerNo)
          || !sOrgMotNo  .equals(sMotNo  ) 
          || !sOrgBoatNo .equals(sBoatNo ) ) {
    		
	        PosParameter param = new PosParameter();
	        int i = 0;
	        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR    ".trim()));
	        param.setWhereClauseParameter(i++, ctx.get("MBR_CD       ".trim()));
	        param.setWhereClauseParameter(i++, ctx.get("TMS          ".trim()));
	        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_NO".trim())));
    		
	        PosRowSet rowset = this.getDao("boadao").find("tbeb_arrange_fb003", param);

	        int nCount = 0;
	        if (rowset.hasNext())
	        {
	            PosRow row = rowset.next();
	            nCount = ((BigDecimal) row.getAttribute("CNT".trim())).intValue();
	        }

	        if ( nCount == 0 ) {
		        PosParameter paramArrange = new PosParameter();
		        i = 0;
		        paramArrange.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO  ".trim())));
		        paramArrange.setValueParamter(i++, Util.trim(record.getAttribute("MOT_NO    ".trim())));
		        paramArrange.setValueParamter(i++, Util.trim(record.getAttribute("BOAT_NO   ".trim())));
		        paramArrange.setValueParamter(i++, SESSION_USER_ID );
		
		        i = 0;
		        paramArrange.setWhereClauseParameter(i++, ctx.get("STND_YEAR    ".trim()));
		        paramArrange.setWhereClauseParameter(i++, ctx.get("MBR_CD       ".trim()));
		        paramArrange.setWhereClauseParameter(i++, ctx.get("TMS          ".trim()));
		        paramArrange.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ORG_RACER_NO".trim())));
		
		        dmlcount = this.getDao("boadao").update("tbeb_arrange_ub003", paramArrange);
	        } else {
		        PosParameter paramArrange = new PosParameter();
		        i = 0;
		        paramArrange.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO  ".trim())));
		        paramArrange.setValueParamter(i++, Util.trim(record.getAttribute("MOT_NO    ".trim())));
		        paramArrange.setValueParamter(i++, Util.trim(record.getAttribute("BOAT_NO   ".trim())));
		        paramArrange.setValueParamter(i++, SESSION_USER_ID );
		
		        i = 0;
		        paramArrange.setWhereClauseParameter(i++, ctx.get("STND_YEAR    ".trim()));
		        paramArrange.setWhereClauseParameter(i++, ctx.get("MBR_CD       ".trim()));
		        paramArrange.setWhereClauseParameter(i++, ctx.get("TMS          ".trim()));
		        paramArrange.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_NO".trim())));
		
		        dmlcount = this.getDao("boadao").update("tbeb_arrange_ub003", paramArrange);
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
    protected int updateEquipLot(PosRecord record, PosContext ctx) 
    {
    	int dmlcount = 0;
    	String sOrgMotNo = Util.trim(record.getAttribute("ORG_MOT_NO"));
    	String sMotNo    = Util.trim(record.getAttribute("MOT_NO"));

    	if ( sOrgMotNo.length() != 0 && sMotNo.length() != 0 ) {
	    	if ( !sOrgMotNo.equals(sMotNo) ) {
		        PosParameter param = new PosParameter();
		        int i = 0;
		        param.setValueParamter(i++, SESSION_USER_ID );
	
		        i = 0;
		        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR    ".trim()));
		        param.setWhereClauseParameter(i++, ctx.get("MBR_CD       ".trim()));
		        param.setWhereClauseParameter(i++, ctx.get("TMS          ".trim()));
		        param.setWhereClauseParameter(i++, "M"      );
		        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("MOT_NO      ".trim())));
	
		        dmlcount = this.getDao("boadao").update("tbef_equip_lot_ub001", param);
	    	}
    	}

    	String sOrgBoatNo = Util.trim(record.getAttribute("ORG_BOAT_NO"));
    	String sBoatNo    = Util.trim(record.getAttribute("BOAT_NO"));
    	if ( sOrgBoatNo.length() != 0 && sBoatNo.length() != 0 ) {
	    	if ( !sOrgBoatNo.equals(sBoatNo) ) {
		        PosParameter param = new PosParameter();
		        int i = 0;
		        param.setValueParamter(i++, SESSION_USER_ID );
	
		        i = 0;
		        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR    ".trim()));
		        param.setWhereClauseParameter(i++, ctx.get("MBR_CD       ".trim()));
		        param.setWhereClauseParameter(i++, ctx.get("TMS          ".trim()));
		        param.setWhereClauseParameter(i++, "B"      );
		        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("BOAT_NO     ".trim())));
	
		        dmlcount = this.getDao("boadao").update("tbef_equip_lot_ub001", param);
	    	}
    	}

    	return dmlcount;
    }

    
    /**
     * <p> ������ �� ȸ���� �۾� �̷� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRaceDayOrd(PosContext ctx) 
    {
        PosParameter paramTms = new PosParameter();
        int i = 0;
        paramTms.setValueParamter(i++, null);
        paramTms.setValueParamter(i++, "040");
        paramTms.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        paramTms.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramTms.setWhereClauseParameter(i++, ctx.get("MBR_CD")   );
        paramTms.setWhereClauseParameter(i++, ctx.get("TMS")      );
        
        int dmlcount = this.getDao("boadao").update("tbeb_race_tms_ub003", paramTms);

        if ( !ctx.get("ORGAN_STAT_CD").equals("002") ) {

            return 0;
    	}
    	
        PosParameter param = new PosParameter();
        i = 0;
        param.setValueParamter(i++, ctx.get("ORGAN_STAT_CD".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR    ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD       ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS          ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD      ".trim()));
        dmlcount = this.getDao("boadao").update("tbeb_race_day_ord_ub001", param);
        
        
        // �۾��α� �ۼ�
        //==============================================
        HashMap hmProcess = new HashMap();
        hmProcess.put("STND_YEAR", ctx.get("STND_YEAR"));
        hmProcess.put("MBR_CD"   , ctx.get("MBR_CD"   ));
        hmProcess.put("TMS"      , ctx.get("TMS"      ));
        hmProcess.put("DAY_ORD"  , ctx.get("DAY_ORD"  ));
        hmProcess.put("DUTY_CD"  , "002");
        hmProcess.put("WORK_CD"  , "040");
        hmProcess.put("PROG_STAT", "001");
        hmProcess.put("USER_ID",   SESSION_USER_ID);

        SaveProcess sp = new SaveProcess();
        sp.saveProcess(hmProcess);
        //==============================================
         
        return dmlcount;
    }
    
    /**
     * <p> ���� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateTempOrgan(PosRecord record, PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO     ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MOT_NO       ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("BOAT_NO      ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, Util.trim(record.getAttribute("ST_MTHD_CD   ".trim())));

        i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR    ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD       ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("TMS          ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD      ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID      ".trim()));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ      ".trim()));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACE_NO      ".trim())));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACE_REG_NO  ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_temp_organ_ub001", param);
        
        return dmlcount;
		
    }

    /**
     * <p> ���� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertTempOrgan(PosRecord record, PosContext ctx) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, ctx.get("STND_YEAR    ".trim()));
        param.setValueParamter(i++, ctx.get("MBR_CD       ".trim()));
        param.setValueParamter(i++, ctx.get("TMS          ".trim()));
        param.setValueParamter(i++, ctx.get("DAY_ORD      ".trim()));
        
        param.setValueParamter(i++, ctx.get("ORGAN_OWNER_ID".trim()));
        param.setValueParamter(i++, ctx.get("ORGAN_SEQ    ".trim()));
        
        
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_NO      ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_REG_NO  ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO     ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MOT_NO       ".trim())));
        param.setValueParamter(i++, Util.trim(record.getAttribute("BOAT_NO      ".trim())));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, Util.trim(record.getAttribute("ST_MTHD_CD   ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_temp_organ_ib001", param);
        return dmlcount;
    }    
    
}
