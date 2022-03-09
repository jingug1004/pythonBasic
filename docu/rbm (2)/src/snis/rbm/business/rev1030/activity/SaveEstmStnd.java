/*================================================================================
 * �ý���			: �򰡹��ǥ  ����
 * �ҽ����� �̸�	: snis.rbm.business.rev1030.activity.SaveEVDistr.java
 * ���ϼ���		: �򰡹��ǥ ����
 * �ۼ���			: �̽¹�
 * ����			: 1.0.0
 * ��������		: 2011-08-31
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rev1030.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.business.rev1010.activity.SaveEVMistr;

public class SaveEstmStnd extends SnisActivity {
		public SaveEstmStnd(){}
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
	    	int nSaveCount2   = 0;
	    	int nDeleteCount = 0; 
	    	String sDsName   = "";
	    	String sDsName2   = "";
	    	
	    	PosDataset ds;
	    	PosDataset ds2;
	    	int nSize        = 0;
	        int nTempCnt     = 0;
	        int nSize2        = 0;
	        int nTempCnt2     = 0;
	        
	        String sEvalYear = (String)ctx.get("ESTM_YEAR");	//�򰡳⵵
	        String sEvalNum  = (String)ctx.get("ESTM_NUM");		//��ȸ��
	        
	        SaveEVMistr SaveEVMistr = new SaveEVMistr();

	        if( !SaveEVMistr.getUpdateYn(sEvalYear, sEvalNum).equals("Y") ) {
	        	try { 
	    			throw new Exception(); 
	        	} catch(Exception e) {       		
	        		this.rollbackTransaction("tx_rbm");
	        		Util.setSvcMsg(ctx, "�μ�������ڸ� Ȯ���� �μ��� �����ϹǷ� �����ڷḦ �����Ͻ� �� �����ϴ�.");
	        		
	        		return;
	        	}
	        }
	        
	        sDsName = "dsTotWkFld";
	        sDsName2 = "dsItemGrd";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nTempCnt = updateEstmStnd(record)) == 0 ) {
			        		nTempCnt = insertEstmStnd(record);
			        	}
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        }
			        
		            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
			        	nDeleteCount = nDeleteCount + deleteEstmStnd(record);	            	
		            }
		        }
		        
	        }
	        if ( ctx.get(sDsName2) != null ) {
		        ds2   		 = (PosDataset) ctx.get(sDsName2);
		        nSize2 		 = ds2.getRecordCount();

		        for ( int i = 0; i < nSize2; i++ ) {
		            PosRecord record2 = ds2.getRecord(i);

			        if ( record2.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record2.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	if ( (nTempCnt2 = updateItemGrd(record2)) == 0 ) {
			        		nTempCnt2 = insertItemGrd(record2);
			        	}
				        nSaveCount2 = nSaveCount2 + nTempCnt2;
			        	continue;
			        }
			        
		        }	        
	        }
	        
	        sDsName2 = "dsItm";
	        if ( ctx.get(sDsName2) != null ) {
		        ds2   		 = (PosDataset) ctx.get(sDsName2);
		        nSize2 		 = ds2.getRecordCount();

            	deleteItmDtl((String)ctx.get("ESTM_YEAR"), (String)ctx.get("ESTM_NUM"), (String)ctx.get("ITEM_CD"));
            	
		        for ( int i = 0; i < nSize2; i++ ) {
		            PosRecord record = ds2.getRecord(i);
		            insertItmDtl(record);
		        }	        
	        }

	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    
	    /**
	     * <p> �򰡹��ǥ �Է� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertEstmStnd(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));       // 1.�򰡳⵵
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));        // 2.��ȸ��
	        param.setValueParamter(i++, record.getAttribute("CNTR_ITM_CD"));       // 3.�򰡳⵵
	        
	        param.setValueParamter(i++, record.getAttribute("CNTR_ITM_IN"));    	// 5.�򰡺����о�in�ڵ�
	        param.setValueParamter(i++, record.getAttribute("CNTR_ITM_NM"));       // 6.�򰡺����о� 
	        param.setValueParamter(i++, record.getAttribute("CNTR_JOB_NM"));       // 7.�򰡺����о�
	        
	        param.setValueParamter(i++, SESSION_USER_ID);                        // 8.����� ID	
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1030_i01", param);
	        
	        return dmlcount;
	    }
	    
	    
	    
	    /**
	     * <p> �򰡹��ǥ ���� </p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateEstmStnd(PosRecord record)
	    {			
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setValueParamter(i++, record.getAttribute("CNTR_ITM_IN"));    // 1.�򰡺����о�in�ڵ�
	        param.setValueParamter(i++, record.getAttribute("CNTR_ITM_NM"));       // 2.�򰡺����о� 
	        param.setValueParamter(i++, record.getAttribute("CNTR_JOB_NM"));
	        
	        param.setValueParamter(i++, SESSION_USER_ID);                             // 3. ����� ID      
	  
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));     // 4. �򰡳⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));      // 5. ��ȸ��

	        param.setWhereClauseParameter(i++, record.getAttribute("CNTR_ITM_CD"));    // 6.�򰡺����о��ڵ�
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1030_u01", param);
	        return dmlcount;
	    }

	    
	    
	    /**
	     * <p> �򰡹��ǥ ���� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteEstmStnd(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));     // 1. �򰡳⵵
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));      // 2. ��ȸ��
	        param.setValueParamter(i++, record.getAttribute("CNTR_ITM_CD"));    // 3.�򰡺����о��ڵ�
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1030_d01", param);

	        return dmlcount;
	    }
	    
	    protected int insertItemGrd(PosRecord record2) 
	    {
	    	PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, record2.getAttribute("ESTM_YEAR"));       // 1.�򰡳⵵
	        param.setValueParamter(i++, record2.getAttribute("ESTM_NUM"));        // 2.��ȸ��
	        param.setValueParamter(i++, record2.getAttribute("S_RATE"));       // 3.S��޹����
	        param.setValueParamter(i++, record2.getAttribute("A_RATE"));        // 4.A��޹����
	        param.setValueParamter(i++, record2.getAttribute("B_RATE"));       // 5.B��޹����
	        param.setValueParamter(i++, record2.getAttribute("C_RATE"));        // 6.C��޹����
	        param.setValueParamter(i++, record2.getAttribute("D_RATE"));        // 6.D��޹����
	        	        
	        param.setValueParamter(i++, SESSION_USER_ID);                        // 9.����� ID	
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1030_i02", param);
	        
	        return dmlcount;
	    }
	    protected int updateItemGrd(PosRecord record2) 
	    {
	    	PosParameter param = new PosParameter();
	        int i = 0;
	   
	        param.setValueParamter(i++, record2.getAttribute("S_RATE"));       // 1.S��޹����
	        param.setValueParamter(i++, record2.getAttribute("A_RATE"));        // 2.A��޹����
	        param.setValueParamter(i++, record2.getAttribute("B_RATE"));       // 3.B��޹����
	        param.setValueParamter(i++, record2.getAttribute("C_RATE"));        // 4.C��޹����
	        param.setValueParamter(i++, record2.getAttribute("D_RATE"));        // 5.D��޹����
	        
	        param.setValueParamter(i++, SESSION_USER_ID);                             // 6. ����� ID      
	  
	        i = 0;
	        param.setWhereClauseParameter(i++, record2.getAttribute("ESTM_YEAR"));     // 7. �򰡳⵵
	        param.setWhereClauseParameter(i++, record2.getAttribute("ESTM_NUM"));      // 8. ��ȸ��

	        int dmlcount = this.getDao("rbmdao").update("rev1030_u02", param);
	        return dmlcount;
	    }
	    
	    protected int deleteItemGrd(PosRecord record) 
	    {
	    	PosParameter param = new PosParameter();
	        int i = 0;
	        
	        i = 0;
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));     // 7. �򰡳⵵
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));      // 8. ��ȸ��

	        int dmlcount = this.getDao("rbmdao").update("rev1030_d02", param);
	        return dmlcount;
	    }
	   
	    /**
	     * <p> �򰡺����׸� ���� �߰� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertItmDtl(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;

	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));		
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));		
	        param.setValueParamter(i++, record.getAttribute("ESTM_ITEM_CD"));			
	        param.setValueParamter(i++, record.getAttribute("CNTR_ITM_CD"));			
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));			
	        
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));
	        param.setValueParamter(i++, record.getAttribute("ESTM_ITEM_CD"));
	        param.setValueParamter(i++, record.getAttribute("CNTR_ITM_DTL"));						
	        param.setValueParamter(i++, SESSION_USER_ID);						
	      
	        int dmlcount = this.getDao("rbmdao").update("rev1020_i01", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �򰡺����׸� ���� ���� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteItmDtl(String sEvalYear, String sEvalNum, String sEstmItemCd) 
	    {
	        PosParameter param = new PosParameter();
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, sEvalYear);     // 1. �򰡳⵵
	        param.setValueParamter(i++, sEvalNum);      // 2. ��ȸ��
	        param.setValueParamter(i++, sEstmItemCd);   // 3.�����׸��ڵ�
	        
	        int dmlcount = this.getDao("rbmdao").update("rev1020_d01", param);

	        return dmlcount;
	    }
}
