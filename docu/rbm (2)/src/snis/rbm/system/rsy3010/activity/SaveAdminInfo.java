/*
 * ================================================================================
 * �ý��� : Ư������� ���� 
 * �ҽ����� �̸� : snis.rbm.system.rsy3010.activity.SaveAdminInfo.java 
 * ���ϼ��� : Ư������� ���� 
 * �ۼ��� : ������
 * ���� : 1.0.0 
 * �������� : 2011- 10 - 08
 * ������������ : 
 * ���������� : 
 * ������������ :
 * =================================================================================
 */
package snis.rbm.system.rsy3010.activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;


public class SaveAdminInfo extends SnisActivity {
	public SaveAdminInfo(){
		
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
    	String sDsName2  = "";
    	String UserID	 = "";
    	String Lvl1	 	 = "";
    	String Lvl2	 	 = "";
    	
    	PosDataset ds;
    	PosDataset ds2;
        int nSize        = 0;
        int nSize2       = 0;
        int nTempCnt     = 0;
        
        Connection conn = null; 
        conn = getDao("rbmdao").getDBConnection();

        String sLvl	  	 = "";

        sDsName 	= "dsUser";	//����ڸ���Ʈ
        sDsName2 	= "dsMenuAuth";	//�޴����Ѹ���Ʈ
        
        String  curDateTime = "";
        curDateTime = getCurTime();
        
        if ( ctx.get(sDsName) != null) {	
        	ds2			= (PosDataset) ctx.get(sDsName);
        	nSize2 		= ds2.getRecordCount();
        	
        	PreparedStatement stmt = null;
        	String sqlStr = Util.getQuery(ctx, "rsy3010_m01");
        	
        	try {
        		stmt = conn.prepareStatement(sqlStr);
        		
	        	for ( int j = 0; j < nSize2; j++ ) {		//����ڸ���Ʈ��ŭ �ݺ�
	        		PosRecord urecord = ds2.getRecord(j);
	        		
	        		if(((String)urecord.getAttribute("CHK")).equals("1") ) {	//����ڸ���Ʈ����  üũ�� ��������� üũ
	        			UserID = (String)urecord.getAttribute("USER_ID");
	        			
	        			if(UserID == null) UserID="";
	        			
	        			if ( ctx.get(sDsName2) != null && !UserID.equals("")) {
	    	    	        ds   		 = (PosDataset) ctx.get(sDsName2);
	    	    	        nSize 		 = ds.getRecordCount();
	    	    	        
	    	    	        //deleteAuthUserAll(UserID);
    	    	        
	    	        	    // ����
	    	    	        for ( int i = 0; i < nSize; i++ ) {
	    	    	           PosRecord record = ds.getRecord(i);
		    	        	   if(!((String)record.getAttribute("CHK")).equals("1")) {	//�޴�����Ʈ����  üũ�� �޴� ���� üũ
			    	        	   Lvl1 = (String)record.getAttribute("UPUP_MN_ID");
			    	        	   Lvl2 = (String)record.getAttribute("UP_MN_ID");
		    	        		   deleteAdminInfo(record.getAttribute("MN_ID").toString(), UserID);
			    	        	   deleteAdminInfo(Lvl2, UserID);	    	        	   
			    	        	   deleteAdminInfo(Lvl1, UserID);
		    	        	   }
	    	    	        }
	    	    	        
	    	    	        for ( int i = 0; i < nSize; i++ ) {
	    	    	           PosRecord record = ds.getRecord(i);
	    	    	           	    	    	           
		    	        	   Lvl1 = (String)record.getAttribute("UPUP_MN_ID");
		    	        	   Lvl2 = (String)record.getAttribute("UP_MN_ID");
		    	        	   if(((String)record.getAttribute("CHK")).equals("1")) {	//�޴�����Ʈ����  üũ�� �޴� ���� üũ
			           	           sLvl   = record.getAttribute("LVL").toString().substring(0,1);
			           	           AddAminInfo(stmt, record, UserID, record.getAttribute("MN_ID").toString());

			           	           if(sLvl.equals("3")) AddAminInfo(stmt, record, UserID, Lvl2);
		    	    	           AddAminInfo(stmt, record, UserID, Lvl1);  
		    	        	   }
	    	    	       		
	    	    	        } // end for
	    	    	        stmt.executeBatch();
	    		        	nSaveCount += stmt.getUpdateCount();
	    	    	       
	    	            }	  // end if
	        			insertAuthAplyDept(urecord);
	        			
	        			//��������μ� ���� 
	        			deleteAuthAplyDept(UserID);
	        			
	        			if(getAdminAuthMenuCnt(urecord) > 0){ //������ ������ ��� ����ڸ޴����� �ο�	        				
	        				
	        				insertMenuAuth(urecord, curDateTime);		 //����ڱ������̺�  ����ڸ޴����� �߰�	        				
	        				insertGnrAuthAplyDept(urecord);  //��������μ� �߰� 
	        				
	        				
	        			}else{ //������ �������� �������  ����ڸ޴����� ����
	        				
	        				
	        				deleteMenuAuth(UserID,"RSY3020",curDateTime);			//����ڱ������̺�  ����ڸ޴����� ����
	        				
	        				deleteGnrAuthAplyDept(UserID);    //��������μ� ����
	        			}
	        			
	        		}
	        		
	        	} 
        	} catch (SQLException e) {
        		e.printStackTrace();
        	} finally {
        		try { stmt.close(); } catch (SQLException e) {  }
        	}

        	
        	//nSaveCount 		= nSaveCount + nTempCnt;
        }
        
        Util.setSaveCount  (ctx, nSaveCount);
    }


