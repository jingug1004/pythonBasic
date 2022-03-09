/**
 * ���ϸ�: tubis.asi.x.tray.RequestTray.java ���ϰ���: Ŭ���̾�Ʈ ��û ������ ĸ��ȭ�� Ŭ���� ���۱�: Copyright (c) 2003 by SK C&C. All rights reserved. �ۼ���: ������ (nucha@dreamwiz.com)
 */

package org.sosfo.framework.tray;

// Java API
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;

/**
 * RequestTray Ŭ������ HttpServletRequest ��ü ������ �����ϱ� ���� Tray�̴�.
 * <p>
 * @author <b>������</b>
 * @version 1.0, 2004/02/01
 */

public class RequestTray extends AbstractTray implements java.io.Serializable {

    public RequestTray() {
    }

    /**
     * ����� �����Ϳ� ���� ���� ����.
     * @return - Enumeration (List�� Ÿ�� ĳ�����ؼ� ����ϸ� ��)
     */
    public Enumeration getEnumeration() {
	return new Enumeration() {
	    Set      set   = map.keySet();

	    Iterator it    = set.iterator();

	    int      count = size();

	    public boolean hasMoreElements() {
		return size() != 0;
	    }

	    public Object nextElement() {
		String key = (String) it.next();
		return map.get(key);
	    }
	};
    }

    public String toString() {
	return super.toString();
    }
}