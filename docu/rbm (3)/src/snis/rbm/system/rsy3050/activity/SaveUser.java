/*
 * ================================================================================
 * �ý��� : ����� ���� 
 * �ҽ����� �̸� : snis.rbm.system.rsy3050.activity.SaveUser.java 
 * ���ϼ��� : ����� ���� 
 * �ۼ��� : ������
 * ���� : 1.0.0 
 * �������� : 2011-10-08
 * ������������ : 
 * ���������� : 
 * ������������ :
 * =================================================================================
 */
package snis.rbm.system.rsy3050.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.common.util.UtilDb;
import snis.rbm.common.util.EncryptSHA256;

public class SaveUser extends SnisActivity {
	
	public SaveUser(){
		
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

    	String strErr = saveState(ctx);
    	if( strErr.equals("F") ){    	
    
    		return PosBizControlConstants.FAILURE;
    	}else{
    		return PosBizControlConstants.SUCCESS;
    	
    	}


    }

    /**
    * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
    * @param   ctx		PosContext	�����
    * @return  none
    * @throws  none
    */
    protected String saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
    	String sDsName   = "";
    	
    	String sJobGbn	 = (String) ctx.get("JOB_GBN");
    	if(sJobGbn ==null) sJobGbn = ""; //01: �Է�/����,  02: ��ȣ����
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
        
        int nUserCnt	 = 0;
        String  curDateTime = "";
        UtilDb udb = new UtilDb();
        curDateTime = udb.getCurTime();
	        
        sDsName = "dsTempUser";        
        
