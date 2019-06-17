package thym.app.service.impl;



import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thym.app.model.Role;
import thym.app.model.User;
import thym.app.repository.UserRepository;
import thym.app.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
		@Autowired
		UserRepository usrRepo;
		
		
	@Override
	public User findByEmail(String email) {
		 User usr =  usrRepo.getByEmail(email);
		if(usr!=null) {
		if(usr.getImage() != null) {
		 usr.setBase64Image(Base64.getEncoder().encodeToString(usr.getImage()));
		 
		 }
		}
		 System.out.println("returning user <UserServiceImpl.findByEmail(email)>");
		return usr; 
	}

	@Override
	public void saveUser(User user) {
		user.getCredentials().setUsername(user.getEmail());
		user.getCredentials().setRole(Role.USER);
		usrRepo.save(user);
	}

	@Override
	public User findByConfirmationToken(String token) {
		return usrRepo.getByConfirmationToken(token);
	}

	@Override
	public void updateUser(User user) {
		usrRepo.save(user);
	}

}
