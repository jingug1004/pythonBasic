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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="APP_ATTENDANCE")
@ApiModel
@EqualsAndHashCode
@ToString
public class Attendance {

	@ApiModelProperty(value = "출첵 ID")
	@Id
	@Column(name="attendance_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long attendanceId;
	
	@ApiModelProperty(value = "유저 고유ID")
	@ManyToOne
	@JoinColumn(name="user_id")
	@Getter @Setter
	private User user;
	
	@Column(name="attend_month")
	@ApiModelProperty(value = "출석년월")
	@Getter @Setter
	private Long attendMonth;
	
	@Column(name="attend_day")
	@ApiModelProperty(value = "출석일")
	@Getter @Setter
	private Long attendDay;
	
	@JsonIgnore
	@ApiModelProperty(value = "등록일")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();
}
