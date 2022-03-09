/**
 * ���ϸ�: TubisUtil.java �ۼ���: 2004. 2. 23. �ۼ���: �����(hkjin@daou.co.kr)
 */
package org.sosfo.framework.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.regex.Pattern;

import org.sosfo.framework.sql.QueryRunner;
import org.sosfo.framework.tray.Tray;
import org.sosfo.framework.tray.TrayUtil;

/**
 * �پ��� static method�� ���� �ִ� ��ƿ��Ƽ Ŭ����. ������ ���� ����� �����Ѵ�.
 * <ul>
 * <li>����/�Ǽ��� �Է¹޾� ���ڿ��� ��ȯ(�ĸ� ���� �� ��ȿ�ڸ��� ����)</li>
 * <li>���� ��¥/�ð��� return</li>
 * <li>���ڿ� ��ȣ��ȯ</li>
 * <li>byte �迭�� 16���� ���ڿ��� ��ȣ��ȯ</li>
 * <li>javascript���� ���ڿ� ������ �ʵ��� '\' ÷��</li>
 * </ul>
 * @author �����
 * @version 2004-04-10
 * @version 2004-02-23
 */
public class CRAUtil {
    /**
     * ������ ���� �⺻ ����: 3�ڸ����� ',' ����. (������ 2^31-1�� �ִ밪�̹Ƿ� 10�ڸ��� ���� �� ����.)
     */
    private static final DecimalFormat DEFAULT_INTEGER_FORMAT = new DecimalFormat("#,###,###,###");

    /**
     * �Ǽ��� ���� �⺻ ����: 3�ڸ����� ',' �����ϰ� �Ҽ������ϴ� 2�ڸ��� �ݿø�.
     */
    private static final DecimalFormat DEFAULT_DOUBLE_FORMAT  = new DecimalFormat("####,###,###,###,###,###,###,###,###,###.##");

    /**
     * �Ҽ��� ���ϰ� ���� �Ǽ� ����
     */
    public static final DecimalFormat  NO_FRACTION_FORMAT     = new DecimalFormat("############################");

    /**
     * 3�ڸ����� ',' �������� �ʴ� �Ǽ� ����.
     */
    public static final DecimalFormat  NO_COMMA_DOUBLE_FORMAT = new DecimalFormat("############################.##########");

    /**
     * �ν��Ͻ��� ������ �� ������ �⺻�����ڸ� private�� ����.
     */
    private CRAUtil() {
    }

    /**
     * �־��� ���˴�� �Ǽ����� �����Ͽ� ���ڿ��� ��ȯ�Ѵ�. ���������� <code>java.text.DecimalFormat</code> �� API�� ����.
     * @param src ��ȯ�ϰ����ϴ� �Ǽ���
     * @param format ��ȯ�Ϸ��� ����
     * @return ��ȯ�� ���ڿ�
     */
    public static String format(double src, String format) {
	try {
	    return new DecimalFormat(format).format(src);
	} catch (Exception ex) {
	    return "";
	}
    }

    /**
     * �־��� ���˴�� �Ǽ����� �����Ͽ� ���ڿ��� ��ȯ�Ѵ�.
     * @param src ��ȯ�ϰ����ϴ� �Ǽ���
     * @param format ��ȯ�Ϸ��� ����
     * @return ��ȯ�� ���ڿ�
     */
    public static String format(double src, DecimalFormat format) {
	try {
	    return format.format(src);
	} catch (Exception ex) {
	    return "";
	}
    }

    /**
     * �⺻���˴�� �������� �����Ͽ� ���ڿ��� ��ȯ�Ѵ�. ���������� <code>java.text.DecimalFormat</code> �� API�� ����.
     * @param src ��ȯ�ϰ����ϴ� ������
     * @return ��ȯ�� ���ڿ�
     */
    public static String format(int src) {
	return DEFAULT_INTEGER_FORMAT.format(src);
    }
    
