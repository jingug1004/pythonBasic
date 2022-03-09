package org.sosfo.framework.util;

/**
 * ���ڿ� ��ƿ Ŭ����
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
	 * @param HTML ��Ʈ��
	 * @return UNICODE�� ��ȯ�� ��Ʈ��
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
	 * ���ڿ��� ���� ���� ����
	 * 
	 * @param ��������
	 * @return String �������ŵ� ���ڿ�
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
	 * ���ڿ��� null���̳� ����("")���ڿ� �ϰ�� default ���ڿ��� ��ü�Ѵ�.
	 * 
	 * @param value - ���� ���ڿ�
	 * @param defaultValue - default ���ڿ�
	 * @return String
	 */
	public static String evl(String value, String defaultValue) {
		return (value == null || value.equals("")) ? defaultValue : value;
	}

	/**
	 * ���ڿ��� null���̳� Ʈ�� �� ����("")���ڿ� �ϰ�� default ���ڿ��� ��ü�Ѵ�.
	 * 
	 * @param value - ���� ���ڿ�
	 * @param defaultValue - default ���ڿ�
	 * @return String
	 */
	public static String evlTrim(String value, String defaultValue) {
		return (value == null) ? defaultValue : ((value.trim().equals("")) ? defaultValue : value.trim());
	}

	/**
	 * ��Ʈ������ Ư�����ڸ� HTML���¿� �°� ��ȯ���� �ش�.
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
	 * ���ڿ��� ������(int)���� ��ȯ�Ͽ� ��ȯ�Ѵ�. ��, ���������� ������ �� ���� ���ڿ��� ���� ����Ʈ ������ ��ȯ.
	 * 
	 * @param value - ���������� ��ȯ�� ���ڿ�
	 * @param defaultValue - ����Ʈ int��
	 * @return int - ���ڿ��� ���������� ��ȯ�� ��
	 */
	public static int parseInt(String value, int defaultValue) {
		try {
			return (value == null || value.equals("")) ? defaultValue : Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 * ���ڿ��� ������(int)���� ��ȯ�Ͽ� ��ȯ�Ѵ�. ��, ���������� ������ �� ���� ���ڿ��� ���� -1 ��ȯ.
	 * 
	 * @param value - ���������� ��ȯ�� ���ڿ�
	 * @return int - ���ڿ��� ���������� ��ȯ�� ��
	 */
	public static int parseInt(String value) {
		return parseInt(value, -1);
	}

	/**
	 * ���ڿ��� ���ϴ� �κ��� substring �Ͽ� ��ȯ
	 * 
	 * @param str - substring �� ���ڿ�
	 * @param beginIndex - ���� �ε���
	 * @param endIndex - �� �ε���
	 * @return String - substring �� ���ڿ�
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
	 * ���ڿ��� ���ϴ� �κ��� substring �Ͽ� ��ȯ
	 * 
	 * @param str - substring �� ���ڿ�
	 * @param beginIndex - ���� �ε���
	 * @return String - substring �� ���ڿ�
	 */
	public static String substring(String str, int beginIndex) {
		return substring(str, beginIndex, str.length());
	}
	/**
	 * ���ڿ� ����
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
