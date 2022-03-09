/*================================================================================
 * �ý���			: ��Ÿ�ҵ漼����
 * �ҽ����� �̸�	: snis.rbm.business.rsm5060.activity.SaveDbGitaFile
 * ���ϼ���		: ������������_��Ÿ�ҵ漼���� ����
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-10-14
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rsm5060.activity;

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

import snis.rbm.common.util.RbmJdbcDao;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.common.util.FileReader;

public class SaveDbGitaFile extends SnisActivity {
	
	public SaveDbGitaFile(){}

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

    protected void saveState(PosContext ctx) 
    {  	
    	String sFilePath = (String)ctx.get("FILE_PATH");	//�������
    	String sPayYear  = (String)ctx.get("PAY_YEAR");		//���޳⵵
    	String sPayMonth = (String)ctx.get("PAY_MM");		//���޿�
    	
    	if( getCfmCheck(sPayYear, sPayMonth) == -1) {
    		Util.setSvcMsgCode(ctx, "SNIS_RBM_D008");
    		Util.setReturnParam(ctx, "DBErr", "O");
    		return;
    	}
    	
    	ArrayList list = new ArrayList();
    	FileReader File = new FileReader();
    	list = File.executeInterface(sFilePath);

    	//��������
    	deleteGitaCntnt(sPayYear, sPayMonth);

    	int nSaveCnt = insertGitaCntnt(list, sPayYear, sPayMonth);	//Insert


    	deleteGitaGrn(sPayYear, sPayMonth);    	//�׸�ī�� ��������
    	insertGitaGrn(sPayYear, sPayMonth);	    //�׸�ī�� �Ǹ��������� ����

    	nSaveCnt += updateGitaCntntGrn(sPayYear, sPayMonth);	//Insert
    	
    	Util.setSaveCount  (ctx, 0);
    }
    
    /**
     * <p> ��Ÿ�ҵ漼 �߰� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertGitaCntnt(ArrayList list, String sPayYear,String sPayMonth) 
    {
    	RbmJdbcDao rbmdao  = this.getRbmDao("rbmjdbcdao");
    	String[] sArrMap   = new String[list.size()];
    	String sCommCd, sDivNo;
    	StringBuffer sb = new StringBuffer();

    	//������ �о�鿩 DB INSERT
    	for(int i=0; i < list.size() ;i++) {  
    		
    		HashMap map = (HashMap)list.get(i);

    		//���� ���߱�    
    		//column3(������ȣ)   => ���ڸ��� ��� ���ڸ��� '0' �߰� 
    		//column4(��ǥ����ȣ) => ���ڸ��� ��� ���ڸ��� '0' �߰� 
    		//column14(��Ÿ)     => "\" ����
    		
			int iWinAmt 	= Util.NVL(map.get("column11"),0) + Util.NVL(map.get("column17"),0) + Util.NVL(map.get("column23"),0) + Util.NVL(map.get("column29"),0) + Util.NVL(map.get("column35"),0) + Util.NVL(map.get("column41"),0);
			int iJegeupAmt 	= Util.NVL(map.get("column12"),0) + Util.NVL(map.get("column18"),0) + Util.NVL(map.get("column24"),0) + Util.NVL(map.get("column30"),0) + Util.NVL(map.get("column36"),0) + Util.NVL(map.get("column42"),0);
			int iGita1 		= Util.NVL(map.get("column13"),0) + Util.NVL(map.get("column19"),0) + Util.NVL(map.get("column25"),0) + Util.NVL(map.get("column31"),0) + Util.NVL(map.get("column37"),0) + Util.NVL(map.get("column43"),0);
			int iGita2		= Util.NVL(map.get("column14"),0) + Util.NVL(map.get("column20"),0) + Util.NVL(map.get("column26"),0) + Util.NVL(map.get("column32"),0) + Util.NVL(map.get("column38"),0) + Util.NVL(map.get("column44"),0);
			int iGitaPay 	= Util.NVL(map.get("column15"),0) + Util.NVL(map.get("column21"),0) + Util.NVL(map.get("column27"),0) + Util.NVL(map.get("column33"),0) + Util.NVL(map.get("column39"),0) + Util.NVL(map.get("column45"),0);
			
    		sCommCd = (String)map.get("column"+5);
    		if(sCommCd.length() == 1)	sCommCd = "0" + sCommCd;
    		sDivNo = (String)map.get("column"+6);
    		if(sDivNo.length() == 1)	sDivNo = "0" + sDivNo;
    		
    		sb.delete(0, sb.length()); // StringBuffer ����
    		
    		sb.append(" INSERT INTO TBRD_GITA_CNTNT ( ");
    		sb.append("             SELL_CD         " );
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
    		sb.append("             ,PAY_YEAR       " );
    		sb.append("             ,PAY_MM         " );
    		sb.append("             ,INST_ID        " );
    		sb.append("             ,INST_DT        " );
    		sb.append(" 			) VALUES (      " );
    		sb.append(" '0" +  (String)map.get("column"+0)   +"' " );
    		sb.append(",'00"+  (String)map.get("column"+1)   +"' " );
    		sb.append(",'"   +  (String)map.get("column"+2)  +"' " );
    		sb.append(",'"   +  sCommCd  +                    "' " );
    		sb.append(",'"   +  sDivNo   +                    "' " );
    		sb.append(",'"   +  (String)map.get("column"+7)  +"' " );
    		sb.append(",'"   +  (String)map.get("column"+8)  +"' " );
    		sb.append(",'"   +  (String)map.get("column"+9)  +"' " );
    		sb.append(",'"   +  iJegeupAmt  +"' " );
    		sb.append(",'"   +  iWinAmt 	+"' " );
    		sb.append(",'"   +  iGita1 		+"' " );
    		sb.append(",'"   +  iGita2 		+"' " );
    		sb.append(",'"   +  iGitaPay 	+"' " );
    		sb.append(",'"   +  sPayYear +                    "' " );
    		sb.append(",'"   +  sPayMonth +                   "' " );
    		sb.append(",'"   +  SESSION_USER_ID +             "' " );
    		sb.append(", SYSDATE                               ) " );
    		
    		sArrMap[i] = sb.toString();
    	}

    	int nRtn     = 0;
    	int nFailCnt = 0;
    	
    	int[] insertCounts = rbmdao.executeBatch( sArrMap );
    	
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
     * <p> Ȯ������ �˻�  </p>
     * @param   PosGenericDao dao			DB Connect ����
     *          String        sAttFileKey	FileKey
     * @return  nRtnKey	int			        sAttFileKey�� ���� ÷������ ����
     * @throws  none
     */
	public int getCfmCheck(String sPayYear, String sPayMm)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        int nRtnKey = -1;
        
        param.setWhereClauseParameter(i++, sPayYear);	//���޳⵵
        param.setWhereClauseParameter(i++, sPayMm);	    //���޿�
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm5060_s05", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        
        if( pr.length > 1) {
        	return -1;	//Ȯ���� ��Ȯ���� �������� ���
        } else if( pr.length == 0) {
        	return 0;	
        }
        
        String sRtnKey = String.valueOf(pr[0].getAttribute("CFM_CD"));
        
        if( sRtnKey.equals("001")) {
        	nRtnKey = -1;	//Ȯ��
        } else {
        	nRtnKey = 0;	//��Ȯ��
        }
        
        return nRtnKey;
    }
	
    /**
     * <p> �Է¹��� �⵵/���� ��Ÿ�ҵ漼 ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteGitaCntnt(String sPayYear, String sPayMm) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, sPayYear);	//���޳⵵
        param.setValueParamter(i++, sPayMm);	//���޿�
        
        int dmlcount = this.getDao("rbmdao").update("rsm5060_d01", param);

        return dmlcount;
    }
    

    /**
     * <p> �Է¹��� �⵵/���� ��Ÿ�ҵ漼 ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteGitaGrn(String sPayYear, String sPayMm) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, sPayYear);	//���޳⵵
        param.setValueParamter(i++, sPayMm);	//���޿�
        
        int dmlcount = this.getDao("rbmdao").update("rsm5060_d03", param);

        return dmlcount;
    }
    

    /**
     * <p> �׸�ī��DB���� �⵵/���� ��Ÿ�ҵ漼 �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertGitaGrn(String sPayYear, String sPayMm) 
    {
        PosParameter param = new PosParameter();
        int i = 0;        
        param.setValueParamter(i++, SESSION_USER_ID);	//�Է��� ���
        param.setValueParamter(i++, sPayYear+sPayMm);			//���޳��
        
        int dmlcount = this.getDao("rbmdao").update("rsm5060_i02", param);

        return dmlcount;
    }

    /**
     * <p> ��Ÿ�ҵ漼(�׸�ī��) �ڷ� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateGitaCntntGrn(String sPayYear,String sPayMm) 
    {
        PosParameter param = new PosParameter();
        int i = 0;        
        param.setValueParamter(i++, SESSION_USER_ID);	//������ ���
        param.setValueParamter(i++, sPayYear);			//���޳⵵
        param.setValueParamter(i++, sPayMm);			//���޿�
        
        int dmlcount = this.getDao("rbmdao").update("rsm5060_u01", param);

        return dmlcount;
    }
    
}