    /**
     * �⺻���˴�� �������� �����Ͽ� ���ڿ��� ��ȯ�Ѵ�. ���������� <code>java.text.DecimalFormat</code> �� API�� ����.
     * @param src ��ȯ�ϰ����ϴ� ������
     * @return ��ȯ�� ���ڿ�
     */
    public static String format(long src) {
	return DEFAULT_INTEGER_FORMAT.format(src);
    }

    /**
     * �⺻���˴�� �Ǽ����� �����Ͽ� ���ڿ��� ��ȯ�Ѵ�. ���������� <code>java.text.DecimalFormat</code> �� API�� ����.
     * @param src ��ȯ�ϰ����ϴ� �Ǽ���
     * @return ��ȯ�� ���ڿ�
     */
    public static String format(double src) {
	return DEFAULT_DOUBLE_FORMAT.format(src);
    }

    /**
     * {@link formatNumber} method���� ���������� ����ϴ� regex pattern. �� ���� ���� 1~2�ڸ����� match�ȴ�.
     */
    private static final Pattern REPLACE_1ST_DIGITS   = Pattern.compile("^([+-]?\\d{1,2})((\\d{3})+)(?=$|\\.)");

    /**
     * {@link formatNumber} method���� ���������� ����ϴ� regex pattern. ���ڸ� ���ڸ��� match�ȴ�.
     */
    private static final Pattern REPLACE_OTHER_DIGITS = Pattern.compile("(\\d{3})(?=\\d)");

    /**
     * ���ڿ�(�ַ� BigDecimal.toString()) ���·� �� 10�������� �޾� ���ڸ����� ','�� �����Ѵ�. (���������� �����Ұ���. 3�ڸ����� ',' ���Ը� ������) double �����ε� �����ϰ� ǥ���� �� ���� ��ȿ���ڸ� ���� ���� ���� ���
     * @param src
     * @return
     */
    public static String formatNumber(String src) {
	String tmp = REPLACE_1ST_DIGITS.matcher(src).replaceFirst("$1,$2");
	return REPLACE_OTHER_DIGITS.matcher(tmp).replaceAll("$1,");
    }

    /**
     * @param src
     * @return
     */
    public String unformatNumber(String src) {
	return src.replaceAll(",", "");
    }

    /**
     * ���ڿ�(�ַ� BigDecimal.toString()) ���·� �� 10�������� �޾� ���ڸ����� ','�� �����ϰ�, �Ҽ��� ���ϴ� digitBelowZero �ڸ����� �ݿø��Ѵ�. ��, �Ҽ��� ���� �ݿø��� �� 50�ڸ� �̸�(double ���� �ִ� ��ȿ�ڸ���)�� �����Ѵ�.
     * @param src
     * @param digitFraction
     * @return
     */
    public static String formatNumber(String src, int digitFraction) {
	int idx;
	if ((idx = src.indexOf('.')) > 0) {
	    String fraction = src.substring(idx);
	    try {
		DecimalFormat df1 = new DecimalFormat("." + repeat('0', digitFraction));
		fraction = format(Double.parseDouble(fraction), df1);
		return formatNumber(src.substring(0, idx) + fraction);
	    } catch (Throwable t) {
		return src;
	    }
	}
	return formatNumber(src);
    }

    /**
     * �⺻���˴�� �Ǽ����� �����Ͽ� ���ڿ��� ��ȯ�Ѵ�. ������ ��� 0���� ó���Ѵ�. ���������� <code>java.text.DecimalFormat</code> �� API�� ����.
     * @param src ��ȯ�ϰ����ϴ� �Ǽ���
     * @return ��ȯ�� ���ڿ�
     */
    public static String formatPositive(double src) {
	if (src < 0) {
	    src = 0;
	}
	return DEFAULT_DOUBLE_FORMAT.format(src);
    }

    /**
     * ������ �ڸ����� �޾�, �ڸ����� �°� ������ �տ� '0'�� ���ٿ��ش�. key ���� ���� ������ �뵵�� ���ǹǷ�, 0���� ū �������� ���ؼ��� �����Ѵ�. ��) getZeroInsertedString(23, 5) -> "00023"
     * @param value
     * @param digit
     * @return
     */
    public static String getZeroInsertedString(int value, int digit) {
	if (value < 0) {
	    return "";
	}

	DecimalFormat f = new DecimalFormat(repeat('0', digit));
	return f.format(value);
    }

