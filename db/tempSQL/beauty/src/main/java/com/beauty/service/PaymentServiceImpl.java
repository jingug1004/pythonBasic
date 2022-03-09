package com.beauty.service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.beauty.BeautyConstants;
import com.beauty.common.AES256Util;
import com.beauty.common.DateUtil;
import com.beauty.common.RandomStringBuilder;
import com.beauty.entity.Brand;
import com.beauty.entity.Category;
import com.beauty.entity.Coupon;
import com.beauty.entity.CouponUser;
import com.beauty.entity.OrderCoupon;
import com.beauty.entity.OrderDelivery;
import com.beauty.entity.OrderItem;
import com.beauty.entity.OrderProduct;
import com.beauty.entity.OrderTotal;
import com.beauty.entity.OrderValid;
import com.beauty.entity.PaymentItem;
import com.beauty.entity.PaymentOrder;
import com.beauty.entity.Payments;
import com.beauty.entity.Product;
import com.beauty.entity.ProductCategory;
import com.beauty.entity.ProductItem;
import com.beauty.entity.ProductTimeSale;
import com.beauty.entity.Shipping;
import com.beauty.entity.SpecialCoupon;
import com.beauty.entity.User;
import com.beauty.entity.UserPoint;
import com.beauty.iamport.IamportClient;
import com.beauty.repository.BrandRepository;
import com.beauty.repository.CategoryRepository;
import com.beauty.repository.CouponRepository;
import com.beauty.repository.CouponUserRepository;
import com.beauty.repository.OrderCouponRepository;
import com.beauty.repository.OrderItemRepository;
import com.beauty.repository.OrderProductRepository;
import com.beauty.repository.OrderTotalRepository;
import com.beauty.repository.PaymentItemRepository;
import com.beauty.repository.PaymentRepository;
import com.beauty.repository.ProductCategoryRepository;
import com.beauty.repository.ProductItemRepository;
import com.beauty.repository.ProductRepository;
import com.beauty.repository.ProductTimeSaleRepository;
import com.beauty.repository.ShippingRepository;
import com.beauty.repository.SpecialCouponRepository;
import com.beauty.repository.UserPointRepository;
import com.beauty.repository.UserRepository;
import com.beauty.repository.specification.PaymentSpecification;
import com.beauty.response.OrderResponse;
import com.beauty.response.PaymentResponse;
import com.beauty.response.result.PaymentResultCode;
import com.beauty.response.result.ResultCode;
import com.beauty.security.auth.JwtAuthenticationToken;
import com.beauty.security.model.UserContext;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private PaymentItemRepository paymentItemRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductItemRepository productItemRepository;

	@Autowired
	private ProductTimeSaleRepository productTimeSaleRepository;

	@Autowired
	private UserPointRepository userPointRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private CouponUserRepository couponUserRepository;

	@Autowired
	private ShippingService shippingService;

	@Autowired
	private CouponService couponService;

	@Autowired
	private SpecialCouponRepository specialCouponRepository;

	@Autowired
	private OrderTotalRepository orderTotalRepository;

	@Autowired
	private OrderProductRepository orderProductRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private OrderCouponRepository orderCouponRepository;

	@Autowired
	private ShippingRepository shippingRepository;

	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	@Autowired
	private BrandRepository brandRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	@Transactional
	public OrderTotal paymentOrder(List<PaymentOrder> orderList, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		Long totalPrice = 0L;	// 총 가격
		Long delivery = 0L;		// 배송비
		int itemCount = orderList.size(); //총수량

		List<Long> ids = new ArrayList<>();
		for(PaymentOrder order:orderList) {
			ids.add(Long.parseLong(order.getItemId()));
		}

		OrderTotal orderTotal = new OrderTotal();
		String orderId =  new RandomStringBuilder().
				putLimitedChar(RandomStringBuilder.ALPHABET_NUMBER).
				setLength(15).build();
		orderTotal.setOrderId(orderId);
		orderTotal.setPaymentName("임시저장");
		orderTotal.setTotalCount(itemCount);
		orderTotal.setTotalPrice(totalPrice);
		orderTotal.setTotalDelivery(delivery);
		orderTotal.setUser(user);

		orderTotalRepository.save(orderTotal);

		List<OrderProduct> orderProductList = new ArrayList<>();
		OrderProduct orderProduct;


		List<OrderItem> orderItemList = new ArrayList<>();
		OrderItem orderItem;

		Product product = null;

		/* 배송비 계산을 위한 ..*/
		Map<Long, OrderDelivery> deliveryMap = new HashMap<>();
		OrderDelivery orderDelivery;

		List<Long> prodList = new ArrayList<>();
		List<Product> pList = new ArrayList<>();
		int i = 0;
		List<ProductItem> itemList = productItemRepository.findAll(ids);
		for(ProductItem item:itemList) {
			product = item.getProduct();
			if(itemCount > 1) {
				if(i == 0) {
					orderTotal.setPaymentName(product.getProdName());
				}
				i = i++;
			} else {
				orderTotal.setPaymentName(product.getProdName());
			}

			if(orderProductList.size() == 0) {
				orderProduct = new OrderProduct();
				orderProduct.setOrderTotal(orderTotal);
				orderProduct.setProductId(product.getProductId());
				orderProduct.setBrandName(product.getBrand().getBrandName());
				orderProduct.setProductName(product.getProdName());
				orderProduct.setThumbnail(product.getThumbUrl());
				orderProductList.add(orderProduct);
				prodList.add(product.getProductId());
				pList.add(product);
			} else {
				boolean chk = false;
				for(OrderProduct op:orderProductList) {
					if(op.getProductId() == product.getProductId()) {
						chk = true;
						break;
					}
				}

				if(!chk) {
					orderProduct = new OrderProduct();
					orderProduct.setOrderTotal(orderTotal);
					orderProduct.setProductId(product.getProductId());
					orderProduct.setBrandName(product.getBrand().getBrandName());
					orderProduct.setProductName(product.getProdName());
					orderProduct.setThumbnail(product.getThumbUrl());
					orderProductList.add(orderProduct);
					prodList.add(product.getProductId());
					pList.add(product);
				}
			}

			/* 브랜드 별 배송비 계산*/
			if(deliveryMap.containsKey(product.getBrand().getBrandId())) {
				orderDelivery = deliveryMap.get(product.getBrand().getBrandId());
				if(product.getProductDelivery().getFreeDelivery() == 0) {
					orderDelivery.setFreeDelivery(0);
					orderDelivery.setDeliveryPrice(0);
					for(PaymentOrder order:orderList) {
						if(item.getItemId() == Long.parseLong(order.getItemId())) {
							orderDelivery.setPrice(orderDelivery.getPrice() + (item.getPrice() * order.getItemCnt()));
							break;
						}
					}
					orderDelivery.setItemId(item.getItemId());
					deliveryMap.put(product.getBrand().getBrandId(), orderDelivery);
				} else if(product.getProductDelivery().getFreeDelivery() == -1) {
					if(orderDelivery.getFreeDelivery() == -1) {
						orderDelivery.setFreeDelivery(-1);
						orderDelivery.setDeliveryPrice(product.getProductDelivery().getDeliveryPrice());
						for(PaymentOrder order:orderList) {
							if(item.getItemId() == Long.parseLong(order.getItemId())) {
								orderDelivery.setPrice(orderDelivery.getPrice() + (item.getPrice() * order.getItemCnt()));
								break;
							}
						}
						orderDelivery.setItemId(item.getItemId());
						deliveryMap.put(product.getBrand().getBrandId(), orderDelivery);
					}
				} else {
					if(orderDelivery.getFreeDelivery() != 0) {
						orderDelivery.setFreeDelivery(product.getProductDelivery().getFreeDelivery());
						orderDelivery.setDeliveryPrice(product.getProductDelivery().getDeliveryPrice());
						for(PaymentOrder order:orderList) {
							if(item.getItemId() == Long.parseLong(order.getItemId())) {
								orderDelivery.setPrice(orderDelivery.getPrice() + (item.getPrice() * order.getItemCnt()));
								break;
							}
						}
						orderDelivery.setItemId(item.getItemId());
						deliveryMap.put(product.getBrand().getBrandId(), orderDelivery);
					}
				}
			} else {
				orderDelivery = new OrderDelivery();
				orderDelivery.setProductId(product.getProductId());
				orderDelivery.setDeliveryPrice(product.getProductDelivery().getDeliveryPrice());
				orderDelivery.setFreeDelivery(product.getProductDelivery().getFreeDelivery());
				for(PaymentOrder order:orderList) {
					if(item.getItemId() == Long.parseLong(order.getItemId())) {
						orderDelivery.setPrice(item.getPrice() * order.getItemCnt());
						break;
					}
				}

				orderDelivery.setItemId(item.getItemId());
				deliveryMap.put(product.getBrand().getBrandId(), orderDelivery);
			}

			/*// 브랜드 별 배송비 계산 end */

			orderItem = new OrderItem();

			orderItem.setItemId(item.getItemId());
			orderItem.setProductId(product.getProductId());
			orderItem.setItemName(item.getItemName());
			orderItem.setDelivery(0);
			for(PaymentOrder order:orderList) {
				if(item.getItemId() == Long.parseLong(order.getItemId())) {
					System.out.println("수량 : " + order.getItemCnt());
					totalPrice += item.getPrice() * order.getItemCnt();
					orderItem.setPrice(item.getPrice() * order.getItemCnt());
					orderItem.setItemCount(order.getItemCnt());
					break;
				}
			}

			orderItemList.add(orderItem);
		}

		orderProductList = orderProductRepository.save(orderProductList);

		// 총 배송비 계산
		Iterator<Long> keys = deliveryMap.keySet().iterator();
		while( keys.hasNext() ){
			Long key = keys.next();
			orderDelivery =  deliveryMap.get(key);
			System.out.println("orderDelivery : " + orderDelivery);

			// 유료배송이거나 조건 보다 낮은 금액일 경우
			if(orderDelivery.getFreeDelivery() == -1
					|| orderDelivery.getFreeDelivery() > orderDelivery.getPrice()) {
				delivery += orderDelivery.getDeliveryPrice();
			}

			for(OrderItem item : orderItemList) {
				if(item.getItemId() == orderDelivery.getItemId()) {
					item.setDelivery(orderDelivery.getDeliveryPrice());
				}
			}
		}

		for(OrderItem item : orderItemList) {
			for(OrderProduct op:orderProductList) {
				System.out.println(op);
				if(op.getProductId() == item.getProductId()) {
					item.setOrderProduct(op);
					break;
				}
			}
		}

		orderItemRepository.save(orderItemList);

		System.out.println("브랜드별 배송비 " + deliveryMap);	
		System.out.println("총 가격 " + totalPrice);
		System.out.println("총 수량 " + itemCount);
		System.out.println("배송비 " + delivery);

		orderTotal.setTotalCount(itemCount);
		orderTotal.setTotalPrice(totalPrice);
		orderTotal.setTotalDelivery(delivery);

		orderTotal = orderTotalRepository.save(orderTotal);
		
		
		// 쿠폰 확인
		List<CouponUser> couponUser = couponUserRepository.findByUserAndEndDateAfterAndUseYn(user, new Date(), "N");
		// 사용가능 쿠폰			
		List<OrderCoupon> coupon = new ArrayList<>();
		Coupon cp = null;
		OrderCoupon oc = null;
		for(CouponUser cpUser:couponUser) {
			cp = cpUser.getCoupon();
			if(cp.getMinimum() <= orderTotal.getTotalPrice()) {
				if(cp.getCpType() == 0) { // 일반쿠폰
					if(cp.getMinimum() <= totalPrice) {
						oc = new OrderCoupon();
						oc.setCoupon(cp);
						oc.setOrderTotal(orderTotal);
						oc.setUser(user);
						oc.setOrdProdId(0L);
						if(cp.getSaleType() == 0) {
							// % 할인
							oc.setMaxDis(totalPrice * (cp.getPrice()/100));
						} else {
							// 원 할인
							oc.setMaxDis(cp.getPrice());
						}
						coupon.add(oc);
					}
					
				} else if(cp.getCpType() == 1) { // 중복쿠폰
				} else {
					//2:특별쿠폰-브랜드, 3:특별쿠폰-카테고리, 4:특별쿠폰-제품, 5:제품교환권
					List<SpecialCoupon> spList = specialCouponRepository.findByCpId(cp.getCpId());
					List<Long> spids = new ArrayList<>();
					
					// 적용 가능 상품
					List<Product> prod = null;
					
					for(SpecialCoupon sc:spList) {
						if(cp.getCpType() == 4 || cp.getCpType() == 5) {
							for(Long prodId:prodList) {
								if(prodId == sc.getCtId()) {
									spids.add(sc.getCtId());
								}
							}
						} else {
							spids.add(sc.getCtId());
						}
					}
					
					// 브랜드
					if(cp.getCpType() == 2) {
						List<Brand> brand = brandRepository.findAll(spids);
						prod = productRepository.findByProductIdInAndBrandIn(prodList, brand);
					} else if(cp.getCpType() == 3) {
						List<Category> category = categoryRepository.findAll(spids);
						List<ProductCategory> pc = productCategoryRepository.findByCategoryInAndProductIn(category, pList);
						prod = new ArrayList<>();
						for(ProductCategory p:pc) {
							prod.add(p.getProduct());
						}

					} else if(cp.getCpType() == 4 || cp.getCpType() == 5) {
						prod = productRepository.findAll(spids);
					}
					OrderComparator comp = new OrderComparator();
					Collections.sort(orderProductList, comp);
					
					OrderProduct op = orderProductList.get(0);
					
					if(prod != null && prod.size() > 0) {
						for(Product pd:prod) {
							if(pd.getProductId() == op.getProductId()) {
								if(cp.getMinimum() <= op.getProdPrice()) {
									oc = new OrderCoupon();
									oc.setCoupon(cp);
									oc.setOrderTotal(orderTotal);
									oc.setUser(user);
									oc.setOrdProdId(op.getOrdProdId());
									if(cp.getSaleType() == 0) {
										// % 할인
										oc.setMaxDis(op.getProdPrice() * (cp.getPrice()/100));
									} else {
										// 원 할인
										oc.setMaxDis(cp.getPrice());
									}
									coupon.add(oc);
								}
								break;
							}
						}
					}
				}
			}
		}
		if(coupon.size() > 0) {
			orderCouponRepository.save(coupon);
		}
		return orderTotal;
	}

	@Override
	public OrderResponse getOrderTotal(String orderId,JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());
		if(user != null) {
			OrderTotal orderTotal = orderTotalRepository.findByOrderIdAndUser(orderId, user);
			if(orderTotal == null) {
				return OrderResponse.of("잘못된 주문 정보 입니다.", ResultCode.FAIL, null, null, 0, 0, null, null);
			}
			// 이름
			String username = user.getName();

			// 내 포인트
			int myPoint = 0;
			List<UserPoint> userPoint = userPointRepository.findByUser(user);
			for(UserPoint point:userPoint) {
				myPoint += point.getPoint();
			}

			// 최대 사용 가능 포인트 (주문가의 최대 20%)
			int maxPoint = 0;
			maxPoint = (int)(orderTotal.getTotalPrice() * 0.2);
			//사용가능 포인트가 내 포인트 보다 크면 나의 포인트가 최대 포인트가 된다.
			if(maxPoint > myPoint) {	
				maxPoint = myPoint;
			}


			List<CouponUser> couponUser = couponUserRepository.findByUserAndEndDateAfterAndUseYn(user, new Date(), "N");
			// 사용가능 중복쿠폰
			List<CouponUser> overlap_cp = new ArrayList<>();
			Coupon cp = null;
			for(CouponUser cpUser:couponUser) {
				cp = cpUser.getCoupon();
				if(cp.getMinimum() <= orderTotal.getTotalPrice()) {
					if(cp.getCpType() == 1) { // 중복쿠폰
						overlap_cp.add(cpUser);
					}
				}
			}
		
			// 주소목록
			List<Shipping> shipping = shippingRepository.findFirst3ByUser(user, new Sort(Direction.ASC, "defAddr").and(new Sort(Direction.DESC, "regDate")));

			return OrderResponse.of("success", ResultCode.SUCCESS, username, orderTotal, myPoint, maxPoint, overlap_cp, shipping);
		}
		return OrderResponse.of("회원이 존재하지 않습니다.", ResultCode.SUCCESS, null, null, 0, 0, null, null);
	}


	@Override
	public PaymentResponse orderValid(OrderValid valid, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());
		if(user != null) {
			OrderTotal orderTotal = orderTotalRepository.findByOrderIdAndUser(valid.getOrderId(), user);
			if(orderTotal == null) {
				return PaymentResponse.of("잘못된 주문 정보 입니다.", PaymentResultCode.FAIL, null);
			}
			String paymentId = DateUtil.getDateString("yyyyMMddHHmmssSSS");
			
			Payments payment = new Payments();
			if(orderTotal.getTotalCount() == 1) {
				payment.setName(orderTotal.getPaymentName());
			} else {
				payment.setName(orderTotal.getPaymentName() + "외 " + (orderTotal.getTotalCount()-1) + "건");
			}
			payment.setTotalPayment(valid.getTotalPayment());	// 결제금액
			payment.setStatus("ready");
			payment.setPaymentId(paymentId);
			payment.setNote(valid.getMemo());
			//payment.setImpUid(imp_uid);
			payment.setMerchantUid("bgr_"+DateUtil.getDateLong("yyyyMMddHHmmssSSS")); // 가맹점 주문 ID
			payment.setUser(user);
			payment.setCouponId(valid.getOverlapCpId());		// 중복할인쿠폰 코드
			payment.setCouponDiscount(valid.getOverlapCpDis());	// 중복쿠폰 할인금액
			
			// 내 포인트
			int myPoint = 0;
			List<UserPoint> userPoint = userPointRepository.findByUser(user);
			for(UserPoint point:userPoint) {
				myPoint += point.getPoint();
			}

			// 최대 사용 가능 포인트 (주문가의 최대 20%)
			int maxPoint = 0;
			maxPoint = (int)(orderTotal.getTotalPrice() * 0.2);
			//사용가능 포인트가 내 포인트 보다 크면 나의 포인트가 최대 포인트가 된다.
			if(maxPoint > myPoint) {	
				maxPoint = myPoint;
			}
			
			if(valid.getUsePoint() <= maxPoint) {
				payment.setPointDiscount(valid.getUsePoint());		// 포인트 할인금액
			} else {		// 잘못된 포인트 사용
				return PaymentResponse.of("잘못된 주문 정보 입니다.", PaymentResultCode.FAIL, null);
			}
			payment.setBuyer_addr(valid.getAddress1() + " " + valid.getAddress2());
			payment.setBuyer_name(valid.getName());
			payment.setBuyer_postcode(valid.getZipcode());
			payment.setBuyer_tel(valid.getPhone());
			payment.setBuyer_email(user.getEmail());
			
			AES256Util aes;
			try {
				if(valid.getRefund_holder() != null && !valid.getRefund_holder().equals("") ) {
					payment.setRefund_holder(valid.getRefund_holder());
				}

				if(valid.getRefund_bank() != null && !valid.getRefund_account().equals("")) {
					payment.setRefund_bank(valid.getRefund_bank());
				}

				if(valid.getRefund_account() != null && !valid.getRefund_account().equals("")) {
					aes = new AES256Util(BeautyConstants.IAMPORT_API_SECRET);
					payment.setRefund_account(aes.encrypt(valid.getRefund_account()));
				}
			} catch (UnsupportedEncodingException e) {
				return PaymentResponse.of(e.getMessage(), PaymentResultCode.FAIL, null);
			} catch (GeneralSecurityException e) {
				return PaymentResponse.of(e.getMessage(), PaymentResultCode.FAIL, null);
			}
			
			payment = paymentRepository.save(payment);
			shippingService.save(valid.getName(), valid.getPhone(), valid.getZipcode(), valid.getAddress1(), valid.getAddress2(), "N", token);
			
			
		
			//------------- 상품 목록 수량 & 쿠폰 & 가격 검증 ----------//
			List<PaymentItem> pItemList = new ArrayList<>();
			PaymentItem paymentItem = null;
			ProductItem item = null;
			List<OrderProduct> ordProdList = orderTotal.getOrderProduct();
			OrderCoupon ordCoupon = null;
			if(valid.getOrderCpId() != 0L) {
				ordCoupon = orderCouponRepository.findByOrderCpIdAndUser(valid.getOrderCpId(), user);
			}
			 
			for(OrderProduct ordProd:ordProdList) {
				List<OrderItem> ordItemList = ordProd.getOrderItem();
				for(OrderItem ordItem:ordItemList) {
					paymentItem = new PaymentItem();
					paymentItem.setPayments(payment);
					item = productItemRepository.findOne(ordItem.getItemId());
					paymentItem.setItemId(item);
					paymentItem.setOrderCount(ordItem.getItemCount());
					paymentItem.setDelivery_pay(ordItem.getDelivery());
					if(ordItem.getDelivery() == 0) {
						paymentItem.setDelivery_type(1);
					} else {
						paymentItem.setDelivery_type(0);
					}
					if(ordCoupon != null) {
						if(ordCoupon.getOrdProdId() == item.getProduct().getProductId()) {
							paymentItem.setCouponId(ordCoupon.getCoupon().getCpId());
							paymentItem.setCouponDiscount(ordCoupon.getMaxDis().intValue());
							ordCoupon = null;
						}
					}
					
					paymentItem.setStatus("ready");

					paymentItem.setAmount(item.getPrice());
					paymentItem.setUser(item.getProduct().getSeller());
					paymentItem.setSupply_price(item.getSupply_price());
					paymentItem.setProdName(item.getProduct().getProdName());
					paymentItem.setItemName(item.getItemName());
					paymentItem.setDeliveryCode(item.getProduct().getProductDelivery().getDeliveryCompany().getCode());
					paymentItem.setDeliveryName(item.getProduct().getProductDelivery().getDeliveryCompany().getName());
					
					pItemList.add(paymentItem);
				}
			}
			
			paymentItemRepository.save(pItemList);
			
			return PaymentResponse.of("success", PaymentResultCode.SUCCESS, payment);	
		}
		return PaymentResponse.of("잘못된 접근 입니다.", PaymentResultCode.FAIL, null);
	}
	
	@Override
	@Transactional
	public PaymentResponse verifyPayment(String data, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		// 가맹점ID 
		// b_{pItemId}_{주문시간}
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(data);

			JSONObject jsonObj = (JSONObject) obj;

			//String imp_uid = jsonObj.get("imp_uid").toString();
			//String merchant_uid = jsonObj.get("merchant_uid").toString();			
			String paymentId = DateUtil.getDateString("yyyyMMddHHmmssSSS");
			int totalPayment = Integer.parseInt(jsonObj.get("total_payment").toString()); //결쩨 예정금액
			int couponDis = Integer.parseInt(jsonObj.get("coupon_dis").toString());		// 총 쿠폰 할인금액
			int pointDis = Integer.parseInt(jsonObj.get("point_dis").toString());  	// 총 포인트 할인금액
			String cpId = jsonObj.get("coupon_id").toString();	// 중복 쿠폰
			JSONArray item_list =  (JSONArray) jsonObj.get("item_list");			// 상품 목록

			Object refund_holder = jsonObj.get("refund_holder");
			Object refund_bank = jsonObj.get("refund_bank");
			Object refund_account = jsonObj.get("refund_account");


			if(!pointCheck(pointDis, token)) {
				// 결제 취소
				return PaymentResponse.of("포인트가 부족합니다.", PaymentResultCode.POINT_ERROR, null);
			}


			Payments payment = new Payments();

			payment.setName(jsonObj.get("name").toString());
			payment.setStatus("ready");
			payment.setPaymentId(paymentId);
			payment.setNote(jsonObj.get("note").toString());
			//payment.setImpUid(imp_uid);
			payment.setMerchantUid("bgr_"+DateUtil.getDateLong("yyyyMMddHHmmssSSS"));
			payment.setUser(user);
			payment.setCouponDiscount(couponDis);
			payment.setTotalPayment(totalPayment);
			payment.setPointDiscount(pointDis);

			AES256Util aes;
			try {
				if(refund_holder != null && !refund_account.equals("") ) {
					payment.setRefund_holder(refund_holder.toString());
				}

				if(refund_bank != null && !refund_account.equals("")) {
					payment.setRefund_bank(refund_bank.toString());
				}

				if(refund_account != null && !refund_account.equals("")) {
					aes = new AES256Util(BeautyConstants.IAMPORT_API_SECRET);
					payment.setRefund_account(aes.encrypt(refund_account.toString()));
				}
			} catch (UnsupportedEncodingException e) {
				return PaymentResponse.of(e.getMessage(), PaymentResultCode.FAIL, null);
			} catch (GeneralSecurityException e) {
				return PaymentResponse.of(e.getMessage(), PaymentResultCode.FAIL, null);
			}

			List<PaymentItem> pItemList = new ArrayList<>();
			PaymentItem paymentItem = null;

			//------------- 상품 목록 수량 & 쿠폰 & 가격 검증 ----------// 
			List<Long> itemIds = new ArrayList<>();	// 구매 상품 목록
			Map<Long, PaymentItem> info = new HashMap<>();
			JSONObject itemObject = null;
			ProductItem item = null;
			for (int i=0; i<item_list.size(); i++) {
				itemObject = (JSONObject) item_list.get(i);

				Long id = Long.parseLong(itemObject.get("item_id").toString());
				item = productItemRepository.findOne(id);

				if(item == null) {
					return PaymentResponse.of("잘못된 상품 정보가 존재합니다.", PaymentResultCode.ITEM_ERROR, null);
				}
				int itemCount = Integer.parseInt(itemObject.get("item_count").toString());
				int delivery_pay = Integer.parseInt(itemObject.get("delivery_pay").toString());
				int delivery_type = Integer.parseInt(itemObject.get("delivery_type").toString());

				String couponId = itemObject.get("coupon_id").toString();
				int coupon_dis = Integer.parseInt(itemObject.get("coupon_dis").toString());

				paymentItem = new PaymentItem();

				if(item.getItemCnt() < itemCount) {
					// 결제 취소
					return PaymentResponse.of(item.getItemName() + " 상품 수량이 부족합니다. (남은수량 : " + item.getItemCnt() + ")", PaymentResultCode.ITEM_ERROR, null);
				}

				paymentItem.setItemId(item);
				paymentItem.setOrderCount(itemCount);
				paymentItem.setDelivery_pay(delivery_pay);
				paymentItem.setDelivery_type(delivery_type);
				paymentItem.setCouponId(couponId);
				paymentItem.setStatus("ready");
				paymentItem.setCouponDiscount(coupon_dis);

				paymentItem.setAmount(item.getPrice());
				paymentItem.setUser(item.getProduct().getSeller());
				paymentItem.setSupply_price(item.getSupply_price());
				paymentItem.setProdName(item.getProduct().getProdName());
				paymentItem.setItemName(item.getItemName());
				paymentItem.setDeliveryCode(item.getProduct().getProductDelivery().getDeliveryCompany().getCode());
				paymentItem.setDeliveryName(item.getProduct().getProductDelivery().getDeliveryCompany().getName());

				info.put(id, paymentItem);
				itemIds.add(id);

			}

			// 상품이 없을 경우
			//			List<ProductItem> pi = productItemRepository.findAll(itemIds);
			//			if(pi.size() != itemIds.size()) {
			//				// 결제 취소
			//				return PaymentResponse.of("잘못된 상품 정보가 존재합니다.", PaymentResultCode.ITEM_ERROR, null);
			//			}

			// 수량이 없을 경우
			//			for(ProductItem item:pi) {
			//				if(item.getItemCnt() < info.get(item.getItemId()).getOrderCount()) {
			//					// 결제 취소
			//					return PaymentResponse.of(item.getItemName() + " 상품 수량이 부족합니다. (남은수량 : " + item.getItemCnt() + ")", PaymentResultCode.ITEM_ERROR, null);
			//				} else {
			//					int price = item.getPrice();
			//					PaymentItem tmpItem = info.get(item.getItemId());
			//
			//					tmpItem.setAmount(price);
			//					tmpItem.setUser(item.getProduct().getSeller());
			//					tmpItem.setSupply_price(item.getSupply_price());
			//					tmpItem.setProdName(item.getProduct().getProdName());
			//					tmpItem.setItemName(item.getItemName());
			//					tmpItem.setDeliveryCode(item.getProduct().getProductDelivery().getDeliveryCompany().getCode());
			//					tmpItem.setDeliveryName(item.getProduct().getProductDelivery().getDeliveryCompany().getName());
			//					info.put(item.getItemId(), tmpItem);
			//
			//				}
			//			}

			if(pointDis > 0L) {
				//	if((amount < 25000 && pointDis > 5000) || amount < pointDis) {
				// 결제 취소
				//		return PaymentResponse.of("포인트 사용조건에 맞지 않습니다.", PaymentResultCode.POINT_ERROR, null);
				//	}

				//	int discount = (int)(amount * 0.2);
				//	if(discount == pointDis) {

				//	} else {
				// 결제 취소
				//		return PaymentResponse.of("포인트 사용조건에 맞지 않습니다.", PaymentResultCode.POINT_ERROR, null);
				//	}

			} 

			// 일반쿠폰 & 특별쿠폰 계산

			// paymentITem에 할인금액 및 구매금액 Set


			// 중복쿠폰
			if(!cpId.equals("")) {
				Coupon coupon = couponRepository.findOne(cpId);
				if(coupon == null) {
					// 결제 취소
					return PaymentResponse.of("잘못된 쿠폰 정보입니다.", PaymentResultCode.COUPON_ERROR, null);
				} else {
					List<CouponUser> userPoint = couponUserRepository.findByUserAndCouponAndUseYn(user, coupon, "N");
					if(userPoint == null) {
						// 결제 취소
						return PaymentResponse.of("잘못된 쿠폰 정보입니다.", PaymentResultCode.COUPON_ERROR, null);
					} else {
						payment.setCouponId(cpId);
					}
				}
			}

			payment = paymentRepository.save(payment);

			Iterator<Long> keys = info.keySet().iterator();
			while( keys.hasNext() ){
				Long key = keys.next();
				PaymentItem pi = info.get(key);
				pi.setPayments(payment);
				pItemList.add(pi);
			}

			paymentItemRepository.save(pItemList);

			return PaymentResponse.of("success", PaymentResultCode.SUCCESS, payment);	
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
			return PaymentResponse.of("Parsing Error", PaymentResultCode.FAIL, null);
		} catch (Exception e) {
			e.printStackTrace();
			return PaymentResponse.of("Error Fail", PaymentResultCode.FAIL, null);
		}

	}
	@Override
	@Transactional
	public PaymentResponse verifyPaymentV2(String data, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		// 가맹점ID 
		// b_{pItemId}_{주문시간}
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(data);

			JSONObject jsonObj = (JSONObject) obj;

			//String imp_uid = jsonObj.get("imp_uid").toString();
			//String merchant_uid = jsonObj.get("merchant_uid").toString();			
			String paymentId = DateUtil.getDateString("yyyyMMddHHmmssSSS");
			int totalPayment = Integer.parseInt(jsonObj.get("total_payment").toString()); //결쩨 예정금액
			int couponDis = Integer.parseInt(jsonObj.get("coupon_dis").toString());		// 총 쿠폰 할인금액
			int pointDis = Integer.parseInt(jsonObj.get("point_dis").toString());  	// 총 포인트 할인금액
			String cpId = jsonObj.get("coupon_id").toString();	// 중복 쿠폰
			int normalCouponDis = Integer.parseInt(jsonObj.get("normal_coupon_dis").toString());		// 일반 쿠폰 할인금액
			String normalCpId = jsonObj.get("normal_coupon_id").toString();	// 중복 쿠폰
			JSONArray item_list =  (JSONArray) jsonObj.get("item_list");			// 상품 목록

			Object refund_holder = jsonObj.get("refund_holder");
			Object refund_bank = jsonObj.get("refund_bank");
			Object refund_account = jsonObj.get("refund_account");


			if(!pointCheck(pointDis, token)) {
				// 결제 취소
				return PaymentResponse.of("포인트가 부족합니다.", PaymentResultCode.POINT_ERROR, null);
			}


			Payments payment = new Payments();

			payment.setName(jsonObj.get("name").toString());
			payment.setStatus("ready");
			payment.setPaymentId(paymentId);
			payment.setNote(jsonObj.get("note").toString());
			//payment.setImpUid(imp_uid);
			payment.setMerchantUid("bgr_"+DateUtil.getDateLong("yyyyMMddHHmmssSSS"));
			payment.setUser(user);
			payment.setCouponDiscount(couponDis);
			payment.setTotalPayment(totalPayment);
			payment.setPointDiscount(pointDis);
			payment.setNormalCouponDiscount(normalCouponDis);
			payment.setNormalCouponId(normalCpId);

			AES256Util aes;
			try {
				if(refund_holder != null && !refund_account.equals("") ) {
					payment.setRefund_holder(refund_holder.toString());
				}

				if(refund_bank != null && !refund_account.equals("")) {
					payment.setRefund_bank(refund_bank.toString());
				}

				if(refund_account != null && !refund_account.equals("")) {
					aes = new AES256Util(BeautyConstants.IAMPORT_API_SECRET);
					payment.setRefund_account(aes.encrypt(refund_account.toString()));
				}
			} catch (UnsupportedEncodingException e) {
				return PaymentResponse.of(e.getMessage(), PaymentResultCode.FAIL, null);
			} catch (GeneralSecurityException e) {
				return PaymentResponse.of(e.getMessage(), PaymentResultCode.FAIL, null);
			}

			List<PaymentItem> pItemList = new ArrayList<>();
			PaymentItem paymentItem = null;

			//------------- 상품 목록 수량 & 쿠폰 & 가격 검증 ----------// 
			List<Long> itemIds = new ArrayList<>();	// 구매 상품 목록
			Map<Long, PaymentItem> info = new HashMap<>();
			JSONObject itemObject = null;
			ProductItem item = null;
			for (int i=0; i<item_list.size(); i++) {
				itemObject = (JSONObject) item_list.get(i);

				Long id = Long.parseLong(itemObject.get("item_id").toString());
				item = productItemRepository.findOne(id);

				if(item == null) {
					return PaymentResponse.of("잘못된 상품 정보가 존재합니다.", PaymentResultCode.ITEM_ERROR, null);
				}
				int itemCount = Integer.parseInt(itemObject.get("item_count").toString());
				int delivery_pay = Integer.parseInt(itemObject.get("delivery_pay").toString());
				int delivery_type = Integer.parseInt(itemObject.get("delivery_type").toString());

				paymentItem = new PaymentItem();

				if(item.getItemCnt() < itemCount) {
					// 결제 취소
					return PaymentResponse.of(item.getItemName() + " 상품 수량이 부족합니다. (남은수량 : " + item.getItemCnt() + ")", PaymentResultCode.ITEM_ERROR, null);
				}

				paymentItem.setItemId(item);
				paymentItem.setOrderCount(itemCount);
				paymentItem.setDelivery_pay(delivery_pay);
				paymentItem.setDelivery_type(delivery_type);
				paymentItem.setStatus("ready");

				paymentItem.setAmount(item.getPrice());
				paymentItem.setUser(item.getProduct().getSeller());
				paymentItem.setSupply_price(item.getSupply_price());
				paymentItem.setProdName(item.getProduct().getProdName());
				paymentItem.setItemName(item.getItemName());
				paymentItem.setDeliveryCode(item.getProduct().getProductDelivery().getDeliveryCompany().getCode());
				paymentItem.setDeliveryName(item.getProduct().getProductDelivery().getDeliveryCompany().getName());

				info.put(id, paymentItem);
				itemIds.add(id);

			}

			if(pointDis > 0L) {
				//	if((amount < 25000 && pointDis > 5000) || amount < pointDis) {
				// 결제 취소
				//		return PaymentResponse.of("포인트 사용조건에 맞지 않습니다.", PaymentResultCode.POINT_ERROR, null);
				//	}

				//	int discount = (int)(amount * 0.2);
				//	if(discount == pointDis) {

				//	} else {
				// 결제 취소
				//		return PaymentResponse.of("포인트 사용조건에 맞지 않습니다.", PaymentResultCode.POINT_ERROR, null);
				//	}

			} 

			// 일반쿠폰 & 특별쿠폰 계산

			// 중복쿠폰
			if(!cpId.equals("")) {
				Coupon coupon = couponRepository.findOne(cpId);
				if(coupon == null) {
					// 결제 취소
					return PaymentResponse.of("잘못된 쿠폰 정보입니다.", PaymentResultCode.COUPON_ERROR, null);
				} else {
					List<CouponUser> userPoint = couponUserRepository.findByUserAndCouponAndUseYn(user, coupon, "N");
					if(userPoint == null) {
						// 결제 취소
						return PaymentResponse.of("잘못된 쿠폰 정보입니다.", PaymentResultCode.COUPON_ERROR, null);
					}
				}
			}

			if(!normalCpId.equals("")) {
				Coupon coupon = couponRepository.findOne(normalCpId);
				if(coupon == null) {
					// 결제 취소
					return PaymentResponse.of("잘못된 쿠폰 정보입니다.", PaymentResultCode.COUPON_ERROR, null);
				} else {
					List<CouponUser> userPoint = couponUserRepository.findByUserAndCouponAndUseYn(user, coupon, "N");
					if(userPoint == null) {
						// 결제 취소
						return PaymentResponse.of("잘못된 쿠폰 정보입니다.", PaymentResultCode.COUPON_ERROR, null);
					}
				}
			}

			payment = paymentRepository.save(payment);

			Iterator<Long> keys = info.keySet().iterator();
			while( keys.hasNext() ){
				Long key = keys.next();
				PaymentItem pi = info.get(key);
				pi.setPayments(payment);
				pItemList.add(pi);
			}

			paymentItemRepository.save(pItemList);

			return PaymentResponse.of("success", PaymentResultCode.SUCCESS, payment);	
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
			return PaymentResponse.of("Parsing Error", PaymentResultCode.FAIL, null);
		} catch (Exception e) {
			e.printStackTrace();
			return PaymentResponse.of("Error Fail", PaymentResultCode.FAIL, null);
		}

	}

	@Override
	@Transactional
	public PaymentResponse complete(String payment_id, String imp_uid, String merchant_uid, String name, String phone, String zipcode, String address1, String address2, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user == null) {
			return PaymentResponse.of("User Not Found", PaymentResultCode.FAIL, null);
		}

		Payments payment = paymentRepository.findByPaymentIdAndUser(payment_id, user);

		payment.setImpUid(imp_uid);
		payment.setMerchantUid(merchant_uid);
		if(payment.getTotalPayment() == 0) {
			payment.setBuyer_name(name);
			payment.setBuyer_email("");
			payment.setBuyer_tel(phone);
			payment.setBuyer_addr(address1 + " " + address2);
			payment.setBuyer_postcode(zipcode);
			payment.setStatus("paid");
			paymentRepository.save(payment);
			paymentItemRepository.updateStatus(payment.getStatus(), payment);
			paymentComplete(payment);

			shippingService.save(name, phone, zipcode, address1, address2, "N", token);
			return PaymentResponse.of("success", PaymentResultCode.SUCCESS, payment);
		} 

		IamportResponse<Payment> payment_response = payment(imp_uid);
		if(payment_response == null) {
			// 취소 처리
			return PaymentResponse.of("Error Fail", PaymentResultCode.FAIL, null);	
		}

		if(payment_response.getCode() == 0) {
			Payment pt = payment_response.getResponse();

			payment.setPay_method(pt.getPayMethod());
			payment.setPg_provider(pt.getPgProvider());
			payment.setPg_tid(pt.getPgTid());
			payment.setEscrow(pt.isEscrow());
			payment.setApply_num(pt.getApplyNum());
			payment.setCard_name(pt.getCardName());
			payment.setCard_quota(pt.getCardQuota());
			payment.setVbank_name(pt.getVbankName());
			payment.setVbank_num(pt.getVbankNum());
			payment.setVbank_holder(pt.getVbankHolder());
			payment.setVbank_date( pt.getVbankDate());
			payment.setName(pt.getName());
			payment.setAmount(pt.getAmount());
			payment.setCancel_amount(pt.getCancelAmount());
			payment.setPaid_at(pt.getPaidAt());
			payment.setFailed_at(pt.getFailedAt());
			payment.setCancelled_at(pt.getCancelledAt());
			payment.setFail_reason(pt.getFailReason());
			payment.setCancel_reason(pt.getCancelReason());
			payment.setReceipt_url(pt.getReceiptUrl());
			payment.setStatus(pt.getStatus());

			if(pt.getStatus().equals("paid") && pt.getAmount().equals(new BigDecimal(payment.getTotalPayment()))) {
				//&& pt.getAmount().equals(new BigDecimal(1004))
				payment.setBuyer_name(name);
				payment.setBuyer_email(pt.getBuyerEmail());
				payment.setBuyer_tel(pt.getBuyerTel());
				payment.setBuyer_addr(pt.getBuyerAddr());
				payment.setBuyer_postcode(pt.getBuyerPostcode());
				payment.setTotalPayment(Integer.valueOf(pt.getAmount().intValue()));

				paymentRepository.save(payment);

				paymentItemRepository.updateStatus(payment.getStatus(), payment);
				paymentComplete(payment);

				//pointSave(payment, false);

				shippingService.save(name, phone, zipcode, address1, address2, "N", token);
				return PaymentResponse.of(payment_response.getMessage(), PaymentResultCode.SUCCESS, payment);
			} else if(pt.getStatus().equals("ready") &&  pt.getPayMethod().equals("vbank")) {
				payment.setBuyer_name(name);
				payment.setBuyer_email(pt.getBuyerEmail());
				payment.setBuyer_tel(pt.getBuyerTel());
				payment.setBuyer_addr(pt.getBuyerAddr());
				payment.setBuyer_postcode(pt.getBuyerPostcode());
				payment.setTotalPayment(Integer.valueOf(pt.getAmount().intValue()));

				paymentRepository.save(payment);

				paymentItemRepository.updateStatus(payment.getStatus(), payment);
				paymentComplete(payment);

				shippingService.save(name, phone, zipcode, address1, address2, "N", token);


				return PaymentResponse.of(payment_response.getMessage(), PaymentResultCode.SUCCESS, payment);
			} else {
				// 취소 처리

				paymentRepository.save(payment);
				paymentItemRepository.updateStatus(payment.getStatus(), payment);
				return PaymentResponse.of("Error Fail", PaymentResultCode.FAIL, null);	
			}

		} else {
			// 취소 처리
			paymentRepository.save(payment);
			paymentItemRepository.updateStatus(payment.getStatus(), payment);
			return PaymentResponse.of("Error Fail", PaymentResultCode.FAIL, null);
		}
	}

	@Override
	public PaymentResponse cancel(String payment_id, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user == null) {
			return PaymentResponse.of("User Not Found", PaymentResultCode.FAIL, null);
		}


		Payments payment = paymentRepository.findByPaymentIdAndUser(payment_id, user);

		if(paymentItemRepository.countByPaymentsAndProdConfirm(payment, 1) > 0L) {
			return PaymentResponse.of("배송 준비된 상품이 존재합니다.", PaymentResultCode.FAIL, payment);
		}

		if(payment.getTotalPayment() == 0) {
			payment.setStatus("cancelled");
			paymentRepository.save(payment);
			paymentItemRepository.updateStatus(payment.getStatus(), payment);

			if(payment.getPointYn() == 1) {
				pointSave(payment, true);
			}
			paymentCancel(payment);
			return PaymentResponse.of("success", PaymentResultCode.SUCCESS, payment);
		} else {
			String cancelled_imp_uid = payment.getImpUid();
			IamportClient client = new IamportClient(BeautyConstants.IAMPORT_API_KEY, BeautyConstants.IAMPORT_API_SECRET);
			IamportResponse<Payment> payment_response = null;
			if(payment.getStatus().equals("ready") && (payment.getVbank_num() != null || !payment.getVbank_num().equals("")) ) {
				payment_response = client.cancelVBankByImpUid(cancelled_imp_uid);
				if(payment_response.getResponse() == null) {
					return PaymentResponse.of("취소할 가상계좌 건이 존재하지 않습니다.", PaymentResultCode.FAIL, null);
				}	
			} else {


				if(payment.getVbank_num() != null && !payment.getVbank_num().equals("")) {

					//					AES256Util aes;
					//					try {
					//						aes = new AES256Util(BeautyConstants.IAMPORT_API_SECRET);
					//						cancel_data.setRefund_account(aes.decrypt(payment.getRefund_account()));
					//					} catch (Exception e) {
					//						return  PaymentResponse.of(e.getMessage(), PaymentResultCode.FAIL, null);
					//					} 					
					//					
					//					cancel_data.setRefund_holder(payment.getRefund_holder());
					//					cancel_data.setRefund_bank(payment.getRefund_bank());

					payment.setStatus("cancelled");
					paymentRepository.save(payment);
					paymentItemRepository.updateStatus(payment.getStatus(), payment);

					if(payment.getPointYn() == 1) {
						pointSave(payment, true);
					}
					paymentCancel(payment);
					return PaymentResponse.of("success", PaymentResultCode.SUCCESS, payment);

				}
				CancelData cancel_data = new CancelData(cancelled_imp_uid, true); //imp_uid를 통한 전액취소
				payment_response = client.cancelPaymentByImpUid(cancel_data);
				if(payment_response.getResponse() == null) {
					return PaymentResponse.of("이미 취소 되었습니다.", PaymentResultCode.FAIL, null);
				}
			}
			Payment pt = payment_response.getResponse();
			payment.setPay_method(pt.getPayMethod());
			payment.setPg_provider(pt.getPgProvider());
			payment.setPg_tid(pt.getPgTid());
			payment.setEscrow(pt.isEscrow());
			payment.setApply_num(pt.getApplyNum());
			payment.setCard_name(pt.getCardName());
			payment.setCard_quota(pt.getCardQuota());
			payment.setVbank_name(pt.getVbankName());
			payment.setVbank_num(pt.getVbankNum());
			payment.setVbank_holder(pt.getVbankHolder());
			payment.setVbank_date( pt.getVbankDate());
			payment.setName(pt.getName());
			payment.setAmount(pt.getAmount());
			payment.setCancel_amount(pt.getCancelAmount());
			payment.setPaid_at(pt.getPaidAt());
			payment.setFailed_at(pt.getFailedAt());
			payment.setCancelled_at(pt.getCancelledAt());
			payment.setFail_reason(pt.getFailReason());
			payment.setCancel_reason(pt.getCancelReason());
			payment.setReceipt_url(pt.getReceiptUrl());
			payment.setStatus(pt.getStatus());

			paymentItemRepository.updateStatus(payment.getStatus(), payment);
			paymentRepository.save(payment);

			if(payment.getPointYn() == 1) {
				pointSave(payment, true);
			}
			paymentCancel(payment);
			return PaymentResponse.of(payment_response.getMessage(), PaymentResultCode.SUCCESS, payment);
		}

	}

	@Transactional
	public void paymentCancel(Payments payment) {
		payment = paymentRepository.findOne(payment.getPaymentId());
		if(payment.getPaidProc() == 1) {
			if(payment.getCouponId() != null) {
				Coupon coupon = couponRepository.findOne(payment.getCouponId());
				List<CouponUser> cp = couponUserRepository.findByUserAndCouponAndUseYn(payment.getUser(), coupon, "Y");
				for(CouponUser cu:cp) {
					cu.setUseYn("N");
					cu.setUseDate(DateUtil.getDate());
				}
				couponUserRepository.save(cp);
			}
			List<PaymentItem> itemList = paymentItemRepository.findByPayments(payment);

			List<String> cpIds = new ArrayList<>();
			List<ProductItem> piList = new ArrayList<>();
			List<Product> products = new ArrayList<>();
			Product prod = null;
			for(PaymentItem item:itemList) {
				if(item.getCouponId() != null) {
					cpIds.add(item.getCouponId());
				}

				if(item.getItemId() != null) {
					//ProductItem pi = productItemRepository.findOne(item.getItemId());

					ProductTimeSale pts = productTimeSaleRepository.findByProductAndTimeType(item.getItemId().getProduct(), 1);
					if(pts != null) {
						pts.setProdCount(pts.getProdCount() + item.getOrderCount());
						productTimeSaleRepository.save(pts);
					}

					item.getItemId().setItemCnt(item.getItemId().getItemCnt() + item.getOrderCount());
					piList.add(item.getItemId());
					prod = item.getItemId().getProduct();
					prod.setScore(prod.getScore() - 10L);
					products.add(prod);
				}
			}

			productRepository.save(products);

			if(payment.getPointDiscount() > 0) {
				UserPoint up = new UserPoint();
				up.setNote("[구매취소]"+payment.getName());
				up.setUser(payment.getUser());
				up.setPoint(payment.getPointDiscount());

				userPointRepository.save(up);	
			}


			productItemRepository.save(piList);

			List<Coupon> cpList = couponRepository.findAll(cpIds);
			if(cpList != null && cpList.size() > 0) {
				List<CouponUser> cpUserList = couponUserRepository.findByUserAndCouponIn(payment.getUser(), cpList);
				for(CouponUser cu:cpUserList) {
					cu.setUseYn("N");
					cu.setUseDate(DateUtil.getDate());
				}
				couponUserRepository.save(cpUserList);
			}
		}
		//		User user = payment.getUser();
		//		if(user.getFirstCoupon().equals("Y")) {
		//			couponService.autoCoupon(user, 2);
		//			userRepository.save(user);
		//		}
		//		Long count = paymentRepository.countByUser(payment.getUser());
		//		if(count == 1) {
		//			couponService.autoCoupon(payment.getUser(), 2);
		//		}
	}

	public void pointSave(Payments payment, boolean minor) {
		User user = payment.getUser();
		int point = (int) (Integer.valueOf(payment.getAmount().intValue()) * 0.01);

		UserPoint userPoint = new UserPoint();
		if(minor) {
			userPoint.setNote("[구매1%적립취소]" + payment.getName());
			userPoint.setPoint(-point); 
		} else {
			userPoint.setNote("[구매1%적립]" + payment.getName());
			userPoint.setPoint(point);
		}
		userPoint.setUser(user);


		userPointRepository.save(userPoint);
	}

	/**
	 * 사용 포인트를 가지고 있는지 체크
	 * @param point
	 * @param token
	 * @return
	 */
	public boolean pointCheck(int point, JwtAuthenticationToken token) {
		Long myPoint = 0L;

		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			List<UserPoint> userPoint = userPointRepository.findByUser(user);
			for(UserPoint up:userPoint) {
				myPoint += up.getPoint();
			}

			if(point <= myPoint) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	public IamportResponse<Payment> payment(String imp_uid) {
		IamportClient client = new IamportClient(BeautyConstants.IAMPORT_API_KEY, BeautyConstants.IAMPORT_API_SECRET);
		IamportResponse<Payment> payment_response = client.paymentByImpUid(imp_uid);
		return payment_response;

	}

	@Transactional
	public void paymentComplete(Payments payment) {
		payment = paymentRepository.findOne(payment.getPaymentId());
		if(payment.getPaidProc() == 0) {
			payment.setPaidProc(1);
			paymentRepository.save(payment);
			if(payment.getCouponId() != null) {
				Coupon coupon = couponRepository.findOne(payment.getCouponId());
				List<CouponUser> cp = couponUserRepository.findByUserAndCouponAndUseYn(payment.getUser(), coupon, "N");
				for(CouponUser cu:cp) {
					cu.setUseYn("Y");
					cu.setUseDate(DateUtil.getDate());
				}
				couponUserRepository.save(cp);
			}
			List<PaymentItem> itemList = paymentItemRepository.findByPayments(payment);

			List<String> cpIds = new ArrayList<>();
			List<ProductItem> piList = new ArrayList<>();
			List<Product> products = new ArrayList<>();
			Product prod = null;
			for(PaymentItem item:itemList) {
				if(item.getCouponId() != null) {
					cpIds.add(item.getCouponId());
				}

				if(item.getItemId() != null) {
					//				ProductItem pi = productItemRepository.findOne(item.getItemId());

					ProductTimeSale pts = productTimeSaleRepository.findByProductAndTimeType(item.getItemId().getProduct(), 1);
					if(pts != null) {
						pts.setProdCount(pts.getProdCount() - item.getOrderCount());
						productTimeSaleRepository.save(pts);
					}

					item.getItemId().setItemCnt(item.getItemId().getItemCnt() - item.getOrderCount());
					piList.add(item.getItemId());
					prod = item.getItemId().getProduct();
					prod.setScore(prod.getScore() + 10L);
					products.add(prod);
				}
			}

			productRepository.save(products);

			if(payment.getPointDiscount() > 0) {
				UserPoint up = new UserPoint();
				up.setNote("[결제사용]"+payment.getName());
				up.setUser(payment.getUser());
				up.setPoint(payment.getPointDiscount()*-1);

				userPointRepository.save(up);	
			}


			productItemRepository.save(piList);

			List<Coupon> cpList = couponRepository.findAll(cpIds);
			if(cpList != null && cpList.size() > 0) {
				List<CouponUser> cpUserList = couponUserRepository.findByUserAndCouponIn(payment.getUser(), cpList);
				for(CouponUser cu:cpUserList) {
					cu.setUseYn("Y");
					cu.setUseDate(DateUtil.getDate());
				}
				couponUserRepository.save(cpUserList);
			}

			User user = payment.getUser();
			if(user.getFirstCoupon().equals("N")) {
				couponService.autoCoupon(user, 2);
				user.setFirstCoupon("Y");
				userRepository.save(user);
			}


		}
		//
		//		if(!payment.getStatus().equals("ready")) {
		//			Long count = paymentRepository.countByUser(payment.getUser());
		//			if(count == 1L) {
		//				couponService.autoCoupon(payment.getUser(), 2);
		//			}
		//		}
	}

	@Override
	public List<Payments> payment(JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		return paymentRepository.findAll(PaymentSpecification.payment(user), new Sort(Direction.DESC, "regDate"));
	}

	@Override
	public List<PaymentItem> paymentItem(String payment_id, JwtAuthenticationToken token) {
		Payments payment = paymentRepository.findOne(payment_id);
		return paymentItemRepository.findByPayments(payment);
	}

	@Override
	public String iamportComplete(String imp_uid, String merchant_uid, String status) {
		List<Payments> payment = paymentRepository.findByImpUidAndMerchantUid(imp_uid, merchant_uid);
		for(Payments p:payment) {
			if(!status.equals(p.getStatus())) {
				p.setStatus(status);
				if(status.equals("paid")) { 
					paymentComplete(p);
				} else if(status.equals("cancelled")) {
					paymentCancel(p);
				}
			}
			//ready:미결제, paid:결제완료, cancelled:결제취소, failed:
			//			p.setStatus(status);
		}
		if(status.equals("failed") || status.equals("ready")) {
			paymentRepository.save(payment);
		}

		//		List<PaymentItem> items = paymentItemRepository.findByPaymentsIn(payment);
		//		for(PaymentItem item : items) {
		//			item.setStatus(status);
		//		}
		//		paymentItemRepository.save(items);

		return "success";
	}

	@Override
	public Payments getPayments(String payment_id) {
		return paymentRepository.findOne(payment_id);
	}

	@Override
	public void save(Payments payment, String name, String phone, String zipcode, String address1, String address2) {
		User user = userRepository.findOne(payment.getUser().getUserId());
		shippingService.save(name, phone, zipcode, address1, address2, "N", user);
		paymentRepository.save(payment);

	}

	@Override
	public PaymentResponse complete(String payment_id, String imp_uid, String merchant_uid, JwtAuthenticationToken token) {
		return complete(payment_id, imp_uid, merchant_uid);
	}
	
	@Override
	public PaymentResponse complete(String payment_id, String imp_uid, String merchant_uid) {
		Payments payment = paymentRepository.findOne(payment_id);

		payment.setImpUid(imp_uid);
		payment.setMerchantUid(merchant_uid);
		if(payment.getTotalPayment() == 0) {

			payment.setStatus("paid");
			paymentRepository.save(payment);
			paymentItemRepository.updateStatus(payment.getStatus(), payment);
			paymentComplete(payment);

			//			shippingService.save(name, phone, zipcode, address1, address2, "N", token);
			return PaymentResponse.of("success", PaymentResultCode.SUCCESS, payment);
		} 

		IamportResponse<Payment> payment_response = payment(imp_uid);
		if(payment_response == null) {
			// 취소 처리
			return PaymentResponse.of("Error Fail", PaymentResultCode.FAIL, null);	
		}

		if(payment_response.getCode() == 0) {
			Payment pt = payment_response.getResponse();

			payment.setPay_method(pt.getPayMethod());
			payment.setPg_provider(pt.getPgProvider());
			payment.setPg_tid(pt.getPgTid());
			payment.setEscrow(pt.isEscrow());
			payment.setApply_num(pt.getApplyNum());
			payment.setCard_name(pt.getCardName());
			payment.setCard_quota(pt.getCardQuota());
			payment.setVbank_name(pt.getVbankName());
			payment.setVbank_num(pt.getVbankNum());
			payment.setVbank_holder(pt.getVbankHolder());
			payment.setVbank_date( pt.getVbankDate());
			payment.setName(pt.getName());
			payment.setAmount(pt.getAmount());
			payment.setCancel_amount(pt.getCancelAmount());
			payment.setPaid_at(pt.getPaidAt());
			payment.setFailed_at(pt.getFailedAt());
			payment.setCancelled_at(pt.getCancelledAt());
			payment.setFail_reason(pt.getFailReason());
			payment.setCancel_reason(pt.getCancelReason());
			payment.setReceipt_url(pt.getReceiptUrl());
			payment.setStatus(pt.getStatus());

			if(pt.getStatus().equals("paid") && pt.getAmount().equals(new BigDecimal(payment.getTotalPayment()))) {
				//&& pt.getAmount().equals(new BigDecimal(1004))
				//				payment.setBuyer_name(name);
				//				payment.setBuyer_email(pt.getBuyerEmail());
				//				payment.setBuyer_tel(pt.getBuyerTel());
				//				payment.setBuyer_addr(pt.getBuyerAddr());
				//				payment.setBuyer_postcode(pt.getBuyerPostcode());
				payment.setTotalPayment(Integer.valueOf(pt.getAmount().intValue()));

				paymentRepository.save(payment);

				paymentItemRepository.updateStatus(payment.getStatus(), payment);
				paymentComplete(payment);

				//pointSave(payment, false);

				//				shippingService.save(name, phone, zipcode, address1, address2, "N", token);
				return PaymentResponse.of(payment_response.getMessage(), PaymentResultCode.SUCCESS, payment);
			} else if(pt.getStatus().equals("ready") &&  pt.getPayMethod().equals("vbank")) {
				//				payment.setBuyer_name(name);
				//				payment.setBuyer_email(pt.getBuyerEmail());
				//				payment.setBuyer_tel(pt.getBuyerTel());
				//				payment.setBuyer_addr(pt.getBuyerAddr());
				//				payment.setBuyer_postcode(pt.getBuyerPostcode());
				payment.setTotalPayment(Integer.valueOf(pt.getAmount().intValue()));

				paymentRepository.save(payment);

				paymentItemRepository.updateStatus(payment.getStatus(), payment);
				paymentComplete(payment);

				//				shippingService.save(name, phone, zipcode, address1, address2, "N", token);


				return PaymentResponse.of(payment_response.getMessage(), PaymentResultCode.SUCCESS, payment);
			} else {
				// 취소 처리

				paymentRepository.save(payment);
				paymentItemRepository.updateStatus(payment.getStatus(), payment);
				return PaymentResponse.of("Error Fail", PaymentResultCode.FAIL, null);	
			}

		} else {
			// 취소 처리
			paymentRepository.save(payment);
			paymentItemRepository.updateStatus(payment.getStatus(), payment);
			return PaymentResponse.of("Error Fail", PaymentResultCode.FAIL, null);
		}
	}


}
class OrderComparator implements Comparator<OrderProduct> {
	@Override
	public int compare(OrderProduct first, OrderProduct second) {
		int firstValue = first.getProdPrice();
		int secondValue = second.getProdPrice();
		
		// Order by descending 
		if (firstValue > secondValue) {
			return -1;
		} else if (firstValue < secondValue) {
			return 1;
		} else {
			return 0;
		}
	}
}