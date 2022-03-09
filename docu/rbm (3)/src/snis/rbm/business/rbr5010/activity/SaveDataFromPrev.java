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

public class SaveDataFromPrev extends SnisActivity {
	
	public SaveDataFromPrev(){}
 
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
    	
    	String sMeetCd   = (String) ctx.get("MEET_CD");
    	String sStndYear   = (String) ctx.get("STND_YEAR");
    	String sStndMm   = (String) ctx.get("STND_MM");
       
    	nDeleteCount = deleteOrganExam(sMeetCd, sStndYear, sStndMm);
    	nSaveCount = saveDataFromPrev(sMeetCd, sStndYear, sStndMm);

    	Util.setSaveCount  (ctx, nSaveCount  );
    	Util.setSaveCount  (ctx, nDeleteCount  );

    }
    
    /**
     * <p> ����ǥ ���ۼ��� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteOrganExam(String sMeetCd, String sStndYear, String sStndMm) 
    {
    	
        PosParameter param = new PosParameter();
        int i = 0;
        
        param.setValueParamter(i++, sStndYear);			//���س⵵
        param.setValueParamter(i++, sStndMm);			//���ؿ�
        param.setValueParamter(i++, sMeetCd);			//���/���� ����

        int dmlcount = this.getDao("rbmdao").update("rbr5010_d02", param);
        
        return dmlcount;
    }
    
    
    /**
     * <p> ����ǥ ���ۼ��� �ű� �Է�(���� ������ Ȱ��)  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveDataFromPrev(String sMeetCd, String sStndYear, String sStndMm) 
    {
    	PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, SESSION_USER_ID);	//�����ID(�ۼ���)
        param.setValueParamter(i++, sStndMm);			//���ؿ�
        param.setValueParamter(i++, sStndMm);			//���ؿ�
        param.setValueParamter(i++, sStndYear);			//���س⵵    
        param.setValueParamter(i++, sStndMm);			//���ؿ�
        param.setValueParamter(i++, sStndYear);			//���س⵵
        param.setValueParamter(i++, sStndMm);			//���ؿ�
        param.setValueParamter(i++, sMeetCd);			//���/���� ����
      
        int savecount = 0;
        
        savecount = this.getDao("rbmdao").update("rbr5010_i01", param);						//���������
        
        return savecount;
       
    }
    
}