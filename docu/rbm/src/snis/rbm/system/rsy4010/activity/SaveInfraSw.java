/*================================================================================
 * �ý���			: ����ý���  ����
 * �ҽ����� �̸�	: snis.rbm.business.rsy4010.activity.SaveInfraSw.java
 * ���ϼ���		: ����ý���  ����
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2011-08-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/

package snis.rbm.system.rsy4010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;


public class SaveInfraSw extends SnisActivity {
	
	public SaveInfraSw(){
		
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

        sDsName = "dsInfraSw";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = updateInfraSw(record)) == 0 ) {
		        		nTempCnt = insertInfraSw(record);
		        	}
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteInfraSw(record);	            	
	            }		        
	        }	        
        }

        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


    /**
     * <p> ������ SW �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertInfraSw(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("SW_NM"));			//SW��
        param.setValueParamter(i++, record.getAttribute("BUILD_YEAR"));		//����⵵
        param.setValueParamter(i++, record.getAttribute("BUILD_AMT"));		//������
        param.setValueParamter(i++, record.getAttribute("DEV_COMP"));		//���߾�ü
        param.setValueParamter(i++, record.getAttribute("SYS_DESC"));		//�ý��� ����
        
        param.setValueParamter(i++, record.getAttribute("MANAGER"));		//������
        param.setValueParamter(i++, record.getAttribute("DEV_LANG"));		//���߾��
        param.setValueParamter(i++, record.getAttribute("MAINT_MNG"));		//�������������
        param.setValueParamter(i++, record.getAttribute("MAINT_MNG_TEL"));	//������������ڿ���ó
        param.setValueParamter(i++, record.getAttribute("GOOD_NM"));		//��ǰ��
        
        param.setValueParamter(i++, record.getAttribute("VER_INFO"));		//��������
        param.setValueParamter(i++, record.getAttribute("ETC"));			//��Ÿ
        param.setValueParamter(i++, SESSION_USER_ID);        				//�ۼ���
  
        
        int dmlcount = this.getDao("rbmdao").update("rsy4010_i01", param);
        
        return dmlcount;
    }
    
    
    
    /**
     * <p> ������ SW ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateInfraSw(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;          
        
        param.setValueParamter(i++, record.getAttribute("SW_NM"));			//SW��
        param.setValueParamter(i++, record.getAttribute("BUILD_YEAR"));		//����⵵
        param.setValueParamter(i++, record.getAttribute("BUILD_AMT"));		//������
        param.setValueParamter(i++, record.getAttribute("DEV_COMP"));		//���߾�ü
        param.setValueParamter(i++, record.getAttribute("SYS_DESC"));		//�ý��� ����
        param.setValueParamter(i++, record.getAttribute("MANAGER"));		//������
        
        param.setValueParamter(i++, record.getAttribute("DEV_LANG"));		//���߾��
        param.setValueParamter(i++, record.getAttribute("MAINT_MNG"));		//�������������
        param.setValueParamter(i++, record.getAttribute("MAINT_MNG_TEL"));	//������������ڿ���ó
        param.setValueParamter(i++, record.getAttribute("GOOD_NM"));		//��ǰ��
        param.setValueParamter(i++, record.getAttribute("VER_INFO"));		//��������
        
        param.setValueParamter(i++, record.getAttribute("ETC"));			//��Ÿ
        param.setValueParamter(i++, SESSION_USER_ID);						//������

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("SW_DIST_NO" ));

        int dmlcount = this.getDao("rbmdao").update("rsy4010_u01", param);
        return dmlcount;
    }

    
    
    /**
     * <p> ������ SW  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteInfraSw(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("SW_DIST_NO"      ) );	//SW������ȣ
        
        int dmlcount = this.getDao("rbmdao").update("rsy4010_d01", param);

        return dmlcount;
    }
}
