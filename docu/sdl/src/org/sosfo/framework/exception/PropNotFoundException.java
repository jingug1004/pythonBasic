package org.sosfo.framework.exception;

/**
 * <p>
 * Title: ������Ƽ�� ���� ���Ѱ�� �߻�
 * </p>
 * <p>
 * Description: �Ⱓ�ý��۰� LDAP ���� API
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: www.UbiwareLab.com
 * </p>
 * @author yunkidon@hotmail.com
 * @version 1.0
 */
public class PropNotFoundException extends Exception {

    public PropNotFoundException() {
    }

    public PropNotFoundException(String msg) {
	super(msg);
    }
}
