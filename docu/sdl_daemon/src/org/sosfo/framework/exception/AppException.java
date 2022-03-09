package org.sosfo.framework.exception;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.log.Log;
import org.sosfo.framework.util.StringUtil;
import java.net.URLEncoder;
/**
 * message.properties를 연동한 Exception 처리
 * @author NUCHA.
 * @version 1.2 reversioning ---> yunkidon@htomail.com
 */

public class AppException extends RuntimeException {

    /**
     * 프로퍼티 인스턴스
     */
    private static PropertiesConfiguration conf	    = null;

    /**
     * '시스템에러'의 에러코드.
     */
    public static final String	     SYSTEM_ERR_CODE = "X0001";

    /**
     * '시스템에러'의 에러메시지.
     */
    protected static final String	  SYSTEM_ERR_MSG  = "시스템 에러가 발생하였습니다.";

    /**
     * 에러코드. workflow 에러의 경우에는 해당 에러코드를 가지며, system 에러의 경우에는 기본으로 '시스템에러'의 에러코드를 갖는다.
     */
    private String			 errorCode       = SYSTEM_ERR_CODE;

    /**
     * 화면에 출력될 에러메시지. log에 출력될 메시지와 구별하기 위해 필요.
     */
    private String			 errorMessage    = SYSTEM_ERR_MSG;

    /**
     * 이 예외를 발생시킨 원인이 되는 예외객체. (SQLException, NullPointerException, NumberFormatException... 등)
     */
    private Throwable		      cause	   = null;

    /**
     * 화면에 출력될 에러상세메시지.
     * @param errorCode
     * @return
     */
    private String			 errorDetail     = null;

    /**
     * 아무런 정보도 없는 생성자. 아무런 부가정보도 주어지지 않으므로 deprecate되었음.
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
     * 에러상세메시지 혹은 에러코드를 전달하여 AppException을 생성한다. 에러의 원인이 된 Throwable 객체를 함께 전달한다.
     * @param msg 에러상세메시지 혹은 에러코드
     * @param cause 에러의 원인이 되는 Throwable 객체
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
     * 에러코드 및 에러상세 메시지를 전달하여 AppException을 생성한다. 에러의 원인이 된 Throwable 객체를 함께 전달한다.
     * @param errorCode 에러코드
     * @param errorDetail 에러상세메시지
     * @param Throwable 에러객체
     */
    public AppException(String errorCode, String errorDetail, Throwable t) {
		this(errorCode, t);
		this.errorDetail = errorDetail;
    }

    /**
     * 주어진 에러코드에 해당하는 에러정보를 세팅한다. 에러코드에 해당하는 레코드가 없을 경우, 혹은 에러정보를 가져오는 과정에서 에러가 발생할 경우 시스템 에러로 처리.
     * @param errorCode 에러코드
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
		    Log.error("ERROR", this, "message.properties를 읽어들이지 못했습니다.");
		}
    }

    /**
     * printStackTrace 출력
     */
    public void printStackTrace() {
		if (cause != null) {
		    Log.error("ERROR", this, "예외 발생 이유: " + getMessage());
		}
		super.printStackTrace();
    }

    /**
     * 이 예외를 발생시킨 원인이 되는 예외객체를 돌려준다. (주로 try-catch 구문에서 catch된 예외객체)
     */
    public Throwable getCause() {
    	return cause;
    }

    /**
     * 에러메시지 프로퍼티 에러코드를 돌려준다.
     * @return 에러코드
     */
    public String getErrorCode() {
    	return errorCode;
    }

    /**
     * 에러메시지 테이블상의 에러메시지를 돌려준다.
     * @return 에러메시지
     */
    public String getErrorMessage() {
    	return errorMessage;
    }

    /**
     * 개별 프로그램에서 지정한 상세 에러메시지를 돌려준다.
     * @return 재정의 에러메시지
     */
    public String getErrorDetail() {
    	return errorDetail;
    }

    /**
     * 로그파일 출력용으로 쓰이는 문자열을 생성.
     */
    public String toString() {
		StringBuffer buf = new StringBuffer(100);
		buf.append("에러코드=").append(errorCode).append(',');
		buf.append("에러메시지=").append(errorMessage).append(',');
		if (errorDetail != null) {
		    buf.append("에러상세=").append(errorDetail).append(',');
		}
		if (cause != null) {
		    buf.append("에러원인=").append(cause.getMessage());
		}
		
		return buf.toString();
    }

    /**
     * 웹페이지로 전달할 수 있도록 HTTP parameter 형식의 문자열을 생성.
     * @return HTML 형식의 로그
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
