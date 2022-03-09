/*================================================================================
 * 시스템			: Results 파일 업로드
 * 소스파일 이름	: snis.rbm.business.rsm1020.activity.Results.java
 * 파일설명		: PC파일 업로드
 * 작성자			: 이기석
 * 버전			: 1.0.0
 * 생성일자		: 2011-10-05
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rsm1020.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import com.posdata.glue.util.log.PosLog;
import com.posdata.glue.util.log.PosLogFactory;

import snis.rbm.common.util.FileReader;
import snis.rbm.common.util.RbmJdbcDao;
import snis.rbm.common.util.Util;

public class Results {
	
	private PosContext ctx = null;
	private String sFilePath = "";
	private RbmJdbcDao rbmjdbcdao = null;
	private String user_id = "";
	public Results()
    {
    }
	
	public Results( PosContext ctx, String sFilePath, RbmJdbcDao rbmjdbcdao, String session_user_id){
		this.ctx = ctx;
		this.sFilePath = sFilePath;
		this.rbmjdbcdao = rbmjdbcdao;
		this.user_id = session_user_id;
		
	}
	
	public int insertResults(ArrayList aList){
		int intResult = 0; // 결과값
        String sStndYear = Util.getCtxStr(ctx, "STND_YEAR");
		String sSessionNo = Util.getCtxStr(ctx, "SESSION_NO");
		String sJigeupDt = Util.getCtxStr(ctx, "RACE_DAY");

//		ArrayList aList = new ArrayList();	// 원본 데이터용 ArrayList
//		FileReader File = new FileReader();	// 파일 데이터 읽는 클래스
//		aList = File.alReadFile(sFilePath);	// 파일 데이터 읽어들임
		
		// 파일을 읽어들여 DB INSERT
		String[] arrQuery = new String[aList.size()];
       
		HashMap hMap = null;
		for (int i = 0; i < aList.size(); i++) {

			hMap = (HashMap) aList.get(i);
          
	        	
	        	arrQuery[i] = " INSERT INTO TBJI_PC_RESULT (    /* Results.insertResults */      "+
	        	"      MEET_CD,								 "+ 
	        	"      STND_YEAR,								 "+
	        	"      TMS,								 "+
	        	"      DAY_ORD,								 "+
	        	"      SELL_CD,								 "+
	        	"      RACE_NO,								 "+
	        	"      POOL_CD,								 "+
	        	"      RLT_NUM,								 "+
	        	"      SESSION_NO,								 "+
	        	"      PERF_NO,									 "+		            
	        	"      RLT_RUN_NO,								 "+
	        	"      WIN_AMT,								 "+
	        	"      ROW_ODDS,								 "+
	        	"      UNIT_ODDS,								 "+
	        	"      INCE_TAX_RATE,								 "+
	        	"      INH_TAX_RATE,								 "+			            			            
	        	"      INST_ID,								 "+
	        	"      INST_DT,								 "+
	        	"      UPDT_ID,								 "+
	        	"      UPDT_DT          "+
	        	" 			) VALUES (             "+
	        	"  '"+SavePCFileUpload.searchMeet((String)hMap.get("column1"))+"' "+
	        	", '"+sStndYear+"' "+
	        	", '"+SavePCFileUpload.searchTms((String)hMap.get("column1"))+"' "+
	        	", '"+SavePCFileUpload.searchDay((String)hMap.get("column1"))+"' "+
	        	", '"+SavePCFileUpload.searchSell((String)hMap.get("column0"))+"' "+
	        	", '"+SavePCFileUpload.searchRaceNo((String)hMap.get("column3"))+"' "+
	        	", '"+"00"+hMap.get("column4")+"' "+
	        	", '"+hMap.get("column5")+"' "+
	        	", '"+sSessionNo+"' "+
	        	", '"+hMap.get("column2")+"' "+
	        	", '"+hMap.get("column6")+"' "+
	        	", '"+hMap.get("column7")+"' "+
	        	", '"+hMap.get("column8")+"' "+	        	
	        	", '"+hMap.get("column9")+"' "+
	        	", '"+hMap.get("column10")+"' "+
	        	", '"+hMap.get("column11")+"' "+
	        	", '"+user_id+"' "+
	        	", SYSDATE "+
	        	", '"+user_id+"' "+
	        	", SYSDATE ) ";            		    	  
        }
        
        
        int[] insertCounts = rbmjdbcdao.executeBatch( arrQuery );
		int failure_count = 0;
		
		for (int i = 0; i < insertCounts.length; i++){
			if (insertCounts[i] == -3){
				failure_count++;
			}
		}
		
		if(failure_count==0)
		{
			intResult = insertCounts.length;
		}
		else
		{
			intResult = 0;
		}
		
    	return intResult;
	}
	
	
	/**
     * <p> PC Results파일 입력 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
	public int insertResults(PosGenericDao dao, PosRecord record, String sStndYear, String UserID, String SessionNo) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, SavePCFileUpload.searchMeet((String)record.getAttribute("column1")));		//1.Meet(경기장코드)
        param.setValueParamter(i++, sStndYear);															//2.StndYear(기준년도)
        param.setValueParamter(i++, SavePCFileUpload.searchTms((String)record.getAttribute("column1")));		//3.TMS(회차)
        param.setValueParamter(i++, SavePCFileUpload.searchDay((String)record.getAttribute("column1")));		//4.DAY_ORD(일차)
        param.setValueParamter(i++, SavePCFileUpload.searchSell((String)record.getAttribute("column0")));		//5.SellCd(발매처)
        param.setValueParamter(i++, record.getAttribute("column3"));									//6.RACE_NO(경주번호)        
        param.setValueParamter(i++, "00"+record.getAttribute("column4"));								//7.POOL_CD(승식번호)
        param.setValueParamter(i++, record.getAttribute("column5"));									//8.RLT_NUM(승자(조합)수)
        param.setValueParamter(i++, SessionNo);															//9.SESSION_NO(세션번호)
        param.setValueParamter(i++, record.getAttribute("column2"));									//10.PERF_NO(퍼포먼스번호)        
        param.setValueParamter(i++, record.getAttribute("column6"));									//11.RLT_RUN_NO(승자번호)
        param.setValueParamter(i++, record.getAttribute("column7"));									//12.WIN_AMT(적중금액)
        param.setValueParamter(i++, record.getAttribute("column8"));									//13.ROW_ODDS(배당율)
        param.setValueParamter(i++, record.getAttribute("column9"));									//14.UNIT_ODDS(배당율(절사))
        param.setValueParamter(i++, record.getAttribute("column10"));									//15.INCE_TAX_RATE(소득세)
        param.setValueParamter(i++, record.getAttribute("column11"));									//16.INH_TAX_RATE(주민세)
        param.setValueParamter(i++, UserID);															//17.작성자ID
        param.setValueParamter(i++, UserID);															//18.수정자ID        
        
        int dmlcount = dao.update("rsm1020_i05", param);
        
        return dmlcount;
    }
    
    
    /**
     * <p> PC Results파일 삭제 </p>
     * @param   record	PosRecord	데이타셋에 대한 하나의 레코드
     * @throws  none
     */
	public void deleteResults(PosGenericDao dao, PosRecord record, String sStndYear)
    {
    	PosParameter param = new PosParameter();  
    	
        int i = 0;
        param.setWhereClauseParameter(i++, SavePCFileUpload.searchMeet((String)record.getAttribute("column1")));//1.Meet(경기장코드)
        param.setWhereClauseParameter(i++, sStndYear);													//2.StndYear(기준년도)
        param.setWhereClauseParameter(i++, SavePCFileUpload.searchTms((String)record.getAttribute("column1")));	//3.TMS(회차)
        param.setWhereClauseParameter(i++, SavePCFileUpload.searchDay((String)record.getAttribute("column1")));	//4.DAY_ORD(일차)
//        param.setWhereClauseParameter(i++, SavePcUp.searchSell((String)record.getAttribute("column0")));//5.SellCd(발매처)
//        param.setWhereClauseParameter(i++, record.getAttribute("column3"));								//6.RACE_NO(경주번호)        
//        param.setWhereClauseParameter(i++, "00"+record.getAttribute("column4"));						//7.POOL_CD(승식번호)
//        param.setWhereClauseParameter(i++, record.getAttribute("column5"));								//8.RLT_NUM(승자(조합)수)
        
        dao.update("rsm1020_d05", param);										        
    }
}
