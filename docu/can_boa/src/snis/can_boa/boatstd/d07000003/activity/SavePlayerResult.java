/*================================================================================
 * �ý���		: �����Ʒð�����
 * �ҽ����� �̸�	: snis.can.system.d02000002.activity.SavePlayerResult.java
 * ���ϼ���		: �ڵ� ����
 * �ۼ���		: ������
 * ����			: 1.0.0
 * ��������		: 2007-01-03
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can_boa.boatstd.d07000003.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;
/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �Խù��� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther �ֹ���
* @version 1.0
*/

public class SavePlayerResult extends SnisActivity 
{
	public SavePlayerResult() { }
	
	/**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
    	//  ����� ���� Ȯ��
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
     	
        int nSize    	= 0;
        int nTempCnt    = 0;
        
        // �Ʒû��� ����       
        ds   = (PosDataset) ctx.get("dsRacerTrngRslt");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds.getRecord(i);
             
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
             {      
            	 if ( (nTempCnt = updatePlayerResult(record)) == 0 ) {
                     nTempCnt = insertPlayerResult(record);
                 }              	 
            	 updateRacerResult(record);
            	 nSaveCount = nSaveCount + nTempCnt;
             }
        }        
                  
        // ��ŸƮ ���� ��� ����       
        ds   = (PosDataset) ctx.get("dsRacerTrngRsltStart");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds.getRecord(i);
             
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
             {      
            	 nTempCnt = mergeUpdateResultStart(ctx, record);
            	 nSaveCount = nSaveCount + nTempCnt;
             }
        }
        
        // ������ �� ��� ����       
        ds   = (PosDataset) ctx.get("dsRacerTrngRsltMngTech");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds.getRecord(i);
             
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
             {      
            	 nTempCnt = mergeUpdateResultMngTech(ctx, record);
            	 nSaveCount = nSaveCount + nTempCnt;
             }
        }
        
        // ���ͺ�Ʈ �����Ǳ� �� ��� ����       
        ds   = (PosDataset) ctx.get("dsRacerTrngRsltManiEx");
        nSize = ds.getRecordCount();

        for ( int i = 0; i < nSize; i++ ) 
        {
             PosRecord record = ds.getRecord(i);
             
             if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
               || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT )
             {      
            	 nTempCnt = mergeUpdateResultManiEx(ctx, record);
            	 nSaveCount = nSaveCount + nTempCnt;
             }
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
     }
     
     
     /**
      * <p> ���α������� �Է� </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		insert ���ڵ� ����
      * @throws  none
      */
  	protected int insertPlayerResult(PosRecord record) 
	{
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setValueParamter(i++, record.getAttribute("TRNG_ASK_SEQ"));
		param.setValueParamter(i++, record.getAttribute("ESTI_DT")); 
		param.setValueParamter(i++, record.getAttribute("RACER_NO"));
		
		param.setValueParamter(i++, record.getAttribute("PER_TURN"));
		param.setValueParamter(i++, record.getAttribute("FORM_TURN"));
		param.setValueParamter(i++, record.getAttribute("FORM_TURN_3"));
		param.setValueParamter(i++, record.getAttribute("FORM_TURN_1"));
		param.setValueParamter(i++, record.getAttribute("ST"));
		param.setValueParamter(i++, record.getAttribute("IMIT_RACE"));
		param.setValueParamter(i++, record.getAttribute("BOAT_TIME"));
		param.setValueParamter(i++, record.getAttribute("BOAT_TIME_GRD"));
		param.setValueParamter(i++, record.getAttribute("MOTOR_MNT"));
		param.setValueParamter(i++, record.getAttribute("BODY_TRNG"));
		param.setValueParamter(i++, record.getAttribute("VIDEO"));
		param.setValueParamter(i++, record.getAttribute("PRIZ_PENAL")); 
		param.setValueParamter(i++, record.getAttribute("CMPL_YN")); 
		param.setValueParamter(i++, record.getAttribute("DETL_EDU_CNTN")); 
		param.setValueParamter(i++, record.getAttribute("MOTOR_MNT_PELLER")); 
		param.setValueParamter(i++, SESSION_USER_ID);

		int dmlcount = this.getDao("candao").insert("tbdo_racer_trng_rslt_io001", param);
		return dmlcount;
	}
     