    /**
     * <p> Ư������� ���� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertAdminInfo(PosRecord record, String UserID, String MN_ID) 
    {
    	PosParameter param = new PosParameter();    
    	PosRowSet    rowset;
    	int dmlcount = 0;
        int i = 0;
        
    	param.setWhereClauseParameter(i++, UserID);
    	param.setWhereClauseParameter(i++, MN_ID);
        rowset = this.getDao("rbmdao").find("rsy3010_s04", param);

        if (!rowset.hasNext()) {
        	param = new PosParameter();
        	i = 0;
        	
	        param.setValueParamter(i++, UserID);
	        param.setValueParamter(i++, MN_ID);        
	        param.setValueParamter(i++, SESSION_USER_ID);
	
	        dmlcount = this.getDao("rbmdao").update("rsy3010_i01", param);
    	}
        return dmlcount;
    }


    /**
     * <p> Ư������� ���� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected void AddAminInfo(PreparedStatement stmt, PosRecord record, String UserID, String MN_ID) 
    {
    	int i = 1;
    	try {	
        	stmt.clearParameters();
        	stmt.setObject(i++, UserID);
        	stmt.setObject(i++, MN_ID);
        	stmt.setObject(i++, SESSION_USER_ID);

        	stmt.addBatch();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
        
        return;
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
    	
  
        param.setValueParamter(i++, "002"); 							//AUTH_GBN : 001: �Ϲ�, 002: Ư��������               	    	     
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("USER_ID"));	//�����ID

     
        dmlcount = this.getDao("rbmdao").update("rsy3010_m02", param);
        
        
        return dmlcount;
    }
    
    /**
     * <p> �Ϲݻ���� ��������μ� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertGnrAuthAplyDept(PosRecord record)
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

     
        dmlcount = this.getDao("rbmdao").update("rsy3010_m02", param);
        
        
        return dmlcount;
    }
    
   /**
     * <p> Ư������� �޴����� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteAdminInfo(String MN_ID, String UserID)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, UserID);
        param.setWhereClauseParameter(i++, MN_ID); 

        int dmlcount = this.getDao("rbmdao").update("rsy3010_d01", param);
        return dmlcount;
    }    
    
    
    /**
     * <p> Ư������� ��������μ� ����  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteAuthAplyDept(String UserID)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, UserID);
        param.setWhereClauseParameter(i++, "002"); 

        int dmlcount = this.getDao("rbmdao").update("rsy3010_d02", param);
        return dmlcount;
    }
    
    
    /**
     * <p> ����ڸ޴����� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertMenuAuth(PosRecord record, String curDtm)
    {
    	PosParameter param = new PosParameter();    
    	PosRowSet    rowset;
    	int dmlcount = 0;
        int i = 0;
        
		String sUserId  = (String)record.getAttribute("USER_ID");
		String sMnId    = "RSY3020";
		
		// ���� �޴��� ���� ������ ������ ����. ������ ����...
    	param = new PosParameter();
    	i = 0;
    	param.setValueParamter(i++, curDtm);  
		param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, sUserId);
        param.setWhereClauseParameter(i++, sMnId);	        
        param.setWhereClauseParameter(i++, curDtm);          
		
        dmlcount += this.getDao("rbmdao").update("rsy3020_u03", param);
        

		// �ű� ���� �Է�
        param = new PosParameter();
		i = 0;
        param.setValueParamter(i++, sUserId);
        param.setValueParamter(i++, sMnId);	    
        param.setValueParamter(i++, curDtm);      
        param.setValueParamter(i++, "Y");
        param.setValueParamter(i++, "Y");  
        param.setValueParamter(i++, "Y");  
        param.setValueParamter(i++, "Ư������ڿ��� ���Ѻο�ȭ�� ������� �ο�");           	    	     
        param.setValueParamter(i++, SESSION_USER_ID);

        dmlcount += this.getDao("rbmdao").update("rsy3010_i02", param);
        /*dmlcount += this.getDao("rbmdao").update("rsy3020_i02", param);*/
        
