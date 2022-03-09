/*================================================================================
 * 시스템			: 공정 관리 
 * 소스파일 이름	: snis.boa.system.e01010120.activity.SaveSdlFile.java
 * 파일설명		: SDL 파일업로드
 * 작성자			: 유동훈
 * 버전			: 1.0.0
 * 생성일자		: 2008-04-10
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.system.e01010120.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosRecord;


import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
import org.motorboat.ToteRead;

/**
* SDL파일정보 업로드
* @auther 한영택 
* @version 1.0
*/
public class SaveSdlFile extends SnisActivity
{    
    public SaveSdlFile()
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
        saveState(ctx);
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 

    	System.out.println("[STND_YEAR  : " + ctx.get("STND_YEAR"));
    	System.out.println("[MBR_CD     : " + ctx.get("MBR_CD"));
    	System.out.println("[TMS        : " + ctx.get("TMS"));
    	System.out.println("[DAY_ORD    : " + ctx.get("DAY_ORD"));
    	System.out.println("[RACE_NO    : " + ctx.get("RACE_NO"));
    	System.out.println("[SDL_FILE   : " + ctx.get("SDL_FILE"));
    	
    	System.out.println("[RACE_ALL   : " + ctx.get("RACE_ALL"));
    	System.out.println("[DT         : " + ctx.get("DT"));
    	System.out.println("[FN         : " + ctx.get("FN"));
    	System.out.println("[PB         : " + ctx.get("PB"));
    	System.out.println("[PT         : " + ctx.get("PT"));
    	System.out.println("[RS         : " + ctx.get("RS"));
    	System.out.println("[TM         : " + ctx.get("TM"));
    	System.out.println("[TR         : " + ctx.get("TR"));

    	
    	boolean bChkAll = false;
		if ( ctx.get("RACE_ALL") != null && ((String) ctx.get("RACE_ALL")).equals("1") ) {
			bChkAll = true;
		}
    	
    	boolean[] abCheck = new boolean[7];
    	for ( int i = 0; i < abCheck.length; i++ ) {
    		abCheck[i] = false;
    	}
    	
    	String[] asCheck = new String[7];
    	int j = 0;
    	asCheck[j++] = "DT";
    	asCheck[j++] = "FN";
    	asCheck[j++] = "PB";
    	asCheck[j++] = "PT";
    	asCheck[j++] = "RS";
    	asCheck[j++] = "TM";
    	asCheck[j++] = "TR";

    	for( int i = 0; i < asCheck.length; i++ ) {
    		if ( ctx.get(asCheck[i]) != null && ((String) ctx.get(asCheck[i])).equals("1") ) {
    			abCheck[i] = true;
    		}
    	}
    	
    	ToteRead toteRead = new ToteRead();

//    	toteRead.applyHomeDirectory("C:\\SNIS\\JEUS5.0\\webhome\\yu_container1\\boa\\WEB-INF");
    	toteRead.applyHomeDirectory("/WAS/webApp/boa/WEB-INF");
    	toteRead.applyFileDataWithOption((String) ctx.get("MBR_CD")
    			                       , (String) ctx.get("STND_YEAR")
		    			               , Integer.parseInt((String) ctx.get("TMS"))
		    			               , Integer.parseInt((String) ctx.get("DAY_ORD"))
		    			               , (String) ctx.get("SDL_FILE")
		    			               , bChkAll
		    			               , Integer.parseInt((String) ctx.get("RACE_NO"))
		    			               , abCheck[0]
		    			               , abCheck[1]
		    			               , abCheck[2]
		    			               , abCheck[3]
		    			               , abCheck[4]
		    			               , abCheck[5]
		    			               , abCheck[6]
    	);
    	
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 승식별 배당률 등록 정보Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveDivRate(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateDivRate(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertDivRate(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> 승식별 배당률 등록 정보  Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateDivRate(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ODDS"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("MEET_CD"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("POOL_CD"));
        param.setValueParamter(i++, record.getAttribute("RUNNER_1ST"));
        param.setValueParamter(i++, record.getAttribute("RUNNER_2ND"));
        param.setValueParamter(i++, record.getAttribute("RUNNER_3RD"));
        
        return this.getDao("boadao").update("tbes_sdl_pb_ue001", param);
    }

    /**
     * <p>승식별 배당률 정보 입력</p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insertDivRate(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MEET_CD")); 
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("POOL_CD"));
        param.setValueParamter(i++, record.getAttribute("RUNNER_1ST"));
        param.setValueParamter(i++, record.getAttribute("RUNNER_2ND"));
        param.setValueParamter(i++, record.getAttribute("RUNNER_3RD"));
        param.setValueParamter(i++, record.getAttribute("ODDS"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").update("tbes_sdl_pb_ie001", param);
    }

}
