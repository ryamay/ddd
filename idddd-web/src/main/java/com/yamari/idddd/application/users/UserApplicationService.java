package com.yamari.idddd.application.users;

import com.yamari.idddd.domain.models.users.IUserRepository;
import com.yamari.idddd.domain.models.users.User;
import com.yamari.idddd.domain.models.users.UserId;
import com.yamari.idddd.domain.models.users.UserName;
import com.yamari.idddd.domain.services.UserService;

public class UserApplicationService {
	
	private IUserRepository userRepository;
	private UserService userService;
	
	public UserApplicationService(IUserRepository userRepository, UserService userService) {
		this.userRepository =userRepository; 
		this.userService = userService;
	}
	
	public void CreateUser (String userName) throws Exception {
		User user = new User(new UserName(userName));
		
		UserService userService = new UserService(userRepository);
		if (userService.exists(user)) {
			throw new Exception(userName + "ÇÕä˘Ç…ë∂ç›ÇµÇƒÇ¢Ç‹Ç∑ÅB");
		}
		
		userRepository.save(user);
	}
	
	public User get(String userId)	{
		UserId targetId = new UserId(userId);
		return userRepository.find(targetId);
	}
}
