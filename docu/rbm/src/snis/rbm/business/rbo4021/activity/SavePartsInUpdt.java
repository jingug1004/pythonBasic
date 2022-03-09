/*
 * ================================================================================
 * �ý��� : ��ǰ �԰� ���� �ҽ����� 
 * �̸� : snis.rbm.business.rbo4021.activity..java 
 * ���ϼ��� : �԰� �̷°��� �˾� 
 * �ۼ��� : ���ѳʿ�
 * ���� : 1.0.0 
 * �������� : 2011-11-10
 * ������������ : 
 * ���������� : ������������ :
 * =================================================================================
 */
package snis.rbm.business.rbo4021.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SavePartsInUpdt extends SnisActivity {
	public SavePartsInUpdt() {
		
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
		
		sDsName = "dsList";
		
		if( ctx.get(sDsName) != null )
		{
			ds	  = (PosDataset) ctx.get(sDsName);
			nSize = ds.getRecordCount();
			
			for ( int i = 0; i < nSize; i++ )
			{
				PosRecord record = ds.getRecord(i);
				
				if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE)
				{
					nTempCnt = updatePartsIn(record);
							   UpdatePartsStock(record);
				}else if( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
				{					
					nTempCnt = insertPartsIn(record);
							   UpdatePartsStock(record);
				}else if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
				{
					nDeleteCount = nDeleteCount + deletePartIn(record);
					  							  DeletePartsStock(record);
				}
				nSaveCount = nSaveCount + nTempCnt;
				continue;
			}
		}
		
		Util.setSaveCount	(ctx, nSaveCount	);
		Util.setDeleteCount	(ctx, nDeleteCount	);
	
		
	}
	
	
    /**
     * <p> �԰���� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
	protected int insertPartsIn(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;
		
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	));	// 1. ��ǰ�ڵ�
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	));	// 2-1). ��ǰ�ڵ�(SEQ)
		param.setValueParamter(i++, record.getAttribute("IN_DT"		));	// 2-2). �԰�����(SEQ)															
		param.setValueParamter(i++, record.getAttribute("IN_DT"		));	// 3. �԰�����
		param.setValueParamter(i++, SESSION_USER_ID					 );	// 4. �԰��� (IN_NO)
		param.setValueParamter(i++, record.getAttribute("IN_CNT"	));	// 5. �԰����
		param.setValueParamter(i++, record.getAttribute("UPDT_RSN"	));	// 6. ��������
		param.setValueParamter(i++, SESSION_USER_ID					 );	// 7. �ۼ���ID

		int dmlcount = this.getDao("rbmdao").update("rbo4021_i01", param);
		
		return dmlcount;
	}
	
	
    /**
     * <p> �԰���� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */	
	protected int updatePartsIn(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;
		
		param.setValueParamter(i++, record.getAttribute("IN_CNT"	));		// 1. �԰����
		param.setValueParamter(i++, record.getAttribute("UPDT_RSN"	));		// 2. ��������
		param.setValueParamter(i++, SESSION_USER_ID					 );		// 3. ������ID
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	));		// 4. ��ǰ�ڵ�
		param.setValueParamter(i++, record.getAttribute("IN_DT"		));		// 5. �԰�����
		param.setValueParamter(i++, record.getAttribute("SEQ"		));		// 6. ����
		
		int dmlcount = this.getDao("rbmdao").update("rbo4021_u01", param);
		
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

		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	 ));	// 1. ��ǰ�ڵ�
		param.setValueParamter(i++, record.getAttribute("IN_CNT"	 ));	// 2. �԰����
		param.setValueParamter(i++, SESSION_USER_ID					  );	// 3. ������ID
		
		int dmlcount = this.getDao("rbmdao").update("rbo4020_u01", param);
		
		return dmlcount;
	}
	
	
    /**
     * <p> �԰���� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */	
	protected int deletePartIn(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;

		param.setValueParamter(i++, SESSION_USER_ID					 );	// 1. ������ID
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	));	// 2. ��ǰ�ڵ�
		param.setValueParamter(i++, record.getAttribute("IN_DT"		));	// 3. �԰�����
		param.setValueParamter(i++, record.getAttribute("SEQ"		));	// 4. ����
		
		int dmlcount = this.getDao("rbmdao").update("rbo4021_d01", param);
		return dmlcount;
		
	}
	
	
    /**
     * <p> ����� ���� (�԰� ���� ��) </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
	protected int DeletePartsStock(PosRecord record)
	{
		PosParameter param = new PosParameter();
		
		int i = 0;
		
		param.setValueParamter(i++, record.getAttribute("IN_CNT"	 ));	// 1. �԰����
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	 ));	// 2. ��ǰ�ڵ�
		param.setValueParamter(i++, SESSION_USER_ID					  );	// 3. ������ID
		param.setValueParamter(i++, record.getAttribute("PARTS_CD"	 ));	// 4. ��ǰ�ڵ�
		
		int dmlcount = this.getDao("rbmdao").update("rbo4020_d02", param);
		
		return dmlcount;
	}
}
