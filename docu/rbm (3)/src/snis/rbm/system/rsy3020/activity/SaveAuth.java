/*================================================================================
 * �ý���			: �޴����� ����
 * �ҽ����� �̸�	: snis.rbm.system.rsy0040.activity.SaveAuth.java
 * ���ϼ���		: �޴����� ����
 * �ۼ���			: �̿���
 * ����			: 1.0.0
 * ��������		: 2011-07-31
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.system.rsy3020.activity;

import java.sql.Connection;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.common.util.UtilDb;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

/**
* ����ں� �޴������� �����ϴ� Ŭ�����̴�.
* @auther �̿���
* @version 1.0
*/
public class SaveAuth extends SnisActivity
{    
    public SaveAuth()
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

    	/*
        PosDataset ds;
        int        nSize        = 0;
        String     sDsName = "";
        	        
        sDsName = "dsAuthListValue";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	        }
        }
        */
	        
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
    	String sDsName2  = "";
    	String UserID	 = "";
    	
    	PosDataset ds;
    	PosDataset ds2;
        int nSize        = 0;
        int nSize2       = 0;
                
        Connection conn = null; 
        conn = getDao("rbmdao").getDBConnection();
        
        sDsName 	= "dsUserListValue";	//����ڸ���Ʈ
        sDsName2 	= "dsAuthListValue";	//�޴����Ѹ���Ʈ
        
        String  curDateTime = "";
        UtilDb udb = new UtilDb();
        curDateTime = udb.getCurTime();
        
        if ( ctx.get(sDsName) != null) {    	
        	ds2			= (PosDataset) ctx.get(sDsName);
        	nSize2 		= ds2.getRecordCount();
        	
        	for ( int j = 0; j < nSize2; j++ ) {		//����ڸ���Ʈ��ŭ �ݺ�
        		PosRecord urecord = ds2.getRecord(j);
        		
        		if (((String)urecord.getAttribute("CHK")).equals("1")) {	// ����ڸ���Ʈ����  üũ�� ��������� üũ
        			UserID = (String)urecord.getAttribute("USER_ID");
        			
        			if(UserID == null) UserID="";
        			
        			if ( ctx.get(sDsName2) != null  && !UserID.equals("")) {
    	    	        ds   		 = (PosDataset) ctx.get(sDsName2);
    	    	        nSize 		 = ds.getRecordCount();
    	    	        
    	    	        for ( int i = 0; i < nSize; i++ ) {
    	    	           PosRecord record = ds.getRecord(i);    	    	           	    	    	            
    	    	           
    	    	           nSaveCount += updateAuthMenu(UserID, curDateTime, record);
    	    	           
    	    	       		
    	    	        } // end for
    	            }	  // end if
        			
        			insertAuthAplyDept(urecord);		// ����ں� : ���� ������� �μ����� ������Ʈ
        			deleteAuthAplyDept(UserID, curDateTime);			// ����ں� : �޴��� ���� ������ �ϳ��� ���� ��� ������ ���� ��� ���Ѽ����μ������� ����
        		}
	        	
        	}
        	
        	//���α׷� ������ �ϳ��� ���� ����ڿ� ���ؼ� �޴����� �ϰ� ����
        	PosParameter param = new PosParameter();	        	
			int i = 0;
			param.setValueParamter(i++, curDateTime);
			param.setValueParamter(i++, curDateTime);
	        i = 0;
	        param.setWhereClauseParameter(i++, curDateTime);
        	this.getDao("rbmdao").update("rsy3020_d02", param);
	        	
        }
        
