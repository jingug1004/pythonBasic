package com.beauty.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beauty.entity.AppBasket;
import com.beauty.entity.AppBasketProduct;
import com.beauty.entity.AppBasketProductItem;
import com.beauty.entity.AppBasketSeller;
import com.beauty.entity.Product;
import com.beauty.entity.ProductItem;
import com.beauty.entity.User;
import com.beauty.repository.AppBasketProductItemRepository;
import com.beauty.repository.AppBasketProductRepository;
import com.beauty.repository.AppBasketRepository;
import com.beauty.repository.AppBasketSellerRepository;
import com.beauty.repository.ProductItemRepository;
import com.beauty.repository.UserRepository;
import com.beauty.response.AppBasketResponse;
import com.beauty.response.result.ResultCode;
import com.beauty.security.auth.JwtAuthenticationToken;
import com.beauty.security.model.UserContext;

@Service
public class AppBasketServiceImpl implements AppBasketService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AppBasketRepository basketRepository;

	@Autowired
	private AppBasketSellerRepository basketSellerRepository;
	
	@Autowired
	private AppBasketProductRepository basketProductRepository;
	
	@Autowired
	private AppBasketProductItemRepository basketProductItemRepository;
	
	
	@Autowired
	private ProductItemRepository productItemRepository;

	
	@Override
	public AppBasketResponse getBasketList(JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());
		if(user != null) {
			AppBasket basket = basketRepository.findByUser(user);
			return AppBasketResponse.of("success", ResultCode.SUCCESS, soldOutBasket(basket));
		}
		return AppBasketResponse.of("User Not Found", ResultCode.FAIL, null);
	}

	@Override
	public AppBasketResponse basketAdd(Long itemId, int itemCount, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			ProductItem productItem = productItemRepository.findOne(itemId);
			if(productItem == null) {
				return AppBasketResponse.of("Product Item Not Found", ResultCode.FAIL, null);
			}
			
			if(itemCount > productItem.getMaxItemCnt()) {			// 최대 구입수 보다 많은 경우
				itemCount = productItem.getMaxItemCnt();
			} else if(itemCount > productItem.getItemCnt()) {		//남은 수량보다 많은 경우
				itemCount = productItem.getItemCnt();
			}
			
			Product product = productItem.getProduct();
			
			AppBasket basket = basketRepository.findByUser(user);
			if(basket == null) {
				basket = new AppBasket();
				basket.setUser(user);
				basket = basketRepository.save(basket);	// 장바구니 생성			
			}
			
			AppBasketSeller basketSeller =  basketSellerRepository.findByBasketAndSeller(basket, product.getSeller());
			if(basketSeller == null) {
				basketSeller = new AppBasketSeller();
				basketSeller.setBasket(basket);
				basketSeller.setSeller(product.getSeller());
				basketSeller.setBrand(product.getBrand());
				basketSeller = basketSellerRepository.save(basketSeller);
			}

			AppBasketProduct basketProduct = basketProductRepository.findByProductAndBasketSeller(product, basketSeller);
			if(basketProduct == null) {
				basketProduct = new AppBasketProduct();
				basketProduct.setBasketSeller(basketSeller);
				basketProduct.setProduct(product);
				int totalPrice = productItem.getPrice() * itemCount;
				basketProduct = basketProductRepository.save(basketProduct);
				
				AppBasketProductItem basketItem = new AppBasketProductItem();
				basketItem.setBasketProduct(basketProduct);
				basketItem.setBasketCount(itemCount);
				basketItem.setProductItem(productItem);
				basketItem.setTotalPrice(totalPrice);
				basketItem.setPrice(productItem.getPrice());
				basketProductItemRepository.save(basketItem);

			} else {
				AppBasketProductItem basketItem = basketProductItemRepository.findByBasketProductAndProductItem(basketProduct, productItem);
				int totalPrice = productItem.getPrice() * itemCount;
				if(basketItem == null) {
					basketItem = new AppBasketProductItem();
				}
				basketItem.setBasketProduct(basketProduct);
				basketItem.setBasketCount(itemCount);
				basketItem.setProductItem(productItem);
				basketItem.setTotalPrice(totalPrice);
				basketItem.setPrice(productItem.getPrice());
				basketProductItemRepository.save(basketItem);
				
			}

//			basket = basketRepository.findByUser(user);
			return AppBasketResponse.of("success", ResultCode.SUCCESS, basket);
		}
		return AppBasketResponse.of("User Not Found", ResultCode.FAIL, null);
	}
	
	@Override
	public AppBasketResponse basketUpd(Long bpi_id, int itemCount, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			AppBasketProductItem basketItem = basketProductItemRepository.findOne(bpi_id);
			
			if(!basketItem.getBasketProduct().getBasketSeller().getBasket().getUser().getUserId().equals(user.getUserId())) {
				return AppBasketResponse.of("User Not Found", ResultCode.FAIL, null);
			}
			
			ProductItem productItem = basketItem.getProductItem();
			if(itemCount > productItem.getMaxItemCnt()) {			// 최대 구입수 보다 많은 경우
				itemCount = productItem.getMaxItemCnt();
			} else if(itemCount > productItem.getItemCnt()) {		//남은 수량보다 많은 경우
				itemCount = productItem.getItemCnt();
			}
			
			int totalPrice = productItem.getPrice() * itemCount;
			basketItem.setBasketCount(itemCount);
			basketItem.setTotalPrice(totalPrice);
			basketItem.setPrice(productItem.getPrice());
			basketProductItemRepository.save(basketItem);
			return AppBasketResponse.of("success", ResultCode.SUCCESS, null);
		}
		return AppBasketResponse.of("User Not Found", ResultCode.FAIL, null);
	}

	@Override
	public AppBasketResponse basketDelete(Long[] bpi_id, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());
		if(user != null) {
			List<Long> ids = new ArrayList<Long>();
			for(Long bpi:bpi_id) {
				ids.add(bpi);
			}
			List<AppBasketProductItem> items = basketProductItemRepository.findAll(ids);
			basketProductItemRepository.delete(items);
			
			for(AppBasketProductItem item:items) {
				AppBasketProduct basketProduct = basketProductRepository.findOne(item.getBasketProduct().getBpId());
				if(basketProduct != null) {
					if(basketProduct.getBasketProductItem() == null || basketProduct.getBasketProductItem().size() == 0) {
						basketProductRepository.delete(basketProduct);
					}
					
					AppBasketSeller basketSeller = basketSellerRepository.findOne(basketProduct.getBasketSeller().getSellerId());
					if(basketSeller != null) {
						if(basketSeller.getBasketProduct() == null  || basketSeller.getBasketProduct().size() == 0) {
							basketSellerRepository.delete(basketSeller);
						}
						
						AppBasket basket = basketRepository.findOne(basketSeller.getBasket().getBasketId());
						if(basket.getAppBasketSeller() == null || basket.getAppBasketSeller().size() == 0) {
							basketRepository.delete(basket);
						}
						
					}
				} 
			}
//			AppBasket basket = basketRepository.findByUser(user);
//			List<AppBasketSeller> sellers = basket.getAppBasketSeller();
//			List<AppBasketProduct> products = null;
//			for(AppBasketSeller seller:sellers) {
//				products = seller.getBasketProduct();
//				if(products == null || products.size() == 0) {
//					basketSellerRepository.delete(seller);
//				} else {
//					for(AppBasketProduct basketProduct:products) {
//						if(basketProduct.getBasketProductItem() == null) {
//							basketProductRepository.delete(basketProduct);
//						}
//					}
//					
//				}
//			}
//			
			
			AppBasket basket = basketRepository.findByUser(user);
			

			return AppBasketResponse.of("success", ResultCode.SUCCESS, soldOutBasket(basket));
		}
		return AppBasketResponse.of("User Not Found", ResultCode.FAIL, null);
	}

	public AppBasket soldOutBasket(AppBasket basket) {
		if(basket != null) {
			for(AppBasketSeller seller : basket.getAppBasketSeller()) {
				int prodSoldOut = 0;
				boolean sold = false;
				for(AppBasketProduct prod:seller.getBasketProduct()) {
					if(prod.getProduct().getStopSelling() == 1) {
						prod.setSoldOut(1);		// 상품 품절
						prodSoldOut += 1;
						for(AppBasketProductItem item:prod.getBasketProductItem()) {
							item.setSoldOut(1);		// 품절
						}
					} else {
						int itemSoldOut = 0;
						for(AppBasketProductItem item:prod.getBasketProductItem()) {
							if(item.getProductItem().getItemCnt() <= 0 || item.getProductItem().getStopSelling() == 1) {
								item.setSoldOut(1);		// 품절
								itemSoldOut += 1;
							} else {
								item.setSoldOut(0);		// 판매중
							}
						}
						if(prod.getBasketProductItem().size() == itemSoldOut) {
							prod.setSoldOut(1);		// 상품 품절
							prodSoldOut += 1;
						} else if(itemSoldOut > 0) {
							prod.setSoldOut(2);		// 품절상품 존재
							sold = true;
//							prodSoldOut += 1;
						} else {
							prod.setSoldOut(0);		// 품절 없음
						}
					}
				}
				if(prodSoldOut == seller.getBasketProduct().size()) {
					seller.setSoldOut(1);       // 브랜드 전체 품절
				} else if(sold) {
					seller.setSoldOut(2); 		// 일부 품절
				} else {
					seller.setSoldOut(0); 		// 판매중
				}
			}
		}
		
		return basket;
	}
}
