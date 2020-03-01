package com.yamari.idddd.domain.models.users;

public interface IUserRepository
{
	void save(User user);
	User find(UserName name);
}