        return dmlcount;
    }

    protected String getCurTime()
    {
 		PosRowSet rowset;
 		String curDateTime = "";
 			
 		rowset = this.getDao("rbmdao").find("rsy3020_s05");
 		if (rowset.hasNext()) {
 			PosRow row = rowset.next();
 			curDateTime = row.getAttribute("CUR_DTM").toString();
 		}
 		return curDateTime;
    }
    
    /**
     * <p> �Ϲݻ���� ����ڸ޴� ���ѻ���  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteMenuAuth(String UserID,String sMnId, String curDtm)
    {
    	String sUserId  = UserID;
		
		// ���� �޴��� ���� ������ ������ ����. ������ ����...
		PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, curDtm);  
		param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, sUserId);
        param.setWhereClauseParameter(i++, sMnId);	        
        param.setWhereClauseParameter(i++, curDtm);          
		
        int dmlcount = this.getDao("rbmdao").update("rsy3020_u03", param);
        
        return dmlcount;
    }
  
    /**
     * <p> �Ϲݻ���� ����ڸ޴� �������ѻ���  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteParentMenuAuth(String UserID,String parentMnId)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, UserID);
        param.setWhereClauseParameter(i++, parentMnId); 
        param.setWhereClauseParameter(i++, UserID);

        int dmlcount = this.getDao("rbmdao").update("rsy3010_d05", param);
        return dmlcount;
    }
    
    
    /**
     * <p> �Ϲݻ���� ��������μ� ����  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteGnrAuthAplyDept(String UserID)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, UserID);
        param.setWhereClauseParameter(i++, "001"); 

        int dmlcount = this.getDao("rbmdao").update("rsy3010_d03", param);
        return dmlcount;
    }
    
    /**
     * <p> �����ڱ��Ѹ޴�  Count ��ȸ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int getAdminAuthMenuCnt( PosRecord record) 
    {
        PosParameter param 	= new PosParameter();
        PosRowSet    prs 	= null; 
        int i 				= 0;
        int dmlcount		= 0;
                
        param.setWhereClauseParameter(i++, record.getAttribute("USER_ID" ));
        
        prs = this.getDao("rbmdao").find("rsy3010_s05", param);
        
        dmlcount = prs.count();
    
        
        return dmlcount;
    }
    
    
    /**
     * <p> ������ �޴����� ��ü ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteAuthUserAll(String UserID)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, UserID);

        int dmlcount = this.getDao("rbmdao").update("rsy3010_d06", param);
        return dmlcount;
    }    
}
