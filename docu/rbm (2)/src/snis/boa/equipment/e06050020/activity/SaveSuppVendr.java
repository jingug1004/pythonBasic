/*================================================================================
 * �ý���			: ��� ���� 
 * �ҽ����� �̸�	: snis.boa.equipment.e06010010.activity.SaveEquip.java
 * ���ϼ���		: ��� ���� 
 * �ۼ���			: �輺�� 
 * ����			: 1.0.0
 * ��������		: 2007-11-22
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06050020.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* ���� ��Ʈ�� ��� ���� ������ ���, ����, ���� �Ѵ�.
* @auther �輺�� 
* @version 1.0
*/
public class SaveSuppVendr extends SnisActivity
{    
    public SaveSuppVendr()
    {
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

    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsVender");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	nDeleteCount = nDeleteCount + deleteSuppVendr(record);
            } else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE){
            	nSaveCount = nSaveCount + updateSuppVendr(record);
            } else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT){
            	nSaveCount = nSaveCount + insertSuppVendr(record);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }


    /**
     * <p>��� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertSuppVendr(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("VENDR_NM"));
        param.setValueParamter(i++, record.getAttribute("SAUP_NO"));
        param.setValueParamter(i++, record.getAttribute("VENDR_CEO"));
        param.setValueParamter(i++, record.getAttribute("TEL_NO"));
        param.setValueParamter(i++, record.getAttribute("FAX_NO"));
        param.setValueParamter(i++, record.getAttribute("ZIP"));
        param.setValueParamter(i++, record.getAttribute("ADDR"));
        param.setValueParamter(i++, record.getAttribute("START_DEAL"));
        param.setValueParamter(i++, record.getAttribute("MGR_NM"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, SESSION_USER_ID);
        return this.getDao("boadao").update("e06050020_i01", param);
    }

    /**
     * <p>��� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int updateSuppVendr(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("VENDR_NM"));
        param.setValueParamter(i++, record.getAttribute("SAUP_NO"));
        param.setValueParamter(i++, record.getAttribute("VENDR_CEO"));
        param.setValueParamter(i++, record.getAttribute("TEL_NO"));
        param.setValueParamter(i++, record.getAttribute("FAX_NO"));
        param.setValueParamter(i++, record.getAttribute("ZIP"));
        param.setValueParamter(i++, record.getAttribute("ADDR"));
        param.setValueParamter(i++, record.getAttribute("START_DEAL"));
        param.setValueParamter(i++, record.getAttribute("MGR_NM"));
        param.setValueParamter(i++, record.getAttribute("RMK"));
        param.setValueParamter(i++, SESSION_USER_ID);
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("VENDR_CD"));
        return this.getDao("boadao").update("e06050020_u01", param);
    }

    /**
     * <p>��� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteSuppVendr(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("VENDR_CD"));
        
        return  this.getDao("boadao").update("e06050020_d01", param);
    }
   
}