    /**
     * DB�� �����Ͽ� �����Ͻø� �޾ƿ��� {@link tubis.asi.x.sql.QueryRunner}
     */
    private static final QueryRunner currentTimeRunner = new QueryRunner("SELECT TO_CHAR(SYSDATE, 'yyyymmddHH24MISS') FROM DUAL");

    private static final DateFormat  currentTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    private static final DateFormat  todayFormat       = new SimpleDateFormat("yyyyMMdd");

    /**
     * ���� ��¥�� yyyyMMdd �������� �����ش�. �ϴ��� �ð��� DB������ ũ�� �ٸ��� �����Ƿ� WAS�� �ð��� �������� ����.
     * @return ���� ��¥
     */
    public static String getToday() {
	return todayFormat.format(new Date());
    }

    /**
     * ISO-8859-1 ���ڵ��� ���ڿ��� �⺻ ���ڵ��� ���ڿ��� ��ȯ�Ѵ�. (UNIX�� ��� ȯ�溯�� LANG�� ko, Ȥ�� ko_KR�̶�� �⺻ ���ڵ��� "EUC-KR"�� �ȴ�)
     * @param src
     * @return
     */
    public static String fromISO8859(String src) {
	try {
	    return new String(src.getBytes("ISO-8859-1"));
	} catch (Exception ex) {
	    return null;
	}
    }

    /**
     * �⺻ ���ڵ��� ���ڿ��� ISO-8859-1 ���ڵ��� ���ڿ��� ��ȯ�Ѵ�. (UNIX�� ��� ȯ�溯�� LANG�� ko, Ȥ�� ko_KR�̶�� �⺻ ���ڵ��� "EUC-KR"�� �ȴ�)
     * @param src
     * @return
     */
    public static String toISO8859(String src) {
	try {
	    return new String(src.getBytes(), "ISO-8859-1");
	} catch (Exception ex) {
	    return null;
	}
    }

    /**
     * �־��� �迭�� ���Ҹ� ��ȯ�ϸ� �� ���� System.out���� ������ش�.
     * @param objArray
     */
    public static void dump(byte[] byteArray) {
	if (byteArray == null)
	    return;
	System.out.print("array: [");
	for (int i = 0; i < byteArray.length; i++) {
	    System.out.print(byteArray[i]);
	    System.out.print(", ");
	}
	System.out.println("]");
    }

    /**
     * �־��� �迭�� ���Ҹ� ��ȯ�ϸ� �� ���� System.out���� ������ش�.
     * @param objArray
     */
    public static void dump(Object[] objArray) {
	if (objArray == null)
	    return;
	System.out.print("array: [");
	for (int i = 0; i < objArray.length; i++) {
	    System.out.print(objArray[i]);
	    System.out.print(", ");
	}
	System.out.println("]");
    }

    /**
     * �־��� Enumeration�� ��ȯ�ϸ� �� ���� System.out���� ������ش�.
     * @param en
     */
    public static void dump(Enumeration en) {
	if (en == null)
	    return;
	System.out.print("enumeration: [");
	while (en.hasMoreElements()) {
	    System.out.print(en.nextElement());
	    System.out.print(", ");
	}
	System.out.println("]");
    }

    /**
     * Ʈ���̿� ��� ���� �ٸ� Ʈ���̿� ��ģ��. target tray�� �̹� ������ key�� �־��� ��쿡�� �ڿ� append�ȴ�.
     * @deprecated TrayUtil�� �̵�
     * @param target ������ target�� �Ǵ� tray
     * @param src ������ source�� �Ǵ� tray
     */
    public static void mergeTray(Tray target, Tray src) {
	mergeTray(target, src, false);
    }

