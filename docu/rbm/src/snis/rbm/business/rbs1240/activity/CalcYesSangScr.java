/*================================================================================
 * �ý���			: ������ ���߷� ����
 * �ҽ����� �̸�	: snis.rbm.business.rbs1210.activity.SaveYesCom.java
 * ���ϼ���		: ������ ����
 * �ۼ���			: ���缱
 * ����			: 1.0.0
 * ������			: ���⵷ (��Ʈ����� RaceByDAO.java)
 * ��������		: 2014-09-06
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbs1240.activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;

public class CalcYesSangScr extends SnisActivity {
		static final int _AB  = 6;
		static final int _BA  = 3;
		static final int _AC  = 3;
		static final int _ABC = 3;
		static final int _EX_A1 = 6;
		static final int _EX_A2 = 3;
		static final int _EX_A3 = 2;
		static final int _EX_AB1 = 6;
		static final int _EX_AB2 = 3;
		static final int _EX_AB3 = 2;
		static final int _EX_AC1 = 3;
		static final int _EX_AC2 = 2;
		static final int _EX_AC3 = 1;
		static final int _EX_BA1 = 3;
		static final int _EX_BA2 = 2;
		static final int _EX_BA3 = 1;
		static final int _RT_AB10 = 10;
		static final int _RT_AB20 = 15;
		static final int _RT_AB30 = 20;
		static final int _RT_BA10 = 5;
		static final int _RT_BA20 = 10;
		static final int _RT_BA30 = 15;
		
		public CalcYesSangScr(){}
		
		/**
	     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
	     * @param   ctx		PosContext	�����
	     * @return  SUCCESS	String		sucess ���ڿ�
	     * @throws  none
	     */    
	    public String runActivity(PosContext ctx)
	    {	   	
	    	// ����� ���� Ȯ��
	    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
	    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
	            return PosBizControlConstants.SUCCESS;
	    	}
		        
	        saveState(ctx);

	        return PosBizControlConstants.SUCCESS;
	    }

	    /**
	    * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
	    * @param   ctx		PosContext	�����
	    * @return  none
	    * @throws  none
	    */
	    protected void saveState(PosContext ctx) 
	    {
	    	int nSaveCount   = 0; 
	    	int nDeleteCount = 0;
	    	String sRet = "";
	    	    	

			ArrayList <PosRow> arrRaceRslt, arrYesSang;
	        
	        /* ó������
	         1. ��ȸ ���ǿ� �ش��ϴ� ���ְ���� ��ȸ�Ѵ�.(����, ��/�ֽ� ����)
	         2. ��ȸ ���ǿ� �ش��ϴ� ������ü�� ���󳻿��� ��ȸ�Ѵ�.
	         3. ���ֺ��� LOOP : ���ֺ��� ������ �����Ѵ�.
	           3-1) ��ü���� ���������� ���
	           3-2) �½ĺ� ���߾�ü�� ī��Ʈ �Ѵ�.
	           3-3) �½ĺ� ���߾�ü���� ���� ���������� ���
	           3-4) ������� ���� �������� ���
	         4.���� ������ DB�� �����Ѵ�.
	        */   
			arrRaceRslt = selectRaceInfo(ctx);		// ���ְ�� ��ȸ
			arrYesSang  = selectYesSang(ctx);		// ������ ���󳻿� ��ȸ
			
			sRet = calcScrYesSAng(ctx, arrRaceRslt, arrYesSang);	// �������� ���
			
			if (sRet.equals(PosBizControlConstants.SUCCESS)) {
				nSaveCount = SaveCalcResult(ctx, arrYesSang);		// ��� ��� ����
			}
			
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    

		/**
		* <p> ���ְ�� ��ȸ </p>
		* @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
		* @return  dmlcount int		update ���ڵ� ����
		* @throws  none
		*/
		protected ArrayList <PosRow> selectRaceInfo(PosContext ctx)
		{			

	    	String sMeetCd   = (String)ctx.get("MEET_CD");
	    	String sStndYear = (String)ctx.get("STND_YEAR");
	    	String sTmsFrom  = (String)ctx.get("TMS_FROM");
	    	String sTmsTo    = (String)ctx.get("TMS_TO");	
	    	
	    	PosRowSet rowset;
	    	PosParameter param = new PosParameter();
	        int i = 0;

			i = 0;
			param.setWhereClauseParameter(i++, sMeetCd);
			param.setWhereClauseParameter(i++, sStndYear);
			param.setWhereClauseParameter(i++, sTmsFrom);
			param.setWhereClauseParameter(i++, sTmsTo);

			rowset = this.getDao("rbmdao").find("rbs1240_s02", param);
			
			ArrayList <PosRow> arrRslt = new ArrayList<PosRow>();				
			while(rowset.hasNext())
			{
				PosRow row = rowset.next();
				arrRslt.add(row);
			}
	        return arrRslt;
	    }


	    /**
	     * <p> ���󳻿� ��ȸ </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected  ArrayList <PosRow> selectYesSang(PosContext ctx)
	    {			

	    	String sMeetCd   = (String)ctx.get("MEET_CD");
	    	String sStndYear = (String)ctx.get("STND_YEAR");
	    	String sTmsFrom  = (String)ctx.get("TMS_FROM");
	    	String sTmsTo    = (String)ctx.get("TMS_TO");	
	    	
	    	PosRowSet rowset;
	    	PosParameter param = new PosParameter();
	        int i = 0;

			i = 0;
			param.setWhereClauseParameter(i++, sMeetCd);
			param.setWhereClauseParameter(i++, sStndYear);
			param.setWhereClauseParameter(i++, sTmsFrom);
			param.setWhereClauseParameter(i++, sTmsTo);

			rowset = this.getDao("rbmdao").find("rbs1240_s03", param);

			ArrayList <PosRow> arrYeSang = new ArrayList<PosRow>();				
			while(rowset.hasNext())
			{
				PosRow row = rowset.next();
				arrYeSang.add(row);
			}
	        return arrYeSang;
	    }

	    /**
	     * <p> ���ֺ� ���� ��� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected String calcScrYesSAng(PosContext ctx, ArrayList <PosRow> arrRslt, ArrayList <PosRow> arrYeSang)
	    {			
	    	/*
	         3. ���ֺ��� LOOP : ���ֺ��� ������ �����Ѵ�.
	           3-0) ��� ���� ���� üũ
	           3-1) ��ü���� ���������� ��� �� ���߾�ü ī��Ʈ
	           3-2) �½ĺ� ���߾�ü�� �� ������� ���� �������� ���
	        */
		       
	    	String arrRank[] = new String[3];	// ���� ����
	    	int    arrHitCnt[] = new int[4];	// ���߾�ü���� ����
	    	
	    	float  nExRate, nQuRate;	// �ֽ� �� ������ ����
	    	PosRow rowRslt, rowYeSang;
	    	String sFuncResult;		// �Լ��� �����
	    	String sHitCnt = "";	//��ü�� ���߰Ǽ��� ���ڿ��� ����
	    	String sExclusion = "N";
	    	
	    	for (int iCnt=0;iCnt<arrRslt.size();iCnt++) {		// ���ֺ��� LOOP ���鼭 ������ �����  ********************************************
	    	
	    		clearStr(arrRank);		// ���ڿ� �迭���� �ʱ�ȭ
	    		clearint(arrHitCnt);	// ����    �迭���� �ʱ�ȭ
	    		
	    		rowRslt = arrRslt.get(iCnt);
	    		
	    		// 3-0) ��� ���� ���� üũ
	    		sFuncResult = checkRaceResult(ctx, rowRslt); 	// ���� üũ(���� ������ �ڷ� ����)
	    		if (PosBizControlConstants.FAILURE.equals(sFuncResult)) return PosBizControlConstants.FAILURE;	// ��� ������ ����
	    		
	    		arrRank[0] = rowRslt.getAttribute("RANK_1").toString();						// 1�� ����
	    		arrRank[1] = rowRslt.getAttribute("RANK_2").toString();						// 2�� ����
	    		arrRank[2] = rowRslt.getAttribute("RANK_3").toString();						// 3�� ����
	    		nExRate = Float.parseFloat(rowRslt.getAttribute("EX_RATE").toString());		// �ֽ� ����
	    		nQuRate = Float.parseFloat(rowRslt.getAttribute("QU_RATE").toString());		// ���� ����
	    		
	    		// ������ҿ��� ..���� ���� üũ
	    		if (nExRate == 1.0 && arrRank[0] == null) sExclusion = "Y";
	    		else                                      sExclusion = "N";
	    		
	    		// 3-1) ��ü�� �������� ���
	    		for (int jCnt=0;jCnt<arrYeSang.size();jCnt++) {
	    			rowYeSang = arrYeSang.get(jCnt);
	    			if (rowRslt.getAttribute("MEET_CD").toString().equals(rowYeSang.getAttribute("MEET_CD").toString()) &&
    					rowRslt.getAttribute("STND_YEAR").toString().equals(rowYeSang.getAttribute("STND_YEAR").toString()) &&
    					rowRslt.getAttribute("TMS").toString().equals(rowYeSang.getAttribute("TMS").toString()) &&
    					rowRslt.getAttribute("DAY_ORD").toString().equals(rowYeSang.getAttribute("DAY_ORD").toString()) &&
    					rowRslt.getAttribute("RACE_NO").toString().equals(rowYeSang.getAttribute("RACE_NO").toString())     	) {
	    				
	    	    		initYesSangScore(rowYeSang);						// ��� ������ 0���� �ʱ�ȭ
	    	    		
	    				// �������� ��� �� ���߾�ü Counting
	    				setScore(ctx, arrRank, arrHitCnt, rowYeSang);
	    			}
	    		}
	    		
	    		sHitCnt = "";
	    		for (int i=0;i<arrHitCnt.length;i++) {			// ���߾�ü���� "2,2,1,2" ������ ���ڿ��� ��ȯ
	    			if (i > 0) sHitCnt = sHitCnt.concat(","); 
	    			sHitCnt = sHitCnt.concat(String.valueOf(arrHitCnt[i]));
	    		}
	    		
	    		//3-2) �½ĺ� ���߾�ü�� �� ������� ���� �������� ���
	    		for (int jCnt=0;jCnt<arrYeSang.size();jCnt++) {	
	    			rowYeSang = arrYeSang.get(jCnt);
	    			if (rowRslt.getAttribute("MEET_CD").toString().equals(rowYeSang.getAttribute("MEET_CD").toString()) &&
    					rowRslt.getAttribute("STND_YEAR").toString().equals(rowYeSang.getAttribute("STND_YEAR").toString()) &&
    					rowRslt.getAttribute("TMS").toString().equals(rowYeSang.getAttribute("TMS").toString()) &&
    					rowRslt.getAttribute("DAY_ORD").toString().equals(rowYeSang.getAttribute("DAY_ORD").toString()) &&
    					rowRslt.getAttribute("RACE_NO").toString().equals(rowYeSang.getAttribute("RACE_NO").toString())     	) {
	    				
	    				//3-2-1) �డ������ ���� ���
	    				System.out.println("dfkdjl EX_A:"+Integer.parseInt(rowYeSang.getAttribute("EX_A").toString()));	    				
	    				System.out.println("dfkdjl arrHitCnt[0]:"+arrHitCnt[0]);	    				
	    				if (Integer.parseInt(rowYeSang.getAttribute("EX_A").toString()) == 1.0) {
	    					if      (arrHitCnt[0] == 1) rowYeSang.setAttribute("EX_A", _EX_A1); 
	    					else if (arrHitCnt[0] == 2) rowYeSang.setAttribute("EX_A", _EX_A2);
	    					else if (arrHitCnt[0] == 3) rowYeSang.setAttribute("EX_A", _EX_A3);
	    				}
	    				//3-2-2) A��B ������ ���
	    				if (Integer.parseInt(rowYeSang.getAttribute("AB").toString()) > 0) {
	    					// ��ü���߼��� ���� ������
	    					if      (arrHitCnt[1] == 1) rowYeSang.setAttribute("EX_AB", _EX_AB1); 
	    					else if (arrHitCnt[1] == 2) rowYeSang.setAttribute("EX_AB", _EX_AB2);
	    					else if (arrHitCnt[1] == 3) rowYeSang.setAttribute("EX_AB", _EX_AB3);
	    					
	    					// ������ ���� ������
	    					if      (nExRate >= 10.0 && nExRate < 20.0) rowYeSang.setAttribute("RT_AB", _RT_AB10); 
	    					else if (nExRate >= 20.0 && nExRate < 30.0) rowYeSang.setAttribute("RT_AB", _RT_AB20); 
	    					else if (nExRate >= 30.0                  ) rowYeSang.setAttribute("RT_AB", _RT_AB30); 
	    					
	    				}
	    				//3-2-3) A��C ������ ���
	    				if (Integer.parseInt(rowYeSang.getAttribute("AC").toString()) > 0) {
	    					if      (arrHitCnt[2] == 1) rowYeSang.setAttribute("EX_AC", _EX_AC1); 
	    					else if (arrHitCnt[2] == 2) rowYeSang.setAttribute("EX_AC", _EX_AC2);
	    					else if (arrHitCnt[2] == 3) rowYeSang.setAttribute("EX_AC", _EX_AC3); 
	    				}
	    				//3-2-4) B��A ������ ���
	    				if (Integer.parseInt(rowYeSang.getAttribute("BA").toString()) > 0) {
	    					// ��ü���߼��� ���� ������
	    					if      (arrHitCnt[3] == 1) rowYeSang.setAttribute("EX_BA", _EX_BA1); 
	    					else if (arrHitCnt[3] == 2) rowYeSang.setAttribute("EX_BA", _EX_BA2);
	    					else if (arrHitCnt[3] == 3) rowYeSang.setAttribute("EX_BA", _EX_BA3); 
	    					
	    					// ������ ���� ������
	    					if      (nQuRate >= 10.0 && nExRate < 20.0) rowYeSang.setAttribute("RT_AB", _RT_BA10); 
	    					else if (nQuRate >= 20.0 && nExRate < 30.0) rowYeSang.setAttribute("RT_AB", _RT_BA20); 
	    					else if (nQuRate >= 30.0                  ) rowYeSang.setAttribute("RT_AB", _RT_BA30); 
	    				}
	    				
	    				// 
	    				rowYeSang.setAttribute("ETC",       sHitCnt);			//���߾�ü�� ����				
	    				rowYeSang.setAttribute("RATE",      nExRate);			//�ֽ� ����
	    				rowYeSang.setAttribute("RATE_QU",   nQuRate);			//���� ����
	    				rowYeSang.setAttribute("EXCLUSION", sExclusion);		//���� ����
	    				
	    			}
	    		}
	    	}				// ���ֺ��� LOOP End  ********************************************
	        return PosBizControlConstants.SUCCESS;
	    }

	    /**
	     * <p> �������� �� ������ �ʱ�ȭ </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected String initYesSangScore(PosRow row)
	    {	
	    	row.setAttribute("AB",    0);		//A��B
	    	row.setAttribute("AC",    0);		//A��C
	    	row.setAttribute("BA",    0);		//B��A
	    	row.setAttribute("ABC",   0);		//�ﺹ��
	    	row.setAttribute("EX_A",  0);		//A�� ������
	    	row.setAttribute("EX_AB", 0);		//A��B ������
	    	row.setAttribute("EX_AC", 0);		//A��C ������
	    	row.setAttribute("EX_BA", 0);		//B��A ������
	    	row.setAttribute("RT_AB", 0);		//A��B ���� ������
	    	row.setAttribute("RT_BA", 0);		//B��A ���� ������
	    	
	    	row.setAttribute("RATE",  0);		//�ֽ� ����
	    	row.setAttribute("RATE_QU", 0);		//���� ����
	    	row.setAttribute("ETC",  "");		//���߾�ü��
	    	row.setAttribute("EXCLUSION", "N");		//���� ����
	    	row.setAttribute("AUTO", "Y");		//���� ����
	    	
    		return PosBizControlConstants.SUCCESS;
	    }


	    /**
	     * <p> ���ְ�� �� ���� üũ </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected String checkRaceResult(PosContext ctx, PosRow row)
	    {		

	    	String sRank1, sRank2, sRank3;
	    	//float  nExRate, nQuRate;
	    	
    		sRank1 = row.getAttribute("RANK_1").toString();						// 1�� ����
    		sRank2 = row.getAttribute("RANK_2").toString();						// 2�� ����
    		sRank3 = row.getAttribute("RANK_3").toString();						// 3�� ����
    		//nExRate = Float.parseFloat(row.getAttribute("EX_RATE").toString());	// �ֽ� ����
    		//nQuRate = Float.parseFloat(row.getAttribute("QU_RATE").toString());	// ���� ����
    		
    		if ("-".equals(sRank1)) {	// ����� �Էµ��� �ʾ���
    			Util.setSvcMsgCode(ctx, "SNIS_RBM_E029");	// ���ְ�� ������
                return PosBizControlConstants.FAILURE;
    		}
    		if ("-".equals(sRank2)) {	//2�� ����
    			row.setAttribute("RANK_2", sRank1);
    			sRank2 = sRank1;
    		}
    		if ("-".equals(sRank3)) {	//3�� ����
    			row.setAttribute("RANK_3", sRank2);
    		}
    		
    		return PosBizControlConstants.SUCCESS;
	    }


	    /**
	     * <p> ��ü�� �������� ��� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected String setScore(PosContext ctx, String[] aRank, int[] aHitCnt, PosRow row)
	    {		
	    	String sYesang = "";	    	
	    	sYesang = row.getAttribute("YESANG").toString();
	    	
	    	if (sYesang == null || sYesang.length() < 4) {
	    		Util.setSvcMsgCode(ctx, "SNIS_RBM_E030");	// ���ְ�� ������
                return PosBizControlConstants.FAILURE;
	    	}
	    	String a = sYesang.substring(0, 1);
	    	String b = sYesang.substring(1, 2);
	    	String c = sYesang.substring(2, 3);
	    	
    		// A��B
	    	if (aRank[0].contains(a) && aRank[1].contains(b)) {
    			row.setAttribute("AB", _AB);
    			aHitCnt[1] ++;
    		} else if (aRank[0].contains(b) && aRank[1].contains(a)) {
    			row.setAttribute("BA", _BA);
    			aHitCnt[3] ++;
    		} else if (aRank[0].contains(a) && aRank[1].contains(c)) {
    			row.setAttribute("AC", _AC);
    			aHitCnt[2] ++;
    		}
	    	
	    	if ((aRank[0]+aRank[1]+aRank[2]).contains(a) && 
	    		(aRank[0]+aRank[1]+aRank[2]).contains(b) && 
	    		(aRank[0]+aRank[1]+aRank[2]).contains(c))   {
	    		row.setAttribute("ABC", _ABC);
	    	}

	    	if (aRank[0].contains(a)) {	// �� ���� ����
	    		row.setAttribute("EX_A", 1);
    			aHitCnt[0] ++;
	    	} 
    		
	    	return PosBizControlConstants.SUCCESS;
	    }

	    /**
	     * <p> ��������� ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int SaveCalcResult(PosContext ctx, ArrayList <PosRow> arrYesSang)
	    {		
	    	Connection conn = null;
	    	conn = getDao("rbmdao").getDBConnection();
	    	int i = 0;
	    	int dmlCount = 0;
	    	
	    	PreparedStatement stmt = null;
	    	String sqlStr = Util.getQuery(ctx, "rbs1240_u01");
	    	
	    	try {
	    		stmt = conn.prepareStatement(sqlStr);
	    		for (int rCnt=0;rCnt<arrYesSang.size();rCnt++) {
	    			PosRow row = arrYesSang.get(rCnt);
	    			stmt.clearParameters();
	    			i= 1;
	    			stmt.setObject(i++, row.getAttribute("AB").toString());
	    			stmt.setObject(i++, row.getAttribute("AC").toString());
	    			stmt.setObject(i++, row.getAttribute("BA").toString());
	    			stmt.setObject(i++, row.getAttribute("CA").toString());
	    			stmt.setObject(i++, row.getAttribute("ABC").toString());
	    			stmt.setObject(i++, row.getAttribute("EX_AB").toString());
	    			stmt.setObject(i++, row.getAttribute("EX_ABC").toString());
	    			stmt.setObject(i++, row.getAttribute("EX_AC").toString());
	    			stmt.setObject(i++, row.getAttribute("EX_A").toString());
	    			stmt.setObject(i++, row.getAttribute("AUTO").toString());
	    			stmt.setObject(i++, row.getAttribute("RATE").toString());
	    			stmt.setObject(i++, row.getAttribute("RATE_QU").toString());
	    			stmt.setObject(i++, row.getAttribute("ETC").toString());
	    			stmt.setObject(i++, row.getAttribute("EXCLUSION").toString());
	    			stmt.setObject(i++, row.getAttribute("EX_BA").toString());
	    			stmt.setObject(i++, row.getAttribute("RT_AB").toString());
	    			stmt.setObject(i++, row.getAttribute("RT_BA").toString());
	    			stmt.setObject(i++, SESSION_USER_ID);
	    			stmt.setObject(i++, row.getAttribute("YES_COM_SEQ").toString());
	    			stmt.setObject(i++, row.getAttribute("MEET_CD").toString());
	    			stmt.setObject(i++, row.getAttribute("STND_YEAR").toString());
	    			stmt.setObject(i++, row.getAttribute("TMS").toString());
	    			stmt.setObject(i++, row.getAttribute("DAY_ORD").toString());
	    			stmt.setObject(i++, row.getAttribute("RACE_NO").toString());
	    			
	    			stmt.addBatch();
	    		}
		    	stmt.executeBatch();
		    	dmlCount = stmt.getUpdateCount();
		    	stmt.close();
	    	} catch (SQLException e) {
	    		e.printStackTrace();
	    	}
	    	return dmlCount;
	    }
	    
	    // ���ڿ� �迭���� �ʱ�ȭ
	    private String[] clearStr(String a[]) {
	    	for (int i=0;i<a.length;i++) {
	    		a[i] = "";
	    	}
	    	return a;
	    }
	    // ���� �迭���� �ʱ�ȭ
	    private int[] clearint(int a[]) {
	    	for (int i=0;i<a.length;i++) {
	    		a[i] = 0;
	    	}
	    	return a;
	    }
}
