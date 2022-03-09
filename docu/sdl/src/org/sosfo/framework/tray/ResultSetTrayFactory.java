/**
 * ���ϸ�: tubis.asi.x.tray.ResultSetTrayFactory.java ���ϰ���: select ��� �� ������ Tray�� ĸ��ȭ�ϴ� Ŭ���� ���۱�: Copyright (c) 2003 by SK C&C. All rights reserved. �ۼ���: ������ (nucha@dreamwiz.com)
 */

package org.sosfo.framework.tray;

// Java API
import java.sql.ResultSet;

/**
 * ResultSetTrayFactory Ŭ������ Tray ��ü�� �����ϴ� ���丮 Ŭ������ ���� �߻�Ŭ�����̸� ���� ������ ��ӹ��� ���丮�� �����Ѵ�.
 * <p>
 * @author <b>������</b>
 * @version 1.0, 2004/02/02
 */

public abstract class ResultSetTrayFactory {
    /**
     * Tray ��ü ����
     * @param rs - ResultSet
     * @return - rs ��ü�� ������ ���� Tray ��ü
     */
    protected abstract Tray createTray(ResultSet rs);

    /**
     * ������ Tray�� �������� �޼ҵ�
     * @param rs - ResultSet
     * @return - rs ��ü�� ������ ���� Tray ��ü
     */
    public Tray getTray(ResultSet rs) {
	return createTray(rs);
    }
}