package lkv.app.service.impl;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lkv.app.model.UserCredential;
import lkv.app.repository.UserCredRepository;
import lkv.app.service.UserCredService;



@Service
public class UserCredServiceImpl implements UserCredService{

	private static final Logger LOGGER = LoggerFactory.getLogger(UserCredServiceImpl.class);	
	
	@Autowired
	private UserCredRepository userCredDao;
	
	//@Autowired
	//private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
		@Override
	public UserDetails loadUserByUsername(String userName) {
		UserCredential userCredential = userCredDao.getUserByUserName(userName);
		LOGGER.info("@@@@@@@@@@@@@@@Going to login with  User Name @@@@@@@@@@@@@@@@{}",userName);
		GrantedAuthority authority = new SimpleGrantedAuthority(userCredential.getRole().toString());
		org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(userCredential.getUserName(), userCredential.getPassword(), Arrays.asList(authority));
		UserDetails userDetails = (UserDetails)user;
		LOGGER.info("@@@@@@@@@@@@@@@ Returning from loadUserByUsername @@@@@@@@@@@@@@@@{}",userName);
		return userDetails;
	}

		
		@Override
		public UserCredential updatePassword(String email, String password) {
			LOGGER.info("@@@@@@@@@@@@@@@Going to Update Password @@@@@@@@@@@@@@@@{}",email);
			UserCredential userCred = userCredDao.getUserByUserName(email);
			if(userCred != null){				
				//userCred.setPassword(bCryptPasswordEncoder.encode(password));
				//LOGGER.info("@@@@@@@@@@@@@ Decrypting Details @@@@@@@@@@@{}",bCryptPasswordEncoder.d);
				userCredDao.updateUserCred(userCred);
				return userCred;
			}
			return null;
		}
	
	
		
	
	
}