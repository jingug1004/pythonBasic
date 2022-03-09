/*================================================================================
 * 시스템			: 연혁  관리
 * 소스파일 이름	: snis.rbm.business.rbr1012.activity.SaveHistMana.java
 * 파일설명		: 연혁 저장
 * 작성자			: 김호철
 * 버전			: 1.0.0
 * 생성일자		: 2011-09-17
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rbr1012.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveHistMana extends SnisActivity {
	
	public SaveHistMana(){}

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
    	int nSaveCount     = 0; 
    	int nDeleteCount   = 0; 
    	String sDsName     = "";
    	//String sAttFileKey = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
        
        sDsName = "dsHistMana";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	/*
		        	if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT){
		        		sAttFileKey = getFileManaMaxKey();
		        		record.setAttribute("ATT_FILE_KEY", sAttFileKey);		        		
		        	}*/		        	
		        	
		        	nTempCnt   = saveHistMana(record);
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	//첨부파일 추가시
	            	/*
	            	int deleteValid = getAttFileKey(record);
	            	
	            	if( deleteValid > 0 ) {
	            		
	                    try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		
		            		this.rollbackTransaction("tx_rbm");
		            		
		            		//Util.setSvcMsgCode(ctx, "SNIS_RBM_B001");
		            		Util.setSvcMsg(ctx, "체크하신 항목 중 [ " + (i+1) + " ]번째 줄은 [ 첨부파일 ]이 존재하므로 삭제하실 수 없습니다.");
		            		
		            		return;
		            	} 

	            	}*/
		        	nDeleteCount = nDeleteCount + deleteHistMana(record);	            	
	            }		        
	        }	        
        }
        
        // 첨부파일 추가시
        /*
        sDsName = "dsUploadFile";
        
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
	        			        		
	        		//등록시 첨부파일 키 생성 
	        		if(sAttFileKey != null && !sAttFileKey.equals("")) {
	        			record.setAttribute("ATT_FILE_KEY", sAttFileKey);
	        		}
	        		
		        	nTempCnt = insertFileMana(record);
		        	nSaveCount = nSaveCount + nTempCnt;
			        continue;
	            }
  
				if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
				{
					nDeleteCount = nDeleteCount + deleteFileMana(record);	            	
				}
	         } //for
        } //if*/
        
        Util.setSaveCount  (ctx, nSaveCount);
        Util.setDeleteCount(ctx, nDeleteCount);
    }

    /**
     * <p> 연혁 입력/수정 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int saveHistMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//지점코드
        param.setValueParamter(i++, record.getAttribute("YM"));				//년월
        param.setValueParamter(i++, record.getAttribute("SEQ"));			//순번
        param.setValueParamter(i++, record.getAttribute("CNTNT"));			//내용	
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));	//첨부파일키
		
        param.setValueParamter(i++, record.getAttribute("RMK"));			//비고
        param.setValueParamter(i++, SESSION_USER_ID);						//사용자ID(등록자)
        param.setValueParamter(i++, SESSION_USER_ID);						//사용자ID(수정자)

        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//지점코드
        param.setValueParamter(i++, record.getAttribute("YM"));				//년월

        int dmlcount = this.getDao("rbmdao").update("rbr1012_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 연혁 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteHistMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//지점코드
        param.setValueParamter(i++, record.getAttribute("YM"));				//년월
        param.setValueParamter(i++, record.getAttribute("SEQ"));			//순번
        
        int dmlcount = this.getDao("rbmdao").update("rbr1012_d01", param);

        return dmlcount;
    }
    
    /**
     * <p> 첨부파일  KEY 조회  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected String getFileManaMaxKey() 
    {
        String rtnKey = "";
        PosRowSet keyRecord = this.getDao("rbmdao").find("rbr1012_s02");        
        	
        PosRow pr[] = keyRecord.getAllRow();
           
        rtnKey = String.valueOf(pr[0].getAttribute("ATT_FILE_KEY"));
   
        return rtnKey;
    }
    
    /**
     * <p> 첨부파일  입력(사용X) </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int insertFileMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));
        param.setValueParamter(i++, record.getAttribute("TBL_NM"));
        param.setValueParamter(i++, record.getAttribute("FILE_NM"));
        param.setValueParamter(i++, record.getAttribute("FILE_PATH"));

        param.setValueParamter(i++, record.getAttribute("REAL_FILE_NM"));
        param.setValueParamter(i++, SESSION_USER_ID);  
                
        int dmlcount = this.getDao("rbmdao").update("rbr4020_i01", param);

        return dmlcount;
    }
    
    /**
     * <p> 첨부파일  삭제(사용X) </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int deleteFileMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ATT_FILE_KEY"));
        param.setValueParamter(i++, record.getAttribute("SEQ"));

        
        int dmlcount = this.getDao("rbmdao").update("rbr4020_d02", param);

        return dmlcount;
    }
    
    /**
     * <p> 연혁에 대한 첨부파일 조회(사용X)  </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  rtnKey	int			사건에 걸려있는 사건이력 개수
     * @throws  none
     */
    protected int getAttFileKey(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String rtnKey = "";
        
        param.setWhereClauseParameter(i++, record.getAttribute("ATT_FILE_KEY" ));	//첨부파일키
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rbr4020_s05", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.parseInt(rtnKey);
    }
}