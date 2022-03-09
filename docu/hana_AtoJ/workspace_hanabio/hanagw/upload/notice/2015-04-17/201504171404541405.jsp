<%@ page language="java" contentType="text/html;charset=euc-kr"%>
<%
    /* ============================================================================== */
    /* =   PAGE : ���� ��û ó�� PAGE                                               = */
    /* = -------------------------------------------------------------------------- = */
    /* =   ������ ������ �߻��ϴ� ��� �Ʒ��� �ּҷ� �����ϼż� Ȯ���Ͻñ� �ٶ��ϴ�.= */
    /* =   ���� �ּ� : http://kcp.co.kr/technique.requestcode.do			        = */
    /* = -------------------------------------------------------------------------- = */
    /* =   Copyright (c)  2013   KCP Inc.   All Rights Reserverd.                   = */
    /* ============================================================================== */
%>
<%!
    /* ============================================================================== */
    /* =   null ���� ó���ϴ� �޼ҵ�                                                = */
    /* = -------------------------------------------------------------------------- = */
        public String f_get_parm( String val )
        {
          if ( val == null ) val = "";
          return  val;
        }
    /* ============================================================================== */
%>
<%
    /* ============================================================================== */
    /* = ���̺귯�� �� ����Ʈ ���� include                                          = */
    /* = -------------------------------------------------------------------------- = */
%>
    <%@ page import="com.kcp.*"%>
    <%@ page import="java.net.URLEncoder"%>
    <%@ include file="../cfg/site_conf_inc.jsp"%>
