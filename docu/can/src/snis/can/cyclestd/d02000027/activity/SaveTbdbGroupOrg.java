
/*================================================================================
 * �ý���			: ������ֱ����������Ÿ ����
 * �ҽ����� �̸�	: snis.can.system.d02000032.activity.SaveTbdbRaceRecMst.java
 * ���ϼ���		: ������ֱ����������Ÿ ����
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2008-03-21 
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/


package snis.can.cyclestd.d02000027.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ������ֱ����������Ÿ ������ ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SaveTbdbGroupOrg  extends SnisActivity
{    
	
    public SaveTbdbGroupOrg()
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
    	
        PosDataset ds;
        int        nSize   = 0;
        String     sDsName = "";

        for ( int j = 1; j <= 12; j++ ) {
	        sDsName = "dsTbdbGroupOrg" + j;
	    	
	        if ( ctx.get(sDsName) != null ) {
		        ds    = (PosDataset)ctx.get(sDsName);
		        nSize = ds.getRecordCount();
            	
		        for ( int i = 0; i < nSize; i++ ) 
		        {
		            PosRecord record = ds.getRecord(i);
		            //logger.logInfo(record);
		        }
	        }
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
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        // ������
        for ( int j = 1; j <= 12; j++ ) {
	        sDsName = "dsTbdbGroupOrg" + j;
	    	
	        if ( ctx.get(sDsName) != null ) {
		        ds    = (PosDataset) ctx.get(sDsName);
		        nSize = ds.getRecordCount();
		        

	            if (j == 1){
	            	deleteTbdbGroupOrg(ds.getRecord(j));    // ���� ���� 
	            	deleteTbdbRaceRecMst(ds.getRecord(j));  // ����Ÿ ����
	            	deleteTbdbRaceRecDetl(ds.getRecord(j)); // ������ ���� 
	            }
		        for ( int i = 0; i < nSize; i++ ) 
		        {
		            PosRecord record = ds.getRecord(i);
		            //master insert
		        	
		            if (i == 0){

		            	nTempCnt = insertTbdbRaceRecMst(record);
	                	
		            } 

		            record.setAttribute("BACK_NO", String.valueOf (i+1)); 
		       	 
		            //����  insert 
                	nTempCnt = insertTbdbGroupOrg(record);
		            //detail insert 
                	nTempCnt = insertTbdbRaceRecDetl(record);
		        }

		        nSaveCount = nSaveCount + nTempCnt;
	        }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    
    /**
     * <p> ������ֱ����������Ÿ  �Է�  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertTbdbRaceRecMst(PosRecord record) 
    {
   	    logger.logInfo("==========================  ������ֱ����������Ÿ   �Է�   ============================");        
        PosParameter param = new PosParameter();
        int i = 0;
        int dmlcount = 0;      
       
       
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
	        param.setValueParamter(i++, record.getAttribute("ROUND"));
	        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
	        
			param.setValueParamter(i++, SESSION_USER_ID);
			param.setValueParamter(i++, SESSION_USER_ID);
			 	
			dmlcount += this.getDao("candao").insert("tbdb_race_rec_mst_key001", param);
     
        return dmlcount;
    } 
    

    /**
     * <p> ������ֱ������Detail  �Է�  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertTbdbRaceRecDetl(PosRecord record) 
    {
   	    logger.logInfo("==========================  ������ֱ������Detail   �Է�   ============================");        
        PosParameter param = new PosParameter();
        int i = 0;
        int dmlcount = 0;      
            
        	param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
	        param.setValueParamter(i++, record.getAttribute("ROUND"));
	        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
	        param.setValueParamter(i++, record.getAttribute("BACK_NO"));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	        
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_GRD_CD")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("MAT_CD")));
     
	        
			param.setValueParamter(i++, SESSION_USER_ID);
			param.setValueParamter(i++, SESSION_USER_ID);
			 	
			dmlcount += this.getDao("candao").insert("tbdb_race_rec_detl_key001", param);
     
        return dmlcount;
    }

    /**
     * <p> ����  ����  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int deleteTbdbGroupOrg(PosRecord record) 
    {
   	    logger.logInfo("==========================  ����   ����   ============================");        
        PosParameter param = new PosParameter();
        int i = 0;
        int dmlcount = 0;      
       
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
	        param.setValueParamter(i++, record.getAttribute("ROUND"));
	        			 	
			dmlcount += this.getDao("candao").insert("tbdb_group_org_030", param);
     
        return dmlcount;
    } 
    
    /**
     * <p> ������ֱ����������Ÿ  ����  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int deleteTbdbRaceRecMst(PosRecord record) 
    {
   	    logger.logInfo("==========================  ������ֱ����������Ÿ   ����   ============================");        
        PosParameter param = new PosParameter();
        int i = 0;
        int dmlcount = 0;      
       
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
	        param.setValueParamter(i++, record.getAttribute("ROUND"));
	        			 	
			dmlcount += this.getDao("candao").insert("tbdb_group_org_020", param);
     
        return dmlcount;
    } 
    

    /**
     * <p> ������ֱ������Detail  ����  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int deleteTbdbRaceRecDetl(PosRecord record) 
    {
   	    logger.logInfo("==========================  ������ֱ������Detail   ����   ============================");        
        PosParameter param = new PosParameter();
        int i = 0;
        int dmlcount = 0;      
        	
        	param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
	        param.setValueParamter(i++, record.getAttribute("ROUND"));
			 	
			dmlcount += this.getDao("candao").insert("tbdb_group_org_021", param);
     
        return dmlcount;
    }
    

    /**
     * <p> ����  �Է�  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertTbdbGroupOrg(PosRecord record) 
    {
   	    logger.logInfo("==========================  ����   �Է�   ============================");        
        PosParameter param = new PosParameter();
        int i = 0;
        int dmlcount = 0;      
               	
        	param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_YY")));
	        param.setValueParamter(i++, record.getAttribute("ROUND"));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("MAT_CD")));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("RACE_GRD_CD")));
	        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
	        param.setValueParamter(i++, record.getAttribute("BACK_NO"));
	        param.setValueParamter(i++, record.getAttribute("GEAR_RATE"));
	        param.setValueParamter(i++, record.getAttribute("TOT_AVG"));
	        param.setValueParamter(i++, Util.trim(record.getAttribute("GRD")));
	        param.setValueParamter(i++, record.getAttribute("STRATEGY"));
	        param.setValueParamter(i++, record.getAttribute("LEG_TPE"));
	        param.setValueParamter(i++, record.getAttribute("WIN_RATE"));
	        param.setValueParamter(i++, record.getAttribute("HIGH_RANK_RATE"));
	        param.setValueParamter(i++, record.getAttribute("RANK1_CNT"));
	        param.setValueParamter(i++, record.getAttribute("RANK2_CNT"));
	        param.setValueParamter(i++, record.getAttribute("RANK3_CNT"));
	        param.setValueParamter(i++, record.getAttribute("RANK4_CNT"));
	        param.setValueParamter(i++, record.getAttribute("RANK5_CNT"));
	        param.setValueParamter(i++, record.getAttribute("RANK6_CNT"));
	        param.setValueParamter(i++, record.getAttribute("RANK7_CNT"));
	        param.setValueParamter(i++, record.getAttribute("ELIM1_CNT"));
	        param.setValueParamter(i++, record.getAttribute("ELIM2_CNT"));
	        param.setValueParamter(i++, record.getAttribute("ELIM3_CNT"));
	        param.setValueParamter(i++, record.getAttribute("TOT_RANK"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_SCR"));
	        param.setValueParamter(i++, record.getAttribute("BF_ROUND"));
	        param.setValueParamter(i++, record.getAttribute("BF_RACE"));

			param.setValueParamter(i++, SESSION_USER_ID);
			param.setValueParamter(i++, SESSION_USER_ID);
			 	
			dmlcount += this.getDao("candao").insert("tbdb_group_org_031", param);
     
        return dmlcount;
    }

    
}
