/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.racer.e02040055.activity.SearchRacerVerify.java
 * ���ϼ���		: ����ǥ������ȸ
 * �ۼ���			: �迵ö
 * ����			: 1.0.0
 * ��������		: 2007-12-21
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02040055.activity;

import java.util.ArrayList;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosColumnDef;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.dao.vo.PosRowSetImpl;
/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ����ǥ������ȸ�ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SearchRacerVerify extends SnisActivity
{    
	
    public SearchRacerVerify()
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
    
    	// ��� ����Ʈ ��ȸ
        PosRowSet rowsetPerid = this.getDao("boadao").find("tbeb_racer_perio_no_reg_term_fb003");    
        
    	/* IWORK_SFR-008 ���� ������ �޴� ���� ���� */
    	String organType = (String)ctx.get("ORGAN_TYPE");
    	
    	// ����ǥ���� ��ȸ
        PosRowSet rowset = null;
        PosRowSet rowsetPeridCnt = null;       
        
        if("TEMP".equals(organType)){
        	
            // ����ǥ���� ��ȸ
        	PosParameter param = new PosParameter();
            
            int i = 0;
            // �⵵�� ������
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
            param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
            param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            
            // ȸ���� ������
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
            param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));
            
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
            param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
            param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));            
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));

            rowset = this.getDao("boadao").find("tbeb_temp_organ_fb003", param);    

            // ����� ����Ƚ�� ��ȸ
        	PosParameter paramPeridCnt = new PosParameter();
            
            i = 0;
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));

            rowsetPeridCnt = this.getDao("boadao").find("tbeb_temp_organ_fb004", paramPeridCnt); 
            
        }else if("REAL".equals(organType)){
            // ����ǥ���� ��ȸ
        	PosParameter param = new PosParameter();
            
            int i = 0;
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            
            param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            param.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));

            rowset = this.getDao("boadao").find("tbeb_organ_fb010", param);    

            // ����� ����Ƚ�� ��ȸ
        	PosParameter paramPeridCnt = new PosParameter();
            
            i = 0;
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("TMS"      ));
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));
            paramPeridCnt.setWhereClauseParameter(i++, ctx.get("ST_MTHD_CD"));

            rowsetPeridCnt = this.getDao("boadao").find("tbeb_organ_fb011", paramPeridCnt); 
        }

        PosRow peridCnts[] = rowsetPeridCnt.getAllRow();

        // column����
        PosRow rowPerid[] = rowsetPerid.getAllRow();
        PosColumnDef columnDefTemp[] = rowset.getColumnDefs();
        
        PosColumnDef columnDef[] = new PosColumnDef[columnDefTemp.length + rowPerid.length];
        
        for ( int j = 0; j < columnDefTemp.length; j++ ) {
        	columnDef[j] = columnDefTemp[j];
        }
        
        for ( int j = columnDefTemp.length, k = 0; j < columnDefTemp.length + rowPerid.length; j++ ) {
        	columnDef[j] = new PosColumnDef("SR" + rowPerid[k].getAttribute("RACER_PERIO_NO"), 12, 2);
        	k++;
        }

        // ����� ����Ƚ�� �Է�
        PosRow datarows[] = rowset.getAllRow();
        boolean isSet = false;
	    for ( int j = 0; j < datarows.length; j++ ) {
	    	for ( int k = 0; k < rowPerid.length; k++ ) {
	    		isSet = false;
	    		for(int m = 0; m < peridCnts.length; m++){
	    			if(datarows[j].getAttribute("RACE_NO").equals(peridCnts[m].getAttribute("RACE_NO")) 
	    					&& k+1 == Integer.parseInt((String)peridCnts[m].getAttribute("RACER_PERIO_NO"))){
	    				datarows[j].setAttribute("SR" + rowPerid[k].getAttribute("RACER_PERIO_NO"), peridCnts[m].getAttribute("CNT"));
	    				isSet = true;
	    				break;
	    			}
	    		}
	    		if(!isSet) datarows[j].setAttribute("SR" + rowPerid[k].getAttribute("RACER_PERIO_NO"), "0");
	    	}
	    }

        // Dataset ����
		ArrayList alRows  = new ArrayList();
        for ( int j = 0; j < datarows.length; j++ ) {
        	alRows.add(datarows[j]);
        }
        
		if ( alRows.size() > 0 ) rowset = new PosRowSetImpl(alRows);

		rowset.setColumnDefs(columnDef);

        String sResultKey = "dsOutRaceVerify";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
        
        
        // �� ���������� ��ȸ
        if("TEMP".equals(organType)){
        	searchTempOrganLastUpdate(ctx);
        }else if("REAL".equals(organType)){
        	searchOrganLastUpdate(ctx);
        }

    }
    
    /**
     * <p> ���� ���� ������������ȸ </p>
     * @param ctx
     */
    protected void searchTempOrganLastUpdate(PosContext ctx) 
    {
    	int i =0;
    	PosParameter param = new PosParameter();
    	
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));
        
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));
        
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));
        
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));

        PosRowSet rowset = this.getDao("boadao").find("tbeb_temp_organ_fb005", param);

    	String sResultKey = "dsOutLastUpdate";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }  
    
    /**
     * <p> �� ���� ������������ȸ </p>
     * @param ctx
     */    
    protected void searchOrganLastUpdate(PosContext ctx) 
    {
    	int i =0;
    	PosParameter param = new PosParameter();
    	
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));

        PosRowSet rowset = this.getDao("boadao").find("tbeb_organ_fb027", param);

    	String sResultKey = "dsOutLastUpdate";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }      
}
