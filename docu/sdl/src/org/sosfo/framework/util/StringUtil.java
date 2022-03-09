package org.sosfo.framework.util;

/**
 * 문자열 유틸 클래스
 * 
 * @author yunkidon@sosfo.or.kr
 * @company www.sosfo.or.kr
 */
public final class StringUtil {

	private static final String	LT			= "&lt;";

	private static final String	GT			= "&gt;";

	private static final String	QUOT		= "&quot;";

	private static final String	AMP			= "&amp;";

	public static final String	HTML_SPACE	= "&nbsp;";

	public static final String	NULL		= "";

	public static final String	SPACE		= " ";

	public static final String	BR			= "<br>";

	/**
	 * Don't let anyone instantiate this class
	 */
	private StringUtil() {

	}

	/**
	 * @param HTML 스트링
	 * @return UNICODE로 변환된 스트링
	 */
	public static String convertHTML(String htmlString) {
		if (htmlString == null) { return htmlString; }
		StringBuffer tmp = new StringBuffer();
		int olen = htmlString.length();

		for (int i = 0; i < olen; i++) {
			if (htmlString.charAt(i) == '<') tmp.append(LT);
			else if (htmlString.charAt(i) == '>') tmp.append(GT);
			else if (htmlString.charAt(i) == '"') tmp.append(QUOT);
			else if (htmlString.charAt(i) == '&') tmp.append(AMP);
			else if (htmlString.charAt(i) == ' ') {
				if (i + 1 < olen && htmlString.charAt(i + 1) == ' ') tmp.append(HTML_SPACE);
				else tmp.append(htmlString.charAt(i));
			} else if (htmlString.charAt(i) == '\n') tmp.append(BR);
			else if (htmlString.charAt(i) == '\r') tmp.append(SPACE);
			else tmp.append(htmlString.charAt(i));
		}

		return tmp.toString();
	}

	/**
	 * 문자열중 공백 문자 제거
	 * 
	 * @param 원본문자
	 * @return String 공백제거된 문자열
	 */
	public static String trim(String s) {
		int st = 0;
		char[] val = s.toCharArray();
		int count = val.length;
		int len = count;

		while ((st < len) && ((val[st] <= ' ') || (val[st] == ' ')))
			st++;
		while ((st < len) && ((val[len - 1] <= ' ') || (val[len - 1] == ' ')))
			len--;

		return ((st > 0 || (len < count)) ? s.substring(st, len) : s);
	}

	/**
	 * 문자열의 null값이나 공백("")문자열 일경우 default 문자열로 대체한다.
	 * 
	 * @param value - 원본 문자열
	 * @param defaultValue - default 문자열
	 * @return String
	 */
	public static String evl(String value, String defaultValue) {
		return (value == null || value.equals("")) ? defaultValue : value;
	}

	/**
	 * 문자열의 null값이나 트림 후 공백("")문자열 일경우 default 문자열로 대체한다.
	 * 
	 * @param value - 원본 문자열
	 * @param defaultValue - default 문자열
	 * @return String
	 */
	public static String evlTrim(String value, String defaultValue) {
		return (value == null) ? defaultValue : ((value.trim().equals("")) ? defaultValue : value.trim());
	}

	/**
	 * 스트링내의 특수문자를 HTML형태에 맞게 변환시켜 준다.
	 */
	public static String toHtml(String s) {
		if (s == null) return null;

		StringBuffer buf = new StringBuffer();
		char[] c = s.toCharArray();
		int len = c.length;

		for (int i = 0; i < len; i++) {
			if (c[i] == '&') buf.append("&amp;");
			else if (c[i] == '<') buf.append("&lt;");
			else if (c[i] == '>') buf.append("&gt;");
			else if (c[i] == '"') buf.append("&quot;");
			else if (c[i] == '\\') buf.append("&#039;");
			else buf.append(c[i]);
		}
		// String resultStr = replace(buf.toString(),"\n","<br>");
		return buf.toString();
	}

	/**
	 * 문자열을 정수형(int)으로 변환하여 반환한다. 단, 정수형으로 변경할 수 없는 문자열의 경우는 디폴트 정수값 반환.
	 * 
	 * @param value - 정수형으로 변환할 문자열
	 * @param defaultValue - 디폴트 int값
	 * @return int - 문자열을 정수형으로 변환한 값
	 */
	public static int parseInt(String value, int defaultValue) {
		try {
			return (value == null || value.equals("")) ? defaultValue : Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 * 문자열을 정수형(int)으로 변환하여 반환한다. 단, 정수형으로 변경할 수 없는 문자열의 경우는 -1 반환.
	 * 
	 * @param value - 정수형으로 변환할 문자열
	 * @return int - 문자열을 정수형으로 변환한 값
	 */
	public static int parseInt(String value) {
		return parseInt(value, -1);
	}

	/**
	 * 문자열을 원하는 부분을 substring 하여 반환
	 * 
	 * @param str - substring 할 문자열
	 * @param beginIndex - 시작 인덱스
	 * @param endIndex - 끝 인덱스
	 * @return String - substring 된 문자열
	 */
	public static String substring(String str, int beginIndex, int endIndex) {
		String strRtn = "";
		try {
			strRtn = str.substring(beginIndex, endIndex);
		} catch (IndexOutOfBoundsException e) {
			return str;
		}
		return strRtn;
	}

	/**
	 * 문자열을 원하는 부분을 substring 하여 반환
	 * 
	 * @param str - substring 할 문자열
	 * @param beginIndex - 시작 인덱스
	 * @return String - substring 된 문자열
	 */
	public static String substring(String str, int beginIndex) {
		return substring(str, beginIndex, str.length());
	}
	/**
	 * 문자열 변경
	 * 
	 * @param str String
	 * @param n1 String
	 * @param n2 String
	 * @return String
	 */
	public static String rplc(String str, String n1, String n2) {
		int itmp = 0;
		if (str == null) { return ""; }

		String tmp = str;
		StringBuffer sb = new StringBuffer();
		sb.append("");
		while (tmp.indexOf(n1) > -1) {
			itmp = tmp.indexOf(n1);
			sb.append(tmp.substring(0, itmp));
			sb.append(n2);
			tmp = tmp.substring(itmp + n1.length());
		}
		sb.append(tmp);
		return sb.toString();
	}
}// end class
