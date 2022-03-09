package com.beauty.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
 * 1:1 문의 테이블
 * SEQ	INT(11)  Auto
 * 제목		VARCHAR(100)
 * 문의내용	CLOB
 * 답글내용	CLOB
 * 답변여부	CHAR(1)
 * 문의날짜	DATETIME
 * 답변날짜	DATETIME
 * 회원 FK MANYTOONE
 */
@Entity
@Table(name="APP_QNA_IMAGE")
@EqualsAndHashCode
@ToString
public class QnaPhoto {

	@JsonIgnore
	@Id
	@GeneratedValue
	@Column(name="photo_id")
	@Getter @Setter
	private Long photoId;
	
	@ApiModelProperty(value = "1:1문의 ID")
	@Column(name="qna_id")
	@Getter @Setter
	private Long qnaId;
	
	@Column(name="photo_path")
	@Getter @Setter
	private String photoPath;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();


}
