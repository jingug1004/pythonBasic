package com.beauty.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="APP_USER_SMS")
@EqualsAndHashCode
@ToString
@ApiModel
public class SmsCert {

	@Id
	@Column(name="phone")
	@Getter @Setter
	private String phone;
	
	@Column(name="number")
	@Getter @Setter
	private String certNumber;
	
	@Column(name="effective_time")
	@Getter @Setter
	private Date effectiveTime;
	
	@Column(name="cert_yn", columnDefinition="CHAR(1) default 'N'")
	@Getter @Setter
	private String certYn = "N";
}
