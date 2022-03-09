/*================================================================================
 * �ý���			: ��������
 * �ҽ����� �̸�	: snis.can.racer.e03100010.activity.SaveRacerFood.java
 * ���ϼ���		: ���� �ĺ� �������
 * �ۼ���			: ���ȭ
 * ����			: 1.0.0
 * ��������		: 2007-01-12
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.racer.e03100010.activity;

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
* �����Ͽ� ����� ���������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ���ȭ 
* @version 1.0
*/ 
public class SaveRacerFood extends SnisActivity
{    
    public SaveRacerFood()
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
        
        ds    = (PosDataset) ctx.get("dsOutRacerFood");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
                    || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
            {
            	nSaveCount = nSaveCount + saveRacerFood(record);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ���� �ĺ� Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveRacerFood(PosRecord record)
    {
    	int effectedRowCnt = 0;
   		effectedRowCnt =insertRacerFood(record);

        return effectedRowCnt;
    }

    /**
     * <p>���� �ĺ� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertRacerFood(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        int iSeq      	= getSeq(record);
        String tmpSeq = "0";        
        if (record.getAttribute("SEQ".trim()) != null)	tmpSeq = String.valueOf(record.getAttribute("SEQ".trim()));

        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR")));
        param.setValueParamter(i++, String.valueOf(tmpSeq));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD")));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("FD_EXP_DT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO")));
        param.setValueParamter(i++, record.getAttribute("BRKF_PRC"));
        param.setValueParamter(i++, record.getAttribute("LUNCH_PRC"));
        param.setValueParamter(i++, record.getAttribute("DINN_PRC"));
        param.setValueParamter(i++, record.getAttribute("SNCK_PRC1"));
        param.setValueParamter(i++, record.getAttribute("SNCK_PRC2"));
        param.setValueParamter(i++, record.getAttribute("SUM_AMT"));
        param.setValueParamter(i++, SESSION_USER_ID);       
        param.setValueParamter(i++, String.valueOf(iSeq));
        param.setValueParamter(i++, Util.trim(record.getAttribute("STND_YEAR")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("MBR_CD")));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, Util.trim(record.getAttribute("FD_EXP_DT")));
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO")));
        param.setValueParamter(i++, record.getAttribute("BRKF_PRC"));
        param.setValueParamter(i++, record.getAttribute("LUNCH_PRC"));
        param.setValueParamter(i++, record.getAttribute("DINN_PRC"));
        param.setValueParamter(i++, record.getAttribute("SNCK_PRC1"));
        param.setValueParamter(i++, record.getAttribute("SNCK_PRC2"));
        param.setValueParamter(i++, record.getAttribute("SUM_AMT"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);        
       
        return this.getDao("boadao").update("tbec_racer_fd_exp_ic001", param);
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
        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));      
        PosRowSet rowset = this.getDao("boadao").find("tbec_racer_fd_exp_fc001", param);

        BigDecimal nCnt = null;
        if (rowset.hasNext())
        {
            PosRow row = rowset.next();
            nCnt = (BigDecimal) row.getAttribute("SEQ".trim());
        }
            
        return nCnt.intValue();
    } 
}