<%
    /* ============================================================================== */
    request.setCharacterEncoding ( "euc-kr" ) ; 
    /* ============================================================================== */
    /* =   01. ���� ��û ���� ����                                                  = */
    /* = -------------------------------------------------------------------------- = */
    String    pay_method = f_get_parm( request.getParameter( "pay_method" ) ); // ���� ���
    String    ordr_idxx  = f_get_parm( request.getParameter( "ordr_idxx"  ) ); // �ֹ� ��ȣ
    String    good_name  = f_get_parm( request.getParameter( "good_name"  ) ); // ��ǰ ����
    String    good_mny   = f_get_parm( request.getParameter( "good_mny"   ) ); // ���� �ݾ�
    String    buyr_name  = f_get_parm( request.getParameter( "buyr_name"  ) ); // �ֹ��� �̸�
    String    buyr_mail  = f_get_parm( request.getParameter( "buyr_mail"  ) ); // �ֹ��� E-Mail
    String    buyr_tel1  = f_get_parm( request.getParameter( "buyr_tel1"  ) ); // �ֹ��� ��ȭ��ȣ
    String    buyr_tel2  = f_get_parm( request.getParameter( "buyr_tel2"  ) ); // �ֹ��� �޴�����ȣ
    String    req_tx     = f_get_parm( request.getParameter( "req_tx"     ) ); // ��û ����
    String    currency   = f_get_parm( request.getParameter( "currency"   ) ); // ȭ����� (WON/USD)
    String    quotaopt   = f_get_parm( request.getParameter( "quotaopt"   ) ); // �Һΰ����� �ɼ�
    /* = -------------------------------------------------------------------------- = */
	String    mod_type     = f_get_parm( request.getParameter( "mod_type"    ) ); // ����TYPE(������ҽ� �ʿ�)
    String    mod_desc     = f_get_parm( request.getParameter( "mod_desc"    ) ); // �������
    String    panc_mod_mny = "";                                                  // �κ���� �ݾ�
    String    panc_rem_mny = "";                                                  // �κ���� ���� �ݾ�
    String    mod_tax_mny  = f_get_parm( request.getParameter( "mod_tax_mny" ) ); // ���ް� �κ� ��� ��û �ݾ�
    String    mod_vat_mny  = f_get_parm( request.getParameter( "mod_vat_mny" ) ); // �ΰ��� �κ� ��� ��û �ݾ�
    String    mod_free_mny = f_get_parm( request.getParameter( "mod_free_mny") ); // ����� �κ� ��� ��û �ݾ�
    /* = -------------------------------------------------------------------------- = */
    String    tran_cd    = "";                                                 // Ʈ����� �ڵ�
    String    bSucc      = "";                                                 // DB �۾� ���� ����
    /* = -------------------------------------------------------------------------- = */
    String    res_cd     = "";                                                 // ����ڵ�
    String    res_msg    = "";                                                 // ����޽���
    String    tno        = "";                                                 // �ŷ���ȣ
    /* = -------------------------------------------------------------------------- = */
    String    amount          = f_get_parm( request.getParameter( "amount"          ) );  // ���� �ݾ�
    String    card_pay_method = f_get_parm( request.getParameter( "card_pay_method" ) );  // ī�� ���� ���
    String    card_cd         = "";                                                       // ī�� �ڵ�
    String    card_name       = "";                                                       // ī���
    String    app_time        = "";                                                       // ���νð�
    String    app_no          = "";                                                       // ���ι�ȣ
    String    noinf           = "";                                                       // �����ڿ���
    String    quota           = "";                                                       // �Һΰ���
    /* ============================================================================== */


    /* ============================================================================== */
    /* =   02. �ν��Ͻ� ���� �� �ʱ�ȭ                                              = */
    /* = -------------------------------------------------------------------------- = */
    C_PP_CLI  c_PayPlus = new C_PP_CLI();

	c_PayPlus.mf_init( g_conf_home_dir, g_conf_gw_url, g_conf_gw_port, g_conf_tx_mode );
    c_PayPlus.mf_init_set();
    /* ============================================================================== */


    /* ============================================================================== */
    /* =   03. ó�� ��û ���� ����, ����                                            = */
    /* = -------------------------------------------------------------------------- = */

    /* = -------------------------------------------------------------------------- = */
    /* =   03-1. ���� ��û                                                          = */
    /* = -------------------------------------------------------------------------- = */
        // ��ü ȯ�� ����
        String cust_ip = request.getRemoteAddr();

        if ( req_tx.equals( "pay" ) )
        {
            tran_cd = "00100000";

            int payx_data_set;
            int common_data_set;

            payx_data_set   = c_PayPlus.mf_add_set( "payx_data" );
            common_data_set = c_PayPlus.mf_add_set( "common"    );


            c_PayPlus.mf_set_us( common_data_set, "amount",   good_mny    );
            c_PayPlus.mf_set_us( common_data_set, "currency", currency    );

            c_PayPlus.mf_set_us( common_data_set, "cust_ip",  cust_ip     );
            c_PayPlus.mf_set_us( common_data_set, "escw_mod", "N"         );

            c_PayPlus.mf_add_rs( payx_data_set, common_data_set );

            // �ֹ� ����
            int ordr_data_set;

            ordr_data_set = c_PayPlus.mf_add_set( "ordr_data" );

            c_PayPlus.mf_set_us( ordr_data_set, "ordr_idxx", ordr_idxx );
            c_PayPlus.mf_set_us( ordr_data_set, "good_name", good_name );
            c_PayPlus.mf_set_us( ordr_data_set, "good_mny",  good_mny  );
            c_PayPlus.mf_set_us( ordr_data_set, "buyr_name", buyr_name );
            c_PayPlus.mf_set_us( ordr_data_set, "buyr_tel1", buyr_tel1 );
            c_PayPlus.mf_set_us( ordr_data_set, "buyr_tel2", buyr_tel2 );
            c_PayPlus.mf_set_us( ordr_data_set, "buyr_mail", buyr_mail );

			if (pay_method.equals("CARD") )
            {
                int card_data_set;

                card_data_set = c_PayPlus.mf_add_set( "card" );

                c_PayPlus.mf_set_us( card_data_set, "card_mny",     good_mny   );        // ���� �ݾ�             
                c_PayPlus.mf_set_us( card_data_set, "card_tx_type", "11111000" );
                c_PayPlus.mf_set_us( card_data_set, "quota",        request.getParameter( "quota"   ) );
                c_PayPlus.mf_set_us( card_data_set, "card_no",      request.getParameter( "card_no" ) );
                c_PayPlus.mf_set_us( card_data_set, "card_expiry",  request.getParameter( "expiry_yy" ) + request.getParameter( "expiry_mm" ) );               
                
				c_PayPlus.mf_add_rs( payx_data_set, card_data_set );
			}
        }

    /* = -------------------------------------------------------------------------- = */
    /* =   03-2. ���/���� ��û                                                     = */
    /* = -------------------------------------------------------------------------- = */
        else if ( req_tx.equals( "mod" ) )
        {
            int     mod_data_set_no;

            tran_cd = "00200000";
            mod_data_set_no = c_PayPlus.mf_add_set( "mod_data" );

            c_PayPlus.mf_set_us( mod_data_set_no, "tno",        request.getParameter( "tno"       ) );      // KCP ���ŷ� �ŷ���ȣ
            c_PayPlus.mf_set_us( mod_data_set_no, "mod_type",   mod_type                            );      // ���ŷ� ���� ��û ����
            c_PayPlus.mf_set_us( mod_data_set_no, "mod_ip",     cust_ip                             );      // ���� ��û�� IP
            c_PayPlus.mf_set_us( mod_data_set_no, "mod_desc",   request.getParameter( "mod_desc"  ) );      // ���� ����

            if ( mod_type.equals( "STPC" ) ) // �κ������ ���
            {
                c_PayPlus.mf_set_us( mod_data_set_no, "mod_mny", request.getParameter( "mod_mny" ) ); // ��ҿ�û�ݾ�
                c_PayPlus.mf_set_us( mod_data_set_no, "rem_mny", request.getParameter( "rem_mny" ) ); // ��Ұ����ܾ�

                //���հŷ� �κ� ��ҽ� �ּ��� Ǯ�� �ֽñ� �ٶ��ϴ�.
	            //c_PayPlus.mf_set_us( mod_data_set_no, "tax_flag",     "TG03"                       ); // ���հ��� ����
                //c_PayPlus.mf_set_us( mod_data_set_no, "mod_tax_mny",  mod_tax_mny                  ); // ���ް� �κ� ��� ��û �ݾ�
                //c_PayPlus.mf_set_us( mod_data_set_no, "mod_vat_mny",  mod_vat_mny                  ); // �ΰ��� �κ� ��� ��û �ݾ�
                //c_PayPlus.mf_set_us( mod_data_set_no, "mod_free_mny", mod_free_mny                 ); // ����� �κ� ��� ��û �ݾ�
            }
        }
    /* ============================================================================== */


    /* ============================================================================== */
    /* =   03-3. ����                                                               = */
    /* ------------------------------------------------------------------------------ */
        if ( tran_cd.length() > 0 )
        {
            c_PayPlus.mf_do_tx( g_conf_site_cd, g_conf_site_key, tran_cd, "", ordr_idxx, "3", "0" );
        }
        else
        {
            c_PayPlus.m_res_cd  = "9562";
            c_PayPlus.m_res_msg = "���� ����";
        }
        res_cd  = c_PayPlus.m_res_cd;                      // ��� �ڵ�
        res_msg = c_PayPlus.m_res_msg;                     // ��� �޽���
    /* ============================================================================== */


    /* ============================================================================== */
    /* =   04. ���� ��� ó��                                                       = */
    /* = -------------------------------------------------------------------------- = */
        if ( req_tx.equals( "pay" ) )
        {
            if ( res_cd.equals( "0000" ) )
            {
                tno = c_PayPlus.mf_get_res( "tno" );       // KCP �ŷ� ���� ��ȣ

    /* = -------------------------------------------------------------------------- = */
    /* =   04-1. �ſ�ī�� ���� ��� ó��                                            = */
    /* = -------------------------------------------------------------------------- = */
                if ( pay_method.equals( "CARD" ) )
                {
                    card_cd   = c_PayPlus.mf_get_res( "card_cd"   );
                    card_name = c_PayPlus.mf_get_res( "card_name" );
                    app_time  = c_PayPlus.mf_get_res( "app_time"  );
                    app_no    = c_PayPlus.mf_get_res( "app_no"    );
                    noinf     = c_PayPlus.mf_get_res( "noinf"     );
                    quota     = c_PayPlus.mf_get_res( "quota"     );
                }

    /* = -------------------------------------------------------------------------- = */
    /* =   04-2. ���� ����� ��ü ��ü������ DB ó�� �۾��Ͻô� �κ��Դϴ�.         = */
    /* = -------------------------------------------------------------------------- = */
    /* =         ���� ����� DB �۾� �ϴ� �������� ���������� ���ε� �ǿ� ����      = */
    /* =         DB �۾��� �����Ͽ� DB update �� �Ϸ���� ���� ���, �ڵ�����       = */
    /* =         ���� ��� ��û�� �ϴ� ���μ����� �����Ǿ� �ֽ��ϴ�.                = */
    /* =         DB �۾��� ���� �� ���, bSucc ��� ����(String)�� ���� "false"     = */
    /* =         �� ������ �ֽñ� �ٶ��ϴ�. (DB �۾� ������ ��쿡�� "false" �̿��� = */
    /* =         ���� �����Ͻø� �˴ϴ�.)                                           = */
    /* = -------------------------------------------------------------------------- = */
                bSucc = "";             // DB �۾� ������ ��� "false" �� ����

    /* = -------------------------------------------------------------------------- = */
    /* =   04-3. DB �۾� ������ ��� �ڵ� ���� ���                                 = */
    /* = -------------------------------------------------------------------------- = */
                if ( bSucc.equals("false") )
                {
                    int mod_data_set_no;

                    c_PayPlus.mf_init_set();

                    tran_cd = "00200000";

                    mod_data_set_no = c_PayPlus.mf_add_set( "mod_data" );

                    c_PayPlus.mf_set_us( mod_data_set_no, "tno",      tno    );                         // KCP ���ŷ� �ŷ���ȣ
                    c_PayPlus.mf_set_us( mod_data_set_no, "mod_type", "STSC" );                         // ���ŷ� ���� ��û ����
                    c_PayPlus.mf_set_us( mod_data_set_no, "mod_ip",   cust_ip );                        // ���� ��û�� IP
                    c_PayPlus.mf_set_us( mod_data_set_no, "mod_desc", "������ ��� ó�� ���� - ���������� �ڵ� ��� ��û" );   // ���� ����
                    c_PayPlus.mf_do_tx( g_conf_site_cd, g_conf_site_key, tran_cd, cust_ip, ordr_idxx, "3", "0" );

                    res_cd  = c_PayPlus.m_res_cd;
                    res_msg = c_PayPlus.m_res_msg;
                }

            }    // End of [res_cd = "0000"]

    /* = -------------------------------------------------------------------------- = */
    /* =   04-4. ���� ���и� ��ü ��ü������ DB ó�� �۾��Ͻô� �κ��Դϴ�.         = */
    /* = -------------------------------------------------------------------------- = */
            else
            {
            }
        }
    /* ============================================================================== */


    /* ============================================================================== */
    /* =   05. ���/���� ��� ó��                                                  = */
    /* = -------------------------------------------------------------------------- = */
        else if ( req_tx.equals( "mod" ) )
        {
            if ( res_cd.equals( "0000" ) )
            {
                if ( mod_type.equals ( "STPC") )
                {
                    amount       = c_PayPlus.mf_get_res( "amount"       ); // �� �ݾ�
                    panc_mod_mny = c_PayPlus.mf_get_res( "panc_mod_mny" ); // �κ���� ��û�ݾ�
                    panc_rem_mny = c_PayPlus.mf_get_res( "panc_rem_mny" ); // �κ���� ���ɱݾ�
                }
            }
        }
    /* ============================================================================== */


    /* ============================================================================== */
    /* =   06. �� ���� �� ��������� ȣ��                                           = */
    /* ============================================================================== */
