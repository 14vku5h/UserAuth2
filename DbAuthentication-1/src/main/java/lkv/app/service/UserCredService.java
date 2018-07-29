package lkv.app.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import lkv.app.model.UserCredential;



public interface UserCredService extends UserDetailsService {

UserDetails loadUserByUsername(String email);
	
	UserCredential updatePassword(String email, String password);
}