    /**
     * Ʈ���̿� ��� ���� �ٸ� Ʈ���̿� ��ģ��. target tray�� �̹� ������ key�� �־��� ��쿡�� �ڿ� append�ȴ�.
     * @deprecated TrayUtil�� �̵�
     * @param target ������ target�� �Ǵ� tray
     * @param src ������ source�� �Ǵ� tray
     * @param keyToLower key�� �ҹ��ڷ� ��ȯ
     */
    public static void mergeTray(Tray target, Tray src, boolean keyToLower) {
	TrayUtil.mergeTray(target, src, keyToLower);
    }

    /**
     * �־��� ���ڿ��� �ѱ��� �ѱ��ڶ� ����ִ����� �˷��ش�.
     * @param str
     * @return
     */
    public static boolean containsHangul(String str) {
	for (int i = 0; i < str.length(); i++) {
	    Character.UnicodeBlock unicode_block = Character.UnicodeBlock.of(str.charAt(i));
	    if (unicode_block == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO || unicode_block == Character.UnicodeBlock.HANGUL_JAMO || unicode_block == Character.UnicodeBlock.HANGUL_SYLLABLES) {
		return true;
	    }
	}

	return false;
    }

    /**
     * �־��� ���ڿ��� white space(�����̽�, ��, ����)�� ����ִ��� �˷��ش�.
     * @param str
     * @return
     */
    public static boolean containsWhiteSpace(String str) {
	for (int i = 0; i < str.length(); i++) {
	    if (Character.isWhitespace(str.charAt(i))) {
		return true;
	    }
	}
	return false;
    }

    /**
     * ���ڿ��� ���ϴ±��̸� �޾Ƽ� ���ڿ��� �����Ѵ�. ���Ͻ� ���ڿ��� ���ϴ� ���̺��� ª����� �������� ä���� �����Ѵ�. ��) getFixedStrFormat("String", 10, '0') return = 'String0000'
     * @return ���ڿ�
     */
    public static String getFixedStrFormat(String str, int len, char ch) {
	StringBuffer sb = new StringBuffer(str);
	int byteLen = str.getBytes().length;

	try {
	    for (int i = 0; i < (len - byteLen); i++) {
		sb.append(ch);
	    }

	    return sb.toString();
	} catch (Exception ex) {
	    return str;
	}
    }

    /**
     * ���ڿ��� ���ϴ±��̸� �޾Ƽ� ���ڿ��� �����Ѵ�. ���Ͻ� ���ڿ��� ���ϴ� ���̺��� ª�� ��� �������� ä���� �����Ѵ�. �ѱ��� 2���ڷ� ó���ȴ�. ��) getFixedStrFormat("String", 10) return = 'String ' @ return ���ڿ�
     */
    public static String getFixedStrFormat(String str, int len) {
	return getFixedStrFormat(str, len, ' ');
    }

    /**
     * �־��� ���� ch�� length����ŭ �ݺ��� ���ڿ��� �����Ͽ� �����ش�.
     * @param ch
     * @param length
     * @return
     */
    public static String repeat(char ch, int length) {
	char[] ch_array = new char[length];
	Arrays.fill(ch_array, ch);
	return new String(ch_array);
    }

    /**
     * ȣ��� ���������� stack trace�� ���ڿ� ���·� �����Ͽ� �����ش�. (����� weblogic server ������ stack trace�� ����)
     * @return
     */
    public static String getStackTrace() {
	try {
	    throw new RuntimeException();
	} catch (RuntimeException ex) {
	    StackTraceElement[] ste = ex.getStackTrace();
	    StringBuffer sb = new StringBuffer();
	    for (int i = ste.length - 17; i > 1; i--) {
		sb.append(ste[i].getClassName()).append('.');
		sb.append(ste[i].getMethodName());
		sb.append(" -> ");
	    }
	    return sb.toString();
	}
    }

