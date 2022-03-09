/*================================================================================
 * �ý���			: �޴����� ����
 * �ҽ����� �̸�	: snis.can.system.d01010030.activity.MenuManager.java
 * ���ϼ���		: �޴����� ����
 * �ۼ���			: �ڿ���
 * ����			: 1.0.0
 * ��������		: 2007-12-06
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can_boa.system.d05000004.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �޴������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther �ڿ���
* @version 1.0
*/
public class SaveMenuAuth extends SnisActivity
{    
	protected String sStndYear = "";
    public SaveMenuAuth()
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
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;

        sDsName = "dsAuthListValue";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
           
	        logger.logInfo("========== NSIZE " + nSize + "=======================");
	         
	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            //System.out.println(record.getAttribute("MN_ID"));
	            //System.out.println(record.getAttribute("INPT_YN"));
	            //System.out.println(record.getAttribute("SRCH_YN"));
	            //System.out.println(record.getAttribute("APRV_YN"));
	            
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT
	                 || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
	            	updateAuthMenu(record);
	            	nSaveCount ++;
	            }
	            
	            // �����޴��� ���� ���Ѽ����� ���� �ʴ´�. ��ȸ�ÿ� �����޴��� ��ȸ�ϵ��� ����
	        } // end for	         
        }

        Util.setSaveCount  (ctx, nSaveCount     );
    }
    
     /**
     * <p> �޴����� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateAuthMenu(PosRecord record) 
    {
    	logger.logInfo("updateAuthMenu============================ ");
    	logger.logInfo("USER_ID==================================>"+record.getAttribute("USER_ID"));
    	logger.logInfo("MN_ID==================================>"+record.getAttribute("MN_ID"));
    	logger.logInfo("SRCH_YN==================================>"+record.getAttribute("SRCH_YN"));
    	logger.logInfo("INPT_YN==================================>"+record.getAttribute("INPT_YN"));
    	String sSrchYn = "N";
    	String sInptYn = "N";
    	String sAprvYn = "N";

    	String sUserId  = (String)record.getAttribute("USER_ID");
    	String sStrDtTm = (String)record.getAttribute("STR_DT_TM");
    	String sMnId    = Util.trim(record.getAttribute("MN_ID"));
    	
        // ���� Insert �� ��������μ�����(TBEA_AUTH_APLY_DEPT) ���̺��� ����ȭ �Ѵ�.
        syncAuthAplyDept(sUserId);	            

    	// 1)���� �ڷ� ���翩��
    	// 2)�ڷᰡ ������ 
    	//   2-1) ������ �ִ� ��� �űԷ� �Է�(�������� : ����, �������� : ���Ѵ�)
    	// 3)�ڷᰡ ������ 
    	//   3-1) ������ �ִ� ��� �����ڷ� ����(��������:����), �ű��ڷ� �Է�(��������:����, ��������:���Ѵ�)
    	//   3-2) ������ ���� ��� ����ó��(��������:����) : �������ڰ� ���ú��� ���� ��쿡��
    	
    	// 0) ���Ѻο� �Ǵ� ���� ���� üũ
        boolean bAuthSet   = true;		// ������ �Ѱ��� �ִ� �� ����(������ false)
        boolean bAuthExist = false;
        int     dmlcount = 0;
        int     dDelcount = 0;
        
        if(record.getAttribute("SRCH_YN") != null) {
    		if (Double.valueOf(record.getAttribute("SRCH_YN").toString()) == 1) {
    			sSrchYn = "Y";
    		}
    	}
        if(record.getAttribute("INPT_YN") != null) {
    		if (Double.valueOf(record.getAttribute("INPT_YN").toString()) == 1) {
    			sInptYn = "Y";
    		}
    	}
        if(record.getAttribute("APRV_YN") != null) {
    		if (Double.valueOf(record.getAttribute("APRV_YN").toString()) == 1) {
    			sAprvYn = "Y";
    		}
    	}
        if (sSrchYn.equals("N") && sInptYn.equals("N") && sAprvYn.equals("N")) {
        	bAuthSet = false;
        }
        

		// 1) ������ �ڷᰡ �����ϴ� ���� ��ȸ
    	PosParameter param = new PosParameter();       					
        PosRowSet rowset;
        
        int i = 0;		
		param.setWhereClauseParameter(i++, sUserId);
		param.setWhereClauseParameter(i++, sMnId);
		param.setWhereClauseParameter(i++, sStrDtTm);

		rowset = this.getDao("candao").find("d05000004_s02", param);
		if (rowset.hasNext()) bAuthExist = true;
		
		//System.out.println("bAuthSet="+bAuthSet);
		//System.out.println("bAuthExist="+bAuthExist);
		
		
    	// 2)�ڷᰡ ������ ������ �ִ� ��� �űԷ� �Է�(�������� : ����, �������� : ���Ѵ�)
		if (bAuthExist == false && bAuthSet == true) {
			//System.out.println("case 1:");
			param = new PosParameter();
			i = 0;
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, sMnId);	        
	        param.setValueParamter(i++, sSrchYn);
	        param.setValueParamter(i++, sInptYn);
	        param.setValueParamter(i++, sAprvYn);	           	    	     
	        param.setValueParamter(i++, SESSION_USER_ID);

	        dmlcount += this.getDao("candao").update("d05000004_i01", param);
			
    	// 3)�ڷᰡ ������
		} else if (bAuthExist == true) {
			//System.out.println("case 2:");
	    	// 3-1) ������ �ִ� ��� ���������� ����(��������:����) : �������ڰ� ���ú��� ���� ��쿡��
			param = new PosParameter();
			i = 0;
			param.setValueParamter(i++, SESSION_USER_ID);
	        
	        i = 0;
	        param.setWhereClauseParameter(i++, sUserId);
	        param.setWhereClauseParameter(i++, sMnId);	        
	        param.setWhereClauseParameter(i++, sStrDtTm);	        
			
	        dDelcount += this.getDao("candao").update("d05000004_u01", param);	        
	        
	        // 3-2) �߰����� ������ �ִ� ��� �ű��ڷ� �Է�(��������:����, ��������:���Ѵ�)
	        if (dDelcount == 0) {	// �����Ͻú��� ������ ���(�б��� ���� ���)
				//System.out.println("case 3:");
				param = new PosParameter();
				i = 0;
		        param.setValueParamter(i++, sSrchYn);
		        param.setValueParamter(i++, sInptYn);
		        param.setValueParamter(i++, sAprvYn);	           	    	     
		        param.setValueParamter(i++, SESSION_USER_ID);
		        i = 0;
		        param.setWhereClauseParameter(i++, sUserId);
		        param.setWhereClauseParameter(i++, sMnId);	        
		        param.setWhereClauseParameter(i++, sStrDtTm);	        

		        dmlcount += this.getDao("candao").update("d05000004_u02", param);
		        
			} else {
		        if (bAuthSet == true) {		
						//System.out.println("case 4:");
						param = new PosParameter();
						i = 0;
				        param.setValueParamter(i++, sUserId);
				        param.setValueParamter(i++, sMnId);	        
				        param.setValueParamter(i++, sSrchYn);
				        param.setValueParamter(i++, sInptYn);
				        param.setValueParamter(i++, sAprvYn);	           	    	     
				        param.setValueParamter(i++, SESSION_USER_ID);
		
				        dmlcount += this.getDao("candao").update("d05000004_i01", param);
				}				
			}
		}		
		
        return dmlcount;
    }

    protected void syncAuthAplyDept(String sUserId){
    	PosParameter param = new PosParameter();       					
        int i = 0;
        param.setValueParamter(i++, sUserId);
        param.setValueParamter(i++, SESSION_USER_ID);

        int dmlcount = this.getDao("candao").update("tbdm_auth_aply_dept_ma001", param);
    }
}
