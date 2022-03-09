/*================================================================================
 * �ý���			: ������������
 * �ҽ����� �̸�	: snis.rbm.business.rsm5010.activity.SavePayCntnt
 * ���ϼ���		: ������������_�󼼳��� ����
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-10-07
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rsm5040.activity;

import java.util.ArrayList;
import java.util.HashMap;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.common.util.FileReader;
import snis.rbm.common.util.RbmJdbcDao;

public class SaveDbFile extends SnisActivity {
	
	public SaveDbFile(){}

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
    	String sFilePath = (String)ctx.get("FILE_PATH");	//�������
    	String sPayYear  = (String)ctx.get("PAY_YEAR");		//���޳⵵
    	
    	if( getCfmCheck(sPayYear) == -1) {
    		Util.setSvcMsgCode(ctx, "SNIS_RBM_D008");
    		return;
    	}
    	
    	ArrayList list = new ArrayList();
    	FileReader File = new FileReader();
    	list = File.executeInterface(sFilePath);

    	//��������
    	deletePayCntnt(sPayYear);
    	System.out.println( list.size() );
    	
    	int nSaveCnt = insertPayCntnt(list, sPayYear); 
    	
    	if(nSaveCnt < 0) {
    		try { 
    			throw new Exception(); 
        	} catch(Exception e) {
        		this.rollbackTransaction("tx_rbm");
        		Util.setSvcMsgCode(ctx, "SNIS_RBM_D007");
        		
        		return;
        	} 
    	}
    	
    	int nUpdCnt = updatePayCntntGreen(sPayYear); 
    	if(nUpdCnt < 0) {
    		try { 
    			throw new Exception(); 
        	} catch(Exception e) {
        		this.rollbackTransaction("tx_rbm");
        		Util.setSvcMsgCode(ctx, "SNIS_RBM_D021");
        		
        		return;
        	} 
    	}
    	
    	Util.setSaveCount(ctx, nSaveCnt);
    }
    
    /**
     * <p> ������������ �߰� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertPayCntnt(ArrayList list, String sPayYear) 
    {
    	RbmJdbcDao rbmdao  = this.getRbmDao("rbmjdbcdao");
    	String[] sArrMap   = new String[list.size()];
    	int[] insertCounts = null;
    	StringBuffer sb = new StringBuffer();
    	
    	String sCommCd, sDivNo, sEtc;
    	
    	//������ �о�鿩 DB INSERT
    	for(int i=0; i < list.size();i++) {
    		
    		HashMap map = (HashMap)list.get(i);
    		System.out.println( map );
			int iWinAmt 	= Util.NVL(map.get("column12"),0) + Util.NVL(map.get("column19"),0) + Util.NVL(map.get("column26"),0) + Util.NVL(map.get("column33"),0) + Util.NVL(map.get("column40"),0) + Util.NVL(map.get("column47"),0);
			int iJegeupAmt 	= Util.NVL(map.get("column13"),0) + Util.NVL(map.get("column20"),0) + Util.NVL(map.get("column27"),0) + Util.NVL(map.get("column34"),0) + Util.NVL(map.get("column41"),0) + Util.NVL(map.get("column48"),0);
			int iGita1 		= Util.NVL(map.get("column14"),0) + Util.NVL(map.get("column21"),0) + Util.NVL(map.get("column28"),0) + Util.NVL(map.get("column35"),0) + Util.NVL(map.get("column42"),0) + Util.NVL(map.get("column49"),0);
			int iGita2		= Util.NVL(map.get("column15"),0) + Util.NVL(map.get("column22"),0) + Util.NVL(map.get("column29"),0) + Util.NVL(map.get("column36"),0) + Util.NVL(map.get("column43"),0) + Util.NVL(map.get("column50"),0);
			int iGitaPay 	= Util.NVL(map.get("column16"),0) + Util.NVL(map.get("column23"),0) + Util.NVL(map.get("column30"),0) + Util.NVL(map.get("column37"),0) + Util.NVL(map.get("column44"),0) + Util.NVL(map.get("column51"),0);
			String 	sDetail = Util.NVL(map.get("column18"),"")+ Util.NVL(map.get("column25"),"")+ Util.NVL(map.get("column32"),"")+ Util.NVL(map.get("column39"),"")+ Util.NVL(map.get("column46"),"")+ Util.NVL(map.get("column53"),"");
    		//���� ���߱�    
    		//column3(������ȣ)   => ���ڸ��� ��� ���ڸ��� '0' �߰� 
    		//column4(��ǥ����ȣ) => ���ڸ��� ��� ���ڸ��� '0' �߰� 
    		//column14(��Ÿ)     => "\" ����
    		sCommCd = (String)map.get("column"+5);
    		if(sCommCd.length() == 1)	sCommCd = "0" + sCommCd;
    		sDivNo = (String)map.get("column"+6);
    		if(sDivNo.length() == 1)	sDivNo = "0" + sDivNo;
    		sEtc = sDetail;
    		sEtc.replace("\"", "");

    		sb.delete(0, sb.length()); // StringBuffer ����

    		sb.append(" MERGE INTO TBRD_PAY_CNTNT  DST ");
    		sb.append(" USING (                     ");
    		sb.append("    SELECT  '0" +  (String)map.get("column"+0)   +"' AS SELL_CD " 		);
    		sb.append("           ,'00"+  (String)map.get("column"+1)   +"' AS MEET_CD " 		);
    		sb.append("           ,'"   + (String)map.get("column"+2)  	+"' AS PERF_NO " 		);
    		sb.append("           ,'"   + sCommCd  						+"' AS BRNC_CD " 		);
    		sb.append("           ,'"   + sDivNo   						+"' AS DIV_NO " 		);
    		sb.append("           ,'"   + (String)map.get("column"+7)  	+"' AS REFUND_AMT " 	);
    		sb.append("           ,'"   + (String)map.get("column"+8)  	+"' AS SELL_AMT " 		);
    		sb.append("           ,'"   + (String)map.get("column"+9)  	+"' AS TSN_NO " 		);
    		sb.append("           ,'"   + iJegeupAmt                   	+"' AS PAY_AMT " 		);
    		sb.append("           ,'"   + iWinAmt  						+"' AS COST " 			);
    		sb.append("           ,'"   + iGita1 						+"' AS INCO_TAX " 		);
    		sb.append("           ,'"   + iGita2 						+"' AS INHA_TAX " 		);
    		sb.append("           ,'"   + iGitaPay 						+"' AS DIF_PAY_AMT " 	);
    		sb.append("           ,FC_ENC('"   +  (String)map.get("column"+10) +"') AS RES_NO " );
    		sb.append("           ,'"   + sEtc     						+"' AS ETC " 			);
    		sb.append("           ,'"   + sPayYear 						+"' AS PAY_YEAR " 		);
    		sb.append("           ,'"   + SESSION_USER_ID 				+"' AS INST_ID " 		);
    		sb.append("           , SYSDATE                                 AS INST_DT " 		);
    		sb.append("    FROM DUAL " 															);
    		sb.append("   ) SRC "																);
    		sb.append("   ON ( "																);
    		sb.append("          DST.SELL_CD  = SRC.SELL_CD  	"								);
    		sb.append("      AND DST.MEET_CD  = SRC.MEET_CD  	"								);
    		sb.append("      AND DST.TSN_NO   = SRC.TSN_NO  	"								);
    		sb.append("      AND DST.PAY_YEAR = SRC.PAY_YEAR  	"								);
    		sb.append("   )  "																	);
    		sb.append("   WHEN  MATCHED THEN  "													);
    		sb.append("         UPDATE SET  "													);
    		sb.append("             DST.PERF_NO     = SRC.PERF_NO  	" 	);
    		sb.append("           , DST.BRNC_CD     = SRC.BRNC_CD  	" 	);
    		sb.append("           , DST.DIV_NO      = SRC.DIV_NO  	" 	);
    		sb.append("           , DST.REFUND_AMT  = SRC.REFUND_AMT" 	);
    		sb.append("           , DST.SELL_AMT    = SRC.SELL_AMT  " 	);
    		sb.append("           , DST.PAY_AMT     = SRC.PAY_AMT  	" 	);
    		sb.append("           , DST.COST        = SRC.COST  	" 	);
    		sb.append("           , DST.INCO_TAX    = SRC.INCO_TAX  " 	);
    		sb.append("           , DST.INHA_TAX    = SRC.INHA_TAX  " 	);
    		sb.append("           , DST.DIF_PAY_AMT = SRC.DIF_PAY_AMT" 	);
    		sb.append("           , DST.RES_NO      = SRC.RES_NO  	" 	);
    		sb.append("           , DST.ETC         = SRC.ETC  		" 	);
    		sb.append("   WHEN  NOT MATCHED THEN  	" );
    		sb.append("         INSERT (  			" );
    		sb.append("              SELL_CD        " );
    		sb.append("             ,MEET_CD        " );
    		sb.append("             ,PERF_NO        " );
    		sb.append("             ,BRNC_CD        " );
    		sb.append("             ,DIV_NO         " );
    		sb.append("             ,REFUND_AMT     " );
    		sb.append("             ,SELL_AMT       " );
    		sb.append("             ,TSN_NO         " );
    		sb.append("             ,PAY_AMT        " );
    		sb.append("             ,COST           " );
    		sb.append("             ,INCO_TAX       " );
    		sb.append("             ,INHA_TAX       " );
    		sb.append("             ,DIF_PAY_AMT    " );
    		sb.append("             ,RES_NO         " );
    		sb.append("             ,ETC            " );
    		sb.append("             ,PAY_YEAR       " );
    		sb.append("             ,INST_ID        " );
    		sb.append("             ,INST_DT        " ); 
    		sb.append("          ) VALUES (  		" );
    		sb.append("              SRC.SELL_CD        " );
    		sb.append("             ,SRC.MEET_CD        " );
    		sb.append("             ,SRC.PERF_NO        " );
    		sb.append("             ,SRC.BRNC_CD        " );
    		sb.append("             ,SRC.DIV_NO         " );
    		sb.append("             ,SRC.REFUND_AMT     " );
    		sb.append("             ,SRC.SELL_AMT       " );
    		sb.append("             ,SRC.TSN_NO         " );
    		sb.append("             ,SRC.PAY_AMT        " );
    		sb.append("             ,SRC.COST           " );
    		sb.append("             ,SRC.INCO_TAX       " );
    		sb.append("             ,SRC.INHA_TAX       " );
    		sb.append("             ,SRC.DIF_PAY_AMT    " );
    		sb.append("             ,SRC.RES_NO         " );
    		sb.append("             ,SRC.ETC            " );
    		sb.append("             ,SRC.PAY_YEAR       " );
    		sb.append("             ,SRC.INST_ID        " );
    		sb.append("             ,SRC.INST_DT        " );
    		sb.append("          )  "                     );
 		
    		sArrMap[i] = sb.toString();
    	}
    	
    	int nRtn     = 0;
    	int nFailCnt = 0;
    	
    	System.out.println("executeBatch Before");
    	insertCounts = rbmdao.executeBatch( sArrMap );
    	System.out.println("executeBatch After");
		for (int i = 0; i < insertCounts.length; i++) {
			if (insertCounts[i] == -3) {
				nFailCnt++;
			} else {
				nRtn += insertCounts[i];
			}
		}
		
		if( nFailCnt > 0 ) {
			return nFailCnt*-1; 
		}
		
    	return nRtn;
    }
    
    /**
     * <p> ������������ ���� (�׸�ī�� ���� �и�) </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updatePayCntntGreen(String sPayYear) 
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, SESSION_USER_ID					 	);		// 1. ������ID
        param.setValueParamter(i++, sPayYear);	//���޳⵵
        
        int updcount = this.getDao("rbmdao").update("rsm5040_u02", param);

        return updcount;
    }
    
    /**
     * <p> Ȯ������ �˻�  </p>
     * @param   PosGenericDao dao			DB Connect ����
     *          String        sAttFileKey	FileKey
     * @return  nRtnKey	int			        sAttFileKey�� ���� ÷������ ����
     * @throws  none
     */
	public int getCfmCheck(String sPayYear)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        int nRtnKey = -1;
        
        param.setWhereClauseParameter(i++, sPayYear);	//���޳⵵
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm5040_s05", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        if( pr.length > 1) {
        	return -1;	//Ȯ���� ��Ȯ���� �������� ���
        } else if( pr.length == 0) {
        	return 0;	
        }
        
        String sRtnKey = String.valueOf(pr[0].getAttribute("SND_CFM_CD"));
        
        if( sRtnKey.equals("001")) {
        	nRtnKey = -1;	//Ȯ��
        } else {
        	nRtnKey = 0;	//��Ȯ��
        }
        
        return nRtnKey;
    }
	
    /**
     * <p> �Է¹��� �⵵�� ������������ ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deletePayCntnt(String sPayYear) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, sPayYear);	//���޳⵵
        
        int dmlcount = this.getDao("rbmdao").update("rsm5040_d01", param);

        return dmlcount;
    }
}
