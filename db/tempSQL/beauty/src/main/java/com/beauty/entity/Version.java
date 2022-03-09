package com.beauty.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="APP_VERSION")
@EqualsAndHashCode
@ToString
public class Version {

	@Id
	@GeneratedValue
	@Column(name="version_seq")
	@Getter @Setter
	private Long versionSeq;
	
	@Column(name="version_name")
	@Getter @Setter
	private String versionName;
	
	@Column(name="os_type")
	@Getter @Setter
	private String osType;
	
	@Column(name="status", length=1)
	@Getter @Setter
	private int status;
	
}