    /**
     * byte�� �迭�� ���ڿ��� ��ȯ�Ѵ�. 1byte�� 2�ڸ��� char�� ��ȯ�Ǹ�, ������ ���� ���¸� ���. 9 -> "9" 10 (=0xa) -> "a" 255 (=0xff) -> "ff"
     * @param byteArray
     * @return
     */
    public static String toHexString(byte[] byteArray) {
	if (byteArray == null)
	    return null;
	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < byteArray.length; i++) {
	    int b = (byteArray[i] & 0x80) == 0x80 ? 256 + byteArray[i] : byteArray[i];
	    sb.append(Character.forDigit(b / 16, 16)).append(Character.forDigit(b % 16, 16));
	}
	return sb.toString();
    }

    /**
     * ���ڿ���
     * @param str
     * @return
     */
    public static byte[] fromHexString(String str) {
	if (str == null)
	    return null;
	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	for (int i = 0; i < str.length(); i++) {
	    int digit = Character.digit(str.charAt(i), 16) * 16 + Character.digit(str.charAt(++i), 16);
	    bos.write(digit);
	}
	return bos.toByteArray();
    }

    private static final int DEFAULT_READ_BUFFER_SIZE = 4000;

    /**
     * �־��� Reader ��ü�� ���������� stream�� �а�, �� ����� String���� �����ش�.
     * @param in stream�� �о Reader ��ü
     * @param bufferSize ���ۻ�����
     * @return stream�� ����ִ� String ��ü
     * @throws IOException
     */
    public static String readToEnd(Reader in, int bufferSize) throws IOException {
	char[] cbuf = new char[bufferSize];
	StringWriter out = new StringWriter(bufferSize);
	int chars_read = 0;
	while ((chars_read = in.read(cbuf, 0, bufferSize)) >= 0) {
	    out.write(cbuf, 0, chars_read);
	}

	return out.toString();
    }

    public static String readToEnd(Reader in) throws IOException {
	return readToEnd(in, DEFAULT_READ_BUFFER_SIZE);
    }

    public static URL getClassLocation(String className) {
	return CRAUtil.class.getResource(className);
    }

    private static final Pattern FOR_JAVASCRIPT_PATTERN1 = Pattern.compile("(?<!\\\\)('|\")");

    private static final Pattern FOR_JAVASCRIPT_PATTERN2 = Pattern.compile("\r?\n");

    /**
     * ����ǥ�� carriage return�� Ư�����ڰ� javascript���� ��Ȯ�� �νĵǵ��� escape�����ִ� method. Ư������ �տ� '\'�� �ٿ��ش�.
     * @param src
     * @return
     */
    public static String forJavaScript(String src) {
	String temp = FOR_JAVASCRIPT_PATTERN1.matcher(src).replaceAll("\\\\$1");
	return FOR_JAVASCRIPT_PATTERN2.matcher(temp).replaceAll("\\\\n");
    }

    /**
     * {@link #forJavaScript}�� Ȯ�����. onclick handler�� ���� ���ڿ��� �������� �ο�Ǿ�� �� �� ����Ѵ�. inHandler �з����Ͱ� true�� ��쿡�� �ܼ��� �տ� '\'�� �ٿ��ִµ��� ������ �ʰ� Ư������ ��ü�� html escape sequence�� ��ü�Ѵ�.
     * @param src
     * @param inHandler
     * @return
     */
    public static String forJavaScript(String src, boolean inHandler) {
	return (inHandler ? forJavaScriptHandler(src) : forJavaScript(src));
    }

    /**
     * @param src
     */
    private static String forJavaScriptHandler(String src) {
	StringBuffer buf = new StringBuffer(src.length() * 6 / 5);
	for (int i = 0; i < src.length(); i++) {
	    char c = src.charAt(i);
	    switch (c) {
		case '"':
		    buf.append("\\&#34;");
		    break;
		case '\'':
		    buf.append("\\&#39;");
		    break;
		case '<':
		    buf.append("\\&lt;");
		    break;
		case '>':
		    buf.append("\\&gt;");
		    break;
		case '\n':
		    buf.append("\\n");
		    break;
		case '\r':
		    break;
		default:
		    buf.append(c);
	    }
	}
	return buf.toString();
    }

    /**
     * ������ �������� ������� �������� ��� true ����.
     * @return
     */
    public static boolean inDemoMode() {
	return Boolean.getBoolean("tubis.demo");
    }
}