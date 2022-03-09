package com.beauty.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="APP_PAYMENT_ITEM")
@ApiModel
@EqualsAndHashCode
@ToString
public class PaymentItem {

	@ApiModelProperty(value = "결제 상품")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="pi_id")
	@Getter @Setter
	private Long piId;

	@Transient
	@ApiModelProperty(value = "주문ID")
	private String paymentId;
	
	@ManyToOne
	@JoinColumn(name="payment_id")
	@JsonBackReference
	@Getter @Setter
	private Payments payments;

	@ApiModelProperty(value = "주문 상품 명")
	@Column(name="prod_name")
	@Getter @Setter
	private String prodName;
	
	@ApiModelProperty(value = "주문 옵션명")
	@Column(name="item_name")
	@Getter @Setter
	private String itemName;
	
//	@ApiModelProperty(value = "주문상품아이템")
//	@Column(name="item_id")
//	@Getter @Setter
//	private Long itemId;
	
    @ManyToOne
	@JoinColumn(name="item_id")
	@Getter @Setter
    private ProductItem itemId;
    
	@ApiModelProperty(value = "공급가")
	@Column(name="supply_price")
	@Getter @Setter
	private int supply_price;
	
	@ApiModelProperty(value = "판매금액")
	@Column(name="amount")
	@Getter @Setter
	private int amount;

	@ApiModelProperty(value = "쿠폰ID")
	@Column(name="coupon_id")
	@Getter @Setter
	private String couponId;

	@ApiModelProperty(value = "할인금액")
	@Column(name="coupon_discount")
	@Getter @Setter
	private int couponDiscount;

	@ApiModelProperty(value = "구매 금액")
	@Column(name="payment")
	@Getter @Setter
	private int payment;

	@ApiModelProperty(value = "주문수량")
	@Column(name="order_count")
	@Getter @Setter
	private int orderCount;

	@ApiModelProperty(value = "택배비용")
	@Column(name="delivery_pay")
	@Getter @Setter
	private int delivery_pay;

	@ApiModelProperty(value = "0:묶음배송  1:무료배송  2:배송료지불")
	@Column(name="delivery_type")
	@Getter @Setter
	private int delivery_type;

	@ApiModelProperty(value = "판매자ID")
	@ManyToOne
	@JoinColumn(name="user_id")
	@Getter @Setter
	private User user;

    @ApiModelProperty(value = "택배사 코드")
    @Column(name="delivery_code")
    @Getter @Setter
	private String deliveryCode;
    
    @ApiModelProperty(value = "택배사")
    @Column(name="delivery_name", nullable=true, length=20)
    @Getter @Setter
	private String deliveryName;
    
	@ApiModelProperty(value = "송장번호")
	@Column(name="delivery_number")
	@Getter @Setter
	private String deliveryNumber;

    @ApiModelProperty(value = "결제상태. ready:미결제, paid:결제완료, cancelled:결제취소, failed:결제실패 = ['ready', 'paid', 'cancelled', 'failed']")
    @Column(name="status")
	@Getter @Setter
	private String status;
    
	@ApiModelProperty(value = "등록일")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();
    
	@Column(name="prod_confirm", columnDefinition="int(1) default '0'")
	@ApiModelProperty(value = "상품 확인 [0 : 미확인 , 1 : 확인]")
	@Getter @Setter
	private int prodConfirm = 0;
	
	@ApiModelProperty(value = "주문자명")
	@Transient
	private String buyer_name;

    @ApiModelProperty(value = "주문자 Email주소")
    @Transient
	private String buyer_email;
    
    @ApiModelProperty(value = "주문자 전화번호")
    @Transient
	private String buyer_tel;
    
    @ApiModelProperty(value = "주문자 주소 ")
    @Transient
	private String buyer_addr;
    
    @ApiModelProperty(value = "주문자 우편번호")
    @Transient
	private String buyer_postcode;
    
    @ApiModelProperty(value = "배송 요청사항")
    @Transient
	private String note;
    
    
    
	public String getBuyer_name() {
		return payments.getBuyer_name();
	}

	public String getBuyer_email() {
		return payments.getBuyer_email();
	}

	public String getBuyer_tel() {
		return payments.getBuyer_tel();
	}

	public String getBuyer_addr() {
		return payments.getBuyer_addr();
	}

	public String getBuyer_postcode() {
		return payments.getBuyer_postcode();
	}

	public String getNote() {
		return payments.getNote();
	}

	public String getPaymentId() {
		return payments.getPaymentId();
	}
    
}
