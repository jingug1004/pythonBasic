/*================================================================================
 * �ý���			: �ĺ�������
 * �ҽ����� �̸�	: snis.can_boa.boatstd.d09000001.activity.SaveHealth.java
 * ���ϼ���		: �ǰ����
 * �ۼ���			: �ֹ���
 * ����			: 1.0.0
 * ��������		: 2007-01-03
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can_boa.boatstd.d08000001.activity;

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

public class SaveEasyOutOil extends SnisActivity 
{
	public SaveEasyOutOil() { }
	
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
    	
        PosDataset ds1;
  
        int nSize1 = 0;

        String sDsName = "";

        //���λ���
        sDsName = "dsOilEasyOut";
        if ( ctx.get(sDsName) != null ) {
	        ds1    = (PosDataset)ctx.get(sDsName);
	        nSize1 = ds1.getRecordCount();
	        for ( int i = 0; i < nSize1; i++ ) 
	        {
	            PosRecord record = ds1.getRecord(i);
	            logger.logInfo("dsOilEasyOut------------------->"+record);
	        }
        }
        
		if(nSize1 > 0){
			SaveEasyOutOil(ctx); 
		}
		//saveOil(ctx);
		
        return PosBizControlConstants.SUCCESS;
    }
  
    
    /**
     * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	����� : ���λ���
     * @return  none
     * @throws  none
     */
     protected void SaveEasyOutOil(PosContext ctx) 
     {
     	int nSaveCount   = 0; 
     	int nDeleteCount = 0; 

     	PosDataset ds;
     	
        int nSize    	= 0;
        int nTempCnt    = 0;
            
        ds   = (PosDataset) ctx.get("dsOilEasyOut");
        nSize = ds.getRecordCount();
        logger.logInfo("nSize------------------->"+nSize);
  
        for ( int i = 0; i < nSize; i++ ){
        	PosRecord record = ds.getRecord(i);
        	
            if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ||
               record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE){
            	 nSaveCount += updateOutOil(record);
            }
            else if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
             	nDeleteCount = nDeleteCount + deleteOutOil(record);
            }
         }
         // ���� ��뷮�� 0���� �� ����Ÿ�� �����ؾ� ���� �ʳ�? ���ʿ��� ����Ÿ�� �����ϰ� ��...
         // ���� ���� ��ƾ�� �̺κп� �߰��ؾ� ��(commented by TOMMY)
         // ...
         // ...
        
         Util.setSaveCount  (ctx, nSaveCount  );
         Util.setDeleteCount(ctx, nDeleteCount);
     }
     

	     /**
	      * <p> ����� ����  </p>
	      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	      * @return  dmlcount	int		update ���ڵ� ����
	      * @throws  none
	      */
        protected int updateOutOil(PosRecord record){
        	logger.logInfo("insertOutOil start ============================");
        	PosParameter param;       					
            int i = 0;
            int dmlcount = 0;
        	String sOutDt = Util.trim(record.getAttribute("OUT_DT"));
        	String arrQuant[][] = new String[2][4];
            
        	arrQuant[0][0] = Util.NVL(record.getAttribute("OUT_QUANT11"), "0");
        	arrQuant[0][1] = Util.NVL(record.getAttribute("OUT_QUANT12"), "0");
        	arrQuant[0][2] = Util.NVL(record.getAttribute("OUT_QUANT13"), "0");
        	arrQuant[0][3] = Util.NVL(record.getAttribute("OUT_QUANT14"), "0");
        	arrQuant[1][0] = Util.NVL(record.getAttribute("OUT_QUANT21"), "0");
        	arrQuant[1][1] = Util.NVL(record.getAttribute("OUT_QUANT22"), "0");
        	arrQuant[1][2] = Util.NVL(record.getAttribute("OUT_QUANT23"), "0");
        	arrQuant[1][3] = Util.NVL(record.getAttribute("OUT_QUANT24"), "0");
        	
            for (int j=0;j<2;j++) {
            	for (int k=0;k<4;k++) {
            		i = 0;
            		param = new PosParameter();
                    param.setValueParamter(i++, sOutDt);
                    param.setValueParamter(i++, String.format("%03d", j+1));
                    param.setValueParamter(i++, String.format("%03d", k+1));            
                    param.setValueParamter(i++, arrQuant[j][k]);
                    param.setValueParamter(i++, SESSION_USER_ID);
                    dmlcount += this.getDao("candao").insert("d08000001_iub001", param);
            	}
            }

            
            logger.logInfo("insertOutOil end ============================");
            return dmlcount;
        }
        
        /**
         * <p> ����� ����</p>
         * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
         * @return  dmlcount	int		delete ���ڵ� ����
         * @throws  none
         */
        protected int deleteOutOil(PosRecord record){
       	 	logger.logInfo("deleteOutOil start============================");
            PosParameter param = new PosParameter();       					
            
            int i = 0;
            param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("OUT_DT")));
                    
            int dmlcount = this.getDao("candao").delete("d08000001_iub002", param);
            
            logger.logInfo("deleteOutOil end============================");
            return dmlcount;
        }        
}
