/*================================================================================
 * �ý���			: ���� ���� 
 * �ҽ����� �̸�	: snis.boa.system.e01010120.activity.SaveSdlFile.java
 * ���ϼ���		: SDL ���Ͼ��ε�
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2008-04-10
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.system.e01010120.activity;


import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosRecord;


import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
import org.motorboat.ToteRead;

/**
* SDL�������� ���ε�
* @auther �ѿ��� 
* @version 1.0
*/
public class SaveSdlFile extends SnisActivity
{    
    public SaveSdlFile()
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

    	System.out.println("[STND_YEAR  : " + ctx.get("STND_YEAR"));
    	System.out.println("[MBR_CD     : " + ctx.get("MBR_CD"));
    	System.out.println("[TMS        : " + ctx.get("TMS"));
    	System.out.println("[DAY_ORD    : " + ctx.get("DAY_ORD"));
    	System.out.println("[RACE_NO    : " + ctx.get("RACE_NO"));
    	System.out.println("[SDL_FILE   : " + ctx.get("SDL_FILE"));
    	
    	System.out.println("[RACE_ALL   : " + ctx.get("RACE_ALL"));
    	System.out.println("[DT         : " + ctx.get("DT"));
    	System.out.println("[FN         : " + ctx.get("FN"));
    	System.out.println("[PB         : " + ctx.get("PB"));
    	System.out.println("[PT         : " + ctx.get("PT"));
    	System.out.println("[RS         : " + ctx.get("RS"));
    	System.out.println("[TM         : " + ctx.get("TM"));
    	System.out.println("[TR         : " + ctx.get("TR"));

    	
    	boolean bChkAll = false;
		if ( ctx.get("RACE_ALL") != null && ((String) ctx.get("RACE_ALL")).equals("1") ) {
			bChkAll = true;
		}
    	
    	boolean[] abCheck = new boolean[7];
    	for ( int i = 0; i < abCheck.length; i++ ) {
    		abCheck[i] = false;
    	}
    	
    	String[] asCheck = new String[7];
    	int j = 0;
    	asCheck[j++] = "DT";
    	asCheck[j++] = "FN";
    	asCheck[j++] = "PB";
    	asCheck[j++] = "PT";
    	asCheck[j++] = "RS";
    	asCheck[j++] = "TM";
    	asCheck[j++] = "TR";

    	for( int i = 0; i < asCheck.length; i++ ) {
    		if ( ctx.get(asCheck[i]) != null && ((String) ctx.get(asCheck[i])).equals("1") ) {
    			abCheck[i] = true;
    		}
    	}
    	
    	ToteRead toteRead = new ToteRead();

//    	toteRead.applyHomeDirectory("C:\\SNIS\\JEUS5.0\\webhome\\yu_container1\\boa\\WEB-INF");
    	toteRead.applyHomeDirectory("/WAS/webApp/boa/WEB-INF");
    	toteRead.applyFileDataWithOption((String) ctx.get("MBR_CD")
    			                       , (String) ctx.get("STND_YEAR")
		    			               , Integer.parseInt((String) ctx.get("TMS"))
		    			               , Integer.parseInt((String) ctx.get("DAY_ORD"))
		    			               , (String) ctx.get("SDL_FILE")
		    			               , bChkAll
		    			               , Integer.parseInt((String) ctx.get("RACE_NO"))
		    			               , abCheck[0]
		    			               , abCheck[1]
		    			               , abCheck[2]
		    			               , abCheck[3]
		    			               , abCheck[4]
		    			               , abCheck[5]
		    			               , abCheck[6]
    	);
    	
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> �½ĺ� ���� ��� ����Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveDivRate(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = updateDivRate(record);
    	if(effectedRowCnt<1){
    		effectedRowCnt =insertDivRate(record);
    	}
        return effectedRowCnt;
    }

    /**
     * <p> �½ĺ� ���� ��� ����  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateDivRate(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("ODDS"));
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, record.getAttribute("MEET_CD"));
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("POOL_CD"));
        param.setValueParamter(i++, record.getAttribute("RUNNER_1ST"));
        param.setValueParamter(i++, record.getAttribute("RUNNER_2ND"));
        param.setValueParamter(i++, record.getAttribute("RUNNER_3RD"));
        
        return this.getDao("boadao").update("tbes_sdl_pb_ue001", param);
    }

    /**
     * <p>�½ĺ� ���� ���� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertDivRate(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("MEET_CD")); 
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));
        param.setValueParamter(i++, record.getAttribute("TMS"));
        param.setValueParamter(i++, record.getAttribute("DAY_ORD"));
        param.setValueParamter(i++, record.getAttribute("RACE_NO"));
        param.setValueParamter(i++, record.getAttribute("POOL_CD"));
        param.setValueParamter(i++, record.getAttribute("RUNNER_1ST"));
        param.setValueParamter(i++, record.getAttribute("RUNNER_2ND"));
        param.setValueParamter(i++, record.getAttribute("RUNNER_3RD"));
        param.setValueParamter(i++, record.getAttribute("ODDS"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").update("tbes_sdl_pb_ie001", param);
    }

}
