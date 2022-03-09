package com.beauty.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.Transient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="APP_REVIEW")
@EqualsAndHashCode
@ApiModel
public class Review {
	
	@Id 
    @Column(name="review_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long reviewId;
	
	@ApiModelProperty(value = "유저 고유ID")
	@ManyToOne
	@JoinColumn(name="user_id")
	@Getter @Setter
	private User user;
	
	@ApiModelProperty(value = "리뷰 사진")
	@OneToMany(cascade=CascadeType.ALL, mappedBy="reviewId")
	@Getter @Setter
	private List<ReviewPhoto> reviewPhoto;

	@ApiModelProperty(value = "상품 정보")
	@ManyToOne
	@JoinColumn(name="product_id")
	@Getter @Setter
	private Product product;
	
	@ApiModelProperty(value = "별점")
	@Column(name="star", columnDefinition="int(1) default '0'")
	@Getter @Setter
	private int star;
	
	@ApiModelProperty(value = "내용")
	@Lob
	@Getter @Setter
	private String content;
	
	@ApiModelProperty(value = "0 일반리뷰  1 포토리뷰")
    @Column(name="review_type", columnDefinition="int(1) default '0'")
	@Getter @Setter
	private int reviewType;
	
	@ApiModelProperty(value = "추천수")
    @Column(name="like_count", columnDefinition="int(11) default '0'")
	@Getter @Setter
	private int likeCount;
	
    @ApiModelProperty(value = "피부타입  0 : 건성,  1 : 중성,  2 : 지성,  3 : 복합성")
    @Column(name="skin_type", columnDefinition="int(1) default '0'")
    @Getter @Setter
	private int skinType = 0;
    
	@ApiModelProperty(value = "등록일")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();

	@Transient
	private String reviewerName;

	public String getReviewerName() {
		return reviewerName;
	}

	@Transient
	@Getter @Setter
	public boolean isLike = false;

	
}
