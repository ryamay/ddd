package com.yamari.idddd.domain.models.users;

public interface IUserRepository
{
	void save(User user);
	void delete(User user);
	User find(UserName name);
	User find(UserId id);
}