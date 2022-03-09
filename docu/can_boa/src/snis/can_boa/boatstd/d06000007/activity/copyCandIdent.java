/*================================================================================
 * �ý���			: �ĺ��� �Ż� ����
 * �ҽ����� �̸�	: snis.can.system.d06000007.activity.copyCandIdent.java
 * ���ϼ���		: �ĺ��� �Ż� ����
 * �ۼ���			: ���μ�
 * ����			: 1.0.0
 * ��������		: 2008-03-05
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can_boa.boatstd.d06000007.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;


/**
* �ĺ������� �հ��ڸ� �ĺ����Ż� ������ �����Ѵ�.
* @auther ������
* @version 1.0
*/ 
public class copyCandIdent  extends SnisActivity
{    
	
    public copyCandIdent ()
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

    	PosDataset ds;
        int nSize        = 0;
        
        //�������̺� ����	
        ds    = (PosDataset) ctx.get("dsCopyPerioNo");
        nSize = ds.getRecordCount();
        
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
              
           	nSaveCount = nSaveCount + CopyCandIdent(record);
        }
        
        
    }
    /**
     * <p> �ĺ����Ż����̺� Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int CopyCandIdent(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = insertCandIdent(record);
    	return effectedRowCnt;
    }
    
    /**
     * <p> �ĺ����Ż����̺�  Insert </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    
     protected int insertCandIdent(PosRecord record)
    {
        
    	PosParameter param = new PosParameter();
         
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO" ));
     	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO" ));
     	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO" ));
     	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO" ));
     	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO" ));
     	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO" ));
     	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO" ));
     	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO" ));
     	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO" ));
     	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO" ));
     	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO" ));
     	param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO" ));
     	
     	int inscount  = this.getDao("candao").insert("d06000007_copyCandIdent", param);
        return inscount;
    	 
    	 
    }
     
}