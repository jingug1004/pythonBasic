/*================================================================================
 * �ý���		: ����ǥ �˼����� ����
 * �ҽ����� �̸�: snis.rbm.business.rbr5030.activity.SaveOrganExamBrnc.java
 * ���ϼ���		: SaveOrganExamBrnc
 * �ۼ���		: ������
 * ����			: 1.0.0
 * ��������		: 2016-03-24
 * ������������	: 
 * ����������	: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbr5020.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.RbmJdbcDao;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveVeriStat extends SnisActivity {
	
	public SaveVeriStat(){}
 
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
    	int nSize        = 0;
    	int nTempCnt	 = 0;
    	String sDsName   = "";
    	
    	PosDataset ds;
        
        sDsName = "dsVeriProcStat";
       
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);		        	
		        	nTempCnt = saveOrganExamVeri(record);
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;		        
	        }	        
        }

        Util.setSaveCount  (ctx, nSaveCount  );

    }
    
    /**
     * <p> ����ǥ �˼� Ȯ�� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveOrganExamVeri(PosRecord record) 
    {
    	PosParameter param = new PosParameter();   
        
        int i = 0;

        String sMeetCd = (String)record.getAttribute("MEET_CD");
        
        param.setValueParamter(i++, SESSION_USER_ID);						//�����ID(�ۼ���)
        param.setValueParamter(i++, record.getAttribute("CFM_VALUE"));		//Ȯ������
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));		//���س⵵
        param.setValueParamter(i++, record.getAttribute("STND_MM"));		//���ؿ�                
      
        int dmlcount = 0;
        
        if("003".equals(sMeetCd)) dmlcount = this.getDao("rbmdao").update("rbr5020_m02", param); //����������
        else dmlcount = this.getDao("rbmdao").update("rbr5020_m01", param);						//���������
        
        return dmlcount;
       
    }
    
}