package snis.can_boa.common.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionManager implements HttpSessionListener {
	public static SessionManager sessionManager = null;
	public static Hashtable sessionMonitor;
	
	public SessionManager() {
		if (sessionMonitor == null) sessionMonitor = new Hashtable();
		sessionManager = this;
	}

	public int getActiveSessionCount() {
		return sessionMonitor.size();
	}
	public Enumeration getIds() {
		return sessionMonitor.keys();
	}
	
	// 중복사번 존재시 true Return
	public boolean checkDuplicationLogin(String sessionId, String userId) {
		boolean ret = false;
		Enumeration eNum = sessionMonitor.elements();
		System.out.println("session count:"+getActiveSessionCount());
		
		while(eNum.hasMoreElements()) {
			HttpSession sh_session = null;
			try {
				sh_session = (HttpSession)eNum.nextElement();
			} catch (Exception e) {
				continue;
			}

			String sUserId = (String)sh_session.getAttribute("USER_ID");
			if (sUserId != null) {
				if (sUserId.equals(userId) && !sessionId.equals(sh_session.getId())) {
					// 중복된 사번이 존재하는 경우 기존 세션을 소멸시킨다.
					try {
						// DB 삭제 처리
					} catch(Exception e) {
						e.printStackTrace();
					}
					sh_session.invalidate();
					ret = true;
					break;
				}
			}			
		}
		
		return ret;
	}
	
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		synchronized(sessionMonitor) {
			sessionMonitor.put(session.getId(), session);
			System.out.println("sessionCreated:"+session.getId());
		}
		
		// start of test
//		Enumeration eNum = sessionMonitor.elements();
//		System.out.println("session count:"+getActiveSessionCount());
//		
//		while(eNum.hasMoreElements()) {
//			HttpSession sh_session = null;
//			try {
//				sh_session = (HttpSession)eNum.nextElement();
//			} catch (Exception e) {
//				continue;
//			}
//			System.out.println("session id:"+sh_session.getId());
//			Enumeration eNum2  = sh_session.getAttributeNames();
//			System.out.println("session id:"+eNum2.toString());
//		}
		// end of test 
		
		
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		synchronized(sessionMonitor) {
			sessionMonitor.remove(session.getId());
		}
	}
}