        if(sJobGbn.equals("01")){
        	
        	sDsName = "dsTempUser";
        	 
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();
		        
		        //Des des = new Des();	--�� ��ȣȭ ��� 20141216
		        
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
	
			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	
			        	
			        	record.setAttribute("PSWD", EncryptSHA256.getEncryptSHA256((String) record.getAttribute("PSWD")));
			        	
			        	if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT){
			        		nUserCnt = getUserCount((String)record.getAttribute("USER_ID"));
			        		
			        		
			        		if(nUserCnt>0){
				        		try { 
			            			throw new Exception(); 
				            	} catch(Exception e) {       		
				            		this.rollbackTransaction("tx_rbm");
				            		Util.setSvcMsg(ctx, "�Է��Ͻ� ����� ID �� �̹� �����մϴ�.!");
				            		return "F";
				            		
				            	}
			        			
			        		}
			        		
			        		
			        	}
			        	
			        	
				        nTempCnt = saveUserMana(record);
	
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        }
			        
		            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
			        	nDeleteCount = nDeleteCount + deleteUserMana(record);
			        	
			        	if(nDeleteCount >0){
			        		
			        		deleteUserAuth(record, curDateTime);
			        		deleteAdminInfo(record);
			        		deleteAuthDept(record);
			        	}
		            }		        
		        }	        
	        }
	        
        	sDsName = "dsEstmUser";
       	 
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();
		        
		        //Des des = new Des();
		        
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
	
			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	
			        	record.setAttribute("PSWD", EncryptSHA256.getEncryptSHA256((String) record.getAttribute("PSWD")));
			        	
			        	if(record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT){
			        		nUserCnt = getUserCount((String)record.getAttribute("USER_ID"));
			        		
			        		
			        		if(nUserCnt>0){
				        		try { 
			            			throw new Exception(); 
				            	} catch(Exception e) {       		
				            		this.rollbackTransaction("tx_rbm");
				            		Util.setSvcMsg(ctx, "�Է��Ͻ� ����� ID �� �̹� �����մϴ�.!");
				            		return "F";
				            		
				            	}
			        			
			        		}
			        		
			        		
			        	}
			        	
			        	
				        nTempCnt = saveUserMana(record);
	
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        }
			        
		            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
		            {
			        	nDeleteCount = nDeleteCount + deleteUserMana(record);
			        	
			        	if(nDeleteCount >0){
			        		
			        		deleteUserAuth(record, curDateTime);
			        		deleteAdminInfo(record);
			        		deleteAuthDept(record);
			        	}
		            }		        
		        }	        
	        } 
	        
	        
        }else if(sJobGbn.equals("02")){
        	
        	sDsName = "dsUserPw";
        	
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();
		        
		        //Des des = new Des();
		        
		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
	
			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
			          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
			        	
			        	record.setAttribute("PSWD", EncryptSHA256.getEncryptSHA256((String) record.getAttribute("PSWD")));
			        	
			        	
				        nTempCnt = updateUserPW(record);
	
				        nSaveCount = nSaveCount + nTempCnt;
			        	continue;
			        }
      
		        }	        
	        }
        } else if(sJobGbn.equals("03")){    	
			sDsName = "dsUserPw";			
			if ( ctx.get(sDsName) != null ) {
			    ds   		 = (PosDataset) ctx.get(sDsName);
			    nSize 		 = ds.getRecordCount();
			    
			    //Des des = new Des();			    
			    for ( int i = 0; i < nSize; i++ ) {
			        PosRecord record = ds.getRecord(i);			
			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
			        	
			        	record.setAttribute("PSWD_OLD", EncryptSHA256.getEncryptSHA256((String) record.getAttribute("PSWD_OLD")));
			        	record.setAttribute("PSWD_NEW", EncryptSHA256.getEncryptSHA256((String) record.getAttribute("PSWD_NEW")));
			        	
				        nTempCnt = changeUserPW(record);
				        
			        	if (nTempCnt == 0) {
				        	try { 
			        			throw new Exception(); 
			            	} catch(Exception e) {       		
			            		this.rollbackTransaction("tx_rbm");
			            		Util.setReturnParam(ctx, "RESULT", "F");
			            		Util.setSvcMsg(ctx, "���� ��ȣ�� ��ġ���� �ʽ��ϴ�.");
			            		
			            		return "";
			            	}
			        	}
			        }
		        }	        
	        }
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
        Util.setReturnParam(ctx, "RESULT", "S");
        
        return "";
    }
 


    
    
    /**
     * <p> �����  �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveUserMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("GBN"));	  //�����ڵ�
        param.setValueParamter(i++, record.getAttribute("USER_ID"));	  //�����ID
        param.setValueParamter(i++, record.getAttribute("PSWD"));	  //��й�ȣ
        param.setValueParamter(i++, record.getAttribute("USER_NM"));	  //����ڸ�
        param.setValueParamter(i++, record.getAttribute("EMAIL"));	  //�̸���

        param.setValueParamter(i++, record.getAttribute("HP_NO"));	  //�̵���ȭ��ȣ
        param.setValueParamter(i++, record.getAttribute("TEL_NO"));	  //��ȭ��ȣ
        param.setValueParamter(i++, record.getAttribute("FLOC"));	  //����
        param.setValueParamter(i++, record.getAttribute("FGRADE"));	  //����
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	  //�����ڵ�
        param.setValueParamter(i++, record.getAttribute("ASSO_CD"));	  //��з��ڵ�

        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));	  //�μ��ڵ�
        param.setValueParamter(i++, record.getAttribute("TEAM_CD"));	  //���ڵ�
        param.setValueParamter(i++, record.getAttribute("TEAM_DETL_CD"));	  //�����ڵ�
        param.setValueParamter(i++, record.getAttribute("ASSO_NM"));	  //��з���
        param.setValueParamter(i++, record.getAttribute("DEPT_NM"));	  //�μ���
        param.setValueParamter(i++, record.getAttribute("TEAM_NM"));	  //����
        param.setValueParamter(i++, record.getAttribute("RETIRE_GBN"));	  //��翩��
        

        param.setValueParamter(i++, record.getAttribute("INST_ID"));	  //�ۼ���
        param.setValueParamter(i++, record.getAttribute("UPDT_ID"));	  //������

        
        
        int dmlcount = this.getDao("rbmdao").update("rsy3050_m01", param);
        
        return dmlcount;
    }

    
    /**
     * <p> ��ȣ  ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateUserPW(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("PSWD"));		//��ȣ
        param.setValueParamter(i++, SESSION_USER_ID);						//������     

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("GBN" ));			//���ѱ���
        param.setWhereClauseParameter(i++, record.getAttribute("USER_ID" ));		//�����ID

        int dmlcount = this.getDao("rbmdao").update("rsy3050_u01", param);
        return dmlcount;
    }
    

    
    /**
     * <p> ��ȣ  ����2 </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int changeUserPW(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("PSWD_NEW"));		//��ȣ
        param.setValueParamter(i++, SESSION_USER_ID);						//������     

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("USER_ID" ));		//�����ID
        param.setWhereClauseParameter(i++, record.getAttribute("PSWD_OLD" ));		//�����ID

        int dmlcount = this.getDao("rbmdao").update("rsy3050_u02", param);
        return dmlcount;
    }
    
    
    /**
     * <p> �����  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteUserMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("GBN") ); 		//����
        param.setValueParamter(i++, record.getAttribute("USER_ID") ); 	//����� ID

        int dmlcount = this.getDao("rbmdao").update("rsy3050_d01", param);

        return dmlcount;
    }
    
    /**
     * <p> ����ڱ��� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteUserAuth(PosRecord record, String curDtm) 
    {

    	
    	// 1)�ش� ������� ������ ��� ����
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, curDtm);						//�ۼ���
        param.setValueParamter(i++, SESSION_USER_ID);					//�ۼ���
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("USER_ID"));	//�����ID
        param.setWhereClauseParameter(i++, curDtm);
        
        int dmlcount = this.getDao("rbmdao").update("rsy3021_u01", param);
        
        return dmlcount;
    }
    
    
    /**
     * <p> Ư�������� ���� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteAdminInfo(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("USER_ID") ); 	//����� ID

        int dmlcount = this.getDao("rbmdao").update("rsy3050_d03", param);

        return dmlcount;
    }
    /**
     * <p> ��������μ� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteAuthDept(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("USER_ID") ); 	//����� ID

        int dmlcount = this.getDao("rbmdao").update("rsy3050_d04", param);

        return dmlcount;
    }
    
    
	/**
     * <p> ���� ����� �Ǽ� ��ȸ   </p>
     * @param   PosGenericDao dao			DB Connect ����
     *          String        sUserId		����� Id
     * @return  nRtnCnt	int			        ����� ��ȸ  �Ǽ� 
     * @throws  none
     */
    protected int getUserCount(String sUserId)
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, sUserId); //����� ID
        
        PosRowSet keyRecord = this.getDao("rbmdao").find("rsy3050_s03", param);        

        int nRtnCnt    = keyRecord.count();
        
        return nRtnCnt;
    }
}
