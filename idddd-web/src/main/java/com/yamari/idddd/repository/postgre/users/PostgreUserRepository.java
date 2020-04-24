package com.yamari.idddd.repository.postgre.users;

import com.yamari.idddd.domain.models.users.IUserRepository;
import com.yamari.idddd.domain.models.users.MailAddress;
import com.yamari.idddd.domain.models.users.User;
import com.yamari.idddd.domain.models.users.UserId;
import com.yamari.idddd.domain.models.users.UserName;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class PostgreUserRepository implements IUserRepository {

  private String url;
  private String dbUser;
  private String password;

  public PostgreUserRepository() {
    try {
      Properties dbConfig = new Properties();
      InputStream inputStream = PostgreUserRepository.class.getResourceAsStream("/db.properties");
      dbConfig.load(inputStream);

      url = dbConfig.get("jdbc.url").toString();
      dbUser = dbConfig.get("jdbc.username").toString();
      password = dbConfig.get("jdbc.password").toString();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void save(User user) {
    String saveUserSQL =
        "INSERT INTO users (id, name, mail_address) VALUES (?, ?, ?) "
            + "ON CONFLICT (id) "
            + "DO UPDATE SET name=?, mail_address=?;";

    try (Connection conn = DriverManager.getConnection(url, dbUser, password);
        PreparedStatement ps = conn.prepareStatement(saveUserSQL);) {
      ps.setString(1, user.id.getValue());
      ps.setString(2, user.name.getValue());
      ps.setString(3, user.mailAddress.getValue());
      ps.setString(4, user.name.getValue());
      ps.setString(5, user.mailAddress.getValue());
      ps.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getErrorCode());
      System.out.println(e.getSQLState());
      System.out.println(e.getMessage());
    }
  }

  @Override
  public void delete(User user) {
    String deleteUserSQL = "DELETE FROM users WHERE id=?;";

    try (Connection conn = DriverManager.getConnection(url, dbUser, password);
        PreparedStatement ps = conn.prepareStatement(deleteUserSQL);) {
      ps.setString(1, user.id.getValue());
      ps.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getErrorCode());
      System.out.println(e.getSQLState());
      System.out.println(e.getMessage());
    }
  }

  @Override
  public User find(UserName name) {
    String findUserSQL = "SELECT * FROM users WHERE name=?;";

    try (Connection conn = DriverManager.getConnection(url, dbUser, password);
        PreparedStatement ps = conn.prepareStatement(findUserSQL);) {
      ps.setString(1, name.getValue());
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          String userId = rs.getString("id");
          String userName = rs.getString("name");
          String mailAddress = rs.getString("mail_address");

          return new User(new UserId(userId), new UserName(userName), new MailAddress(mailAddress));
        } else {
          return null;
        }
      }
    } catch (SQLException e) {
      System.out.println(e.getErrorCode());
      System.out.println(e.getSQLState());
      System.out.println(e.getMessage());
    }
    // 接続エラー時
    return null;
  }

  @Override
  public User find(UserId id) {
    String findUserSQL = "SELECT * FROM users WHERE id=?;";

    try (Connection conn = DriverManager.getConnection(url, dbUser, password);
        PreparedStatement ps = conn.prepareStatement(findUserSQL);) {
      ps.setString(1, id.getValue());
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          String userId = rs.getString("id");
          String userName = rs.getString("name");
          String mailAddress = rs.getString("mail_address");

          return new User(new UserId(userId), new UserName(userName), new MailAddress(mailAddress));
        } else {
          return null;
        }
      }
    } catch (SQLException e) {
      System.out.println(e.getErrorCode());
      System.out.println(e.getSQLState());
      System.out.println(e.getMessage());
    }
    // 接続エラー時
    return null;
  }
}
