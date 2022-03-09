package com.beauty.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Table(name="APP_QNA")
@EqualsAndHashCode
@ToString
public class Qna {
	
    @Id 
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Long id;
    
    @ApiModelProperty(value = "주문내역")
    @Column(name="order_id", nullable=true, length=100)
    @Getter @Setter
    private Long orderId;
    
    @ApiModelProperty(value = "문의분류1")
    @Column(name="qna_class_1", nullable=true, length=100)
    @Getter @Setter
    private String qnaClass1;
    
    @ApiModelProperty(value = "문의분류2")
    @Column(name="qna_class_2", nullable=true, length=100)
    @Getter @Setter
    private String qnaClass2;
    
    @ApiModelProperty(value = "제목")
    @Column(name="subject", nullable=true, length=100)
    @Getter @Setter
    private String subject;
    
    @ApiModelProperty(value = "내용")
    @Lob
    @Getter @Setter
    private String content;
    
    @ApiModelProperty(value = "답글내용")
    @Lob
    @Getter @Setter
    private String reply_content;
    
    @ApiModelProperty(value = "답변여부")
    @Column(name="reply_yn", columnDefinition="CHAR(1) default 'N'")
    @Getter @Setter
    private String replyYn = "N";
    
	@ApiModelProperty(value = "유저 고유ID")
	@ManyToOne
	@JoinColumn(name="user_id")
	@Getter @Setter
	private User user;
    

	@ApiModelProperty(value = "문의날짜")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();
	

	@ApiModelProperty(value = "답변달짜")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reply_date")
	@Getter @Setter
	private Date replyDate;

	@ApiModelProperty(value = "문의 이미지")
	@OneToMany
	@JoinColumn(name="qna_id")
	@Getter @Setter
	private List<QnaPhoto> qnaPhoto;
}
