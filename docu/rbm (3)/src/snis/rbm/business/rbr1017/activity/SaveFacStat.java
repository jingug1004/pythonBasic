/*================================================================================
 * �ý���			: �ü���Ȳ ����
 * �ҽ����� �̸�	: snis.rbm.business.rbr1017.activity.SaveFacStat.java
 * ���ϼ���		: �뿪 ����
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-09-19
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbr1017.activity;

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

public class SaveFacStat extends SnisActivity {
	
	public SaveFacStat(){}

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
        
        sDsName = "dsFacStat";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	//�⺻Ű �ߺ�üũ
		        	if( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        		if( selectFacCnt(record) > 0 ) {
		        			try { 
		            			throw new Exception(); 
			            	} catch(Exception e) {
			            		
			            		Double dFloor = (Double)record.getAttribute("FLOOR");
			            		int nFloor    = dFloor.intValue();
			            		String sFacCd = (String)record.getAttribute("FAC_CD");

			            		this.rollbackTransaction("tx_rbm");
			            		Util.setSvcMsg(ctx, "[ " + nFloor                + "�� ]" +
			            							"[ " + selectNm("6", sFacCd) + " ]"  + "(��)�� �ߺ��Ǵ� ���Դϴ�.");
			            							 

			            		return;
			            	}
		        		}
		        	}
		        	
		        	nTempCnt = saveFacStat(record);
		        	nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
		        	nDeleteCount = nDeleteCount + deleteFacStat(record);	            	
	            }		        
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount);
        Util.setDeleteCount(ctx, nDeleteCount);
    }

    /**
     * <p> �ü���Ȳ �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveFacStat(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        //pk set 
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));		//�⵵
        param.setValueParamter(i++, record.getAttribute("FLOOR"));			//����
        param.setValueParamter(i++, record.getAttribute("FAC_CD"));			//�ü��ڵ�
        param.setValueParamter(i++, record.getAttribute("FAC_GBN"));		//�ü������ڵ�
        
        param.setValueParamter(i++, record.getAttribute("AREA_SQMT"));		//����(��)
        param.setValueParamter(i++, record.getAttribute("AREA_PY"));		//����(��)
        param.setValueParamter(i++, record.getAttribute("AMT"));			//����(�Ǹſ�)
        param.setValueParamter(i++, record.getAttribute("RMK"));			//���     
        param.setValueParamter(i++, SESSION_USER_ID);						//�����ID(�ۼ���)
        
        param.setValueParamter(i++, SESSION_USER_ID);						//�����ID(������)
        
        int dmlcount = this.getDao("rbmdao").update("rbr1017_m01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> �ü���Ȳ ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteFacStat(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));		//�⵵
        param.setValueParamter(i++, record.getAttribute("FLOOR"));			//����
        param.setValueParamter(i++, record.getAttribute("FAC_CD"));			//�ü��ڵ�
        
        int dmlcount = this.getDao("rbmdao").update("rbr1017_d01", param);

        return dmlcount;
    }
    
    /**
     * <p> �ü���Ȳ �⺻Ű ���� ��ȸ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int selectFacCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));     //�����ڵ�
        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));   //�⵵
        param.setWhereClauseParameter(i++, record.getAttribute("FLOOR")); 		//��
        param.setWhereClauseParameter(i++, record.getAttribute("FAC_CD")); 		//�ü��ڵ�
                		
        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbr1017_s03", param);  
        
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
