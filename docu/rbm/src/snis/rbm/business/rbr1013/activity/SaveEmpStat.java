/*================================================================================
 * �ý���			: ������Ȳ 
 * �ҽ����� �̸�	: snis.rbm.business.rbr1013.activity.SaveEmpStat.java
 * ���ϼ���		: ������ �����ϴ� ������ ������Ȳ �ڷḦ �����ϰ�, ���� Insert���ش�. 
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-09-22
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbr1013.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
//import com.posdata.glue.dao.vo.PosRow;
//import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveEmpStat extends SnisActivity {
	
	public SaveEmpStat(){}

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
    	
    	getEmpStatCurr(ctx);
    	
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
    	String sDsName     = "";
    	
    	PosRowSet ds;

        int nSize        = 0;
        int nTempCnt     = 0;
        
        String sBrncCd   = (String)ctx.get("BRNC_CD");
        String sStndYear = (String)ctx.get("STND_YEAR");
        
        //���� ������ ����
        deleteEmpStat(sBrncCd, sStndYear);

        sDsName = "dsEmpStat";
               
        if ( ctx.get(sDsName) != null ) {
        	ds   		 = (PosRowSet) ctx.get(sDsName);
	        nSize 		 = ds.count();
	        


			PosRow pr[] = ds.getAllRow();
			PosRow record ;
	        for ( int i = 0; i < nSize; i++ ) {
	            record = pr[i];
		        	
	        	nTempCnt = saveEmpStat(record, sBrncCd, sStndYear);
		        nSaveCount = nSaveCount + nTempCnt;
	        	continue;
        
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount);
    }

    /**
     * <p> ���� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveEmpStat(PosRow record, String sBrncCd, String sStndYear) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, sBrncCd);								//�����ڵ�
        param.setValueParamter(i++, sStndYear);								//�⵵
        param.setValueParamter(i++, sBrncCd);								//����(where : 1.�����ڵ�)
        param.setValueParamter(i++, record.getAttribute("JOB_GRP_NM"));		//��å�ڵ�
        param.setValueParamter(i++, record.getAttribute("WORK_GBN_NM"));	//�ٹ������ڵ�
		
        param.setValueParamter(i++, record.getAttribute("JOB_NM"));			//������
		param.setValueParamter(i++, record.getAttribute("CAP_NM"));			//���޸�
		param.setValueParamter(i++, record.getAttribute("CNTR_JOB_NM"));	//������
		param.setValueParamter(i++, record.getAttribute("PRSN_NUM"));		//�ο���   
        param.setValueParamter(i++, SESSION_USER_ID);						//�����ID

        int dmlcount = this.getDao("rbmdao").update("rbr1013_i01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> ������ �����ϴ� ���� ������Ȳ ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteEmpStat(String sBrncCd, String sStndYear) 
    {	
    	PosParameter param = new PosParameter();   
        
        int i = 0;
        	        		        
        param.setValueParamter(i++, sBrncCd);      // �����ڵ�
        param.setValueParamter(i++, sStndYear);    // ����⵵
             
        int dmlcount = this.getDao("rbmdao").update("rbr1013_d01", param);

        return dmlcount;
    }
    
    
	private void getEmpStatCurr(PosContext ctx)  {
		String sSearchValue = Util.getCtxStr(ctx, "VW_BRNC_CD");
		

		PosParameter param = new PosParameter();
		int iParam = 0;
		param.setWhereClauseParameter(iParam++, sSearchValue);

		PosRowSet prs = null;

	
		prs = this.getDao("venisrbmdao").find("rbr1013_s02",param);

		ctx.put("dsEmpStat", prs);


	}
}
