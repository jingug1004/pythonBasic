/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.racer.e02040042.activity.SearchRacerRelation.java
 * ���ϼ���		: ����������ȸ
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-12-21
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02040042.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ����������ȸ�ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SearchRacerRelation extends SnisActivity
{    
	
    public SearchRacerRelation()
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
    	
        searchState(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> ��ȸ���� </p>
    * @param   ctx		PosContext	�����
    * @return  none
    * @throws  none
    */
    protected void searchState(PosContext ctx) 
    {   	
    	// ����������ȸ
        PosParameter param = new PosParameter();
        int i = 0;
        
        /* IWORK_SFR-008 ���� ������ �޴� ���� ���� */  
        
        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, "1"                  );
        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_1"));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"));
        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_1"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_2"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_3"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_4"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_5"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_6"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_7"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_8")); 
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"));
        
        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_1"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_2"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_3"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_4"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_5"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_6"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_7"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_8")); 
        param.setWhereClauseParameter(i++, "1"                  );
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR" ));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"    ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"       ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_1"));

        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, "2"                  );
        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_2"));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"));        
        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_2"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_1"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_3"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_4"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_5"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_6"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_7"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_8")); 
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"));
        
        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_2"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_1"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_3"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_4"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_5"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_6"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_7"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_8")); 
        param.setWhereClauseParameter(i++, "2"                  );
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR" ));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"    ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"       ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_2"));

        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, "3"                  );
        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_3"));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"));
        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_3"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_2"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_1"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_4"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_5"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_6"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_7"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_8")); 
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"));
        
        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_3"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_2"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_1"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_4"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_5"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_6"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_7"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_8")); 
        param.setWhereClauseParameter(i++, "3"                  );
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR" ));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"    ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"       ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_3"));

        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, "4"                  );
        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_4"));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"));
        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_4"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_2"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_3"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_1"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_5"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_6"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_7"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_8")); 
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"));
        
        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_4"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_2"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_3"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_1"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_5"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_6"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_7"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_8")); 
        param.setWhereClauseParameter(i++, "4"                  );
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR" ));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"    ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"       ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_4"));

        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, "5"                  );
        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_5"));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"));
        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_5"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_2"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_3"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_4"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_1"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_6"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_7"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_8")); 
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"));        
        
        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_5"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_2"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_3"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_4"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_1"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_6"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_7"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_8")); 
        param.setWhereClauseParameter(i++, "5"                  );
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR" ));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"    ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"       ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_5"));

        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, "6"                  );
        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_6"));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"));
        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_6"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_2"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_3"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_4"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_5"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_1"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_7"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_8")); 
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"));
        
        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_6"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_2"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_3"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_4"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_5"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_1"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_7"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_8")); 
        param.setWhereClauseParameter(i++, "6"                  );
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR" ));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"    ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"       ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_6"));

        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, "7"                  );
        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_7"));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"));
        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_7"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_2"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_3"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_4"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_5"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_1"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_6"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_8")); 
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"));
        
        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_7"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_2"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_3"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_4"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_5"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_1"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_6"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_8")); 
        param.setWhereClauseParameter(i++, "7"                  );
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR" ));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"    ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"       ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_7"));

        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, "8"                  );
        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_8"));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"));
        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_8"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_2"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_3"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_4"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_5"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_1"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_6"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_7")); 
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"));
        
        param.setWhereClauseParameter(i++, ctx.get("RACE_NO"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_8"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_2"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_3"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_4"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_5"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_1"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_7"));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_6")); 
        param.setWhereClauseParameter(i++, "8"                  );
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR" ));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"    ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"       ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"   ));
        param.setWhereClauseParameter(i++, ctx.get("RACER_NO_8"));        

        PosRowSet rowset = this.getDao("boadao").find("tbec_racer_relation_fb002", param);
        
        String sResultKey = "dsOutRacerRelation";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }
}
