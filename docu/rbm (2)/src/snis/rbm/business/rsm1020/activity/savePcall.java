/*================================================================================
 * 시스템			: LIS FILE 업로드
 * 소스파일 이름	: snis.rbm.business.rsm1010.activity.SavePcall.java
 * 파일설명		: sp_IMPORT_PC_* 저장
 * 작성자			: 서주원
 * 버전			: 1.0.0
 * 생성일자		: 2017-11-02
 * 최종수정일자	: 
 * 최종수정자		: 
 * 최종수정내용	: 
=================================================================================*/
package snis.rbm.business.rsm1020.activity;

import java.sql.CallableStatement;
import java.sql.Connection;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.miplatform.vo.PosDataset;

public class savePcall extends SnisActivity {
	public savePcall(){}
	
	/**
     * <p> SaveStates Activity를 실행시키기 위한 메소드 </p>
     * @param   ctx		PosContext	저장소
     * @return  SUCCESS	String		sucess 문자열
     * @throws  none
     */
	// dao 세팅
	public static String rbmdao = "rbmdao"; // 경륜내부시스템
		
	String MEETCD = "";
	String YEAR = "";
    public String runActivity(PosContext ctx)
    {	   	
    	// 사용자 정보 확인
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
	        
    	savePcall(ctx);
    	
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> 하나의 데이타셋을 가져와 한 레코드씩 looping하면서 DML 메소드를 호출하기 위한 메소드 </p>
    * @param   ctx		PosContext	저장소
    * @return  none
    * @throws  none
    */
    protected void savePcall(PosContext ctx) 
    {
    	Connection conn = null;
        CallableStatement proc =  null;
        String sRaceDay = (String) ctx.get("RACE_DAY");
        String sMeetCd= (String) ctx.get("MEET_CD");
        String sDt = (String) ctx.get("DT");
        System.out.println("sRaceDay==="+sRaceDay);
		try { 
			int i = 1;
	
			conn = this.getDao("rbmdao").getDBConnection();
			proc = conn.prepareCall("{call SP_IMPORT_PC_ALL(?)}");
			proc.setString(i++, sRaceDay);
			proc.execute();
			
		} catch (Exception e) {
			e.printStackTrace(); 
		} finally{
			//try {if(conn!=null) conn.close();} catch(Exception e) {} 
		}  
		
		// 파일을 업로드 했는지 확인용(검증 성공, 실패 구분 할 때 사용)
		String strFILE_VERI = "002";
		
		// 파일 업로드 검증 수정
		String sDsName = "";
		String sRtnVeri = "002";

		// 파일 검증 실행
		SaveVeri objVeri = new SaveVeri(ctx, this.getRbmDao("rbmjdbcdao"),
				this.getDao(rbmdao), SESSION_USER_ID);

		// 6.1 1차 검증 검증 사항(TBES_SDL_DT,TBES_SDL_PT)
		String sVeriDTPTFst = objVeri.strVeriDTPTFst();
		System.out.println("sVeriDTPTFst====="+sVeriDTPTFst);

		// 6.2 1차 검증 검증 사항 (TBES_SDL_PT,TBJI_PC_PAYOFFS)
		String sVeriPTPayoffFst = objVeri.strVeriPTPayoffFst();
		System.out.println("sVeriPTPayoffFst====="+sVeriPTPayoffFst);
		
		// 6.3 1차 검증 TBJI_PC_SATLIT VW_SELL_DEV@CYBETLINK / RACE_NO,POOL_CD 검증
		String sVeriDTVwGrnSell = objVeri.strVeriDTVwGrnSell();
		System.out.println("sVeriDTVwGrnSell====="+sVeriDTVwGrnSell);
		
		// 6.4 1차 검증 TBJI_PC_SATLIT VW_SELL_DEV@CYBETLINK / COMM_NO 검증
		String sVeriDTVwGrnSellDev = objVeri.strVeriDTVwGrnSellDev();
		System.out.println("sVeriDTVwGrnSell====="+sVeriDTVwGrnSellDev);
				
		// 6.6 2차 검증 SDL_DT SATLIT 검증
		String sVeriDTSatlitSnd = objVeri.strVeriDTSatlitSnd();
		System.out.println("sVeriDTSatlitSnd====="+sVeriDTSatlitSnd);

		// 6.7 2차 검증 확인(SDL_DT TBJI_PC_SELLST)
		String sVeriDTSellstSnd = objVeri.strVeriDTSellstSnd();
		System.out.println("sVeriDTSellstSnd====="+sVeriDTSellstSnd);

		// 6.8 2차 검증 SDL_DT TBJI_PC_TELMP 검증
		// TELMP에는 그린카드 판매처별 분리가 안되어(전부 SELL_CD='01') : 그린카드는 SELL_CD='01'로 설정후 비교(2014.04.03)
		String sVeriDTTelmpSnd = objVeri.strVeriDTTelmpSnd();
		System.out.println("sVeriDTTelmpSnd====="+sVeriDTTelmpSnd);
		
		
		String sVeriDTPTPayoffFst = "002";
		String sFstVeri = "002";
		String sSndVeri = "002";
		String sGrnSell = "002";
		
		if ("001".equals(sVeriDTPTFst) 
				&& "001".equals(sVeriPTPayoffFst) 
				&& "001".equals(sVeriDTVwGrnSell)
				&& "001".equals(sVeriDTVwGrnSellDev)) {
			sFstVeri = "001";
		}
		
		if ("001".equals(sVeriDTSatlitSnd)
				&& "001".equals(sVeriDTSellstSnd)
				&& "001".equals(sVeriDTTelmpSnd) ) {
			sSndVeri = "001";
		}
		
		if ("001".equals(sVeriDTPTFst) && "001".equals(sVeriPTPayoffFst)
				&& "001".equals(sVeriDTSatlitSnd)
				&& "001".equals(sVeriDTSellstSnd)
				&& "001".equals(sVeriDTTelmpSnd)
				&& "001".equals(sVeriDTVwGrnSell)
				&& "001".equals(sVeriDTVwGrnSellDev)) {
			sVeriDTPTPayoffFst = "001";
		}

		// 6.3 파일 검증, 1차 검증 검증 입력
		objVeri.insertVeriFstPcall(sMeetCd, SESSION_USER_ID, strFILE_VERI,
				sFstVeri, sSndVeri, sVeriDTPTPayoffFst);

		sRtnVeri = sVeriDTPTPayoffFst;
		System.out.println("sRtnVeri====="+sRtnVeri);

		Util.setReturnParam(ctx, "FILE_VERI", strFILE_VERI);
		Util.setReturnParam(ctx, "RESULT", "T"); // 결과
		Util.setReturnParam(ctx, "RESULT_VERI", sRtnVeri); // 결과
    }
    
}
