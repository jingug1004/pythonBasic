/*================================================================================
 * 시스템			: 체력측정
 * 소스파일 이름	: snis.can.system.d02000002.activity.SaveCDetailEduSchd.java
 * 파일설명		: 코드 관리
 * 작성자			: 최문규
 * 버전			: 1.0.0
 * 생성일자		: 2007-01-03
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can_boa.boatstd.d06000016.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;

/**
* 이 클래스는 클라이언트로부터의 저장 데이타셋을 받아 해당 Query의 조건절에 맞는 파라미터를
* 매핑하여 게시물을 저장(입력/수정)및 삭제하는 클래스이다.
* @auther 최문규
* @version 1.0
*/


public class SaveGetStrength extends SnisActivity 
{
	public SaveGetStrength() { }
	
	/**
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String	sucess 문자열
     * @throws  none
     */    
	
    
    public String runActivity(PosContext ctx)
    {  
    	//사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	
        PosDataset ds1;
        PosDataset ds2;
        PosDataset ds3;
        PosDataset ds3_1;
        PosDataset ds4;

        int nSize1 = 0;
        int nSize2 = 0;
        int nSize3 = 0;
        int nSize3_1 = 0;
        int nSize4 = 0;
        
        String sDsName = "";
 
        //체력측정평가항목코드
        sDsName = "dsStrtMesurCd1";
        if ( ctx.get(sDsName) != null ) {
	        ds1    = (PosDataset)ctx.get(sDsName);
	        nSize1 = ds1.getRecordCount();
	        for ( int i = 0; i < nSize1; i++ ) 
	        {
	            PosRecord record = ds1.getRecord(i);
	          }
        }
        
        //체력측정기준
        sDsName = "dsStrtMesurBas2";
        if ( ctx.get(sDsName) != null ) {
	        ds2 = (PosDataset)ctx.get(sDsName);
	        nSize2 = ds2.getRecordCount();
	        for ( int i = 0; i < nSize2; i++ ) 
	        {
	            PosRecord record = ds2.getRecord(i);
	         }
        }   
        
        //일자별체력측정
        sDsName = "dsStrtMesurDt3";
        if ( ctx.get(sDsName) != null ) {
	        ds3 = (PosDataset)ctx.get(sDsName);
	        nSize3 = ds3.getRecordCount();
	        for ( int i = 0; i < nSize3; i++ ) 
	        {
	            PosRecord record = ds3.getRecord(i);
	        }
        }  
        
        sDsName = "dsStrtMesurDt3_1";
        if ( ctx.get(sDsName) != null ) {
	        ds3_1 = (PosDataset)ctx.get(sDsName);
	        nSize3_1 = ds3_1.getRecordCount();
	        for ( int i = 0; i < nSize3_1; i++ ) 
	        {
	            PosRecord record = ds3_1.getRecord(i);
	        }
        }  
        
        //차수별체력측정
        sDsName = "dsStrtMesurRound5";
        if ( ctx.get(sDsName) != null ) {
	        ds4 = (PosDataset)ctx.get(sDsName);
	        nSize4 = ds4.getRecordCount();
	        for ( int i = 0; i < nSize4; i++ ) 
	        {
	           PosRecord record = ds4.getRecord(i);
	        }
        }    
        
		if(nSize1 > 0){
			saveStateStrt_Mesur_Cd(ctx); 
		}else if(nSize2 > 0){
			saveStateStrt_Mesur_Bas(ctx); 
		}else if( (nSize3 > 0) || (nSize3_1 > 0)){
		    saveStateDt_Strt_Mesur(ctx);         	
		}else if(nSize4 > 0){
			saveStateRound_Strt_Mesur(ctx); 		
		}        
        
        return PosBizControlConstants.SUCCESS;
    }
    