%>

<%
	String result = "";
	result += "{";
	result += "\"req_tx\":" + "\"" + req_tx + "\",";		// ��û ����
	result += "\"pay_method\":" + "\"" + pay_method + "\",";		// ����� ���� ����
	result += "\"bSucc\":" + "\"" + bSucc + "\",";		// ���θ� DB ó�� ���� ����
	result += "\"res_cd\":" + "\"" + res_cd + "\",";		// ��� �ڵ�
	result += "\"res_msg\":" + "\"" + res_msg + "\",";		// ��� �޼���
	result += "\"ordr_idxx\":" + "\"" + ordr_idxx + "\",";		// �ֹ���ȣ
	result += "\"tno\":" + "\"" + tno + "\",";		// KCP �ŷ���ȣ
	result += "\"good_mny\":" + "\"" + good_mny + "\",";		// �����ݾ�
	result += "\"good_name\":" + "\"" + good_name + "\",";		// ��ǰ��
	result += "\"buyr_name\":" + "\"" + buyr_name + "\",";		// �ֹ��ڸ�
	result += "\"buyr_tel1\":" + "\"" + buyr_tel1 + "\",";		// �ֹ��� ��ȭ��ȣ
	result += "\"buyr_tel2\":" + "\"" + buyr_tel2 + "\",";		// �ֹ��� �޴�����ȣ
	result += "\"buyr_mail\":" + "\"" + buyr_mail + "\",";		// �ֹ��� E-mail
	
	result += "\"card_cd\":" + "\"" + card_cd + "\",";		// ī���ڵ�
	result += "\"card_name\":" + "\"" + card_name + "\",";		// ī���
	result += "\"app_time\":" + "\"" + app_time + "\",";		// ���νð�
	result += "\"app_no\":" + "\"" + app_no + "\",";		// ���ι�ȣ
	result += "\"quota\":" + "\"" + quota + "\",";		// �Һΰ���
	result += "\"noinf\":" + "\"" + noinf + "\",";		// �����ڿ���
	
	result += "},";

