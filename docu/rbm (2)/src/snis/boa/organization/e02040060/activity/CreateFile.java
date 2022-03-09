/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02040060.activity.CreateFile.java
 * ���ϼ���		: ÷�����ϻ���
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02040060.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosColumnDef;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowImpl;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.dao.vo.PosRowSetImpl;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ÷������ �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class CreateFile extends SnisActivity
{    
	// column�� ����
	String STND_YEAR                     = "STND_YEAR                    ".trim();
	String MBR_CD                        = "MBR_CD                       ".trim();
	String TMS                           = "TMS                          ".trim();
	String DAY_ORD                       = "DAY_ORD                      ".trim();
	String ORGAN_STAT_CD                 = "ORGAN_STAT_CD                ".trim();
	String RACE_NO                       = "RACE_NO                      ".trim();
	String RACER_GRD_CD                  = "RACER_GRD_CD                 ".trim(); //����߰�. 2013.1.18
	String RACE_REG_NO                   = "RACE_REG_NO                  ".trim();
	String RACE_DAY                      = "RACE_DAY                     ".trim();
	String RACE_REG_COLOR                = "RACE_REG_COLOR               ".trim();
	String RACER_PERIO_NO                = "RACER_PERIO_NO               ".trim();
	String RACER_NO                      = "RACER_NO                     ".trim();
	String NM_KOR                        = "NM_KOR                       ".trim();
	String ABSE_CD                       = "ABSE_CD                      ".trim();
	String AGE                           = "AGE                          ".trim();
	String ENTRY_BODY_WGHT               = "ENTRY_BODY_WGHT              ".trim();
	String BOAT_NO                       = "BOAT_NO                      ".trim();
	String VIEW_BOAT_NO                  = "VIEW_BOAT_NO                 ".trim();
	String MOT_NO                        = "MOT_NO                       ".trim();
	String VIEW_MOT_NO                   = "VIEW_MOT_NO                  ".trim();
	String RACER_SEX                     = "RACER_SEX                    ".trim();
	String TRRAS_TMS_6_AVG_RANK_SCR      = "TRRAS_TMS_6_AVG_RANK_SCR     ".trim();
	String TRRAS_TMS_6_AVG_ACDNT_SCR     = "TRRAS_TMS_6_AVG_ACDNT_SCR    ".trim();
	String TRRAS_TMS_6_AVG_SCR           = "TRRAS_TMS_6_AVG_SCR          ".trim();
	String TRRAS_TMS_6_WIN_RATIO         = "TRRAS_TMS_6_WIN_RATIO        ".trim();
	String TRRAS_TMS_6_HIGH_RANK_RATIO   = "TRRAS_TMS_6_HIGH_RANK_RATIO  ".trim();
	String TRRAS_TMS_6_HIGH_3_RANK_RATIO = "TRRAS_TMS_6_HIGH_3_RANK_RATIO".trim();
	String TRRAS_TMS_6_AVG_STRT_TM       = "TRRAS_TMS_6_AVG_STRT_TM      ".trim();
	String TRRAS_TMS_8_RANK_ORD          = "TRRAS_TMS_8_RANK_ORD         ".trim();
	String TRRAS_AVG_RANK_SCR            = "TRRAS_AVG_RANK_SCR           ".trim();
	String AVG_ACDNT_SCR                 = "AVG_ACDNT_SCR                ".trim();
	String TRRAS_AVG_SCR                 = "TRRAS_AVG_SCR                ".trim();
	String TRRAS_WIN_RATIO               = "TRRAS_WIN_RATIO              ".trim();
	String TRRAS_HIGH_RANK_RATIO         = "TRRAS_HIGH_RANK_RATIO        ".trim();
	String TRRAS_HIGH_3_RANK_RATIO       = "TRRAS_HIGH_3_RANK_RATIO      ".trim();
	String TRRAS_AVG_STRT_TM             = "TRRAS_AVG_STRT_TM            ".trim();
	String FL_CNT                        = "FL_CNT                       ".trim();
	String MON_6_RACE_CNT                = "MON_6_RACE_CNT               ".trim();
	String MON_6_HIGH_RANK_RATIO         = "MON_6_HIGH_RANK_RATIO        ".trim();
	String MON_6_RACE_REG_NO_1           = "MON_6_RACE_REG_NO_1          ".trim();
	String MON_6_RACE_REG_NO_2           = "MON_6_RACE_REG_NO_2          ".trim();
	String MON_6_RACE_REG_NO_3           = "MON_6_RACE_REG_NO_3          ".trim();
	String MON_6_RACE_REG_NO_4           = "MON_6_RACE_REG_NO_4          ".trim();
	String MON_6_RACE_REG_NO_5           = "MON_6_RACE_REG_NO_5          ".trim();
	String MON_6_RACE_REG_NO_6           = "MON_6_RACE_REG_NO_6          ".trim();
	String MON_6_RACE_REG_NO_7           = "MON_6_RACE_REG_NO_7          ".trim();
	String MON_6_RACE_REG_NO_8           = "MON_6_RACE_REG_NO_8          ".trim();
	String TMRAS_AVG_RANK_SCR            = "TMRAS_AVG_RANK_SCR           ".trim();
	String TMRAS_HIGH_RANK_RATIO         = "TMRAS_HIGH_RANK_RATIO        ".trim();
	String TMRAS_HIGH_3_RANK_RATIO       = "TMRAS_HIGH_3_RANK_RATIO      ".trim();
	String MOT_PRE_RECE1                 = "MOT_PRE_RECE1                ".trim();
	String MOT_PRE_RECE2                 = "MOT_PRE_RECE2                ".trim();
	String TBRAS_AVG_RANK_SCR            = "TBRAS_AVG_RANK_SCR           ".trim();
	String TBRAS_HIGH_RANK_RATIO         = "TBRAS_HIGH_RANK_RATIO        ".trim();
	String TBRAS_HIGH_3_RANK_RATIO       = "TBRAS_HIGH_3_RANK_RATIO      ".trim();
	String ETC_RACE                      = "ETC_RACE                     ".trim();
	String PRE_RACE                      = "PRE_RACE                     ".trim();
	String RMK                           = "RMK                          ".trim();
	
	// ���� ���� ��ġ ����
    private static String FILE_SEPERATOR = File.separator;
    private static String ORGAN_PATH
	= System.getProperty("os.name").toUpperCase().indexOf("WIN") != -1 ? 
				"C:" + FILE_SEPERATOR + "upload_file" + FILE_SEPERATOR + "organ"  + FILE_SEPERATOR
				: "/WAS/upload_file/organ/";

	ArrayList alFileList = new ArrayList();

    public CreateFile()
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
    	
        searchState(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> ��ȸ���� </p>
    * @param   ctx		PosContext	�����
    * @return  none
    * @throws  none
    */
    protected void searchState(PosContext ctx) 
    {
    	
    	// ������ȸ
    	PosParameter paramRace = new PosParameter();
        int i = 0;
    	
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, "");
    	
        PosRowSet rowsetRace = this.getDao("boadao").find("tbeb_race_fb001", paramRace);
       
        try {
        	createFileAntc (ctx, rowsetRace);
        	createFilePrint(ctx, rowsetRace);

            String sResultKey = "dsOutFile";
            ctx.put(sResultKey, createRowSet(alFileList));
            Util.addResultKey(ctx, sResultKey);
        } catch ( Exception e ) {
    		Util.setSvcMsg(ctx, e.toString());
        	logger.logError(e);
        }
    }
    
    public void createDir(String sDir) {
    	File createDir = new File(sDir);
    
        if (!createDir.exists()){
        	createDir.mkdirs();
        }
    }
    
    /**
     * <p> �����ڰ� ������ ���� ���� </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    public void createFileAntc(PosContext ctx, PosRowSet rowsetRace) throws Exception {

    	createDir(ORGAN_PATH);
    	String sFileName = "";
    	sFileName = (String) ctx.get("STND_YEAR") 
		          + (String) ctx.get("MBR_CD") 
		          + Util.getFormat("000", (String) ctx.get("TMS"))
		          + Util.getFormat("000", (String) ctx.get("DAY_ORD"))
		          + Util.getFormat("000", (String) ctx.get("ORGAN_STAT_CD"))
		          + ".antc";

		FileOutputStream fosFileOut = new FileOutputStream(ORGAN_PATH + sFileName);
    	alFileList.add(ORGAN_PATH + sFileName);

		sFileName = (String) ctx.get("STND_YEAR") 
				  + (String) ctx.get("MBR_CD") 
				  + Util.getFormat("000", (String) ctx.get("TMS"))
				  + Util.getFormat("000", (String) ctx.get("DAY_ORD"))
				  + Util.getFormat("000", (String) ctx.get("ORGAN_STAT_CD"))
				  + "title"
				  + ".antc";
		
		FileOutputStream fosFileOutTitle = new FileOutputStream(ORGAN_PATH + sFileName);
    	alFileList.add(ORGAN_PATH + sFileName);

		sFileName = (String) ctx.get("STND_YEAR") 
		  + (String) ctx.get("MBR_CD") 
		  + Util.getFormat("000", (String) ctx.get("TMS"))
		  + Util.getFormat("000", (String) ctx.get("DAY_ORD"))
		  + Util.getFormat("000", (String) ctx.get("ORGAN_STAT_CD"))
		  + "mail"
		  + ".antc";

		FileOutputStream fosFileOutMail = new FileOutputStream(ORGAN_PATH + sFileName);
		alFileList.add(ORGAN_PATH + sFileName);

		String sWrite  = "";
		String sReturn = "\r\n";
		String sDiv    = " ";

		PosRow aRowRace [] = rowsetRace .getAllRow();

		try {
			String sRaceDay = (String) ctx.get("RACE_DAY");
    		sWrite = " ������ : " 
    			   + sRaceDay.substring(2, 4) + "."
    		       + sRaceDay.substring(4, 6) + "."
		       	   + sRaceDay.substring(6, 8);
	        write(fosFileOut     , sWrite + sReturn);
	        write(fosFileOut     , sReturn);
	        write(fosFileOutTitle, sWrite + sReturn);
	        write(fosFileOutTitle, sReturn);
	        write(fosFileOutMail,  sWrite + sReturn);
	        write(fosFileOutMail,  sReturn);

	        // ����������ȸ
            for ( int i = 0; i < aRowRace.length; i++ ) {
	        	
	        	// �������� �μ�
	        	PosRow rowRace = aRowRace [i];
	        	String sRaceNo = (String) rowRace.getAttribute("RACE_NO");
	        	String sStMehdCd = (String) rowRace.getAttribute("ST_MTHD_CD");
	        	String sStartType = "�ö���";
	        	if("001".equals(sStMehdCd)) sStartType = "�¶���";
	        	
	        	ctx.put("RACE_NO", sRaceNo);
	        	
		        write(fosFileOut     , sReturn);
		        write(fosFileOutTitle, sReturn);
		        write(fosFileOutMail , sReturn);
	        	sWrite = "���� " + Util.getStringLen(Integer.toString(Integer.parseInt(sRaceNo)), 2) + " ������"
	        	       + " " + rowRace.getAttribute("RACE_DGRE") + "���� " + sStartType
	        	       + " " + rowRace.getAttribute("TURN_CNT") + "��ȸ"
	        	       + "(  " + Util.getNumFormat(((BigDecimal) rowRace.getAttribute("RACE_DIST")).toString()) + "m )"
	        	       + " ���ֽð� " + Util.getTime((String) rowRace.getAttribute("STRT_TIME"));
		        write(fosFileOut     , sWrite + sReturn);
		        write(fosFileOutTitle, sWrite + sReturn);
		        write(fosFileOutMail , sWrite + sReturn);
		        write(fosFileOut     , sReturn);
		        printTitle(fosFileOutTitle);
		        printTitle(fosFileOutMail);
		        
		    	PosParameter paramRaces = new PosParameter();
		        int k = 0;

		        String sQueryId = "tbeb_organ_fb007";
		        String sDayOrd = (String)ctx.get("DAY_ORD");
		        if ("3".equals(sDayOrd)) sQueryId = "tbeb_organ_fb007_3";
		        
		        paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        //paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        //paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        //paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        //paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        //paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        //paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        
		        paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("DAY_ORD"  ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("RACE_NO"  ));
		        
		        paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("DAY_ORD"  ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("RACE_NO"  ));
		        
		        paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("DAY_ORD"  ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("DAY_ORD"  ));

		        if ("3".equals(sDayOrd)) {
		        	paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		            paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		            paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		            paramRaces.setWhereClauseParameter(k++, ctx.get("DAY_ORD"  ));
		            paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		            paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		            paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		            paramRaces.setWhereClauseParameter(k++, ctx.get("DAY_ORD"  ));
		        }
		        paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("DAY_ORD"  ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("RACE_NO"  ));

		        PosRowSet rowsetRacer = this.getDao("boadao").find(sQueryId, paramRaces);
/*		        
		        paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"    ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"       ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"          ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("DAY_ORD"      ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("ORGAN_STAT_CD"));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("RACE_NO"      ));

		        PosRowSet rowsetRacer = this.getDao("boadao").find("tbeb_race_doc_fb001", paramRaces);
*/		        
		        //���� ���� �μ�
		        while ( rowsetRacer.hasNext() ) {
		        	PosRow rowRacer = rowsetRacer.next();
		        	
		        	       sWrite      = "";
		        	String sWriteTitle = "";

		        	if ( !Util.trim(rowRacer.getAttribute("ABSE_CD")).equals("001") ) {
			        	sWrite +=                        Util.trim((String    ) rowRacer.getAttribute(RACE_REG_NO                  )) + sDiv;  // ����
			        	sWrite +=                        Util.trim((String    ) rowRacer.getAttribute(RACE_REG_COLOR               )) + sDiv;  // ����
			        	sWrite +=                        Util.trim((String    ) rowRacer.getAttribute(RACER_GRD_CD                 )) + sDiv;  // ���. 2013.1.18
			        	sWrite += Util.getStringLen(     Util.trim((String    ) rowRacer.getAttribute(RACER_PERIO_NO               )), 2) + sDiv;  // ������ȣ
			        	sWrite +=                        Util.trim((String    ) rowRacer.getAttribute(NM_KOR                       )) + sDiv;  // ������
			        	sWrite +=                        Util.trim((String    ) rowRacer.getAttribute(RACER_SEX                    )) + sDiv;  // ����
			        	sWrite +=                        Util.trim((BigDecimal) rowRacer.getAttribute(AGE                          )) + sDiv;  // ����
			        	sWrite += Util.getStringLen(     Util.trim((BigDecimal) rowRacer.getAttribute(ENTRY_BODY_WGHT              )), 2) + sDiv;  // ü��
		        		sWrite += "                       ��        ��                    ";
				        write(fosFileOut     , sWrite + sReturn);
				        write(fosFileOutTitle, sWrite + sReturn);
				        write(fosFileOutMail , sWrite + sReturn);
		        		continue;
		        	}
		        	
		        	/*****************************************************************
		        	 * ��ü
		        	 ****************************************************************/
		        	sWrite +=                         Util.trim((String    ) rowRacer.getAttribute(RACE_REG_NO                  )) + sDiv;  // ����
		        	sWrite +=                         Util.trim((String    ) rowRacer.getAttribute(RACE_REG_COLOR               )) + sDiv;  // ����
		        	sWrite +=                         Util.trim((String    ) rowRacer.getAttribute(RACER_GRD_CD                 )) + sDiv;  // ���. 2013.1.18
		        	sWrite += Util.getStringLen(      Util.trim((String    ) rowRacer.getAttribute(RACER_PERIO_NO               )), 2) + sDiv;  // ������ȣ
		        	sWrite +=                         Util.trim((String    ) rowRacer.getAttribute(NM_KOR                       )) + sDiv;  // ������
		        	sWrite +=                         Util.trim((String    ) rowRacer.getAttribute(RACER_SEX                    )) + sDiv;  // ����
		        	sWrite +=                         Util.trim((BigDecimal) rowRacer.getAttribute(AGE                          )) + sDiv;  // ����
		        	sWrite += Util.getStringLen(      Util.trim((BigDecimal) rowRacer.getAttribute(ENTRY_BODY_WGHT              )), 2);     // ü��

		        	sWrite += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_RANK_SCR     ))) + sDiv;  // 6ȸ���������  
		        	sWrite += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_SCR          ))) + sDiv;  // 6ȸ��յ���    
		        	sWrite += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_WIN_RATIO        ))) + sDiv;  // 6ȸ�·�        
		        	sWrite += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_HIGH_RANK_RATIO  ))) + sDiv;  // 6ȸ������      
		        	sWrite += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_HIGH_3_RANK_RATIO))) + sDiv;  // 6ȸ3������     
		        	sWrite += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_STRT_TM      ))) + sDiv + sDiv;  // 6ȸ���ST      
		        	sWrite +=      get8Race (         Util.trim((String    ) rowRacer.getAttribute(TRRAS_TMS_8_RANK_ORD         )));         // 8���� ����     
		        	sWrite += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_AVG_RANK_SCR           ))) + sDiv;  // ���������     
		        	sWrite += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_HIGH_RANK_RATIO        ))) + sDiv + sDiv;  // �·�           

		        	sWrite +=                         Util.trim((String    ) rowRacer.getAttribute(FL_CNT                       ))  ;        // �ݱ�FL��  
		        	sWrite += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(AVG_ACDNT_SCR                ))) + sDiv;  // �ݱ�����   

		        	// ��������
		        	sWrite +=                         Util.trim((String    ) rowRacer.getAttribute(VIEW_MOT_NO                  ));  // ���͹�ȣ
		        	sWrite += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_AVG_RANK_SCR           ))) + sDiv;  // �������������
		        	sWrite += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_HIGH_RANK_RATIO        ))) + sDiv;  // ���Ϳ�����
		        	sWrite += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_HIGH_3_RANK_RATIO      ))) + sDiv;  // ����3������

		        	// ��Ʈ ����
		        	sWrite +=                         Util.trim((String    ) rowRacer.getAttribute(VIEW_BOAT_NO                 ));  // ��Ʈ��ȣ
		        	sWrite += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TBRAS_AVG_RANK_SCR           ))) + sDiv;  // �������������
		        	sWrite += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TBRAS_HIGH_RANK_RATIO        ))) + sDiv + sDiv;  // ��Ʈ������

		        	sWrite += Util.getStringLen(      Util.trim((String    ) rowRacer.getAttribute(ETC_RACE                     )), 2)  + sDiv;  // �������ְ���
		        	sWrite += Util.getStringLen(      Util.trim(""                                                               ), 4)  + sDiv;  // �������ְ���
		        	sWrite +=      getPreRace(        Util.trim((String    ) rowRacer.getAttribute(PRE_RACE                     ))) + sDiv;  // ���ϰ���
		        	
		        	/*****************************************************************
		        	 * �Ź�������
		        	 ****************************************************************/
		        	sWriteTitle +=                         Util.trim((String    ) rowRacer.getAttribute(RACE_REG_NO                  )) + sDiv + sDiv;       // ����
		        	sWriteTitle +=                         Util.trim((String    ) rowRacer.getAttribute(RACE_REG_COLOR               )) + sDiv;              // ����
		        	sWriteTitle +=                         Util.trim((String    ) rowRacer.getAttribute(RACER_GRD_CD              	 )) + sDiv;              // ���. 2013.1.18
		        	sWriteTitle += Util.getStringLen(      Util.trim((String    ) rowRacer.getAttribute(RACER_PERIO_NO               )), 2) + sDiv;          // ������ȣ
		        	sWriteTitle +=                         Util.trim((String    ) rowRacer.getAttribute(NM_KOR                       )) + sDiv;              // ������
		        	sWriteTitle +=                         Util.trim((String    ) rowRacer.getAttribute(RACER_SEX                    )) + sDiv;              // ����
		        	sWriteTitle += Util.getStringLen(      Util.trim((BigDecimal) rowRacer.getAttribute(AGE                          )), 2) + sDiv;          // ����
		        	sWriteTitle += Util.getStringLen(      Util.trim((BigDecimal) rowRacer.getAttribute(ENTRY_BODY_WGHT              )), 2);                 // ü��

		        	sWriteTitle += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_RANK_SCR     ))) + sDiv;             // 6ȸ���������             
		        	sWriteTitle += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_SCR          ))) + sDiv;             // 6ȸ��յ���               
		        	sWriteTitle += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_WIN_RATIO        ))) + sDiv;             // 6ȸ�·�                   
		        	sWriteTitle += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_HIGH_RANK_RATIO  ))) + sDiv;             // 6ȸ������                 
		        	sWriteTitle += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_HIGH_3_RANK_RATIO))) + sDiv;             // 6ȸ3������                
		        	sWriteTitle += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_STRT_TM      ))) + sDiv + sDiv;      // 6ȸ���ST          
		        	sWriteTitle +=      get8Race (         Util.trim((String    ) rowRacer.getAttribute(TRRAS_TMS_8_RANK_ORD         )));                    // 8���� ����                       
		        	sWriteTitle += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_AVG_RANK_SCR           ))) + sDiv;             // ���������                
		        	sWriteTitle += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_HIGH_RANK_RATIO        ))) + sDiv + sDiv;      // �·�               
                                                                                                                                                                                  
		        	sWriteTitle +=                         Util.trim((String    ) rowRacer.getAttribute(FL_CNT                       ))   + sDiv;            // �ݱ�FL��            
		        	sWriteTitle += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(AVG_ACDNT_SCR                ))) + sDiv + sDiv;      // �ݱ�����   
                                                                                                                                                                                  
		        	// ��������                                                                                                                                                   
		        	sWriteTitle +=                         Util.trim((String    ) rowRacer.getAttribute(VIEW_MOT_NO                  ))  + sDiv;      		 // ���͹�ȣ      
		        	sWriteTitle += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_AVG_RANK_SCR           ))) + sDiv;             // �������������        
		        	sWriteTitle += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_HIGH_RANK_RATIO        ))) + sDiv;             // ���Ϳ�����            
		        	sWriteTitle += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_HIGH_3_RANK_RATIO      ))) + sDiv + sDiv;             // ����3������           
                                                                                                                                                                                  
		        	// ��Ʈ ����                                                                                                                                                  
		        	sWriteTitle +=                         Util.trim((String    ) rowRacer.getAttribute(VIEW_BOAT_NO                 ))  + sDiv;      // ��Ʈ��ȣ      
		        	sWriteTitle += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TBRAS_AVG_RANK_SCR           ))) + sDiv;             // �������������        
		        	sWriteTitle += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TBRAS_HIGH_RANK_RATIO        ))) + sDiv;             // ��Ʈ������           
                                                                                                                                                                                  
		        	sWriteTitle += Util.getStringLen(      Util.trim((String    ) rowRacer.getAttribute(ETC_RACE                     )), 4)  + sDiv;  // �������ְ���
		        	sWriteTitle += Util.getStringLen(      Util.trim(""                                                               ), 4)  + sDiv;  // �������ְ���

		        	write(fosFileOutMail , sWriteTitle + sReturn);	//���Ͽ뿡�� ���ϰ��ּ����� �������� �ʴ´�.
		        	
		        	sWriteTitle +=      getPreRace(        Util.trim((String    ) rowRacer.getAttribute(PRE_RACE                     ))) + sDiv;  		// ���ϰ���

		        	write(fosFileOut     , sWrite      + sReturn);
		        	write(fosFileOutTitle, sWriteTitle + sReturn);
		        }

		        write(fosFileOut     , sReturn);
		        write(fosFileOutTitle, sReturn);
		        write(fosFileOutMail , sReturn);
	        }
    	} catch ( Exception e ) {
    		Util.setSvcMsg(ctx, e.toString());
    		logger.logError(e);
    	} finally {
        	fosFileOut     .close();
			fosFileOutTitle.close();
			fosFileOutMail.close();
        }
    }
    
    /**
     * <p> ���Ͽ� �ش系�� ���� </p>
     */
    public void write(FileOutputStream fosFileOut, String sString) throws Exception {
    	
        byte[] Buffer = sString.getBytes();
        int    len    = Buffer.length;
        
        fosFileOut.write(Buffer, 0, len);
    }

    /**
     * <p> �ֱ� 8������ ����� ���� �ֱ� </p>
     */
    public String get8Race(String s8Race) {
    	if ( s8Race == null ) s8Race = "";
    	for ( int i = s8Race.length(); i < 8; i++ ) {
    		s8Race = s8Race + " ";
    	}

		s8Race = s8Race.substring(0, 4) + " " + s8Race.substring(4);

        return s8Race;
    }
    
    /**
     * <p> �ٸ� ������ �����ڸ� �������� ���� </p>
     */
    public String getPreRace(String sRace) {
    	if ( sRace == null ) sRace = "";

    	return sRace.replaceAll(",", "    ");
    }
    
    /**
     * <p> �μ� Ÿ��Ʋ ���� </p>
     */
    public void printTitle(FileOutputStream fosFileOut) throws Exception {
    	
    	String sWrite  = "";
    	String sReturn = "\n";
    	
        sWrite = "------------------------------------------------------------------------------------------------------------------------------------------------";
        write(fosFileOut, sWrite + sReturn);
        sWrite = "�� �� �� �� ��  �� �� �� ü ���  ���  �·� ������ �￬  ���  �ֱ�      ��� ������ F/L  ����� ���� ��� ������ �￬ ��Ʈ  ��� ������ ����";
        write(fosFileOut, sWrite + sReturn);
        sWrite = "�� �� �� ��        �� �� �� ����  ����              ����   ST   8�������� ����                         ����        ����       ����        ����";
        write(fosFileOut, sWrite + sReturn);
        sWrite = "------------------------------------------------------------------------------------------------------------------------------------------------";
        write(fosFileOut, sWrite + sReturn);
        write(fosFileOut, sReturn);
    }
    
    
    /**
     * <p> ���� ���� ����Ʈ�� Dataset���� ���� </p>
     */
    public PosRowSet createRowSet(ArrayList alFileList) {
    	ArrayList alRowSet = new ArrayList();

    	for ( int i = 0; i < alFileList.size(); i++ ) {
    		String sFileName = (String) alFileList.get(i);
        	File   fFile     = new File(sFileName);

        	Map map = new HashMap();
        	map.put("FILE_URL" , sFileName);
        	map.put("FILE_SIZE", Long.toString(fFile.length()));
        	
        	String[] sTemp = sFileName.replace('\\', '/').split("/");
        	map.put("FILE_ID" , sTemp[sTemp.length - 1]);

        	alRowSet.add(new PosRowImpl(map));
    	}
    	PosRowSet rowset = new PosRowSetImpl(alRowSet);

        int i = 0;
        PosColumnDef columnDef[] = new PosColumnDef[5];
        columnDef[i++] = new PosColumnDef("FILE_NAME", 12, 255);
        columnDef[i++] = new PosColumnDef("FILE_URL" , 12, 255);
        columnDef[i++] = new PosColumnDef("FILE_ID"  , 12, 255);
        columnDef[i++] = new PosColumnDef("FILE_SIZE", 12, 20 );
        columnDef[i++] = new PosColumnDef("PROG_BAR" , 12, 3  );
        rowset.setColumnDefs(columnDef);
        
        return rowset;
    }

    /**
     * <p> �����ڰ� ',' �� ���� ���� </p>
     */
    public void createFilePrint(PosContext ctx, PosRowSet rowsetRace) throws Exception {

		createDir(ORGAN_PATH);
    	String sFileName = "";
    	sFileName = (String) ctx.get("STND_YEAR") 
		          + (String) ctx.get("MBR_CD") 
		          + Util.getFormat("000", (String) ctx.get("TMS"))
		          + Util.getFormat("000", (String) ctx.get("DAY_ORD"))
		          + Util.getFormat("000", (String) ctx.get("ORGAN_STAT_CD"))
		          + "excel"
		          + ".csv";

		FileOutputStream fosFileOutExcel = new FileOutputStream(ORGAN_PATH + sFileName);
    	alFileList.add(ORGAN_PATH + sFileName);

    	sFileName = (String) ctx.get("STND_YEAR") 
				  + (String) ctx.get("MBR_CD") 
				  + Util.getFormat("000", (String) ctx.get("TMS"))
			  	  + Util.getFormat("000", (String) ctx.get("DAY_ORD"))
				  + Util.getFormat("000", (String) ctx.get("ORGAN_STAT_CD"))
				  + "print"
				  + ".prn";
		
		FileOutputStream fosFileOutPrint = new FileOutputStream(ORGAN_PATH + sFileName);
    	alFileList.add(ORGAN_PATH + sFileName);

    	sFileName = (String) ctx.get("STND_YEAR") 
			      + (String) ctx.get("MBR_CD") 
			      + Util.getFormat("000", (String) ctx.get("TMS"))
			      + Util.getFormat("000", (String) ctx.get("DAY_ORD"))
			      + Util.getFormat("000", (String) ctx.get("ORGAN_STAT_CD"))
			      + "homepage"
			      + ".prn";

		FileOutputStream fosFileOutHomepage = new FileOutputStream(ORGAN_PATH + sFileName);
    	alFileList.add(ORGAN_PATH + sFileName);

    	sFileName = (String) ctx.get("STND_YEAR") 
	              + (String) ctx.get("MBR_CD") 
	              + Util.getFormat("000", (String) ctx.get("TMS"))
	              + Util.getFormat("000", (String) ctx.get("DAY_ORD"))
	              + Util.getFormat("000", (String) ctx.get("ORGAN_STAT_CD"))
	              + ""
	              + ".prn";

		FileOutputStream fosFileOut  = new FileOutputStream(ORGAN_PATH + sFileName);
		alFileList.add(ORGAN_PATH + sFileName);
		
		sFileName = (String) ctx.get("STND_YEAR") 
		          + (String) ctx.get("MBR_CD") 
	         	  + Util.getFormat("000", (String) ctx.get("TMS"))
		          + Util.getFormat("000", (String) ctx.get("DAY_ORD"))
		          + Util.getFormat("000", (String) ctx.get("ORGAN_STAT_CD"))
		          + "atch"
		          + ".prn";
		
		FileOutputStream fosFileOutAtch  = new FileOutputStream(ORGAN_PATH + sFileName);
		alFileList.add(ORGAN_PATH + sFileName);

		String sWrite  = "";
		String sReturn = "\r\n";
		String sDiv    = ",";

		PosRow aRowRace [] = rowsetRace .getAllRow();

		try {
			String sRaceDay = (String) ctx.get("RACE_DAY");
    		sWrite = " ������ : " 
    			   + sRaceDay.substring(2, 4) + "."
    		       + sRaceDay.substring(4, 6) + "."
		       	   + sRaceDay.substring(6, 8);
	        write(fosFileOutExcel   , sReturn);
	        write(fosFileOutExcel   , sWrite + sReturn);

	        write(fosFileOutPrint   , sWrite + sReturn);
	        write(fosFileOutPrint   , sReturn);

	        write(fosFileOutHomepage, sWrite + sReturn);
	        write(fosFileOutHomepage, sReturn);

	        write(fosFileOut        , sWrite + sReturn);
	        write(fosFileOut        , sReturn);
	        
	        int raceRegNoCnt = 0;

	        for ( int i = 0; i < aRowRace.length; i++ ) {
	        	
	        	
	        	// �������� �μ�
	        	PosRow rowRace = aRowRace [i];
	        	String sRaceNo = (String) rowRace.getAttribute("RACE_NO");
	        	String sStMehdCd = (String) rowRace.getAttribute("ST_MTHD_CD");
	        	String sStartType = "�ö���";
	        	if("001".equals(sStMehdCd)) sStartType = "�¶���";
	        	
	        	ctx.put("RACE_NO", sRaceNo);
	        	
		        write(fosFileOutPrint   , sReturn);
		        write(fosFileOutHomepage, sReturn);
		        write(fosFileOut        , sReturn);

	        	sWrite = "���� " + Util.getStringLen(Integer.toString(Integer.parseInt(sRaceNo)), 2) + " ������"
	        	       + " " + rowRace.getAttribute("RACE_DGRE") + "���� " + sStartType
	        	       + " " + rowRace.getAttribute("TURN_CNT") + "��ȸ"
	        	       + "(  " + Util.getNumFormat(((BigDecimal) rowRace.getAttribute("RACE_DIST")).toString()) + "m )"
	        	       + " ���ֽð� " + Util.getTime((String) rowRace.getAttribute("STRT_TIME"));
		        write(fosFileOutExcel, sWrite + sReturn);

		        write(fosFileOutPrint   , sWrite + sReturn);
		        write(fosFileOutPrint   , sReturn);
		        write(fosFileOutHomepage, sWrite + sReturn);
		        write(fosFileOutHomepage, sReturn);
		        write(fosFileOut        , sWrite + sReturn);
		        write(fosFileOut        , sReturn);
		        
		        /*  IWORK_SFR-008 ���� ������ �޴� ����  */
		        // ������
	        	raceRegNoCnt = ((BigDecimal)rowRace.getAttribute("RACE_REG_NO_CNT")).intValue();
	        			        
		        
		    	PosParameter paramRaces = new PosParameter();
		        int k = 0;

		        String sQueryId = "tbeb_organ_fb007";
		        String sDayOrd = (String)ctx.get("DAY_ORD");
		        if ("3".equals(sDayOrd)) sQueryId = "tbeb_organ_fb007_3";

		     // ����ǥ ��ȸ
		        paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        //paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        //paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        //paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        //paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        //paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        //paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("DAY_ORD"  ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("RACE_NO"  ));
		        
		        paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("DAY_ORD"  ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("RACE_NO"  ));
		        
		        paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("DAY_ORD"  ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("DAY_ORD"  ));

		        if ("3".equals(sDayOrd)) {
		        	paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		            paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		            paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		            paramRaces.setWhereClauseParameter(k++, ctx.get("DAY_ORD"  ));
		            paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		            paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		            paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		            paramRaces.setWhereClauseParameter(k++, ctx.get("DAY_ORD"  ));
		        }
		        paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"   ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"      ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("DAY_ORD"  ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("RACE_NO"  ));

		        PosRowSet rowsetRacer = this.getDao("boadao").find(sQueryId, paramRaces);
/*
		        paramRaces.setWhereClauseParameter(k++, ctx.get("STND_YEAR"    ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("MBR_CD"       ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("TMS"          ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("DAY_ORD"      ));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("ORGAN_STAT_CD"));
		        paramRaces.setWhereClauseParameter(k++, ctx.get("RACE_NO"      ));

		        PosRowSet rowsetRacer = this.getDao("boadao").find("tbeb_race_doc_fb001", paramRaces);
*/
		        //���� ���� �μ�
		        while ( rowsetRacer.hasNext() ) {
		        	PosRow rowRacer = rowsetRacer.next();
		        	String sWritePrint    = "";
		        	String sWriteHomepage = "";
		        	String sWriteNormal   = "";
		        	String sWriteAtch     = "";
		        	
		        	
		        	
		        	if ( !Util.trim(rowRacer.getAttribute("ABSE_CD")).equals("001") ) {
			        	sWrite +=                        Util.trim((String    ) rowRacer.getAttribute(RACE_REG_NO                  )) + sDiv;  // ����
			        	sWrite +=                        Util.trim((String    ) rowRacer.getAttribute(RACE_REG_COLOR               )) + sDiv;         // ����
			        	sWrite +=                        Util.trim((String    ) rowRacer.getAttribute(RACER_GRD_CD                 )) + sDiv;         // ���, 2013.1.18
			        	sWrite += Util.getStringLen(     Util.trim((String    ) rowRacer.getAttribute(RACER_PERIO_NO               )), 2) + sDiv;  // ������ȣ
			        	sWrite +=                        Util.trim((String    ) rowRacer.getAttribute(NM_KOR                       )) + sDiv;         // ������
			        	sWrite +=                        Util.trim((String    ) rowRacer.getAttribute(RACER_SEX                    )) + sDiv;         // ����
			        	sWrite +=                        Util.trim((BigDecimal) rowRacer.getAttribute(AGE                          )) + sDiv;         // ����
			        	sWrite += Util.getStringLen(     Util.trim((BigDecimal) rowRacer.getAttribute(ENTRY_BODY_WGHT              )), 2) + sDiv;     // ü��
		        		sWrite += "                       ��        ��                   " + sDiv;
				        write(fosFileOutExcel   , sWrite + sReturn);
				        write(fosFileOutPrint   , sWrite + sReturn);
				        write(fosFileOutHomepage, sWrite + sReturn);
				        write(fosFileOut        , sWrite + sReturn);
				        
			        	sWrite  =                        Util.trim((String    ) rowRacer.getAttribute(RACE_REG_NO                  )) + sDiv;  // ����
			        	sWrite +=                        Util.trim((String    ) rowRacer.getAttribute(NM_KOR                       )) + sDiv;         // ������
			        	sWrite +=                        Util.trim((BigDecimal) rowRacer.getAttribute(AGE                          )) + sDiv;         // ����
			        	sWrite += Util.getStringLen(     Util.trim((BigDecimal) rowRacer.getAttribute(ENTRY_BODY_WGHT              )), 2) + sDiv;     // ü��
		        		sWrite += "                       ��        ��                   " + sDiv;
				        write(fosFileOutAtch    , sWrite + sReturn);
		        		continue;
		        	}
		        	
		        	/*****************************************************************
		        	 * �μ�
		        	 ****************************************************************/
		        	sWritePrint +=                         Util.trim((String    ) rowRacer.getAttribute(RACE_REG_NO                  )) + sDiv;        // ����
		        	sWritePrint +=                         Util.trim((String    ) rowRacer.getAttribute(RACE_REG_COLOR               )) + sDiv;        // ����
		        	sWritePrint +=                         Util.trim((String    ) rowRacer.getAttribute(RACER_GRD_CD                 )) + sDiv;        // ���, 2013.1.18
		        	sWritePrint += Util.getStringLen(      Util.trim((String    ) rowRacer.getAttribute(RACER_PERIO_NO               )), 2) + sDiv;    // ������ȣ
		        	sWritePrint +=                         Util.trim((String    ) rowRacer.getAttribute(NM_KOR                       )) + sDiv;        // ������
		        	sWritePrint +=                         Util.trim((String    ) rowRacer.getAttribute(RACER_SEX                    )) + sDiv;        // ����
		        	sWritePrint +=                         Util.trim((BigDecimal) rowRacer.getAttribute(AGE                          )) + sDiv;        // ����
		        	sWritePrint += Util.getStringLen(      Util.trim((BigDecimal) rowRacer.getAttribute(ENTRY_BODY_WGHT              )), 2) + sDiv;    // ü��

		        	sWritePrint += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_RANK_SCR     ))) + sDiv;       // 6ȸ���������  
		        	sWritePrint += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_SCR          ))) + " " + sDiv;       // 6ȸ��յ���    
		        	sWritePrint += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_WIN_RATIO        ))) + sDiv;       // 6ȸ�·�        
		        	sWritePrint += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_HIGH_RANK_RATIO  ))) + sDiv;       // 6ȸ������      
		        	sWritePrint += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_HIGH_3_RANK_RATIO))) + sDiv;       // 6ȸ3������     
		        	sWritePrint += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_STRT_TM      ))) + sDiv;       // 6ȸ���ST      
		        	sWritePrint +=      get8Race (        Util.trim((String    ) rowRacer.getAttribute(TRRAS_TMS_8_RANK_ORD         ))) + sDiv;       // 8���� ����     

		        	sWritePrint +=                        Util.trim((String    ) rowRacer.getAttribute(FL_CNT                       ))  + sDiv;       // �ݱ�FL��  
		        	sWritePrint += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(AVG_ACDNT_SCR                ))) + sDiv;       // �ݱ�����   

		        	sWritePrint +=                         Util.trim((String    ) rowRacer.getAttribute(VIEW_MOT_NO                  ))  + sDiv;       // ���͹�ȣ
		        	sWritePrint += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_AVG_RANK_SCR           ))) + sDiv;       // �������������
		        	sWritePrint += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_HIGH_RANK_RATIO        ))) + sDiv;       // ���Ϳ�����
		        	sWritePrint += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_HIGH_3_RANK_RATIO      ))) + sDiv;       // ����3������
		        	
		        	sWritePrint +=                         Util.trim((String    ) rowRacer.getAttribute(ETC_RACE                     ))  + sDiv;       // �������ְ���
		        	
		        	/*****************************************************************
		        	 * Ȩ������
		        	 ****************************************************************/
		        	sWriteHomepage +=                         Util.trim((String    ) rowRacer.getAttribute(RACE_REG_NO                  )) + sDiv;        // ����
		        	sWriteHomepage +=                         Util.trim((String    ) rowRacer.getAttribute(RACE_REG_COLOR               )) + sDiv;        // ����
		        	sWriteHomepage +=                         Util.trim((String    ) rowRacer.getAttribute(RACER_GRD_CD                 )) + sDiv;        // ���, 2013.1.18
		        	sWriteHomepage += Util.getStringLen(      Util.trim((String    ) rowRacer.getAttribute(RACER_PERIO_NO               )), 2) + sDiv;    // ������ȣ
		        	sWriteHomepage +=                         Util.trim((String    ) rowRacer.getAttribute(NM_KOR                       )) + sDiv;        // ������
		        	sWriteHomepage +=                         Util.trim((String    ) rowRacer.getAttribute(RACER_SEX                    )) + sDiv;        // ����
		        	sWriteHomepage +=                         Util.trim((BigDecimal) rowRacer.getAttribute(AGE                          )) + sDiv;        // ����
		        	sWriteHomepage += Util.getStringLen(      Util.trim((BigDecimal) rowRacer.getAttribute(ENTRY_BODY_WGHT              )), 2) + sDiv;    // ü��

		        	sWriteHomepage += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_RANK_SCR     ))) + sDiv;       // 6ȸ���������  
		        	sWriteHomepage += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_SCR          ))) + " " + sDiv;       // 6ȸ��յ���    
		        	sWriteHomepage += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_WIN_RATIO        ))) + sDiv;       // 6ȸ�·�        
		        	sWriteHomepage += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_HIGH_RANK_RATIO  ))) + sDiv;       // 6ȸ������      
		        	sWriteHomepage += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_HIGH_3_RANK_RATIO))) + sDiv;       // 6ȸ3������     
		        	sWriteHomepage += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_STRT_TM      ))) + sDiv;       // 6ȸ���ST      
		        	sWriteHomepage +=      get8Race (        Util.trim((String    ) rowRacer.getAttribute(TRRAS_TMS_8_RANK_ORD         ))) + sDiv;       // 8���� ����     
		        	sWriteHomepage += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_AVG_RANK_SCR           ))) + sDiv;             // ���������                
		        	sWriteHomepage += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_HIGH_RANK_RATIO        ))) + sDiv;      // �·�               

		        	sWriteHomepage +=                        Util.trim((String    ) rowRacer.getAttribute(FL_CNT                       ))  + sDiv;       // �ݱ�FL��  
		        	sWriteHomepage += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(AVG_ACDNT_SCR                ))) + sDiv;       // �ݱ�����   

		        	// ��������
		        	sWriteHomepage  +=                        Util.trim((String    ) rowRacer.getAttribute(VIEW_MOT_NO                  ))  + sDiv;       // ���͹�ȣ
		        	sWriteHomepage  += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_AVG_RANK_SCR           ))) + sDiv;       // �������������
		        	sWriteHomepage  += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_HIGH_RANK_RATIO        ))) + sDiv;       // ���Ϳ�����
		        	sWriteHomepage  += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_HIGH_3_RANK_RATIO      ))) + sDiv;       // ����3������

		        	// ��Ʈ ����
		        	sWriteHomepage  +=                        Util.trim((String    ) rowRacer.getAttribute(VIEW_BOAT_NO                 ))  + sDiv;       // ��Ʈ��ȣ
		        	sWriteHomepage  += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TBRAS_AVG_RANK_SCR           ))) + sDiv;       // �������������
		        	sWriteHomepage  += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TBRAS_HIGH_RANK_RATIO        ))) + sDiv;       // ��Ʈ������

		        	sWriteHomepage  +=                         Util.trim((String    ) rowRacer.getAttribute(ETC_RACE                     ))  + sDiv;       // �������ְ���
