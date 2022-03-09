package org.sosfo.framework.exception;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.log.Log;
import org.sosfo.framework.util.StringUtil;
import java.net.URLEncoder;
/**
 * message.properties�� ������ Exception ó��
 * @author NUCHA.
 * @version 1.2 reversioning ---> yunkidon@htomail.com
 */

public class AppException extends RuntimeException {

    /**
     * ������Ƽ �ν��Ͻ�
     */
    private static PropertiesConfiguration conf	    = null;

    /**
     * '�ý��ۿ���'�� �����ڵ�.
     */
    public static final String	     SYSTEM_ERR_CODE = "X0001";

    /**
     * '�ý��ۿ���'�� �����޽���.
     */
    protected static final String	  SYSTEM_ERR_MSG  = "�ý��� ������ �߻��Ͽ����ϴ�.";

    /**
     * �����ڵ�. workflow ������ ��쿡�� �ش� �����ڵ带 ������, system ������ ��쿡�� �⺻���� '�ý��ۿ���'�� �����ڵ带 ���´�.
     */
    private String			 errorCode       = SYSTEM_ERR_CODE;

    /**
     * ȭ�鿡 ��µ� �����޽���. log�� ��µ� �޽����� �����ϱ� ���� �ʿ�.
     */
    private String			 errorMessage    = SYSTEM_ERR_MSG;

    /**
     * �� ���ܸ� �߻���Ų ������ �Ǵ� ���ܰ�ü. (SQLException, NullPointerException, NumberFormatException... ��)
     */
    private Throwable		      cause	   = null;

    /**
     * ȭ�鿡 ��µ� �����󼼸޽���.
     * @param errorCode
     * @return
     */
    private String			 errorDetail     = null;

    /**
     * �ƹ��� ������ ���� ������. �ƹ��� �ΰ������� �־����� �����Ƿ� deprecate�Ǿ���.
     * @deprecated
     */
    public AppException() {
    }

    /**
     * use public AppException(String code, Throwable cause)
     * @param errorCode String
     * @deprecated
     */
    public AppException(String errorCode) {
	this(errorCode, new Throwable(SYSTEM_ERR_MSG));
    }

    /**
     * �����󼼸޽��� Ȥ�� �����ڵ带 �����Ͽ� AppException�� �����Ѵ�. ������ ������ �� Throwable ��ü�� �Բ� �����Ѵ�.
     * @param msg �����󼼸޽��� Ȥ�� �����ڵ�
     * @param cause ������ ������ �Ǵ� Throwable ��ü
     */
    public AppException(String errorCode, Throwable cause) {
	if ((errorCode == null) || ("".equals(errorCode.trim()))) {
	    this.errorCode = SYSTEM_ERR_CODE;
	    this.errorMessage = SYSTEM_ERR_MSG;
	} else {
	    this.errorCode = errorCode;
	    loadProperties(errorCode);
	}
	this.cause = cause;
    }

    /**
     * �����ڵ� �� ������ �޽����� �����Ͽ� AppException�� �����Ѵ�. ������ ������ �� Throwable ��ü�� �Բ� �����Ѵ�.
     * @param errorCode �����ڵ�
     * @param errorDetail �����󼼸޽���
     * @param Throwable ������ü
     */
    public AppException(String errorCode, String errorDetail, Throwable t) {
	this(errorCode, t);
	this.errorDetail = errorDetail;
    }

    /**
     * �־��� �����ڵ忡 �ش��ϴ� ���������� �����Ѵ�. �����ڵ忡 �ش��ϴ� ���ڵ尡 ���� ���, Ȥ�� ���������� �������� �������� ������ �߻��� ��� �ý��� ������ ó��.
     * @param errorCode �����ڵ�
     */
    private void loadProperties(String errorCode) {
	try {
	    conf = ConfigFactory.getInstance().getConfiguration("message.properties");
	    if (("".equals(conf.getString(errorCode))) || (conf.getString(errorCode) == null)) {
		this.errorMessage = errorCode;
	    } else {
		this.errorMessage = conf.getString(errorCode);
	    }
	} catch (Exception ex) {
	    Log.error("ERROR", this, "message.properties�� �о������ ���߽��ϴ�.");
	}
    }

    /**
     * printStackTrace ���
     */
    public void printStackTrace() {
	if (cause != null) {
	    Log.error("ERROR", this, "���� �߻� ����: " + getMessage());
	}
	super.printStackTrace();
    }

    /**
     * �� ���ܸ� �߻���Ų ������ �Ǵ� ���ܰ�ü�� �����ش�. (�ַ� try-catch �������� catch�� ���ܰ�ü)
     */
    public Throwable getCause() {
	return cause;
    }

    /**
     * �����޽��� ������Ƽ �����ڵ带 �����ش�.
     * @return �����ڵ�
     */
    public String getErrorCode() {
	return errorCode;
    }

    /**
     * �����޽��� ���̺���� �����޽����� �����ش�.
     * @return �����޽���
     */
    public String getErrorMessage() {
	return errorMessage;
    }

    /**
     * ���� ���α׷����� ������ �� �����޽����� �����ش�.
     * @return ������ �����޽���
     */
    public String getErrorDetail() {
	return errorDetail;
    }

    /**
     * �α����� ��¿����� ���̴� ���ڿ��� ����.
     */
    public String toString() {
	StringBuffer buf = new StringBuffer(100);
	buf.append("�����ڵ�=").append(errorCode).append(',');
	buf.append("�����޽���=").append(errorMessage).append(',');
	if (errorDetail != null) {
	    buf.append("������=").append(errorDetail).append(',');
	}
	if (cause != null) {
	    buf.append("��������=").append(cause.getMessage());
	}
	return buf.toString();
    }

    /**
     * ���������� ������ �� �ֵ��� HTTP parameter ������ ���ڿ��� ����.
     * @return HTML ������ �α�
     */
    public String toHTTPParameter() {
	StringBuffer buf = new StringBuffer(100);
	buf.append("errorCode=").append(errorCode).append('&');
	buf.append("errorMessage=").append(errorMessage).append('&');
	if (errorDetail != null) {
	    buf.append("errorDetail=").append(errorDetail).append('&');
	}
	if (cause != null) {
	    buf.append("cause=").append( StringUtil.convertHTML( cause.toString()) );
	}
	return buf.toString();
    }
}
