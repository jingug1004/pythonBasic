/*================================================================================
 * �ý���			: ���߰Ǽ� ����
 * �ҽ����� �̸�	: snis.rbm.business.rbS9310.activity.SaveList.java
 * ���ϼ���		: ���߰Ǽ� ����
 * �ۼ���			: ���ֿ�
 * ����			: 1.0.0
 * ��������		: 2017-05-18
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbs9310.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class NgccSaveList extends SnisActivity {
	
	public NgccSaveList(){}

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
    	int nSaveCount = 0;
    	int nSaveChk   = 0;
    	int nSize      = 0;
    	int nDeleteCount = 0;
    	
    	String sFlag = "N";	//�޼����� ����ڿ��� ����� ������ ���ϴ� Flag
    	String sMsg  = "";
    	PosDataset ds;
    	
        String sDsName = "dsList";
    	
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();	        
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ||
	            	 record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT 	 ) {	
	            	
			        nSaveCount += saveList(record);			        
		        }
	            
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteList(record);	            	
	            }	
	        }	 
        }
  
        Util.setSaveCount  (ctx, nSaveCount);
    }

    /**
     * <p> ���߰Ǽ� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveList(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	   			// �����ڵ�
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));				// �⵵
        param.setValueParamter(i++, record.getAttribute("STND_MM"));				// ��
        param.setValueParamter(i++, record.getAttribute("INSPECTION_CNT"));			// ����Ƚ��
        param.setValueParamter(i++, record.getAttribute("TOT_EXPOSE_CNT"));	   		// �� ���߰Ǽ�
        param.setValueParamter(i++, record.getAttribute("PRSN_CNT"));				// ���� ���Ż��� ���� 
        param.setValueParamter(i++, record.getAttribute("AUTO_CNT"));				// ���� ���Ż��� ���� 
        param.setValueParamter(i++, record.getAttribute("ETC_CNT"));				// ��Ÿ ���Ż��� ���� 
        param.setValueParamter(i++, record.getAttribute("MANAGE_VIOLATION"));	   	// ��������
        param.setValueParamter(i++, record.getAttribute("BASIC_ORDER_VIOLATION"));	// �������� ����
        param.setValueParamter(i++, record.getAttribute("FILE_NM"));				// ���ϸ�
        param.setValueParamter(i++, record.getAttribute("FILE_PATH"));				// ���ϰ��
        param.setValueParamter(i++, SESSION_USER_ID);					   			// �����ID(������)
        param.setValueParamter(i++, SESSION_USER_ID);					   			// �����ID(������)
        param.setValueParamter(i++, record.getAttribute("MEET_CD"));				// ������ڵ�
        param.setValueParamter(i++, record.getAttribute("TOT_CNT"));				// ��
        
        int dmlcount = this.getDao("rbmdao").update("rbs9310_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> ���߰Ǽ�  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteList(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD" ));
        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR" ));
        param.setWhereClauseParameter(i++, record.getAttribute("STND_MM" ));
        param.setWhereClauseParameter(i++, record.getAttribute("MEET_CD" ));
        
        int dmlcount = this.getDao("rbmdao").update("rbs9310_d01", param);
		return dmlcount;
        
    }
}
