/*================================================================================
 * �ý���			: ���� ����
 * �ҽ����� �̸�	: snis.rbm.business.rbr1010.activity.SaveLastYear.java
 * ���ϼ���		: ���⵵��  ���� ������ ���� �⵵�� ����
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-09-21
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rbr1010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveLastYear extends SnisActivity {
	
	public SaveLastYear(){}

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
    	int nSaveCount = 0;
    	int nSaveChk   = 0;
    	
    	String sFlag = "N";	//�޼����� ����ڿ��� ����� ������ ���ϴ� Flag
    	String sMsg  = "";

        String sBrncCd   = (String)ctx.get("BRNC_CD");
        String sLastYear = (String)ctx.get("LAST_YEAR");
        String sStndYear = (String)ctx.get("STND_YEAR");
           
        if ( sBrncCd != null && sLastYear != null && sStndYear != null) {
        	
        	//�Ϲݻ��� �߰�
        	nSaveChk = insertCommDesc(sBrncCd, sLastYear, sStndYear);	
        	if( nSaveChk == 0) {
        		sMsg  = "[ �Ϲݻ��� ]";
        		sFlag = "Y";
        	}
        	nSaveCount += nSaveChk;
        	
        	//������ �߰�
        	nSaveChk = insertFloorMana(sBrncCd, sLastYear, sStndYear);	
        	if( nSaveChk == 0) {
        		sMsg += "[ ������ ]";
        		sFlag = "Y";
        	}
        	nSaveCount += nSaveChk;
        	
        	//�뿪���� �߰�
        	nSaveChk = insertServMana(sBrncCd, sLastYear, sStndYear);	
        	if( nSaveChk == 0) {
        		sMsg += "[ �뿪���� ]";
        		sFlag = "Y";
        	}
        	nSaveCount += nSaveChk;
        	
        	//���׼��� �߰� 
        	nSaveChk = insertEquipFaci(sBrncCd, sLastYear, sStndYear);	
        	if( nSaveChk == 0) {
        		sMsg += "[ ���׼������ ]";
        		sFlag = "Y";
        	}
        	nSaveCount += nSaveChk;
        	
        	//�ü���Ȳ �߰�
        	nSaveChk = insertFacStat(sBrncCd, sLastYear, sStndYear);
        	if( nSaveChk == 0)	{
        		sMsg += "[ �ü���Ȳ ]";
        		sFlag = "Y";
        	}
        	nSaveCount += nSaveChk;
        	
        	if( sFlag.equals("Y") ) {
        	    Util.setSvcMsg(ctx, sMsg + "�� ���⵵ �ڷᰡ ���� ������ �߰����� �ʾҽ��ϴ�.");
        	}
        }
  
        Util.setSaveCount  (ctx, nSaveCount  );
    }
    
    /**
     * <p> ���⵵�� �Ϲݻ����� ���� �⵵�� ���� </p>
     * @param   sBrncCd		�����ڵ�
     * 			sLastYear	�۳�⵵
     * 			sStndYear	����⵵
     * @return  dmlcount	int		insert ����
     * @throws  none
     */
    protected int insertCommDesc(String sBrncCd, String sLastYear, String sStndYear) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        	        		        
        param.setValueParamter(i++, SESSION_USER_ID);  	// ����� ID
        param.setValueParamter(i++, sBrncCd);         	// �����ڵ�
        param.setValueParamter(i++, sLastYear);         // �۳�⵵
        param.setValueParamter(i++, sStndYear);         // ����⵵
        
        int dmlcount = this.getDao("rbmdao").update("rbr1010_i01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> ���⵵�� �������� ���� �⵵�� ���� </p>
     * @param   sBrncCd		�����ڵ�
     * 			sLastYear	�۳�⵵
     * 			sStndYear	����⵵
     * @return  dmlcount	int		insert ����
     * @throws  none
     */
    protected int insertFloorMana(String sBrncCd, String sLastYear, String sStndYear) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        	        		        
        param.setValueParamter(i++, SESSION_USER_ID);  	// ����� ID
        param.setValueParamter(i++, sBrncCd);         	// �����ڵ�
        param.setValueParamter(i++, sLastYear);         // �۳�⵵
        param.setValueParamter(i++, sStndYear);         // ����⵵
        
        int dmlcount = this.getDao("rbmdao").update("rbr1010_i02", param);
        
        return dmlcount;
    }
    
    /**
     * <p> ���⵵�� �뿪������ ���� �⵵�� ���� </p>
     * @param   sBrncCd		�����ڵ�
     * 			sLastYear	�۳�⵵
     * 			sStndYear	����⵵
     * @return  dmlcount	int		insert ����
     * @throws  none
     */
    protected int insertServMana(String sBrncCd, String sLastYear, String sStndYear) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        	        		        
        param.setValueParamter(i++, SESSION_USER_ID);  	// ����� ID
        param.setValueParamter(i++, sBrncCd);         	// �����ڵ�
        param.setValueParamter(i++, sLastYear);         // �۳�⵵
        param.setValueParamter(i++, sStndYear);         // ����⵵
        
        int dmlcount = this.getDao("rbmdao").update("rbr1010_i03", param);
        
        return dmlcount;
    }
    
    
    /**
     * <p> ���⵵�� ���׼��� ���� �⵵�� ���� </p>
     * @param   sBrncCd		�����ڵ�
     * 			sLastYear	�۳�⵵
     * 			sStndYear	����⵵
     * @return  dmlcount	int		insert ����
     * @throws  none
     */
    protected int insertEquipFaci(String sBrncCd, String sLastYear, String sStndYear) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        	        		        
        param.setValueParamter(i++, SESSION_USER_ID);  	// ����� ID
        param.setValueParamter(i++, sBrncCd);         	// �����ڵ�
        param.setValueParamter(i++, sLastYear);         // �۳�⵵
        param.setValueParamter(i++, sStndYear);         // ����⵵
        
        int dmlcount = this.getDao("rbmdao").update("rbr1010_i04", param);
        
        return dmlcount;
    }
    
    /**
     * <p> ���⵵�� �ü���Ȳ�� ���� �⵵�� ���� </p>
     * @param   sBrncCd		�����ڵ�
     * 			sLastYear	�۳�⵵
     * 			sStndYear	����⵵
     * @return  dmlcount	int		insert ����
     * @throws  none
     */
    protected int insertFacStat(String sBrncCd, String sLastYear, String sStndYear) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        	        		        
        param.setValueParamter(i++, SESSION_USER_ID);  	// ����� ID
        param.setValueParamter(i++, sBrncCd);         	// �����ڵ�
        param.setValueParamter(i++, sLastYear);         // �۳�⵵
        param.setValueParamter(i++, sStndYear);         // ����⵵
        
        int dmlcount = this.getDao("rbmdao").update("rbr1010_i05", param);
        
        return dmlcount;
    }
}
