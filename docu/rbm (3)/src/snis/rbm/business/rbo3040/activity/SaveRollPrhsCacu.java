/*
 * ================================================================================
 * �ý��� : ���ż������� ���� �ҽ����� 
 * �̸� : snis.rbm.business.rbo3040.activity..java 
 * ���ϼ��� : ���ż���������� 
 * �ۼ��� : ������
 * ���� : 1.0.0 
 * �������� : 2011-09-17
 * ������������ : 
 * ���������� : ������������ :
 * =================================================================================
 */
package snis.rbm.business.rbo3040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveRollPrhsCacu extends SnisActivity {
	public SaveRollPrhsCacu(){
		
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
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        
        
        //String STND_YEAR,ROLL_GBN,END_GBN
        
        String sStndYear 	= (String) ctx.get("STND_YEAR");
        String sRollGbn 	= (String) ctx.get("ROLL_GBN");
        String sBrncCd 		= (String) ctx.get("BRNC_CD");
        String sUnitPrice	= (String)ctx.get("UNIT_PRICE");
        
        String sCfmYn 		= (String) ctx.get("CFM_YN");
        
        //01 : ����, 02 : Ȯ��, 03: Ȯ�����, 04: ����
        String sJobGbn 		= (String) ctx.get("JOB_GBN");
        if(sJobGbn == null )sJobGbn = "";
        
        
        if(sJobGbn.equals("01")){	// ���ż��� ���� �űԻ���
        	deleteRollPrhs(sStndYear, sRollGbn);
        	
        	nSaveCount = insertRollPrhsCacu(sStndYear, sRollGbn, sUnitPrice);
        	
        	
        	
        }else if(sJobGbn.equals("02") || sJobGbn.equals("03") ){	//���ż��� ���� Ȯ��/ Ȯ�����
        	
        	nSaveCount = updateRollPrhsCacuCfm(sStndYear, sRollGbn, sBrncCd, sCfmYn);
        	
        	
        }else if(sJobGbn.equals("04")){	//���ż��� ���� ���� ���� 
        	
            sDsName = "dsRollPrhsCacu";
            
            if ( ctx.get(sDsName) != null ) {
    	        ds   		 = (PosDataset) ctx.get(sDsName);
    	        nSize 		 = ds.getRecordCount();

    	        for ( int i = 0; i < nSize; i++ ) {
    	            PosRecord record = ds.getRecord(i);

    	            nSaveCount = updateRollPrhsCacu(record);
    	            
    	        }	        
            }
        	
        }
        

      
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    


   
    /**
     * <p> ���ż��� ����  �Է�  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertRollPrhsCacu(String sStndYear, String sRollGbn, String sUnitPrice) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
         
        param.setValueParamter(i++, sStndYear);		//���س⵵
        param.setValueParamter(i++, sRollGbn);		//��������
        param.setValueParamter(i++, sUnitPrice);		//���س⵵
        param.setValueParamter(i++, sUnitPrice);		//��������
        param.setValueParamter(i++, sStndYear);		//��������
        
        param.setValueParamter(i++, sRollGbn);								//��������
        param.setValueParamter(i++, sStndYear);								//��������
        param.setValueParamter(i++, sRollGbn);						//�ۼ���
        param.setValueParamter(i++, sStndYear);						//�ۼ���
        param.setValueParamter(i++, sRollGbn);						//�ۼ���
        param.setValueParamter(i++, sStndYear);						//�ۼ���
        
        int dmlcount = this.getDao("rbmdao").update("rbo3040_i01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> ���ż��� ���� ����  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRollPrhsCacu(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
         
        param.setValueParamter(i++, record.getAttribute("INC_DEC_RATE"));	//������
        param.setValueParamter(i++, record.getAttribute("NECE_CNT"));		//�ҿ䷮
        param.setValueParamter(i++, record.getAttribute("QUAR_USGE_CNT"));	//1/4��뷮
        param.setValueParamter(i++, record.getAttribute("MAKE_CNT"));		//���ۼ���
        param.setValueParamter(i++, record.getAttribute("UNIT_PRICE"));		//�ܰ�
        param.setValueParamter(i++, record.getAttribute("NECE_BUDG"));		//�ҿ俹��
        param.setValueParamter(i++, record.getAttribute("RMK"));			//���
        param.setValueParamter(i++, record.getAttribute("UPDT_ID"));		//������

        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));		//���س⵵
        param.setValueParamter(i++, record.getAttribute("ROLL_GBN"));		//��������
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//�����ڵ�
        
        int dmlcount = this.getDao("rbmdao").update("rbo3040_u01", param);
        
        return dmlcount;
    }
    
    
    /**
     * <p> ���ż������� Ȯ��  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateRollPrhsCacuCfm(String sStndYear, String sRollGbn, String sBrncCd, String sCfmYn) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sCfmYn );
        param.setValueParamter(i++, SESSION_USER_ID );
        
        
        param.setValueParamter(i++, sStndYear );
        param.setValueParamter(i++, sRollGbn );
        
        
        int dmlcount = this.getDao("rbmdao").update("rbo3040_u02", param);

        return dmlcount;
    }
    
    /**
     * <p> ���ż������� ����  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteRollPrhs(String sStndYear, String sRollGbn) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        
        param.setValueParamter(i++, sStndYear );
        param.setValueParamter(i++, sRollGbn );
        
        
        int dmlcount = this.getDao("rbmdao").update("rbo3040_d01", param);

        return dmlcount;
    }
}