//		        	sWriteHomepage  +=      getPreRace(        Util.trim((String    ) rowRacer.getAttribute(PRE_RACE                     ))) + sDiv;       // ���ϰ���    
		        	
		        	/*****************************************************************
		        	 * �Ϲ�
		        	 ****************************************************************/
		        	sWriteNormal +=                         Util.trim((String    ) rowRacer.getAttribute(RACE_REG_NO                  )) + sDiv;        // ����
		        	sWriteNormal +=                         Util.trim((String    ) rowRacer.getAttribute(RACE_REG_COLOR               )) + sDiv;        // ����
		        	sWriteNormal +=                         Util.trim((String    ) rowRacer.getAttribute(RACER_GRD_CD                 )) + sDiv;        // ���, 2013.1.18
		        	sWriteNormal += Util.getStringLen(      Util.trim((String    ) rowRacer.getAttribute(RACER_PERIO_NO               )), 2) + sDiv;    // ������ȣ
		        	sWriteNormal +=                         Util.trim((String    ) rowRacer.getAttribute(NM_KOR                       )) + sDiv;        // ������
		        	sWriteNormal +=                         Util.trim((String    ) rowRacer.getAttribute(RACER_SEX                    )) + sDiv;        // ����
		        	sWriteNormal +=                         Util.trim((BigDecimal) rowRacer.getAttribute(AGE                          )) + sDiv;        // ����
		        	sWriteNormal += Util.getStringLen(      Util.trim((BigDecimal) rowRacer.getAttribute(ENTRY_BODY_WGHT              )), 2) + sDiv;    // ü��

		        	sWriteNormal += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_RANK_SCR     ))) + sDiv;        // 6ȸ���������  
		        	sWriteNormal += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_SCR          ))) + " " + sDiv;  // 6ȸ��յ���    
		        	sWriteNormal += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_WIN_RATIO        ))) + sDiv;        // 6ȸ�·�        
		        	sWriteNormal += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_HIGH_RANK_RATIO  ))) + sDiv;        // 6ȸ������      
		        	sWriteNormal += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_HIGH_3_RANK_RATIO))) + sDiv;        // 6ȸ3������     
		        	sWriteNormal += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_STRT_TM      ))) + sDiv;        // 6ȸ���ST      
		        	sWriteNormal +=      get8Race (        Util.trim((String    ) rowRacer.getAttribute(TRRAS_TMS_8_RANK_ORD         ))) + sDiv;        // 8���� ����     
		        	sWriteNormal += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_AVG_RANK_SCR           ))) + sDiv;        // ���������                
		        	sWriteNormal += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_HIGH_RANK_RATIO        ))) + sDiv;        // �·�               

		        	sWriteNormal +=                        Util.trim((String    ) rowRacer.getAttribute(FL_CNT                       ))  + sDiv;        // �ݱ�FL��  
		        	sWriteNormal += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(AVG_ACDNT_SCR                ))) + sDiv;        // �ݱ�����   

