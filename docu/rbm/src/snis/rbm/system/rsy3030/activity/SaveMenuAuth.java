package snis.rbm.system.rsy3030.activity;

import java.util.ArrayList;
import java.util.List;

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

public class SaveMenuAuth extends SnisActivity {
	public SaveMenuAuth(){
		
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
    	int nSaveCount   = 0; 
    	String sDsName   = "";
    	String sDsName2  = "";
    	String sMenuId	 = "";
    	String sUserId	 = "";
    	String Lvl1	 	 = "";
    	String Lvl2	 	 = "";
    	
    	PosDataset ds;
    	PosDataset ds2;
        int nSize        = 0;
        int nSize2       = 0;
        int nTempCnt     = 0;
                
        String srchYn 	 = "";
        String instYn 	 = "";
        String aproYn 	 = "";
        String sLvl	  	 = "";
        
        List userlist = new ArrayList();
        
        sDsName2 	= "dsMenuGrpValue";		//�޴����Ѹ���Ʈ
        sDsName 	= "dsUserListValue";	//����ڸ���Ʈ

        String  curDateTime = "";
        UtilDb udb = new UtilDb();
        curDateTime = udb.getCurTime();

        
        if ( ctx.get(sDsName2) != null) {
        	//System.out.println("---1---");        	
        	ds2			= (PosDataset) ctx.get(sDsName2);
        	nSize2 		= ds2.getRecordCount();
        	
        	for ( int j = 0; j < nSize2; j++ ) {		//�޴� ����Ʈ��ŭ �ݺ�
        		//System.out.println("---2---");        	
        		PosRecord mrecord = ds2.getRecord(j);
        		
        		sMenuId = (String)mrecord.getAttribute("MN_ID");
    			
    			if(sMenuId == null) sMenuId="";
    			
    			if ( ctx.get(sDsName2) != null  && !sMenuId.equals("")) {
	    	        ds    = (PosDataset) ctx.get(sDsName);
	    	        nSize = ds.getRecordCount();
	    	        
	    	        for ( int i = 0; i < nSize; i++ ) {
	    	        	
	    	           PosRecord record = ds.getRecord(i); 
	    	           nSaveCount += updateAuthMenu(sMenuId, curDateTime, record);
	    	           
	    	           sUserId = (String)record.getAttribute("USER_ID");
	    	           if (userlist.indexOf(sUserId) < 0) userlist.add(sUserId);
	    	       		
	    	        } // end for
	            }	  // end if
    			
        	}
        	
        	for (int i=0;i<userlist.size();i++) {
    			insertAuthAplyDept(userlist.get(i).toString());		// ����ں� : ���� ������� �μ����� ������Ʈ
    			deleteAuthAplyDept(userlist.get(i).toString(), curDateTime);			// ����ں� : �޴��� ���� ������ �ϳ��� ���� ��� ������ ���� ��� ���Ѽ����μ������� ����
        	}
        	
        	//���α׷� ������ �ϳ��� ���� ����ڿ� ���ؼ� �޴����� �ϰ� ����
        	PosParameter param = new PosParameter();	        	
			int i = 0;
			param.setValueParamter(i++, curDateTime);
			param.setValueParamter(i++, curDateTime);
	        i = 0;
	        param.setWhereClauseParameter(i++, curDateTime);
	        System.out.println("case 7:");
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
    protected int updateAuthMenu(String MenuID, String curDtm, PosRecord record) 
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
        
 		// 1) ������ �ڷᰡ �����ϴ� ���� ��ȸ
 		PosParameter param = new PosParameter();       					
 		PosRowSet rowset;
 		int i = 0;
 		
 		String sUserId   = Util.trim(record.getAttribute("USER_ID"));
 		String sStrDtTm  = (String)record.getAttribute("STR_DT_TM");
 		String sMnId     = MenuID;
 		String sPersYn   = (String)record.getAttribute("PERSONAL_YN");
 		String sPerMnlIp = (String)record.getAttribute("PERSONAL_MN_IP");
    	
 		//System.out.println("UserId="+sUserId);
 		//System.out.println("sMnId ="+sMnId);
 		//System.out.println("curDtm="+curDtm);
 		
 		param.setWhereClauseParameter(i++, sUserId);
 		param.setWhereClauseParameter(i++, sMnId);
 		param.setWhereClauseParameter(i++, curDtm);		// ���� ������ ���� ���θ� üũ

 		rowset = this.getDao("rbmdao").find("rsy3020_s04", param);
 		if (rowset.hasNext()) bAuthExist = true;
 		
 		//System.out.println("bAuthSet="+bAuthSet);
 		//System.out.println("bAuthExist="+bAuthExist);
 		
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
 	        param.setValueParamter(i++, "");           	    	     
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sPersYn);        
	        param.setValueParamter(i++, sPerMnlIp);

 	        dmlcount += this.getDao("rbmdao").update("rsy3020_i02", param);
 			
 	    // 3)�ڷᰡ ������
 		} else if (bAuthExist == true) {
 			//System.out.println("case 2:");
 	    	// 3-1) ������ �ִ� ��� ���������� ����(��������:����) : �������ڰ� ���ú��� ���� ��쿡��
			if (sStrDtTm.equals(curDtm)) {	// ���Ͻð��� �Է��� �ڷ��� ��� ����
        		
				System.out.println("case 4-1:");
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

		        dmlcount += this.getDao("rbmdao").update("rsy3020_u02", param);
		        
		        return dmlcount;
			} else {
				System.out.println("case 4-2:");
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
			        param.setValueParamter(i++, "");           	    	     
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
     protected int insertAuthAplyDept(String UserID)
     {
		PosParameter param = new PosParameter();    
		PosRowSet    rowset;
		int dmlcount = 0;
		int i = 0;
		
		param = new PosParameter();
		i = 0;
		
		param.setValueParamter(i++, "001"); 							//AUTH_GBN : 001: �Ϲ�, 002: Ư��������               	    	     
		param.setValueParamter(i++, SESSION_USER_ID);
		param.setValueParamter(i++, UserID);	//�����ID
		
		System.out.println("case 5:");
		
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
		
		System.out.println("case 6:");
		int dmlcount = this.getDao("rbmdao").update("rsy3020_d03", param);
		return dmlcount;
     }
       
}
