/*================================================================================
 * �ý���			: �޴� ����
 * �ҽ����� �̸�	: snis.rbm.system.rsy0020.activity.SaveMenu.java
 * ���ϼ���		: �޴� ����
 * �ۼ���			: �̿���
 * ����			: 1.0.0
 * ��������		: 2011-07-29
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.system.rsy2010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.*;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �Խù��� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther �̿���
* @version 1.0
*/
public class SaveMenu extends SnisActivity
{    
    public SaveMenu()
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
        	        
        sDsName = "dsMenuGrpList";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	        }
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
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        sDsName = "dsMenuGrpList";
        
        String sBaseSrchYn;
        String sBaseInstYn;
        String sPersonalDataYn; //�������� 
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	if ( (nTempCnt = update(record)) == 0 ) {
		        		nTempCnt = insert(record);
		        	}
		        	sBaseSrchYn = (String)record.getAttribute("BASE_SRCH_YN");
		        	if(sBaseSrchYn == null) sBaseSrchYn="";
		        	
		        	sBaseInstYn = (String)record.getAttribute("BASE_INST_YN");
		        	if(sBaseInstYn == null) sBaseInstYn="";
		        	
		        	sPersonalDataYn = (String)record.getAttribute("PERSONAL_DATA_MN");
		        	if(sPersonalDataYn == null) sPersonalDataYn=""; //��������
		        	
		        	if(nTempCnt>0){
		        		
		        		//default ��ȸ 
		        	    if(sBaseSrchYn.equals("Y")){
		        	    	updateMenuBaseSrchY(record);
		        	    }else if(sBaseSrchYn.equals("N")){
		        	    	updateMenuBaseSrchN(record);  // ���� �޴� BaseSrch N ���� ����
		        	    	
		        	    	updateMenuBaseSrchN(record);  // ���� �� �����޴� BaseSrch N ���� ����
		        	    	
		        	    	updateChildMenuBaseSrchN(record);
		        	    }
		        	
		        	    
		        	    //default ����
		        	    if(sBaseInstYn.equals("Y")){
		        	    	updateMenuBaseInstY(record);
		        	    }else if(sBaseInstYn.equals("N")){
		        	    	updateMenuBaseInstN(record);  // ���� �޴� BaseInst N ���� ����
		        	    	
		        	    	updateMenuBaseInstN(record);  // ���� �� �����޴� BaseInst N ���� ����
		        	    	
		        	    	updateChildMenuBaseInstN(record);
		        	    }
		        	}
		        	
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	if(getChildMenuCnt(record) >0){
	            		
		        		try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsg(ctx, "���� �޴��� �����մϴ�. !");
		            		return "F";
		            		
		            	}
	            	}else{
	            		
	            		
	            		nDeleteCount = nDeleteCount + delete(record);	
	            	}
		        	
	            	            	
	            }		        
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
        
        return "";
    }
    


    /**
     * <p> �޴� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insert(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MN_ID"      ));
        param.setValueParamter(i++, record.getAttribute("MN_NM"      ));
        param.setValueParamter(i++, record.getAttribute("ORD_NO"      ));
        param.setValueParamter(i++, record.getAttribute("UP_MN_ID"      ));
        param.setValueParamter(i++, record.getAttribute("SCRN_ID"      ));
        param.setValueParamter(i++, record.getAttribute("URL"      ));
        param.setValueParamter(i++, record.getAttribute("MN_LEVEL"      ));
        param.setValueParamter(i++, record.getAttribute("RMK"      ));
        param.setValueParamter(i++, record.getAttribute("BASE_SRCH_YN"      ));
        param.setValueParamter(i++, record.getAttribute("BASE_INST_YN"      ));
        param.setValueParamter(i++, record.getAttribute("PERSONAL_DATA_MN"      ));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, SESSION_USER_ID);
        
        int dmlcount = this.getDao("rbmdao").update("rsy2010_i01", param);
        
        return dmlcount;
    }
    
    
    
    /**
     * <p> �޴� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int update(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MN_NM"      ));
        param.setValueParamter(i++, record.getAttribute("ORD_NO"      ));
        param.setValueParamter(i++, record.getAttribute("SCRN_ID"      ));
        param.setValueParamter(i++, record.getAttribute("URL"      ));
        param.setValueParamter(i++, record.getAttribute("RMK"      ));
        param.setValueParamter(i++, record.getAttribute("BASE_SRCH_YN"      ));
        param.setValueParamter(i++, record.getAttribute("BASE_INST_YN"      ));
        param.setValueParamter(i++, record.getAttribute("PERSONAL_DATA_MN"      ));
        param.setValueParamter(i++, record.getAttribute("MN_LEVEL"      ));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("MN_ID" ));

        int dmlcount = this.getDao("rbmdao").update("rsy2010_u01", param);
        return dmlcount;
    }

    /**
     * <p> BaseSrch ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateMenuBaseSrchY(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MN_ID"      ));
        param.setValueParamter(i++, record.getAttribute("MN_ID"      ));

        i = 0;

        int dmlcount = this.getDao("rbmdao").update("rsy2010_u02", param);
        return dmlcount;
    }

    /**
     * <p> BaseSrch ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateMenuBaseSrchN(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MN_ID"      ));     
        
        i = 0;


        int dmlcount = this.getDao("rbmdao").update("rsy2010_u03", param);
        return dmlcount;
    }
    
    
    /**
     * <p> BaseSrch �����޴�  N ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateChildMenuBaseSrchN(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MN_ID"      ));
      
        int dmlcount = this.getDao("rbmdao").update("rsy2010_u04", param);
        return dmlcount;
    }
    
    
    /**
     * <p> BaseInst ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateMenuBaseInstY(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MN_ID"      ));
        param.setValueParamter(i++, record.getAttribute("MN_ID"      ));

        i = 0;

        int dmlcount = this.getDao("rbmdao").update("rsy2010_u05", param);
        return dmlcount;
    }

    /**
     * <p> BaseInst ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateMenuBaseInstN(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MN_ID"      ));     
        
        i = 0;


        int dmlcount = this.getDao("rbmdao").update("rsy2010_u06", param);
        return dmlcount;
    }
    
    
    /**
     * <p> BaseInst �����޴�  N ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateChildMenuBaseInstN(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MN_ID"      ));
      
        int dmlcount = this.getDao("rbmdao").update("rsy2010_u07", param);
        return dmlcount;
    }
    
    
    /**
     * <p> �޴� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int delete( PosRecord record) 
    {
        PosParameter param 	= new PosParameter();
        PosRowSet    prs 	= null; 
        int i 				= 0;
        int dmlcount		= 0;
                
        param.setWhereClauseParameter(i++, record.getAttribute("MN_ID" ));

        
        dmlcount += this.getDao("rbmdao").update("rsy2010_d03", param);
        dmlcount += this.getDao("rbmdao").update("rsy2010_d02", param); 	// �޴������� ���� �̷µ� ���� ����
        dmlcount += this.getDao("rbmdao").update("rsy2010_d01", param);  
        
        return dmlcount;
    }
    
    /**
     * <p> ���� �޴� Count ��ȸ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int getChildMenuCnt( PosRecord record) 
    {
        PosParameter param 	= new PosParameter();
        PosRowSet    prs 	= null; 
        int i 				= 0;
        int dmlcount		= 0;
                
        param.setWhereClauseParameter(i++, record.getAttribute("MN_ID" ));
        
        prs = this.getDao("rbmdao").find("rsy2010_s01", param);
        
        dmlcount = prs.count();
    
        
        return dmlcount;
    }
}