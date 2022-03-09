/*================================================================================
 * �ý���		: ��������
 * �ҽ����� �̸�: snis.boa.racer.e03060015.activity.SaveRacerArrangeEsc.java
 * ���ϼ���		: �ּ����ܼ������
 * �ۼ���		: ���ȭ
 * ����			: 1.0.0
 * ��������		: 2007-12-29
 * ������������	: 
 * ����������	: 
 * ������������	: 
=================================================================================*/
package snis.boa.racer.e03060015.activity;

import java.math.BigDecimal;

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
* �����Ͽ� �ּ����ܼ��������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ���ȭ
* @version 1.0
*/
public class SaveRacerArrangeEsc extends SnisActivity
{    
    public SaveRacerArrangeEsc()
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
        
        ds    = (PosDataset) ctx.get("dsOutRacerArrangeEsc");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	
            	nDeleteCount = nDeleteCount + deleteRacerArrangeEsc(record);
            }
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
                    || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	nSaveCount = nSaveCount + saveRacerArrangeEsc(record);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> �ּ����ܼ��� Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveRacerArrangeEsc(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt =  insertRacerArrangeEsc(record);
        return effectedRowCnt;
    }

 
    /**
     * <p>�ּ����ܼ���  �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertRacerArrangeEsc(PosRecord record) 
    {
    	
        PosParameter param = new PosParameter();
        int i = 0;
        int iSeq      	= getSeq(record);
        
        param.setValueParamter(i++, record.getAttribute("SEQ"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("STR_DT"))); 
        param.setValueParamter(i++, Util.trim(record.getAttribute("END_DT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RSN_CD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, String.valueOf(iSeq));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("STR_DT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("END_DT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RSN_CD")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RMK")));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);

        return this.getDao("boadao").update("tbec_arrange_esc_ic001", param);
    }
    
    /**
     * <p>�ּ����ܼ��� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteRacerArrangeEsc(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ".trim()));
        
        return  this.getDao("boadao").update("tbec_arrange_esc_dc001", param);
    }
    
    /**
     * <p> ������������</p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int getSeq(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;        
        PosRowSet rowset = this.getDao("boadao").find("tbec_arrange_esc_fc002", param);

        BigDecimal nCnt = null;
        if (rowset.hasNext())
        {
            PosRow row = rowset.next();
            nCnt = (BigDecimal) row.getAttribute("SEQ".trim());
        }
            
        return nCnt.intValue();
    }      
    
}