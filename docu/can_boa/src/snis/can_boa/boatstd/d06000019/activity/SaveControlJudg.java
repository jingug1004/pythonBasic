/*================================================================================
 * 시스템			: 학사관리
 * 소스파일 이름	: snis.can_boa.boatstd.d06000019.activity.SaveControlJudg.java
 * 파일설명		: 조종실기평가
 * 작성자			: 최문규
 * 버전			: 1.0.0
 * 생성일자		: 2007-01-03
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.can_boa.boatstd.d06000019.activity;

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

public class SaveControlJudg extends SnisActivity 
{
	public SaveControlJudg() { }
	
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
		
		PosDataset ds1;
		PosDataset ds2;
		PosDataset ds3;
		PosDataset ds4;	
		PosDataset ds5;
		PosDataset ds6;
		PosDataset ds7;
		PosDataset ds8;
		PosDataset ds9;
		PosDataset ds10;
		PosDataset ds11;

        int    nSize1 = 0;
        int    nSize2 = 0;
        int    nSize3 = 0;
        int    nSize4 = 0; 
        int    nSize5 = 0;
        int    nSize6 = 0;
        int    nSize7 = 0;
        int    nSize8 = 0;
        int    nSize9 = 0;
        int    nSize10 = 0;
        int    nSize11 = 0;
        String     sDsName = "";
   
        //평가항목
        sDsName = "dsPilotItemCd1";
        if ( ctx.get(sDsName) != null ) {
        	ds1   = (PosDataset)ctx.get(sDsName);
	        nSize1 = ds1.getRecordCount();
        }
        
        //스타트타임,주회타임배점
        sDsName = "dsPilotRoundBas2";
        if ( ctx.get(sDsName) != null ) {
	        ds2   = (PosDataset)ctx.get(sDsName);
	        nSize2= ds2.getRecordCount();
        }   
                
        //주회타임 
        sDsName = "dsPilotCcitTimeBas3";
        if ( ctx.get(sDsName) != null ) {
	        ds3   = (PosDataset)ctx.get(sDsName);
	        nSize3= ds3.getRecordCount();
        }   
        
        //스타트타임
        sDsName = "dsPilotStTimeBas4";
        if ( ctx.get(sDsName) != null ) {
	        ds4    = (PosDataset)ctx.get(sDsName);
	        nSize4 = ds4.getRecordCount();
        }         

        //평가표
        sDsName = "dsPilotEstmItem5";
        if ( ctx.get(sDsName) != null ) {
	        ds5    = (PosDataset)ctx.get(sDsName);
	        nSize5 = ds5.getRecordCount();
        }   
        
        //조종실기평가표 세부 저장
        sDsName = "dsPilotEstmDetl6";
        if ( ctx.get(sDsName) != null ) {
	        ds6    = (PosDataset)ctx.get(sDsName);
	        nSize6 = ds6.getRecordCount();
        }   
        
        //주회타임 세부평가표
        sDsName = "dsPilotEstmTimeDetlScr1";
        if ( ctx.get(sDsName) != null ) {
	        ds7    = (PosDataset)ctx.get(sDsName);
	        nSize7 = ds7.getRecordCount();
        }    
        
        //스타트타임 세부평가표 
        sDsName = "dsPilotEstmTimeDetlScr2";
        if ( ctx.get(sDsName) != null ) {
	        ds8    = (PosDataset)ctx.get(sDsName);
	        nSize8 = ds8.getRecordCount();
        }           
        
        //주회타임 세부기록평가표 
        sDsName = "dsPilotEstmTimeRec1";
        if ( ctx.get(sDsName) != null ) {
	        ds9    = (PosDataset)ctx.get(sDsName);
	        nSize9 = ds9.getRecordCount();
        }           
        
        //스타트타임 세부기록평가표 
        sDsName = "dsPilotEstmTimeRec2";
        if ( ctx.get(sDsName) != null ) {
	        ds10    = (PosDataset)ctx.get(sDsName);
	        nSize10 = ds10.getRecordCount();
        }   
        
       //스타트타임 세부기록평가표 평가일 
        sDsName = "dsPilotItemDt";
        if ( ctx.get(sDsName) != null ) {
	        ds11    = (PosDataset)ctx.get(sDsName);
	        nSize11 = ds11.getRecordCount();
        }  
       
                
        if(nSize1 > 0){
        	saveStatePilot_Item_Cd(ctx); 
        }else if(nSize2 > 0){
        	saveStatePilot_Round_Bas(ctx); 
        }else if(nSize3 > 0){
        	saveStatePilot_Ccit_Time_Bas(ctx);         	
        }else if(nSize4 > 0){
        	saveStatePilot_St_Time_Bas(ctx); 
        }else if(nSize5 > 0){
        	saveStatePilot_Estm_Item(ctx);
        }else if(nSize6 > 0){
        	saveStatePilot_Estm_Detl(ctx);  
        }else if(nSize7 > 0){
        	saveStatePilot_Estm_Ccit_Time(ctx);     
        }else if(nSize8 > 0){
        	saveStatePilot_Estm_St_Time(ctx);             	
        }else if(nSize9 > 0){
        	saveStatePilot_Estm_Ccit_Time_Rec(ctx);             	
        }else if(nSize10 > 0){
        	saveStatePilot_Estm_St_Time_Rec(ctx);             	
        }else if(nSize11 > 0){
        	saveStatePilot_Dt(ctx);             	
        }

        return PosBizControlConstants.SUCCESS; 
    }
    
	/**
     * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소 평가항목
     * @return  none
     * @throws  none
     */
	protected void saveStatePilot_Item_Cd(PosContext ctx) 
	{
		int nSaveCount   = 0; 
     	int nDeleteCount = 0; 
     	PosDataset ds;
     	
        int nSize        = 0;
        int nTempCnt     = 0;
        
        //평가항목저장       
        ds   = (PosDataset) ctx.get("dsPilotItemCd1");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ) 
        {
        	PosRecord record = ds.getRecord(i);
             
        	if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE )
        	{
        		nTempCnt = updatePilot_Item_Cd(record);
        		nSaveCount = nSaveCount + nTempCnt;
        	}
        	if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
        	{          	 
        		nTempCnt = insertPilot_Item_Cd(record);
        		nSaveCount = nSaveCount + nTempCnt;
        	}
        	if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
        	{
        		// delete
             	nDeleteCount = nDeleteCount + deletePilot_Item_Cd(record);
        	}
        }
                  
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
	}
     
	/**
	 * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
	 * @param   ctx		PosContext	저장소 차부별기준
	 * @return  none
	 * @throws  none
	 */
	protected void saveStatePilot_Round_Bas(PosContext ctx) 
	{
		int nSaveCount   = 0; 
      	int nDeleteCount = 0; 
      	PosDataset ds;
      	
      	int nSize        = 0;
      	int nTempCnt     = 0;
         
      	// 평가항목저장       
      	ds   = (PosDataset) ctx.get("dsPilotRoundBas2");
      	nSize = ds.getRecordCount();
      	for ( int i = 0; i < nSize; i++ ) 
      	{
      		PosRecord record = ds.getRecord(i);
              
      		if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE )
      		{
      			nTempCnt = updatePilot_Round_Bas(record);
      			nSaveCount = nSaveCount + nTempCnt;
      		}
      		if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
      		{          	 
      			nTempCnt = insertPilot_Round_Bas(record);
      			nSaveCount = nSaveCount + nTempCnt;
      		}
              
      		if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
      		{
      			// delete
              	nDeleteCount = nDeleteCount + deletePilot_Round_Bas(record);
      		}
      	}
                   
      	Util.setSaveCount  (ctx, nSaveCount     );
      	Util.setDeleteCount(ctx, nDeleteCount   );
	}   
      
	/**
	 * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
	 * @param   ctx		PosContext	저장소 주회타임
	 * @return  none
	 * @throws  none
	 */
	protected void saveStatePilot_Ccit_Time_Bas(PosContext ctx) 
	{
		int nSaveCount   = 0; 
       	int nDeleteCount = 0; 
       	PosDataset ds;
       	
       	int nSize        = 0;
       	int nTempCnt     = 0;
          
       	// 시간배정표 저장       
       	ds   = (PosDataset) ctx.get("dsPilotCcitTimeBas3");
       	nSize = ds.getRecordCount();
       	for ( int i = 0; i < nSize; i++ ) 
       	{
       		PosRecord record = ds.getRecord(i);
               
       		if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE)
       		{          	 
       			nTempCnt = updatePilot_Ccit_Time_Bas(record);
       			nSaveCount = nSaveCount + nTempCnt;
       		}
            if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
            {
   				nTempCnt = insertPilot_Ccit_Time_Bas(record);
       			nSaveCount = nSaveCount + nTempCnt;
            }
       		if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
       		{
       			// delete
               	nDeleteCount = nDeleteCount + deletePilot_Ccit_Time_Bas(record);
       		}
       	}
                    
       	Util.setSaveCount  (ctx, nSaveCount     );
       	Util.setDeleteCount(ctx, nDeleteCount   );
	}      
     
	/**
	 * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
	 * @param   ctx		PosContext	저장소 스타트타임
	 * @return  none
	 * @throws  none
	 */
	protected void saveStatePilot_St_Time_Bas(PosContext ctx) 
	{
		int nSaveCount   = 0; 
		int nDeleteCount = 0; 
		PosDataset ds;
        	
		int nSize        = 0;
		int nTempCnt     = 0;
           
		// 시간배정표 저장       
		ds   = (PosDataset) ctx.get("dsPilotStTimeBas4");
		nSize = ds.getRecordCount();
		for ( int i = 0; i < nSize; i++ ) 
		{
			PosRecord record = ds.getRecord(i);
                
			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE)
			{
				nTempCnt = updatePilot_St_Time_Bas(record);
				nSaveCount = nSaveCount + nTempCnt;
			}
			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
			{          	 
				nTempCnt = insertPilot_St_Time_Bas(record);
				nSaveCount = nSaveCount + nTempCnt;
			}
                
			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
			{
				// delete
				nDeleteCount = nDeleteCount + deletePilot_St_Time_Bas(record);
			}
		}
                     
		Util.setSaveCount  (ctx, nSaveCount     );
		Util.setDeleteCount(ctx, nDeleteCount   );
	}         
        
        
	/**
	 * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
	 * @param   ctx		PosContext	저장소 평가표
	 * @return  none
	 * @throws  none
	 */
	protected void saveStatePilot_Estm_Item(PosContext ctx) 
	{
		int nSaveCount   = 0; 
		int nDeleteCount = 0; 
		PosDataset ds;
         	
		int nSize        = 0;
		int nTempCnt     = 0;
            
		//  저장       
		ds   = (PosDataset) ctx.get("dsPilotEstmItem5");
		nSize = ds.getRecordCount();

		for ( int i = 0; i < nSize; i++ ) 
		{
			PosRecord record = ds.getRecord(i);
                 
			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
					|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
			{          	 
				if ( (nTempCnt = updatePilot_Estm_Item(record)) == 0 ) {
					nTempCnt = insertPilot_Estm_Item(record);
				}            	
				nSaveCount = nSaveCount + nTempCnt;
			}

			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
			{
				// delete
				nDeleteCount = nDeleteCount + deletePilot_Estm_Item(record);
			}
		}
                      
		Util.setSaveCount  (ctx, nSaveCount     );
		Util.setDeleteCount(ctx, nDeleteCount   );
	}   
	
	protected void saveStatePilot_Dt(PosContext ctx) 
	{
		int nSaveCount   = 0; 
		PosDataset ds;
         	
		int nSize        = 0;
            
		//  저장       
		ds   = (PosDataset) ctx.get("dsPilotItemDt");
		nSize = ds.getRecordCount();
		for ( int i = 0; i < nSize; i++ ) 
		{
			PosRecord record = ds.getRecord(i);
                 
			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE)
			{     
				logger.logInfo("updatePilot_Estm_Item_Dt start============================");
		     	PosParameter param = new PosParameter();       					
		     	int j = 0;
				
		        param.setValueParamter(j++, Util.trim(record.getAttribute("DT")));     	

				j = 0;    
				param.setWhereClauseParameter(j++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
				param.setWhereClauseParameter(j++, record.getAttribute("ROUND"));

				int dmlcount = this.getDao("candao").update("tbdn_pilot_estm_item_un002", param);

				logger.logInfo("updatePilot_Estm_Item_Dt end============================");
				nSaveCount = nSaveCount + dmlcount;
			}

		}
		Util.setSaveCount  (ctx, nSaveCount);
	}
             
	/**
	 * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
	 * @param   ctx		PosContext	저장소 조종실기평가표 세부
	 * @return  none
	 * @throws  none
	 */
	protected void saveStatePilot_Estm_Detl(PosContext ctx) 
	{
		int nSaveCount   = 0; 
      	int nDeleteCount = 0; 
      	PosDataset ds;
      	
      	int nSize        = 0;
      	int nTempCnt     = 0;
         
      	//  저장       
      	ds   = (PosDataset) ctx.get("dsPilotEstmDetl6");
      	nSize = ds.getRecordCount();
      	for ( int i = 0; i < nSize; i++ ) 
      	{
      		PosRecord record = ds.getRecord(i);
              
      		if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
      				|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
      		{          	 
      			if ( (nTempCnt = updatePilot_Estm_Detl(record)) == 0 ) {
      				nTempCnt = insertPilot_Estm_Detl(record);
      			}            	
      			nSaveCount = nSaveCount + nTempCnt;
      		}
              
      		if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
      		{
      			// delete
      			nDeleteCount = nDeleteCount + updatePilot_Estm_Detl(record);
      		}
      	}
                   
      	Util.setSaveCount  (ctx, nSaveCount     );
      	Util.setDeleteCount(ctx, nDeleteCount   );
	}           
       
      
	/**
	 * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
	 * @param   ctx		PosContext	저장소 주회타임 세부평가표 세부
	 * @return  none
	 * @throws  none
	 */
	protected void saveStatePilot_Estm_Ccit_Time(PosContext ctx) 
	{
		int nSaveCount   = 0; 
       	int nDeleteCount = 0; 
       	PosDataset ds;
       	
       	int nSize        = 0;
       	int nTempCnt     = 0;
          
       	//  저장       
       	ds   = (PosDataset) ctx.get("dsPilotEstmTimeDetlScr1");
       	nSize = ds.getRecordCount();
       	for ( int i = 0; i < nSize; i++ ) 
       	{
       		PosRecord record = ds.getRecord(i);
               
       		if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
       				|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
       		{          	 
       			if ( (nTempCnt = updatePilot_Estm_Ccit_Time(record)) == 0 ) {
       				nTempCnt = insertPilot_Estm_Ccit_Time(record);
       			}            	
       			nSaveCount = nSaveCount + nTempCnt;
       		}
               
       		if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
       		{
       			// delete
       			nDeleteCount = nDeleteCount + deletePilot_Estm_Ccit_Time(record);
       		}
       	}
                    
       	Util.setSaveCount  (ctx, nSaveCount     );
       	Util.setDeleteCount(ctx, nDeleteCount   );
	}  
       
	/**
	 * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
	 * @param   ctx		PosContext	저장소 주회타임 세부평가표 세부
	 * @return  none
	 * @throws  none
	 */
	protected void saveStatePilot_Estm_Ccit_Time_Rec(PosContext ctx) 
	{
		int nSaveCount   = 0; 
		int nDeleteCount = 0; 
		PosDataset ds;
        	
		int nSize        = 0;
		int nTempCnt     = 0;
           
		//  저장       
		ds   = (PosDataset) ctx.get("dsPilotEstmTimeRec1");
		nSize = ds.getRecordCount();
		for ( int i = 0; i < nSize; i++ ) 
		{
			PosRecord record = ds.getRecord(i);
                
			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
					|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
			{          	 
				if ( (nTempCnt = updatePilot_Estm_Time_Rec(record)) == 0 ) {
					nTempCnt = insertPilot_Estm_Time_Rec(record);
				}            	
				nSaveCount = nSaveCount + nTempCnt;
			}
                
			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
			{
				// delete
				nDeleteCount = nDeleteCount + deletePilot_Estm_Time_Rec(record);
			}
		}
                     
		Util.setSaveCount  (ctx, nSaveCount     );
		Util.setDeleteCount(ctx, nDeleteCount   );
	}  
       
	/**
	 * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
	 * @param   ctx		PosContext	저장소 주회타임 세부평가표 세부
	 * @return  none
	 * @throws  none
	 */
	protected void saveStatePilot_Estm_St_Time_Rec(PosContext ctx) 
	{
		int nSaveCount   = 0; 
		int nDeleteCount = 0; 
		PosDataset ds;
        	
		int nSize        = 0;
		int nTempCnt     = 0;
           
		//  저장       
		ds   = (PosDataset) ctx.get("dsPilotEstmTimeRec2");
		nSize = ds.getRecordCount();
		for ( int i = 0; i < nSize; i++ ) 
		{
			PosRecord record = ds.getRecord(i);
                
			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
					|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
			{          	 
				if ( (nTempCnt = updatePilot_Estm_Time_Rec(record)) == 0 ) {
					nTempCnt = insertPilot_Estm_Time_Rec(record);
				}            	
				nSaveCount = nSaveCount + nTempCnt;
			}
                
			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
			{
				// delete
				nDeleteCount = nDeleteCount + deletePilot_Estm_Time_Rec(record);
			}
		}
                     
		Util.setSaveCount  (ctx, nSaveCount     );
		Util.setDeleteCount(ctx, nDeleteCount   );
	}  
       
	/**
	 * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
	 * @param   ctx		PosContext	저장소 스타트타임 세부평가표 
	 * @return  none
	 * @throws  none
	 */
	protected void saveStatePilot_Estm_St_Time(PosContext ctx) 
	{
		int nSaveCount   = 0; 
		int nDeleteCount = 0; 
		PosDataset ds;
        	
		int nSize        = 0;
		int nTempCnt     = 0;
           
		//  저장       
		ds = (PosDataset) ctx.get("dsPilotEstmTimeDetlScr2");
		nSize = ds.getRecordCount();
		for ( int i = 0; i < nSize; i++ ) 
		{
			PosRecord record = ds.getRecord(i);
			if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
					|| record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
			{          	 
				if ( (nTempCnt = updatePilot_Estm_Ccit_Time(record)) == 0 ) {
					nTempCnt = insertPilot_Estm_Ccit_Time(record);
				}            	
				nSaveCount = nSaveCount + nTempCnt;
			}
                
			if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
			{
				// delete
				nDeleteCount = nDeleteCount + deletePilot_Estm_Ccit_Time(record);
			}
		}
                     
		Util.setSaveCount  (ctx, nSaveCount     );
		Util.setDeleteCount(ctx, nDeleteCount   );
	}        
       
              
      
	/**
	 * <p> 평가항목 입력 </p>
	 * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	 * @return  dmlcount	int		insert 레코드 개수
	 * @throws  none
	 */
	protected int insertPilot_Item_Cd(PosRecord record) 
	{
		logger.logInfo("insertPilot_Item_Cd start ============================");
		PosParameter param = new PosParameter();       					
		int i = 0;
          
		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ESTM_ITEM_CD")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("ESTM_ITEM_DETL")));
		param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR_LIMIT"));
		param.setValueParamter(i++, record.getAttribute("ROUND"));
		param.setValueParamter(i++, SESSION_USER_ID);
         
		int dmlcount = this.getDao("candao").insert("tbdn_pilot_item_cd_in001", param);
         
		logger.logInfo("insertPilot_Item_Cd end ============================");
		return dmlcount;
	}
     
	/**
	 * <p> 평가항목 갱신  </p>
	 * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	 * @return  dmlcount	int		update 레코드 개수
	 * @throws  none
	 */
	protected int updatePilot_Item_Cd(PosRecord record) 
	{
		logger.logInfo("updatePilot_Item_Cd start============================");
     	PosParameter param = new PosParameter();       					
     	int i = 0;
		
     	param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));     	
        param.setValueParamter(i++, Util.trim(record.getAttribute("ESTM_ITEM_CD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("ESTM_ITEM_DETL")));
        param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR_LIMIT"));
        param.setValueParamter(i++, record.getAttribute("ROUND"));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
		int dmlcount = this.getDao("candao").update("tbdn_pilot_item_cd_un001", param);

		logger.logInfo("updatePilot_Item_Cd end============================");
		return dmlcount;
	}

	/**
	 * <p> 평가항목  삭제</p>
	 * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	 * @return  dmlcount	int		delete 레코드 개수
	 * @throws  none
	 */
	protected int deletePilot_Item_Cd(PosRecord record) 
	{
		logger.logInfo("deletePilot_Item_Cd start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;
             
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
                 
		int dmlcount = this.getDao("candao").delete("tbdn_pilot_item_cd_dn001", param);
         
		logger.logInfo("deletePilot_Item_Cd end============================");
		return dmlcount;
	}
     
	/**
	 * <p> 차부별기준 입력 </p>
	 * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	 * @return  dmlcount	int		insert 레코드 개수
	 * @throws  none
	 */
	protected int insertPilot_Round_Bas(PosRecord record) 
	{
		logger.logInfo("insertPilot_Round_Bas start ============================");
		PosParameter param = new PosParameter();       					
		int i = 0;
                    
		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, record.getAttribute("ROUND"));
		param.setValueParamter(i++, record.getAttribute("ST_TIME_SCR"));
		param.setValueParamter(i++, record.getAttribute("CCIT_SCR"));
		param.setValueParamter(i++, SESSION_USER_ID);
                    
		int dmlcount = this.getDao("candao").insert("tbdn_pilot_round_bas_in001", param);
         
		logger.logInfo("insertPilot_Round_Bas end ============================");
		return dmlcount;
	}
     
	/**
	 * <p> 차부별기준 갱신  </p>
	 * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	 * @return  dmlcount	int		update 레코드 개수
	 * @throws  none
	 */
	protected int updatePilot_Round_Bas(PosRecord record) 
	{
		logger.logInfo("updatePilot_Round_Bas start============================");
     	PosParameter param = new PosParameter();       					
     	int i = 0;
		

        param.setValueParamter(i++, record.getAttribute("ST_TIME_SCR"));
        param.setValueParamter(i++, record.getAttribute("CCIT_SCR"));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
        param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));		
		int dmlcount = this.getDao("candao").update("tbdn_pilot_round_bas_un001", param);

		logger.logInfo("updatePilot_Round_Bas end============================");
		return dmlcount;
	}
     
 
	/**
	 * <p> 차부별기준  삭제</p>
	 * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	 * @return  dmlcount	int		delete 레코드 개수
	 * @throws  none
	 */
	protected int deletePilot_Round_Bas(PosRecord record) 
	{
		logger.logInfo("deletePilot_Round_Bas start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;
             
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));                 
		int dmlcount = this.getDao("candao").delete("tbdn_pilot_round_bas_dn001", param);
         
		logger.logInfo("deletePilot_Round_Bas end============================");
		return dmlcount;
	}
     
	/**
	 * <p> 주회타임 입력 </p>
	 * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	 * @return  dmlcount	int		insert 레코드 개수
	 * @throws  none
	 */
	protected int insertPilot_Ccit_Time_Bas(PosRecord record) 
	{
    	 logger.logInfo("insertPilot_Ccit_Time_Bas start ============================");
    	 PosParameter param = new PosParameter();       					
         int i = 0;
               
         param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
         param.setValueParamter(i++, record.getAttribute("ROUND"));
         param.setValueParamter(i++, record.getAttribute("UPPER_FROM"));
         param.setValueParamter(i++, record.getAttribute("UPPER_TO"));
         param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR_FROM"));
         param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR_TO"));
         param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
         param.setValueParamter(i++, SESSION_USER_ID);
                    
         int dmlcount = this.getDao("candao").insert("tbdn_pilot_ccit_time_bas_in001", param);
         
         logger.logInfo("insertPilot_Ccit_Time_Bas end ============================");
         return dmlcount;
	}
     
	/**
	 * <p> 주회타임 갱신  </p>
	 * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	 * @return  dmlcount	int		update 레코드 개수
	 * @throws  none
	 */
	protected int updatePilot_Ccit_Time_Bas(PosRecord record) 
	{
		logger.logInfo("updatePilot_Ccit_Time_Bas start============================");
     	PosParameter param = new PosParameter();       					
     	int i = 0;
		
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));     	
        param.setValueParamter(i++, record.getAttribute("ROUND"));
        param.setValueParamter(i++, record.getAttribute("UPPER_FROM"));
        param.setValueParamter(i++, record.getAttribute("UPPER_TO"));
        param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR_FROM"));
        param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR_TO"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
		int dmlcount = this.getDao("candao").update("tbdn_pilot_ccit_time_bas_un001", param);

		logger.logInfo("updatePilot_Ccit_Time_Bas end============================");
		return dmlcount;
	}
     
 
	/**
	 * <p> 주회타임 삭제</p>
	 * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	 * @return  dmlcount	int		delete 레코드 개수
	 * @throws  none
	 */
	protected int deletePilot_Ccit_Time_Bas(PosRecord record) 
	{
		logger.logInfo("deletePilot_Ccit_Time_Bas start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;
             
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
                 
		int dmlcount = this.getDao("candao").delete("tbdn_pilot_ccit_time_bas_dn001", param);
         
		logger.logInfo("deletePilot_Ccit_Time_Bas end============================");
		return dmlcount;
	}    
     
	/**
	 * <p> 스타트타임 입력 </p>
	 * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	 * @return  dmlcount	int		insert 레코드 개수
	 * @throws  none
	 */
	protected int insertPilot_St_Time_Bas(PosRecord record) 
	{
		logger.logInfo("insertPilot_St_Time_Bas start ============================");
		PosParameter param = new PosParameter();       					
		int i = 0;
             
		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, record.getAttribute("ROUND"));
		param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR"));
		param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR_FROM"));
		param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR_TO"));
		param.setValueParamter(i++, SESSION_USER_ID);
                    
		int dmlcount = this.getDao("candao").insert("tbdn_pilot_st_time_bas_in001", param);
         
		logger.logInfo("insertPilot_St_Time_Bas end ============================");
		return dmlcount;
	}
     
	/**
	 * <p> 스타트타임 갱신  </p>
	 * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	 * @return  dmlcount	int		update 레코드 개수
	 * @throws  none
	 */
	protected int updatePilot_St_Time_Bas(PosRecord record) 
	{
		logger.logInfo("updatePilot_St_Time_Bas start============================");
     	PosParameter param = new PosParameter();       					
     	int i = 0;
		
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
        param.setValueParamter(i++, record.getAttribute("ROUND"));        
        param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR"));
        param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR_FROM"));
        param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR_TO"));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
		int dmlcount = this.getDao("candao").update("tbdn_pilot_st_time_bas_un001", param);

		logger.logInfo("updatePilot_St_Time_Bas end============================");
		return dmlcount;
	}
     
 
	/**
	 * <p> 스타트타임 삭제</p>
	 * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	 * @return  dmlcount	int		delete 레코드 개수
	 * @throws  none
	 */
	protected int deletePilot_St_Time_Bas(PosRecord record) 
	{
		logger.logInfo("deletePilot_St_Time_Bas start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;
             
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));
                 
		int dmlcount = this.getDao("candao").delete("tbdn_pilot_st_time_bas_dn001", param);
         
		logger.logInfo("deletePilot_St_Time_Bas end============================");
		return dmlcount;
	}     
     
	/**
	 * <p> 평가표 입력 </p>
	 * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	 * @return  dmlcount	int		insert 레코드 개수
	 * @throws  none
	 */
	protected int insertPilot_Estm_Item(PosRecord record) 
	{
		logger.logInfo("insertPilot_Estm_Item start ============================");
		PosParameter param = new PosParameter();       					
		int i = 0;
             
		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setValueParamter(i++, record.getAttribute("ROUND"));
		param.setValueParamter(i++, SESSION_USER_ID);
		param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));
		param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR"));
		param.setValueParamter(i++, Util.trim(record.getAttribute("DCSN")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
		param.setValueParamter(i++, SESSION_USER_ID);
                    
		int dmlcount = this.getDao("candao").insert("tbdn_pilot_estm_item_in001", param);
         
		logger.logInfo("insertPilot_Estm_Item end ============================");
		return dmlcount;
	}
     
	/**
	 * <p> 평가표 갱신  </p>
	 * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	 * @return  dmlcount	int		update 레코드 개수
	 * @throws  none
	 */
	protected int updatePilot_Estm_Item(PosRecord record) 
	{
		logger.logInfo("updatePilot_Estm_Item start============================");
     	PosParameter param = new PosParameter();       					
     	int i = 0;
		
        param.setValueParamter(i++, Util.trim(record.getAttribute("DT")));     	
        param.setValueParamter(i++, record.getAttribute("ASSIGN_SCR"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("DCSN")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
        param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));		
		param.setWhereClauseParameter(i++, SESSION_USER_ID);
		int dmlcount = this.getDao("candao").update("tbdn_pilot_estm_item_un001", param);

		logger.logInfo("updatePilot_Estm_Item end============================");
		return dmlcount;
	} 
 
	/**
	 * <p> 평가표 삭제</p>
	 * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	 * @return  dmlcount	int		delete 레코드 개수
	 * @throws  none
	 */
	protected int deletePilot_Estm_Item(PosRecord record) 
	{
		logger.logInfo("deletePilot_Estm_Item start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));        
		param.setWhereClauseParameter(i++, SESSION_USER_ID);
		int dmlcount = this.getDao("candao").delete("tbdn_pilot_estm_item_dn001", param);

		logger.logInfo("deletePilot_Estm_Item end============================");
		return dmlcount;
	}     
	
	/**
	 * <p> 조종실기평가표 세부 입력 </p>
	 * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	 * @return  dmlcount	int		insert 레코드 개수
	 * @throws  none
	 */
	protected int insertPilot_Estm_Detl(PosRecord record) 
	{
		PosParameter param = new PosParameter();       					
		int i = 0;
 
		param.setValueParamter(i++, Util.trim(record.getAttribute("SEQ")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setValueParamter(i++, record.getAttribute("ROUND"));
		
		param.setValueParamter(i++, record.getAttribute("ESTM_SCR"));
		param.setValueParamter(i++, SESSION_USER_ID);
		param.setValueParamter(i++, SESSION_USER_ID);
			   
		int dmlcount = this.getDao("candao").insert("tbdn_pilot_estm_detl_in001", param);

		return dmlcount;
	}

	/**
	 * <p> 조종실기평가표 세부 갱신  </p>
	 * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	 * @return  dmlcount	int		update 레코드 개수
	 * @throws  none
	 */
	protected int updatePilot_Estm_Detl(PosRecord record) 
	{
		logger.logInfo("updatePilot_Estm_Detl start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;

		
		param.setValueParamter(i++, record.getAttribute("ESTM_SCR"));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));		
		param.setWhereClauseParameter(i++, SESSION_USER_ID);
		int dmlcount = this.getDao("candao").update("tbdn_pilot_estm_detl_un001", param);

		logger.logInfo("updatePilot_Estm_Detl end============================");
		return dmlcount;
	}


	/**
	 * <p> 조종실기평가표 세부 삭제</p>
	 * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	 * @return  dmlcount	int		delete 레코드 개수
	 * @throws  none
	 */
	protected int deletePilot_Estm_Detl(PosRecord record) 
	{
		logger.logInfo("deletePilot_Estm_Detl start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));		
		param.setWhereClauseParameter(i++, SESSION_USER_ID);
		int dmlcount = this.getDao("candao").delete("tbdn_pilot_estm_detl_dn001", param);

		logger.logInfo("deletePilot_Estm_Detl end============================");
		return dmlcount;
	}	
	
	/**
	 * <p> 주회타임/스타트타임 세부평가표 입력 </p>
	 * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	 * @return  dmlcount	int		insert 레코드 개수
	 * @throws  none
	 */
	protected int insertPilot_Estm_Ccit_Time(PosRecord record) 
	{
		logger.logInfo("insertPilot_Estm_Ccit_Time start ============================");
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setValueParamter(i++, record.getAttribute("ROUND"));
		param.setValueParamter(i++, Util.trim(record.getAttribute("TIME_GBN")));
		param.setValueParamter(i++, SESSION_USER_ID);
		param.setValueParamter(i++, record.getAttribute("ESTM_SCR"));
		param.setValueParamter(i++, SESSION_USER_ID);
		int dmlcount = this.getDao("candao").insert("tbdn_pilot_estm_ccit_time_in001", param);

		logger.logInfo("insertPilot_Estm_Ccit_Time end ============================");
		return dmlcount;
	}	
	
	/**
	 * <p> 주회타임/스타트타임 세부평가표 갱신  </p>
	 * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	 * @return  dmlcount	int		update 레코드 개수
	 * @throws  none
	 */
	protected int updatePilot_Estm_Ccit_Time(PosRecord record) 
	{
		logger.logError("updatePilot_Estm_Ccit_Time start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setValueParamter(i++, record.getAttribute("ESTM_SCR"));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, SESSION_USER_ID);
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("TIME_GBN")));	
		int dmlcount = this.getDao("candao").update("tbdn_pilot_estm_ccit_time_un001", param);

		logger.logError("updatePilot_Estm_Ccit_Time end============================");
		return dmlcount;
	}

	/**
	 * <p> 주회타임/스타트타임 세부평가표 삭제</p>
	 * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	 * @return  dmlcount	int		delete 레코드 개수
	 * @throws  none
	 */
	protected int deletePilot_Estm_Ccit_Time(PosRecord record) 
	{
		logger.logInfo("deletePilot_Estm_Ccit_Time start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setWhereClauseParameter(i++, SESSION_USER_ID);
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("TIME_GBN")));	
		int dmlcount = this.getDao("candao").delete("tbdn_pilot_estm_ccit_time_dn001", param);

		logger.logInfo("deletePilot_Estm_Ccit_Time end============================");
		return dmlcount;
	}	
	
	/**
	 * <p> 주회타임/스타트타임 세부 기록 입력 </p>
	 * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	 * @return  dmlcount	int		insert 레코드 개수
	 * @throws  none
	 */
	protected int insertPilot_Estm_Time_Rec(PosRecord record) 
	{
		logger.logInfo("insertPilot_Estm_Time_Rec start ============================");
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setValueParamter(i++, record.getAttribute("ROUND"));
		param.setValueParamter(i++, Util.trim(record.getAttribute("TIME_GBN")));
		param.setValueParamter(i++, SESSION_USER_ID);
		param.setValueParamter(i++, record.getAttribute("ESTM_CNT"));
		param.setValueParamter(i++, record.getAttribute("ESTM_REC"));
		param.setValueParamter(i++, SESSION_USER_ID);
		int dmlcount = this.getDao("candao").insert("tbdn_pilot_estm_time_rec_in001", param);

		logger.logInfo("insertPilot_Estm_Time_Rec end ============================");
		return dmlcount;
	}	
	
	/**
	 * <p> 주회타임/스타트타임 세부 기록 갱신  </p>
	 * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	 * @return  dmlcount	int		update 레코드 개수
	 * @throws  none
	 */
	protected int updatePilot_Estm_Time_Rec(PosRecord record) 
	{
		logger.logError("updatePilot_Estm_Time_Rec start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setValueParamter(i++, record.getAttribute("ESTM_REC"));
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("TIME_GBN")));	
		param.setWhereClauseParameter(i++, SESSION_USER_ID);
		param.setWhereClauseParameter(i++, record.getAttribute("ESTM_CNT"));
		int dmlcount = this.getDao("candao").update("tbdn_pilot_estm_time_rec_un001", param);

		logger.logError("updatePilot_Estm_Time_Rec end============================");
		return dmlcount;
	}

	/**
	 * <p> 주회타임/스타트타임 세부 기록 삭제</p>
	 * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
	 * @return  dmlcount	int		delete 레코드 개수
	 * @throws  none
	 */
	protected int deletePilot_Estm_Time_Rec(PosRecord record) 
	{
		logger.logInfo("deletePilot_Estm_Time_Rec start============================");
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("CAND_NO")));
		param.setWhereClauseParameter(i++, record.getAttribute("ROUND"));
		param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("TIME_GBN")));	
		param.setWhereClauseParameter(i++, SESSION_USER_ID);
		param.setWhereClauseParameter(i++, record.getAttribute("ESTM_CNT"));
		int dmlcount = this.getDao("candao").delete("tbdn_pilot_estm_time_rec_dn001", param);

		logger.logInfo("deletePilot_Estm_Time_Rec end============================");
		return dmlcount;
	}	
}
