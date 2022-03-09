/*================================================================================
 * �ý���			: �� ���� 
 * �ҽ����� �̸�	: snis.can.cyclestd.d02000035.activity.SaveBatch3Record.java
 * ���ϼ���		: ��������
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-03-19
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.cyclestd.d02000035.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* �·�/������ �����ڷ� �����Ѵ�.
* @auther ������
* @version 1.0
*/
public class SaveBatch5Record extends SnisActivity
{    
    public SaveBatch5Record()
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
        //�Ѽ������� �����ڷḦ  ���ʻ���
        ds    = (PosDataset) ctx.get("dsOutBatch");
        nSize = ds.getRecordCount();
        
        for ( int i = 0; i < nSize; i++ ){
        
        	PosRecord record = ds.getRecord(i);
           	nSaveCount = nSaveCount + SaveBatch5(record);
        }

        Util.setSaveCount  (ctx, nSaveCount     );
         
        
    }

    /**
     * <p> �Ѽ������� �����ڷ� Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int SaveBatch5(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateTbdbRecTot(record);
    	
    	
    	return effectedRowCnt;
    }
     
    /**
     * <p> �Ѽ������� �������̺� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateTbdbRecTot(PosRecord record)
    {
        
        
         
        PosParameter param = new PosParameter();
        int i = 0;
        
      
        param.setValueParamter(i++, record.getAttribute("RACE_YY"));
        param.setValueParamter(i++, record.getAttribute("ROUND_FROM")); 
        param.setValueParamter(i++, record.getAttribute("ROUND_TO"));
        param.setValueParamter(i++, record.getAttribute("RACE_YY"));
        param.setValueParamter(i++, record.getAttribute("ROUND_FROM")); 
        param.setValueParamter(i++, record.getAttribute("ROUND_TO"));
        param.setValueParamter(i++, record.getAttribute("RACE_YY"));
        param.setValueParamter(i++, record.getAttribute("RACE_YY"));
        return  this.getDao("candao").update("tbdb_rec_tot_ub004", param); 
        
    }
  
}