     /**
      * <p> ���α������� ����  </p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		update ���ڵ� ����
      * @throws  none
      */
     protected int updatePlayerResult(PosRecord record) 
     {
     	PosParameter param = new PosParameter();       					
     	int i = 0;
  
		param.setValueParamter(i++, record.getAttribute("PER_TURN"));
		param.setValueParamter(i++, record.getAttribute("FORM_TURN"));
		param.setValueParamter(i++, record.getAttribute("FORM_TURN_3"));
		param.setValueParamter(i++, record.getAttribute("FORM_TURN_1"));
		param.setValueParamter(i++, record.getAttribute("ST"));
		param.setValueParamter(i++, record.getAttribute("IMIT_RACE"));
		param.setValueParamter(i++, record.getAttribute("BOAT_TIME"));
		param.setValueParamter(i++, record.getAttribute("BOAT_TIME_GRD"));
		param.setValueParamter(i++, record.getAttribute("MOTOR_MNT"));
		param.setValueParamter(i++, record.getAttribute("BODY_TRNG"));
		param.setValueParamter(i++, record.getAttribute("VIDEO"));
		param.setValueParamter(i++, record.getAttribute("PRIZ_PENAL")); 
		param.setValueParamter(i++, record.getAttribute("CMPL_YN")); 
		param.setValueParamter(i++, record.getAttribute("DETL_EDU_CNTN")); 
		param.setValueParamter(i++, record.getAttribute("MOTOR_MNT_PELLER")); 
		param.setValueParamter(i++, SESSION_USER_ID);

		i = 0;    
		param.setWhereClauseParameter(i++, record.getAttribute("TRNG_ASK_SEQ"));
		param.setWhereClauseParameter(i++, record.getAttribute("RACER_NO"));
			
		int dmlcount = this.getDao("candao").update("tbdo_racer_trng_rslt_uo001", param);
		return dmlcount;
     }
     
     
     /**
      * <p> �򰡰��  ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
 	protected int deletePlayerResult(PosRecord record) 
	{
		PosParameter param = new PosParameter();       					
		int i = 0;

		param.setWhereClauseParameter(i++, record.getAttribute("TRNG_ASK_SEQ"));
		param.setWhereClauseParameter(i++, record.getAttribute("RACER_NO"));

		int dmlcount = this.getDao("candao").delete("tbdo_racer_trng_rslt_do001", param);
		return dmlcount;
	}
 	 
    /**
     * <p> �򰡰��  ����</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		delete ���ڵ� ����
     * @throws  none
     */
	protected int updateRacerResult(PosRecord record) 
	{
		PosParameter param = new PosParameter();       					
		int i = 0;
		if ( record.getAttribute("CMPL_YN").equals("Y") ) {
			param.setValueParamter(i++, "002");
		} else if ( record.getAttribute("CMPL_YN").equals("N") ) {
			param.setValueParamter(i++, "003");
		} else {
			param.setValueParamter(i++, "001");
		}

		i = 0;
		param.setWhereClauseParameter(i++, record.getAttribute("TRNG_ASK_SEQ"));
		param.setWhereClauseParameter(i++, record.getAttribute("RACER_NO"));

		int dmlcount = this.getDao("boadao").update("tbeb_trng_ask_racer_rslt_uo001", param);
		return dmlcount;
	}
	 
	

