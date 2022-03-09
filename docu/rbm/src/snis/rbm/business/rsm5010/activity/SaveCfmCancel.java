/*================================================================================
 * �ý���			: ������������
 * �ҽ����� �̸�	: snis.rbm.business.rsm5010.activity.SaveCfmCntnt
 * ���ϼ���		: ������������_Ȯ������ ����
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-10-07
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rsm5010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveCfmCancel extends SnisActivity {
	
	public SaveCfmCancel(){}

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
    	String sValue = (String) ctx.get("SEND_VALUE"); // ���� ȭ��� OR �̺�Ʈ
    	System.out.println("sValue : "+sValue);
    	if( sValue.equals("SEN") )
    	{
    		updateCfmCancelSen(ctx);
    	}
    	else
    	{
    		saveState(ctx);
    	}
    	
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
        
        String sValue   = (String)ctx.get("SEND_VALUE");	//ȭ���        
        sDsName = "dsPcTax";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        int nCnt = 0;
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

	            if( sValue.equals("FST") ){
		            //1��Ȯ�����
	            	nTempCnt = updateCfmCancelFst(record);
	            }else  if( sValue.equals("SEN") ){
	            	// 2��Ȯû���
	            	nTempCnt = updateCfmCancelSen(record);
	            }else  if( sValue.equals("THR") ){
	            	// 3��Ȯ�����
	            	nTempCnt = updateCfmCancelThr(record);
	            }	        	
		        nSaveCount = nSaveCount + nTempCnt; 
	        }	        
        }       
        Util.setSaveCount(ctx, nSaveCount);
    }


    /**
     * <p> ������������_1��Ȯ�� ��� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateCfmCancelFst(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(�ۼ���)
        param.setValueParamter(i++, record.getAttribute("MEET_CD"));	//������ڵ�
        param.setValueParamter(i++, record.getAttribute("SELL_CD"));	//�ó�ڵ�
        param.setValueParamter(i++, record.getAttribute("TSN"));	    //���ֱǹ�ȣ
        param.setValueParamter(i++, record.getAttribute("PAY_YEAR"));	//���޳⵵    
        
        int dmlcount = this.getDao("rbmdao").update("rsm5010_u01", param);

        return dmlcount;
    }
    
    /**
     * <p> ������������_2��Ȯ�� ��� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateCfmCancelSen(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(�ۼ���)
        param.setValueParamter(i++, record.getAttribute("TAX_MEET_CD"));	//������ڵ�
        param.setValueParamter(i++, record.getAttribute("TAX_SELL_CD"));	//�ó�ڵ�
        param.setValueParamter(i++, record.getAttribute("TAX_TSN"));	    //���ֱǹ�ȣ
        param.setValueParamter(i++, record.getAttribute("PAY_YEAR"));	//���޳⵵    
        
        int dmlcount = this.getDao("rbmdao").update("rsm5010_u02", param);

        return dmlcount;
    }
    
    
    /**
     * <p> ������������_2��Ȯ�� ��� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
	protected int updateCfmCancelSen(PosContext ctx) {

		PosRowSet prs = null;
		PosRow[] pr = null;
		PosParameter param = new PosParameter();

		String PAY_YEAR = (String) ctx.get("PAY_YEAR"); // <!-- ���޳⵵ -->
		String MEET_CD = (String) ctx.get("MEET_CD"); // <!-- ������ڵ�(����ó)-->
		String SELL_CD = (String) ctx.get("SELL_CD"); // <!-- �ó�ڵ�(�Ǹ�ó)-->
		String BRNC_CD = (String) ctx.get("BRNC_CD"); // <!-- �����ڵ�-->

		int i = 0;
		param.setWhereClauseParameter(i++, PAY_YEAR);
		param.setWhereClauseParameter(i++, MEET_CD);
		param.setWhereClauseParameter(i++, SELL_CD);
		param.setWhereClauseParameter(i++, BRNC_CD);
		param.setWhereClauseParameter(i++, BRNC_CD);
		param.setWhereClauseParameter(i++, BRNC_CD);
		param.setWhereClauseParameter(i++, PAY_YEAR);
		param.setWhereClauseParameter(i++, MEET_CD);
		param.setWhereClauseParameter(i++, SELL_CD);
		param.setWhereClauseParameter(i++, BRNC_CD);
		param.setWhereClauseParameter(i++, BRNC_CD);
		param.setWhereClauseParameter(i++, BRNC_CD);

		prs = this.getDao("rbmdao").find("rsm5040_s07", param);
		pr = prs.getAllRow();
		
		System.out.println("pr.length : "+pr.length);

		String[] arrQuery = new String[pr.length];

		for (int prI = 0; prI < pr.length; prI++) {
			arrQuery[prI] = " UPDATE TBRD_CFM_CNTNT SET                    			  \n"+ 
			"      SND_CFM_CD = '002'	-- 	(SND_CFM_CD) 2��Ȯ���ڵ�  \n"+                   
			"     ,UPDT_ID    = '"+SESSION_USER_ID+"'	--	(UPDT_ID     ������               \n"+      
			"     ,UPDT_DT    = SYSDATE     --  	  �����Ͻ�		  \n"+ 
			" WHERE	MEET_CD  	= '"+pr[prI].getAttribute("TAX_MEET_CD")+"'	--	(MEET_CD)    ������ڵ�	  \n"+ 
			" AND 	SELL_CD  	= '"+pr[prI].getAttribute("TAX_SELL_CD")+"'	-- 	(SELL_CD)    �ó�ڵ�	  \n"+ 
			" AND 	TSN_NO   	= '"+pr[prI].getAttribute("TAX_TSN")+"'	--	(TSN)        ���ֱǹ�ȣ	  \n"+ 
			" AND 	PAY_YEAR 	= '"+pr[prI].getAttribute("PAY_YEAR")+"'	--	(PAY_YEAR)   ���޳⵵	  \n";
			
			 
		}

		int[] insertCounts = getRbmDao("rbmjdbcdao").executeBatch(arrQuery);
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
     * <p> ������������_3��Ȯ�� ��� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateCfmCancelThr(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(�ۼ���)
        param.setValueParamter(i++, record.getAttribute("MEET_CD"));	//������ڵ�
        param.setValueParamter(i++, record.getAttribute("SELL_CD"));	//�ó�ڵ�
        param.setValueParamter(i++, record.getAttribute("TSN"));	    //���ֱǹ�ȣ
        param.setValueParamter(i++, record.getAttribute("PAY_YEAR"));	//���޳⵵    
        
        int dmlcount = this.getDao("rbmdao").update("rsm5010_u03", param);

        return dmlcount;
    }
    
    /**
     * <p> ������������_Ȯ������ ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveCfmCntnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("MEET_CD"));	//������ڵ�
        param.setValueParamter(i++, record.getAttribute("SELL_CD"));	//�ó�ڵ�
        param.setValueParamter(i++, record.getAttribute("TSN"));	    //���ֱǹ�ȣ
        param.setValueParamter(i++, record.getAttribute("PAY_YEAR"));	//���޳⵵    
        param.setValueParamter(i++, record.getAttribute("CUST_SSN"));   //�ֹε�Ϲ�ȣ
 
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));  	//�����ڵ�       
        param.setValueParamter(i++, record.getAttribute("FST_CFM_CD"));	//1��Ȯ���ڵ�
        param.setValueParamter(i++, record.getAttribute("SND_CFM_CD"));	//2��Ȯ���ڵ�
        param.setValueParamter(i++, record.getAttribute("THR_CFM_CD"));	//3��Ȯ���ڵ�
        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(�ۼ���)
        
        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(������)
        
        int dmlcount = this.getDao("rbmdao").update("rsm5010_m02", param);

        return dmlcount;
    }
    
    /**
     * <p> ���������Է� ��ü ���� ��ȸ  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  rtnKey	int			������������ ����
     * @throws  none
     */
    protected int getFstCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String strCommNo =  record.getAttribute("COMM_NO" ).toString();
        String strSellCd =  record.getAttribute("SELL_CD" ).toString();
        if(strSellCd==null)strSellCd="03";
        // ������ �������� ������ �ϳ��� ���⶧���� ���Խ����� ������ �Ǵ�.
        
        if( !strSellCd.equals("03") && (strCommNo.equals("01") ||  strCommNo.equals("02") ||  strCommNo.equals("03") ||  strCommNo.equals("04") ||  strCommNo.equals("08") )){
        	strCommNo = "^(0[123468])$";    	
        }        
        
		if(strSellCd.equals("03")){
			strCommNo ="98";
		}
		
        param.setWhereClauseParameter(i++, record.getAttribute("PAY_YEAR"));	//���޳⵵
        param.setWhereClauseParameter(i++, strCommNo);  	//�����ڵ�
        param.setWhereClauseParameter(i++, strCommNo);  	//�����ڵ�
        param.setWhereClauseParameter(i++, strCommNo);  	//�����ڵ�
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm5010_s03", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        String rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.parseInt(rtnKey);
    }
    
    /**
     * <p> ������������ ��ü���� ��ȸ  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  rtnKey	int			������������ ����
     * @throws  none
     */
    protected int getCrossCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("PAY_YEAR"));	//���޳⵵
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm5030_s01", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        String rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.parseInt(rtnKey);
    }
    
    /**
     * <p> ������������ ��ü���� ��ȸ  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  rtnKey	int			������������ ����
     * @throws  none
     */
    protected int getSndCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;       
        
        param.setWhereClauseParameter(i++, record.getAttribute("PAY_YEAR"));	//���޳⵵
        param.setWhereClauseParameter(i++, record.getAttribute("PAY_YEAR"));	//���޳⵵
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm5040_s03", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        String rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.parseInt(rtnKey);
    }
    
    /**
     * <p> ������������ ��ġ���� Ȯ��  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  rtnKey	int			��ġ����( 0 : ��ġ 0�ʰ� : ����ġ)
     * @throws  none
     */
    protected int getValidCnt(String sPayYear) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setWhereClauseParameter(i++, sPayYear);	//���޳⵵
        param.setWhereClauseParameter(i++, sPayYear);	//���޳⵵
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm5040_s04", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        String rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.parseInt(rtnKey);
    }
    
    /**
     * <p> ��������Ȯ�� ��ü���� ��ȸ  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  rtnKey	int			������������ ����
     * @throws  none
     */
    protected int getThrCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("PAY_YEAR"));	//���޳⵵
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsm5050_s01", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        String rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

        return Integer.parseInt(rtnKey);
    }
}
