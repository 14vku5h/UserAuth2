
package lkv.app.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import lkv.app.model.UserCredential;
import lkv.app.repository.UserCredRepository;

@Repository
@Transactional
public class UserCredRepositoryImpl implements UserCredRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public UserCredential getUserByUserName(String userName) {
		UserCredential userCredential = new UserCredential();
		Integer enabled = 1;
		List<?>list =  entityManager.createQuery("Select u from UserCredential u where userName=?1 and isActive=?2").setParameter(1 , userName).setParameter(2, enabled).getResultList();
		if(!list.isEmpty()){			
			userCredential = (UserCredential) list.get(0);
		}
		return userCredential;
	}


	
	@Override
	public void updateUserCred(UserCredential userCred) {
		
		entityManager.persist(userCred);
	}
	
}
