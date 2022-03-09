/*================================================================================
 * 시스템			: 편성관리
 * 소스파일 이름	: snis.boa.organization.e02040060.activity.CreateFile.java
 * 파일설명		: 첨부파일생성
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2007-10-26
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
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
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 첨부파일 생성하는 클래스이다.
* @auther 유동훈
* @version 1.0
*/
public class CreateFile extends SnisActivity
{    
	// column명 정의
	String STND_YEAR                     = "STND_YEAR                    ".trim();
	String MBR_CD                        = "MBR_CD                       ".trim();
	String TMS                           = "TMS                          ".trim();
	String DAY_ORD                       = "DAY_ORD                      ".trim();
	String ORGAN_STAT_CD                 = "ORGAN_STAT_CD                ".trim();
	String RACE_NO                       = "RACE_NO                      ".trim();
	String RACER_GRD_CD                  = "RACER_GRD_CD                 ".trim(); //등급추가. 2013.1.18
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
	
	// 파일 생성 위치 정의
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
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String		sucess 문자열
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
    	// 사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	
        searchState(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> 조회시작 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected void searchState(PosContext ctx) 
    {
    	
    	// 경주조회
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
     * <p> 구분자가 공백인 파일 생성 </p>
     * @param   ctx		PosContext	저장소
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
    		sWrite = " 경주일 : " 
    			   + sRaceDay.substring(2, 4) + "."
    		       + sRaceDay.substring(4, 6) + "."
		       	   + sRaceDay.substring(6, 8);
	        write(fosFileOut     , sWrite + sReturn);
	        write(fosFileOut     , sReturn);
	        write(fosFileOutTitle, sWrite + sReturn);
	        write(fosFileOutTitle, sReturn);
	        write(fosFileOutMail,  sWrite + sReturn);
	        write(fosFileOutMail,  sReturn);

	        // 경주정보조회
            for ( int i = 0; i < aRowRace.length; i++ ) {
	        	
	        	// 경주정보 인쇄
	        	PosRow rowRace = aRowRace [i];
	        	String sRaceNo = (String) rowRace.getAttribute("RACE_NO");
	        	String sStMehdCd = (String) rowRace.getAttribute("ST_MTHD_CD");
	        	String sStartType = "플라잉";
	        	if("001".equals(sStMehdCd)) sStartType = "온라인";
	        	
	        	ctx.put("RACE_NO", sRaceNo);
	        	
		        write(fosFileOut     , sReturn);
		        write(fosFileOutTitle, sReturn);
		        write(fosFileOutMail , sReturn);
	        	sWrite = "제【 " + Util.getStringLen(Integer.toString(Integer.parseInt(sRaceNo)), 2) + " 】경주"
	        	       + " " + rowRace.getAttribute("RACE_DGRE") + "경주 " + sStartType
	        	       + " " + rowRace.getAttribute("TURN_CNT") + "주회"
	        	       + "(  " + Util.getNumFormat(((BigDecimal) rowRace.getAttribute("RACE_DIST")).toString()) + "m )"
	        	       + " 발주시간 " + Util.getTime((String) rowRace.getAttribute("STRT_TIME"));
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
		        //선수 정보 인쇄
		        while ( rowsetRacer.hasNext() ) {
		        	PosRow rowRacer = rowsetRacer.next();
		        	
		        	       sWrite      = "";
		        	String sWriteTitle = "";

		        	if ( !Util.trim(rowRacer.getAttribute("ABSE_CD")).equals("001") ) {
			        	sWrite +=                        Util.trim((String    ) rowRacer.getAttribute(RACE_REG_NO                  )) + sDiv;  // 정번
			        	sWrite +=                        Util.trim((String    ) rowRacer.getAttribute(RACE_REG_COLOR               )) + sDiv;  // 색상
			        	sWrite +=                        Util.trim((String    ) rowRacer.getAttribute(RACER_GRD_CD                 )) + sDiv;  // 등급. 2013.1.18
			        	sWrite += Util.getStringLen(     Util.trim((String    ) rowRacer.getAttribute(RACER_PERIO_NO               )), 2) + sDiv;  // 선수번호
			        	sWrite +=                        Util.trim((String    ) rowRacer.getAttribute(NM_KOR                       )) + sDiv;  // 선수명
			        	sWrite +=                        Util.trim((String    ) rowRacer.getAttribute(RACER_SEX                    )) + sDiv;  // 성별
			        	sWrite +=                        Util.trim((BigDecimal) rowRacer.getAttribute(AGE                          )) + sDiv;  // 나이
			        	sWrite += Util.getStringLen(     Util.trim((BigDecimal) rowRacer.getAttribute(ENTRY_BODY_WGHT              )), 2) + sDiv;  // 체중
		        		sWrite += "                       결        장                    ";
				        write(fosFileOut     , sWrite + sReturn);
				        write(fosFileOutTitle, sWrite + sReturn);
				        write(fosFileOutMail , sWrite + sReturn);
		        		continue;
		        	}
		        	
		        	/*****************************************************************
		        	 * 전체
		        	 ****************************************************************/
		        	sWrite +=                         Util.trim((String    ) rowRacer.getAttribute(RACE_REG_NO                  )) + sDiv;  // 정번
		        	sWrite +=                         Util.trim((String    ) rowRacer.getAttribute(RACE_REG_COLOR               )) + sDiv;  // 색상
		        	sWrite +=                         Util.trim((String    ) rowRacer.getAttribute(RACER_GRD_CD                 )) + sDiv;  // 등급. 2013.1.18
		        	sWrite += Util.getStringLen(      Util.trim((String    ) rowRacer.getAttribute(RACER_PERIO_NO               )), 2) + sDiv;  // 선수번호
		        	sWrite +=                         Util.trim((String    ) rowRacer.getAttribute(NM_KOR                       )) + sDiv;  // 선수명
		        	sWrite +=                         Util.trim((String    ) rowRacer.getAttribute(RACER_SEX                    )) + sDiv;  // 성별
		        	sWrite +=                         Util.trim((BigDecimal) rowRacer.getAttribute(AGE                          )) + sDiv;  // 나이
		        	sWrite += Util.getStringLen(      Util.trim((BigDecimal) rowRacer.getAttribute(ENTRY_BODY_WGHT              )), 2);     // 체중

		        	sWrite += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_RANK_SCR     ))) + sDiv;  // 6회평균착순점  
		        	sWrite += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_SCR          ))) + sDiv;  // 6회평균득점    
		        	sWrite += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_WIN_RATIO        ))) + sDiv;  // 6회승률        
		        	sWrite += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_HIGH_RANK_RATIO  ))) + sDiv;  // 6회연대율      
		        	sWrite += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_HIGH_3_RANK_RATIO))) + sDiv;  // 6회3연대율     
		        	sWrite += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_STRT_TM      ))) + sDiv + sDiv;  // 6회평균ST      
		        	sWrite +=      get8Race (         Util.trim((String    ) rowRacer.getAttribute(TRRAS_TMS_8_RANK_ORD         )));         // 8경주 착순     
		        	sWrite += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_AVG_RANK_SCR           ))) + sDiv;  // 평균착순점     
		        	sWrite += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_HIGH_RANK_RATIO        ))) + sDiv + sDiv;  // 승률           

		        	sWrite +=                         Util.trim((String    ) rowRacer.getAttribute(FL_CNT                       ))  ;        // 반기FL수  
		        	sWrite += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(AVG_ACDNT_SCR                ))) + sDiv;  // 반기사고점   

		        	// 모터정보
		        	sWrite +=                         Util.trim((String    ) rowRacer.getAttribute(VIEW_MOT_NO                  ));  // 모터번호
		        	sWrite += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_AVG_RANK_SCR           ))) + sDiv;  // 모터평균착순점
		        	sWrite += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_HIGH_RANK_RATIO        ))) + sDiv;  // 모터연대율
		        	sWrite += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_HIGH_3_RANK_RATIO      ))) + sDiv;  // 모터3연대율

		        	// 보트 정보
		        	sWrite +=                         Util.trim((String    ) rowRacer.getAttribute(VIEW_BOAT_NO                 ));  // 보트번호
		        	sWrite += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TBRAS_AVG_RANK_SCR           ))) + sDiv;  // 모터평균착순점
		        	sWrite += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TBRAS_HIGH_RANK_RATIO        ))) + sDiv + sDiv;  // 보트연대율

		        	sWrite += Util.getStringLen(      Util.trim((String    ) rowRacer.getAttribute(ETC_RACE                     )), 2)  + sDiv;  // 금일출주경주
		        	sWrite += Util.getStringLen(      Util.trim(""                                                               ), 4)  + sDiv;  // 금일출주경주
		        	sWrite +=      getPreRace(        Util.trim((String    ) rowRacer.getAttribute(PRE_RACE                     ))) + sDiv;  // 전일경주
		        	
		        	/*****************************************************************
		        	 * 신문사제목
		        	 ****************************************************************/
		        	sWriteTitle +=                         Util.trim((String    ) rowRacer.getAttribute(RACE_REG_NO                  )) + sDiv + sDiv;       // 정번
		        	sWriteTitle +=                         Util.trim((String    ) rowRacer.getAttribute(RACE_REG_COLOR               )) + sDiv;              // 색상
		        	sWriteTitle +=                         Util.trim((String    ) rowRacer.getAttribute(RACER_GRD_CD              	 )) + sDiv;              // 등급. 2013.1.18
		        	sWriteTitle += Util.getStringLen(      Util.trim((String    ) rowRacer.getAttribute(RACER_PERIO_NO               )), 2) + sDiv;          // 선수번호
		        	sWriteTitle +=                         Util.trim((String    ) rowRacer.getAttribute(NM_KOR                       )) + sDiv;              // 선수명
		        	sWriteTitle +=                         Util.trim((String    ) rowRacer.getAttribute(RACER_SEX                    )) + sDiv;              // 성별
		        	sWriteTitle += Util.getStringLen(      Util.trim((BigDecimal) rowRacer.getAttribute(AGE                          )), 2) + sDiv;          // 나이
		        	sWriteTitle += Util.getStringLen(      Util.trim((BigDecimal) rowRacer.getAttribute(ENTRY_BODY_WGHT              )), 2);                 // 체중

		        	sWriteTitle += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_RANK_SCR     ))) + sDiv;             // 6회평균착순점             
		        	sWriteTitle += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_SCR          ))) + sDiv;             // 6회평균득점               
		        	sWriteTitle += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_WIN_RATIO        ))) + sDiv;             // 6회승률                   
		        	sWriteTitle += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_HIGH_RANK_RATIO  ))) + sDiv;             // 6회연대율                 
		        	sWriteTitle += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_HIGH_3_RANK_RATIO))) + sDiv;             // 6회3연대율                
		        	sWriteTitle += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_STRT_TM      ))) + sDiv + sDiv;      // 6회평균ST          
		        	sWriteTitle +=      get8Race (         Util.trim((String    ) rowRacer.getAttribute(TRRAS_TMS_8_RANK_ORD         )));                    // 8경주 착순                       
		        	sWriteTitle += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_AVG_RANK_SCR           ))) + sDiv;             // 평균착순점                
		        	sWriteTitle += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_HIGH_RANK_RATIO        ))) + sDiv + sDiv;      // 승률               
                                                                                                                                                                                  
		        	sWriteTitle +=                         Util.trim((String    ) rowRacer.getAttribute(FL_CNT                       ))   + sDiv;            // 반기FL수            
		        	sWriteTitle += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(AVG_ACDNT_SCR                ))) + sDiv + sDiv;      // 반기사고점   
                                                                                                                                                                                  
		        	// 모터정보                                                                                                                                                   
		        	sWriteTitle +=                         Util.trim((String    ) rowRacer.getAttribute(VIEW_MOT_NO                  ))  + sDiv;      		 // 모터번호      
		        	sWriteTitle += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_AVG_RANK_SCR           ))) + sDiv;             // 모터평균착순점        
		        	sWriteTitle += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_HIGH_RANK_RATIO        ))) + sDiv;             // 모터연대율            
		        	sWriteTitle += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_HIGH_3_RANK_RATIO      ))) + sDiv + sDiv;             // 모터3연대율           
                                                                                                                                                                                  
		        	// 보트 정보                                                                                                                                                  
		        	sWriteTitle +=                         Util.trim((String    ) rowRacer.getAttribute(VIEW_BOAT_NO                 ))  + sDiv;      // 보트번호      
		        	sWriteTitle += Util.getFormat("#0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TBRAS_AVG_RANK_SCR           ))) + sDiv;             // 모터평균착순점        
		        	sWriteTitle += Util.getFormat("#00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TBRAS_HIGH_RANK_RATIO        ))) + sDiv;             // 보트연대율           
                                                                                                                                                                                  
		        	sWriteTitle += Util.getStringLen(      Util.trim((String    ) rowRacer.getAttribute(ETC_RACE                     )), 4)  + sDiv;  // 금일출주경주
		        	sWriteTitle += Util.getStringLen(      Util.trim(""                                                               ), 4)  + sDiv;  // 금일출주경주

		        	write(fosFileOutMail , sWriteTitle + sReturn);	//메일용에는 전일경주성적을 포함하지 않는다.
		        	
		        	sWriteTitle +=      getPreRace(        Util.trim((String    ) rowRacer.getAttribute(PRE_RACE                     ))) + sDiv;  		// 전일경주

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
     * <p> 파일에 해당내용 쓰기 </p>
     */
    public void write(FileOutputStream fosFileOut, String sString) throws Exception {
    	
        byte[] Buffer = sString.getBytes();
        int    len    = Buffer.length;
        
        fosFileOut.write(Buffer, 0, len);
    }

    /**
     * <p> 최근 8경주의 가운데에 공백 넣기 </p>
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
     * <p> 다른 경주의 구분자를 공백으로 변경 </p>
     */
    public String getPreRace(String sRace) {
    	if ( sRace == null ) sRace = "";

    	return sRace.replaceAll(",", "    ");
    }
    
    /**
     * <p> 인쇄 타이틀 설정 </p>
     */
    public void printTitle(FileOutputStream fosFileOut) throws Exception {
    	
    	String sWrite  = "";
    	String sReturn = "\n";
    	
        sWrite = "------------------------------------------------------------------------------------------------------------------------------------------------";
        write(fosFileOut, sWrite + sReturn);
        sWrite = "정 색 등 기 성  명 성 나 체 평균  평균  승률 연대율 삼연  평균  최근      평균 연대율 F/L  사고점 모터 평균 연대율 삼연 보트  평균 연대율 금일";
        write(fosFileOut, sWrite + sReturn);
        sWrite = "번 상 급 수        별 이 중 착순  득점              대율   ST   8경주착순 착순                         착순        대율       착순        출주";
        write(fosFileOut, sWrite + sReturn);
        sWrite = "------------------------------------------------------------------------------------------------------------------------------------------------";
        write(fosFileOut, sWrite + sReturn);
        write(fosFileOut, sReturn);
    }
    
    
    /**
     * <p> 생성 파일 리스트를 Dataset으로 변경 </p>
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
     * <p> 구분자가 ',' 인 파일 생성 </p>
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
    		sWrite = " 경주일 : " 
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
	        	
	        	
	        	// 경주정보 인쇄
	        	PosRow rowRace = aRowRace [i];
	        	String sRaceNo = (String) rowRace.getAttribute("RACE_NO");
	        	String sStMehdCd = (String) rowRace.getAttribute("ST_MTHD_CD");
	        	String sStartType = "플라잉";
	        	if("001".equals(sStMehdCd)) sStartType = "온라인";
	        	
	        	ctx.put("RACE_NO", sRaceNo);
	        	
		        write(fosFileOutPrint   , sReturn);
		        write(fosFileOutHomepage, sReturn);
		        write(fosFileOut        , sReturn);

	        	sWrite = "제【 " + Util.getStringLen(Integer.toString(Integer.parseInt(sRaceNo)), 2) + " 】경주"
	        	       + " " + rowRace.getAttribute("RACE_DGRE") + "경주 " + sStartType
	        	       + " " + rowRace.getAttribute("TURN_CNT") + "주회"
	        	       + "(  " + Util.getNumFormat(((BigDecimal) rowRace.getAttribute("RACE_DIST")).toString()) + "m )"
	        	       + " 발주시간 " + Util.getTime((String) rowRace.getAttribute("STRT_TIME"));
		        write(fosFileOutExcel, sWrite + sReturn);

		        write(fosFileOutPrint   , sWrite + sReturn);
		        write(fosFileOutPrint   , sReturn);
		        write(fosFileOutHomepage, sWrite + sReturn);
		        write(fosFileOutHomepage, sReturn);
		        write(fosFileOut        , sWrite + sReturn);
		        write(fosFileOut        , sReturn);
		        
		        /*  IWORK_SFR-008 경정 선수편성 메뉴 개선  */
		        // 정번수
	        	raceRegNoCnt = ((BigDecimal)rowRace.getAttribute("RACE_REG_NO_CNT")).intValue();
	        			        
		        
		    	PosParameter paramRaces = new PosParameter();
		        int k = 0;

		        String sQueryId = "tbeb_organ_fb007";
		        String sDayOrd = (String)ctx.get("DAY_ORD");
		        if ("3".equals(sDayOrd)) sQueryId = "tbeb_organ_fb007_3";

		     // 출주표 조회
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
		        //선수 정보 인쇄
		        while ( rowsetRacer.hasNext() ) {
		        	PosRow rowRacer = rowsetRacer.next();
		        	String sWritePrint    = "";
		        	String sWriteHomepage = "";
		        	String sWriteNormal   = "";
		        	String sWriteAtch     = "";
		        	
		        	
		        	
		        	if ( !Util.trim(rowRacer.getAttribute("ABSE_CD")).equals("001") ) {
			        	sWrite +=                        Util.trim((String    ) rowRacer.getAttribute(RACE_REG_NO                  )) + sDiv;  // 정번
			        	sWrite +=                        Util.trim((String    ) rowRacer.getAttribute(RACE_REG_COLOR               )) + sDiv;         // 색상
			        	sWrite +=                        Util.trim((String    ) rowRacer.getAttribute(RACER_GRD_CD                 )) + sDiv;         // 등급, 2013.1.18
			        	sWrite += Util.getStringLen(     Util.trim((String    ) rowRacer.getAttribute(RACER_PERIO_NO               )), 2) + sDiv;  // 선수번호
			        	sWrite +=                        Util.trim((String    ) rowRacer.getAttribute(NM_KOR                       )) + sDiv;         // 선수명
			        	sWrite +=                        Util.trim((String    ) rowRacer.getAttribute(RACER_SEX                    )) + sDiv;         // 성별
			        	sWrite +=                        Util.trim((BigDecimal) rowRacer.getAttribute(AGE                          )) + sDiv;         // 나이
			        	sWrite += Util.getStringLen(     Util.trim((BigDecimal) rowRacer.getAttribute(ENTRY_BODY_WGHT              )), 2) + sDiv;     // 체중
		        		sWrite += "                       결        장                   " + sDiv;
				        write(fosFileOutExcel   , sWrite + sReturn);
				        write(fosFileOutPrint   , sWrite + sReturn);
				        write(fosFileOutHomepage, sWrite + sReturn);
				        write(fosFileOut        , sWrite + sReturn);
				        
			        	sWrite  =                        Util.trim((String    ) rowRacer.getAttribute(RACE_REG_NO                  )) + sDiv;  // 정번
			        	sWrite +=                        Util.trim((String    ) rowRacer.getAttribute(NM_KOR                       )) + sDiv;         // 선수명
			        	sWrite +=                        Util.trim((BigDecimal) rowRacer.getAttribute(AGE                          )) + sDiv;         // 나이
			        	sWrite += Util.getStringLen(     Util.trim((BigDecimal) rowRacer.getAttribute(ENTRY_BODY_WGHT              )), 2) + sDiv;     // 체중
		        		sWrite += "                       결        장                   " + sDiv;
				        write(fosFileOutAtch    , sWrite + sReturn);
		        		continue;
		        	}
		        	
		        	/*****************************************************************
		        	 * 인쇄
		        	 ****************************************************************/
		        	sWritePrint +=                         Util.trim((String    ) rowRacer.getAttribute(RACE_REG_NO                  )) + sDiv;        // 정번
		        	sWritePrint +=                         Util.trim((String    ) rowRacer.getAttribute(RACE_REG_COLOR               )) + sDiv;        // 색상
		        	sWritePrint +=                         Util.trim((String    ) rowRacer.getAttribute(RACER_GRD_CD                 )) + sDiv;        // 등급, 2013.1.18
		        	sWritePrint += Util.getStringLen(      Util.trim((String    ) rowRacer.getAttribute(RACER_PERIO_NO               )), 2) + sDiv;    // 선수번호
		        	sWritePrint +=                         Util.trim((String    ) rowRacer.getAttribute(NM_KOR                       )) + sDiv;        // 선수명
		        	sWritePrint +=                         Util.trim((String    ) rowRacer.getAttribute(RACER_SEX                    )) + sDiv;        // 성별
		        	sWritePrint +=                         Util.trim((BigDecimal) rowRacer.getAttribute(AGE                          )) + sDiv;        // 나이
		        	sWritePrint += Util.getStringLen(      Util.trim((BigDecimal) rowRacer.getAttribute(ENTRY_BODY_WGHT              )), 2) + sDiv;    // 체중

		        	sWritePrint += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_RANK_SCR     ))) + sDiv;       // 6회평균착순점  
		        	sWritePrint += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_SCR          ))) + " " + sDiv;       // 6회평균득점    
		        	sWritePrint += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_WIN_RATIO        ))) + sDiv;       // 6회승률        
		        	sWritePrint += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_HIGH_RANK_RATIO  ))) + sDiv;       // 6회연대율      
		        	sWritePrint += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_HIGH_3_RANK_RATIO))) + sDiv;       // 6회3연대율     
		        	sWritePrint += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_STRT_TM      ))) + sDiv;       // 6회평균ST      
		        	sWritePrint +=      get8Race (        Util.trim((String    ) rowRacer.getAttribute(TRRAS_TMS_8_RANK_ORD         ))) + sDiv;       // 8경주 착순     

		        	sWritePrint +=                        Util.trim((String    ) rowRacer.getAttribute(FL_CNT                       ))  + sDiv;       // 반기FL수  
		        	sWritePrint += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(AVG_ACDNT_SCR                ))) + sDiv;       // 반기사고점   

		        	sWritePrint +=                         Util.trim((String    ) rowRacer.getAttribute(VIEW_MOT_NO                  ))  + sDiv;       // 모터번호
		        	sWritePrint += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_AVG_RANK_SCR           ))) + sDiv;       // 모터평균착순점
		        	sWritePrint += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_HIGH_RANK_RATIO        ))) + sDiv;       // 모터연대율
		        	sWritePrint += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_HIGH_3_RANK_RATIO      ))) + sDiv;       // 모터3연대율
		        	
		        	sWritePrint +=                         Util.trim((String    ) rowRacer.getAttribute(ETC_RACE                     ))  + sDiv;       // 금일출주경주
		        	
		        	/*****************************************************************
		        	 * 홈페이지
		        	 ****************************************************************/
		        	sWriteHomepage +=                         Util.trim((String    ) rowRacer.getAttribute(RACE_REG_NO                  )) + sDiv;        // 정번
		        	sWriteHomepage +=                         Util.trim((String    ) rowRacer.getAttribute(RACE_REG_COLOR               )) + sDiv;        // 색상
		        	sWriteHomepage +=                         Util.trim((String    ) rowRacer.getAttribute(RACER_GRD_CD                 )) + sDiv;        // 등급, 2013.1.18
		        	sWriteHomepage += Util.getStringLen(      Util.trim((String    ) rowRacer.getAttribute(RACER_PERIO_NO               )), 2) + sDiv;    // 선수번호
		        	sWriteHomepage +=                         Util.trim((String    ) rowRacer.getAttribute(NM_KOR                       )) + sDiv;        // 선수명
		        	sWriteHomepage +=                         Util.trim((String    ) rowRacer.getAttribute(RACER_SEX                    )) + sDiv;        // 성별
		        	sWriteHomepage +=                         Util.trim((BigDecimal) rowRacer.getAttribute(AGE                          )) + sDiv;        // 나이
		        	sWriteHomepage += Util.getStringLen(      Util.trim((BigDecimal) rowRacer.getAttribute(ENTRY_BODY_WGHT              )), 2) + sDiv;    // 체중

		        	sWriteHomepage += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_RANK_SCR     ))) + sDiv;       // 6회평균착순점  
		        	sWriteHomepage += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_SCR          ))) + " " + sDiv;       // 6회평균득점    
		        	sWriteHomepage += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_WIN_RATIO        ))) + sDiv;       // 6회승률        
		        	sWriteHomepage += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_HIGH_RANK_RATIO  ))) + sDiv;       // 6회연대율      
		        	sWriteHomepage += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_HIGH_3_RANK_RATIO))) + sDiv;       // 6회3연대율     
		        	sWriteHomepage += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_STRT_TM      ))) + sDiv;       // 6회평균ST      
		        	sWriteHomepage +=      get8Race (        Util.trim((String    ) rowRacer.getAttribute(TRRAS_TMS_8_RANK_ORD         ))) + sDiv;       // 8경주 착순     
		        	sWriteHomepage += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_AVG_RANK_SCR           ))) + sDiv;             // 평균착순점                
		        	sWriteHomepage += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_HIGH_RANK_RATIO        ))) + sDiv;      // 승률               

		        	sWriteHomepage +=                        Util.trim((String    ) rowRacer.getAttribute(FL_CNT                       ))  + sDiv;       // 반기FL수  
		        	sWriteHomepage += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(AVG_ACDNT_SCR                ))) + sDiv;       // 반기사고점   

		        	// 모터정보
		        	sWriteHomepage  +=                        Util.trim((String    ) rowRacer.getAttribute(VIEW_MOT_NO                  ))  + sDiv;       // 모터번호
		        	sWriteHomepage  += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_AVG_RANK_SCR           ))) + sDiv;       // 모터평균착순점
		        	sWriteHomepage  += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_HIGH_RANK_RATIO        ))) + sDiv;       // 모터연대율
		        	sWriteHomepage  += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_HIGH_3_RANK_RATIO      ))) + sDiv;       // 모터3연대율

		        	// 보트 정보
		        	sWriteHomepage  +=                        Util.trim((String    ) rowRacer.getAttribute(VIEW_BOAT_NO                 ))  + sDiv;       // 보트번호
		        	sWriteHomepage  += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TBRAS_AVG_RANK_SCR           ))) + sDiv;       // 모터평균착순점
		        	sWriteHomepage  += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TBRAS_HIGH_RANK_RATIO        ))) + sDiv;       // 보트연대율

		        	sWriteHomepage  +=                         Util.trim((String    ) rowRacer.getAttribute(ETC_RACE                     ))  + sDiv;       // 금일출주경주
