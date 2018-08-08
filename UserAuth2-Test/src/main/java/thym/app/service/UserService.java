package thym.app.service;

import thym.app.model.User;

public interface UserService {
	
	public void saveUser(User user);
	
	public User findByEmail(String email);

	public User findByConfirmationToken(String token);

	public void updateUser(User user);

}
