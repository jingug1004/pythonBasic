/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02040040.activity.SearchRace.java
 * ���ϼ���		: ����ȸ
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02040040.activity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosColumnDef;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.dao.vo.PosRowSetImpl;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ���� ��ȸ�ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SearchRace extends SnisActivity
{    
	
    public SearchRace()
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
    	/* IWORK_SFR-008 ���� ������ �޴� ���� ���� */
    	
    	String organType = (String)ctx.get("ORGAN_TYPE");
    	
        // �������ȸ
        PosRowSet rowsetRace = null;
        if("NEW".equals(organType) || "TEMP".equals(organType)){
        	rowsetRace = searchTempOrgan(ctx);
        }else if("REAL".equals(organType)){
        	rowsetRace = searchOrgan(ctx);
        }
        
        String sResultKey = "dsOutRace";
        ctx.put(sResultKey, rowsetRace);
        Util.addResultKey(ctx, sResultKey);

        // �ش������� �ʿ��� �������� ��ȸ�Ѵ�.
        int racerTotalCnt = getRacerTotalCnt(ctx);

        if ( rowsetRace .getAllRow().length < racerTotalCnt ) {
        	
        	// ���ֹ�����ȸ
        	PosRowSet rowsetRacer = null;
            if("NEW".equals(organType) || "TEMP".equals(organType)){
            	rowsetRacer = searchTempOrganAlloc(ctx);
            }else if("REAL".equals(organType)){
            	rowsetRacer = searchOrganAlloc(ctx);
            }        	
	
	    	PosColumnDef[] columnDef = rowsetRacer.getColumnDefs();
	        PosRow rowRaces[] = rowsetRace .getAllRow();
	        PosRow rowRacer[] = rowsetRacer.getAllRow();
	        
	        
	        // ������ ���ܵ� ���� ����Ʈ ��ȸ

	    	List   racerList = new java.util.ArrayList();
	    	/*
	    	 * 2ȸ ������ �÷� ������ 2���� ����� �ش�.
	    	for ( int j = 0; j < rowRacer.length; j++ ) {
	    		int    nAllocCnt = ((BigDecimal) rowRacer[j].getAttribute("RACE_ALLOC_CNT")).intValue();
				for ( int k = 0; k < nAllocCnt; k++ ) {
					racerList.add(rowRacer[j]);
				}
	    	}
	    	*/
	    	for ( int j = 0; j < rowRacer.length; j++ ) {
				racerList.add(rowRacer[j]);
	    	}
	
	    	for ( int j = 0; j < rowRaces.length; j++ ) {
	    		String sRacerNo  = (String) rowRaces[j].getAttribute("RACER_NO");
	    		
	        	for ( int k = 0; k < racerList.size(); k++ ) {
	        		if ( sRacerNo.equals((String)((PosRow) racerList.get(k)).getAttribute("RACER_NO")) ) {
	        			racerList.remove(k);
	        			break;
	        		}
	        	}
	    	}
	
	    	//row����
	    	rowsetRacer = new PosRowSetImpl(racerList);
	    	rowsetRacer.setColumnDefs(columnDef);
	        
	        sResultKey = "dsOutRacer";
	        ctx.put(sResultKey, rowsetRacer);
	        Util.addResultKey(ctx, sResultKey);
        }
    }
    
    
    /**
     * <p> ��������ȸ </p>
     * @param ctx
     * @return
     */
    protected PosRowSet searchOrgan(PosContext ctx){
    	
    	PosParameter paramRace = new PosParameter();
        int i = 0;
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));

        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ""                  );

        return this.getDao("boadao").find("tbeb_organ_fb004", paramRace);
    }
    
    /**
     * <p> ����������ȸ </p>
     * @param ctx
     * @return
     */
    protected PosRowSet searchTempOrgan(PosContext ctx){
    	
    	// �������ȸ
    	PosParameter paramRace = new PosParameter();
        int i = 0;
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));

        
        // ���ϼ��� - ������ ���� ��� ���� ���ֹ�ȣ,����
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));
        
        // ������ ���ּ�
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));   
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        // �߰�
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));       
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));   
        
        paramRace.setWhereClauseParameter(i++, ""                  );
        
        return this.getDao("boadao").find("tbeb_temp_organ_fb001", paramRace);    	

    }  
    
    /**
     * <p> �� �ּ� ������ȸ </p>
     * @param ctx
     * @return
     */
    protected PosRowSet searchOrganAlloc(PosContext ctx){
    	
    	// �ּ����� ��ȸ
    	PosParameter paramRacer = new PosParameter();
        int i = 0;
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        return this.getDao("boadao").find("tbeb_racer_race_alloc_fb003", paramRacer);    	
    }
    
    /**
     * <p> ���� �ּ� ������ȸ </p>
     * @param ctx
     * @return
     */
    protected PosRowSet searchTempOrganAlloc(PosContext ctx){
    	
    	// �ּ����� ��ȸ
    	PosParameter paramRacer = new PosParameter();
        int i = 0;
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));        
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	
        // ���ϼ��� - ������ ���� ��� ���� ���ֹ�ȣ,����
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));    	
    	
        // ������ ���ּ�
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));

    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));           	
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	
        // �߰�
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	
    	// �߰�
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));       
    	
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	
        // �߰�
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));       
    	
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        return this.getDao("boadao").find("tbeb_racer_race_alloc_fb010", paramRacer);    	    	
    }    
    
    /**
     * <p> �ش������ �Ѽ����� ��ȸ ������ȸ </p>
     * @param ctx
     * @return
     */
    protected int getRacerTotalCnt(PosContext ctx){
    	
    	int    racerCnt = 0;
    	
        // ���� ��ȸ
    	PosParameter param = new PosParameter();
    	int i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        PosRowSet rowset = this.getDao("boadao").find("tbeb_race_fb002", param);
        PosRow raceInfos[] = rowset.getAllRow();     	

    	for ( int j = 0; j < raceInfos.length; j++ ) {
    		racerCnt += ((BigDecimal) raceInfos[j].getAttribute("RACE_REG_NO_CNT")).intValue();
    	}        
    	
    	return racerCnt;
    }
}

