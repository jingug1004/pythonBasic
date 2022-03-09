/*================================================================================
 * �ý���			: ��ǰ���� 
 * �ҽ����� �̸�	: snis.boa.equipment.e06020040.activity.SaveEnt.java
 * ���ϼ���		: �԰����� �����Ѵ�.  
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-02-11
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06020040.activity;


import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.Util;

/**
* �԰��Ͽ� ���� ������ ���, ����, ���� �Ѵ�.
* @auther ������ 
* @version 1.0
*/
public class SaveEnt extends SnisActivity
{    
    public SaveEnt()
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
    	//����� ���� Ȯ��
    	if ( setUserInfo(ctx)== null || !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	
    	//�԰���� ���� 
        saveState(ctx);
        
        //�԰����, ��ǰ���̺� ����� ����
        saveOrderState(ctx);
        return PosBizControlConstants.SUCCESS;
    }

    
    protected void saveOrderState(PosContext ctx) 
    {
    	 //�԰����, ��ǰ���̺� ����� ����
    	(new SaveEntState()).runActivity(ctx);
    }
   
    
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
    	
    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutEnt");
        String	stndYear   = (String) ctx.get("STND_YEAR");
        String	mbrCd   = (String) ctx.get("MBR_CD");
        String	entSeq   = (String) ctx.get("ENT_SEQ");
        String  nextEntSeq = (String) ctx.get("NEXT_ENT_SEQ");
        String	entDt   = (String) ctx.get("ENT_DT");
        nSize = ds.getRecordCount();
        
        if(nSize > 0){
        	saveOrderM(stndYear,mbrCd,entSeq,entDt,nextEntSeq);    
        }
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
           	nSaveCount = nSaveCount + saveOrder(record,stndYear,mbrCd,entSeq,entDt,nextEntSeq);            	
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
  
    /**
     * <p> ������ Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveOrder(PosRecord record, String stndYear, String mbrCd, String entSeq, String entDt, String nextEntSeq)
    {
    	int effectedRowCnt = 0;
    	
    	if(entSeq.equals("") || entSeq == null){
    		effectedRowCnt =mergeEntParts(record, nextEntSeq);    	
    	}
    	else
    	{
    		effectedRowCnt =mergeEntParts(record, entSeq);
    	}
    	
    	return effectedRowCnt;
    }
    /**
     * <p> ������ Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveOrderM(String stndYear, String mbrCd, String entSeq, String entDt, String nextEntSeq)
    {
    	int effectedRowCnt = 0;
    	if(entSeq.equals("") || entSeq == null){
    		effectedRowCnt =insertEnt(stndYear,mbrCd,entDt,nextEntSeq);   	
    	}
    	
        return effectedRowCnt;
    }
    /**
     * <p>�԰��� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertEnt(String stndYear, String mbrCd, String entDt,String nextEntSeq) 
    {
        PosParameter param = new PosParameter();
        int i = 0;        
        
        param.setValueParamter(i++, stndYear);
	    param.setValueParamter(i++, mbrCd); 
	    param.setValueParamter(i++, nextEntSeq);
	    param.setValueParamter(i++, entDt);
    	param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").update("tbef_ent_if001", param);
    }    
    /**
     * <p>�԰��� ������ �Է�/��</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int mergeEntParts(PosRecord record, String nextEntSeq) 
    {
        PosParameter param = new PosParameter();
        int i = 0; 
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));	
        param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        //param.setValueParamter(i++, record.getAttribute("ENT_SEQ")); 
        param.setValueParamter(i++, nextEntSeq);
        param.setValueParamter(i++, record.getAttribute("ORDER_SEQ"));
        param.setValueParamter(i++, record.getAttribute("PARTS_CD"));
        param.setValueParamter(i++, record.getAttribute("PRICE_STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("ENT_CNT"));		  
		param.setValueParamter(i++, SESSION_USER_ID);
		param.setValueParamter(i++, nextEntSeq);
        return this.getDao("boadao").update("tbef_ent_mf001", param);
    }    
}
