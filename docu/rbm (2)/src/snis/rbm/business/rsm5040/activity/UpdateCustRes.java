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

public class UpdateCustRes extends SnisActivity {
	
	public UpdateCustRes(){}

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

		int nSaveCount	 = 0;
		int nDeleteCount = 0;
		String sDsName	 = "";
		
		PosDataset ds;
		int nSize		 = 0;
		int nTempCnt	 = 0;
		
    	String sPayYear  = (String)ctx.get("PAY_YEAR");		//���޳⵵
		sDsName = "dsPcTax";
		
		if( ctx.get(sDsName) != null )
		{
			ds	  = (PosDataset) ctx.get(sDsName);
			nSize = ds.getRecordCount();
			
			for ( int i = 0; i < nSize; i++ )
			{
				PosRecord record = ds.getRecord(i);
				
				if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE)
				{
					nTempCnt = updateCustRes(record);
				} 
				nSaveCount = nSaveCount + nTempCnt;
				continue;
			}
		}
		
		Util.setSaveCount	(ctx, nSaveCount	);	
		
    }

    /**
     * <p> PC���Ͽ��� ����� ���̺��� �ֹι�ȣ ���� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */	
	protected int updateCustRes(PosRecord record)
	{
		if (!"1".equals(record.getAttribute("CHK").toString())) return 0;
		
		PosParameter param = new PosParameter();
		
		int i = 0;
				
		param.setValueParamter(i++, SESSION_USER_ID					 	);		// 1. ������ID
		param.setValueParamter(i++, record.getAttribute("TAX_MEET_CD"	));		// 2. ������ڵ�
		param.setValueParamter(i++, record.getAttribute("TAX_SELL_CD"	));		// 3. �ó�ڵ�
		param.setValueParamter(i++, record.getAttribute("TAX_TSN"		));		// 4. ���ֱǹ�ȣ
		param.setValueParamter(i++, record.getAttribute("PAY_YEAR"		));		// 5. ���޳⵵
		
		int dmlcount = this.getDao("rbmdao").update("rsm5040_u01", param);
		
		return dmlcount;
	}
	
}
