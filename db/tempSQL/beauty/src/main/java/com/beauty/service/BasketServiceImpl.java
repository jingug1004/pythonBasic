package com.beauty.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beauty.entity.BasketProductItem;
import com.beauty.entity.Product;
import com.beauty.entity.ProductItem;
import com.beauty.entity.ShoppingBasket;
import com.beauty.entity.User;
import com.beauty.repository.BasketRepository;
import com.beauty.repository.ProductItemRepository;
import com.beauty.repository.UserRepository;
import com.beauty.response.BasketResponse;
import com.beauty.response.result.ResultCode;
import com.beauty.security.auth.JwtAuthenticationToken;
import com.beauty.security.model.UserContext;

@Service
public class BasketServiceImpl implements BasketService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BasketRepository basketRepository;

	@Autowired
	private ProductItemRepository productItemRepository;

	@Override
	public BasketResponse getBasketList(JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());
		if(user != null) {
			List<ShoppingBasket> basketList = basketRepository.findByUserOrderByRegDateDesc(user);

//			List<Basket> bList = new ArrayList<>();
			


			return BasketResponse.of("success", ResultCode.SUCCESS, getBasket(basketList));
		}
		return BasketResponse.of("User Not Found", ResultCode.FAIL, null);
	}

	@Override
	public BasketResponse basketAdd(Long itemId, int itemCount, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			ProductItem productItem = productItemRepository.findOne(itemId);
			if(productItem == null) {
				return BasketResponse.of("Product Item Not Found", ResultCode.FAIL, null);
			}

			ShoppingBasket basket = basketRepository.findByUserAndProductItem(user, productItem);
			if(basket == null) {
				basket = new ShoppingBasket();
				basket.setProductItem(productItem);
				basket.setUser(user);
			}
			if(productItem.getItemCnt() >= itemCount) {
				basket.setItemCount(itemCount);
			} else {
				basket.setItemCount(productItem.getItemCnt());
			}
			basketRepository.save(basket);

			List<ShoppingBasket> basketList = basketRepository.findByUserOrderByRegDateDesc(user);
			return BasketResponse.of("success", ResultCode.SUCCESS, getBasket(basketList));
		}
		return BasketResponse.of("User Not Found", ResultCode.FAIL, null);
	}

	@Override
	public BasketResponse basketUpd(Long basketId, int itemCount, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			ShoppingBasket basket = basketRepository.findByUserAndBasketId(user, basketId);
			if(basket.getProductItem().getItemCnt() >= itemCount) {
				basket.setItemCount(itemCount);
			} else {
				basket.setItemCount(basket.getProductItem().getItemCnt());
			}
			basketRepository.save(basket);

			List<ShoppingBasket> basketList = basketRepository.findByUserOrderByRegDateDesc(user);
			return BasketResponse.of("success", ResultCode.SUCCESS, getBasket(basketList));
		}
		return BasketResponse.of("User Not Found", ResultCode.FAIL, null);
	}

	@Override
	public BasketResponse basketDelete(Long basketId, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());
		if(user != null) {
			basketRepository.deleteByUserAndBasketId(user, basketId);
			List<ShoppingBasket> basketList = basketRepository.findByUserOrderByRegDateDesc(user);

			return BasketResponse.of("success", ResultCode.SUCCESS, getBasket(basketList));
		}
		return BasketResponse.of("User Not Found", ResultCode.FAIL, null);
	}



	
	public Map<Long, Map<Long, List<BasketProductItem>>> getBasket(List<ShoppingBasket> basketList) {
		List<BasketProductItem> bpItemList = new ArrayList<>();
		BasketProductItem basketProductItem = null;
		Product product = null;
		Map<Long, Map<Long, List<BasketProductItem>>> map = new HashMap<>();
		Map<Long, List<BasketProductItem>> mm = new HashMap<>();
		for(ShoppingBasket sb : basketList) {
			if(sb.getProductItem().getStopSelling() == 0) {
				
				product = sb.getProductItem().getProduct();

				if(map.get(product.getBrand().getBrandId()) != null) {
					mm = map.get(product.getBrand().getBrandId());
				} else {
					mm = new HashMap<>(); 
				}
				
				if(mm.get(product.getProductId()) == null) {
					bpItemList = new ArrayList<>();
				} else {
					bpItemList = mm.get(product.getProductId());
				}
				basketProductItem = new BasketProductItem();
				basketProductItem.setItemId(sb.getProductItem().getItemId());
				basketProductItem.setItemName(sb.getProductItem().getItemName());
				basketProductItem.setMaxItemCnt(sb.getProductItem().getMaxItemCnt());
				basketProductItem.setPrice(sb.getProductItem().getPrice());
				basketProductItem.setTotalItemCnt(sb.getProductItem().getTotalItemCnt());
				basketProductItem.setItemCnt(sb.getProductItem().getItemCnt());
				basketProductItem.setProductId(product.getProductId());
				basketProductItem.setBrandId(product.getBrand().getBrandId());
				basketProductItem.setProdName(product.getProdName());
				basketProductItem.setProdDesc(product.getProdDesc());
				basketProductItem.setThumbUrl(product.getThumbUrl());
				basketProductItem.setSale(product.getSale());
				basketProductItem.setSalePrice(product.getSalePrice());
				basketProductItem.setProductDelivery(product.getProductDelivery());
				basketProductItem.setBrand(product.getBrand());
				basketProductItem.setBasketId(sb.getBasketId());
				basketProductItem.setItemCount(sb.getItemCount());
				basketProductItem.setStopSelling(product.getStopSelling());
				bpItemList.add(basketProductItem);
				mm.put(product.getProductId(), bpItemList);
			
				map.put(product.getBrand().getBrandId(), mm);
				//bpItemList.add(basketProductItem);
			}
		}
		
		return map;
	}
}
