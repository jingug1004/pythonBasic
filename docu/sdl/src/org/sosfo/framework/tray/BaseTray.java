package org.sosfo.framework.tray;

import java.util.Enumeration;

import org.sosfo.framework.exception.AppException;

/**
 * {@link AbstractTray}�� �߻� method�� �ִ��� �ܼ��ϰ� ������ Tray. ("�������� ���� method"��� ���� �߻���Ŵ) javax.servlet.http.HttpRequest�� java.sql.ResultSet���� �������� �ʰ� ����ڿ� ���� ���� �����Ǿ� delegate�� EJB�� �μ��� ���޵Ǵ� �뵵�� �����.
 * @author �����
 * @version 1.0, 2004-03-10
 */
public class BaseTray extends AbstractTray {
    public Enumeration getEnumeration() {
	throw new AppException("�������� ���� method�Դϴ�");
    }

}
