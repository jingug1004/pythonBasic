package com.beauty.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USER_ROLE")
public class UserRole {
    @Embeddable
    public static class Id implements Serializable {
        private static final long serialVersionUID = 1322120000551624359L;
        
        @Column(name="APP_USER_ID", nullable=false, length=100)
        protected String userId;
        
        @Enumerated(EnumType.STRING)
        @Column(name="ROLE", nullable=false, length=20)
        protected Role role;
        
        public Id() { }

        public Id(String userId, Role role) {
            this.userId = userId;
            this.role = role;
        }
    }
    
    @EmbeddedId
    @Getter @Setter
    Id id = new Id();
    
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", insertable=false, updatable=false)
    @Setter
    protected Role role;

    public Role getRole() {
        return role;
    }
}
