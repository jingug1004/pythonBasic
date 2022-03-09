/*================================================================================
 * 시스템			: 장비관리 
 * 소스파일 이름	: snis.boa.equipment.e06010010.activity.SaveEquipLot.java
 * 파일설명		: 모터 /보트 추첨 대상을 저장한다.  
 * 작성자			: 김성희 
 * 버전			: 1.0.0
 * 생성일자		: 2007-11-01
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.boa.equipment.e06010040.activity;


import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

/**
* 모터/보트 정비 이력에 대한 정보를 등록, 수정, 삭제 한다.
* 모터/보트 정비 유형에 대하여 등록, 삭제 한다. 
* @auther 김성희 
* @version 1.0
*/
public class SaveEquipReprHis extends SnisActivity
{    
    public SaveEquipReprHis()
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
    	//사용자 정보 확인
    	if ( setUserInfo(ctx)== null || !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
        saveState(ctx);
        return PosBizControlConstants.SUCCESS;
    }

   
    
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 

    	PosDataset ds;
    	PosDataset dsParts;
        int nSize        = 0;
        
        ds      = (PosDataset) ctx.get("dsOutEquipReprHis");
        dsParts = (PosDataset) ctx.get("dsOutOrderPartsList");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	nDeleteCount = nDeleteCount + deleteEquipReprHis(record);
            	//정비 유형 삭제 
    			deleteEquipReprTpeHis(record);
    			//정비부품 삭제 
    			deleteEquipReprparts(record);    			
            } else {
            	nSaveCount = nSaveCount + saveEquipReprHis(record);
            	//정비 유형 삭제후 저장  
    			deleteEquipReprTpeHis(record); 
    			insertEquipReprTpeHis(record);
    			
    			saveEquipReprParts(record, dsParts);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
  
    /**
     * <p> 정비이력 Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveEquipReprHis(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	String sReprMaxSeq = "";
    	
    	if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT){
    		// 정비일련번호 생성
    		sReprMaxSeq = getMaxReprSeq(record);
    		
    		// 정비일련번호 설정 
    		record.setAttribute("REPR_SEQ", sReprMaxSeq);
    		
    	} 
    	effectedRowCnt = mergeEquipReprHis(record);
    	
        return effectedRowCnt;
    }

    /**
     * <p> 정비이력 등록 /수정  </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int mergeEquipReprHis(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
    	String homepageYN = (String)record.getAttribute("HOMEPAGE_YN");
    	homepageYN = homepageYN != null && homepageYN.equals("1") ? "Y" :  "N";
    	
    	
    	param.setValueParamter(i++, record.getAttribute("STND_YEAR"));  
    	param.setValueParamter(i++, record.getAttribute("MBR_CD"));   
    	param.setValueParamter(i++, record.getAttribute("EQUIP_TPE_CD"));  
    	param.setValueParamter(i++, record.getAttribute("EQUIP_NO"));  
    	param.setValueParamter(i++, record.getAttribute("REPR_DT"));  
    	param.setValueParamter(i++, record.getAttribute("REPR_SEQ"));  
    	param.setValueParamter(i++, record.getAttribute("REPR_TPE_CDS"));  
    	param.setValueParamter(i++, record.getAttribute("REPR_TPE_NMS"));  
    	param.setValueParamter(i++, record.getAttribute("MJR_PARTS"));  
    	param.setValueParamter(i++, record.getAttribute("REPR_DESC"));  
    	param.setValueParamter(i++, record.getAttribute("REPR_NM"));  
    	param.setValueParamter(i++, homepageYN);  
    	param.setValueParamter(i++, record.getAttribute("HOMEPAGE_DESC")); 
    	param.setValueParamter(i++, record.getAttribute("REL_STND_YEAR"));  
    	param.setValueParamter(i++, record.getAttribute("REL_MBR_CD"));  
    	param.setValueParamter(i++, record.getAttribute("REL_TMS"));  
    	param.setValueParamter(i++, record.getAttribute("REL_DAY_ORD"));  
    	param.setValueParamter(i++, record.getAttribute("REL_RACE_NO"));  
    	param.setValueParamter(i++, record.getAttribute("REL_RACE_REG_NO"));  
    	param.setValueParamter(i++, SESSION_USER_ID);
         
        return this.getDao("boadao").update("tbef_equip_repr_his_mf001", param);
    }
    
    /**
     * 정비 유형 등록 
     * @param record
     * @return
     */
    protected int insertEquipReprTpeHis(PosRecord record) 
    {
        int effectedRowCnt = 0;
        
        String[] reprTpeCds = ((String)record.getAttribute("REPR_TPE_CDS")).split(",");
    	for(int j=0; j<reprTpeCds.length; j++){
    		PosParameter param = new PosParameter();
            int i = 0;
	        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
	        param.setValueParamter(i++, record.getAttribute("REPR_SEQ")); 
	    	param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
	    	param.setValueParamter(i++, record.getAttribute("REPR_SEQ")); 
	    	param.setValueParamter(i++, reprTpeCds[j]);
	    	effectedRowCnt += this.getDao("boadao").update("tbef_equip_repr_tpe_his_if001", param);
    	}
        
        return  effectedRowCnt;
    }

    

    /**
     * <p>정비 이력  삭제 </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		delete 레코드 개수
     * @throws  none
     */
    protected int deleteEquipReprHis(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
    	param.setValueParamter(i++, record.getAttribute("REPR_SEQ")); 
        
        return  this.getDao("boadao").update("tbef_equip_repr_his_df001", param);
    }
    
    
    /**
     * 정비 유형 삭제 
     * @param record
     * @return
     */
    protected int deleteEquipReprTpeHis(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
    	param.setValueParamter(i++, record.getAttribute("REPR_SEQ")); 
    	param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
    	param.setValueParamter(i++, record.getAttribute("REPR_SEQ")); 
        
        return  this.getDao("boadao").update("tbef_equip_repr_tpe_his_df001", param);
    }
    
    /**
     * 정비부품내역 삭제 
     * @param record
     * @return
     */
    protected int deleteEquipReprparts(PosRecord record) 
    {
    	int dCount = 0;
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
    	param.setValueParamter(i++, record.getAttribute("REPR_SEQ")); 
    	
    	dCount  = this.getDao("boadao").update("tbef_equip_repr_his_delv_parts_del", param);        
    	//dCount += this.getDao("boadao").update("tbef_equip_repr_his_delv_del", param);
                
        return dCount;
        
    }

    /**
     * <p> 정비이력 Save </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int saveEquipReprParts(PosRecord record, PosDataset dsParts)
    {
    	int mcount = 0;
    	String stndYear = record.getAttribute("STND_YEAR").toString();
        String mbrCd    = record.getAttribute("MBR_CD").toString();
        String delvSeq  = record.getAttribute("DELV_SEQ").toString();
        String sDelvseq = "";
        String stndYearParts = "";
        String mbrCdParts    = "";
        String delvSeqParts  = "";
        
        // 정비이력 상태를 체크하여 신규인 경우 출고번호 생성
		// 정비일련번호 조회
		sDelvseq = getMaxDelvSeq(record);	// 출고에 자료가 없는 경우 출고 테이블에 Insert
    	MergeTbefDelv(record, sDelvseq);
    	
    	// 세부내역
        int nSize = dsParts.getRecordCount();
        
    	for ( int i = 0; i < nSize; i++ ){
            PosRecord recordParts = dsParts.getRecord(i);
            stndYearParts = recordParts.getAttribute("STND_YEAR").toString();
            mbrCdParts    = recordParts.getAttribute("MBR_CD").toString();
            delvSeqParts  = recordParts.getAttribute("DELV_SEQ").toString();
        	
            if (stndYear.equals(stndYearParts) && mbrCd.equals(mbrCdParts) &&
            	delvSeq.equals(delvSeqParts)) {
            	
	            if (recordParts.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
	                int j = 0;
	                PosParameter param = new PosParameter();
	                param.setValueParamter(j++, stndYearParts);  
	            	param.setValueParamter(j++, mbrCdParts);   
	            	param.setValueParamter(j++, sDelvseq);
	            	param.setValueParamter(j++, recordParts.getAttribute("PARTS_CD"));  
	            	param.setValueParamter(j++, recordParts.getAttribute("PARTS_YEAR"));   
	                
	            	mcount += this.getDao("boadao").update("tbef_equip_parts_repr_df002", param);   
	                
	            	//재고 수량 조정
	            	//updatePartsNowUnitCnt(recordParts, "DELETE"); 모터보트수리 시 재고수량은 조정하지 않음 BY CHO

	            } else {
	                int j = 0;
	                PosParameter param = new PosParameter();
	                param.setValueParamter(j++, stndYearParts);  
	            	param.setValueParamter(j++, mbrCdParts);   
	            	param.setValueParamter(j++, sDelvseq);
	            	param.setValueParamter(j++, recordParts.getAttribute("PARTS_CD"));  
	            	param.setValueParamter(j++, recordParts.getAttribute("PARTS_YEAR"));   
	            	param.setValueParamter(j++, recordParts.getAttribute("DELV_CNT"));   
	            	param.setValueParamter(j++, recordParts.getAttribute("RECEPT_ID"));   
	            	param.setValueParamter(j++, recordParts.getAttribute("RMK"));   
	            	param.setValueParamter(j++, SESSION_USER_ID);
	                
	            	mcount += this.getDao("boadao").update("tbef_delv_parts_repr_mf001", param);	            }
	            
	            	//재고 수량 조정
	            	//updatePartsNowUnitCnt(recordParts, "MERGE"); 모터보트수리 시 재고수량은 조정하지 않음 BY CHO
            }
        }
        return mcount;
    }

    /**
     * <p> 정비일련번호 생성 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected String getMaxReprSeq(PosRecord record) 
    {
        String rtnKey = "";
        
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));	//기준년도        

        PosRowSet keyRecord = this.getDao("boadao").find("tbef_equip_repr_his_max",param);        
        	
        PosRow pr[] = keyRecord.getAllRow();     
       
        rtnKey = String.valueOf(pr[0].getAttribute("REPR_SEQ_MAX"));

        return rtnKey;
    }
        
    /**
     * <p> 출고일련번호 생성 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected String getMaxDelvSeq(PosRecord record) 
    {
        String stndYear = record.getAttribute("STND_YEAR").toString();
        String reprSeq  = record.getAttribute("REPR_SEQ").toString();
    	
        String rtnKey = "";
        
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, stndYear);	//기준년도        
        param.setWhereClauseParameter(i++, reprSeq);	//정비일련번호        
        param.setWhereClauseParameter(i++, stndYear);	//기준년도        
        
        PosRowSet keyRecord = this.getDao("boadao").find("tbef_equip_repr_his_delv_max",param);        
        	
        PosRow pr[] = keyRecord.getAllRow();     
        
        // 자료가 없으면 테이블에 신규로 추가
        if (pr[0].getAttribute("CUR_SEQ").toString().trim().equals("0")) {
        	rtnKey = String.valueOf(pr[0].getAttribute("NEW_SEQ"));
        } else {
        	rtnKey = String.valueOf(pr[0].getAttribute("CUR_SEQ"));
        } 

        return rtnKey;
    }
        
    /**
     * <p> 출고부품 등록 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
    protected int MergeTbefDelv(PosRecord record, String sDelvSeq) 
    {			           
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));       // 0.기준년도
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));  		 // 1.경정장코드
        param.setValueParamter(i++, sDelvSeq);       					     // 2.출고번호
        param.setValueParamter(i++, record.getAttribute("REPR_DT"));         // 3.출고일자
        param.setValueParamter(i++, record.getAttribute("MJR_PARTS")+" ("+record.getAttribute("REPR_DESC")+")");       // 4.출고내용
        param.setValueParamter(i++, record.getAttribute("RECEPT_ID"));      	 // 5.수령자
        param.setValueParamter(i++, SESSION_USER_NM);          			     // 6.반출자(입력자명)	
        param.setValueParamter(i++, SESSION_USER_ID);                        // 7.입력자사번
        param.setValueParamter(i++, record.getAttribute("REPR_SEQ"));        // 4.정비일련번호
                
        int dmlcount = this.getDao("boadao").update("tbef_equip_repr_his_delv_merg", param);
        
        return dmlcount;
    }
    
    /**
     * <p> 부품테이블 현재고 Update </p>
     * @param   record	 PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount int		update 레코드 개수
     * @throws  none
     */
    protected int updatePartsNowUnitCnt(PosRecord record, String recordTpe)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, recordTpe); 
        param.setValueParamter(i++, record.getAttribute("DELV_CNT")); 
    	param.setValueParamter(i++, record.getAttribute("DELV_CNT")); 
    	param.setValueParamter(i++, record.getAttribute("ORG_DELV_CNT")); 
    	param.setValueParamter(i++, SESSION_USER_ID);
    	param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
    	param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        param.setValueParamter(i++, record.getAttribute("PARTS_CD"));
        param.setValueParamter(i++, record.getAttribute("PARTS_YEAR"));
	        
        return this.getDao("boadao").update("tbef_delv_parts_uf702", param);
    }
    
}
