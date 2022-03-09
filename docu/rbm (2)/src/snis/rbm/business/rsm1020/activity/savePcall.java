/*================================================================================
 * �ý���			: LIS FILE ���ε�
 * �ҽ����� �̸�	: snis.rbm.business.rsm1010.activity.SavePcall.java
 * ���ϼ���		: sp_IMPORT_PC_* ����
 * �ۼ���			: ���ֿ�
 * ����			: 1.0.0
 * ��������		: 2017-11-02
 * ������������	: 
 * ����������		: 
 * ������������	: 
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
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */
	// dao ����
	public static String rbmdao = "rbmdao"; // ������νý���
		
	String MEETCD = "";
	String YEAR = "";
    public String runActivity(PosContext ctx)
    {	   	
    	// ����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
	        
    	savePcall(ctx);
    	
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
    * @param   ctx		PosContext	�����
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
		
		// ������ ���ε� �ߴ��� Ȯ�ο�(���� ����, ���� ���� �� �� ���)
		String strFILE_VERI = "002";
		
		// ���� ���ε� ���� ����
		String sDsName = "";
		String sRtnVeri = "002";

		// ���� ���� ����
		SaveVeri objVeri = new SaveVeri(ctx, this.getRbmDao("rbmjdbcdao"),
				this.getDao(rbmdao), SESSION_USER_ID);

		// 6.1 1�� ���� ���� ����(TBES_SDL_DT,TBES_SDL_PT)
		String sVeriDTPTFst = objVeri.strVeriDTPTFst();
		System.out.println("sVeriDTPTFst====="+sVeriDTPTFst);

		// 6.2 1�� ���� ���� ���� (TBES_SDL_PT,TBJI_PC_PAYOFFS)
		String sVeriPTPayoffFst = objVeri.strVeriPTPayoffFst();
		System.out.println("sVeriPTPayoffFst====="+sVeriPTPayoffFst);
		
		// 6.3 1�� ���� TBJI_PC_SATLIT VW_SELL_DEV@CYBETLINK / RACE_NO,POOL_CD ����
		String sVeriDTVwGrnSell = objVeri.strVeriDTVwGrnSell();
		System.out.println("sVeriDTVwGrnSell====="+sVeriDTVwGrnSell);
		
		// 6.4 1�� ���� TBJI_PC_SATLIT VW_SELL_DEV@CYBETLINK / COMM_NO ����
		String sVeriDTVwGrnSellDev = objVeri.strVeriDTVwGrnSellDev();
		System.out.println("sVeriDTVwGrnSell====="+sVeriDTVwGrnSellDev);
				
		// 6.6 2�� ���� SDL_DT SATLIT ����
		String sVeriDTSatlitSnd = objVeri.strVeriDTSatlitSnd();
		System.out.println("sVeriDTSatlitSnd====="+sVeriDTSatlitSnd);

		// 6.7 2�� ���� Ȯ��(SDL_DT TBJI_PC_SELLST)
		String sVeriDTSellstSnd = objVeri.strVeriDTSellstSnd();
		System.out.println("sVeriDTSellstSnd====="+sVeriDTSellstSnd);

		// 6.8 2�� ���� SDL_DT TBJI_PC_TELMP ����
		// TELMP���� �׸�ī�� �Ǹ�ó�� �и��� �ȵǾ�(���� SELL_CD='01') : �׸�ī��� SELL_CD='01'�� ������ ��(2014.04.03)
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

		// 6.3 ���� ����, 1�� ���� ���� �Է�
		objVeri.insertVeriFstPcall(sMeetCd, SESSION_USER_ID, strFILE_VERI,
				sFstVeri, sSndVeri, sVeriDTPTPayoffFst);

		sRtnVeri = sVeriDTPTPayoffFst;
		System.out.println("sRtnVeri====="+sRtnVeri);

		Util.setReturnParam(ctx, "FILE_VERI", strFILE_VERI);
		Util.setReturnParam(ctx, "RESULT", "T"); // ���
		Util.setReturnParam(ctx, "RESULT_VERI", sRtnVeri); // ���
    }
    
}
