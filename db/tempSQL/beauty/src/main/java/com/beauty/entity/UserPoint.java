package com.beauty.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/*
 * 유저 포인트 획득 로그 테이블
 * 포인트SEQ	INT(11)  Auto
 * 포인트 고유ID VARCHAR(20)
 * 회원 고유ID	INT(5)
 */
@Entity
@Table(name="APP_USER_POINT")
@EqualsAndHashCode
@ToString
public class UserPoint {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	@Getter @Setter
	private Long id;

	@ApiModelProperty(value = "포인트")
	@Column(name="point")
	@Getter @Setter
	private int point;

	@JsonIgnore
	@ApiModelProperty(value = "유저 고유ID")
	@ManyToOne
	@JoinColumn(name="user_id")
	@Getter @Setter
	private User user;

    @ApiModelProperty(value = "비고")
    @Column(name="note", nullable=false, length=255)
    @Getter @Setter
    private String note;

	@ApiModelProperty(value = "획득일")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();
    
    
}
