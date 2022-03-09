/*================================================================================
 * �ý���			: �߸ſ����ܱⰣ
 * �ҽ����� �̸�	: snis.rbm.business.rev3200.activity.SaveDistribution.java
 * ���ϼ���		: �߸ſ����ܱⰣ ����
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2012-01-15
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rev3200.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.business.rev2010.activity.SavePrmRslt;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveEscEmp extends SnisActivity {
		public SaveEscEmp(){}
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
	    	int nSize = 0;
	    	
	    	String sEscStrDt = "";
	    	String sEscEndDt = "";
	    	String sOrgEscStrDt = "";
	    	String sOrgEscEndDt = "";

	    	String sDsName = "";
	    	
	    	PosDataset ds;

	        String sEvalYear = (String)ctx.get("ESTM_YEAR");
	        String sEvalNum  = (String)ctx.get("ESTM_NUM");
	        
	        //�� ������ ���� X
	        SavePrmRslt SavePrmRslt = new SavePrmRslt();
	        
	        if( SavePrmRslt.getEndYn(sEvalYear, sEvalNum).equals("Y") ) {
	        	try { 
        			throw new Exception(); 
            	} catch(Exception e) {       		
            		this.rollbackTransaction("tx_rbm");
            		Util.setSvcMsg(ctx, "�򰡸����� �Ϸ�Ǿ� �����Ͻ� �� �����ϴ�.");
            		
            		return;
            	}
	        }
	        
	        sDsName = "dsEscEmp";
	        String sMsg = "���ܱⰣ�� ������ ���Ŀ� �߸Ž����� �� �ǵ� ���� ��쿡�� ���ܱ����� [�Ⱓ]���� �������ּ���.";
	        	
	        if ( ctx.get(sDsName) != null ) {
		        ds   		 = (PosDataset) ctx.get(sDsName);
		        nSize 		 = ds.getRecordCount();

		        for ( int i = 0; i < nSize; i++ ) {
		            PosRecord record = ds.getRecord(i);
		            
		            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT) {
		            	nSaveCount += insertEscEmp(record);
		            	
		            	if( getReleaCnt(record) == 0 ) {
		            		try { 
				    			throw new Exception(); 
				        	} catch(Exception e) {       		
				        		this.rollbackTransaction("tx_rbm");
				        		Util.setSvcMsg(ctx, sMsg);
				        		
				        		return;
				        	}
		            	}
			        	continue;
		            }
		            		
			        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) {

				    	sEscStrDt    = (String)record.getAttribute("ESC_STR_DT");
				    	sEscEndDt    = (String)record.getAttribute("ESC_END_DT");
				    	sOrgEscStrDt = (String)record.getAttribute("ORG_ESC_STR_DT");
				    	sOrgEscEndDt = (String)record.getAttribute("ORG_ESC_END_DT");
				    	
				    	
				    	if( sEscStrDt.equals(sOrgEscStrDt) && sEscEndDt.equals(sOrgEscEndDt) ) {
				    		//�������ڿ� �������ڰ� ������ ���� ���
				    		nSaveCount += updateEscEmp(record);
				    	} else {
				    		//�������ڿ� �������ڰ� ������ ���
				    		deleteEscEmp(record);
				    		
				    		if( getEscDate(record) > 0 ) {
				    			try { 
					    			throw new Exception(); 
					        	} catch(Exception e) {       		
					        		this.rollbackTransaction("tx_rbm");
					        		Util.setSvcMsg(ctx, "�����Ͻ� �Ⱓ ���� ���ԵǴ� �Ⱓ�� �̹� �����մϴ�.");
					        		
					        		return;
					        	}
				    		} else {
				    			nSaveCount += insertEscEmp(record);
				    		}
				    	}
				    	
				    	if( getReleaCnt(record) == 0 ) {
		            		try { 
				    			throw new Exception(); 
				        	} catch(Exception e) {       		
				        		this.rollbackTransaction("tx_rbm");
				        		Util.setSvcMsg(ctx, sMsg);
				        		
				        		return;
				        	}
		            	}
			        }
			        
		            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE) {	
		            	nDeleteCount += deleteEscEmp(record);
		            	continue;
		            }		        
		        }	        
	        }
	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        Util.setDeleteCount(ctx, nDeleteCount   );
	    }
	    
	    /**
	     * <p> �߸����ܱⰣ �Է� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int insertEscEmp(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));	//�򰡳⵵
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));	//������
	        param.setValueParamter(i++, record.getAttribute("TELLER_ID"));  //�߸ſ���ȣ
	        param.setValueParamter(i++, record.getAttribute("EMP_NO"));	    //���
	        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	//�����ڵ�
	        
	        param.setValueParamter(i++, record.getAttribute("ESC_STR_DT"));	//���ܽ�������
	        param.setValueParamter(i++, record.getAttribute("ESC_END_DT"));	//������������
	        param.setValueParamter(i++, record.getAttribute("ESC_CD"));		//���ܱ���
	        param.setValueParamter(i++, record.getAttribute("ESC_RSN"));	//���ܻ���
	        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(�ۼ���)
	        
	        int dmlcount = this.getDao("rbmdao").update("rev3200_i01", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �߸����ܱⰣ ���� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int updateEscEmp(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, record.getAttribute("ESC_STR_DT"));	//���ܽ�������
	        param.setValueParamter(i++, record.getAttribute("ESC_END_DT"));	//������������
	        param.setValueParamter(i++, record.getAttribute("ESC_CD"));		//���ܱ���
	        param.setValueParamter(i++, record.getAttribute("ESC_RSN"));	//���ܻ���
	        param.setValueParamter(i++, SESSION_USER_ID);					//�����ID(������)
	        
	        i = 0;
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));	    //�򰡳⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));	    //������
	        param.setWhereClauseParameter(i++, record.getAttribute("TELLER_ID"));       //�߸ſ���ȣ
	        param.setWhereClauseParameter(i++, record.getAttribute("EMP_NO"));	        //���
	        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));      	//�����ڵ�
	        param.setWhereClauseParameter(i++, record.getAttribute("ORG_ESC_STR_DT"));	//���ܽ�������

	        int dmlcount = this.getDao("rbmdao").update("rev3200_u01", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �߸����ܱⰣ ���� </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    protected int deleteEscEmp(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();   
	        
	        int i = 0;
	        
	        param.setValueParamter(i++, record.getAttribute("ESTM_YEAR"));	    //�򰡳⵵
	        param.setValueParamter(i++, record.getAttribute("ESTM_NUM"));	    //������
	        param.setValueParamter(i++, record.getAttribute("TELLER_ID"));      //�߸ſ���ȣ
	        param.setValueParamter(i++, record.getAttribute("EMP_NO"));	        //���
	        param.setValueParamter(i++, record.getAttribute("BRNC_CD"));	    //�����ڵ�
	        param.setValueParamter(i++, record.getAttribute("ORG_ESC_STR_DT"));	//���ܽ�������
	        
	        int dmlcount = this.getDao("rbmdao").update("rev3200_d01", param);
	        
	        return dmlcount;
	    }
	    
	    /**
	     * <p> �Է��� �Ⱓ ���� ��ġ�� �Ⱓ�� �����ϴ��� ��ȸ  </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    public int getEscDate(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));	//�򰡳⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));	//������
	        param.setWhereClauseParameter(i++, record.getAttribute("TELLER_ID"));   //�߸ſ���ȣ
	        param.setWhereClauseParameter(i++, record.getAttribute("EMP_NO"));	    //���
	        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD"));     //�����ڵ�
	        param.setWhereClauseParameter(i++, record.getAttribute("ESC_STR_DT"));	//���ܽ�������
	        param.setWhereClauseParameter(i++, record.getAttribute("ESC_END_DT"));	//������������
	        param.setWhereClauseParameter(i++, record.getAttribute("ESC_STR_DT")); 	//���ܽ�������
	        param.setWhereClauseParameter(i++, record.getAttribute("ESC_END_DT"));	//������������
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev3200_s03", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        String rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

	        return Integer.parseInt(rtnKey);
	    }
	    
	    /**
	     * <p> ���ܱⰣ�� ������ ���Ŀ� �߸Ž��� �Ǽ�(����) ��ȸ  </p>
	     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
	     * @return  dmlcount	int		update ���ڵ� ����
	     * @throws  none
	     */
	    public int getReleaCnt(PosRecord record) 
	    {
	        PosParameter param = new PosParameter();
	        int i = 0;
	        
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));	//�򰡳⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));	//������
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));	//�򰡳⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));	//������
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));	//�򰡳⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));	//������
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_YEAR"));	//�򰡳⵵
	        param.setWhereClauseParameter(i++, record.getAttribute("ESTM_NUM"));	//������
	        param.setWhereClauseParameter(i++, record.getAttribute("TELLER_ID"));   //�߸ſ���ȣ
	        
	        PosRowSet keyRecord = this.getDao("rbmdao").find("rev3200_s04", param);        
	        	
	        PosRow pr[] = keyRecord.getAllRow();
	        
	        String rtnKey = String.valueOf(pr[0].getAttribute("CNT"));

	        return Integer.parseInt(rtnKey);
	    }
}
