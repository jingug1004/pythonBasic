/*================================================================================
 * �ý���			: �����ڷ� ����
 * �ҽ����� �̸�	: snis.rbm.business.rev1010.activity.SaveData.java
 * ���ϼ���		: �����ڷ� ����
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-10-19
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rev1010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.business.rev1010.activity.SaveEVMistr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaveData extends SnisActivity {
	
	public SaveData(){}

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
        Connection conn = null;        
        
        conn = getDao("rbmdao").getDBConnection();
        
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
        
        deleteWk      (sEvalYear, sEvalNum);	//��������
        deleteAtt     (sEvalYear, sEvalNum);	//���»���
        deleteDate    (sEvalYear, sEvalNum);	//�߷����ڻ���
        deleteDeptHead(sEvalYear, sEvalNum);	//�μ������
        deleteDept    (sEvalYear, sEvalNum);	//�μ�����
        deleteEmp     (sEvalYear, sEvalNum);	//�������
        deleteComm    (sEvalYear, sEvalNum);	//��ǥ�һ���
        deleteAprv    (sEvalYear, sEvalNum);	//���� ��������� ����
        deleteTelmp   (sEvalYear, sEvalNum);    //�߸Ž��� ����
        deletePcDiv   (sEvalYear, sEvalNum);    //�ٽ� ��ǥ�� ���� ����
        deleteAppr    (sEvalYear, sEvalNum);	//�������̺� ����
        
        //���� Insert
        sDsName = "dsVenusWk";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);	        
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);	            
	            record.setAttribute("ESTM_YEAR", sEvalYear);
	            record.setAttribute("ESTM_NUM" , sEvalNum);
	            
//		        nSaveCount = nSaveCount + insertWk(record); 
	        }	        
	        nSaveCount += InsertAllWk(conn, ctx, ds);
	         
        }
        
        //���� Insert
        sDsName = "dsVenusAtt";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            record.setAttribute("ESTM_YEAR", sEvalYear);
	            record.setAttribute("ESTM_NUM" , sEvalNum);

		        //nSaveCount = nSaveCount + insertAtt(record);
	        }	        
	        nSaveCount += InsertAllAtt(conn, ctx, ds);
        }
        
        //�߷����� Insert
        sDsName = "dsVenusDate";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            record.setAttribute("ESTM_YEAR", sEvalYear);
	            record.setAttribute("ESTM_NUM" , sEvalNum);

		        //nSaveCount = nSaveCount + insertDate(record);
	        }	       
	        nSaveCount += InsertAllDate(conn, ctx, ds); 
        }
        
      //�μ��� Insert
        sDsName = "dsVenusDept";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            record.setAttribute("ESTM_YEAR", sEvalYear);
	            record.setAttribute("ESTM_NUM" , sEvalNum);

		        //nSaveCount = nSaveCount + insertDeptHead(record);
	        }	
	        nSaveCount += InsertAllDeptHead(conn, ctx, ds);         
        }
        
      //�μ� Insert
        sDsName = "dsDept";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            record.setAttribute("ESTM_YEAR", sEvalYear);
	            record.setAttribute("ESTM_NUM" , sEvalNum);

		        //nSaveCount = nSaveCount + insertDept(record);
	        }	
	        nSaveCount += InsertAllDept(conn, ctx, ds);                
        }
        
        //��� Insert
        sDsName = "dsRegEmp";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            record.setAttribute("ESTM_YEAR", sEvalYear);
	            record.setAttribute("ESTM_NUM" , sEvalNum);

		        //nSaveCount = nSaveCount + insertEmp(record);
	        }	
	        nSaveCount += InsertAllEmp(conn, ctx, ds);                    
        }
        
      //��ǥ�� Insert
        sDsName = "dsDwComm";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            record.setAttribute("ESTM_YEAR", sEvalYear);
	            record.setAttribute("ESTM_NUM" , sEvalNum);

		        //nSaveCount = nSaveCount + insertComm(record);
	        }	   
	        nSaveCount += InsertAllComm(conn, ctx, ds);           
        }
        
        //�߸Ž��� Insert
	    sDsName = "dsDwTelmp";
	    if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            record.setAttribute("ESTM_YEAR", sEvalYear);
	            record.setAttribute("ESTM_NUM" , sEvalNum);
	
		        //nSaveCount = nSaveCount + insertTelmp(record); 
	        }	     
	        nSaveCount += InsertAllTelmp(conn, ctx, ds);        
	    }
        
	  // �ٽ� ��ǥ�� ����  Insert
	    sDsName = "dsPcDiv";
	    if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();
	        
	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            record.setAttribute("ESTM_YEAR", sEvalYear);
	            record.setAttribute("ESTM_NUM" , sEvalNum);
	
		        //nSaveCount = nSaveCount + insertPcDiv(record); 
	        }	     
	        nSaveCount += InsertAllPcDiv(conn, ctx, ds);              
	    }
	    
	    updateByPass(sEvalYear, sEvalNum); 	//�Ⱓ �߸Ž��� �����ڴ�  TOTAL_CNT = 99999, WK_DAY_CNT = 99999�� ����
	    updateTeamAvg(sEvalYear, sEvalNum); //�Ⱓ �߸Ž��� �������� ��� �߸ŰǼ��� ����� ����
	    insertApprAll(sEvalYear, sEvalNum);
        insertAprv(sEvalYear, sEvalNum);
        
        Util.setReturnParam(ctx, "RESULT", "0");
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
    }
    
    /**
     * <p> ���� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertWk(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));	//�򰡳⵵
        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));	//��ȸ��
        param.setValueParamter(i++, record.getAttribute("WK_JJOB"));	//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("CD_NM"));   	//������
        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(�ۼ���)
        
        int dmlcount = this.getDao("rbmdao").update("rev1010_i02", param);
        
        return dmlcount;
    }

    /**
     * <p> ���� �ϰ� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int InsertAllWk(Connection conn, PosContext ctx, PosDataset ds)
    {
    	int dmlcount = 0;
        PreparedStatement stmt = null;
        String sqlStr = Util.getQuery(ctx, "rev1010_i02");
        PosParameter param = new PosParameter();   

        try {
        	int iparam = 1;
			stmt = conn.prepareStatement(sqlStr);
			for ( int i = 0; i < ds.getRecordCount(); i++ ) {
			    PosRecord record = ds.getRecord(i);
				stmt.clearParameters();
				iparam = 1;
				stmt.setString(iparam++,  record.getAttribute("ESTM_YEAR").toString());	//�򰡳⵵
				stmt.setString(iparam++,  record.getAttribute("ESTM_NUM").toString());	//��ȸ��
				stmt.setString(iparam++,  record.getAttribute("WK_JJOB").toString());	//�����ڵ�
				stmt.setString(iparam++,  record.getAttribute("CD_NM").toString());   	//������
				stmt.setString(iparam++,  SESSION_USER_ID);					//�����ID(�ۼ���)
	    		stmt.addBatch();
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
     * <p> ���� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteWk(String sYear, String sNum) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sYear);	//�򰡳⵵
        param.setValueParamter(i++, sNum);  //��ȸ��

        int dmlcount = this.getDao("rbmdao").update("rev1010_d02", param);

        return dmlcount;
    }
    
    /**
     * <p> �ٹ����� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertAtt(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));	//�򰡳⵵
        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));	//��ȸ��
        param.setValueParamter(i++, record.getAttribute("ATT_CD"));		//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("EMP_NO"));   	//���
        param.setValueParamter(i++, record.getAttribute("EMP_NM"));   	//����
        
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));   	//�μ��ڵ�
        param.setValueParamter(i++, record.getAttribute("CNT"));   		//�Ǽ�
        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(�ۼ���)
        
        int dmlcount = this.getDao("rbmdao").update("rev1010_i03", param);
        
        return dmlcount;
    }

    /**
     * <p> �ٹ����� �ϰ� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int InsertAllAtt(Connection conn, PosContext ctx, PosDataset ds)  
    {
    	int dmlcount = 0;
        PreparedStatement stmt = null;
        String sqlStr = Util.getQuery(ctx, "rev1010_i03");
        PosParameter param = new PosParameter();   

        try {
        	int iparam = 1;
			stmt = conn.prepareStatement(sqlStr);
			for ( int i = 0; i < ds.getRecordCount(); i++ ) {
			    PosRecord record = ds.getRecord(i);
				stmt.clearParameters();
				iparam = 1;
				stmt.setString(iparam++,  record.getAttribute("ESTM_YEAR").toString());	//�򰡳⵵
				stmt.setString(iparam++,  record.getAttribute("ESTM_NUM").toString());	//��ȸ��
				stmt.setString(iparam++,  record.getAttribute("ATT_CD").toString());		//�����ڵ�
				stmt.setString(iparam++,  record.getAttribute("EMP_NO").toString());   	//���
				stmt.setString(iparam++,  record.getAttribute("EMP_NM").toString());   	//����
		        
				stmt.setString(iparam++,  record.getAttribute("DEPT_CD").toString());   	//�μ��ڵ�
				stmt.setString(iparam++,  record.getAttribute("CNT").toString());   		//�Ǽ�
				stmt.setString(iparam++,  SESSION_USER_ID);					//�����ID(�ۼ���)
	    		stmt.addBatch();
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
     * <p> ���� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteAtt(String sYear, String sNum) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sYear);	//�򰡳⵵
        param.setValueParamter(i++, sNum);  //��ȸ��

        int dmlcount = this.getDao("rbmdao").update("rev1010_d03", param);

        return dmlcount;
    }

    /**
     * <p> �߷����� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertDate(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));	//�򰡳⵵
        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));	//��ȸ��
        param.setValueParamter(i++, record.getAttribute("EMP_NO"));		//���
        param.setValueParamter(i++, record.getAttribute("APT_DT"));   	//�߷�����
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));   	//�μ��ڵ�
        
        param.setValueParamter(i++, record.getAttribute("SEQ"));   	
        param.setValueParamter(i++, record.getAttribute("DEPT_NM"));   	//�μ���
        param.setValueParamter(i++, record.getAttribute("APT_RSN"));   	//�߷ɻ���
        param.setValueParamter(i++, record.getAttribute("WK_JOB_CD"));  //����
        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(�ۼ���)
        
        param.setValueParamter(i++, record.getAttribute("EMP_NM"));   	//����

        int dmlcount = this.getDao("rbmdao").update("rev1010_i04", param);

        return dmlcount;
    }

    /**
     * <p> �߷����� �ϰ��Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int InsertAllDate(Connection conn, PosContext ctx, PosDataset ds)  
    {
    	int dmlcount = 0;
        PreparedStatement stmt = null;
        String sqlStr = Util.getQuery(ctx, "rev1010_i04");
        PosParameter param = new PosParameter();   

        try {
        	int iparam = 1;
			stmt = conn.prepareStatement(sqlStr);
			for ( int i = 0; i < ds.getRecordCount(); i++ ) {
			    PosRecord record = ds.getRecord(i);
				stmt.clearParameters();
				iparam = 1;
			    stmt.setString(iparam++,  record.getAttribute("ESTM_YEAR").toString());	//�򰡳⵵
		        stmt.setString(iparam++,  record.getAttribute("ESTM_NUM").toString());	//��ȸ��
		        stmt.setString(iparam++,  record.getAttribute("EMP_NO").toString());		//���
		        stmt.setString(iparam++,  record.getAttribute("APT_DT").toString());   	//�߷�����
		        stmt.setString(iparam++,  record.getAttribute("DEPT_CD").toString());   	//�μ��ڵ�
		        
		        stmt.setString(iparam++,  record.getAttribute("SEQ").toString());   	
		        stmt.setString(iparam++,  record.getAttribute("DEPT_NM").toString());   	//�μ���
		        stmt.setString(iparam++,  record.getAttribute("APT_RSN").toString());   	//�߷ɻ���
		        stmt.setString(iparam++,  record.getAttribute("WK_JOB_CD").toString());  //����
		        stmt.setString(iparam++,  SESSION_USER_ID);					//�����ID(�ۼ���)
		        
		        stmt.setString(iparam++,  record.getAttribute("EMP_NM").toString());   	//����
	    		stmt.addBatch();
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
     * <p> ���� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteDate(String sYear, String sNum) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sYear);	//�򰡳⵵
        param.setValueParamter(i++, sNum);  //��ȸ��

        int dmlcount = this.getDao("rbmdao").update("rev1010_d04", param);

        return dmlcount;
    }

    /**
     * <p> �μ��� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertDeptHead(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));		//�򰡳⵵
        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));		//��ȸ��
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));		//�μ��ڵ�
        param.setValueParamter(i++, record.getAttribute("UPPER_DEPT_CD"));  //�����μ��ڵ�
        param.setValueParamter(i++, record.getAttribute("DEPT_NM"));   		//�μ���
        
        param.setValueParamter(i++, record.getAttribute("EMP_NO"));   		//���
        param.setValueParamter(i++, record.getAttribute("EMP_NM"));   		//����
        param.setValueParamter(i++, record.getAttribute("HP_NO"));   		//�̵���ȭ��ȣ
        param.setValueParamter(i++, record.getAttribute("JOB_RANK_NM"));   	//���޸�
        param.setValueParamter(i++, record.getAttribute("LVL"));   			//����

        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(�ۼ���)

        int dmlcount = this.getDao("rbmdao").update("rev1010_i05", param);

		
        return dmlcount;
    }

    /**
     * <p> �μ��� �ϰ� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int InsertAllDeptHead(Connection conn, PosContext ctx, PosDataset ds)
    {
    	int dmlcount = 0;
        PreparedStatement stmt = null;
        String sqlStr = Util.getQuery(ctx, "rev1010_i05");
        PosParameter param = new PosParameter();   

        try {
        	int iparam = 1;
			stmt = conn.prepareStatement(sqlStr);
			for ( int i = 0; i < ds.getRecordCount(); i++ ) {
			    PosRecord record = ds.getRecord(i);
				stmt.clearParameters();
				iparam = 1;
				stmt.setString(iparam++,  record.getAttribute("ESTM_YEAR").toString());		//�򰡳⵵
		        stmt.setString(iparam++,  record.getAttribute("ESTM_NUM").toString());		//��ȸ��
		        stmt.setString(iparam++,  record.getAttribute("DEPT_CD").toString());		//�μ��ڵ�
		        stmt.setString(iparam++,  record.getAttribute("DEPT_CD").toString());		//�μ��ڵ�
		        stmt.setString(iparam++,  record.getAttribute("UPPER_DEPT_CD").toString());  //�����μ��ڵ�
		        stmt.setString(iparam++,  record.getAttribute("DEPT_NM").toString());   		//�μ���
		        
		        stmt.setString(iparam++,  record.getAttribute("EMP_NO").toString());   		//���
		        stmt.setString(iparam++,  record.getAttribute("EMP_NM").toString());   		//����
		        stmt.setString(iparam++,  record.getAttribute("HP_NO").toString());   		//�̵���ȭ��ȣ
		        stmt.setString(iparam++,  record.getAttribute("JOB_RANK_NM").toString());   	//���޸�
		        stmt.setString(iparam++,  record.getAttribute("LVL").toString());   			//����
		        stmt.setString(iparam++,  SESSION_USER_ID);					//�����ID(�ۼ���)

	    		stmt.addBatch(); 
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
     * <p> �μ��� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteDeptHead(String sYear, String sNum) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sYear);	//�򰡳⵵
        param.setValueParamter(i++, sNum);  //��ȸ��

        int dmlcount = this.getDao("rbmdao").update("rev1010_d05", param);

        return dmlcount;
    }
    
    /**
     * <p> �μ� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertDept(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));		//�򰡳⵵
        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));		//��ȸ��
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));		//�μ��ڵ�
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));		//�μ��ڵ�
        param.setValueParamter(i++, record.getAttribute("DEPT_NM"));   		//�μ���
        param.setValueParamter(i++, SESSION_USER_ID);						//�����ID(�ۼ���)

        int dmlcount = this.getDao("rbmdao").update("rev1010_i06", param);

		
        return dmlcount;
    }

    /**
     * <p> �μ� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int InsertAllDept(Connection conn, PosContext ctx, PosDataset ds) 
    {
    	int dmlcount = 0;
        PreparedStatement stmt = null;
        String sqlStr = Util.getQuery(ctx, "rev1010_i06");
        PosParameter param = new PosParameter();   

        try {
        	int iparam = 1;
			stmt = conn.prepareStatement(sqlStr);
			for ( int i = 0; i < ds.getRecordCount(); i++ ) {
			    PosRecord record = ds.getRecord(i);
				stmt.clearParameters();
				iparam = 1;
				stmt.setString(iparam++,  record.getAttribute("ESTM_YEAR").toString());	//�򰡳⵵
		        stmt.setString(iparam++,  record.getAttribute("ESTM_NUM").toString());		//��ȸ��
		        stmt.setString(iparam++,  record.getAttribute("DEPT_CD").toString());		//�μ��ڵ�
		        stmt.setString(iparam++,  record.getAttribute("DEPT_CD").toString());		//�μ��ڵ�
		        stmt.setString(iparam++,  record.getAttribute("DEPT_NM").toString());   	//�μ���
		        stmt.setString(iparam++,  SESSION_USER_ID);								//�����ID(�ۼ���)

	    		stmt.addBatch();
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
     * <p> �μ� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteDept(String sYear, String sNum) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sYear);	//�򰡳⵵
        param.setValueParamter(i++, sNum);  //��ȸ��

        int dmlcount = this.getDao("rbmdao").update("rev1010_d06", param);

        return dmlcount;
    }

    /**
     * <p> ��� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertEmp(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));		//�򰡳⵵
        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));		//��ȸ��
        param.setValueParamter(i++, record.getAttribute("PERM_TEMP_GBN"));  //����/�����Ա���
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));		//�μ��ڵ�
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));		//�μ��ڵ�
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));		//�μ��ڵ�
        param.setValueParamter(i++, record.getAttribute("WRK_GBN"));		//�ٹ��ڱ���
        param.setValueParamter(i++, record.getAttribute("EMP_NO"));   		//���
        
        param.setValueParamter(i++, record.getAttribute("EMP_NM"));   		//����
        param.setValueParamter(i++, record.getAttribute("RES_NO"));			//�ֹι�ȣ
        param.setValueParamter(i++, record.getAttribute("PERM_TEMP_GBN"));  //����/�����Ա���
        param.setValueParamter(i++, record.getAttribute("ESTM_DEPT"));		//�򰡺μ�
        param.setValueParamter(i++, record.getAttribute("ESTM_DEPT"));		//�򰡺μ�
        param.setValueParamter(i++, record.getAttribute("ESTM_DEPT"));		//�򰡺μ�
        param.setValueParamter(i++, record.getAttribute("PWD_NO"));			//��й�ȣ
        param.setValueParamter(i++, record.getAttribute("WK_JOB_CD"));   	//�����ڵ�
        
        param.setValueParamter(i++, record.getAttribute("APT_DT"));   		//�߷�����
        param.setValueParamter(i++, record.getAttribute("JOB_TIT_CD"));		//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("JOB_TIT_NM"));		//���޸�
        param.setValueParamter(i++, record.getAttribute("HP_NO"));			//�̵���ȭ��ȣ
        param.setValueParamter(i++, record.getAttribute("ENTR_DT"));		//�Ի�����
        
        param.setValueParamter(i++, record.getAttribute("ESTM_ESC_GBN"));   	//�򰡴�������ڱ���
        param.setValueParamter(i++, record.getAttribute("APT_DT"));   		//1��2�����ڱ���
        param.setValueParamter(i++, record.getAttribute("MNG_GBN"));		//����ڱ���
        param.setValueParamter(i++, record.getAttribute("MULT_ESTM_GRP"));	//�ٸ����ڱ׷�
        param.setValueParamter(i++, record.getAttribute("CNL_GBN"));		//����ұ���
        
        param.setValueParamter(i++, record.getAttribute("ESTM_WK_JOB"));   	//�򰡺����ڵ�    
        param.setValueParamter(i++, record.getAttribute("ESTM_EMP"));  		//���ڻ��
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));		//���������ڵ�
        param.setValueParamter(i++, record.getAttribute("DIV_NO"));			//��ǥ���ڵ�
        param.setValueParamter(i++, record.getAttribute("MNR_FST_SND"));	//�ٹ��µ�1��2������
        
        param.setValueParamter(i++, record.getAttribute("SVR_FST_SND"));   	//����1��2������
        param.setValueParamter(i++, record.getAttribute("PERM_TEMP_GBN"));  //����/�����Ա���
        param.setValueParamter(i++, record.getAttribute("CO_WRK_GBN"));     //�����ٹ��� ����
        param.setValueParamter(i++, SESSION_USER_ID);						//�����ID(�ۼ���)

        int dmlcount = this.getDao("rbmdao").update("rev1010_i07", param);
		
        return dmlcount;
    }

    /**
     * <p> ��� �ϰ� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int InsertAllEmp(Connection conn, PosContext ctx, PosDataset ds)  
    {    
    	int dmlcount = 0;
	    PreparedStatement stmt = null;
	    String sqlStr = Util.getQuery(ctx, "rev1010_i07");
	    PosParameter param = new PosParameter();   
	
	    try {
	    	int iparam = 1;
			stmt = conn.prepareStatement(sqlStr);
			for ( int i = 0; i < ds.getRecordCount(); i++ ) {
			    PosRecord record = ds.getRecord(i);
				stmt.clearParameters();
				iparam = 1;
				stmt.setString(iparam++,  record.getAttribute("ESTM_YEAR").toString());		//�򰡳⵵
		        stmt.setString(iparam++,  record.getAttribute("ESTM_NUM").toString());		//��ȸ��
		        stmt.setString(iparam++,  record.getAttribute("PERM_TEMP_GBN").toString());  //����/�����Ա���
		        stmt.setString(iparam++,  record.getAttribute("DEPT_CD").toString());		//�μ��ڵ�
		        stmt.setString(iparam++,  record.getAttribute("DEPT_CD").toString());		//�μ��ڵ�
		        stmt.setString(iparam++,  record.getAttribute("DEPT_CD").toString());		//�μ��ڵ�
		        stmt.setString(iparam++,  record.getAttribute("WRK_GBN").toString());		//�ٹ��ڱ���
		        stmt.setString(iparam++,  record.getAttribute("EMP_NO").toString());   		//���
		        
		        stmt.setString(iparam++,  record.getAttribute("EMP_NM").toString());   		//����
		        stmt.setString(iparam++,  record.getAttribute("RES_NO").toString());			//�ֹι�ȣ
		        stmt.setString(iparam++,  record.getAttribute("PERM_TEMP_GBN").toString());  //����/�����Ա���
		        stmt.setString(iparam++,  record.getAttribute("ESTM_DEPT").toString());		//�򰡺μ�
		        stmt.setString(iparam++,  record.getAttribute("ESTM_DEPT").toString());		//�򰡺μ�
		        stmt.setString(iparam++,  record.getAttribute("ESTM_DEPT").toString());		//�򰡺μ�
		        stmt.setString(iparam++,  record.getAttribute("PWD_NO").toString());			//��й�ȣ
		        stmt.setString(iparam++,  record.getAttribute("WK_JOB_CD").toString());   	//�����ڵ�
		        
		        stmt.setString(iparam++,  record.getAttribute("APT_DT").toString());   		//�߷�����
		        stmt.setString(iparam++,  record.getAttribute("JOB_TIT_CD").toString());		//�����ڵ�
		        stmt.setString(iparam++,  record.getAttribute("JOB_TIT_NM").toString());		//���޸�
		        stmt.setString(iparam++,  record.getAttribute("HP_NO").toString());			//�̵���ȭ��ȣ
		        stmt.setString(iparam++,  (String)record.getAttribute("ENTR_DT"));		//�Ի�����
		        
		        stmt.setString(iparam++,  record.getAttribute("ESTM_ESC_GBN").toString());   	//�򰡴�������ڱ���
		        stmt.setString(iparam++,  record.getAttribute("APT_DT").toString());   		//1��2�����ڱ���
		        stmt.setString(iparam++,  record.getAttribute("MNG_GBN").toString());		//����ڱ���
		        stmt.setString(iparam++,  record.getAttribute("MULT_ESTM_GRP").toString());	//�ٸ����ڱ׷�
		        stmt.setString(iparam++,  record.getAttribute("CNL_GBN").toString());		//����ұ���
		        
		        stmt.setString(iparam++,  record.getAttribute("ESTM_WK_JOB").toString());   	//�򰡺����ڵ�    
		        stmt.setString(iparam++,  record.getAttribute("ESTM_EMP").toString());  		//���ڻ��
		        stmt.setString(iparam++,  record.getAttribute("COMM_NO").toString());		//���������ڵ�
		        stmt.setString(iparam++,  record.getAttribute("DIV_NO").toString());			//��ǥ���ڵ�
		        stmt.setString(iparam++,  record.getAttribute("MNR_FST_SND").toString());	//�ٹ��µ�1��2������
		        
		        stmt.setString(iparam++,  record.getAttribute("SVR_FST_SND").toString());   	//����1��2������
		        stmt.setString(iparam++,  record.getAttribute("PERM_TEMP_GBN").toString());  //����/�����Ա���
		        stmt.setString(iparam++,  (String)record.getAttribute("CO_WRK_GBN"));     //�����ٹ��� ����
		        stmt.setString(iparam++,  SESSION_USER_ID);						//�����ID(�ۼ���)
		        
	    		stmt.addBatch();
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
     * <p> ��� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteEmp(String sYear, String sNum) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sYear);	//�򰡳⵵
        param.setValueParamter(i++, sNum);  //��ȸ��

        int dmlcount = this.getDao("rbmdao").update("rev1010_d07", param);

        return dmlcount;
    }
    
    /**
     * <p> ��ǥ�� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertComm(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));		//�򰡳⵵
        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));		//��ȸ��
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));		//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("DIV_NO"));   		//��ǥ���ڵ�
        param.setValueParamter(i++, record.getAttribute("TELLER_ID"));   	//�߸ſ���ȣ
        
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));   		//�μ��ڵ�
        param.setValueParamter(i++, record.getAttribute("YEAR_DATE"));   	//�ֱ�����
        param.setValueParamter(i++, SESSION_USER_ID);						//�����ID(�ۼ���)

        int dmlcount = this.getDao("rbmdao").update("rev1010_i08", param);

		
        return dmlcount;
    }

    /**
     * <p> ��ǥ�� �ϰ� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int InsertAllComm(Connection conn, PosContext ctx, PosDataset ds)  
    {    	
    	int dmlcount = 0;
	    PreparedStatement stmt = null;
	    String sqlStr = Util.getQuery(ctx, "rev1010_i08");
	    PosParameter param = new PosParameter();   
	
	    try {
	    	int iparam = 1;
			stmt = conn.prepareStatement(sqlStr);
			for ( int i = 0; i < ds.getRecordCount(); i++ ) {
			    PosRecord record = ds.getRecord(i);
				stmt.clearParameters();
				iparam = 1;
		        stmt.setString(iparam++,  record.getAttribute("ESTM_YEAR").toString());		//�򰡳⵵
		        stmt.setString(iparam++,  record.getAttribute("ESTM_NUM").toString());		//��ȸ��
		        stmt.setString(iparam++,  record.getAttribute("COMM_NO").toString());		//�����ڵ�
		        stmt.setString(iparam++,  record.getAttribute("DIV_NO").toString());   		//��ǥ���ڵ�
		        stmt.setString(iparam++,  record.getAttribute("TELLER_ID").toString());   	//�߸ſ���ȣ
		        
		        stmt.setString(iparam++,  record.getAttribute("DEPT_CD").toString());   		//�μ��ڵ�
		        stmt.setString(iparam++,  record.getAttribute("YEAR_DATE").toString());   	//�ֱ�����
		        stmt.setString(iparam++,  SESSION_USER_ID);						//�����ID(�ۼ���)
				
	    		stmt.addBatch();
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
     * <p> ��ǥ�� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteComm(String sYear, String sNum) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sYear);	//�򰡳⵵
        param.setValueParamter(i++, sNum);  //��ȸ��

        int dmlcount = this.getDao("rbmdao").update("rev1010_d08", param);

        return dmlcount;
    }
    
    /**
     * <p> ���� ���������  �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertAprv(String sEvalYear, String sEvalNum) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, sEvalYear);		//1.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);		//  ��ȸ��
        param.setValueParamter(i++, sEvalYear);		//2.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);		//  ��ȸ��
        param.setValueParamter(i++, sEvalYear);		//3.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);		//  ��ȸ��

        int dmlcount = this.getDao("rbmdao").update("rev1010_i10", param);

		
        return dmlcount;
    }
    
    /**
     * <p> ���� ���������  ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteAprv(String sEvalYear, String sEvalNum) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sEvalYear);	//�򰡳⵵
        param.setValueParamter(i++, sEvalNum);  //��ȸ��
        param.setValueParamter(i++, sEvalYear);	//�򰡳⵵
        param.setValueParamter(i++, sEvalNum);  //��ȸ��

        int dmlcount = this.getDao("rbmdao").update("rev1010_d10", param);

        return dmlcount;
    }

    /**
     * <p> �߸Ž��� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    public int insertTelmp(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));	//�򰡳⵵
        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));	//��ȸ��
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));	//�μ�
        param.setValueParamter(i++, record.getAttribute("TELLER_ID"));	//�߸ſ���ȣ
        param.setValueParamter(i++, record.getAttribute("EMP_NO"));   	//���
        
        param.setValueParamter(i++, record.getAttribute("EMP_NM"));   	//����
        param.setValueParamter(i++, record.getAttribute("SOLD_AVG"));   //�߸Ž����Ǽ�
        param.setValueParamter(i++, record.getAttribute("TOTAL_CNT"));   
        param.setValueParamter(i++, record.getAttribute("WK_DAY_CNT"));   
        
        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(�ۼ���)

        int dmlcount = this.getDao("rbmdao").update("rev1010_i09", param);
        
        return dmlcount;
    }

    /**
     * <p> �߸Ž��� �ϰ� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    public int InsertAllTelmp(Connection conn, PosContext ctx, PosDataset ds)  
    {    	
    	int dmlcount = 0;
	    PreparedStatement stmt = null;
	    String sqlStr = Util.getQuery(ctx, "rev1010_i09");
	    PosParameter param = new PosParameter();   
	
	    try {
	    	int iparam = 1;
			stmt = conn.prepareStatement(sqlStr);
			for ( int i = 0; i < ds.getRecordCount(); i++ ) {
			    PosRecord record = ds.getRecord(i);
				stmt.clearParameters();
				iparam = 1;
		        stmt.setString(iparam++,  record.getAttribute("ESTM_YEAR").toString());	//�򰡳⵵
		        stmt.setString(iparam++,  record.getAttribute("ESTM_NUM").toString());	//��ȸ��
		        stmt.setString(iparam++,  record.getAttribute("DEPT_CD").toString());	//�μ�
		        stmt.setString(iparam++,  record.getAttribute("TELLER_ID").toString());	//�߸ſ���ȣ
		        stmt.setString(iparam++,  record.getAttribute("EMP_NO").toString());   	//���
		        
		        stmt.setString(iparam++,  record.getAttribute("EMP_NM").toString());   	//����
		        stmt.setString(iparam++,  record.getAttribute("SOLD_AVG").toString());   //�߸Ž����Ǽ�
		        stmt.setString(iparam++,  record.getAttribute("TOTAL_CNT").toString());   
		        stmt.setString(iparam++,  record.getAttribute("WK_DAY_CNT").toString());   
		        
		        stmt.setString(iparam++,  SESSION_USER_ID);					//�����ID(�ۼ���)
	    		stmt.addBatch();
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
     * <p> �߸Ž��� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    public int deleteTelmp(String sYear, String sNum) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sYear);	//�򰡳⵵
        param.setValueParamter(i++, sNum);  //��ȸ��

        int dmlcount = this.getDao("rbmdao").update("rev1010_d09", param);

        return dmlcount;
    }

    /**
     * <p> �ٽ� ��ǥ�� ���� �߰� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertPcDiv(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));	//�򰡳⵵
        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));	//��ȸ��
        param.setValueParamter(i++, record.getAttribute("DEPT_CD"));	//
        param.setValueParamter(i++, record.getAttribute("SELL_CD"));   	//
        param.setValueParamter(i++, record.getAttribute("COMM_NO"));   	//
        
        param.setValueParamter(i++, record.getAttribute("DIV_NO"));     //
        param.setValueParamter(i++, record.getAttribute("COMM_NAME"));  //
        param.setValueParamter(i++, record.getAttribute("DIV_NAME"));   //
        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(�ۼ���)

        int dmlcount = this.getDao("rbmdao").update("rev1010_i11", param);
        
        return dmlcount;
    }

    /**
     * <p> �ٽ� ��ǥ�� ���� �ϰ� �߰� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int InsertAllPcDiv(Connection conn, PosContext ctx, PosDataset ds)  
    {    	
		int dmlcount = 0;
	    PreparedStatement stmt = null;
	    String sqlStr = Util.getQuery(ctx, "rev1010_i11");
	    PosParameter param = new PosParameter();   
	
	    try {
	    	int iparam = 1;
			stmt = conn.prepareStatement(sqlStr);
			for ( int i = 0; i < ds.getRecordCount(); i++ ) {
			    PosRecord record = ds.getRecord(i);
				stmt.clearParameters();
				iparam = 1;
				stmt.setString(iparam++,  record.getAttribute("ESTM_YEAR").toString());	//�򰡳⵵
		        stmt.setString(iparam++,  record.getAttribute("ESTM_NUM").toString());	//��ȸ��
		        stmt.setString(iparam++,  record.getAttribute("DEPT_CD").toString());	//
		        stmt.setString(iparam++,  record.getAttribute("SELL_CD").toString());   	//
		        stmt.setString(iparam++,  record.getAttribute("COMM_NO").toString());   	//
		        
		        stmt.setString(iparam++,  record.getAttribute("DIV_NO").toString());     //
		        stmt.setString(iparam++,  record.getAttribute("COMM_NAME").toString());  //
		        stmt.setString(iparam++,  record.getAttribute("DIV_NAME").toString());   //
		        stmt.setString(iparam++,  SESSION_USER_ID);					//�����ID(�ۼ���)

		        stmt.addBatch();
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
     * <p> �ٽ� ��ǥ�� ���� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deletePcDiv(String sYear, String sNum) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sYear);	//�򰡳⵵
        param.setValueParamter(i++, sNum);  //��ȸ��

        int dmlcount = this.getDao("rbmdao").update("rev1010_d11", param);

        return dmlcount;
    }
    
    /**
     * <p> �������̺� �߰� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertApprAll(String sEvalYear, String sEvalNum)
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, sEvalYear);		//1.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);		//  ��ȸ��
        param.setValueParamter(i++, sEvalYear);		//2.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);		//  ��ȸ��
        param.setValueParamter(i++, sEvalYear);		//3.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);		//  ��ȸ��
        param.setValueParamter(i++, sEvalYear);		//4.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);		//  ��ȸ��
        param.setValueParamter(i++, sEvalYear);		//5.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);		//  ��ȸ��
        param.setValueParamter(i++, sEvalYear);		//6.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);		//  ��ȸ��
        param.setValueParamter(i++, sEvalYear);		//7.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);		//  ��ȸ��
        param.setValueParamter(i++, sEvalYear);		//8.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);		//  ��ȸ��
        
        int dmlcount = this.getDao("rbmdao").update("rev1060_i01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> �������̺� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteAppr(String sEvalYear, String sEvalNum)
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setWhereClauseParameter(i++, sEvalYear);		//1.�򰡳⵵
        param.setWhereClauseParameter(i++, sEvalNum);		//2.��ȸ��
        
        int dmlcount = this.getDao("rbmdao").update("rev1060_d01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> �Ⱓ �߸Ž��� �����ڴ�  TOTAL_CNT = 99999, WK_DAY_CNT = 99999�� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    public int updateByPass(String sEvalYear, String sEvalNum)
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, sEvalYear);		//1.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);		//2.��ȸ��
        
        int dmlcount = this.getDao("rbmdao").update("rev1010_u03", param);
        
        return dmlcount;
    }
    
    /**
     * <p> �Ⱓ �߸Ž��� �������� ��� �߸ŰǼ��� ����� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    public int updateTeamAvg(String sEvalYear, String sEvalNum)
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, sEvalYear);		//1.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);		//2.��ȸ��
        
        int dmlcount = this.getDao("rbmdao").update("rev1010_u04", param);
        
        return dmlcount;
    }
}