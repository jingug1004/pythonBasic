package com.beauty.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="APP_USER")
@EqualsAndHashCode
@ToString
@ApiModel
public class User {

	@Id 
    @ApiModelProperty(value = "고유 ID")
	@Column(name="user_id", nullable=false, length=100)
    @Getter @Setter
	private String userId;
    
    @ApiModelProperty(value = "EMAIL ID")
    @Column(name="email", nullable=false, length=100)
    @Getter @Setter
	private String email;

    @ApiModelProperty(value = "이름")
    @Column(name="name", nullable=false, length=20)
    @Getter @Setter
	private String name;
	
    @JsonIgnore
    @ApiModelProperty(value = "비밀번호")
	@Column(name="password", nullable=false, length=255)
	@Getter @Setter
	private String password;

    @ApiModelProperty(value = "전화번호")
	@Column(name="phone", nullable=false, length=20)
	@Getter @Setter
	private String phone;
	
    @ApiModelProperty(value = "프로필 사진")
	@Column(name="profile")
	@Getter @Setter
	private String profile;
	
    @ApiModelProperty(value = "성별  M : 남자  F : 여자")
    @Column(name="gender", columnDefinition="CHAR(1) default 'F'")
    @Getter @Setter
	private String gender = "F";
	
    @ApiModelProperty(value = "생년월일 ex) 20170101")
    @Column(name="birthday", columnDefinition="INT(8)")
    @Getter @Setter
	private int birthday;
    
    @ApiModelProperty(value = "피부타입  0 : 건성,  1 : 중성,  2 : 지성,  3 : 복합성")
    @Column(name="skin_type", columnDefinition="int(1) default '0'")
    @Getter @Setter
	private int skinType = 0;

    @ApiModelProperty(value = "SMS 수신동의  Y : 동의  N : 미동의")
    @Column(name="sms_agree", columnDefinition="CHAR(1) default 'N'")
    @Getter @Setter
	private String smsAgree = "N";
    
    @ApiModelProperty(value = "EMAIL 수신동의  Y : 동의  N : 미동의")
    @Column(name="email_agree", columnDefinition="CHAR(1) default 'N'")
    @Getter @Setter
	private String emailAgree = "N";
    
    @ApiModelProperty(value = "나의 추천코드")
    @Column(name="my_code", nullable=false, length=20)
    @Getter @Setter
	private String myCode;
	
	@JsonIgnore
	@ApiModelProperty(value = "탈퇴 이유")
	@Lob
	@Getter @Setter
	private String reason;
	
	@Column(name="del_yn", columnDefinition="CHAR(1) default 'N'")
	@Getter @Setter
	private String delYn = "N";

	@Column(name="first_coupon", columnDefinition="CHAR(1) default 'N'")
	@Getter @Setter
	private String firstCoupon = "N";
	
	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="del_date")
	@Getter @Setter
	private Date delDate;
	
	@ApiModelProperty(value = "가입일")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();
	
	@OneToMany
	@JoinColumn(name="APP_USER_ID", referencedColumnName="user_id")
	@Getter @Setter
	private List<UserRole> roles;
	
    @ApiModelProperty(value = "회원가입 유형 N : 네이버  F : 페이스북  K : 카카오톡  D : 기본")
    @Column(name="join_type", columnDefinition="CHAR(1) default 'D'")
    @Getter @Setter
	private String joinType = "D";
	
	@ApiModelProperty(value = "나이")
	@Transient
	@Getter @Setter
	private int age;

	@ApiModelProperty(value = "카카오연결여부")
	@Transient
	@Getter @Setter
	private boolean isKakao = false;
	
	@ApiModelProperty(value = "페이스북 연결여부")
	@Transient
	@Getter @Setter
	private boolean isFacebook = false;
	
	@ApiModelProperty(value = "네이버 연결여부")
	@Transient
	@Getter @Setter
	private boolean isNaver = false;
}
