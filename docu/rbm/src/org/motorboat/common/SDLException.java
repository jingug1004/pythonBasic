package org.motorboat.common;

/**
 * SDL 수신프로그램에서 업무와 관련된 예외상황을 관리하는 Exception 
 * 
 * @author 김원겸 대상정보기술
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
