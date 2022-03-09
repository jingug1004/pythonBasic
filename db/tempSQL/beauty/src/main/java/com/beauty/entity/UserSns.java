package com.beauty.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="APP_USER_SNS")
@EqualsAndHashCode
@ToString
@ApiModel
public class UserSns {

	@Id 
    @ApiModelProperty(value = "고유 ID")
	@Column(name="sns_id", nullable=false, length=100)
    @Getter @Setter
	private String snsId;
	
    @ApiModelProperty(value = "회원가입 유형 N : 네이버  F : 페이스북  K : 카카오톡  D : 기본")
    @Column(name="join_type", columnDefinition="CHAR(1) default 'D'")
    @Getter @Setter
	private String joinType = "D";
    
    @JsonIgnore
    @ApiModelProperty(value = "비밀번호")
	@Column(name="password", nullable=false, length=255)
	@Getter @Setter
	private String password;
    
	@ApiModelProperty(value = "유저 고유ID")
	@ManyToOne
	@JoinColumn(name="user_id")
	@Getter @Setter
    private User user;
}
