package snis.rbm.system.rsy3030.activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

public class SaveMyMenu extends SnisActivity {
	public SaveMyMenu(){
		
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
    	int nDelCount   = 0; 
    	int nSaveCount  = 0; 
    	String sDsName  = "";    	
    	PosDataset ds;
        sDsName 	= "dsMyMenu";	//���� �޴�
        
        Connection conn = null;        
        conn = getDao("rbmdao").getDBConnection();
        if ( ctx.get(sDsName) != null ) {
	        ds   	    = (PosDataset) ctx.get(sDsName);
	        nDelCount  += deleteAllMyMenu(conn, ctx, ds);
	        nSaveCount += InsertAllMyMenu(conn, ctx, ds);
		}    
        	
        Util.setDeleteCount(ctx, nDelCount);
        Util.setSaveCount  (ctx, nSaveCount);
    }
    

    /**
     * <p> �޴����� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int InsertAllMyMenu(Connection conn, PosContext ctx, PosDataset ds) 
    {
    	int dmlcount = 0;
        PreparedStatement stmt = null;
        String sqlStr = Util.getQuery(ctx, "rsy3030_i02");
        
        try {
			stmt = conn.prepareStatement(sqlStr);
			for ( int i = 0; i < ds.getRecordCount(); i++ ) {
			    PosRecord record = ds.getRecord(i);
			    if  ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {
			    	if ("1".equals(record.getAttribute("CHK"))) {
						stmt.clearParameters();
						stmt.setString(1,  SESSION_USER_ID);
						stmt.setString(2,  record.getAttribute("MN_ID").toString());						     
			    		stmt.addBatch();
			        }
			    }
			}
			stmt.executeBatch();
			dmlcount = stmt.getUpdateCount();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
        return dmlcount;
    }


   /**
     * <p> �޴����� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteAllMyMenu(Connection conn, PosContext ctx, PosDataset ds)
    {
    	int dmlcount = 0;
        PreparedStatement stmt = null;
        String sqlStr = Util.getQuery(ctx, "rsy3030_d05");
        
        try {
        	stmt = conn.prepareStatement(sqlStr);
	        for ( int i = 0; i < ds.getRecordCount(); i++ ) {
	            PosRecord record = ds.getRecord(i);
	            if  ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE ||
	                  ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE &&
	            	    !"1".equals(record.getAttribute("CHK")))) {
	            		stmt.clearParameters();
						stmt.setString(1,  SESSION_USER_ID);
		        		stmt.setString(2,  record.getAttribute("MN_ID").toString());						     
		        		stmt.addBatch();
		        }
			}
	        stmt.executeBatch();
	        dmlcount = stmt.getUpdateCount();
	        stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
        return dmlcount;       
    }   
}