String result1 = "";
result1 += res_cd + ",";		// 0.��� �ڵ�
result1 += req_tx + ",";		// 1.��û ����
result1 += pay_method + ",";	// 2.����� ���� ����
result1 += bSucc + ",";			// 3.���θ� DB ó�� ���� ����
result1 += res_msg + ",";		// 4.��� �޼���
result1 += ordr_idxx + ",";		// 5.�ֹ���ȣ
result1 += tno + ",";			// 6.KCP �ŷ���ȣ
result1 += good_mny + ",";		// 7.�����ݾ�
result1 += good_name + ",";		// 8.��ǰ��
result1 += buyr_name + ",";		// 9.�ֹ��ڸ�
result1 += buyr_tel1 + ",";		// 10.�ֹ��� ��ȭ��ȣ
result1 += buyr_tel2 + ",";		// 11.�ֹ��� �޴�����ȣ
result1 += buyr_mail + ",";		// 12.�ֹ��� E-mail
result1 += card_cd + ",";		// 13.ī���ڵ�
result1 += card_name + ",";		// 14.ī���
result1 += app_time + ",";		// 15.���νð�
result1 += app_no + ",";		// 16.���ι�ȣ
result1 += quota + ",";			// 17.�Һΰ���
result1 += noinf + ",";			// 18.�����ڿ���

	out.print(result1);
%>