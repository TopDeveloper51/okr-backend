package com.apis.okre.controller;

import com.apis.okre.util.Constants;
import com.apis.okre.util.JwtTokenUtil;
import com.apis.okre.util.Utils;
import com.apis.okre.entity.Kanban;
import com.apis.okre.entity.Krobject;
import com.apis.okre.entity.User;
import com.apis.okre.entity.UserRequest;
import com.apis.okre.mapper.UserMapper;
import com.apis.okre.service.JwtUserDetailsService;
import com.apis.okre.service.UserService;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
/**
 * login and signup, SSO(single sign on),api health
 * @author alex
 *
 */
@RestController
@SpringBootApplication
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper userMapper;

	// @Autowired
	// private Validator validator;

	@CrossOrigin
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody UserRequest authenticationRequest, BindingResult bindingResult) {
		try {
			if(bindingResult.hasErrors()) {
				return new ResponseEntity<String>(
						Utils.getResponseObjectError(Constants.VALIDATION_ERROR, bindingResult.getFieldErrors()).toString(),
						HttpStatus.BAD_REQUEST);
			}
			
			User param = new User();
			if(authenticationRequest.userPhone.contains("@")) {
				param.email = authenticationRequest.userPhone;
			}else {
				param.phone = authenticationRequest.userPhone;
			}

			List<User> userlist = userService.selectByFields(param);

			if (userlist.size() == 0) {
				if(authenticationRequest.userPhone.contains("@")) {
					throw new UsernameNotFoundException(Constants.NOT_FOUND_EMAIL + authenticationRequest.userPhone);
				}else {
					throw new UsernameNotFoundException(Constants.NOT_FOUND_PHONE + authenticationRequest.userPhone);
				}
			}

			final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.userPhone);

			if (!bcryptEncoder.matches(authenticationRequest.userPassword, userDetails.getPassword())) {
				return new ResponseEntity<String>(Constants.WRONG_PASSWORD, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
			}

			Authentication authentication = null;
			try {
				authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
						authenticationRequest.userPhone, authenticationRequest.userPassword));
			} catch (AuthenticationException e) {
				System.out.println(e.getMessage());
			}

			SecurityContextHolder.getContext().setAuthentication(authentication);

			final String token = jwtTokenUtil.generateToken(userDetails);
			userlist.get(0).user_password = "";
			userlist.get(0).user_api_token = token;
			try {
				userlist.get(0).user_sso = jwtTokenUtil.encryptAES(userlist.get(0).loginName);
			}catch(Exception e) {
				
			}
			
			return ResponseEntity.ok(userlist.get(0));

		} catch (UsernameNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}
	
	@CrossOrigin
	@RequestMapping(value = "/sso", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getOneObjectById(@RequestParam String username) {
		try {
			
			User param = new User();
			try {
				param.loginName = jwtTokenUtil.decryptAES(username);
			}catch(Exception e) {
				return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
						HttpStatus.BAD_REQUEST);
			}
			if(param.loginName==null) {
				throw new UsernameNotFoundException(Constants.NOT_FOUND_EMAIL);
			}
			
			List<User> userlist = userService.selectByFields(param);
			
			if (userlist.size() == 0) {
				throw new UsernameNotFoundException(Constants.NOT_FOUND_EMAIL);
			}
			
			if (userlist.size() >1) {
				throw new UsernameNotFoundException(Constants.NOT_FOUND_EMAIL);
			}

			final UserDetails userDetails = userDetailsService.loadUserByUsername(userlist.get(0).phone);

			Authentication authentication = null;
			try {
				authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
						userlist.get(0).phone, username));
			} catch (AuthenticationException e) {
				System.out.println(e.getMessage());
			}

			SecurityContextHolder.getContext().setAuthentication(authentication);

			final String token = jwtTokenUtil.generateToken(userDetails);
			userlist.get(0).user_password = "";
			userlist.get(0).user_api_token = token;
			userlist.get(0).user_sso = username;
			
			return ResponseEntity.ok(userlist.get(0));
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin
	@RequestMapping(value = "/api/health", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> health() {
		try {
			// test db connection by using user table
			User param = new User();
			param.id = 1L;
			List<User> userlist = userService.selectByFields(param);
			return new ResponseEntity<String>(Constants.HEALTH_COMMENT, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(Constants.DB_HEALTH_COMMENT, HttpStatus.BAD_REQUEST);
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> addOne(@RequestBody User param) {
		try {
			Long ret = userService.addOne(param);
			if (ret == 0) {
				return new ResponseEntity<String>(Utils.getResponseObject(Constants.INSERT_FAILED).toString(),
						HttpStatus.BAD_REQUEST);
			} else if (ret == 2L) {
				return new ResponseEntity<String>(Utils.getResponseObject(Constants.PHONE_DUPLICATED).toString(),
						HttpStatus.BAD_REQUEST);
			} else {
				User newObj = new User();
				newObj.id = param.id;
				List<User> retObj = userService.selectByFields(newObj);
				return new ResponseEntity<String>(
						Utils.getResponseObject(Constants.INSERT_SUCCESSFULLY, retObj.get(0)).toString(),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	
//	public ResponseEntity<?> addUser(@RequestBody User userObj) {
//		try {
//			Long ret = 0L;
//			ret = userService.addOne(userObj);
//			if (ret == 0L) {
//				return new ResponseEntity<String>(Utils.getResponseObject(Constants.INSERT_FAILED).toString(),
//						HttpStatus.BAD_REQUEST);
//			} else if (ret == 2L) {
//				return new ResponseEntity<String>(Utils.getResponseObject(Constants.PHONE_DUPLICATED).toString(),
//						HttpStatus.BAD_REQUEST);
//			} else {
//				// User newObj = new User();
//				// newObj.id = userObj.id;
//				// List<User> retObj = userService.selectByFields(newObj);
//				return new ResponseEntity<String>(Utils.getResponseObject(Constants.ADD_USER_SUCCESSFULLY).toString(),
//						HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			return new ResponseEntity<String>(Utils.getResponseObject(e.getMessage()).toString(),
//					HttpStatus.BAD_REQUEST);
//		}
//	}
	
}
