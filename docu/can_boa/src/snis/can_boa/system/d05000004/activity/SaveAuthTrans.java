package snis.can_boa.system.d05000004.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;


import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;

public class SaveAuthTrans extends SnisActivity {

		public SaveAuthTrans(){
			
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
	        int nTempCnt     = 0;

	        sDsName = "dsUser";
	        
	        String sAuthGbn = (String) ctx.get("AUTH_GBN");
	        if(sAuthGbn == null) sAuthGbn="";
	        
	        String sAuthUserId = (String) ctx.get("AUTH_USER_ID");
	        if(sAuthUserId == null) sAuthUserId="";
	        
	        String sChk ="";
	        
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);

		            if(record.getAttribute("CHK").equals("1")){
		            	record.setAttribute("AUTH_USER_ID", sAuthUserId);
		            	
	            		record.setAttribute("AUTH_GBN", "001");
	            		nTempCnt = saveUserAuthTrans(record);
		            		
	            		updateMenuUser(record);
		            	updateAuthAplyDept(record);
		           
			        	continue;

		            }

		        }	        
	        }

	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    


	    /**
	     * <p> �����̾� �Է�/����(�Ϲݻ����) </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int saveUserAuthTrans(PosRecord record) 
	    {
	    	/* ó�����
	    	 * 1) �޴� ����� ������ ��� ���� ó��
	    	 * 2) �ο��ڱ����� ��ȸ�Ͽ� ���� ����鿡�� �űԷ� �Է� : insert .. select
	    	 */

	    	
	    	// 1)�ش� ������� ������ ��� ����
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;					//�ۼ���
	        param.setValueParamter(i++, SESSION_USER_ID);					//�ۼ���
	        
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("USER_ID"));	//�����ID
	        
	        int dmlcount = this.getDao("candao").update("tbdm_mn_auth_dm001", param);
	        
	        // 2)�ο��� ������ ��ȸ�Ͽ� ���� ������� �ű� �Է�
	        param = new PosParameter();   
	        
	        i = 0;
	        param.setValueParamter(i++, record.getAttribute("USER_ID"));	//�����ID
	        param.setValueParamter(i++, SESSION_USER_ID);					//�Է��� ���
	        
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("AUTH_USER_ID"));	//���Ѻ����� �����ID
	        
	        dmlcount += this.getDao("candao").update("d05000004_i02", param);
	        
	        return dmlcount;
	    }


	    /**
	     * <p> ��������μ� ����</p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateMenuUser(PosRecord record)
	    {
	    	PosParameter param = new PosParameter();
	        int i = 0;	        
	        param.setValueParamter(i++, SESSION_USER_ID);					//�ۼ���
	        param.setValueParamter(i++, record.getAttribute("USER_ID"));	//�����ID

	        int dmlcount = this.getDao("candao").update("d05000004_m02", param);
	        return dmlcount;
	    }

	    /**
	     * <p> ��������μ� ����</p>
	     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateAuthAplyDept(PosRecord record)
	    {
	    	PosParameter param = new PosParameter();
	        int i = 0;	        
	        param.setValueParamter(i++, SESSION_USER_ID);					//�ۼ���
	        param.setValueParamter(i++, record.getAttribute("USER_ID"));	//�����ID

	        int dmlcount = this.getDao("candao").update("d05000004_m01", param);
	        return dmlcount;
	    }

}
