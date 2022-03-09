package com.beauty.response.result;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumeration of REST Error types.
 * 
 * @author vladimir.stankovic
 *
 *         Aug 3, 2016
 */
public enum PaymentResultCode{
	SUCCESS(200), FAIL(99), POINT_ERROR(101), ITEM_ERROR(102), COUPON_ERROR(102);
	
    private int resultCode;
    
    private PaymentResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    @JsonValue
    public int getResultCode() {
        return resultCode;
    }
    
}
