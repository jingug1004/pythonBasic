package org.sosfo.framework.business;

import java.util.Collection;

import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.tray.Tray;

/**
 * ���� ����Ͻ� �������̽� ���: �޼ҵ� ȣ�� �� �� ����Ͻ� �������̽��� �������� �� ��. �Ķ���Ϳ� ���� Ÿ������ �׻� ���� ����Ʈ �������̽��� ����ؾ� �Ѵ�.
 * @author <b>������</b>
 * @version 1.0, 2004/01/08
 */

public interface Business {

    /**
     * �� �޼ҵ�� ������ �ҽ����� �ϳ��� ���ڵ带 �˻��ϴ� ���� �����Ѵ�.
     * @param reqTrayPrimaryKey: ������ �ҽ����� ���ڵ带 �����ϴ� ������ ID ��
     * @return Tray: ������ �ҽ����� �˻��� �����͸� Map ������� ������ ���� Ŭ����
     * @exception <code>org.sosfo.framework.exception.AppException</code>: ����Ͻ� ���� ����� �߻��ϴ� ���� ��.
     */
    public Tray findByPrimaryKey(Tray reqTrayPrimaryKey) throws AppException;

    /**
     * �� �޼ҵ�� ������ �ҽ��� �ϳ��� ���ڵ带 �����ϴ� ���� �����Ѵ�.
     * @param reqTray: ����ڰ� ȭ�鿡�� �Է��� �����͸� Map ������� ������ ���� Ŭ����
     * @return Tray: ������ �ҽ����� �˻��� �����͸� Map ������� ������ ���� Ŭ����
     * @exception <code>org.sosfo.framework.exception.AppException</code>: ����Ͻ� ���� ����� �߻��ϴ� ���� ��.
     */
    public boolean insert(Tray reqTray) throws AppException;

    /**
     * �� �޼ҵ�� ������ �ҽ��� �ϳ��� ���ڵ带 �����ϴ� ���� �����Ѵ�.
     * @param reqTray: ����ڰ� ȭ�鿡�� �Է��� �����͸� Map ������� ������ ���� Ŭ����
     * @return Tray: ������ �ҽ����� �˻��� �����͸� Map ������� ������ ���� Ŭ����
     * @exception <code>org.sosfo.framework.exception.AppException</code>: ����Ͻ� ���� ����� �߻��ϴ� ���� ��.
     */
    public boolean update(Tray reqTray) throws AppException;

    /**
     * �� �޼ҵ�� ������ �ҽ��� �ϳ��� ���ڵ带 �����ϴ� ���� �����Ѵ�.
     * @param reqTray: ����ڰ� ȭ�鿡�� �Է��� �����͸� Map ������� ������ ���� Ŭ����
     * @return Tray: ������ �ҽ����� �˻��� �����͸� Map ������� ������ ���� Ŭ����
     * @exception <code>org.sosfo.framework.exception.AppException</code>: ����Ͻ� ���� ����� �߻��ϴ� ���� ��.
     */
    public boolean delete(Tray reqTray) throws AppException;

    /**
     * �� �޼ҵ�� �� Tray�� �����Ͽ� insert(pTray)�� ȣ���Ѵ�.
     * @return Tray: �� �� Map ����� ���� Ŭ����
     * @exception <code>org.sosfo.framework.exception.AppException</code>: ����Ͻ� ���� ����� �߻��ϴ� ���� ��.
     */
    public Tray createTray() throws AppException;

    /**
     * �� �޼ҵ�� ������ �ҽ����� �ٰ��� �����͸� �˻��Ͽ� �����Ѵ�.
     * @param reqTray: ����ڰ� ȭ�鿡�� �Է��� �����͸� Map ������� ������ ���� Ŭ����
     * @return Tray: ResultSetTray ��ü - primary key�� �ƴ� �ٸ� �������� ã�� �Ǵ� �����
     * @exception <code>org.sosfo.framework.exception.AppException</code>: ����Ͻ� ���� ����� �߻��ϴ� ���� ��.
     */
    public Tray find(Tray reqTray) throws AppException;

    /**
     * �� �޼ҵ�� ������ �ҽ����� �˻��� ���� ������� �����Ѵ�.
     * @param reqTray: ����ڰ� ȭ�鿡�� �Է��� �����͸� Map ������� ������ ���� Ŭ����
     * @return Collection: ResultSetTray ��ü�� ��� �÷���
     * @exception <code>org.sosfo.framework.exception.AppException</code>: ����Ͻ� ���� ����� �߻��ϴ� ���� ��.
     */
    public Collection findAll(Tray reqTray) throws AppException;
}
