package org.motorboat.common;

/**
 * SDL �������α׷����� ������ ���õ� ���ܻ�Ȳ�� �����ϴ� Exception 
 * 
 * @author ����� ����������
 *
 */
public class SDLException extends Exception {

	private String message = "";
	
	public SDLException() {
		super();
	}
	
	public SDLException(String message) {
		this.message = message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
