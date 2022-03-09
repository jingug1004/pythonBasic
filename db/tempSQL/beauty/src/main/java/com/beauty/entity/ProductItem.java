package com.beauty.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="APP_PRODUCT_ITEM")
@EqualsAndHashCode
@ApiModel
public class ProductItem {
    
	@Id 
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long itemId;
    
    @ApiModelProperty(value = "상품")
	@ManyToOne
	@JoinColumn(name="product_id")
	@Getter @Setter
	private Product product;
    
	@ApiModelProperty(value = "아이템명")
	@Column(name="item_name")
	@Getter @Setter
	private String itemName;
	
	@ApiModelProperty(value = "공급가")
	@Column(name="supply_price")
	@Getter @Setter
	private int supply_price;
	
	@ApiModelProperty(value = "세일가")
	@Column(name="price")
	@Getter @Setter
	private int price;
	
	@ApiModelProperty(value = "아이템 총 수량")
	@Column(name="total_item_count")
	@Getter @Setter
	private int totalItemCnt;
	
	@ApiModelProperty(value = "구매가능한 최대 수량")
	@Column(name="max_item_count")
	@Getter @Setter
	private int maxItemCnt;
	
	@ApiModelProperty(value = "남은 수량")
	@Column(name="item_count")
	@Getter @Setter
	private int itemCnt;
	
	@ApiModelProperty(value = "사이즈/용량/중량")
	@Column(name="info1")
	@Getter @Setter
	private String info1;
	
	@ApiModelProperty(value = "제품주요사양")
	@Column(name="info2")
	@Getter @Setter
	private String info2;
	
	@ApiModelProperty(value = "사용기한/개봉 후 사용기간")
	@Column(name="info3")
	@Getter @Setter
	private String info3;
	
	@ApiModelProperty(value = "제조/생산일자")
	@Column(name="info4")
	@Getter @Setter
	private String info4;
	
	@ApiModelProperty(value = "사용방법")
	@Column(name="info5")
	@Lob
	@Getter @Setter
	private String info5;
	
	@ApiModelProperty(value = "제조자")
	@Column(name="info6")
	@Getter @Setter
	private String info6;
	
	@ApiModelProperty(value = "제조판매업자")
	@Column(name="info7")
	@Getter @Setter
	private String info7;
	
	@ApiModelProperty(value = "제조국")
	@Column(name="info8")
	@Getter @Setter
	private String info8;
	
	@ApiModelProperty(value = "전성분")
	@Column(name="info9")
	@Lob
	@Getter @Setter
	private String info9;
	
	@ApiModelProperty(value = "식품의약품안전처 심사필 유무")
	@Column(name="info10")
	@Lob
	@Getter @Setter
	private String info10;
	
	@ApiModelProperty(value = "사용시 주의사항")
	@Column(name="info11")
	@Lob
	@Getter @Setter
	private String info11;
	
	@ApiModelProperty(value = "품질보증 기준")
	@Column(name="info12")
	@Getter @Setter
	private String info12;
	
	@ApiModelProperty(value = "소비자상당 관련 전화번호")
	@Column(name="info13")
	@Getter @Setter
	private String info13;
	
	@Column(name="stop_selling", columnDefinition="int(1) default '0'")
	@ApiModelProperty(value = "판매중지 0 : 판매  1 : 중지")
	@Getter @Setter
	private int stopSelling = 0;
	
	
	@ApiModelProperty(value = "등록일")
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();
	
	
}