/*
		        	// ��������
		        	sWriteNormal  +=                         Util.trim((String    ) rowRacer.getAttribute(VIEW_MOT_NO                  ))  + sDiv;       // ���͹�ȣ
		        	sWriteNormal  += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_AVG_RANK_SCR           ))) + sDiv;       // �������������
		        	sWriteNormal  += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_HIGH_RANK_RATIO        ))) + sDiv;       // ���Ϳ�����
		        	sWriteNormal  += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_HIGH_3_RANK_RATIO      ))) + sDiv;       // ����3������

		        	// ��Ʈ ����
		        	sWriteNormal  +=                         Util.trim((String    ) rowRacer.getAttribute(VIEW_BOAT_NO                 ))  + sDiv;       // ��Ʈ��ȣ
		        	sWriteNormal  += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TBRAS_HIGH_RANK_RATIO        ))) + sDiv;       // ��Ʈ������
*/
		        	sWriteNormal  +=                         Util.trim((String    ) rowRacer.getAttribute(ETC_RACE                     ))  + sDiv;       // �������ְ���
		        	sWriteNormal  +=      getPreRace(        Util.trim((String    ) rowRacer.getAttribute(PRE_RACE                     ))) + sDiv;       // ���ϰ���    
		        	
		        	/*****************************************************************
		        	 * ÷��
		        	 ****************************************************************/
		        	sWriteAtch  +=                         Util.trim((String    ) rowRacer.getAttribute(RACE_REG_NO                  )) + sDiv;        // ����
		        	sWriteAtch  +=                         Util.trim((String    ) rowRacer.getAttribute(NM_KOR                       )) + sDiv;        // ������

		        	sWriteAtch  += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(MON_6_HIGH_RANK_RATIO         ))) + sDiv;       // 6ȸ�·�        
		        	sWriteAtch  +=                        Util.trim((BigDecimal) rowRacer.getAttribute(MON_6_RACE_CNT                ))  + sDiv;       // 6ȸ�·�        
		        	sWriteAtch  +=                        Util.trim((String    ) rowRacer.getAttribute(MON_6_RACE_REG_NO_1           ))  + sDiv;       // 6ȸ�·�        
		        	sWriteAtch  +=                        Util.trim((String    ) rowRacer.getAttribute(MON_6_RACE_REG_NO_2           ))  + sDiv;       // 6ȸ�·�        
		        	sWriteAtch  +=                        Util.trim((String    ) rowRacer.getAttribute(MON_6_RACE_REG_NO_3           ))  + sDiv;       // 6ȸ�·�        
		        	sWriteAtch  +=                        Util.trim((String    ) rowRacer.getAttribute(MON_6_RACE_REG_NO_4           ))  + sDiv;       // 6ȸ�·�        
		        	sWriteAtch  +=                        Util.trim((String    ) rowRacer.getAttribute(MON_6_RACE_REG_NO_5           ))  + sDiv;       // 6ȸ�·�        
		        	sWriteAtch  +=                        Util.trim((String    ) rowRacer.getAttribute(MON_6_RACE_REG_NO_6           ))  + sDiv;       // 6ȸ�·�      
		        	
		        	/*  IWORK_SFR-008 ���� ������ �޴� ����  */ 
		        	if(raceRegNoCnt>=7)sWriteAtch  +=                        Util.trim((String    ) rowRacer.getAttribute(MON_6_RACE_REG_NO_7           ))  + sDiv;       // 6ȸ�·�
		        	if(raceRegNoCnt==8)sWriteAtch  +=                        Util.trim((String    ) rowRacer.getAttribute(MON_6_RACE_REG_NO_8           ))  + sDiv;       // 6ȸ�·�

		        	// ��������
		        	sWriteAtch  +=                         Util.trim((String    ) rowRacer.getAttribute(VIEW_MOT_NO                  ))  + sDiv;       // ���͹�ȣ
		        	sWriteAtch  += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_AVG_RANK_SCR           ))) + sDiv;       // �������������
		        	sWriteAtch  += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_HIGH_RANK_RATIO        ))) + sDiv;       // ���Ϳ�����
		        	sWriteAtch  += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_HIGH_3_RANK_RATIO      ))) + sDiv;       // ����3������

		        	if ( Util.trim((String) rowRacer.getAttribute(MOT_PRE_RECE1)).equals("") ) {
			        	sWriteAtch  +=                        "/"  + sDiv;       // 6ȸ�·�
		        	} else {
			        	sWriteAtch  +=                        Util.trim((String    ) rowRacer.getAttribute(MOT_PRE_RECE1                 ))  + sDiv;       // 6ȸ�·�
		        	}
		        	if ( Util.trim((String) rowRacer.getAttribute(MOT_PRE_RECE2)).equals("") ) {
			        	sWriteAtch  +=                        "/"  + sDiv;       // 6ȸ�·�
		        	} else {
			        	sWriteAtch  +=                        Util.trim((String    ) rowRacer.getAttribute(MOT_PRE_RECE2                 ))  + sDiv;       // 6ȸ�·�        
		        	}

		        	// ��Ʈ ����
		        	sWriteAtch  +=                         Util.trim((String    ) rowRacer.getAttribute(VIEW_BOAT_NO                 ))  + sDiv;       // ��Ʈ��ȣ
		        	sWriteAtch  += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TBRAS_AVG_RANK_SCR           ))) + sDiv;       // �������������
		        	sWriteAtch  += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TBRAS_HIGH_RANK_RATIO        ))) + sDiv;       // ��Ʈ������

		        	sWriteAtch  +=                        Util.trim((String    ) rowRacer.getAttribute(RMK                           ))  + sDiv;       // 6ȸ�·�        

		        	write(fosFileOutExcel   , sWriteHomepage + sReturn);
		        	write(fosFileOutPrint   , sWritePrint    + sReturn);
		        	write(fosFileOutHomepage, sWriteHomepage + sReturn);
		        	write(fosFileOut        , sWriteNormal   + sReturn);
		        	write(fosFileOutAtch    , sWriteAtch     + sReturn);
		        }

		        write(fosFileOutPrint   , sReturn);
		        write(fosFileOutHomepage, sReturn);
		        write(fosFileOut        , sReturn);
	        }
    	} catch ( Exception e ) {
    		Util.setSvcMsg(ctx, e.toString());
    		logger.logError(e);
    	} finally {
			fosFileOutExcel   .close();
			fosFileOutPrint   .close();
			fosFileOutHomepage.close();
			fosFileOut        .close();
			fosFileOutAtch    .close();
        }
    }
}
