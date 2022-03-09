/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02030030.activity.SaveArrangeBacAlloc.java
 * ���ϼ���		: �������⺻����Ƚ�����
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02030030.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �������⺻����Ƚ���� ����(�Է�/����)�ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SaveArrangeBacAlloc extends SnisActivity
{
	private String sStndYear = "";
	private String sQurtCd   = "";

    public SaveArrangeBacAlloc()
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
        int        nSize   = 0;
        String     sDsName = "";
        
        sDsName = "dsOutBacAlloc";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ )
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
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
    	int    nSaveCount   = 0; 
    	int    nDeleteCount = 0; 
        String sDsName      = "";

    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        // �������⺻����Ƚ������
        sStndYear = Util.nullToStr((String) ctx.get("STND_YEAR".trim()));
        sQurtCd   = Util.nullToStr((String) ctx.get("QURT_CD  ".trim()));
        
        sDsName = "dsOutBacAlloc";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
            // ����
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
            	if ( (nTempCnt = updateBacRaceAlloc(record)) == 0 ) {
                	nTempCnt = insertBacRaceAlloc(record);
                }

            	nSaveCount = nSaveCount + nTempCnt;
	        }
	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        return;
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> �������⺻����Ƚ�� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateBacRaceAlloc(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Integer.toString(Util.nullToInt(Util.trim(record.getAttribute("RACE_ALLOC_CNT".trim())), 0)));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sQurtCd  );
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_NO      ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_racer_bac_race_alloc_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> �������⺻����Ƚ�� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertBacRaceAlloc(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, sStndYear);
        param.setValueParamter(i++, sQurtCd  );
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO      ".trim())));
        param.setValueParamter(i++, Integer.toString(Util.nullToInt(Util.trim(record.getAttribute("RACE_ALLOC_CNT".trim())), 0)));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_racer_bac_race_alloc_ib001", param);
        return dmlcount;
    }
}
