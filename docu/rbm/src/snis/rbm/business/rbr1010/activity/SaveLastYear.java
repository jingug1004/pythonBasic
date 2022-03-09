/*================================================================================
 * 시스템			: 지점 관리
 * 소스파일 이름	: snis.rbm.business.rbr1010.activity.SaveLastYear.java
 * 파일설명		: 전년도의  지점 사항을 현재 년도로 저장
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-21
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbr1010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveLastYear extends SnisActivity {
	
	public SaveLastYear(){}

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
    	int nSaveCount = 0;
    	int nSaveChk   = 0;
    	
    	String sFlag = "N";	//메세지를 사용자에게 띄울지 말지를 정하는 Flag
    	String sMsg  = "";

        String sBrncCd   = (String)ctx.get("BRNC_CD");
        String sLastYear = (String)ctx.get("LAST_YEAR");
        String sStndYear = (String)ctx.get("STND_YEAR");
           
        if ( sBrncCd != null && sLastYear != null && sStndYear != null) {
        	
        	//일반사항 추가
        	nSaveChk = insertCommDesc(sBrncCd, sLastYear, sStndYear);	
        	if( nSaveChk == 0) {
        		sMsg  = "[ 일반사항 ]";
        		sFlag = "Y";
        	}
        	nSaveCount += nSaveChk;
        	
        	//층관리 추가
        	nSaveChk = insertFloorMana(sBrncCd, sLastYear, sStndYear);	
        	if( nSaveChk == 0) {
        		sMsg += "[ 층관리 ]";
        		sFlag = "Y";
        	}
        	nSaveCount += nSaveChk;
        	
        	//용역관리 추가
        	nSaveChk = insertServMana(sBrncCd, sLastYear, sStndYear);	
        	if( nSaveChk == 0) {
        		sMsg += "[ 용역관리 ]";
        		sFlag = "Y";
        	}
        	nSaveCount += nSaveChk;
        	
        	//장비및설비 추가 
        	nSaveChk = insertEquipFaci(sBrncCd, sLastYear, sStndYear);	
        	if( nSaveChk == 0) {
        		sMsg += "[ 장비및설비관리 ]";
        		sFlag = "Y";
        	}
        	nSaveCount += nSaveChk;
        	
        	//시설현황 추가
        	nSaveChk = insertFacStat(sBrncCd, sLastYear, sStndYear);
        	if( nSaveChk == 0)	{
        		sMsg += "[ 시설현황 ]";
        		sFlag = "Y";
        	}
        	nSaveCount += nSaveChk;
        	
        	if( sFlag.equals("Y") ) {
        	    Util.setSvcMsg(ctx, sMsg + "은 전년도 자료가 없기 때문에 추가되지 않았습니다.");
        	}
        }
  
        Util.setSaveCount  (ctx, nSaveCount  );
    }
    
    /**
     * <p> 전년도의 일반사항을 현재 년도로 생성 </p>
     * @param   sBrncCd		지점코드
     * 			sLastYear	작년년도
     * 			sStndYear	현재년도
     * @return  dmlcount	int		insert 개수
     * @throws  none
     */
    protected int insertCommDesc(String sBrncCd, String sLastYear, String sStndYear) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        	        		        
        param.setValueParamter(i++, SESSION_USER_ID);  	// 사용자 ID
        param.setValueParamter(i++, sBrncCd);         	// 지점코드
        param.setValueParamter(i++, sLastYear);         // 작년년도
        param.setValueParamter(i++, sStndYear);         // 현재년도
        
        int dmlcount = this.getDao("rbmdao").update("rbr1010_i01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 전년도의 층관리를 현재 년도로 생성 </p>
     * @param   sBrncCd		지점코드
     * 			sLastYear	작년년도
     * 			sStndYear	현재년도
     * @return  dmlcount	int		insert 개수
     * @throws  none
     */
    protected int insertFloorMana(String sBrncCd, String sLastYear, String sStndYear) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        	        		        
        param.setValueParamter(i++, SESSION_USER_ID);  	// 사용자 ID
        param.setValueParamter(i++, sBrncCd);         	// 지점코드
        param.setValueParamter(i++, sLastYear);         // 작년년도
        param.setValueParamter(i++, sStndYear);         // 현재년도
        
        int dmlcount = this.getDao("rbmdao").update("rbr1010_i02", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 전년도의 용역관리를 현재 년도로 생성 </p>
     * @param   sBrncCd		지점코드
     * 			sLastYear	작년년도
     * 			sStndYear	현재년도
     * @return  dmlcount	int		insert 개수
     * @throws  none
     */
    protected int insertServMana(String sBrncCd, String sLastYear, String sStndYear) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        	        		        
        param.setValueParamter(i++, SESSION_USER_ID);  	// 사용자 ID
        param.setValueParamter(i++, sBrncCd);         	// 지점코드
        param.setValueParamter(i++, sLastYear);         // 작년년도
        param.setValueParamter(i++, sStndYear);         // 현재년도
        
        int dmlcount = this.getDao("rbmdao").update("rbr1010_i03", param);
        
        return dmlcount;
    }
    
    
    /**
     * <p> 전년도의 장비및설비를 현재 년도로 생성 </p>
     * @param   sBrncCd		지점코드
     * 			sLastYear	작년년도
     * 			sStndYear	현재년도
     * @return  dmlcount	int		insert 개수
     * @throws  none
     */
    protected int insertEquipFaci(String sBrncCd, String sLastYear, String sStndYear) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        	        		        
        param.setValueParamter(i++, SESSION_USER_ID);  	// 사용자 ID
        param.setValueParamter(i++, sBrncCd);         	// 지점코드
        param.setValueParamter(i++, sLastYear);         // 작년년도
        param.setValueParamter(i++, sStndYear);         // 현재년도
        
        int dmlcount = this.getDao("rbmdao").update("rbr1010_i04", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 전년도의 시설현황을 현재 년도로 생성 </p>
     * @param   sBrncCd		지점코드
     * 			sLastYear	작년년도
     * 			sStndYear	현재년도
     * @return  dmlcount	int		insert 개수
     * @throws  none
     */
    protected int insertFacStat(String sBrncCd, String sLastYear, String sStndYear) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        	        		        
        param.setValueParamter(i++, SESSION_USER_ID);  	// 사용자 ID
        param.setValueParamter(i++, sBrncCd);         	// 지점코드
        param.setValueParamter(i++, sLastYear);         // 작년년도
        param.setValueParamter(i++, sStndYear);         // 현재년도
        
        int dmlcount = this.getDao("rbmdao").update("rbr1010_i05", param);
        
        return dmlcount;
    }
}
