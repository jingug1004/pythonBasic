/*================================================================================
 * �ý���		    : �л����
 * �ҽ����� �̸�	: snis.can.cyclestd.d02000008.activity.SearchPerioExam.java
 * ���ϼ���		: �����ڰݽ���
 * �ۼ���		    : ������
 * ����			: 1.0.0
 * ��������		: 2007-01-18
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.cyclestd.d02000045.activity;

import java.util.ArrayList;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;
/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �Խù��� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther �ֹ���
* @version 1.0
*/


public class SavePerioExam extends SnisActivity 
{
	public SavePerioExam() { }
	
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
    	
    	PosDataset ds;
        String     sDsName = "";
        int        nSize   = 0;

        sDsName = "dsPerioExam1";
        if ( ctx.get(sDsName) != null ) {
        	ds   = (PosDataset)ctx.get(sDsName);
        	nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
        }
        
        saveState(ctx);
        return PosBizControlConstants.SUCCESS;
    }
   
    
    /**
     * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
     * @param   ctx	PosContext ���񱸺� �����
     * @return  none
     * @throws  none
     */
     protected void saveState(PosContext ctx) 
     {
		int nSaveCount   = 0; 

    	PosDataset ds;
        String     sDsName  = "";
        int        nSize    = 0;
        int        nTempCnt = 0;
 	
 		ArrayList alItemCd = new ArrayList();

 		// �����ŽǱ� ����
		alItemCd.clear();
		alItemCd.add("301");
		alItemCd.add("302");
		alItemCd.add("303");
		alItemCd.add("304");
		alItemCd.add("305");
		alItemCd.add("306");
		 
    	ctx.put("EXAM_CD",     "004");
    	ctx.put("ITEM_GBN_CD", "003");

		//�Ǳ� ����       
        sDsName = "dsPerioExam1";
        if ( ctx.get(sDsName) != null ) {
        	ds   = (PosDataset)ctx.get(sDsName);
        	nSize = ds.getRecordCount();
        	for ( int i = 0; i < nSize; i++ ) {
                PosRecord record = ds.getRecord(i);
                for ( int j = 0; j < alItemCd.size(); j++ ) {
                	ctx.put("ITEM_CD", alItemCd.get(j));
	                if ( (nTempCnt = updatePerioExam(record, ctx, (j + 1))) == 0 ) {
	                	nTempCnt = insertPerioExam(record, ctx, (j + 1));
	                }
	                
	                nSaveCount = nSaveCount + nTempCnt;
                }
            }
        }
        
		Util.setSaveCount  (ctx, nSaveCount     );
     }
     
     /**
      * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
      * @param   ctx	 PosContext	ȯ�����ǥ �����
      * @return  none
      * @throws  none
      */
 	protected int insertPerioExam(PosRecord record, PosContext ctx, int nSeq) 
 	{
		PosParameter param = new PosParameter();
		
		int i = 0;
		param.setValueParamter(i++, record.getAttribute("RACER_PERIO_NO"));
		param.setValueParamter(i++, ctx   .get         ("EXAM_CD"       ));
		param.setValueParamter(i++, ctx   .get         ("ITEM_GBN_CD"   ));
		param.setValueParamter(i++, ctx   .get         ("ITEM_CD"       ));
		param.setValueParamter(i++, record.getAttribute("CAND_NO"       ));
        param.setValueParamter(i++, record.getAttribute("EXAM_REC_" + nSeq));
        param.setValueParamter(i++, record.getAttribute("EXAM_SCR_" + nSeq));
		param.setValueParamter(i++, SESSION_USER_ID);
		param.setValueParamter(i++, SESSION_USER_ID);
 		
 		int dmlcount = this.getDao("candao").update("tbdb_racer_exam_cyc_ib001", param);
 		
 		return dmlcount;
 	}
 	
    /**
     * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
     * @param   ctx	 PosContext	ȯ�����ǥ �����
     * @return  none
     * @throws  none
     */
	protected int updatePerioExam(PosRecord record, PosContext ctx, int nSeq) 
	{
        PosParameter param = new PosParameter();

        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("EXAM_REC_" + nSeq));
        param.setValueParamter(i++, record.getAttribute("EXAM_SCR_" + nSeq));
		param.setValueParamter(i++, SESSION_USER_ID);
		
		i = 0;
		param.setWhereClauseParameter(i++, record.getAttribute("RACER_PERIO_NO"));
		param.setWhereClauseParameter(i++, ctx   .get         ("EXAM_CD"       ));
		param.setWhereClauseParameter(i++, ctx   .get         ("ITEM_GBN_CD"   ));
		param.setWhereClauseParameter(i++, ctx   .get         ("ITEM_CD"       ));
		param.setWhereClauseParameter(i++, record.getAttribute("CAND_NO"       ));

		int dmlcount = this.getDao("candao").update("tbdb_racer_exam_cyc_ub001", param);
		
		return dmlcount;
	}
}
