package com.beauty.response.result;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumeration of REST Error types.
 * 
 * @author vladimir.stankovic
 *
 *         Aug 3, 2016
 */
public enum JoinResultCode{
	SUCCESS(200), FAIL(99), EMPTY_PHONE(10);
	
    private int resultCode;
    
    private JoinResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    @JsonValue
    public int getResultCode() {
        return resultCode;
    }
    
}
