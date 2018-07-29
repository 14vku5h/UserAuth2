package lkv.app.service;

import lkv.app.model.Users;
import lkv.app.zException.CustomeException;

public interface UsersService {
	public void create(Users user) throws CustomeException;
	public Users getUserByEmail(String email);
	public void updateUser(Users user);

}
