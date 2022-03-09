/*================================================================================
 * �ý���			: ���  ����
 * �ҽ����� �̸�	: snis.rbm.business.rbr4010.activity.SaveEvntMana.java
 * ���ϼ���		: ��� ����
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-09-07
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbr5010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.common.util.RbmJdbcDao;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveOrganExam extends SnisActivity {
	
	public SaveOrganExam(){}
 
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
        
        sDsName = "dsOrganExamDelete";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	        for ( int i = 0; i < nSize; i++ ) {
	        	PosRecord record = ds.getRecord(i);
	            nDeleteCount = nDeleteCount + deleteOrganExam(record);
	        }	
        }
        
        sDsName = "dsOrganExamSave";
        if ( ctx.get(sDsName) != null ) {
	        ds   	   = (PosDataset) ctx.get(sDsName);
	        nSaveCount = saveOrganExam(ds);
        }

        
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
    }
    
    /**
     * <p> ����ǥ ���ۼ��� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteOrganExam(PosRecord record) 
    {
    	
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));	//���س⵵
        param.setValueParamter(i++, record.getAttribute("STND_MM"));	//���ؿ�
        param.setValueParamter(i++, record.getAttribute("MEET_CD"));	//���
        param.setValueParamter(i++, record.getAttribute("ORGAN_CD"));   //����ǥ�ڵ�

        int dmlcount = this.getDao("rbmdao").update("rbr5010_d01", param);
        
        return dmlcount;
    }
    

    /**
     * <p> ����ǥ ���ۼ��� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveOrganExam(PosDataset ds) 
    {
    	
        int nSize 		= ds.getRecordCount();
        int intResult 	= 0;
        String[] arrQuery = new String[nSize];
        
        RbmJdbcDao rbmjdbcdao = getRbmDao("rbmjdbcdao");
        for ( int i = 0; i < nSize; i++ ) {
        	PosRecord record = ds.getRecord(i);
        	arrQuery[i] = " " +
        			"      INSERT INTO TBRA_ORGAN_EXAM   /* rbo5010_i01 ����ǥ ���ۼ��� �Է�*/ \n"+
        			"      ( 								\n"+
        			"              STND_YEAR -- ���س⵵ 	\n"+
        			"            , STND_MM   -- ���ؿ� 		\n"+
        			"            , DIST_DT   -- �������� 	\n"+
        			"            , MEET_CD   -- ���		\n"+	      
        			"            , BRNC_CD   -- �����ڵ� 	\n"+
        			"            , ORGAN_CD  -- ����ǥ ���� \n"+                 
        			"            , DIST_CNT  -- �������� 	\n"+
        			"            , INST_ID   -- �ۼ���ID 	\n"+
        			"            , INST_DT   -- �ۼ����� 	\n"+
        			"      ) 								\n"+
        			"      VALUES							\n"+
        			"      (								\n"+
        			"      	  '"+record.getAttribute("STND_YEAR")+"'\n"+
        			"       , '"+record.getAttribute("STND_MM")	+"'\n"+
        			"      	, '"+record.getAttribute("DIST_DT")	+"'\n"+
					"      	, '"+record.getAttribute("MEET_CD")	+"'\n"+
					"      	, '"+record.getAttribute("BRNC_CD")	+"'\n"+
					"      	, '"+record.getAttribute("ORGAN_CD")	+"'\n"+
					"      	, '"+record.getAttribute("DIST_CNT")	+"'\n"+
					"       , '"+SESSION_USER_ID					+"'\n"+
			    	"       , SYSDATE							  \n"+
			    	"      ) ";

        }
        int[] insertCounts = rbmjdbcdao.executeBatch(arrQuery);
		int failure_count = 0;

		for (int i = 0; i < insertCounts.length; i++) {
			if (insertCounts[i] == -3) {
				failure_count++;
			}
		}
		
		if (failure_count == 0) {
			intResult = ds.getRecordCount();
		} else {
			intResult = 0;
		}
        
		return intResult;
       
    }
    
}