/*================================================================================
 * �ý���			: �Ϲݻ���  ����
 * �ҽ����� �̸�	: snis.rbm.business.rbr1011.activity.SaveCommDesc.java
 * ���ϼ���		: �Ϲݻ��װ� ������ ����
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-09-16
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbr1011.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveCommDesc extends SnisActivity {
	
	public SaveCommDesc(){}

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

        sDsName = "dsCommDesc";
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);		        	
		        	nTempCnt = saveCommDesc(record);
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;		        
	        }	        
        }

        sDsName = "dsFloorMana";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            
	            Double dFloor = (Double)record.getAttribute("FLOOR");
	            int nFloor = dFloor.intValue();
	            
		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        	
		        	//�⺻Ű �ߺ�üũ
		        	if( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {
		        		if( selectFloorCnt(record) > 0 ) {
		        			try { 
		            			throw new Exception(); 
			            	} catch(Exception e) {       		
			            		this.rollbackTransaction("tx_rbm");
			            		Util.setSvcMsg(ctx, "[ " + nFloor + " ](��)�� ���� ���� �����մϴ�.");
			            		
			            		return;
			            	}
		        		}
		        	}
		        	
		        	nTempCnt = saveFloorMana(record);
			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }
		        
	            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE)
	            {
	            	//���������� ���� ��� ���� ���
	            	if( selectEquipCnt(record) > 0 ) {
	            		try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsg(ctx, "�� [ " + nFloor + " ](��)�� ���������� ��� ���̹Ƿ� �����Ͻ� �� �����ϴ�.");
		            		
		            		return;
		            	}
	            	}
	            	//�ü���Ȳ���� ���� ��� ���� ���
	            	if( selectFacCnt(record) > 0 ) {
	            		try { 
	            			throw new Exception(); 
		            	} catch(Exception e) {       		
		            		this.rollbackTransaction("tx_rbm");
		            		Util.setSvcMsg(ctx, "�� [ " + nFloor + " ](��)�� �ü���Ȳ���� ��� ���̹Ƿ� �����Ͻ� �� �����ϴ�.");
		            		
		            		return;
		            	}
	            	}
	            	
		        	nDeleteCount = nDeleteCount + deleteFloorMana(record);	            	
	            }	
	        }	         
        }
        
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
    }

    /**
     * <p> ��������_�Ϲݻ��� �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveCommDesc(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));			//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));			//�⵵
        param.setValueParamter(i++, record.getAttribute("OP_GOAL"));			//���ǥ	
        param.setValueParamter(i++, record.getAttribute("POST_CD"));			//�����ȣ
        param.setValueParamter(i++, record.getAttribute("ADDR"));				//�ּ�

        param.setValueParamter(i++, record.getAttribute("DETL_ADDR"));			//���ּ�
        param.setValueParamter(i++, record.getAttribute("INSTALL_ALOW_DT"));	//��ġ�㰡����                
        param.setValueParamter(i++, record.getAttribute("OPEN_DT"));			//��������
        param.setValueParamter(i++, record.getAttribute("ENT_FIX_NUM"));		//��������
        param.setValueParamter(i++, record.getAttribute("SALES_GOAL_CRA"));		//�����ǥ���
        param.setValueParamter(i++, record.getAttribute("SALES_GOAL_MRA"));		//�����ǥ����
        param.setValueParamter(i++, record.getAttribute("SALES_GOAL_CRA_GREEN"));		//�����ǥ���(�׸�ī��)
        param.setValueParamter(i++, record.getAttribute("SALES_GOAL_MRA_GREEN"));		//�����ǥ����(�׸�ī��)
        param.setValueParamter(i++, record.getAttribute("SEAT_NUM_NORMAL"));	//�Ϲݽ� �¼���
        param.setValueParamter(i++, record.getAttribute("SEAT_NUM_FIXED"));		//�����¼��� �¼���
        param.setValueParamter(i++, record.getAttribute("SEAT_NUM_GREEN"));		//�׸�ī���� �¼���
        
        param.setValueParamter(i++, SESSION_USER_ID);							//�����ID(�ۼ���)
        param.setValueParamter(i++, SESSION_USER_ID);							//�����ID(������)
      
        int dmlcount = this.getDao("rbmdao").update("rbr1011_m01", param);
        
        return dmlcount;
    }
     
    /**
     * <p> ��������_������ �Է�/���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveFloorMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        //pk set 
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));			//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));			//�⵵
        param.setValueParamter(i++, record.getAttribute("FLOOR"));				//��
        param.setValueParamter(i++, record.getAttribute("COMM_AREA_SQMT"));		//�������(��)	
        param.setValueParamter(i++, record.getAttribute("COMM_AREA_PY"));		//�������(��)
        
        param.setValueParamter(i++, record.getAttribute("ONLY_AREA_SQMT"));		//�������(��)
        param.setValueParamter(i++, record.getAttribute("ONLY_AREA_PY"));		//�������(��)
        param.setValueParamter(i++, record.getAttribute("RELEA_COT_MANNED"));	//�߸�â��(����)                
        param.setValueParamter(i++, record.getAttribute("RELEA_COT_MANLESS"));	//�߸�â��(����)
        param.setValueParamter(i++, record.getAttribute("RELEA_MACH_MANNED"));	//�߸ű�(����)

        param.setValueParamter(i++, record.getAttribute("RELEA_MACH_MANLESS"));	//�߸ű�(����)
        param.setValueParamter(i++, record.getAttribute("GRN_CRD_LOC"));		//�׸�ī����
        param.setValueParamter(i++, record.getAttribute("RMK"));				//���        
        param.setValueParamter(i++, SESSION_USER_ID);							//�����ID(�ۼ���)
        param.setValueParamter(i++, SESSION_USER_ID);							//�����ID(������)
        
        int dmlcount = this.getDao("rbmdao").update("rbr1011_m02", param);
        
        return dmlcount;
    }
    
    /**
     * <p> ��������_������ ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteFloorMana(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));		//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));		//�⵵
        param.setValueParamter(i++, record.getAttribute("FLOOR"));			//��
        
        int dmlcount = this.getDao("rbmdao").update("rbr1011_d01", param);

        return dmlcount;
    }
    
    /**
     * <p> ������ �⺻Ű ���� ��ȸ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int selectFloorCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));    //�����ڵ�
        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));  //�⵵
        param.setWhereClauseParameter(i++, record.getAttribute("FLOOR"));      //��
                		
        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbr1011_s03", param);  
        
        PosRow pr[] = rtnRecord.getAllRow();
        
        String rtnCnt = String.valueOf(pr[0].getAttribute("CNT"));
        
        return Integer.valueOf(rtnCnt).intValue();
    }
    
    /**
     * <p> ������ ���������� ���� ����ϴ��� ���� ��ȸ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int selectEquipCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("FLOOR"));      //��
                		
        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbr1011_s04", param);  
        
        PosRow pr[] = rtnRecord.getAllRow();
        
        String rtnCnt = String.valueOf(pr[0].getAttribute("CNT"));
        
        return Integer.valueOf(rtnCnt).intValue();
    }
    
    /**
     * <p> ������ ���������� ���� ����ϴ��� ���� ��ȸ </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int selectFacCnt(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;

        param.setWhereClauseParameter(i++, record.getAttribute("FLOOR"));      //��
                		
        PosRowSet rtnRecord = this.getDao("rbmdao").find("rbr1011_s05", param);  
        
        PosRow pr[] = rtnRecord.getAllRow();
        
        String rtnCnt = String.valueOf(pr[0].getAttribute("CNT"));
        
        return Integer.valueOf(rtnCnt).intValue();
    }
}
