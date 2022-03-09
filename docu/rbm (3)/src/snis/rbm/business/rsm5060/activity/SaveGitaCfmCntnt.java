/*================================================================================
 * �ý���			: ��Ÿ�ҵ漼 ���� Ȯ��
 * �ҽ����� �̸�	: snis.rbm.business.rsm5010.activity.SaveCfmCntnt
 * ���ϼ���		: ��Ÿ�ҵ漼 Ȯ������ ����
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-10-15
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rsm5060.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveGitaCfmCntnt extends SnisActivity {
	
	public SaveGitaCfmCntnt(){}

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
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
        
        String sPayYear = (String)ctx.get("PAY_YEAR");	//���޳⵵
        String sPayMm   = (String)ctx.get("PAY_MM");	//���޿�
      
        
        
        //����ġ ������ ����
        if( getValidCnt(sPayYear, sPayMm) > 0 ) {
    		try { 
    			throw new Exception(); 
        	} catch(Exception e) {
        		this.rollbackTransaction("tx_rbm");
        		Util.setSvcMsgCode(ctx, "SNIS_RBM_D005");
        		Util.setReturnParam(ctx, "DBErr", "O");	            		
        		return;
        	}
    	}
        
        
        // ��ġ �Ǽ��� ��Ÿ�� �Ǽ� ��
        
        // ��Ÿ �Ǽ� ��ȸ 
        int nCnt = getCfmCnt(ctx);
        
        // ��ġ �Ǽ� ��ȸ
        int intSameCount =  sameGitaCfmCntntCount(ctx);
        
        
        if( nCnt != intSameCount) {
       	 try { 
       			throw new Exception(); 
           	} catch(Exception e) {
           		this.rollbackTransaction("tx_rbm");
           		Util.setSvcMsgCode(ctx, "SNIS_RBM_D002");
           		Util.setReturnParam(ctx, "DBErr", "O");
           		return;
           	}
       }
        
        nSaveCount = saveGitaCfmCntntAll(ctx);
        
        Util.setReturnParam(ctx, "RESULT", "T"); // ���
        Util.setSaveCount(ctx, nSaveCount);
        
    }

    /**
     * <p> ��Ÿ�ҵ漼 Ȯ������ ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveGitaCfmCntnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("PC_MEET_CD"));	//������ڵ�
        param.setValueParamter(i++, record.getAttribute("PC_SELL_CD"));	//�ó�ڵ�
        param.setValueParamter(i++, record.getAttribute("PC_TSN"));	    //���ֱǹ�ȣ
        param.setValueParamter(i++, record.getAttribute("PAY_YEAR"));	//���޳⵵    
        param.setValueParamter(i++, record.getAttribute("PAY_MM"));     //���ݿ�

        param.setValueParamter(i++, record.getAttribute("CFM_CD"));		//Ȯ���ڵ�
        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(�ۼ���)
        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(������)
        
        int dmlcount = this.getDao("rbmdao").update("rsm5060_m01", param);

        return dmlcount;
    }
    
    /**
     * <p> ��Ÿ�ҵ漼 �ϰ�Ȯ�� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveGitaCfmCntntAll(PosContext ctx) 
    {	
    	PosRowSet prs = null;
    	PosRow[] pr = null;
        PosParameter param = new PosParameter();
        
        
        
        String PAY_YEAR = (String)ctx.get("PAY_YEAR");	// ���޳⵵
        String PAY_MM   = (String)ctx.get("PAY_MM");	// ���޿�
        String MEET_CD = (String)ctx.get("MEET_CD");	// ��� ���� ���� �ڵ�
        String SELL_CD   = (String)ctx.get("SELL_CD");	// �Ǹ�ó
        
        int i = 0;
		param.setWhereClauseParameter(i++, PAY_YEAR);
		param.setWhereClauseParameter(i++, PAY_MM);
		param.setWhereClauseParameter(i++, MEET_CD);
		param.setWhereClauseParameter(i++, SELL_CD);
		param.setWhereClauseParameter(i++, PAY_YEAR);
		param.setWhereClauseParameter(i++, PAY_MM);
		param.setWhereClauseParameter(i++, MEET_CD);
		param.setWhereClauseParameter(i++, SELL_CD);
		
		prs = this.getDao("rbmdao").find("rsm5060_s09", param);
		pr = prs.getAllRow();
		
		String[] arrQuery = new String[pr.length];
		
		for(int prI=0;prI<pr.length;prI++)
		{	
			
			arrQuery[prI] = " 	MERGE INTO TBRD_GITA_CFM_CNTNT A  --��Ÿ�ҵ漼 Ȯ������	       \n"+ 
			"                     /* rsm5060_m01 */				       \n"+ 
			"             USING						       \n"+ 
			"                     (SELECT					       \n"+ 
			"                              '"+pr[prI].getAttribute("PC_MEET_CD")+"' AS MEET_CD     --������ڵ�	       \n"+ 
			"                             ,'"+pr[prI].getAttribute("PC_SELL_CD")+"' AS SELL_CD     --�ó�ڵ�	       \n"+  
			"                             ,'"+pr[prI].getAttribute("PC_PAY_YEAR")+"' AS PAY_YEAR    --���޳⵵	       \n"+ 
			"                             ,'"+pr[prI].getAttribute("PC_PAY_MM")+"' AS PAY_MM      --���޿�	       \n"+
			"                             ,'001' AS CFM_CD      --Ȯ���ڵ�	       \n"+ 
			"                             ,'"+SESSION_USER_ID+"' AS INST_ID     --�ۼ���	       \n"+ 
			"                             ,'"+SESSION_USER_ID+"' AS UPDT_ID     --������	       \n"+ 
			"     								       \n"+ 
			"                        FROM    DUAL ) B			       \n"+ 
			"                 ON (						       \n"+ 
			"                         A.MEET_CD  = B.MEET_CD    --������ڵ�       \n"+ 
			"                     AND A.SELL_CD  = B.SELL_CD    --�ó�ڵ�       \n"+  
			"                     AND A.PAY_YEAR = B.PAY_YEAR   --���޳⵵	       \n"+ 
			"                     AND A.PAY_MM   = B.PAY_MM     --���޿�	       \n"+ 
			"             )							       \n"+ 
			"             							       \n"+ 
			"             WHEN MATCHED THEN					       \n"+ 
			"                 UPDATE SET 					       \n"+ 
			"                      A.CFM_CD  = B.CFM_CD    --Ȯ���ڵ�	       \n"+ 
			"                     ,A.UPDT_ID = B.UPDT_ID   --������		       \n"+ 
			"                     ,A.UPDT_DT = SYSDATE     --�����Ͻ�	       \n"+ 
			"                 						       \n"+ 
			"             WHEN NOT MATCHED THEN				       \n"+ 
			"                 INSERT (					       \n"+ 
			"             							       \n"+ 
			"                      A.MEET_CD      --������ڵ�		       \n"+ 
			"                     ,A.SELL_CD      --�ó�ڵ�		       \n"+
			"                     ,A.PAY_YEAR     --���޳⵵		       \n"+ 
			"                     ,A.PAY_MM       --���޿�			       \n"+ 
			"                     						       \n"+ 
			"                     ,A.CFM_CD       --Ȯ���ڵ�		       \n"+ 
			"                     ,A.INST_ID      --�ۼ���			       \n"+ 
			"                     ,A.INST_DT      --�ۼ�����		       \n"+ 
			"                     						       \n"+ 
			"                 ) VALUES (					       \n"+ 
			"                      B.MEET_CD      --������ڵ�		       \n"+ 
			"                     ,B.SELL_CD      --�ó�ڵ�		       \n"+
			"                     ,B.PAY_YEAR     --���޳⵵		       \n"+ 
			"                     ,B.PAY_MM       --���޿�			       \n"+
			"                     ,B.CFM_CD          --Ȯ���ڵ�		       \n"+ 
			"                     ,B.INST_ID      --�ۼ���			       \n"+ 
			"                     ,SYSDATE        --�ۼ�����		       \n"+ 
			"                 )						       \n";

	        	
		}
		
		int [] insertCounts = getRbmDao("rbmjdbcdao").executeBatch(arrQuery);
		int intResult = 0; // �����
		int failure_count = 0;
		
		if (failure_count == 0) {
			intResult = insertCounts.length;
		} else {
			intResult = 0;
		}        

        return intResult;
    }
    
    
    /**
     * <p> ��Ÿ�ҵ漼 �ϰ�Ȯ�� ���� ī��Ʈ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int sameGitaCfmCntntCount(PosContext ctx) 
    {
    	
    	PosRowSet prs = null;
    	PosRow[] pr = null;
        PosParameter param = new PosParameter();
        
        String PAY_YEAR = (String)ctx.get("PAY_YEAR");	// ���޳⵵
        String PAY_MM   = (String)ctx.get("PAY_MM");	// ���޿�
        String MEET_CD = (String)ctx.get("MEET_CD");	// ��� ���� ���� �ڵ�
        String SELL_CD   = (String)ctx.get("SELL_CD");	// �Ǹ�ó
        
        int i = 0;
		param.setWhereClauseParameter(i++, PAY_YEAR);
		param.setWhereClauseParameter(i++, PAY_MM);
		param.setWhereClauseParameter(i++, MEET_CD);
		param.setWhereClauseParameter(i++, SELL_CD);
		param.setWhereClauseParameter(i++, PAY_YEAR);
		param.setWhereClauseParameter(i++, PAY_MM);
		param.setWhereClauseParameter(i++, MEET_CD);
		param.setWhereClauseParameter(i++, SELL_CD);
		
		prs = this.getDao("rbmdao").find("rsm5060_s10", param);
		pr = prs.getAllRow();
		
		int intCNT = Util.nullToInt(String.valueOf(pr[0].getAttribute("CNT")));
		
        return intCNT;
    }
    /**
     * <p> ��Ÿ�ҵ漼 ��ü ���� ��ȸ  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  rtnKey	int			��Ÿ�ҵ漼 ����
     * @throws  none
     */
    protected int getCfmCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("PAY_YEAR"));	//���޳⵵
        param.setWhereClauseParameter(i++, record.getAttribute("PAY_MM" ));  	//���޿�
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm5060_s03", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        String rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.parseInt(rtnKey);
    }
    
    /**
     * <p> ��Ÿ�ҵ漼 ��ü ���� ��ȸ  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  rtnKey	int			��Ÿ�ҵ漼 ����
     * @throws  none
     */
    protected int getCfmCnt(PosContext ctx) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setWhereClauseParameter(i++, ctx.get("PAY_YEAR"));	//���޳⵵
        param.setWhereClauseParameter(i++, ctx.get("PAY_MM" ));  	//���޿�
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm5060_s03", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        String rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.parseInt(rtnKey);
    }
    
    /**
     * <p> ��Ÿ�ҵ漼���� ��ġ���� Ȯ��  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  rtnKey	int			��ġ����( 0 : ��ġ 0�ʰ� : ����ġ)
     * @throws  none
     */
    protected int getValidCnt(String sPayYear, String sPayMm) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, sPayYear);	//���޳⵵
        param.setWhereClauseParameter(i++, sPayMm);	    //���޿�
        param.setWhereClauseParameter(i++, sPayYear);	//���޳⵵
        param.setWhereClauseParameter(i++, sPayMm);	    //���޿�
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm5060_s02", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        String rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.parseInt(rtnKey);
    }
}
