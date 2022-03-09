/*
 * ================================================================================
 * �ý��� : ��ǰ ����û �ҽ����� 
 * �̸� : snis.rbm.business.rbo4020.activity..java 
 * ���ϼ��� : ����û 
 * �ۼ��� : ���ѳʿ�
 * ���� : 1.0.0 
 * �������� : 2011-11-03
 * ������������ : 
 * ���������� : ������������ :
 * =================================================================================
 */
package snis.rbm.business.rbo4040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SavePartsOutReq extends SnisActivity {
	public SavePartsOutReq() {
		
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
		int nDeleteCount = 0;
		String sDsName	 = "";
		
		PosDataset ds;
		int nSize		 = 0;
		int nTempCnt	 = 0;
		
		sDsName = "dsAprvList";
		
		if( ctx.get(sDsName) != null)
		{
			nTempCnt = 0;
			
			ds	  = (PosDataset) ctx.get(sDsName);
			nSize = ds.getRecordCount();
			
			Double dSeq, dReqCnt;
			
			for ( int i = 0; i < nSize; i++)
			{
				PosRecord record = ds.getRecord(i);
				
				if ( record.getType() == PosRecord.UPDATE
							||  record.getType() == PosRecord.INSERT) {
					dSeq = (Double)record.getAttribute("SEQ");
					
					if( ( record.getType() == PosRecord.UPDATE && dSeq.intValue() != 0 ) ) {
						if( !getAprvStas(record).equals("001") && !getAprvStas(record).equals("002")) {
							try { 
		            			throw new Exception(); 
			            	} catch(Exception e) {       		
			            		this.rollbackTransaction("tx_rbm");
			            		Util.setSvcMsg(ctx, "���ε� �����Ͱ� �����ϹǷ� ������ �Ͻ� �� �����ϴ�.");
			            		
			            		return;
			            	}
						}
					}
					
					dReqCnt = (Double)record.getAttribute("REQ_CNT");
					
					//��û������ 0�̸� ����
					if( dReqCnt.intValue() == 0 ) {
						deletePartsOutReq(record);
					} else {
						if ( (nTempCnt = updatePartsOutReq(record)) == 0 ) {
							nTempCnt += insertPartsOutReq(record);
						}
					}	
				}
				
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deletePartsOutReq(record);	            	
	            }	
				nSaveCount = nSaveCount + nTempCnt;
				continue;
			}
		}
		
		Util.setSaveCount	(ctx, nSaveCount	);
		Util.setDeleteCount	(ctx, nDeleteCount	);
		
		
	}
	
	
    /**
     * <p> ����û ���� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
	protected int insertPartsOutReq(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;
		
		System.out.println("111111111111111");
		
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	));	// 1. ��ǰ�ڵ�
		param.setValueParamter(i++, record.getAttribute("BRNC_CD"	));	// 2. �����ڵ�
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	));	// 3-1. ����(��ǰ�ڵ�)
		param.setValueParamter(i++, record.getAttribute("BRNC_CD"	));	// 3-2. ����(�����ڵ�)
		param.setValueParamter(i++, SESSION_USER_ID);	// 4. ��û��
		param.setValueParamter(i++, record.getAttribute("STK_CNT"));	// 5. ��û���ɼ���
		param.setValueParamter(i++, record.getAttribute("REQ_CNT"	));	// 6. ��û����
		param.setValueParamter(i++, record.getAttribute("APRV_STAS"	));	// 7. ���λ���
		param.setValueParamter(i++, record.getAttribute("REQ_RSN"	));	// 8. ��û����
		param.setValueParamter(i++, SESSION_USER_ID					 );	// 9. �ۼ���ID
		
		int dmlcount = this.getDao("rbmdao").update("rbo4040_i01", param);
		
		return dmlcount;
	}
	
	
    /**
     * <p> ����û ���� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */	
	protected int updatePartsOutReq(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;

		param.setValueParamter(i++, record.getAttribute("REQ_CNT"	));	// 1. ��û����
		param.setValueParamter(i++, SESSION_USER_ID					 );	// 2. ��û��
		param.setValueParamter(i++, record.getAttribute("REQ_RSN"	));	// 3. ��û����
		param.setValueParamter(i++, record.getAttribute("APRV_STAS"	));	// 4. ���λ���																		
		param.setValueParamter(i++, SESSION_USER_ID					 );	// 5. ������ID
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	));	// 6. ��ǰ�ڵ�
		param.setValueParamter(i++, record.getAttribute("BRNC_CD"	));	// 7. �����ڵ�
		param.setValueParamter(i++, record.getAttribute("SEQ"		));	// 8. ����
		
		int dmlcount = this.getDao("rbmdao").update("rbo4040_u01", param);
		return dmlcount;
	}
	
	
    /**
     * <p> ����û ���� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */	
	protected int deletePartsOutReq(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;
		
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	));	// 1. ��ǰ�ڵ�
		param.setValueParamter(i++, record.getAttribute("BRNC_CD"	));	// 2. �����ڵ�
		param.setValueParamter(i++, record.getAttribute("SEQ"		));	// 3. ����
		
		int dmlcount = this.getDao("rbmdao").update("rbo4040_d01", param);
		return dmlcount;
	}
	
	/**
     * <p> ���λ��� ��ȸ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    public String getAprvStas(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String rtnKey = "";
        
        param.setWhereClauseParameter(i++, record.getAttribute("PARTS_CD"));
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ"));

        PosRowSet keyRecord = this.getDao("rbmdao").find("rbo4040_s04", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        rtnKey = String.valueOf(pr[0].getAttribute("APRV_STAS"));

        return rtnKey;
    }
}
