/*================================================================================
 * �ý���		: ����ǥ �˼����� ����
 * �ҽ����� �̸�: snis.rbm.business.rbr5030.activity.SaveOrganExamBrnc.java
 * ���ϼ���		: SaveOrganExamBrnc
 * �ۼ���		: ������
 * ����			: 1.0.0
 * ��������		: 2016-03-24
 * ������������	: 
 * ����������	: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbr5030.activity;

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

public class SaveOrganExamBrnc extends SnisActivity {
	
	public SaveOrganExamBrnc(){}
 
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
    	
    	PosDataset ds;
        
        sDsName = "dsOrganExamBrnc";
        if ( ctx.get(sDsName) != null ) {
	        ds   	   = (PosDataset) ctx.get(sDsName);
	        nSaveCount = saveOrganExamBrnc(ds);
        }

        Util.setSaveCount  (ctx, nSaveCount  );

    }
    
    /**
     * <p> ����ǥ ���ۼ��� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveOrganExamBrnc(PosDataset ds) 
    {
        int nSize 		= ds.getRecordCount();
        int intResult 	= 0;
        String[] arrQuery = new String[nSize];
        
        RbmJdbcDao rbmjdbcdao = getRbmDao("rbmjdbcdao");
        for ( int i = 0; i < nSize; i++ ) {
        	PosRecord record = ds.getRecord(i);
        	arrQuery[i] = " " +
        			"   UPDATE TBRA_ORGAN_EXAM   /* rbo5010_i01 ����ǥ �˼����� ����*/ \n"+
        			"	SET VERI_YN 	= '"+record.getAttribute("VERI_YN")+"'  \n"+
        			"	WHERE MEET_CD 	= '"+record.getAttribute("MEET_CD")+"'  \n" +
        			"	AND STND_YEAR 	= '"+record.getAttribute("STND_YEAR")+"'  \n" +
        			"	AND STND_MM 	= '"+record.getAttribute("STND_MM")+"'  \n" +
        			"	AND DIST_DT 	= '"+record.getAttribute("DIST_DT")+"'  \n" +
        			"	AND BRNC_CD 	= '"+record.getAttribute("BRNC_CD")+"'  \n" +
        			"	AND ORGAN_CD 	= '"+record.getAttribute("ORGAN_CD")+"'  \n";
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