package com.yamari.idddd.application.users;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.yamari.idddd.domain.models.users.User;
import com.yamari.idddd.domain.models.users.UserName;
import com.yamari.idddd.domain.services.UserService;

public class UserApplicationService
{
	public void CreateUser (String userName) throws Exception {
		User user = new User(new UserName(userName));
		
		UserService userService = new UserService();
		if (userService.exists(user)) {
			throw new Exception(userName + "ÇÕä˘Ç…ë∂ç›ÇµÇƒÇ¢Ç‹Ç∑ÅB");
		}
		
		String url ="jdbc:postgresql://localhost:5432/testdb";
		String dbUser = "postgres";
		String password = "mysecretpassword1234";
		try(Connection conn = DriverManager.getConnection(url, dbUser, password);
			PreparedStatement ps =  conn.prepareStatement("INSERT INTO users (?, ?)");) {
			ps.setString(1, user.id.getValue());
			ps.setString(2, user.name.getValue());
			ps.executeUpdate();
		} catch (SQLException e) {			
			System.out.println(e.getErrorCode());
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
		
	}
}
