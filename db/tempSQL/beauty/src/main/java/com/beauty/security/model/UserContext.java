package com.beauty.security.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * @author vladimir.stankovic
 *
 * Aug 4, 2016
 */
public class UserContext {
    private final String userId;
    private final List<GrantedAuthority> authorities;

    private UserContext(String userId, List<GrantedAuthority> authorities) {
        this.userId = userId;
        this.authorities = authorities;
    }
    
    public static UserContext create(String userId, List<GrantedAuthority> authorities) {
        if (StringUtils.isBlank(userId)) throw new IllegalArgumentException("ID is blank: " + userId);
        return new UserContext(userId, authorities);
    }

    public String getUserId() {
        return userId;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
