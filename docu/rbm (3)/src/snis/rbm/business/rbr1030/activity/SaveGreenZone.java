package snis.rbm.business.rbr1030.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;


public class SaveGreenZone extends SnisActivity {
	public SaveGreenZone(){
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
        
        // â����ȣ���� ���� ���� ����        
        sDsName = "dsWinNoMapping";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            if(i==0) {
	            	deleteWinNoMapp(record);	
	            }
	            if (record.getType() != PosRecord.DELETE) {
	            	nSaveCount = nSaveCount + insertWinNoMapp(record);
	            }	
	        }	        
        }
        
     // �׸�ī�� �ܸ����ȣ���� ���� ���� ����        
        sDsName = "dsMycatNoMapping";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);
	            if(i==0) {
	            	deleteMycatNoMapp(record);	            
	            }
	            if (record.getType() != PosRecord.DELETE) {
	            	nSaveCount = nSaveCount + insertMycatNoMapp(record);
	            }
	        }	        
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    

    /**
     * <p> �߸ű� �������� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteWinNoMapp(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("COMM_NO"));		//�����ڵ�
        
        int dmlcount = this.getDao("rbmdao").update("rbr1030_d02", param);

        return dmlcount;
    }
    
    /**
     * <p> �׸�ī�� �ܸ����ȣ �������� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteMycatNoMapp(PosRecord record) 
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("COMM_NO"));		//�����ڵ�
        
        int dmlcount = this.getDao("rbmdao").update("rbr1030_d03", param);

        return dmlcount;
    }

    /**
     * <p> �߸ű� �������� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertWinNoMapp(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("COMM_NO"));	//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("WIN_NO"));		//â����ȣ
        param.setValueParamter(i++, record.getAttribute("GRN_ZN_CD"));	//��ұ���
        param.setValueParamter(i++, SESSION_USER_ID);					//�Է��� ���        
         
        int dmlcount = this.getDao("rbmdao").update("rbr1030_i02", param);
        
        return dmlcount;
    }
    
    /**
     * <p> �׸�ī�� �ܸ����ȣ �������� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertMycatNoMapp(PosRecord record) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, record.getAttribute("COMM_NO"));	//�����ڵ�
        param.setValueParamter(i++, record.getAttribute("CHNL_CD"));	//�ܸ�/�۱���
        param.setValueParamter(i++, record.getAttribute("DEV_NM"));		//�ܸ���ȣ
        param.setValueParamter(i++, record.getAttribute("GRN_ZN_CD"));	//��ұ���
        param.setValueParamter(i++, SESSION_USER_ID);					//�Է��� ���        

         
        int dmlcount = this.getDao("rbmdao").update("rbr1030_i03", param);
        
        return dmlcount;
    }
    
}
