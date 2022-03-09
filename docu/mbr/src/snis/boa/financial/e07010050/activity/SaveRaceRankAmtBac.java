/*================================================================================
 * �ý���			: ��� ���� 
 * �ҽ����� �̸�	: snis.boa.RaceRankAmtBacment.e06010010.activity.SaveRaceRankAmtBac.java
 * ���ϼ���		: ���ּ����� ������� 
 * �ۼ���			: �輺�� 
 * ����			: 1.0.0
 * ��������		: 2007-11-22
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.financial.e07010050.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* ���ּ����� ������� ���� ������ ���, ����, ���� �Ѵ�.
* @auther �輺�� 
* @version 1.0
*/
public class SaveRaceRankAmtBac extends SnisActivity
{    
	final Integer RANK_1 = new Integer(1);
	final Integer RANK_2 = new Integer(2);
	final Integer RANK_3 = new Integer(3);
	final Integer RANK_4 = new Integer(4);
	final Integer RANK_5 = new Integer(5);
	final Integer RANK_6 = new Integer(6);
	final Integer RANK_7 = new Integer(70);
	final Integer RANK_8 = new Integer(80);
	final Integer MBS_FEE= new Integer(7);
	
	final String RANK_1_AMT_COLUNM = "RANK_1_AMT";
	final String RANK_2_AMT_COLUNM = "RANK_2_AMT";
	final String RANK_3_AMT_COLUNM = "RANK_3_AMT";
	final String RANK_4_AMT_COLUNM = "RANK_4_AMT";
	final String RANK_5_AMT_COLUNM = "RANK_5_AMT";
	final String RANK_6_AMT_COLUNM = "RANK_6_AMT";
	final String RANK_7_AMT_COLUNM = "RANK_7_AMT";
	final String RANK_8_AMT_COLUNM = "RANK_8_AMT";
	final String MBS_FEE_RATE = "MBS_FEE_RATE";
	
    public SaveRaceRankAmtBac()
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
        
        ds    = (PosDataset) ctx.get("dsOutRaceRankAmtBac");
        nSize = ds.getRecordCount();
        
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	deleteRaceRankAmtBac(record);
            }else{
            	nSaveCount += saveRaceRankAmtBac(record, RANK_1, RANK_1_AMT_COLUNM);
            	nSaveCount += saveRaceRankAmtBac(record, RANK_2, RANK_2_AMT_COLUNM);
            	nSaveCount += saveRaceRankAmtBac(record, RANK_3, RANK_3_AMT_COLUNM);
            	nSaveCount += saveRaceRankAmtBac(record, RANK_4, RANK_4_AMT_COLUNM);
            	nSaveCount += saveRaceRankAmtBac(record, RANK_5, RANK_5_AMT_COLUNM);
            	nSaveCount += saveRaceRankAmtBac(record, RANK_6, RANK_6_AMT_COLUNM);
            	nSaveCount += saveRaceRankAmtBac(record, RANK_7, RANK_7_AMT_COLUNM);
            	nSaveCount += saveRaceRankAmtBac(record, RANK_8, RANK_8_AMT_COLUNM);
            	nSaveCount += saveRaceRankAmtBac(record, MBS_FEE, MBS_FEE_RATE);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ���ּ����� �������Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveRaceRankAmtBac(PosRecord record, Integer rank, String rankColumnName)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateRaceRankAmtBac(record, rank, rankColumnName);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertRaceRankAmtBac(record, rank, rankColumnName);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> ���ּ����� �������  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRaceRankAmtBac(PosRecord record, Integer rank, String rankColumnName)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute(rankColumnName));  
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));  
        param.setValueParamter(i++, record.getAttribute("RACE_KINDS_CD")); 
        param.setValueParamter(i++, record.getAttribute("RACE_DGRE_CD"));  
        param.setValueParamter(i++, rank); 
        return this.getDao("boadao").update("tbeg_race_rank_amt_bac_uf001", param);
    }
    
    

    /**
     * <p>���ּ����� ������� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertRaceRankAmtBac(PosRecord record,Integer rank, String rankColumnName)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));  
        param.setValueParamter(i++, record.getAttribute("RACE_KINDS_CD")); 
        param.setValueParamter(i++, record.getAttribute("RACE_DGRE_CD"));  
        param.setValueParamter(i++, rank);
        param.setValueParamter(i++, record.getAttribute(rankColumnName));  
        param.setValueParamter(i++, SESSION_USER_ID);
        return this.getDao("boadao").update("tbeg_race_rank_amt_bac_if001", param);
    }
    
    /**
     * <p>���ּ����� ������� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int deleteRaceRankAmtBac(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));  
        param.setValueParamter(i++, record.getAttribute("RACE_KINDS_CD")); 
        param.setValueParamter(i++, record.getAttribute("RACE_DGRE_CD"));  
        return this.getDao("boadao").update("tbeg_race_rank_amt_bac_df001", param);
    }
}
