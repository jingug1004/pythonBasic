package com.beauty.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.beauty.entity.Shipping;
import com.beauty.entity.User;
import com.beauty.repository.ShippingRepository;
import com.beauty.repository.UserRepository;
import com.beauty.response.ShippingResponse;
import com.beauty.response.result.ResultCode;
import com.beauty.security.auth.JwtAuthenticationToken;
import com.beauty.security.model.UserContext;

@Service
public class ShippingServiceImpl implements ShippingService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ShippingRepository shippingRepository;

	@Override
	public ShippingResponse info(JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			Shipping shipping = shippingRepository.findByUserAndDefAddr(user, "Y");
			return ShippingResponse.of("success", ResultCode.SUCCESS, shipping);
		}
		return ShippingResponse.of("User Not Found", ResultCode.FAIL, null);
	}

	@Override
	public ShippingResponse save(String name, String phone, String zipcode, String address1, String address2, String def_addr,
			JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			Shipping shipping = null;
			if(def_addr.equals("Y")) {
				shipping = shippingRepository.findByUserAndDefAddr(user, "Y");

			}

			if(shipping == null) {
				shipping = new Shipping();	
			}
			shipping.setName(name);
			shipping.setAddress1(address1);
			shipping.setAddress2(address2);
			shipping.setZipcode(zipcode);
			shipping.setPhone(phone);
			shipping.setUser(user);
			shipping.setDefAddr(def_addr);

			shippingRepository.save(shipping);

			return ShippingResponse.of("success", ResultCode.SUCCESS, shipping);
		}
		return ShippingResponse.of("User Not Found", ResultCode.FAIL, null);
	}

	@Override
	public ShippingResponse save(String name, String phone, String zipcode, String address1, String address2, String def_addr,
			User user) {
		Shipping shipping = null;
		if(def_addr.equals("Y")) {
			shipping = shippingRepository.findByUserAndDefAddr(user, "Y");

		}

		if(shipping == null) {
			shipping = new Shipping();	
		}
		shipping.setName(name);
		shipping.setAddress1(address1);
		shipping.setAddress2(address2);
		shipping.setZipcode(zipcode);
		shipping.setPhone(phone);
		shipping.setUser(user);
		shipping.setDefAddr(def_addr);

		shippingRepository.save(shipping);

		return ShippingResponse.of("success", ResultCode.SUCCESS, shipping);
	}


	@Override
	public List<Shipping> list(JwtAuthenticationToken token) {
		UserContext userContext = (UserContext)token.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());

		if(user != null) {
			return shippingRepository.findFirst3ByUser(user, new Sort(Direction.ASC, "defAddr").and(new Sort(Direction.DESC, "regDate")));
		}

		return null;
	}


}
