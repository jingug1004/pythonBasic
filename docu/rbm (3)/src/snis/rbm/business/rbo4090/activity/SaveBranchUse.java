/*
 * ================================================================================
 * �ý��� : ��ǰ ������ �ҽ����� 
 * �̸� : snis.rbm.business.rbo4090.activity..java 
 * ���ϼ��� : ������ǰ�����
 * �ۼ��� : ���ѳʿ�
 * ���� : 1.0.0 
 * �������� : 2011-11-04
 * ������������ : 
 * ���������� : ������������ :
 * =================================================================================
 */
package snis.rbm.business.rbo4090.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveBranchUse extends SnisActivity {
	public SaveBranchUse() {
		
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
		
		sDsName = "dsListDetail";
		
		if( ctx.get(sDsName) != null)
		{
			ds	  = (PosDataset) ctx.get(sDsName);
			nSize = ds.getRecordCount();
			
			for ( int i = 0; i < nSize; i++)
			{
				PosRecord record = ds.getRecord(i);
				
				if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {
					nDeleteCount = nDeleteCount + deleteBranchUse(record);
					UpdateDelPartsStock(record);
				}

				if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
					//����� ������ �Ǹ� ��� �Ұ���
					Double dUseCnt = (Double)record.getAttribute("USE_CNT");
					
					if( getStock(record) - dUseCnt.intValue() >=0 ){
						nTempCnt += insertBranchUse(record);
						UpdatePartsStock(record);
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
				
				if( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
					nTempCnt += updateBranchUse(record);
				}
				
				nSaveCount = nSaveCount + nTempCnt;
				continue;
			}
		}
		
		Util.setSaveCount	(ctx, nSaveCount	);
		Util.setDeleteCount	(ctx, nDeleteCount	);
	}
	
    /**
     * <p> �����԰� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
	protected int insertBranchUse(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;
		
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	));	// 1. ��ǰ�ڵ�
		param.setValueParamter(i++, record.getAttribute("BRNC_CD"	));	// 2. �����ڵ�
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	));	// 3-1. ��ǰ�ڵ�
		param.setValueParamter(i++, record.getAttribute("BRNC_CD"	));	// 3-2. �����ڵ�
		param.setValueParamter(i++, record.getAttribute("USE_DT"	));	// 4. �������
		param.setValueParamter(i++, SESSION_USER_ID					 );	// 5. �����
		param.setValueParamter(i++, record.getAttribute("USE_CNT"	));	// 6. ������
		param.setValueParamter(i++, record.getAttribute("USE_RSN"	));	// 7. ��볻��
		param.setValueParamter(i++, SESSION_USER_ID				  	 );	// 8. �ۼ���ID
																		// 9. �ۼ��Ͻ�
		
		int dmlcount = this.getDao("rbmdao").update("rbo4090_i01", param);
		
		return dmlcount;
	}
	
	
    /**
     * <p> �����԰� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
	protected int updateBranchUse(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;
		
		param.setValueParamter(i++, record.getAttribute("USE_DT"	));	// 1. �������
		param.setValueParamter(i++, record.getAttribute("USE_RSN"	));	// 2. ��볻��
		param.setValueParamter(i++, SESSION_USER_ID				  	 );	// 3. ������ID
		
		i = 0;
		param.setWhereClauseParameter(i++, record.getAttribute("PARTS_CD")); // 4. ��ǰ�ڵ�
		param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));	 // 5. �����ڵ�
		param.setWhereClauseParameter(i++, record.getAttribute("SEQ"	));	 // 6. ����
		
		int dmlcount = this.getDao("rbmdao").update("rbo4090_u04", param);
		
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

		param.setValueParamter(i++, record.getAttribute("USE_CNT"));	// 1. �����԰����
		param.setValueParamter(i++, SESSION_USER_ID					  );	// 2. ������ID	
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"));		// 3. ��ǰ�ڵ�
		param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		// 3. ��ǰ�ڵ�
		
		int dmlcount = this.getDao("rbmdao").update("rbo4090_u02", param);
		
		return dmlcount;
	}
	
	
    /**
     * <p> ����� ����(delete) </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
	protected int UpdateDelPartsStock(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;

		param.setValueParamter(i++, record.getAttribute("USE_CNT"));	// 1. �����԰����
		param.setValueParamter(i++, SESSION_USER_ID					  );	// 2. ������ID	
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"));		// 3. ��ǰ�ڵ�
		param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		// 3. ��ǰ�ڵ�
		
		int dmlcount = this.getDao("rbmdao").update("rbo4090_u03", param);
		
		return dmlcount;
	}
	
	
    /**
     * <p> �����԰� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */	
	protected int deleteBranchUse(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;
		
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	));	// 1. ��ǰ�ڵ�
		param.setValueParamter(i++, record.getAttribute("BRNC_CD"	));	// 2. �����ڵ�
		param.setValueParamter(i++, record.getAttribute("SEQ"		));	// 3. ����
		
		int dmlcount = this.getDao("rbmdao").update("rbo4090_d01", param);
		return dmlcount;
	}
	
	/**
     * <p> ����� ���� ��ȸ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
	protected int getStock(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String rtnKey = "";
        
        param.setWhereClauseParameter(i++, record.getAttribute("PARTS_CD"));
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rbo4090_s03", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        rtnKey = String.valueOf(pr[0].getAttribute("STK_CNT"));

        return Integer.parseInt(rtnKey);
    }
}
