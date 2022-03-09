package com.beauty.response.result;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumeration of REST Error types.
 * 
 * @author vladimir.stankovic
 *
 *         Aug 3, 2016
 */
public enum CouponResultCode{
	SUCCESS(200), FAIL(99), CP_END(10), CP_EMPTY(20), DOWN_CP(30);
	
    private int resultCode;
    
    private CouponResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    @JsonValue
    public int getResultCode() {
        return resultCode;
    }
    
}
