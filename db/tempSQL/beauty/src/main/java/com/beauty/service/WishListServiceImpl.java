package com.beauty.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beauty.entity.Product;
import com.beauty.entity.User;
import com.beauty.entity.Wishlist;
import com.beauty.repository.ProductRepository;
import com.beauty.repository.UserRepository;
import com.beauty.repository.WishlistRepository;
import com.beauty.response.WishListResponse;
import com.beauty.response.result.ResultCode;
import com.beauty.security.auth.JwtAuthenticationToken;
import com.beauty.security.model.UserContext;

@Service
public class WishListServiceImpl implements WishListService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private WishlistRepository wishlistRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public WishListResponse list(JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			List<Wishlist> wishlist = wishlistRepository.findByUserOrderByRegDateDesc(user);
			return WishListResponse.of("success", ResultCode.SUCCESS, wishlist);
		}
		return WishListResponse.of("User Not Found", ResultCode.FAIL, null);
	}

	@Override
	public WishListResponse add(Long productId, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			Product product = productRepository.findOne(productId);
			if(product == null) {
				return WishListResponse.of("Product Not Found", ResultCode.FAIL, null);
			}
			
			Wishlist wishlist = wishlistRepository.findByUserAndProduct(user, product);
			if(wishlist == null) {
				wishlist = new Wishlist();
				wishlist.setProduct(product);
				wishlist.setUser(user);
			}
			wishlistRepository.save(wishlist);
			
			List<Wishlist> wishList = wishlistRepository.findByUserOrderByRegDateDesc(user);
			return WishListResponse.of("success", ResultCode.SUCCESS, wishList);
		}
		return WishListResponse.of("User Not Found", ResultCode.FAIL, null);
	}

	@Override
	public WishListResponse delete(Long wishId, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			wishlistRepository.deleteByUserAndWishId(user, wishId);
			List<Wishlist> wishList = wishlistRepository.findByUserOrderByRegDateDesc(user);
			
			return WishListResponse.of("success", ResultCode.SUCCESS, wishList);
		}
		return WishListResponse.of("User Not Found", ResultCode.FAIL, null);
	}

	@Override
	public WishListResponse check(Long productId, JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			Product product = productRepository.findOne(productId);
			if(product == null) {
				return WishListResponse.of("Product Not Found", ResultCode.FAIL, null);
			}
			
			Wishlist wishlist = wishlistRepository.findByUserAndProduct(user, product);
			if(wishlist == null) {
				return WishListResponse.of("WishList Not Found", ResultCode.FAIL, null);
			} else {
				return WishListResponse.of("success", ResultCode.SUCCESS, null);
			}
		}
		return WishListResponse.of("User Not Found", ResultCode.FAIL, null);
	}




}
