/*================================================================================
 * 시스템			: 회선   관리
 * 소스파일 이름	: snis.rbm.business.rsy4010.activity.SaveLineMana.java
 * 파일설명		: 회선  관리
 * 작성자			: 김은정
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-09
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/

package snis.rbm.system.rsy4030.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveLineMana extends SnisActivity {
	public SaveLineMana(){
		
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
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        sDsName = "dsLineMana";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	nTempCnt = saveLineMana(record);
		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteLineMana(record);	            	
	            }		        
	        }	        
        }

        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> 회선  입력/수정  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveLineMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
          		
        param.setValueParamter(i++, record.getAttribute("SEQ"));	    	//회선번호        		
        param.setValueParamter(i++, record.getAttribute("LINE_NO"));	    //회선번호
        param.setValueParamter(i++, record.getAttribute("SVR_NO"));	    	//서비스번호
        param.setValueParamter(i++, record.getAttribute("LINE_COMP"));	    //회선제공업체코드
        param.setValueParamter(i++, record.getAttribute("PROD_NM"));	    //상품명

        param.setValueParamter(i++, record.getAttribute("INSTALL_LOC"));    //설치위치
        param.setValueParamter(i++, record.getAttribute("MANAGER"));	    //관리자
        param.setValueParamter(i++, record.getAttribute("IP_INFO"));	    //IP정보
        param.setValueParamter(i++, record.getAttribute("JOIN_DT"));	    //가입일자
        param.setValueParamter(i++, record.getAttribute("AMT"));	    	//요금

        param.setValueParamter(i++, record.getAttribute("PAY_DEPT"));	    //납부부서
        param.setValueParamter(i++, record.getAttribute("USE"));	    	//용도
        param.setValueParamter(i++, record.getAttribute("MEMO"));	    	//메모
        param.setValueParamter(i++, record.getAttribute("COM_REG_NO"));		//사업자등록번호

        param.setValueParamter(i++, SESSION_USER_ID);			    		//작성자
        param.setValueParamter(i++, SESSION_USER_ID);			    		//수정자
  
        
        int dmlcount = this.getDao("rbmdao").update("rsy4030_u01", param);
        
        return dmlcount;
    }
    
    
    
   
    
    
    /**
     * <p> 회선   삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteLineMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("SEQ"      ) );
        
        int dmlcount = this.getDao("rbmdao").update("rsy4030_d01", param);

        return dmlcount;
    }

}
