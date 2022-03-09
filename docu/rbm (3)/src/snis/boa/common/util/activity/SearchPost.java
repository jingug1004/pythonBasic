package snis.boa.common.util.activity;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

/**
* �ּҸ� �˻��Ѵ�. 
* @auther 
* @version 1.0
*/
public class SearchPost  extends SnisActivity
{    
    public SearchPost()
    {
    }

    /**
     * <p> Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
    	searchPostDoro(ctx);
    	return PosBizControlConstants.SUCCESS;
    }

    /**
     * <p> ���θ� �ּҸ� ��ȸ </p>
     * @param   ctx		PosContext	�����
     * @return  none
     * @throws  none
     */
    private void searchPostDoro(PosContext ctx)
    {
    	StringBuffer sbQuery = new StringBuffer();
    	PosParameter param = new PosParameter();
    	int i = 0;
    	
    	String tableName = (String)ctx.get("AREA".trim());
    	
    	sbQuery.append("\n SELECT  	SUBSTR(TP.POSTCODE, 0 ,3)||'-'||SUBSTR(TP.POSTCODE, 4 ,3) ZIPCODE,  				");
    	sbQuery.append("\n 			TP.SIDO,  																			");
    	sbQuery.append("\n 			TP.SIGUNGU GUGUN,  																	");
    	sbQuery.append("\n 			TP.DORONAME DONG,  																	");
    	sbQuery.append("\n 			TP.BUILDINGMAIN || DECODE( TP.BUILDINGSUB, 0, '', '-' || TP.BUILDINGSUB ) BUNJI, 	");
    	sbQuery.append("\n 			TP.SIDO || ' ' || TP.SIGUNGU || ' ' || TP.DORONAME || ' ' || TP.BUILDINGMAIN   		");
    	sbQuery.append("\n          	|| DECODE( TP.BUILDINGSUB, 0, '', '-' || TP.BUILDINGSUB ||' ('||TP.DONGNAME||')' )  AS ADDR  			");
    	sbQuery.append("\n FROM    	KRACEWEB." + tableName 	+ " TP														");
    	sbQuery.append("\n WHERE   	TP.DORONAME LIKE ? || '%'  															");
    	
    	param.setWhereClauseParameter(i++, ctx.get("DONG".trim()));
    	
    	PosRowSet rowset = this.getDao("boadao").findByQueryStatement(sbQuery.toString(), param);
    	String       sResultKey = "dsOutDoro";
        ctx.put(sResultKey, rowset);
        
        Util.addResultKey(ctx, sResultKey);
        Util.setSearchCount(ctx, rowset.getAllRow().length);
        
    }
    
}
