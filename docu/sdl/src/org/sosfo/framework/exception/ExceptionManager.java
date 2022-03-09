package org.sosfo.framework.exception;

import java.rmi.RemoteException;

/**
 * Delegate�� ������ catch �ڵ带 �ܼ�ȭ�ϱ� ���� ������ Ŭ����. ������ ���� �ڵ带 �̿��Ͽ� ����Ѵ�.
 * 
 * <pre>
 *  try {
 * 
 *  catch (Ư��ó���� �ʿ��� exception) {
 *  	Ư��ó��; // ����, ��ü�� ��ȯ, ������ ����...
 *  } catch (Exception ex) {
 *  	throw TubisExceptionManager.handleException(&quot;�α׿� ���� ����׸޽���(�Լ�ȣ����ġ�� �����ִµ�)&quot;, ex);
 *  } finally {
 *  	// �ʿ��ϴٸ�
 *  }
 * </pre>
 * 
 * @author �����
 * @version 1.1, 2004-03-08 return type�� void�� �ƴ� method���� try block ������ return�ϴ� ��� �߻��ϴ� ������ ������ �ذ��ϱ� ���� handleException()�� return type�� {@link AppException}���� ����.
 * @version 1.0, 2004-03-03
 */
public class ExceptionManager {
    /**
     * �Ϲ����� ���ܸ� ó���Ѵ�.
     * <ul>
     * <li>AppException�� �״�� throw</li>
     * <li>�׿��� Exception�� AppException���� ����� throw</li>
     * <li>RemoteException�� getCause()�� ������ ������ ���� ���� ó��</li>
     * </ul>
     * @param msg �α׿� ���� ����� �޽���
     * @param t catch�ϰ��� �ϴ� Exception ��ü
     * @return Exception�� ���ΰ��ִ� {@link AppException} ��ü
     */
    public static AppException handleException(String msg, Throwable t) throws AppException {
	if (t instanceof AppException) {
	    throw (AppException) t;
	} else if (t instanceof RemoteException) {
	    Throwable cause = ((RemoteException) t).getCause();
	    if (cause instanceof AppException) {
		throw (AppException) cause;
	    } else {
		throw new AppException(msg, cause);
	    }
	} else {
	    throw new AppException(msg, t);
	}
    }
}
