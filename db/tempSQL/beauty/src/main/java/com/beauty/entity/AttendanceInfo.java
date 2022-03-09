package com.beauty.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name="APP_ATTENDANCE_INFO")
@ApiModel
@EqualsAndHashCode
@ToString
public class AttendanceInfo {

	@ApiModelProperty(value = "출석체크 ID")
	@Id
	@Column(name="info_id")
	@Getter @Setter
	private Long infoId;
	
    
	@ApiModelProperty(value = "상단이미지")
	@Column(name="thumb_url")
	@Getter @Setter
	private String thumbUrl;
	
	@ApiModelProperty(value = "출석체크 상세")
	@Column(name="detail_url")
	@Getter @Setter
	private String detailUrl;
	
	@ApiModelProperty(value = "색상코드")
	@Column(name="color_code")
	@Getter @Setter
	private String colorCode;
	
	@JsonIgnore
	@ApiModelProperty(value = "등록일")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();
}
