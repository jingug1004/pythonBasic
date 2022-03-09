/*
 * ================================================================================
 * �ý��� : �������� �ҽ�
 * ���� �̸� : snis.rbm.business.rbo3030.activity.SaveRollUsgeStat.java 
 * ���ϼ��� : ��������  ���� 
 * �ۼ��� : ������
 * ���� : 1.0.0 �������� : 2011- 09 - 16
 * ������������ : 
 * ���������� : 
 * ������������ :
 * =================================================================================
 */
package snis.rbm.business.rbo3030.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveRollUsgeStat extends SnisActivity {
	public SaveRollUsgeStat(){
		
		
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
    	int nSaveCount     = 0;
    	int nDeleteCount     = 0; 
    	String sDsName     = "";
    	
    	PosRowSet ds;

        int nSize        = 0;
        int nTempCnt     = 0;
     
        
        //String STND_YEAR,ROLL_GBN,END_GBN
        
        String sStndYear = (String) ctx.get("STND_YEAR");
        String sRollGbn = (String) ctx.get("ROLL_GBN");
        String sEndGbn = (String) ctx.get("END_GBN");
        
        if(sEndGbn != null && !sEndGbn.equals("")){
        	if(sEndGbn.equals("Y")){ // ����
        		 getRollEnd(ctx);
        		
        		 sDsName = "dsRollEnd";
  
    	        if ( ctx.get(sDsName) != null ) {
    	        	ds   		 = (PosRowSet) ctx.get(sDsName);
    		        nSize 		 = ds.count();

    				PosRow pr[] = ds.getAllRow();
    				PosRow record ;
    		        for ( int i = 0; i < nSize; i++ ) {
    		            record = pr[i];
    		           
    		        	nTempCnt = saveRollEnd(record);
    			        
    			        nSaveCount = nSaveCount + nTempCnt;
    		        	continue;
    	        
    		        }	        
    	        }
        	}else if(sEndGbn.equals("N")){ //������� 
        		nSaveCount = deleteRollEnd(sStndYear,sRollGbn);
        	}
        	
        }
      
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    

    /**
     * <p> �������� ���(����) </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteRollEnd(String sStndYear, String sRollGbn) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sStndYear );
        
        
        int dmlcount = this.getDao("rbmdao").update("rbo3030_u02", param);

        return dmlcount;
    }
    
    
    /**
     * <p> �������� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveRollEnd(PosRow record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
     
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));			//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));			//���س⵵
		
        param.setValueParamter(i++, record.getAttribute("ROLL_GBN"));			//��������
		param.setValueParamter(i++, record.getAttribute("END_STK_CNT"));		//����������
		
		   
        param.setValueParamter(i++, SESSION_USER_ID);							//�����ID
        param.setValueParamter(i++, SESSION_USER_ID);							//�����ID

        int dmlcount = this.getDao("rbmdao").update("rbo3030_u01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> ������� ��ȸ  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
	private void getRollEnd(PosContext ctx)  {
		
        String sStndYear = (String) ctx.get("STND_YEAR");        
        
		PosParameter param = new PosParameter();
		int iParam = 0;
		param.setWhereClauseParameter(iParam++, sStndYear);
		param.setWhereClauseParameter(iParam++, sStndYear);
		param.setWhereClauseParameter(iParam++, SESSION_USER_ID);
		param.setWhereClauseParameter(iParam++, SESSION_USER_ID);
		
        

		PosRowSet prs = null;

	
		prs = this.getDao("rbmdao").find("rbo3030_s05",param);

		ctx.put("dsRollEnd", prs);


	}

}
