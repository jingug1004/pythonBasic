package com.beauty.security.auth.ajax;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.beauty.common.PasswordEncoding;
import com.beauty.entity.User;
import com.beauty.entity.UserSns;
import com.beauty.security.exceptions.MemberLeaveException;
import com.beauty.security.model.UserContext;
import com.beauty.service.UserServiceImpl;


/**
 * 
 * @author vladimir.stankovic
 *
 * Aug 3, 2016
 */
@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {
	private final UserServiceImpl userService;

	@Autowired
	public AjaxAuthenticationProvider(final UserServiceImpl userService) {
		this.userService = userService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.notNull(authentication, "No authentication data provided");

		String id = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		User user = null;
		if(password.equals("K") || password.equals("N") || password.equals("F")) {
			UserSns userSns = userService.getById(id, password).orElseThrow(() -> new UsernameNotFoundException("User not found: " + id));
			user = userService.getById(userSns.getUser().getUserId());
			if(user == null) {
				throw new UsernameNotFoundException("User not found: " + id);
			}
		} else {
			user = userService.getById(id);
			if(user == null) {
				throw new UsernameNotFoundException("User not found: " + id);
			}
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			PasswordEncoding passwordEncoding = new PasswordEncoding(passwordEncoder);
//			System.out.println(password);
//			System.out.println(user.getPassword());
			if (!passwordEncoding.matches(password, user.getPassword())) {
				throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
			}
		}
//		Optional<User> user = userService.getById(id);
//		if(user.isPresent()) {
//			// Admin일경우
//			if(!user.get().getJoinType().equals("A") || !user.get().getJoinType().equals("D")) {
//				throw new UsernameNotFoundException("User not found: " + id);
//			} else {
//				PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//				PasswordEncoding passwordEncoding = new PasswordEncoding(passwordEncoder);
//				if (!passwordEncoding.matches(password, user.get().getPassword())) {
//					throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
//				}
//			}
//		} else {
//			UserSns userSns = userService.getById(id, password).orElseThrow(() -> new UsernameNotFoundException("User not found: " + id));
//			user = userService.getById(userSns.getUser().getUserId());
//			if(!user.isPresent()) {
//				throw new UsernameNotFoundException("User not found: " + id);
//			}
//		}


		if(user.getDelYn().equals("Y")) {
			throw new MemberLeaveException("member leave  " + id);
		}

		if (user.getRoles() == null) throw new InsufficientAuthenticationException("User has no roles assigned");

		//        for(UserRole userRole : user.getRoles()) {
		//        	if(userRole.getRole().authority().equals(Role.BLACKLIST.authority())) {
		//        		throw new BlackListException("User has blacklist roles assigned");
		//        	}
		//        }

		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
				.collect(Collectors.toList());

		UserContext userContext = UserContext.create(user.getUserId(), authorities);

		return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
}
