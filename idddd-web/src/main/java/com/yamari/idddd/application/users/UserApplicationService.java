package com.yamari.idddd.application.users;

import com.yamari.idddd.domain.models.users.IUserRepository;
import com.yamari.idddd.domain.models.users.User;
import com.yamari.idddd.domain.models.users.UserName;
import com.yamari.idddd.domain.services.UserService;

public class UserApplicationService {
	
	private IUserRepository userRepository;
	
	public UserApplicationService(IUserRepository userRepository) {
		this.userRepository =userRepository; 
	}
	
	public void CreateUser (String userName) throws Exception {
		User user = new User(new UserName(userName));
		
		UserService userService = new UserService(userRepository);
		if (userService.exists(user)) {
			throw new Exception(userName + "ÇÕä˘Ç…ë∂ç›ÇµÇƒÇ¢Ç‹Ç∑ÅB");
		}
		
		userRepository.save(user);
		
	}
}
