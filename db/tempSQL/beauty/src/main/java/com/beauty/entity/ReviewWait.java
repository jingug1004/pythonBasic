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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="APP_REVIEW_WAIT")
@EqualsAndHashCode
@ApiModel
public class ReviewWait {
	
	@Id 
    @Column(name="wait_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long waitId;
	
	@ApiModelProperty(value = "상품 정보")
	@ManyToOne
	@JoinColumn(name="product_id")
	@Getter @Setter
	private Product product;
	
	@ApiModelProperty(value = "리뷰 옵션 목록")
	@OneToMany(cascade=CascadeType.ALL, mappedBy="reviewWait")
	@Getter @Setter
	private List<ReviewWaitItem> reviewWaitItem;
	
	@ApiModelProperty(value = "결제 ID")
	@Column(name="payment_uid")
	@Getter @Setter
	private String paymentId;
	
	@ApiModelProperty(value = "유저")
	@ManyToOne
	@JoinColumn(name="user_id")
	@Getter @Setter
	private User user;

	@ApiModelProperty(value = "구매일")
	@Column(name="pay_date")
	@Getter @Setter
	private Date payDate;
	
    @ApiModelProperty(value = "포인트지급")
    @Column(name="point", columnDefinition="CHAR(1) default 'N'")
    @Getter @Setter
	private String point = "N";
    
    @ApiModelProperty(value = "리뷰 작성 여부")
    @Column(name="review_write", columnDefinition="CHAR(1) default 'N'")
    @Getter @Setter
	private String reviewWrite = "N";
    

    @ApiModelProperty(value = "리뷰 작성 ID")
	@Getter @Setter
    @Column(name="review_id")
    private Long reviewId;
}
