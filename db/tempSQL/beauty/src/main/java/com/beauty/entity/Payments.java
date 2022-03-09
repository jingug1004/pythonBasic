package com.beauty.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="APP_PAYMENT")
@ApiModel
@EqualsAndHashCode
@ToString
public class Payments {

	@ApiModelProperty(value = "결제 ID")
	@Id
	@Column(name="payment_uid")
	@Getter @Setter
	private String paymentId;
	
	@ApiModelProperty(value = "아임포트 ID")
	@Column(name="imp_uid")
	@Getter @Setter
	private String impUid;
	
	@ApiModelProperty(value = "가맹점에서 전달한 거래 고유 UID")
	@Column(name="merchant_uid")
	@Getter @Setter
	private String merchantUid;
	
	@ApiModelProperty(value = "총 결제금액")
	@Column(name="total_payment")
	@Getter @Setter
	private int totalPayment;
	
    @ApiModelProperty(value = "중복쿠폰 ID")
    @Column(name="coupon_id")
	@Getter @Setter
	private String couponId;
    
    @ApiModelProperty(value = "중복쿠폰 할인액")
    @Column(name="coupon_discount")
	@Getter @Setter
	private int couponDiscount;
    
    @ApiModelProperty(value = "일반쿠폰 ID")
    @Column(name="normal_coupon_id")
	@Getter @Setter
	private String normalCouponId;
    
    @ApiModelProperty(value = "일반쿠폰 할인액")
    @Column(name="normal_coupon_discount")
	@Getter @Setter
	private int normalCouponDiscount;
    
    
    @ApiModelProperty(value = "포인트할인금액")
    @Column(name="point_discount")
	@Getter @Setter
	private int pointDiscount;
    
	@ApiModelProperty(value = "구매자ID")
	@ManyToOne
	@JoinColumn(name="user_id")
	@Getter @Setter
	private User user;

    @ApiModelProperty(value = "주문자명")
    @Column(name="buyer_name")
	@Getter @Setter
	private String buyer_name;
    
    @ApiModelProperty(value = "주문자 Email주소")
    @Column(name="buyer_email")
	@Getter @Setter
	private String buyer_email;
    
    @ApiModelProperty(value = "주문자 전화번호")
    @Column(name="buyer_tel")
	@Getter @Setter
	private String buyer_tel;
    
    @ApiModelProperty(value = "주문자 주소 ")
    @Column(name="buyer_addr")
	@Getter @Setter
	private String buyer_addr;
    
    @ApiModelProperty(value = "주문자 우편번호")
    @Column(name="buyer_postcode")
	@Getter @Setter
	private String buyer_postcode;
    
    @ApiModelProperty(value = "결제상태. ready:미결제, paid:결제완료, cancelled:결제취소, failed:결제실패 = ['ready', 'paid', 'cancelled', 'failed']")
    @Column(name="status")
	@Getter @Setter
	private String status;
    
	@ApiModelProperty(value = "주문일")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();
	
	@ApiModelProperty(value = "결제 옵션 목록")
	@OneToMany
	@JoinColumn(name="payment_id")
	@Getter @Setter
	private List<PaymentItem> paymentItem;
	
    @ApiModelProperty(value = "배송 요청 사항")
    @Lob
    @Getter @Setter
    private String note;
    
	@Column(name="point_yn", columnDefinition="int(1) default '0'")
	@ApiModelProperty(value = "포인트지급여부 [0 : 미지급 , 1 : 지급]")
	@Getter @Setter
	private int pointYn = 0;
	
	@Column(name="paid_proc", columnDefinition="int(1) default '0'")
	@ApiModelProperty(value = "완료 처리 여부 [0 : 미처리 , 1 : 처리]")
	@Getter @Setter
	private int paidProc = 0;
	
	@ApiModelProperty(value = "samsung : 삼성페이 / card : 신용카드 / trans : 계좌이체 / vbank : 가상계좌")
	@Getter @Setter
	String pay_method;
	
	@ApiModelProperty(value = "PG사 명칭. inicis(이니시스) / nice(나이스정보통신)")
	@Getter @Setter
	String pg_provider;
	
	@ApiModelProperty(value = "PG사 승인정보")
	@Getter @Setter
	String pg_tid;
	
	@ApiModelProperty(value = " 에스크로결제 여부")
	@Getter @Setter
	boolean escrow;
	
	@ApiModelProperty(value = " 카드사 승인정보(계좌이체/가상계좌는 값 없음)")
	@Getter @Setter
	String apply_num;
	
	@ApiModelProperty(value = "카드사 명칭 ")
	@Getter @Setter
	String card_name;
	
	@ApiModelProperty(value = "할부개월 수(0이면 일시불) ")
	@Getter @Setter
	int card_quota;
	
	@ApiModelProperty(value = "입금받을 가상계좌 은행명")
	@Getter @Setter
	String vbank_name;
	
	@ApiModelProperty(value = "입금받을 가상계좌 계좌번호")
	@Getter @Setter
	String vbank_num;
	
	@ApiModelProperty(value = "입금받을 가상계좌 예금주")
	@Getter @Setter
	String vbank_holder;
	
	@ApiModelProperty(value = "입금받을 가상계좌 마감기한 UNIX timestamp")
	@Getter @Setter
	Date vbank_date;
	
	@ApiModelProperty(value = "주문명칭")
	@Getter @Setter
	String name;
	
	@ApiModelProperty(value = "주문(결제)금액")
	@Getter @Setter
	BigDecimal amount;
	
	@ApiModelProperty(value = "결제취소금액")
	@Getter @Setter
	BigDecimal cancel_amount;

	@ApiModelProperty(value = "결제완료시점 UNIX timestamp. 결제완료가 아닐 경우 0")
	@Getter @Setter
	Date paid_at;
	
	@ApiModelProperty(value = "결제실패시점 UNIX timestamp. 결제실패가 아닐 경우 0")
	@Getter @Setter
	Date failed_at;
	
	@ApiModelProperty(value = "결제취소시점 UNIX timestamp. 결제취소가 아닐 경우 0")
	@Getter @Setter
	Date cancelled_at;
	
	@ApiModelProperty(value = "결제실패 사유")
	@Getter @Setter
	String fail_reason;
	
	@ApiModelProperty(value = "결제취소 사유")
	@Getter @Setter
	String cancel_reason;
	
	@ApiModelProperty(value = "신용카드 매출전표 확인 URL")
	@Getter @Setter
	String receipt_url;
	
	
	@ApiModelProperty(value = "환불계좌 예금주")
	@Getter @Setter
	private String refund_holder;
	
	@ApiModelProperty(value = "환불계좌 은행코드")
	@Getter @Setter
	private String refund_bank;	
	
	@ApiModelProperty(value = "환불계좌 계좌번호")
	@Getter @Setter
	private String refund_account;
	
	
}
