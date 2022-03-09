/*
 * ================================================================================
 * �ý��� : ��ǰ ������ �ҽ����� 
 * �̸� : snis.rbm.business.rbo4060.activity..java 
 * ���ϼ��� : �����԰�
 * �ۼ��� : ���ѳʿ�
 * ���� : 1.0.0 
 * �������� : 2011-11-04
 * ������������ : 
 * ���������� : ������������ :
 * =================================================================================
 */
package snis.rbm.business.rbo4060.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;


import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveBranchIn extends SnisActivity {
	public SaveBranchIn() {
		
	}
	
	/**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */
	public String runActivity(PosContext ctx)
	{
		// ����� ���� Ȯ��
		if (!setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS)) {
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
		int nSaveCount	 = 0;
//		int nDeleteCount = 0;
		String sDsName	 = "";
		String cDsName	 = "";
		
		PosDataset ds;
		int nSize		 = 0;
		int nTempCnt	 = 0;
		String strFlag	= (String)ctx.get("SEND_VALUE"); // ����(����)�԰�(O)/�����԰�(BO)/����(����)�԰����(C)/�����԰����(BC)
		sDsName = "dsList";
		cDsName = "dsBrncList";

		
		if( ctx.get(sDsName) != null)
		{
			ds	  = (PosDataset) ctx.get(sDsName);	// dsList
			nSize = ds.getRecordCount();	
			
			if( strFlag.equals("IN") ){
				for ( int i = 0; i < nSize; i++)
				{
					PosRecord record = ds.getRecord(i);
				
					// �����԰�
					if ( record.getAttribute("CHK").equals("1") ){
						
						
						Double dAprvCnt = (Double)record.getAttribute("APRV_CNT");
						if( getStock((String)record.getAttribute("PARTS_CD"),"00") - dAprvCnt.intValue() >=0 ){
							nTempCnt = updatePartsReqIn(record);
							
						    UpdatePartsStock(record);		// ���� ����� ����
						    InsertBrncPartsStock(record);	// ���� ����� ���
							   		   
							   		  
					   		nSaveCount = nSaveCount + nTempCnt;
					   		
						} else {
							try { 
				    			throw new Exception(); 
				        	} catch(Exception e) {       		
				        		this.rollbackTransaction("tx_rbm");
				        		Util.setSvcMsg(ctx, "������ 0 �̻��̾�� �մϴ�.");
				        		
				        		return;
				        	}
						}
					}
					
					
				}
			}
		}else if( ctx.get(cDsName) != null)
		{
			ds	  = (PosDataset) ctx.get(cDsName);	// dsBrncList
			nSize = ds.getRecordCount();	
			
			if( strFlag.equals("CANCEL") ){
				for ( int i = 0; i < nSize; i++)
				{
					PosRecord record = ds.getRecord(i);
				
					// �����԰����
					if ( record.getAttribute("CHK").equals("1") ){
						
						//���������� üũ 
						Double dAprvCnt = (Double)record.getAttribute("APRV_CNT");
						if( getStock((String)record.getAttribute("PARTS_CD"),(String)record.getAttribute("BRNC_CD")) - dAprvCnt.intValue() >=0 ){
							nTempCnt = updatePartsReqInCancel(record);
							
							UpdateCnlPartsStock(record);		// ���� ����� ����
						    UpdateCnlBrncPartsStock(record);	// ������ ����� ����

						}else{
							try { 
				    			throw new Exception(); 
				        	} catch(Exception e) {       		
				        		this.rollbackTransaction("tx_rbm");
				        		Util.setSvcMsg(ctx, "������ 0 �̻��̾�� �մϴ�.");
				        		
				        		return;
				        	}
						}

					}
					nSaveCount = nSaveCount + nTempCnt;
				}
			}
		}
		
		Util.setSaveCount	(ctx, nSaveCount	);	
	}
	


    /**
     * <p> ���� ����� ��� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
	protected int InsertBrncPartsStock(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;
		
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	 ));	// 1. ��ǰ�ڵ�
		param.setValueParamter(i++, record.getAttribute("BRNC_CD"	 ));	// 2. �����ڵ�
		param.setValueParamter(i++, record.getAttribute("BRNC_IN_CNT"));	// 3. �����԰����
		param.setValueParamter(i++, SESSION_USER_ID					  );	// 4. �ۼ���ID
		
		int dmlcount = this.getDao("rbmdao").update("rbo4060_i03", param);
		
		return dmlcount;
	}
	
	
    /**
     * <p> ����� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
	protected int UpdatePartsStock(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;

		param.setValueParamter(i++, record.getAttribute("BRNC_IN_CNT"));	// 1. �����԰����
		param.setValueParamter(i++, SESSION_USER_ID					  );	// 2. ������ID	
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"));		// 3. ��ǰ�ڵ�
		
		int dmlcount = this.getDao("rbmdao").update("rbo4060_u01", param);
		
		return dmlcount;
	}
	

    /**
     * <p> ����� ����(�����԰� ��� ��) </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
	protected int UpdateCnlPartsStock(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;

		param.setValueParamter(i++, record.getAttribute("BRNC_IN_CNT"));	// 1. �����԰����
		param.setValueParamter(i++, SESSION_USER_ID					  );	// 2. ������ID
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	 ));	// 3. ��ǰ�ڵ�
		
		int dmlcount = this.getDao("rbmdao").update("rbo4060_u03", param);
		
		return dmlcount;
	}
	
	
    /**
     * <p> ���� ����� ����(�����԰� ��� ��) </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
	protected int UpdateCnlBrncPartsStock(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;

		param.setValueParamter(i++, record.getAttribute("BRNC_IN_CNT"	));	// 1. �����԰����
		param.setValueParamter(i++, SESSION_USER_ID					  	 );	// 2. ������ID
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"		));	// 3. ��ǰ�ڵ�
		param.setValueParamter(i++, record.getAttribute("BRNC_CD" 		));	// 4. �����ڵ�
		
		int dmlcount = this.getDao("rbmdao").update("rbo4060_u04", param);
		
		return dmlcount;
	}
	
	
    /**
     * <p> ����û  �԰����� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updatePartsReqIn(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
  

        param.setValueParamter(i++, record.getAttribute("BRNC_IN_ID"));		
        param.setValueParamter(i++, record.getAttribute("BRNC_IN_DT"));		
        param.setValueParamter(i++, SESSION_USER_ID);								
     
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("PARTS_CD" ));		
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD" ));	
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ" ));	
        


        int dmlcount = this.getDao("rbmdao").update("rbo4060_u05", param);
        return dmlcount;
    }
    
    
    /**
     * <p> ����û  �԰�������� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updatePartsReqInCancel(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
	
        param.setValueParamter(i++, SESSION_USER_ID);								
     
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("PARTS_CD" ));		
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD" ));	
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ" ));	
        


        int dmlcount = this.getDao("rbmdao").update("rbo4060_u06", param);
        return dmlcount;
    }
    
    
	/**
     * <p> ����� ���� ��ȸ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
	protected int getStock(String sPartsCd, String sBrncCd) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String rtnKey = "";
        
        param.setWhereClauseParameter(i++, sPartsCd);
        param.setWhereClauseParameter(i++, sBrncCd);
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rbo4060_s05", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        rtnKey = String.valueOf(pr[0].getAttribute("STK_CNT"));

        return Integer.parseInt(rtnKey);
    }
}