    /**
     * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소 : 체력측정평가항목코드
     * @return  none
     * @throws  none
     */
     protected void saveStateStrt_Mesur_Cd(PosContext ctx) 
     {
     	int nSaveCount   = 0; 
     	int nDeleteCount = 0; 

     	PosDataset ds;
     	
        int nSize    	= 0;
        int nTempCnt    = 0;
            
        ds   = (PosDataset) ctx.get("dsStrtMesurCd1");
        nSize = ds.getRecordCount();
             
        logger.logInfo("##########################sSize:" + nSize);
        
        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds.getRecord(i);
             /*
              * public static final int NORMAL = 1;
  				public static final int INSERT = 2;
  				public static final int UPDATE = 4;
  				public static final int DELETE = 8;
  				public static final int UNKNOWN = 256;
              */
            
             if (  record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT 
            		 || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE)
             {
            	 if ( (nTempCnt = updateStrt_Mesur_Cd(ctx, record)) == 0 ) {
                   	nTempCnt = insertStrt_Mesur_Cd(ctx, record);
                 }    	 
            	 nSaveCount = nSaveCount + nTempCnt;
             }
             
             if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
             {
                 // delete
             	nDeleteCount = nDeleteCount + deleteStrt_Mesur_Cd(ctx, record);
             }

         }
         
                  
         Util.setSaveCount  (ctx, nSaveCount     );
         Util.setDeleteCount(ctx, nDeleteCount   );
     }
     
     
     /**
      * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
      * @param   ctx		PosContext	저장소 : 체력측정기준
      * @return  none
      * @throws  none
      */
      protected void saveStateStrt_Mesur_Bas(PosContext ctx) 
      {
      	int nSaveCount   = 0; 
      	int nDeleteCount = 0; 

      	PosDataset ds;
      	
         int nSize    	= 0;
         int nTempCnt    = 0;
         
         ds = (PosDataset) ctx.get("dsStrtMesurBas2");
         nSize = ds.getRecordCount();
        
         for ( int i = 0; i < nSize; i++ ) 
         {
              PosRecord record = ds.getRecord(i);
              
              if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
                || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
              {
             	 if ( (nTempCnt = updateStrt_Mesur_Bas(record)) == 0 ) {
                    	nTempCnt = insertStrt_Mesur_Bas(record);
                  }    	 
             	 nSaveCount = nSaveCount + nTempCnt;
              }
              
              if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
              {
                  // delete
              	nDeleteCount = nDeleteCount + deleteStrt_Mesur_Bas(record);
              }
          }
                   
          Util.setSaveCount  (ctx, nSaveCount     );
          Util.setDeleteCount(ctx, nDeleteCount   );
     }     
      
      
  	/**
  	* <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
  	* @param   ctx		PosContext	저장소 : 일자별체력측정
  	* @return  none
  	* @throws  none
  	*/
      
   
  	protected void saveStateDt_Strt_Mesur(PosContext ctx) 
  	{
  		int nSaveCount   = 0; 
  		int nDeleteCount = 0; 

  		PosDataset ds;

  		int nSize 		= 0;
  		int nTempCnt    = 0;
  		int nMinItemCd = 101;
  		int nMaxItemCd = 104;
  		
  		ds = (PosDataset) ctx.get("dsStrtMesurDt3");
  		nSize = ds.getRecordCount();
  		 
  		for ( int i = 0; i < nSize; i++ ) 
  		{
  			PosRecord record = ds.getRecord(i);

  			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) 
  			{
  				nDeleteCount = nDeleteCount + deleteDt_Strt_Mesur(record);
  			}
  		} 
  		
  		ds = (PosDataset) ctx.get("dsStrtMesurDt3_1");
  		nSize = ds.getRecordCount();
  		 
  		for ( int i = 0; i < nSize; i++ ) 
  		{
  			PosRecord record = ds.getRecord(i);

  			for ( int j = nMinItemCd; j <= nMaxItemCd; j++ ) {
	  			if ( (nTempCnt = updateDt_Strt_Mesur(record, j)) == 0 ) 
	  			{
	  				nTempCnt = insertDt_Strt_Mesur(record, j);
	  			}
	  			
	  			nSaveCount = nSaveCount + nTempCnt;
  			}
  		}
  		
  		Util.setSaveCount  (ctx, nSaveCount     );
  		Util.setDeleteCount(ctx, nDeleteCount   );
  	}    
  	
  	
 	/**
  	* <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
  	* @param   ctx		PosContext	저장소 : 차수별체력측정
  	* @return  none
  	* @throws  none
  	*/
  	protected void saveStateRound_Strt_Mesur(PosContext ctx) 
  	{
  		int nSaveCount   = 0; 
  		int nDeleteCount = 0; 

  		PosDataset ds;

  		int nSize 	= 0;
  		int nTempCnt    = 0;

  		ds = (PosDataset) ctx.get("dsStrtMesurRound5");
  		nSize = ds.getRecordCount();
  	
  		for ( int i = 0; i < nSize; i++ ) 
  		{
  			PosRecord record = ds.getRecord(i);

  			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
  			|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
  			{
  				if ( (nTempCnt = updateRound_Strt_Mesur(record)) == 0 ) {
  					nTempCnt = insertRound_Strt_Mesur(record);
  				}    	 
  				nSaveCount = nSaveCount + nTempCnt;
  			}

  			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
  			{
  				// delete
  				nDeleteCount = nDeleteCount + deleteRound_Strt_Mesur(record);
  			}
  		}

  		Util.setSaveCount  (ctx, nSaveCount     );
  		Util.setDeleteCount(ctx, nDeleteCount   );
  	}	
     
     /**
      * <p> 체력측정평가항목코드 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int insertStrt_Mesur_Cd(PosContext ctx, PosRecord record) 
     {
     
    	 PosParameter param = new PosParameter();       					
         int i = 0;
         String	racerPerioNo    = (String) ctx.get("racerPerioNo");
         param.setValueParamter(i++, racerPerioNo);
         param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_CD")));
         param.setValueParamter(i++, Util.trim(record.getAttribute("SEX_GBN")));
         param.setValueParamter(i++, record.getAttribute("SORT_ORD"));
         param.setValueParamter(i++, SESSION_USER_ID);

         int dmlcount = this.getDao("candao").insert("tbdn_strt_mesur_cd_in001", param);
         
         return dmlcount;
     }
     
     /**
      * <p> 체력측정평가항목코드 갱신  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int updateStrt_Mesur_Cd(PosContext ctx, PosRecord record) 
     {
     	PosParameter param = new PosParameter();       					
     	int i = 0;
     	String	racerPerioNo    = (String) ctx.get("racerPerioNo");
		param.setValueParamter(i++, record.getAttribute("SORT_ORD"));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;
		
		param.setWhereClauseParameter(i++, racerPerioNo);
		param.setWhereClauseParameter(i++, record.getAttribute("ITEM_CD"));
		param.setWhereClauseParameter(i++, record.getAttribute("SEX_GBN"));
		

		int dmlcount = this.getDao("candao").update("tbdn_strt_mesur_cd_un001", param);

		return dmlcount;
     }
     					
 
     /**
      * <p> 체력측정평가항목코드 삭제</p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		delete 레코드 개수
      * @throws  none
      */
     protected int deleteStrt_Mesur_Cd(PosContext ctx, PosRecord record) 
     {
		PosParameter param = new PosParameter();       					
		int i = 0;
		String	racerPerioNo    = (String) ctx.get("racerPerioNo");     
		param.setWhereClauseParameter(i++, racerPerioNo);
		param.setWhereClauseParameter(i++, record.getAttribute("ITEM_CD"));
		param.setWhereClauseParameter(i++, record.getAttribute("SEX_GBN"));
		         
		int dmlcount = this.getDao("candao").delete("tbdn_strt_mesur_cd_dn001", param);
		 
		return dmlcount;
     }
     
     
     /**
      * <p> 체력측정기준 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int insertStrt_Mesur_Bas(PosRecord record) 
     {
 		PosParameter param = new PosParameter();       					
		int i = 0;
		int dmlcount = 0;	
		
		String sBas100FromScr = Util.trim(String.valueOf(record.getAttribute("BAS_100FROM_SCR")));
		String sBas90FromScr = Util.trim(String.valueOf(record.getAttribute("BAS_90FROM_SCR")));
		String sBas80FromScr = Util.trim(String.valueOf(record.getAttribute("BAS_80FROM_SCR")));
		String sBas70FromScr = Util.trim(String.valueOf(record.getAttribute("BAS_70FROM_SCR")));
		String sBas60FromScr = Util.trim(String.valueOf(record.getAttribute("BAS_60FROM_SCR")));
		String sBas50FromScr = Util.trim(String.valueOf(record.getAttribute("BAS_50FROM_SCR")));
		String sBas40FromScr = Util.trim(String.valueOf(record.getAttribute("BAS_40FROM_SCR")));
		String sBas30FromScr = Util.trim(String.valueOf(record.getAttribute("BAS_30FROM_SCR")));
		String sBas20FromScr = Util.trim(String.valueOf(record.getAttribute("BAS_20FROM_SCR")));
		String sBas10FromScr = Util.trim(String.valueOf(record.getAttribute("BAS_10FROM_SCR")));

		if(!sBas100FromScr.equals("")){
        	param = new PosParameter();
			param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_CD")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SEX_GBN")));
			param.setValueParamter(i++, "100");
			param.setValueParamter(i++, record.getAttribute("BAS_100FROM_SCR"));
			param.setValueParamter(i++, record.getAttribute("BAS_100TO_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			dmlcount += this.getDao("candao").insert("tbdn_strt_mesur_bas_in001", param);
		}

		if(!sBas90FromScr.equals("")){
			i = 0;
        	param = new PosParameter();
			param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_CD")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SEX_GBN")));
			param.setValueParamter(i++, "90");
			param.setValueParamter(i++, record.getAttribute("BAS_90FROM_SCR"));
			param.setValueParamter(i++, record.getAttribute("BAS_90TO_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			dmlcount += this.getDao("candao").insert("tbdn_strt_mesur_bas_in001", param);
		}

		if(!sBas80FromScr.equals("")){
			i = 0;
        	param = new PosParameter();
			param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_CD")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SEX_GBN")));
			param.setValueParamter(i++, "80");
			param.setValueParamter(i++, record.getAttribute("BAS_80FROM_SCR"));
			param.setValueParamter(i++, record.getAttribute("BAS_80TO_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			dmlcount += this.getDao("candao").insert("tbdn_strt_mesur_bas_in001", param);
		}

		if(!sBas70FromScr.equals("")){
			i = 0;
        	param = new PosParameter();
			param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_CD")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SEX_GBN")));
			param.setValueParamter(i++, "70");
			param.setValueParamter(i++, record.getAttribute("BAS_70FROM_SCR"));
			param.setValueParamter(i++, record.getAttribute("BAS_70TO_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			dmlcount += this.getDao("candao").insert("tbdn_strt_mesur_bas_in001", param);
		}

		if(!sBas60FromScr.equals("")){
			i = 0;
        	param = new PosParameter();
			param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_CD")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SEX_GBN")));
			param.setValueParamter(i++, "60");
			param.setValueParamter(i++, record.getAttribute("BAS_60FROM_SCR"));
			param.setValueParamter(i++, record.getAttribute("BAS_60TO_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			dmlcount += this.getDao("candao").insert("tbdn_strt_mesur_bas_in001", param);
		}

		if(!sBas50FromScr.equals("")){
			i = 0;
        	param = new PosParameter();
			param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_CD")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SEX_GBN")));
			param.setValueParamter(i++, "50");
			param.setValueParamter(i++, record.getAttribute("BAS_50FROM_SCR"));
			param.setValueParamter(i++, record.getAttribute("BAS_50TO_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			dmlcount += this.getDao("candao").insert("tbdn_strt_mesur_bas_in001", param);
		}

		if(!sBas40FromScr.equals("")){
			i = 0;
        	param = new PosParameter();
			param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_CD")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SEX_GBN")));
			param.setValueParamter(i++, "40");
			param.setValueParamter(i++, record.getAttribute("BAS_40FROM_SCR"));
			param.setValueParamter(i++, record.getAttribute("BAS_40TO_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			dmlcount += this.getDao("candao").insert("tbdn_strt_mesur_bas_in001", param);
		}

		if(!sBas30FromScr.equals("")){
			i = 0;
        	param = new PosParameter();
			param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_CD")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SEX_GBN")));
			param.setValueParamter(i++, "30");
			param.setValueParamter(i++, record.getAttribute("BAS_30FROM_SCR"));
			param.setValueParamter(i++, record.getAttribute("BAS_30TO_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			dmlcount += this.getDao("candao").insert("tbdn_strt_mesur_bas_in001", param);
		}

		if(!sBas20FromScr.equals("")){
			i = 0;
        	param = new PosParameter();
			param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_CD")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SEX_GBN")));
			param.setValueParamter(i++, "20");
			param.setValueParamter(i++, record.getAttribute("BAS_20FROM_SCR"));
			param.setValueParamter(i++, record.getAttribute("BAS_20TO_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			dmlcount += this.getDao("candao").insert("tbdn_strt_mesur_bas_in001", param);
		}

		if(!sBas10FromScr.equals("")){
			i = 0;
        	param = new PosParameter();
			param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_CD")));
			param.setValueParamter(i++, Util.trim(record.getAttribute("SEX_GBN")));
			param.setValueParamter(i++, "10");
			param.setValueParamter(i++, record.getAttribute("BAS_10FROM_SCR"));
			param.setValueParamter(i++, record.getAttribute("BAS_10TO_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			dmlcount += this.getDao("candao").insert("tbdn_strt_mesur_bas_in001", param);
		}

		logger.logInfo("insertStrt_Mesur_Bas end ============================");
		return dmlcount;
     }
     
     /**
      * <p> 체력측정기준 갱신  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int updateStrt_Mesur_Bas(PosRecord record) 
     {
		logger.logInfo("updateStrt_Mesur_Bas start============================");
     	PosParameter param = new PosParameter();       					
     	int i = 0;
     	int dmlcount = 0;
  
		String sBas100FromScr = Util.trim(String.valueOf(record.getAttribute("BAS_100FROM_SCR")));
		String sBas90FromScr = Util.trim(String.valueOf(record.getAttribute("BAS_90FROM_SCR")));
		String sBas80FromScr = Util.trim(String.valueOf(record.getAttribute("BAS_80FROM_SCR")));
		String sBas70FromScr = Util.trim(String.valueOf(record.getAttribute("BAS_70FROM_SCR")));
		String sBas60FromScr = Util.trim(String.valueOf(record.getAttribute("BAS_60FROM_SCR")));
		String sBas50FromScr = Util.trim(String.valueOf(record.getAttribute("BAS_50FROM_SCR")));
		String sBas40FromScr = Util.trim(String.valueOf(record.getAttribute("BAS_40FROM_SCR")));
		String sBas30FromScr = Util.trim(String.valueOf(record.getAttribute("BAS_30FROM_SCR")));
		String sBas20FromScr = Util.trim(String.valueOf(record.getAttribute("BAS_20FROM_SCR")));
		String sBas10FromScr = Util.trim(String.valueOf(record.getAttribute("BAS_10FROM_SCR")));
		
		String sBas100ToScr = Util.trim(String.valueOf(record.getAttribute("BAS_100TO_SCR")));
		String sBas90ToScr = Util.trim(String.valueOf(record.getAttribute("BAS_90TO_SCR")));
		String sBas80ToScr = Util.trim(String.valueOf(record.getAttribute("BAS_80TO_SCR")));
		String sBas70ToScr = Util.trim(String.valueOf(record.getAttribute("BAS_70TO_SCR")));
		String sBas60ToScr = Util.trim(String.valueOf(record.getAttribute("BAS_60TO_SCR")));
		String sBas50ToScr = Util.trim(String.valueOf(record.getAttribute("BAS_50TO_SCR")));
		String sBas40ToScr = Util.trim(String.valueOf(record.getAttribute("BAS_40TO_SCR")));
		String sBas30ToScr = Util.trim(String.valueOf(record.getAttribute("BAS_30TO_SCR")));
		String sBas20ToScr = Util.trim(String.valueOf(record.getAttribute("BAS_20TO_SCR")));
		String sBas10ToScr = Util.trim(String.valueOf(record.getAttribute("BAS_10TO_SCR")));

		//if(!sBas100FromScr.equals("")){
        	param = new PosParameter();
			param.setValueParamter(i++, record.getAttribute("BAS_100FROM_SCR"));
			param.setValueParamter(i++, record.getAttribute("BAS_100TO_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			
			i = 0;    
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_CD")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("SEX_GBN")));
			param.setWhereClauseParameter(i++, "100");
			dmlcount += this.getDao("candao").update("tbdn_strt_mesur_bas_un001", param);
		//}

		//if(!sBas90FromScr.equals("")){
			i = 0;
        	param = new PosParameter();
			param.setValueParamter(i++, record.getAttribute("BAS_90FROM_SCR"));
			param.setValueParamter(i++, record.getAttribute("BAS_90TO_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			
			i = 0;    
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_CD")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("SEX_GBN")));
			param.setWhereClauseParameter(i++, "90");
			dmlcount += this.getDao("candao").update("tbdn_strt_mesur_bas_un001", param);
		//}

		//if(!sBas80FromScr.equals("")){
			i = 0;
        	param = new PosParameter();
			param.setValueParamter(i++, record.getAttribute("BAS_80FROM_SCR"));
			param.setValueParamter(i++, record.getAttribute("BAS_80TO_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			
			i = 0;    
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_CD")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("SEX_GBN")));
			param.setWhereClauseParameter(i++, "80");
			dmlcount += this.getDao("candao").update("tbdn_strt_mesur_bas_un001", param);
		//}

		//if(!sBas70FromScr.equals("")){
			i = 0;
        	param = new PosParameter();
			param.setValueParamter(i++, record.getAttribute("BAS_70FROM_SCR"));
			param.setValueParamter(i++, record.getAttribute("BAS_70TO_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			
			i = 0;    
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_CD")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("SEX_GBN")));
			param.setWhereClauseParameter(i++, "70");
			dmlcount += this.getDao("candao").update("tbdn_strt_mesur_bas_un001", param);
		//}

		//if(!sBas60FromScr.equals("")){
			i = 0;
        	param = new PosParameter();
			param.setValueParamter(i++, record.getAttribute("BAS_60FROM_SCR"));
			param.setValueParamter(i++, record.getAttribute("BAS_60TO_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			
			i = 0;    
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_CD")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("SEX_GBN")));
			param.setWhereClauseParameter(i++, "60");
			dmlcount += this.getDao("candao").update("tbdn_strt_mesur_bas_un001", param);
		//}

		//if(!sBas50FromScr.equals("")){
			i = 0;
        	param = new PosParameter();
			param.setValueParamter(i++, record.getAttribute("BAS_50FROM_SCR"));
			param.setValueParamter(i++, record.getAttribute("BAS_50TO_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			
			i = 0;    
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_CD")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("SEX_GBN")));
			param.setWhereClauseParameter(i++, "50");
			dmlcount += this.getDao("candao").update("tbdn_strt_mesur_bas_un001", param);
		//}

		//if(!sBas40FromScr.equals("")){
			i = 0;
        	param = new PosParameter();
			param.setValueParamter(i++, record.getAttribute("BAS_40FROM_SCR"));
			param.setValueParamter(i++, record.getAttribute("BAS_40TO_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			
			i = 0;    
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_CD")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("SEX_GBN")));
			param.setWhereClauseParameter(i++, "40");
			dmlcount += this.getDao("candao").update("tbdn_strt_mesur_bas_un001", param);
		//}

		//if(!sBas30FromScr.equals("")){
			i = 0;
        	param = new PosParameter();
			param.setValueParamter(i++, record.getAttribute("BAS_30FROM_SCR"));
			param.setValueParamter(i++, record.getAttribute("BAS_30TO_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			
			i = 0;    
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_CD")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("SEX_GBN")));
			param.setWhereClauseParameter(i++, "30");
			dmlcount += this.getDao("candao").update("tbdn_strt_mesur_bas_un001", param);
		//}

		//if(!sBas20FromScr.equals("")){
			i = 0;
        	param = new PosParameter();
			param.setValueParamter(i++, record.getAttribute("BAS_20FROM_SCR"));
			param.setValueParamter(i++, record.getAttribute("BAS_20TO_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			
			i = 0;    
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_CD")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("SEX_GBN")));
			param.setWhereClauseParameter(i++, "20");
			dmlcount += this.getDao("candao").update("tbdn_strt_mesur_bas_un001", param);
		//}

		//if(!sBas10FromScr.equals("")){
			i = 0;
        	param = new PosParameter();
			param.setValueParamter(i++, record.getAttribute("BAS_10FROM_SCR"));
			param.setValueParamter(i++, record.getAttribute("BAS_10TO_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			
			i = 0;    
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("ITEM_CD")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("SEX_GBN")));
			param.setWhereClauseParameter(i++, "10");
			dmlcount += this.getDao("candao").update("tbdn_strt_mesur_bas_un001", param);
		//}

		return dmlcount;
     }
     
 
     /**
      * <p> 체력측정기준 삭제</p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		delete 레코드 개수
      * @throws  none
      */
     protected int deleteStrt_Mesur_Bas(PosRecord record) 
     {
	     logger.logInfo("deleteStrt_Mesur_Bas start============================");
	     PosParameter param = new PosParameter();       					
	     int i = 0;
	
	     param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
	     param.setWhereClauseParameter(i++, record.getAttribute("REPT_NO"));
	
	     int dmlcount = this.getDao("candao").delete("tbdn_strt_mesur_bas_dn001", param);
	
	     logger.logInfo("deleteStrt_Mesur_Bas end============================");
	     return dmlcount;
     }
     
     /**
      * <p> 일자별체력측정 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int insertDt_Strt_Mesur(PosRecord record, int nItem)
     {
     	logger.logInfo("insertDt_Strt_Mesur start ============================");
     	PosParameter param = new PosParameter();       					
     	int i = 0;

     	logger.logInfo("insertDt_Strt_Mesur ITEM_CD=========>"+record.getAttribute("ITEM_CD"));
     	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
     	param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
     	param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
     	param.setValueParamter(i++, nItem + "");
     	param.setValueParamter(i++, record.getAttribute("ROUND"));
     	param.setValueParamter(i++, record.getAttribute("SCR_" + nItem         ));
     	param.setValueParamter(i++, SESSION_USER_ID);

     	int dmlcount = this.getDao("candao").insert("tbdn_dt_strt_mesur_in001", param);

     	logger.logInfo("insertDt_Strt_Mesur end ============================");
     	return dmlcount;
     }
     
     /**
      * <p> 일자별체력측정 갱신  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int updateDt_Strt_Mesur(PosRecord record, int nItem) 
     {
     	logger.logInfo("updateDt_Strt_Mesur start============================");
     	PosParameter param = new PosParameter();       					
     	int i = 0;
  
        param.setValueParamter(i++, record.getAttribute("ROUND"));
        param.setValueParamter(i++, record.getAttribute("SCR_" + nItem         ));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("CAND_NO"));
		param.setWhereClauseParameter(i++, record.getAttribute("DT"));
		param.setWhereClauseParameter(i++, nItem + "");

		int dmlcount = this.getDao("candao").update("tbdn_dt_strt_mesur_un001", param);

		logger.logInfo("updateDt_Strt_Mesur end ============================");
		return dmlcount;
     }
 
     /**
      * <p> 일자별체력측정 삭제</p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		delete 레코드 개수
      * @throws  none
      */
     protected int deleteDt_Strt_Mesur(PosRecord record) 
     {
		logger.logInfo("deleteDt_Strt_Mesur start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;
		     
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("DT"));
	         
		int dmlcount = this.getDao("candao").delete("tbdn_dt_strt_mesur_dn001", param);
		 
		logger.logInfo("deleteDt_Strt_Mesur end============================");
		return dmlcount;
     }
     
     /**
      * <p> 차수별체력측정 입력 </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		insert 레코드 개수
      * @throws  none
      */
     protected int insertRound_Strt_Mesur(PosRecord record) 
     {
     	logger.logInfo("insertRound_Strt_Mesur start ============================");
     	PosParameter param = null;       					
     	int i = 0;
     	int dmlcount = 0;
     	String sItem01Rec = Util.trim(String.valueOf(record.getAttribute("ITEM01_REC")));
     	String sItem02Rec = Util.trim(String.valueOf(record.getAttribute("ITEM02_REC")));
     	String sItem03Rec = Util.trim(String.valueOf(record.getAttribute("ITEM03_REC")));
     	String sItem04Rec = Util.trim(String.valueOf(record.getAttribute("ITEM04_REC")));
     	String sItem05Rec = Util.trim(String.valueOf(record.getAttribute("ITEM05_REC")));
     	String sItem06Rec = Util.trim(String.valueOf(record.getAttribute("ITEM06_REC")));
     	String sItem07Rec = Util.trim(String.valueOf(record.getAttribute("ITEM07_REC")));
     	String sItem08Rec = Util.trim(String.valueOf(record.getAttribute("ITEM08_REC")));
     	String sItem09Rec = Util.trim(String.valueOf(record.getAttribute("ITEM09_REC")));
     	String sItem10Rec = Util.trim(String.valueOf(record.getAttribute("ITEM10_REC")));
     	String sItem11Rec = Util.trim(String.valueOf(record.getAttribute("ITEM11_REC")));

     	if(!sItem01Rec.equals("")){
     		i = 0;
     		param = new PosParameter();
     		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
     		param.setValueParamter(i++, "001");
     		param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));	         
     		param.setValueParamter(i++, record.getAttribute("ROUND"));
     		param.setValueParamter(i++, record.getAttribute("ITEM01_REC"));
     		param.setValueParamter(i++, record.getAttribute("ITEM01_SCR"));
     		param.setValueParamter(i++, SESSION_USER_ID);
     		dmlcount += this.getDao("candao").insert("tbdn_round_strt_mesur_in001", param);
     	}

     	if(!sItem02Rec.equals("")){
     		i = 0;
     		param = new PosParameter();        	 
     		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
     		param.setValueParamter(i++, "002");
     		param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));	         
     		param.setValueParamter(i++, record.getAttribute("ROUND"));
     		param.setValueParamter(i++, record.getAttribute("ITEM02_REC"));
     		param.setValueParamter(i++, record.getAttribute("ITEM02_SCR"));
     		param.setValueParamter(i++, SESSION_USER_ID);
     		dmlcount += this.getDao("candao").insert("tbdn_round_strt_mesur_in001", param);
     	}

     	if(!sItem03Rec.equals("")){
     		i = 0;
     		param = new PosParameter();        	 
     		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
     		param.setValueParamter(i++, "003");
     		param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));	         
     		param.setValueParamter(i++, record.getAttribute("ROUND"));
     		param.setValueParamter(i++, record.getAttribute("ITEM03_REC"));
     		param.setValueParamter(i++, record.getAttribute("ITEM03_SCR"));
     		param.setValueParamter(i++, SESSION_USER_ID);
     		dmlcount += this.getDao("candao").insert("tbdn_round_strt_mesur_in001", param);
     	}    

     	if(!sItem04Rec.equals("")){
     		i = 0;
     		param = new PosParameter();        	 
     		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
     		param.setValueParamter(i++, "004");
     		param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));	         
     		param.setValueParamter(i++, record.getAttribute("ROUND"));
     		param.setValueParamter(i++, record.getAttribute("ITEM04_REC"));
     		param.setValueParamter(i++, record.getAttribute("ITEM04_SCR"));
     		param.setValueParamter(i++, SESSION_USER_ID);
     		dmlcount += this.getDao("candao").insert("tbdn_round_strt_mesur_in001", param);
     	}     

     	if(!sItem05Rec.equals("")){
     		i = 0;
     		param = new PosParameter();        	 
     		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
     		param.setValueParamter(i++, "005");
     		param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));	         
     		param.setValueParamter(i++, record.getAttribute("ROUND"));
     		param.setValueParamter(i++, record.getAttribute("ITEM05_REC"));
     		param.setValueParamter(i++, record.getAttribute("ITEM05_SCR"));
     		param.setValueParamter(i++, SESSION_USER_ID);
     		dmlcount += this.getDao("candao").insert("tbdn_round_strt_mesur_in001", param);
     	}   

     	if(!sItem06Rec.equals("")){
     		i = 0;
     		param = new PosParameter();        	 
     		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
     		param.setValueParamter(i++, "006");
     		param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));	         
     		param.setValueParamter(i++, record.getAttribute("ROUND"));
     		param.setValueParamter(i++, record.getAttribute("ITEM06_REC"));
     		param.setValueParamter(i++, record.getAttribute("ITEM06_SCR"));
     		param.setValueParamter(i++, SESSION_USER_ID);
     		dmlcount += this.getDao("candao").insert("tbdn_round_strt_mesur_in001", param);
     	}

     	if(!sItem07Rec.equals("")){
     		i = 0;
     		param = new PosParameter();        	 
     		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
     		param.setValueParamter(i++, "007");
     		param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));	         
     		param.setValueParamter(i++, record.getAttribute("ROUND"));
     		param.setValueParamter(i++, record.getAttribute("ITEM07_REC"));
     		param.setValueParamter(i++, record.getAttribute("ITEM07_SCR"));
     		param.setValueParamter(i++, SESSION_USER_ID);
     		dmlcount += this.getDao("candao").insert("tbdn_round_strt_mesur_in001", param);
     	}       

     	if(!sItem08Rec.equals("")){
     		i = 0;
     		param = new PosParameter();        	 
     		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
     		param.setValueParamter(i++, "008");
     		param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));	         
     		param.setValueParamter(i++, record.getAttribute("ROUND"));
     		param.setValueParamter(i++, record.getAttribute("ITEM08_REC"));
     		param.setValueParamter(i++, record.getAttribute("ITEM08_SCR"));
     		param.setValueParamter(i++, SESSION_USER_ID);
     		dmlcount += this.getDao("candao").insert("tbdn_round_strt_mesur_in001", param);
     	}             

     	if(!sItem09Rec.equals("")){
     		i = 0;
     		param = new PosParameter();        	 
     		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
     		param.setValueParamter(i++, "009");
     		param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));	         
     		param.setValueParamter(i++, record.getAttribute("ROUND"));
     		param.setValueParamter(i++, record.getAttribute("ITEM09_REC"));
     		param.setValueParamter(i++, record.getAttribute("ITEM09_SCR"));
     		param.setValueParamter(i++, SESSION_USER_ID);
     		dmlcount += this.getDao("candao").insert("tbdn_round_strt_mesur_in001", param);
     	}             

     	if(!sItem10Rec.equals("")){
     		i = 0;
     		param = new PosParameter();        	 
     		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
     		param.setValueParamter(i++, "010");
     		param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));	         
     		param.setValueParamter(i++, record.getAttribute("ROUND"));
     		param.setValueParamter(i++, record.getAttribute("ITEM10_REC"));
     		param.setValueParamter(i++, record.getAttribute("ITEM10_SCR"));
     		param.setValueParamter(i++, SESSION_USER_ID);
     		dmlcount += this.getDao("candao").insert("tbdn_round_strt_mesur_in001", param);
     	}             

     	if(!sItem11Rec.equals("")){
     		i = 0;
     		param = new PosParameter();        	 
     		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
     		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
     		param.setValueParamter(i++, "011");
     		param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));	         
     		param.setValueParamter(i++, record.getAttribute("ROUND"));
     		param.setValueParamter(i++, record.getAttribute("ITEM11_REC"));
     		param.setValueParamter(i++, record.getAttribute("ITEM11_SCR"));
     		param.setValueParamter(i++, SESSION_USER_ID);
     		dmlcount += this.getDao("candao").insert("tbdn_round_strt_mesur_in001", param);
     	}             

     	logger.logInfo("insertRound_Strt_Mesur end ============================");
     	return dmlcount;
     }
     
     /**
      * <p> 차수별체력측정 갱신  </p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		update 레코드 개수
      * @throws  none
      */
     protected int updateRound_Strt_Mesur(PosRecord record) 
     {
     	logger.logInfo("updateRound_Strt_Mesur start============================");
     	PosParameter param = new PosParameter();       					
     	int i = 0;
     	int dmlcount = 0;
  
     	String sItem01Rec = Util.trim(String.valueOf(record.getAttribute("ITEM01_REC")));
     	String sItem02Rec = Util.trim(String.valueOf(record.getAttribute("ITEM02_REC")));
     	String sItem03Rec = Util.trim(String.valueOf(record.getAttribute("ITEM03_REC")));
     	String sItem04Rec = Util.trim(String.valueOf(record.getAttribute("ITEM04_REC")));
     	String sItem05Rec = Util.trim(String.valueOf(record.getAttribute("ITEM05_REC")));
     	String sItem06Rec = Util.trim(String.valueOf(record.getAttribute("ITEM06_REC")));
     	String sItem07Rec = Util.trim(String.valueOf(record.getAttribute("ITEM07_REC")));
     	String sItem08Rec = Util.trim(String.valueOf(record.getAttribute("ITEM08_REC")));
     	String sItem09Rec = Util.trim(String.valueOf(record.getAttribute("ITEM09_REC")));
     	String sItem10Rec = Util.trim(String.valueOf(record.getAttribute("ITEM10_REC")));
     	String sItem11Rec = Util.trim(String.valueOf(record.getAttribute("ITEM11_REC")));

     	if(!sItem01Rec.equals("")){
     		i = 0;
     		dmlcount = 0;
     		param = new PosParameter();
	        param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
	        param.setValueParamter(i++, record.getAttribute("ITEM01_REC"));
	        param.setValueParamter(i++, record.getAttribute("ITEM01_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			i = 0;    
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
			param.setWhereClauseParameter(i++, "001");
			param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
			dmlcount += this.getDao("candao").update("tbdn_round_strt_mesur_un001", param);
     	}
     	
     	if(!sItem02Rec.equals("")){
     		i = 0;
     		dmlcount = 0;
     		param = new PosParameter();
	        param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
	        param.setValueParamter(i++, record.getAttribute("ITEM02_REC"));
	        param.setValueParamter(i++, record.getAttribute("ITEM02_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			i = 0;    
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
			param.setWhereClauseParameter(i++, "002");
			param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
			dmlcount += this.getDao("candao").update("tbdn_round_strt_mesur_un001", param);
     	} 
     	
    	if(!sItem03Rec.equals("")){
     		i = 0;
     		dmlcount = 0;
     		param = new PosParameter();
	        param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
	        param.setValueParamter(i++, record.getAttribute("ITEM03_REC"));
	        param.setValueParamter(i++, record.getAttribute("ITEM03_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			i = 0;    
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
			param.setWhereClauseParameter(i++, "003");
			param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
			dmlcount += this.getDao("candao").update("tbdn_round_strt_mesur_un001", param);
     	}     
    	
    	if(!sItem04Rec.equals("")){
     		i = 0;
     		dmlcount = 0;
     		param = new PosParameter();
	        param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
	        param.setValueParamter(i++, record.getAttribute("ITEM04_REC"));
	        param.setValueParamter(i++, record.getAttribute("ITEM04_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			i = 0;    
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
			param.setWhereClauseParameter(i++, "004");
			param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
			dmlcount += this.getDao("candao").update("tbdn_round_strt_mesur_un001", param);
     	}   
    	
    	if(!sItem05Rec.equals("")){
     		i = 0;
     		dmlcount = 0;
     		param = new PosParameter();
	        param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
	        param.setValueParamter(i++, record.getAttribute("ITEM05_REC"));
	        param.setValueParamter(i++, record.getAttribute("ITEM05_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			i = 0;    
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
			param.setWhereClauseParameter(i++, "005");
			param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
			dmlcount += this.getDao("candao").update("tbdn_round_strt_mesur_un001", param);
     	}  
    	
    	if(!sItem06Rec.equals("")){
     		i = 0;
     		dmlcount = 0;
     		param = new PosParameter();
	        param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
	        param.setValueParamter(i++, record.getAttribute("ITEM06_REC"));
	        param.setValueParamter(i++, record.getAttribute("ITEM06_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			i = 0;    
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
			param.setWhereClauseParameter(i++, "006");
			param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
			dmlcount += this.getDao("candao").update("tbdn_round_strt_mesur_un001", param);
     	}     
    	
    	if(!sItem07Rec.equals("")){
     		i = 0;
     		dmlcount = 0;
     		param = new PosParameter();
	        param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
	        param.setValueParamter(i++, record.getAttribute("ITEM07_REC"));
	        param.setValueParamter(i++, record.getAttribute("ITEM07_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			i = 0;    
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
			param.setWhereClauseParameter(i++, "007");
			param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
			dmlcount += this.getDao("candao").update("tbdn_round_strt_mesur_un001", param);
     	}     
    	
       	if(!sItem08Rec.equals("")){
     		i = 0;
     		dmlcount = 0;
     		param = new PosParameter();
	        param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
	        param.setValueParamter(i++, record.getAttribute("ITEM08_REC"));
	        param.setValueParamter(i++, record.getAttribute("ITEM08_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			i = 0;    
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
			param.setWhereClauseParameter(i++, "008");
			param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
			dmlcount += this.getDao("candao").update("tbdn_round_strt_mesur_un001", param);
     	}      	
     	
       	if(!sItem09Rec.equals("")){
     		i = 0;
     		dmlcount = 0;
     		param = new PosParameter();
	        param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
	        param.setValueParamter(i++, record.getAttribute("ITEM09_REC"));
	        param.setValueParamter(i++, record.getAttribute("ITEM09_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			i = 0;    
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
			param.setWhereClauseParameter(i++, "009");
			param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
			dmlcount += this.getDao("candao").update("tbdn_round_strt_mesur_un001", param);
     	}      	
     	
       	if(!sItem10Rec.equals("")){
     		i = 0;
     		dmlcount = 0;
     		param = new PosParameter();
	        param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
	        param.setValueParamter(i++, record.getAttribute("ITEM10_REC"));
	        param.setValueParamter(i++, record.getAttribute("ITEM10_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			i = 0;    
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
			param.setWhereClauseParameter(i++, "010");
			param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
			dmlcount += this.getDao("candao").update("tbdn_round_strt_mesur_un001", param);
     	}      	
     	
       	if(!sItem11Rec.equals("")){
     		i = 0;
     		dmlcount = 0;
     		param = new PosParameter();
	        param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
	        param.setValueParamter(i++, record.getAttribute("ITEM11_REC"));
	        param.setValueParamter(i++, record.getAttribute("ITEM11_SCR"));
			param.setValueParamter(i++, SESSION_USER_ID);
			i = 0;    
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
			param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
			param.setWhereClauseParameter(i++, "011");
			param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
			dmlcount += this.getDao("candao").update("tbdn_round_strt_mesur_un001", param);
     	}      	
     	
		logger.logInfo("updateRound_Strt_Mesur end ============================");
		return dmlcount;
     }

 
     /**
      * <p> 차수별체력측정 삭제</p>
      * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
      * @return  dmlcount	int		delete 레코드 개수
      * @throws  none
      */
     protected int deleteRound_Strt_Mesur(PosRecord record) 
     {
     	logger.logInfo("deleteRound_Strt_Mesur start============================");
     	PosParameter param = new PosParameter();       					
     	int i = 0;

     	param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
     	param.setWhereClauseParameter(i++, record.getAttribute("CAND_NO"));
     	param.setWhereClauseParameter(i++, record.getAttribute("ITEM_CD"));
     	param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
     	 
     	int dmlcount = this.getDao("candao").delete("tbdn_round_strt_mesur_dn001", param);

     	logger.logInfo("deleteRound_Strt_Mesur end============================");
     	return dmlcount;
     }
     
   
}
