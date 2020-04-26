package com.yamari.idddd.domain.models.users;

public interface IUserFactory {

  User create(UserName name, MailAddress address) throws Exception;
}
