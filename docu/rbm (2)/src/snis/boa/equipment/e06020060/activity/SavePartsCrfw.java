/*================================================================================
 * �ý���			: ��ǰ���� 
 * �ҽ����� �̸�	: snis.boa.equipment.e06020060.activity.SavePartsCrfw.java
 * ���ϼ���		: ��ǰ�� �̿��Ѵ�.  
 * �ۼ���			: ������ 
 * ����			: 1.0.0
 * ��������		: 2008-01-11
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06020060.activity;


import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.Util;

/**
* ��ǰ�� �̿� �Ѵ�.
* @auther ������ 
* @version 1.0
*/
public class SavePartsCrfw extends SnisActivity
{    
    public SavePartsCrfw()
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
        saveState(ctx);
        return PosBizControlConstants.SUCCESS;
    }

   
    
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 

    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutParts");
        String	stnd_year   = (String) ctx.get("STND_YEAR");
        int year = Integer.parseInt(stnd_year)+1; 
        String crfw_year  = String.valueOf(year);
        String	mbr_cd    = (String) ctx.get("MBR_CD");
        String	parts_gbn = (String) ctx.get("PARTS_GBN");
        String	sCancel_yn = (String) ctx.get("CANCEL_YN");
        
        if ("Y".equals(sCancel_yn)) {
        	nDeleteCount = nDeleteCount + deletePartsAll(crfw_year,mbr_cd, parts_gbn);
        } else {
	        nSize = ds.getRecordCount();
	        
	        //���� ������ ���� �����Ѵ�.
	        if(nSize >0){
	        	nDeleteCount = nDeleteCount + deletePartsAll(crfw_year,mbr_cd, parts_gbn);
	        }
	        
	        //������ ����
	        for ( int i = 0; i < nSize; i++ ){
	            PosRecord record = ds.getRecord(i);
	            if(record.getAttribute("PRICE_STND_YEAR") != null && !"".equals(record.getAttribute("PRICE_STND_YEAR"))){
	            	nSaveCount = nSaveCount + saveParts(record,crfw_year,mbr_cd);
	                //ctx.put("test!!!", record.getAttribute("PRICE_STND_YEAR"));
	            }
	            	
	        }
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
    protected int saveParts(PosRecord record, String stnd_year, String mbr_cd)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt =insertParts(record, stnd_year, mbr_cd);
        return effectedRowCnt;
    }
   
    
    /**
     * �̿� ��� �����͸� �����Ѵ�.
     * @param record
     * @param stnd_year
     * @param mbr_cd
     * @return
     */
    protected int insertParts(PosRecord record, String stnd_year, String mbr_cd) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, stnd_year);
        param.setValueParamter(i++, mbr_cd);
        param.setValueParamter(i++, record.getAttribute("PARTS_CD"));
        param.setValueParamter(i++, record.getAttribute("PRICE_STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("NOW_UNIT_CNT"));
        param.setValueParamter(i++, record.getAttribute("NOW_UNIT_CNT"));
        /*
        param.setValueParamter(i++, "Y");
        */
    	param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").update("tbef_parts_mf001", param);
    }
    
    
    /**
     * �̿� �⵵�� �����Ͱ� ������ ���� �Ѵ�.
     * @param stnd_year
     * @param mbr_cd
     * @return
     */
    protected int deletePartsAll(String stnd_year, String mbr_cd, String parts_gbn) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, stnd_year);
        param.setValueParamter(i++, mbr_cd);
        //param.setValueParamter(i++, parts_gbn);
        return  this.getDao("boadao").update("tbef_parts_df002", param);
    }
}
