package lkv.app.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lkv.app.controller.UsersController;
import lkv.app.model.Role;
import lkv.app.model.UserCredential;
import lkv.app.model.Users;
import lkv.app.repository.UsersRepository;
import lkv.app.service.UsersService;
import lkv.app.zException.CustomeException;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {
	
	private static final Logger log = LoggerFactory.getLogger(UsersController.class);
	@Autowired
	private UsersRepository usersDao;
	
	//@Autowired
	//private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void create(Users user) throws CustomeException {

		log.info("Persisting Users  Details");
		UserCredential  userCredential = user.getUserCredential();
		if(userCredential !=null){
			if(!userCredential.getPassword().equals(userCredential.getConfirmPassword())){
				log.error("UsersServiceImpl.create(): Password Mismatch throwing exception");
				throw new CustomeException("userCredential.password", "Password Mismatch");			
			}
			//userCredential.setPassword(bCryptPasswordEncoder.encode(userCredential.getPassword()));
			userCredential.setRole(Role.USER);
		}
		usersDao.save(user);
		
	}

	@Override
	public Users getUserByEmail(String email) {
		log.info("UsersServiceImpl.getPatien() getting user by email ");
		return usersDao.findByEmail(email);
	}

	@Override
	public void updateUser(Users user) {
		log.info("UsersService.updateUser() updating user");
		usersDao.save(user);
		
	}

}
