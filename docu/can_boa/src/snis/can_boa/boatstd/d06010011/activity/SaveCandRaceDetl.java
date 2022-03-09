package snis.can_boa.boatstd.d06010011.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

//import snis.can_boa.boatstd.d06010010.activity.none;
import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ������/������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SaveCandRaceDetl extends SnisActivity
{    
    public SaveCandRaceDetl()
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
    	int nTempCnt     = 0; 

        int nRoundCnt = Integer.parseInt((String) ctx.get("ROUND_CNT"));
        
        for ( int i = 0; i < nRoundCnt; i++ ) {
        	
        	if ( (nTempCnt = updateCandRaceRace(ctx, (i + 1))) == 0 ) {
        		nTempCnt = insertCandRaceRace(ctx, (i + 1));
        	}
        	nSaveCount = nSaveCount + nTempCnt;
        	
        	for ( int j = 0; j < 4; j++ ) {
        		PosDataset ds = (PosDataset) ctx.get("dsOutCandRaceDetl_" + (i + 1) + "R_" + (j + 1) + "T");
        		
            	if ( ds != null ) {
            		int nSize = ds.getRecordCount();

                    for ( int k = 0; k < nSize; k++ )
                    {
                        PosRecord record = ds.getRecord(k);
                      
                        if ( record.getAttribute("CAND_NO") == null || record.getAttribute("CAND_NO").equals("") ) {
                        	deleteCandRaceOrgan(record, ctx, (i + 1), (j + 1));
                        	continue;
                        }
                        
                    	if ( (nTempCnt = updateCandRaceOrgan(record, ctx, (i + 1), (j + 1))) == 0 ) {
                    		nTempCnt = insertCandRaceOrgan(record, ctx, (i + 1), (j + 1));
                    	}
                    	nSaveCount = nSaveCount + nTempCnt;

                    	/*
                    	// ST������ ���
                    	int nStartNo = 0;
                    	if ( ctx.get("GBN" + (i + 1)).equals("005") ) {
                            
                            PosParameter param = new PosParameter();
                            int n = 0;
                            param.setWhereClauseParameter(n++, ctx   .get         ("RACER_PERIO_NO".trim()));
                            param.setWhereClauseParameter(n++, ctx   .get         ("DAY_NO        ".trim()));
                            param.setWhereClauseParameter(n++, (i + 1) + "");
                            param.setWhereClauseParameter(n++, (j + 1) + "");
                            param.setWhereClauseParameter(n++, record.getAttribute("RACE_REG_NO   ".trim()));
                            param.setWhereClauseParameter(n++, null);

                            this.getDao("candao").update("tbdn_cand_race_recd_dn001", param);	//����
                            nStartNo = 7;
                     	}

                    	//���ǰ���
                    	if ( ctx.get("GBN" + (i + 1)).equals("006") ) {         
                    	    nStartNo = 6;
                    	}
                    	
                     	//for ( int l = nStartNo; l < 7; l++ ) {//20080706����
                    	for ( int l = 0; l < nStartNo; l++ ) {
                        	if ( (nTempCnt = updateCandRaceRecd(record, ctx, (i + 1), (j + 1), (l + 1))) == 0 ) {
                        		nTempCnt = insertCandRaceRecd(record, ctx, (i + 1), (j + 1), (l + 1));
                        	}
                        	nSaveCount = nSaveCount + nTempCnt;
                    	}
                    	*/

                    	for ( int l = 0; l < 3; l++ ) {	//3ȸ������ C/T �Է�
                        	if ( (nTempCnt = updateCandRaceRecd(record, ctx, (i + 1), (j + 1), (l + 1))) == 0 ) {
                        		nTempCnt = insertCandRaceRecd(record, ctx, (i + 1), (j + 1), (l + 1));
                        	}
                        	nSaveCount = nSaveCount + nTempCnt;
                    	}
                    	
                    }//for
            	}
        	}
        }

        PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsRacePlan");
        System.out.println("rio1");
        nSize = ds.getRecordCount();
        System.out.println("rio2 : " + nSize);
        for ( int i = 0; i < nSize; i++ ) 
        {        
        	PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
              || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	nTempCnt = mergeRacePlan(record, ctx);
                nSaveCount = nSaveCount + nTempCnt;
            }
        }
        
        
        Util.setSaveCount  (ctx, nSaveCount     );
    }

    /**
     * <p> Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateCandRaceRace(PosContext ctx, int nRound)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, ctx   .get         ("GBN"          + nRound));
        param.setValueParamter(i++, ctx   .get         ("CCIT_CNT"     + nRound));
        param.setValueParamter(i++, ctx   .get         ("BOAT_TIME"    + nRound));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, ctx   .get         ("RACER_PERIO_NO".trim()));
        param.setWhereClauseParameter(i++, ctx   .get         ("DAY_NO        ".trim()));
        param.setWhereClauseParameter(i++, Integer.toString(nRound));

        int dmlcount = this.getDao("candao").update("tbdn_cand_race_race_un001", param);
        
        return dmlcount;
    }

    /**
     * <p> �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertCandRaceRace(PosContext ctx, int nRound) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, ctx   .get         ("RACER_PERIO_NO".trim()));
        param.setValueParamter(i++, ctx   .get         ("DAY_NO        ".trim()));
        param.setValueParamter(i++, Integer.toString(nRound));
        param.setValueParamter(i++, ctx   .get         ("GBN"          + nRound));
        param.setValueParamter(i++, ctx   .get         ("CCIT_CNT"     + nRound));
        param.setValueParamter(i++, ctx   .get         ("BOAT_TIME"    + nRound));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );
            	
        int dmlcount = this.getDao("candao").update("tbdn_cand_race_race_in001", param);
        return dmlcount;
    }

    /**
     * <p> Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateCandRaceOrgan(PosRecord record, PosContext ctx, int nRound, int nTeamNo)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("CAND_NO       ".trim()));
        param.setValueParamter(i++, record.getAttribute("MOTOR_NO      ".trim()));
        param.setValueParamter(i++, record.getAttribute("BOAT_NO       ".trim()));
        param.setValueParamter(i++, record.getAttribute("ARRIV_ORD     ".trim()));
        param.setValueParamter(i++, record.getAttribute("STRATEGY      ".trim()));
        param.setValueParamter(i++, record.getAttribute("VIOL          ".trim()));
        param.setValueParamter(i++, record.getAttribute("VIOL_CNTNT    ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_CNTNT    ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );

        /*
logger.logInfo("*******update*******["+record.getAttribute("CAND_NO").toString()+"]");
logger.logInfo("*******update*******["+record.getAttribute("MOTOR_NO").toString()+"]");
logger.logInfo("*******update*******["+record.getAttribute("BOAT_NO").toString()+"]");
logger.logInfo("*******update*******["+record.getAttribute("ARRIV_ORD").toString()+"]");
logger.logInfo("*******update*******["+record.getAttribute("STRATEGY").toString()+"]");
logger.logInfo("*******update*******["+record.getAttribute("VIOL").toString()+"]");
logger.logInfo("*******update*******["+record.getAttribute("VIOL_CNTNT").toString()+"]");
logger.logInfo("*******update*******["+record.getAttribute("RACE_CNTNT").toString()+"]");
*/
		i = 0;
        param.setWhereClauseParameter(i++, ctx   .get         ("RACER_PERIO_NO".trim()));
        param.setWhereClauseParameter(i++, ctx   .get         ("DAY_NO        ".trim()));
        param.setWhereClauseParameter(i++, nRound  + "");
        param.setWhereClauseParameter(i++, nTeamNo + "");
        param.setWhereClauseParameter(i++, record.getAttribute("RACE_REG_NO   ".trim()));

        int dmlcount = this.getDao("candao").update("tbdn_cand_race_organ_un001", param);
        
        return dmlcount;
    }

    /**
     * <p> �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertCandRaceOrgan(PosRecord record, PosContext ctx, int nRound, int nTeamNo) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, ctx   .get         ("RACER_PERIO_NO".trim()));
        param.setValueParamter(i++, ctx   .get         ("DAY_NO        ".trim()));
        param.setValueParamter(i++, nRound  + "");
        param.setValueParamter(i++, nTeamNo + "");
        param.setValueParamter(i++, record.getAttribute("RACE_REG_NO   ".trim()));
        param.setValueParamter(i++, record.getAttribute("CAND_NO       ".trim()));
        param.setValueParamter(i++, record.getAttribute("MOTOR_NO      ".trim()));
        param.setValueParamter(i++, record.getAttribute("BOAT_NO       ".trim()));
        param.setValueParamter(i++, record.getAttribute("ARRIV_ORD     ".trim()));
        param.setValueParamter(i++, record.getAttribute("STRATEGY      ".trim()));
        param.setValueParamter(i++, record.getAttribute("VIOL          ".trim()));
        param.setValueParamter(i++, record.getAttribute("VIOL_CNTNT    ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_CNTNT    ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        /*
        logger.logInfo("*******update*******["+record.getAttribute("RACER_PERIO_NO").toString()+"]");
        logger.logInfo("*******update*******["+record.getAttribute("DAY_NO").toString()+"]");
        logger.logInfo("*******update*******["+record.getAttribute("RACE_REG_NO").toString()+"]");
        logger.logInfo("*******update*******["+record.getAttribute("CAND_NO").toString()+"]");
        logger.logInfo("*******update*******["+record.getAttribute("MOTOR_NO").toString()+"]");
        logger.logInfo("*******update*******["+record.getAttribute("ARRIV_ORD").toString()+"]");
        logger.logInfo("*******update*******["+record.getAttribute("STRATEGY").toString()+"]");
        logger.logInfo("*******update*******["+record.getAttribute("VIOL").toString()+"]");
        logger.logInfo("*******update*******["+record.getAttribute("VIOL_CNTNT").toString()+"]");
        logger.logInfo("*******update*******["+record.getAttribute("RACE_CNTNT").toString()+"]");
        */
        
        
        int dmlcount = this.getDao("candao").update("tbdn_cand_race_organ_in001", param);
        return dmlcount;
    }

    /**
     * <p> �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteCandRaceOrgan(PosRecord record, PosContext ctx, int nRound, int nTeamNo) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, ctx   .get         ("RACER_PERIO_NO".trim()));
        param.setWhereClauseParameter(i++, ctx   .get         ("DAY_NO        ".trim()));
        param.setWhereClauseParameter(i++, nRound  + "");
        param.setWhereClauseParameter(i++, nTeamNo + "");
        param.setWhereClauseParameter(i++, record.getAttribute("RACE_REG_NO   ".trim()));

        int dmlcount = this.getDao("candao").update("tbdn_cand_race_organ_dn001", param);
        
        param.setWhereClauseParameter(i++, null);
        this.getDao("candao").update("tbdn_cand_race_recd_dn001", param);
        
        return dmlcount;
    }

    /**
     * <p> Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateCandRaceRecd(PosRecord record, PosContext ctx, int nRound, int nTeamNo, int nRaceSeq)
    {
        PosParameter param = new PosParameter();
        int i = 0;

        //logger.logInfo(">>>>>>>>>>>>>>>>>>>>>>>>>>>>["+record.getAttribute("RACE_FL_"   + nRaceSeq).toString()+"]");
        String race_cource_tmp = Util.NVL(record.getAttribute("RACE_COURSE_" + nRaceSeq),"");
        String race_time_tmp = Util.NVL(record.getAttribute("RACE_TIME_"   + nRaceSeq),"");
        String race_fl_tmp = Util.NVL(record.getAttribute("RACE_FL_"   + nRaceSeq),"");
        
        //�ڽ��� 1~6������ ����.
        if(!("1".equals(race_cource_tmp) || "2".equals(race_cource_tmp) || "3".equals(race_cource_tmp) || "4".equals(race_cource_tmp) || "5".equals(race_cource_tmp) || "6".equals(race_cource_tmp)))
        	race_cource_tmp = "";
        
        //FL�� �������� ���� default�� ó���ϱ� ���� ����
        if("".equals(race_fl_tmp) &&  !"".equals(race_cource_tmp) && !"".equals(race_time_tmp)) race_fl_tmp = "001"; 
        	
        param.setValueParamter(i++, race_cource_tmp);
        param.setValueParamter(i++, race_time_tmp);
        param.setValueParamter(i++, race_fl_tmp);
        param.setValueParamter(i++, SESSION_USER_ID );

        /*
        param.setValueParamter(i++, record.getAttribute("RACE_COURSE_" + nRaceSeq));
        param.setValueParamter(i++, record.getAttribute("RACE_TIME_"   + nRaceSeq));
        param.setValueParamter(i++, record.getAttribute("RACE_FL_"   + nRaceSeq));
        param.setValueParamter(i++, SESSION_USER_ID );
		*/

        i = 0;
        param.setWhereClauseParameter(i++, ctx   .get         ("RACER_PERIO_NO".trim()));
        param.setWhereClauseParameter(i++, ctx   .get         ("DAY_NO        ".trim()));
        param.setWhereClauseParameter(i++, nRound   + "");
        param.setWhereClauseParameter(i++, nTeamNo  + "");
        param.setWhereClauseParameter(i++, record.getAttribute("RACE_REG_NO   ".trim()));
        param.setWhereClauseParameter(i++, nRaceSeq + "");

        logger.logInfo("update_RACER_PERIO_NO============>"+ctx   .get         ("RACER_PERIO_NO".trim()));
        logger.logInfo("update_DAY_NO============>"+ctx   .get         ("DAY_NO".trim()));
        logger.logInfo("update_nRound============>"+nRound);
        logger.logInfo("update_nTeamNo============>"+nTeamNo);
        logger.logInfo("update_nTeamNo============>"+nTeamNo);
        logger.logInfo("update_RACE_REG_NO============>"+record.getAttribute("RACE_REG_NO   ".trim()));
        logger.logInfo("update_nRaceSeq============>"+nRaceSeq);
        logger.logInfo("update_RACE_COURSE============>"+record.getAttribute("RACE_COURSE_" + nRaceSeq));
        logger.logInfo("update_RACE_TIME_============>"+record.getAttribute("RACE_TIME_"   + nRaceSeq));
        logger.logInfo("update_RACE_FL_============>"+record.getAttribute("RACE_FL_"   + nRaceSeq));
        
        int dmlcount = this.getDao("candao").update("tbdn_cand_race_recd_un001", param);
        
        return dmlcount;
    }

    /**
     * <p> �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertCandRaceRecd(PosRecord record, PosContext ctx, int nRound, int nTeamNo, int nRaceSeq) 
    {
        PosParameter param = new PosParameter();
        
        String race_cource_tmp = Util.NVL(record.getAttribute("RACE_COURSE_" + nRaceSeq),"");
        //�ڽ��� 1~6������ ����.
        if(!("1".equals(race_cource_tmp) || "2".equals(race_cource_tmp) || "3".equals(race_cource_tmp) || "4".equals(race_cource_tmp) || "5".equals(race_cource_tmp) || "6".equals(race_cource_tmp)))
        	race_cource_tmp = "";

        
        
        int i = 0;
        param.setValueParamter(i++, ctx   .get         ("RACER_PERIO_NO".trim()));
        param.setValueParamter(i++, ctx   .get         ("DAY_NO        ".trim()));
        param.setValueParamter(i++, nRound   + "");
        param.setValueParamter(i++, nTeamNo  + "");
        param.setValueParamter(i++, record.getAttribute("RACE_REG_NO   ".trim()));
        param.setValueParamter(i++, nRaceSeq + "");
        param.setValueParamter(i++, race_cource_tmp);
        param.setValueParamter(i++, record.getAttribute("RACE_TIME_"   + nRaceSeq));
        param.setValueParamter(i++, record.getAttribute("RACE_FL_"   + nRaceSeq));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );
        
        logger.logInfo("RACER_PERIO_NO============>"+ctx   .get         ("RACER_PERIO_NO".trim()));
        logger.logInfo("DAY_NO============>"+ctx   .get         ("DAY_NO".trim()));
        logger.logInfo("nRound============>"+nRound);
        logger.logInfo("nTeamNo============>"+nTeamNo);
        logger.logInfo("RACE_REG_NO============>"+record.getAttribute("RACE_REG_NO   ".trim()));
        logger.logInfo("nRaceSeq============>"+nRaceSeq);
        logger.logInfo("RACE_COURSE============>"+record.getAttribute("RACE_COURSE_" + nRaceSeq));
        logger.logInfo("RACE_TIME_============>"+record.getAttribute("RACE_TIME_"   + nRaceSeq));
        logger.logInfo("RACE_FL_============>"+record.getAttribute("RACE_FL_"   + nRaceSeq));
        
        int dmlcount = this.getDao("candao").update("tbdn_cand_race_recd_in001", param);
        return dmlcount;
    }

    
    /**
     * <p> �����ĺ��� ����ǥ �Է�/���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int mergeRacePlan(PosRecord record, PosContext ctx)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, ctx   .get         ("RACER_PERIO_NO".trim()));
        param.setValueParamter(i++, ctx   .get         ("DAY_NO".trim()));
        param.setValueParamter(i++, ctx   .get         ("DT".trim()));
        param.setValueParamter(i++, record.getAttribute("CAND_NO       ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_P		   ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_2        ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_3        ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_S        ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_T        ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );

        param.setValueParamter(i++, ctx   .get         ("RACER_PERIO_NO".trim()));
        param.setValueParamter(i++, ctx   .get         ("DAY_NO".trim()));
        param.setValueParamter(i++, ctx   .get         ("DT".trim()));
        param.setValueParamter(i++, record.getAttribute("CAND_NO       ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_P		   ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_2        ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_3        ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_S        ".trim()));
        param.setValueParamter(i++, record.getAttribute("RACE_T        ".trim()));
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("candao").update("tbdn_race_plan_me001", param);
        
        return dmlcount;
    }
    
  
}
