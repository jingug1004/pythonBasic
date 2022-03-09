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
package snis.boa.equipment.e06050010.activity;


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
public class SaveSuppCode extends SnisActivity
{    
    public SaveSuppCode()
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
        
        ds    = (PosDataset) ctx.get("dsSuppCd");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	nDeleteCount = nDeleteCount + deleteSuppCode(record);
            } else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE){
            	nSaveCount = nSaveCount + updateSuppCode(record);
            } else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT){
            	nSaveCount = nSaveCount + insertSuppCode(record);
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
    protected int insertSuppCode(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("SUPP_CD"));
        param.setValueParamter(i++, record.getAttribute("SUPP_NM"));
        param.setValueParamter(i++, record.getAttribute("SPEC"));
        param.setValueParamter(i++, record.getAttribute("VENDR_CD"));
        param.setValueParamter(i++, record.getAttribute("DANGA"));
        param.setValueParamter(i++, record.getAttribute("DAFE_JAEGO"));
        param.setValueParamter(i++, record.getAttribute("LOAD_PLACE"));
        param.setValueParamter(i++, SESSION_USER_ID);
        return this.getDao("boadao").update("e06050010_i01", param);
    }

    /**
     * <p>��� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int updateSuppCode(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("SUPP_NM"));
        param.setValueParamter(i++, record.getAttribute("SPEC"));
        param.setValueParamter(i++, record.getAttribute("VENDR_CD"));
        param.setValueParamter(i++, record.getAttribute("DANGA"));
        param.setValueParamter(i++, record.getAttribute("SAFE_JAEGO"));
        param.setValueParamter(i++, record.getAttribute("LOAD_PLACE"));
        param.setValueParamter(i++, SESSION_USER_ID);
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("SUPP_CD"));
        return this.getDao("boadao").update("e06050010_u01", param);
    }

    /**
     * <p>��� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteSuppCode(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("SUPP_CD"));
        
        return  this.getDao("boadao").update("e06050010_d01", param);
    }
   
}
