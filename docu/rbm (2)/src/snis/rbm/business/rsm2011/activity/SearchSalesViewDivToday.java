package snis.rbm.business.rsm2011.activity;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
public class SearchSalesViewDivToday extends SnisActivity{
	public String runActivity(PosContext ctx) {
		String sRet = PosBizControlConstants.SUCCESS;
		try {

			searchCompare(ctx);	// ������ �˻�
			
		} catch (Exception e) {
			logger.logError(e.getMessage());
		}

		return sRet;
	}
	
	/**
     * ������ ����� �� ��ȸ
     * 
     */
	private void searchCompare(PosContext ctx) throws Exception {
				
		String MEET_CD  = (String) ctx.get("MEET_CD"); 			// ���:001 ,����:003, â������:002, �λ걳��:004
		String RACE_DAY = (String) ctx.get("RACE_DAY"); 		// ������
		String COMM_NO  = (String) ctx.get("COMM_NO"); 			// ���� �ڵ�
		String DIV_NO	= (String) ctx.get("DIV_NO"); 			// �׸� ī�� �� ��
		String BRNCH_NM = (String) ctx.get("BRNCH_NM"); 		// ���� �̸�
		String SELL_CD  = (String) ctx.get("SELL_CD"); 			// �Ǹ�ó�ڵ�
		String TUE_MBR_YN  = (String) ctx.get("TUE_MBR_YN"); 			// ȭ����� ����
				
		PosRowSet rowset;
		String TMS = "";
		String DAY_ORD = "";
		
		rowset = getRaceInfo(RACE_DAY);
		
		if (rowset.hasNext()) {
			
			PosRow row = rowset.next();

			TMS     = String.valueOf(Integer.parseInt(row.getAttribute("TMS").toString()));
			DAY_ORD = String.valueOf(Integer.parseInt(row.getAttribute("DAY_ORD").toString()));
			
		} else {			
			// �ش������� ������ ������ ���� ���				
			throw new RuntimeException("�ش����ڿ� ���� ���������� �����ϴ�.���� ������ �۾��ϼ���");
		}
				
		
		PosRowSet prs = null;
		
		PosParameter param = new PosParameter();
		int nIdx = 0;
		
		
		StringBuffer sbQuery = new StringBuffer();
		
		String sBrnchNm = "";
		if("00".equals(COMM_NO))
		{
			sBrnchNm = "��ü";
		}		
		if("01".equals(COMM_NO)) // ���� ����  
		{	
			sBrnchNm = "������";
		}
		else if("03".equals(COMM_NO)) // �̻縮
		{
			sBrnchNm = "�̻縮";
		}
		else
		{
			sBrnchNm = BRNCH_NM;
		}
		
		param.setWhereClauseParameter(nIdx++, TMS);		// ȸ��
		param.setWhereClauseParameter(nIdx++, DAY_ORD);	// ����
		param.setWhereClauseParameter(nIdx++, RACE_DAY);// ������
		
		sbQuery.append(Util.getQuery(ctx, "rsm2011_select07Today"));
		
		
		if("06".equals(COMM_NO)) // �׸�ī��  
		{		
			sbQuery.append(Util.getQuery(ctx, "rsm2011_from07_02Today"));		// �����ڷ��� ��� �׸�ī��DB���� ���� ��ȸ��
		} else {
			sbQuery.append(Util.getQuery(ctx, "rsm2011_from07_01Today"));
		}
		
		sbQuery.append("\n");	
		if("00".equals(COMM_NO))	// ��ü�� ���
		{	
			sbQuery.append("\n");
		}		
		else if("03".equals(COMM_NO)) // �̻縮 ����
		{
			param.setWhereClauseParameter(nIdx++, "003");			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, "003");			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, "003");			// ������� ���� �ڵ�
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where02"));
			
		}		
		else if("01".equals(COMM_NO)) // ���� ����  
		{		
			param.setWhereClauseParameter(nIdx++, "001");			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, "001");			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, "001");			// ������� ���� �ڵ�
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where01"));
		}
		else if("06".equals(COMM_NO)) // �׸�ī��  
		{		
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// ������� ���� �ڵ�
			if(!"".equals(DIV_NO)) {
				if (!"00".equals(DIV_NO)) {
					SELL_CD = "01";		// �Ǹ�ó�ڵ�
				}
			} else {
				SELL_CD = "%";			// �Ǹ�ó�ڵ�:�׸�ī�� ��ü�� ��� ��ü sell_cd�� �ڷḦ �����;� �� 
			}
			param.setWhereClauseParameter(nIdx++, SELL_CD);		// �Ǹ�ó�ڵ�					
			param.setWhereClauseParameter(nIdx++, COMM_NO);			// ���� �ڵ�
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where03"));	// �׸�ī��
			sbQuery.append("\n");			
			
			if(!"".equals(DIV_NO))
			{
				param.setWhereClauseParameter(nIdx++, DIV_NO);			// ���� �ڵ�
				sbQuery.append(Util.getQuery(ctx, "rsm2011_where06"));	// �׸�ī��
			}
		}
		else if("99".equals(COMM_NO)) // �������Ȱ��  
		{		
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where08"));	// 
		}
		else   
		{		
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, "01");			// �Ǹ�ó�ڵ�
			param.setWhereClauseParameter(nIdx++, COMM_NO);			// ���� �ڵ�
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where03"));	// ������, ����
		}
		
		sbQuery.append("\n");
		sbQuery.append(Util.getQuery(ctx, "rsm2011_groupby07"));
		
		sbQuery.append("\n");
		param.setWhereClauseParameter(nIdx++, RACE_DAY);	// ������
		param.setWhereClauseParameter(nIdx++, RACE_DAY);	// ������
		param.setWhereClauseParameter(nIdx++, TUE_MBR_YN);	// ȭ����� ����
		param.setWhereClauseParameter(nIdx++, RACE_DAY);	// ������
		param.setWhereClauseParameter(nIdx++, TUE_MBR_YN);	// ȭ����� ����
		param.setWhereClauseParameter(nIdx++, RACE_DAY);	// ������
		sbQuery.append(Util.getQuery(ctx, "rsm2011_select08Today"));
		
		sbQuery.append("\n");	
		if("00".equals(COMM_NO))	// ��ü�� ���
		{	
			sbQuery.append("\n");
		}		
		else if("03".equals(COMM_NO)) // �̻縮 ����
		{
			param.setWhereClauseParameter(nIdx++, "003");			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, "003");			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, "003");			// ������� ���� �ڵ�
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where02"));
			
		}		
		else if("01".equals(COMM_NO)) // ���� ����  
		{		
			param.setWhereClauseParameter(nIdx++, "001");			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, "001");			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, "001");			// ������� ���� �ڵ�
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where01"));
		}
		else if("06".equals(COMM_NO)) // �׸�ī��  
		{		
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// ������� ���� �ڵ�
			if(!"".equals(DIV_NO)) {
				if (!"00".equals(DIV_NO)) {
					SELL_CD = "01";		// �Ǹ�ó�ڵ�
				}
			} else {
				SELL_CD = "%";			// �Ǹ�ó�ڵ�:�׸�ī�� ��ü�� ��� ��ü sell_cd�� �ڷḦ �����;� �� 
			}
			param.setWhereClauseParameter(nIdx++, SELL_CD);		// �Ǹ�ó�ڵ�					
			param.setWhereClauseParameter(nIdx++, COMM_NO);			// ���� �ڵ�
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where03"));	// �׸�ī��
			sbQuery.append("\n");			
			
			if(!"".equals(DIV_NO))
			{
				param.setWhereClauseParameter(nIdx++, DIV_NO);			// ���� �ڵ�
				sbQuery.append(Util.getQuery(ctx, "rsm2011_where06"));	// �׸�ī��
			}
		}
		else if("99".equals(COMM_NO)) // �������Ȱ��  
		{		
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where08"));	// 
		}
		else   
		{		
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, MEET_CD);			// ������� ���� �ڵ�
			param.setWhereClauseParameter(nIdx++, "01");			// �Ǹ�ó�ڵ�
			param.setWhereClauseParameter(nIdx++, COMM_NO);			// ���� �ڵ�
			sbQuery.append(Util.getQuery(ctx, "rsm2011_where03"));	// ������, ����
		}
		
		sbQuery.append("\n");
		sbQuery.append(Util.getQuery(ctx, "rsm2011_groupby08"));
		        
    	prs = this.getDao("rbmdao").findByQueryStatement(sbQuery.toString(), param);
		String sOutDs = "dsSalesViewDiv";
    	ctx.put(sOutDs, prs);
    	Util.addResultKey(ctx, sOutDs);    
	}
	

	/**
	 * <p>
	 * ���������� ��ȸ
	 * </p>
	 * 
	 * @param record
	 *            PosRecord ����Ÿ�¿� ���� �ϳ��� ���ڵ�
	 * @return dmlcount int update ���ڵ� ����
	 * @throws none
	 */
	protected PosRowSet getRaceInfo(String race_day) {
		PosParameter param = new PosParameter();
		int i = 0;
		param.setWhereClauseParameter(i++, race_day);

		return this.getDao("rbmdao").find("rsm2010_s01", param);
	}
	
	
}
