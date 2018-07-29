package lkv.app.bootload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import lkv.app.model.UserCredential;
import lkv.app.model.Users;
import lkv.app.service.UsersService;
import lkv.app.zException.CustomeException;


@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UsersService service;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("Data Loading...");
		UserCredential c = new UserCredential();
		Users lkv = new Users();
		lkv.setFirstName("Lavkush");
		lkv.setLastName("Verma");
		lkv.setEmail("vermalavkush46@gmail.com");
		lkv.setGender("Male");
		lkv.setMobNo("8010719583");
			c.setUserName("vermalavkush46@gmail.com");
			c.setPassword("alliswell");
			c.setConfirmPassword("alliswell");
		lkv.setUserCredential(c);
		
		try {
			service.create(lkv);
		} catch (CustomeException e) {
			System.out.println("DataLoader error :failed to create user ");
		}
		
		//service.addStudent(new Users("Lavkush Verma","vermalavkush46@gmail.com","alliswell","ROLE_USER"));
		//service.addStudent(new Users("Suraj","ksuraj1002@gmail.com","allthebest","ROLE_USER"));
		//System.out.println("done.");
		
	}
	

}
