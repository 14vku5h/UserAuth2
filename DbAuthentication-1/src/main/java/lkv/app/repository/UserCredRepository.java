
package lkv.app.repository;

import lkv.app.model.UserCredential;

public interface UserCredRepository{
	
    UserCredential getUserByUserName(String email);
    void updateUserCred(UserCredential userCred);


}
