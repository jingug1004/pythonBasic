package com.beauty.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class MemberLeaveException extends AuthenticationException {
	private static final long serialVersionUID = -5959543783324224865L;

	public MemberLeaveException(String msg) {
		super(msg);
	}

	public MemberLeaveException(String msg, Throwable t) {
		super(msg, t);
	}
}
