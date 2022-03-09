/*================================================================================
 * 시스템			: 메뉴 관리
 * 소스파일 이름	: snis.rbm.system.rsy0020.activity.SaveMenu.java
 * 파일설명		: 메뉴 관리
 * 작성자			: 이영상
 * 버전			: 1.0.0
 * 생성일자		: 2011-07-29
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.system.rsy2010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.*;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 게시물을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 이영상
* @version 1.0
*/
public class SaveMenu extends SnisActivity
{    
    public SaveMenu()
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

        PosDataset ds;
        int        nSize        = 0;
        String     sDsName = "";
        	        
        sDsName = "dsMenuGrpList";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	        }
        }
	        
    	String strErr = saveState(ctx);
    	if( strErr.equals("F") ){    	
    
    		return PosBizControlConstants.FAILURE;
    	}else{
    		return PosBizControlConstants.SUCCESS;
    	
    	}

    }

    /**
    * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected String saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        sDsName = "dsMenuGrpList";
        
        String sBaseSrchYn;
        String sBaseInstYn;
        String sPersonalDataYn; //개인정보 
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = update(record)) == 0 ) {
		        		nTempCnt = insert(record);
		        	}
		        	sBaseSrchYn = (String)record.getAttribute("BASE_SRCH_YN");
		        	if(sBaseSrchYn == null) sBaseSrchYn="";
		        	
		        	sBaseInstYn = (String)record.getAttribute("BASE_INST_YN");
		        	if(sBaseInstYn == null) sBaseInstYn="";
		        	
		        	sPersonalDataYn = (String)record.getAttribute("PERSONAL_DATA_MN");
		        	if(sPersonalDataYn == null) sPersonalDataYn=""; //개인정보
		        	
		        	if(nTempCnt>0){
		        		
		        		//default 조회 
		        	    if(sBaseSrchYn.equals("Y")){
		        	    	updateMenuBaseSrchY(record);
		        	    }else if(sBaseSrchYn.equals("N")){
		        	    	updateMenuBaseSrchN(record);  // 상위 메뉴 BaseSrch N 으로 설정
		        	    	
		        	    	updateMenuBaseSrchN(record);  // 상위 의 상위메뉴 BaseSrch N 으로 설정
		        	    	
		        	    	updateChildMenuBaseSrchN(record);
		        	    }
		        	
		        	    
		        	    //default 저장
		        	    if(sBaseInstYn.equals("Y")){
		        	    	updateMenuBaseInstY(record);
		        	    }else if(sBaseInstYn.equals("N")){
		        	    	updateMenuBaseInstN(record);  // 상위 메뉴 BaseInst N 으로 설정
		        	    	
		        	    	updateMenuBaseInstN(record);  // 상위 의 상위메뉴 BaseInst N 으로 설정
		        	    	
		        	    	updateChildMenuBaseInstN(record);
		        	    }
		        	}
		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	if(getChildMenuCnt(record) >0){
	            		
		        		try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsg(ctx, "하위 메뉴가 존재합니다. !");
		            		return "F";
		            		
		            	}
	            	}else{
	            		
	            		
	            		nDeleteCount = nDeleteCount + delete(record);	
	            	}
		        	
	            	            	
	            }		        
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
        
        return "";
    }
    


    /**
     * <p> 메뉴 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		insert 레코드 개수
     * @throws  none
     */
    protected int insert(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MN_ID"      ));
        param.setValueParamter(i++, record.getAttribute("MN_NM"      ));
        param.setValueParamter(i++, record.getAttribute("ORD_NO"      ));
        param.setValueParamter(i++, record.getAttribute("UP_MN_ID"      ));
        param.setValueParamter(i++, record.getAttribute("SCRN_ID"      ));
        param.setValueParamter(i++, record.getAttribute("URL"      ));
        param.setValueParamter(i++, record.getAttribute("MN_LEVEL"      ));
        param.setValueParamter(i++, record.getAttribute("RMK"      ));
        param.setValueParamter(i++, record.getAttribute("BASE_SRCH_YN"      ));
        param.setValueParamter(i++, record.getAttribute("BASE_INST_YN"      ));
        param.setValueParamter(i++, record.getAttribute("PERSONAL_DATA_MN"      ));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("rbmdao").update("rsy2010_i01", param);
        
        return dmlcount;
    }
    
    
    
    /**
     * <p> 메뉴 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int update(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MN_NM"      ));
        param.setValueParamter(i++, record.getAttribute("ORD_NO"      ));
        param.setValueParamter(i++, record.getAttribute("SCRN_ID"      ));
        param.setValueParamter(i++, record.getAttribute("URL"      ));
        param.setValueParamter(i++, record.getAttribute("RMK"      ));
        param.setValueParamter(i++, record.getAttribute("BASE_SRCH_YN"      ));
        param.setValueParamter(i++, record.getAttribute("BASE_INST_YN"      ));
        param.setValueParamter(i++, record.getAttribute("PERSONAL_DATA_MN"      ));
        param.setValueParamter(i++, record.getAttribute("MN_LEVEL"      ));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("MN_ID" ));

        int dmlcount = this.getDao("rbmdao").update("rsy2010_u01", param);
        return dmlcount;
    }

    /**
     * <p> BaseSrch 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateMenuBaseSrchY(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MN_ID"      ));
        param.setValueParamter(i++, record.getAttribute("MN_ID"      ));

        i = 0;

        int dmlcount = this.getDao("rbmdao").update("rsy2010_u02", param);
        return dmlcount;
    }

    /**
     * <p> BaseSrch 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateMenuBaseSrchN(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MN_ID"      ));     
        
        i = 0;


        int dmlcount = this.getDao("rbmdao").update("rsy2010_u03", param);
        return dmlcount;
    }
    
    
    /**
     * <p> BaseSrch 하위메뉴  N 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateChildMenuBaseSrchN(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MN_ID"      ));
      
        int dmlcount = this.getDao("rbmdao").update("rsy2010_u04", param);
        return dmlcount;
    }
    
    
    /**
     * <p> BaseInst 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateMenuBaseInstY(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MN_ID"      ));
        param.setValueParamter(i++, record.getAttribute("MN_ID"      ));

        i = 0;

        int dmlcount = this.getDao("rbmdao").update("rsy2010_u05", param);
        return dmlcount;
    }

    /**
     * <p> BaseInst 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateMenuBaseInstN(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MN_ID"      ));     
        
        i = 0;


        int dmlcount = this.getDao("rbmdao").update("rsy2010_u06", param);
        return dmlcount;
    }
    
    
    /**
     * <p> BaseInst 하위메뉴  N 수정 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updateChildMenuBaseInstN(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MN_ID"      ));
      
        int dmlcount = this.getDao("rbmdao").update("rsy2010_u07", param);
        return dmlcount;
    }
    
    
    /**
     * <p> 메뉴 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int delete( PosRecord record) 
    {
        PosParameter param 	= new PosParameter();
        PosRowSet    prs 	= null; 
        int i 				= 0;
        int dmlcount		= 0;
                
        param.setWhereClauseParameter(i++, record.getAttribute("MN_ID" ));

        
        dmlcount += this.getDao("rbmdao").update("rsy2010_d03", param);
        dmlcount += this.getDao("rbmdao").update("rsy2010_d02", param); 	// 메뉴삭제시 권한 이력도 전부 삭제
        dmlcount += this.getDao("rbmdao").update("rsy2010_d01", param);  
        
        return dmlcount;
    }
    
    /**
     * <p> 하위 메뉴 Count 조회 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int getChildMenuCnt( PosRecord record) 
    {
        PosParameter param 	= new PosParameter();
        PosRowSet    prs 	= null; 
        int i 				= 0;
        int dmlcount		= 0;
                
        param.setWhereClauseParameter(i++, record.getAttribute("MN_ID" ));
        
        prs = this.getDao("rbmdao").find("rsy2010_s01", param);
        
        dmlcount = prs.count();
    
        
        return dmlcount;
    }
}