/*================================================================================
 * �ý���			: �뿪 ����
 * �ҽ����� �̸�	: snis.rbm.business.rbr1014.activity.SaveServMana.java
 * ���ϼ���		: �뿪 ����
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-09-18
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbr1014.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
//import com.posdata.glue.dao.vo.PosRow;
//import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveServMana extends SnisActivity {
	
	public SaveServMana(){}

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
    	int nSaveCount     = 0; 
    	int nDeleteCount   = 0; 
    	String sDsName     = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
        
        sDsName = "dsServMana";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	//�⺻Ű �ߺ�üũ
		        	if( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        		if( selectServCnt(record) > 0 ) {
		        			try { 
		            			throw new Exception(); 
			            	} catch(Exception e) {
			            		
			            		String sServCd    = (String)record.getAttribute("SERV_CD");
			            		String sJobTitCd  = (String)record.getAttribute("JOB_TIT_CD");
			            		String sWorkTpyCd = (String)record.getAttribute("WORK_TPY_CD");

			            		this.rollbackTransaction("tx_rbm");
			            		Util.setSvcMsg(ctx, "[ " + selectNm("1", sServCd)    + " ]" +
			            							"[ " + selectNm("2", sWorkTpyCd) + " ]" +
			            							"[ " + selectNm("3", sJobTitCd)  + " ]" + "(��)�� �ߺ��Ǵ� ���Դϴ�.");

			            		return;
			            	}
		        		}
		        	}
		        	
		        	nTempCnt   = saveHistMana(record);
		        	nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteHistMana(record);	            	
	            }		        
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount);
        Util.setDeleteCount(ctx, nDeleteCount);
    }

    /**
     * <p> �뿪 �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveHistMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));		//�⵵
        param.setValueParamter(i++, record.getAttribute("SERV_CD"));		//�뿪�����ڵ�
        param.setValueParamter(i++, record.getAttribute("JOB_TIT_CD"));		//��å�ڵ�
        param.setValueParamter(i++, record.getAttribute("WORK_TPY_CD"));	//�ٹ������ڵ�
        
        param.setValueParamter(i++, record.getAttribute("PRSN_NUM"));		//�ο���
        param.setValueParamter(i++, record.getAttribute("RMK"));			//���        
        param.setValueParamter(i++, SESSION_USER_ID);						//�����ID(�ۼ���)
        param.setValueParamter(i++, SESSION_USER_ID);						//�����ID(������)

        int dmlcount = this.getDao("rbmdao").update("rbr1014_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> �뿪 ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteHistMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));		//�⵵
        param.setValueParamter(i++, record.getAttribute("SERV_CD"));		//�뿪�����ڵ�
        param.setValueParamter(i++, record.getAttribute("JOB_TIT_CD"));		//��å�ڵ�
        
        int dmlcount = this.getDao("rbmdao").update("rbr1014_d01", param);

        return dmlcount;
    }
    
    /**
     * <p> �뿪���� �⺻Ű ���� ��ȸ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int selectServCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));     //�����ڵ�
        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));   //�⵵
        param.setWhereClauseParameter(i++, record.getAttribute("SERV_CD"));     //�뿪�����ڵ�
        param.setWhereClauseParameter(i++, record.getAttribute("JOB_TIT_CD"));  //��å�ڵ�
        param.setWhereClauseParameter(i++, record.getAttribute("WORK_TPY_CD")); //�ٹ������ڵ�
                		
        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbr1014_s02", param);  
        
        PosRow pr[] = rtnRecord.getAllRow();
        
        String rtnCnt = String.valueOf(pr[0].getAttribute("CNT"));
        
        return Integer.valueOf(rtnCnt).intValue();
    }
    
    /**
     * <p> �ڵ� �̸� ��ȸ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected String selectNm(String sGrpCd, String sCd) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, sGrpCd);     //GRP_CD
        param.setWhereClauseParameter(i++, sCd);        //CD
                		
        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbr1014_s03", param);  
        
        PosRow pr[] = rtnRecord.getAllRow();
        
        String rtnCnt = String.valueOf(pr[0].getAttribute("CD_NM"));
        
        return rtnCnt;
    }
}
