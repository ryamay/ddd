package com.yamari.idddd.repository.postgre.users;

import com.yamari.idddd.domain.models.users.IUserFactory;
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
import java.util.Properties;

public class PostgreUserFactory implements IUserFactory {

  private String url;
  private String dbUser;
  private String password;

  public PostgreUserFactory() {
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
  public User create(UserName name, MailAddress address) throws Exception {
    String seqId;
    String sequenceSQL = "SELECT to_char(nextval('user_id_seq'), 'FM00000000')";

    try (Connection conn = DriverManager.getConnection(url, dbUser, password);
        PreparedStatement ps = conn.prepareStatement(sequenceSQL)) {
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          seqId = rs.getString(1);
        } else {
          throw new Exception("user_id_seqÇÃéüÇÃílÇ™éÊìæÇ≈Ç´Ç‹ÇπÇÒÇ≈ÇµÇΩÅB");
        }
      }
    }

    UserId id = new UserId(seqId);
    return new User(id, name, address);
  }
}
