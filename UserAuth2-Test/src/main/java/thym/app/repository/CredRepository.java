package thym.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import thym.app.model.UserCredential;

public interface CredRepository extends JpaRepository<UserCredential,Long>{
  
	 UserCredential getCredentialByUsername(String username);
}
