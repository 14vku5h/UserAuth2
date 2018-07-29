package lkv.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lkv.app.model.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
	
	public Users findByEmail(String email);
	

}
