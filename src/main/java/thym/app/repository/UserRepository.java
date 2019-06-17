package thym.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import thym.app.model.User;

public interface UserRepository extends  JpaRepository<User, Long>{
	
	User getByEmail(String email);
	User getByConfirmationToken(String token);
	

}
