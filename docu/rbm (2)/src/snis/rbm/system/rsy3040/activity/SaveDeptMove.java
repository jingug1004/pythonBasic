package snis.rbm.system.rsy3040.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;
import snis.rbm.common.util.UtilDb;

public class SaveDeptMove extends SnisActivity {
	public SaveDeptMove(){
		
		
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
        
        String  curDateTime = "";
        UtilDb udb = new UtilDb();
        curDateTime = udb.getCurTime();
        
        sDsName = "dsDeptMove";
        
        String sAuthChk = "";
        String sDelAuthChk = "";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            	
	            
	            sAuthChk = (String)record.getAttribute("AUTH_CHK");
	            if(sAuthChk == null) sAuthChk ="";

	            sDelAuthChk = (String)record.getAttribute("DELAUTH_CHK");
	            if(sDelAuthChk == null) sDelAuthChk ="";
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
				      || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        		if(sAuthChk.equals("1")){ //��������
		        			
		        		 	nTempCnt = updateAuthAplyDept(record);
		
		        		}else if(sDelAuthChk.equals("1")){	//���ѻ��� 
		        			if(((String)record.getAttribute("AUTH_GBN")).equals("001")){ //�Ϲݻ����
		        				
		             		 	deleteMnAuth(record, curDateTime);
		             		 	
		             		 	nTempCnt = deleteAuthAplyDept(record);
		        			}else if(((String)record.getAttribute("AUTH_GBN")).equals("002")){ //Ư��������
		        				
		        				deleteAdminInfo(record);
		             		 	
		             		 	nTempCnt = deleteAuthAplyDept(record);
		
		             		 	
		        			}
		        	
		        		}
		        }
	        	nSaveCount = nSaveCount + nTempCnt;
	        	continue;
	        }
	        

        }

        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
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
        param.setValueParamter(i++, record.getAttribute("CUR_DEPT_CD"));	//�̵��ĺμ�
        param.setValueParamter(i++, record.getAttribute("CUR_TEAM_CD"));	//�̵�����
        param.setValueParamter(i++, SESSION_USER_ID);							//�ۼ���
     

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("USER_ID" ));		//�����ID
        param.setWhereClauseParameter(i++, record.getAttribute("AUTH_GBN" ));		//���ѱ���

        int dmlcount = this.getDao("rbmdao").update("rsy3040_u01", param);
        return dmlcount;
    }

    
   
    /**
     * <p> �޴����Ѱ��� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteMnAuth(PosRecord record, String curDtm) 
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
     * <p>  ��������μ� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteAuthAplyDept(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("USER_ID") );	
        param.setValueParamter(i++, record.getAttribute("AUTH_GBN") );
        
        
        int dmlcount = this.getDao("rbmdao").update("rsy3040_d02", param);

        return dmlcount;
    }
    
    /**
     * <p> Ư�������� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteAdminInfo(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("USER_ID") );
        
        int dmlcount = this.getDao("rbmdao").update("rsy3040_d03", param);

        return dmlcount;
    }
}