//		        	sWriteHomepage  +=      getPreRace(        Util.trim((String    ) rowRacer.getAttribute(PRE_RACE                     ))) + sDiv;       // 전일경주    
		        	
		        	/*****************************************************************
		        	 * 일반
		        	 ****************************************************************/
		        	sWriteNormal +=                         Util.trim((String    ) rowRacer.getAttribute(RACE_REG_NO                  )) + sDiv;        // 정번
		        	sWriteNormal +=                         Util.trim((String    ) rowRacer.getAttribute(RACE_REG_COLOR               )) + sDiv;        // 색상
		        	sWriteNormal +=                         Util.trim((String    ) rowRacer.getAttribute(RACER_GRD_CD                 )) + sDiv;        // 등급, 2013.1.18
		        	sWriteNormal += Util.getStringLen(      Util.trim((String    ) rowRacer.getAttribute(RACER_PERIO_NO               )), 2) + sDiv;    // 선수번호
		        	sWriteNormal +=                         Util.trim((String    ) rowRacer.getAttribute(NM_KOR                       )) + sDiv;        // 선수명
		        	sWriteNormal +=                         Util.trim((String    ) rowRacer.getAttribute(RACER_SEX                    )) + sDiv;        // 성별
		        	sWriteNormal +=                         Util.trim((BigDecimal) rowRacer.getAttribute(AGE                          )) + sDiv;        // 나이
		        	sWriteNormal += Util.getStringLen(      Util.trim((BigDecimal) rowRacer.getAttribute(ENTRY_BODY_WGHT              )), 2) + sDiv;    // 체중

		        	sWriteNormal += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_RANK_SCR     ))) + sDiv;        // 6회평균착순점  
		        	sWriteNormal += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_SCR          ))) + " " + sDiv;  // 6회평균득점    
		        	sWriteNormal += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_WIN_RATIO        ))) + sDiv;        // 6회승률        
		        	sWriteNormal += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_HIGH_RANK_RATIO  ))) + sDiv;        // 6회연대율      
		        	sWriteNormal += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_HIGH_3_RANK_RATIO))) + sDiv;        // 6회3연대율     
		        	sWriteNormal += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_TMS_6_AVG_STRT_TM      ))) + sDiv;        // 6회평균ST      
		        	sWriteNormal +=      get8Race (        Util.trim((String    ) rowRacer.getAttribute(TRRAS_TMS_8_RANK_ORD         ))) + sDiv;        // 8경주 착순     
		        	sWriteNormal += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_AVG_RANK_SCR           ))) + sDiv;        // 평균착순점                
		        	sWriteNormal += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TRRAS_HIGH_RANK_RATIO        ))) + sDiv;        // 승률               

		        	sWriteNormal +=                        Util.trim((String    ) rowRacer.getAttribute(FL_CNT                       ))  + sDiv;        // 반기FL수  
		        	sWriteNormal += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(AVG_ACDNT_SCR                ))) + sDiv;        // 반기사고점   

