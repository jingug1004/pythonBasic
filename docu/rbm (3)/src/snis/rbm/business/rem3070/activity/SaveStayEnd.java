package snis.rbm.business.rem3070.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;


import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveStayEnd extends SnisActivity {

	
	public SaveStayEnd(){
		
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

        String sChk		 = "";
        sDsName = "dsStayEndMana";
        
        //01 : ����, 02 :  ������� ���� 
        String sJobGbn 		= (String) ctx.get("JOB_GBN");
        if(sJobGbn == null )sJobGbn = "";
        
        String sEndStat  = (String) ctx.get("STAY_END_STAT_CD");
        if(sEndStat == null )sEndStat = "";
        
        
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);

		        if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE
		          || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT ) {

		        	if(sJobGbn.equals("01")){
		        		
		        		updateStayEnd(record);				//��� ����
		        	}else if(sJobGbn.equals("02")){
		        		
		        		sChk = (String) record.getAttribute("CHK");
		        		
		        		if(sChk.equals("1")){
		        			
		        			record.setAttribute("STAY_END_STAT_CD", sEndStat);
				        	nTempCnt = updateStayEndMana(record);
				        	
				        	if(nTempCnt>0){
					        	updateStayMana(record);		//���������� ���ڸ� ����
				        	}
		        		}
		        	}else if(sJobGbn.equals("03")){
		        		
		        		sChk = (String) record.getAttribute("CHK");
		        		
		        		if(sChk.equals("1")){
		        			
		        			record.setAttribute("STAY_END_STAT_CD", sEndStat);
				        	nTempCnt = updateStayEndMana(record);
		        		}
		        	}


			        nSaveCount = nSaveCount + nTempCnt;
		        	continue;
		        }    
	        }	        
        }

        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    
    
    /**
     * <p> �������  ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateStayEndMana(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;

        
        param.setValueParamter(i++, record.getAttribute("RMK"));						//���
        param.setValueParamter(i++, record.getAttribute("STAY_END_STAT_CD" ));			//��������
        param.setValueParamter(i++, SESSION_USER_ID);						//������
     

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACE_DT" ));		//��������
        param.setWhereClauseParameter(i++, record.getAttribute("MEET_CD" ));		//����
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD" ));		//�����ڵ�
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ" ));			//��û���� 
        

        int dmlcount = this.getDao("rbmdao").update("rem3070_u01", param);
        return dmlcount;
    }

    
    /**
     * <p> �������  ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateStayMana(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, SESSION_USER_ID);								//������
     

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACE_DT" ));		//��������
        param.setWhereClauseParameter(i++, record.getAttribute("MEET_CD" ));		//����
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD" ));		//�����ڵ�
        

        int dmlcount = this.getDao("rbmdao").update("rem3070_u02", param);
        return dmlcount;
    }
    
    
    /**
     * <p> �������� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateStayEnd(PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("RMK"));			//���
        param.setValueParamter(i++, SESSION_USER_ID);						//������
     

        i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("RACE_DT" ));		//��������
        param.setWhereClauseParameter(i++, record.getAttribute("MEET_CD" ));		//����
        param.setWhereClauseParameter(i++, record.getAttribute("BRNC_CD" ));		//�����ڵ�
        param.setWhereClauseParameter(i++, record.getAttribute("SEQ" ));			//��û���� 
        

        int dmlcount = this.getDao("rbmdao").update("rem3070_u03", param);
        return dmlcount;
    }

}