        Util.setSaveCount  (ctx, nSaveCount);
    }

    /**
    * <p> �޴����� �Է� </p>
    * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
    * @return  dmlcount	int		update ���ڵ� ����
    * @throws  none
    */
   protected int updateAuthMenu(String UserId, String curDtm, PosRecord record) 
   {
		// 1)���� �ڷ� ���翩��
		// 2)�ڷᰡ ������ 
		//   2-1) ������ �ִ� ��� �űԷ� �Է�(�������� : ����, �������� : ���Ѵ�)
		// 3)�ڷᰡ ������ 
		//   3-1) ������ �ִ� ��� �����ڷ� ����(��������:����), �ű��ڷ� �Է�(��������:����, ��������:���Ѵ�)
		//   3-2) ������ ���� ��� ����ó��(��������:����) : �������ڰ� ���ú��� ���� ��쿡��
   	
		String sSrchYn = "N";
		String sInptYn = "N";
		String sAprvYn = "N";
		
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
		if(record.getAttribute("APRO_YN") != null) {
			if (Double.valueOf(record.getAttribute("APRO_YN").toString()) == 1) {
				sAprvYn = "Y";
			}
		}
		if (sSrchYn.equals("N") && sInptYn.equals("N") && sAprvYn.equals("N")) {
		   	bAuthSet = false;
		}
		String sRmk = (String)record.getAttribute("RMK");
       
		// 1) ������ �ڷᰡ �����ϴ� ���� ��ȸ
		PosParameter param = new PosParameter();       					
		PosRowSet rowset;
		int i = 0;
		
		String sUserId  = UserId;
		String sStrDtTm = (String)record.getAttribute("STR_DT_TM");
		String sMnId    = Util.trim(record.getAttribute("MN_ID"));
		String sPersYn   = (String)record.getAttribute("PERSONAL_YN");
 		String sPerMnlIp = (String)record.getAttribute("PERSONAL_MN_IP");
   	
		System.out.println("UserId="+UserId);
		System.out.println("sMnId ="+sMnId);
		System.out.println("curDtm="+curDtm);
		
		param.setWhereClauseParameter(i++, sUserId);
		param.setWhereClauseParameter(i++, sMnId);
		param.setWhereClauseParameter(i++, curDtm);		// ���� ������ ���� ���θ� üũ

		rowset = this.getDao("rbmdao").find("rsy3020_s04", param);
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
	        param.setValueParamter(i++, curDtm);  
	        param.setValueParamter(i++, sSrchYn);
	        param.setValueParamter(i++, sInptYn);  
	        param.setValueParamter(i++, sAprvYn);           	    	     
	        param.setValueParamter(i++, sRmk);           	    	     
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sPersYn);        
	        param.setValueParamter(i++, sPerMnlIp);

	        dmlcount += this.getDao("rbmdao").update("rsy3020_i02", param);
			
	    // 3)�ڷᰡ ������
		} else if (bAuthExist == true) {
			System.out.println("case 2:");
	    	// 3-1) ������ �ִ� ��� ���������� ����(��������:����) : �������ڰ� ���ú��� ���� ��쿡��
			if (sStrDtTm.equals(curDtm)) {	// ���Ͻð��� �Է��� �ڷ��� ��� ����
        		
				System.out.println("case 4-1:");
				param = new PosParameter();
				i = 0;
		        param.setValueParamter(i++, sSrchYn);
		        param.setValueParamter(i++, sInptYn);
		        param.setValueParamter(i++, sAprvYn);           	    	     
		        param.setValueParamter(i++, sRmk);           	    	     
		        param.setValueParamter(i++, SESSION_USER_ID);
		        i = 0;
		        param.setWhereClauseParameter(i++, sUserId);
		        param.setWhereClauseParameter(i++, sMnId);	        
		        param.setWhereClauseParameter(i++, sStrDtTm);	

		        dmlcount += this.getDao("rbmdao").update("rsy3020_u02", param);
		        
		        return dmlcount;
			} else {
				param = new PosParameter();
				i = 0;
		        param.setValueParamter(i++, curDtm);  
				param.setValueParamter(i++, SESSION_USER_ID);
		        
		        i = 0;
		        param.setWhereClauseParameter(i++, sUserId);
		        param.setWhereClauseParameter(i++, sMnId);	        
		        param.setWhereClauseParameter(i++, sStrDtTm);       
				
		        dDelcount += this.getDao("rbmdao").update("rsy3020_u01", param);	
			}
	        
	        // 3-2) �߰����� ������ �ִ� ��� �ű��ڷ� �Է�(��������:����, ��������:���Ѵ�)

	        if (bAuthSet == true) {		
	        	
	        	System.out.println("case 4:");
				param = new PosParameter();
				i = 0;
		        param.setValueParamter(i++, sUserId);
		        param.setValueParamter(i++, sMnId);	    
		        param.setValueParamter(i++, curDtm);      
		        param.setValueParamter(i++, sSrchYn);
		        param.setValueParamter(i++, sInptYn);  
		        param.setValueParamter(i++, sAprvYn);
		        param.setValueParamter(i++, sRmk); 
		        param.setValueParamter(i++, SESSION_USER_ID);
		        param.setValueParamter(i++, sPersYn);        
		        param.setValueParamter(i++, sPerMnlIp);

		        dmlcount += this.getDao("rbmdao").update("rsy3020_i02", param);	        	
	        }
		}		
       
       return dmlcount;
   }


    /**
     * <p> ��������μ� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertAuthAplyDept(PosRecord record)
    {
    	PosParameter param = new PosParameter();    
    	PosRowSet    rowset;
    	int dmlcount = 0;
        int i = 0;

    	param = new PosParameter();
    	i = 0;
    	
  
        param.setValueParamter(i++, "001"); 							//AUTH_GBN : 001: �Ϲ�, 002: Ư��������               	    	     
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("USER_ID"));	//�����ID

     
        dmlcount = this.getDao("rbmdao").update("rsy3020_m02", param);
        
        
        return dmlcount;
    }
    
    /**
     * <p> ����� ��������μ� ����  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteAuthAplyDept(String UserID, String curDtm)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, UserID);
        param.setWhereClauseParameter(i++, "001"); 
        param.setWhereClauseParameter(i++, curDtm); 

        int dmlcount = this.getDao("rbmdao").update("rsy3020_d03", param);
        return dmlcount;
    }
      
}
