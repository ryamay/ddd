package com.yamari.idddd.domain.models.Users;

class User {
	public UserId id;
	public UserName name;
		
	public User(UserId id, UserName name) {
		this.id = id;
		this.name = name;
	}
}
