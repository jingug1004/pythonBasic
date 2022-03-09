package com.beauty.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="APP_DEVICE_INFO")
@EqualsAndHashCode
@ApiModel
public class DeviceInfo {

    @Id 
    @Column(name="ID")
	@Getter @Setter
	private String deviceId;
	
    @ApiModelProperty(value = "DeviceToken")
    @Lob
	@Getter @Setter
	private String token;
	
    @ApiModelProperty(value = "OS")
    @Column(name="os", nullable=false, length=10)
	@Getter @Setter
	private String os;
    
    @ApiModelProperty(value = "OS버전")
    @Column(name="os_version", nullable=false, length=50)
	@Getter @Setter
	private String osVersion;
	
    @ApiModelProperty(value = "모델명")
    @Column(name="model_name", nullable=false, length=50)
	@Getter @Setter
	private String modelName;
	
    @ApiModelProperty(value = "앱버전")
    @Column(name="app_version", nullable=false, length=20)
	@Getter @Setter
	private String appVersion;
	
	@ApiModelProperty(value = "등록일")
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();
	
	@JsonIgnore
	@ApiModelProperty(value = "유저 고유ID")
	@ManyToOne
	@JoinColumn(name="user_id")
	@Getter @Setter
	private User user;
	
}
