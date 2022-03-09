/*================================================================================
 * �ý���			: �޴����� ����
 * �ҽ����� �̸�	: snis.boa.system.e01010030.activity.MenuManager.java
 * ���ϼ���		: �޴����� ����
 * �ۼ���			: �迵ö
 * ����			: 1.0.0
 * ��������		: 2007-12-06
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.system.e01010040.activity;

import snis.boa.common.util.DesCipher;
import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
import snis.boa.common.util.UtilDb;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;


/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �޴������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* ���泻��(2014.6.22) : ���缱
* ���Ѱ��������� �����丮 ������ ���� ���� ������� ���� �������� ���ÿ� 1�� ���� �������� ����
* @auther ���缱
* @version 1.1
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

        PosDataset ds;
        int        nSize        = 0;
        String     sDsName = "";
        
        sDsName = "dsUserListValue";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
        }
        
        
        sDsName = "dsAuthListValue";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
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
System.out.println("saveStat::::");
 
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
    	
    	String sDsName   = "";
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        // 1) ����� �߰� �� ����
        sDsName = "dsUserListValue";
        
        String  curDateTime = "";
        UtilDb udb = new UtilDb();
        curDateTime = udb.getCurTime();
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
            
            nTempCnt = 0;
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ) {
		        	deleteUser(record);
		        }
		        	
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
		        	if ( record.getAttribute("GBN").equals("001") ) continue;
		        	if ( (nTempCnt = updateUser(record)) != 1 ) {
		        		nTempCnt = insertUser(record);
		        	}
		        	nSaveCount = nSaveCount + nTempCnt;
		        }
	        }
        }

        // 2) ����� ���� ����        
        String sUserId = (String)ctx.get("USER_ID");
        sDsName = "dsAuthListValue";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	         
	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT
	                 || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE ) {
	            	updateAuthMenu(record, curDateTime, ctx);
	            	nSaveCount ++;
	            }
	            
	        } // end for
	         // ���� Insert �� ��������μ�����(TBEA_AUTH_APLY_DEPT) ���̺��� ����ȭ �Ѵ�.
            syncAuthAplyDept(sUserId);
        }


        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    

    protected void syncAuthAplyDept(String sUserId){
    	PosParameter param = new PosParameter();       					
        int i = 0;
        param.setValueParamter(i++, sUserId);
        param.setValueParamter(i++, SESSION_USER_ID);

        int dmlcount = this.getDao("boadao").update("tbea_auth_aply_dept_ma001", param);
    }


    /**
     * <p> ����� ���� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateUser(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;

        DesCipher bean  = new DesCipher();
        if ( record.getAttribute("PSWD") != null && !record.getAttribute("PSWD").equals("") ) {
        	record.setAttribute("PSWD", bean.Encode((String) record.getAttribute("PSWD")));
        }

        param.setValueParamter(i++, record.getAttribute("PSWD"   ));
        param.setValueParamter(i++, record.getAttribute("USER_NM"));
        param.setValueParamter(i++, record.getAttribute("EMAIL"  ));
        param.setValueParamter(i++, record.getAttribute("TEL_NO" ));
        param.setValueParamter(i++, record.getAttribute("FLOC"   ));
        param.setValueParamter(i++, record.getAttribute("FGRADE" ));
        param.setValueParamter(i++, record.getAttribute("ASSO_CD"));
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));
        param.setValueParamter(i++, record.getAttribute("TEAM_CD"));
        param.setValueParamter(i++, record.getAttribute("ASSO_NM"));
        param.setValueParamter(i++, record.getAttribute("DEPT_NM"));
        param.setValueParamter(i++, record.getAttribute("TEAM_NM"));

        i =0;
        param.setWhereClauseParameter(i++,record.getAttribute("USER_ID"));

        int dmlcount = this.getDao("boadao").update("tbea_user_ua001", param);

        return dmlcount;
    }    

    /**
     * <p> ����� �߰� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertUser(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("USER_ID"));
        

        DesCipher bean  = new DesCipher();
        if ( record.getAttribute("PSWD") != null && !record.getAttribute("PSWD").equals("") ) {
        	record.setAttribute("PSWD", bean.Encode((String) record.getAttribute("PSWD")));
        }
        
        param.setValueParamter(i++, record.getAttribute("PSWD"   ));
        param.setValueParamter(i++, record.getAttribute("USER_NM"));
        param.setValueParamter(i++, record.getAttribute("EMAIL"  ));
        param.setValueParamter(i++, record.getAttribute("TEL_NO" ));
        param.setValueParamter(i++, record.getAttribute("FLOC"   ));
        param.setValueParamter(i++, record.getAttribute("FGRADE" ));
        param.setValueParamter(i++, record.getAttribute("ASSO_CD"));
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));
        param.setValueParamter(i++, record.getAttribute("TEAM_CD"));
        param.setValueParamter(i++, record.getAttribute("ASSO_NM"));
        param.setValueParamter(i++, record.getAttribute("DEPT_NM"));
        param.setValueParamter(i++, record.getAttribute("TEAM_NM"));

        int dmlcount = this.getDao("boadao").update("tbea_user_ia001", param);
        return dmlcount;
    }    

    /**
     * <p> ����� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteUser(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++,record.getAttribute("USER_ID"));

        int dmlcount = this.getDao("boadao").update("tbea_user_da001", param);
        
        param = new PosParameter();
        i = 0;
        param.setValueParamter(i++, SESSION_USER_ID);
        i = 0;
        param.setWhereClauseParameter(i++,record.getAttribute("USER_ID"));
        
        this.getDao("boadao").update("tbea_mn_auth_da001", param);
        return dmlcount;
    }  
    

    /**
    * <p> �޴����� �Է� </p>
    * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
    * @return  dmlcount	int		update ���ڵ� ����
    * @throws  none
    */
   protected int updateAuthMenu(PosRecord record, String curDtm, PosContext ctx) 
   {
		String sSrchYn = "N";
		String sInptYn = "N";
		
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
		int		dPercount = 0;
		   
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
		if (sSrchYn.equals("N") && sInptYn.equals("N")) {
		   	bAuthSet = false;
		}
       

		// 1) ������ �ڷᰡ �����ϴ� ���� ��ȸ
		PosParameter param = new PosParameter();       	
		PosParameter perparam = new PosParameter();
		PosRowSet rowset;
		int i = 0;
		
		String sUserId  = (String)record.getAttribute("USER_ID");
		String sStrDtTm = (String)record.getAttribute("STR_DT_TM");
		String sMnId    = Util.trim(record.getAttribute("MN_ID"));
   	
		param.setWhereClauseParameter(i++, sUserId);
		param.setWhereClauseParameter(i++, sMnId);
		param.setWhereClauseParameter(i++, sStrDtTm);

		rowset = this.getDao("boadao").find("e01010040_s02", param);
		if (rowset.hasNext()) bAuthExist = true;
		
		System.out.println("bAuthSet="+bAuthSet);
		System.out.println("bAuthExist="+bAuthExist);
		
		// 2)�ڷᰡ ������ ������ �ִ� ��� �űԷ� �Է�(�������� : ����, �������� : ���Ѵ�)
		if (bAuthExist == false && bAuthSet == true) {
			System.out.println("case 1:");
			param = new PosParameter();
			i = 0;
	        param.setValueParamter(i++, sUserId);
	        param.setValueParamter(i++, sMnId);	        
	        param.setValueParamter(i++, sSrchYn);
	        param.setValueParamter(i++, sInptYn);           	    	     
	        param.setValueParamter(i++, SESSION_USER_ID);

	        dmlcount += this.getDao("boadao").update("e01010040_i01", param);
			
	    // 3)�ڷᰡ ������
		} else if (bAuthExist == true) {
			System.out.println("case 2:");
	    	// 3-1) ������ �ִ� ��� ���������� ����(��������:����) : �������ڰ� ���ú��� ���� ��쿡��
			
			param = new PosParameter();
			i = 0;
			param.setValueParamter(i++, SESSION_USER_ID);
	        
	        i = 0;
	        param.setWhereClauseParameter(i++, sUserId);
	        param.setWhereClauseParameter(i++, sMnId);	        
	        param.setWhereClauseParameter(i++, sStrDtTm);	        
			
	        dDelcount += this.getDao("boadao").update("e01010040_u01", param);	        
	        
	        // 3-2) �߰����� ������ �ִ� ��� �ű��ڷ� �Է�(��������:����, ��������:���Ѵ�)
	        if (dDelcount == 0) {	// �����Ͻú��� ������ ���(�б��� ���� ���)
				System.out.println("case 3:");
				param = new PosParameter();
				i = 0;
		        param.setValueParamter(i++, sSrchYn);
		        param.setValueParamter(i++, sInptYn);           	    	     
		        param.setValueParamter(i++, SESSION_USER_ID);
		        i = 0;
		        param.setWhereClauseParameter(i++, sUserId);
		        param.setWhereClauseParameter(i++, sMnId);	        
		        param.setWhereClauseParameter(i++, sStrDtTm);	        

		        dmlcount += this.getDao("boadao").update("e01010040_u02", param);
		        
			} else {
		        if (bAuthSet == true) {		
						System.out.println("case 4:");
						param = new PosParameter();
						i = 0;
				        param.setValueParamter(i++, sUserId);
				        param.setValueParamter(i++, sMnId);	        
				        param.setValueParamter(i++, sSrchYn);
				        param.setValueParamter(i++, sInptYn);           	    	     
				        param.setValueParamter(i++, SESSION_USER_ID);
		
				        dmlcount += this.getDao("boadao").update("e01010040_i01", param);
				}				
			}
	        
	        if (sSrchYn.equals("N")){
	        	System.out.println("case 4-3:");
				param = new PosParameter();
				i = 0;
		        param.setValueParamter(i++, sUserId);
		        param.setValueParamter(i++, SESSION_USER_ID);
		        param.setValueParamter(i++, sUserId);
		        param.setValueParamter(i++, sMnId);	            

		        dmlcount += this.getDao("boadao").update("e01010040_u03", param);
		        
		        System.out.println("case 4-4:");
	 	        perparam = new PosParameter();
	 	        i = 0;
	 	        perparam.setValueParamter(i++, sMnId); 
	 	        perparam.setValueParamter(i++, SESSION_USER_ID);
	 	        perparam.setValueParamter(i++, sUserId);
	 	        
	 	        dPercount += this.getDao("boadao").update("e01010040_i02", perparam);
	        }
		}		
       
       return dmlcount;
   }

}
