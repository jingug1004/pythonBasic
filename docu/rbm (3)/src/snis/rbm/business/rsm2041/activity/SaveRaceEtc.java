/*================================================================================
 * �ý���			: ����Ư�̻���
 * �ҽ����� �̸�	: snis.rbm.business.rsm2041.activity.SaveRaceEtc.java
 * ���ϼ���		: ����Ư�̻��� ���� / ����
 * �ۼ���			: �̱⼮
 * ����			: 1.0.0
 * ��������		: 2011-09-22
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rsm2041.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveRaceEtc extends SnisActivity {
	public SaveRaceEtc(){}
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
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        sDsName = "dsEtc";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	nTempCnt = updateRaceEtc(record);
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        		        
	        }	        
        }

        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> ���� Ư�̻��� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRaceEtc(PosRecord record) 
    {	   
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MEET_CD"));     // 1.��� ���� ���� �ڵ�      
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));   // 2.���س⵵
        param.setValueParamter(i++, record.getAttribute("TMS"));    	 // 3.ȸ��
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));     // 4.����
        param.setValueParamter(i++, record.getAttribute("SPEC_DESC"));   // 5.Ư�̻��� ����     
        param.setValueParamter(i++, record.getAttribute("RAIN"));   	 // 6.�񿩺�
        param.setValueParamter(i++, record.getAttribute("REST"));   	 // 7.���Ͽ���
        param.setValueParamter(i++, record.getAttribute("REMARKETC"));   // 8.��Ÿ Ư�̻���
        param.setValueParamter(i++, record.getAttribute("SUBJECTRACE"));   // 9.�����ֿ���
        param.setValueParamter(i++, SESSION_USER_ID);                    // 10.�α��� ����� ���̵�	
        
        
        int dmlcount = this.getDao("rbmdao").update("rsm2041_m01", param);
        
        return dmlcount;
    }
    
    
}