/*
		        	// 모터정보
		        	sWriteNormal  +=                         Util.trim((String    ) rowRacer.getAttribute(VIEW_MOT_NO                  ))  + sDiv;       // 모터번호
		        	sWriteNormal  += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_AVG_RANK_SCR           ))) + sDiv;       // 모터평균착순점
		        	sWriteNormal  += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_HIGH_RANK_RATIO        ))) + sDiv;       // 모터연대율
		        	sWriteNormal  += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_HIGH_3_RANK_RATIO      ))) + sDiv;       // 모터3연대율

		        	// 보트 정보
		        	sWriteNormal  +=                         Util.trim((String    ) rowRacer.getAttribute(VIEW_BOAT_NO                 ))  + sDiv;       // 보트번호
		        	sWriteNormal  += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TBRAS_HIGH_RANK_RATIO        ))) + sDiv;       // 보트연대율
*/
		        	sWriteNormal  +=                         Util.trim((String    ) rowRacer.getAttribute(ETC_RACE                     ))  + sDiv;       // 금일출주경주
		        	sWriteNormal  +=      getPreRace(        Util.trim((String    ) rowRacer.getAttribute(PRE_RACE                     ))) + sDiv;       // 전일경주    
		        	
		        	/*****************************************************************
		        	 * 첨부
		        	 ****************************************************************/
		        	sWriteAtch  +=                         Util.trim((String    ) rowRacer.getAttribute(RACE_REG_NO                  )) + sDiv;        // 정번
		        	sWriteAtch  +=                         Util.trim((String    ) rowRacer.getAttribute(NM_KOR                       )) + sDiv;        // 선수명

		        	sWriteAtch  += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(MON_6_HIGH_RANK_RATIO         ))) + sDiv;       // 6회승률        
		        	sWriteAtch  +=                        Util.trim((BigDecimal) rowRacer.getAttribute(MON_6_RACE_CNT                ))  + sDiv;       // 6회승률        
		        	sWriteAtch  +=                        Util.trim((String    ) rowRacer.getAttribute(MON_6_RACE_REG_NO_1           ))  + sDiv;       // 6회승률        
		        	sWriteAtch  +=                        Util.trim((String    ) rowRacer.getAttribute(MON_6_RACE_REG_NO_2           ))  + sDiv;       // 6회승률        
		        	sWriteAtch  +=                        Util.trim((String    ) rowRacer.getAttribute(MON_6_RACE_REG_NO_3           ))  + sDiv;       // 6회승률        
		        	sWriteAtch  +=                        Util.trim((String    ) rowRacer.getAttribute(MON_6_RACE_REG_NO_4           ))  + sDiv;       // 6회승률        
		        	sWriteAtch  +=                        Util.trim((String    ) rowRacer.getAttribute(MON_6_RACE_REG_NO_5           ))  + sDiv;       // 6회승률        
		        	sWriteAtch  +=                        Util.trim((String    ) rowRacer.getAttribute(MON_6_RACE_REG_NO_6           ))  + sDiv;       // 6회승률      
		        	
		        	/*  IWORK_SFR-008 경정 선수편성 메뉴 개선  */ 
		        	if(raceRegNoCnt>=7)sWriteAtch  +=                        Util.trim((String    ) rowRacer.getAttribute(MON_6_RACE_REG_NO_7           ))  + sDiv;       // 6회승률
		        	if(raceRegNoCnt==8)sWriteAtch  +=                        Util.trim((String    ) rowRacer.getAttribute(MON_6_RACE_REG_NO_8           ))  + sDiv;       // 6회승률

		        	// 모터정보
		        	sWriteAtch  +=                         Util.trim((String    ) rowRacer.getAttribute(VIEW_MOT_NO                  ))  + sDiv;       // 모터번호
		        	sWriteAtch  += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_AVG_RANK_SCR           ))) + sDiv;       // 모터평균착순점
		        	sWriteAtch  += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_HIGH_RANK_RATIO        ))) + sDiv;       // 모터연대율
		        	sWriteAtch  += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TMRAS_HIGH_3_RANK_RATIO      ))) + sDiv;       // 모터3연대율

		        	if ( Util.trim((String) rowRacer.getAttribute(MOT_PRE_RECE1)).equals("") ) {
			        	sWriteAtch  +=                        "/"  + sDiv;       // 6회승률
		        	} else {
			        	sWriteAtch  +=                        Util.trim((String    ) rowRacer.getAttribute(MOT_PRE_RECE1                 ))  + sDiv;       // 6회승률
		        	}
		        	if ( Util.trim((String) rowRacer.getAttribute(MOT_PRE_RECE2)).equals("") ) {
			        	sWriteAtch  +=                        "/"  + sDiv;       // 6회승률
		        	} else {
			        	sWriteAtch  +=                        Util.trim((String    ) rowRacer.getAttribute(MOT_PRE_RECE2                 ))  + sDiv;       // 6회승률        
		        	}

		        	// 보트 정보
		        	sWriteAtch  +=                         Util.trim((String    ) rowRacer.getAttribute(VIEW_BOAT_NO                 ))  + sDiv;       // 보트번호
		        	sWriteAtch  += Util.getFormat("0.00", Util.trim((BigDecimal) rowRacer.getAttribute(TBRAS_AVG_RANK_SCR           ))) + sDiv;       // 모터평균착순점
		        	sWriteAtch  += Util.getFormat("00.0", Util.trim((BigDecimal) rowRacer.getAttribute(TBRAS_HIGH_RANK_RATIO        ))) + sDiv;       // 보트연대율

		        	sWriteAtch  +=                        Util.trim((String    ) rowRacer.getAttribute(RMK                           ))  + sDiv;       // 6회승률        

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
