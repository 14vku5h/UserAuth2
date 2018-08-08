package thym.app.service.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import thym.app.model.UserCredential;
import thym.app.repository.CredRepository;
import thym.app.service.CredentialService;

@Service
public class CredentialServiceImpl  implements CredentialService{
		@Autowired
	   CredRepository credRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserCredential credential = credRepo.getCredentialByUsername(username);
		
		GrantedAuthority authority = new SimpleGrantedAuthority(credential.getRole().toString());
		
		User user = new User(credential.getUsername(),credential.getPassword(), Arrays.asList(authority));
		
		UserDetails userDetails = (UserDetails)user;
		
		return userDetails;
	}

}