    /**
     * <p> ��ŸƮ �򰡰��  ����</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		delete ���ڵ� ����
     * @throws  none
     */
	protected int mergeUpdateResultStart(PosContext ctx, PosRecord record) 
	{
		String sTrngSeq = ctx.get("TRNG_ASK_SEQ").toString();
		
		PosParameter param = new PosParameter();       					
		int i = 0;
		param.setValueParamter(i++,  sTrngSeq);
		param.setValueParamter(i++,  record.getAttribute("RACER_NO"));
		param.setValueParamter(i++,  record.getAttribute("ESTI_DT"));
		param.setValueParamter(i++,  record.getAttribute("RSLT1"));
		param.setValueParamter(i++,  record.getAttribute("RSLT2"));
		param.setValueParamter(i++,  record.getAttribute("RSLT3"));
		param.setValueParamter(i++,  record.getAttribute("RSLT4"));
		param.setValueParamter(i++,  record.getAttribute("RSLT5"));
		param.setValueParamter(i++,  record.getAttribute("RSLT6"));
		param.setValueParamter(i++,  record.getAttribute("RSLT"));
		param.setValueParamter(i++,  record.getAttribute("ESTIER"));
		
		param.setValueParamter(i++, SESSION_USER_ID);
		
		int dmlcount = this.getDao("candao").update("d07000003_u11", param);
		return dmlcount;
	}

    /**
     * <p> ������ �򰡰��  ����</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		delete ���ڵ� ����
     * @throws  none
     */
	protected int mergeUpdateResultMngTech(PosContext ctx, PosRecord record) 
	{
		String sTrngSeq = ctx.get("TRNG_ASK_SEQ").toString();
		
		PosParameter param = new PosParameter();       					
		int i = 0;
		param.setValueParamter(i++,  sTrngSeq);
		param.setValueParamter(i++,  record.getAttribute("RACER_NO"));
		param.setValueParamter(i++,  record.getAttribute("ESTI_DT"));
		param.setValueParamter(i++,  record.getAttribute("ITEM1"));
		param.setValueParamter(i++,  record.getAttribute("ITEM2"));
		param.setValueParamter(i++,  record.getAttribute("ITEM3"));
		param.setValueParamter(i++,  record.getAttribute("ITEM4"));
		param.setValueParamter(i++,  record.getAttribute("ITEM5"));
		param.setValueParamter(i++,  record.getAttribute("LEAD_TIME"));
		param.setValueParamter(i++,  record.getAttribute("DISQUAL"));
		param.setValueParamter(i++,  record.getAttribute("RSLT"));
		param.setValueParamter(i++,  record.getAttribute("ESTIER"));
		
		param.setValueParamter(i++, SESSION_USER_ID);
		
		int dmlcount = this.getDao("candao").update("d07000003_u12", param);
		return dmlcount;
	}

    /**
     * <p> ��ŸƮ �򰡰��  ����</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		delete ���ڵ� ����
     * @throws  none
     */
	protected int mergeUpdateResultManiEx(PosContext ctx, PosRecord record) 
	{
		String sTrngSeq = ctx.get("TRNG_ASK_SEQ").toString();
		
		PosParameter param = new PosParameter();       					
		int i = 0;
		param.setValueParamter(i++,  sTrngSeq);
		param.setValueParamter(i++,  record.getAttribute("RACER_NO"));
		param.setValueParamter(i++,  record.getAttribute("ESTI_DT"));
		param.setValueParamter(i++,  record.getAttribute("ITEM1"));
		param.setValueParamter(i++,  record.getAttribute("ITEM2"));
		param.setValueParamter(i++,  record.getAttribute("ITEM3"));
		param.setValueParamter(i++,  record.getAttribute("ITEM4"));
		param.setValueParamter(i++,  record.getAttribute("ITEM5"));
		param.setValueParamter(i++,  record.getAttribute("ITEM6"));
		param.setValueParamter(i++,  record.getAttribute("ITEM7"));
		param.setValueParamter(i++,  record.getAttribute("DISQUAL"));
		param.setValueParamter(i++,  record.getAttribute("RSLT"));
		param.setValueParamter(i++,  record.getAttribute("ESTIER"));
		
		param.setValueParamter(i++, SESSION_USER_ID);
		
		int dmlcount = this.getDao("candao").update("d07000003_u13", param);
		return dmlcount;
	}
	
}
