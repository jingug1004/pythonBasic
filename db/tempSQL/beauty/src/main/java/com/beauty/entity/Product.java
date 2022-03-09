package com.beauty.entity;

import java.text.DecimalFormat;
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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="APP_PRODUCT")
@EqualsAndHashCode
@ApiModel
public class Product {
	
	@ApiModelProperty(value = "상품 ID")
    @Id 
    @Column(name="product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long productId;
    
	@ApiModelProperty(value = "썸네일")
	@Column(name="thumb_url")
	@Getter @Setter
	private String thumbUrl;
	
	@ApiModelProperty(value = "배너")
	@Column(name="banner")
	@Getter @Setter
	private String banner;
	
	@ApiModelProperty(value = "상품 상세")
	@Column(name="detail_url")
	@Getter @Setter
	private String detailUrl;

    @ApiModelProperty(value = "내용")
    @Lob
    @Getter @Setter
    private String content;
    
	@ApiModelProperty(value = "상품명")
	@Column(name="prod_name")
	@Getter @Setter
	private String prodName;
    
	@ApiModelProperty(value = "상품 설명")
	@Column(name="prod_desc")
	@Getter @Setter
	private String prodDesc;
    
	@ApiModelProperty(value = "등록일")
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();
    
	@ApiModelProperty(value = "원가")
	@Column(name="price")
	@Getter @Setter
	private int price;
	
	@ApiModelProperty(value = "세일")
	@Column(name="sale")
	@Getter @Setter
	private int sale;
	
	@ApiModelProperty(value = "세일가")
	@Column(name="sale_price")
	@Getter @Setter
	private int salePrice;
	
	// 카테고리
//	@ApiModelProperty(value = "카테고리 목록")
//    @OneToMany(cascade=CascadeType.ALL, mappedBy="product")
//	@Getter @Setter
//	public List<ProductCategory> category;
    
	// 브랜드
    @ApiModelProperty(value = "브랜드")
	@ManyToOne
	@JoinColumn(name="brand_id")
	@Getter @Setter
	private Brand brand;
	
    @ApiModelProperty(value = "배송정보")
    @ManyToOne
    @JoinColumn(name="delivery_id")
	@Getter @Setter
	private ProductDelivery productDelivery;
	
    @JsonIgnore
    @ApiModelProperty(value = "리뷰 목록")
    @OneToMany(cascade=CascadeType.ALL, mappedBy="product")
	@Getter @Setter
	public List<Review> review;
    
    @ApiModelProperty(value = "리뷰 수")
    @Column(name="review_count", columnDefinition="int(11) default '0'")
    @Getter @Setter
    public int reviewCount;
    
	@ApiModelProperty(value = "조회수")
	@Column(name="view_count", columnDefinition="int(11) default '0'")
	@Getter @Setter
    public int viewCount;
    
    @ApiModelProperty(value = "판매자")
    @ManyToOne
    @JoinColumn(name="seller")
   	@Getter @Setter
	public User seller;
    
	@Column(name="stop_selling", columnDefinition="int(1) default '0'")
	@ApiModelProperty(value = "판매중지 [0 : 판매 , 1 : 중지]")
	@Getter @Setter
	private int stopSelling = 0;
	
    @ApiModelProperty(value = "리뷰 평점")
	@Column(name="review_star")
	@Getter @Setter
    public float reviewStar = 0;

    @ApiModelProperty(value = "한정 특가  0 : 기본 1 : 한정특가 ")
    @Column(name="time_sale", columnDefinition="int(1) default '0'")
	@Getter @Setter
	public int timeSale = 0;

    @ApiModelProperty(value = "박스  [0 : 일반,  1 : 박스] ")
    @Column(name="box", columnDefinition="int(1) default '0'")
	@Getter @Setter
	public int box = 0;
    
    @ApiModelProperty(value = "베스트 점수 ")
    @Column(name="score")
	@Getter @Setter
	public Long score = 0L;
    
    @JsonIgnore
	public float getStarAvg() {
    	float star = 0;
		for(Review rv:getReview()) {
			star = star + rv.getStar(); 
		}
		if(star > 0) {
			star = star / review.size();
			DecimalFormat df=new DecimalFormat("#.0");
			return Float.parseFloat(df.format(star));
			
		}
		return star;
	}
    
    @Transient
    @Getter @Setter
    private int star5;
    @Transient
    @Getter @Setter
    private int star4;
    @Transient
    @Getter @Setter
    private int star3;
    @Transient
    @Getter @Setter
    private int star2;
    @Transient
    @Getter @Setter
    private int star1;
    
}
