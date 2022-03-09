/*================================================================================
 * �ý���		: ��������
 * �ҽ����� �̸�: snis.boa.racer.e03070010.activity.SavePointBasisInfo.java
 * ���ϼ���		: ����� �����������
 * �ۼ���		: ���ȭ
 * ����			: 1.0.0
 * ��������		: 2007-01-04
 * ������������	: 
 * ����������	: 
 * ������������	: 
=================================================================================*/
package snis.boa.racer.e03070010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ����� ���������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ���ȭ
* @version 1.0
*/
public class SavePointBasisInfo extends SnisActivity
{    
    public SavePointBasisInfo()
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
        
        ds    = (PosDataset) ctx.get("dsOutPointBasis");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){            	
            	nDeleteCount = nDeleteCount + deletePointBasis(record);
            }
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
                    || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	nSaveCount = nSaveCount + savePointBasis(record);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ����� ���� Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int savePointBasis(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt =insertPointBasis(record);
        
        return effectedRowCnt;
    }

    /**
     * <p>����� ���� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertPointBasis(PosRecord record) 
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        String sSeq      	= getPointBasisMax(record);
        String tmpSeq 		= "0";        
        if (record.getAttribute("RWNPT_CD".trim()) != null)	tmpSeq = String.valueOf(record.getAttribute("RWNPT_CD".trim()));

        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR")));
        param.setValueParamter(i++, String.valueOf(tmpSeq));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RWNPT_GBN")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TITL")));
        param.setValueParamter(i++, record.getAttribute("SCR"));
        param.setValueParamter(i++, SESSION_USER_ID);       
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR")));
        param.setValueParamter(i++, sSeq);
        param.setValueParamter(i++, Util.trim(record.getAttribute("RWNPT_GBN")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("TITL")));
        param.setValueParamter(i++, record.getAttribute("SCR"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").update("tbec_rwnpt_point_bas_ic001", param);
    }

    /**
     * <p>����� ���� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deletePointBasis(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));
        param.setWhereClauseParameter(i++, record.getAttribute("RWNPT_CD"));
        
        return  this.getDao("boadao").update("tbec_rwnpt_point_bas_dc001", param);
    }
    
    /**
     * <p> Max �� �������� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		Max
     * @throws  none
     */
    protected String getPointBasisMax(PosRecord record)
    {
        PosParameter param = new PosParameter();
        
        param.setWhereClauseParameter(0, Util.trim(record.getAttribute("STND_YEAR".trim())));
        PosRowSet rowset = this.getDao("boadao").find("tbec_rwnpt_point_bas_fc002", param);

        String nStr = null;
        if (rowset.hasNext())
        {
            PosRow row = rowset.next();
            nStr = (String)row.getAttribute("strMax".trim());
        }
            
        return nStr;
    }
}