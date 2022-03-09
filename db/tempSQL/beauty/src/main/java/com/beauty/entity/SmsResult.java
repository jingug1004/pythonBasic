package com.beauty.entity;

import java.util.Date;

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
@Table(name="APP_SMS_RESULT")
@EqualsAndHashCode
@ToString
public class SmsResult {

	@Id
	@GeneratedValue
	@Column(name="sms_seq")
	@Getter @Setter
	private Long smsSeq;
	
	@Column(name="group_id")
	@Getter @Setter
	private String groupId;
	
	@Column(name="result_code")
	@Getter @Setter
	private String resultCode;
	
	@Column(name="result_message")
	@Getter @Setter
	private String resultMessage;
	
	@Column(name="send_number")
	@Getter @Setter
	private String sendNumber;
	
	@Column(columnDefinition = "TEXT", nullable=false)
	@Getter @Setter
	private String message;
	
	@Column(name="send_date")
	@Getter @Setter
	private Date sendDate;
}
