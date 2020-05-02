package com.yamari.idddd.repository.postgre.circles;

import com.yamari.idddd.domain.models.circles.Circle;
import com.yamari.idddd.domain.models.circles.CircleId;
import com.yamari.idddd.domain.models.circles.CircleName;
import com.yamari.idddd.domain.models.circles.ICircleRepository;
import com.yamari.idddd.domain.models.users.IUserRepository;
import com.yamari.idddd.domain.models.users.User;
import com.yamari.idddd.domain.models.users.UserId;
import com.yamari.idddd.repository.RepositoryException;
import com.yamari.idddd.repository.postgre.users.PostgresUserRepository;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PostgresCircleRepository implements ICircleRepository {

  private final IUserRepository userRepository = new PostgresUserRepository();
  private String url;
  private String dbUser;
  private String password;

  public PostgresCircleRepository() {
    try {
      Properties dbConfig = new Properties();
      InputStream inputStream = PostgresUserRepository.class.getResourceAsStream("/db.properties");
      dbConfig.load(inputStream);

      url = dbConfig.get("jdbc.url").toString();
      dbUser = dbConfig.get("jdbc.username").toString();
      password = dbConfig.get("jdbc.password").toString();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void save(Circle circle) throws RepositoryException {
    String saveCircleSQL =
        "INSERT INTO circles (id, name, owner_id) VALUES (?, ?, ?) "
            + "ON CONFLICT (id) "
            + "DO UPDATE SET name=?, owner_id=?;";

    try (Connection conn = DriverManager.getConnection(url, dbUser, password);
        PreparedStatement ps = conn.prepareStatement(saveCircleSQL)) {
      ps.setString(1, circle.getId().getValue());
      ps.setString(2, circle.getName().getValue());
      ps.setString(3, circle.getOwner().getId().getValue());
      ps.setString(4, circle.getName().getValue());
      ps.setString(5, circle.getOwner().getId().getValue());
      ps.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getErrorCode());
      System.out.println(e.getSQLState());
      System.out.println(e.getMessage());
      throw new RepositoryException(e);
    }

    String saveUsersCirclesSQL =
        "INSERT INTO users_circles (user_id, circle_id) VALUES (?, ?) "
            + "ON CONFLICT (user_id, circle_id) "
            + "DO UPDATE SET user_id=?, circle_id=?;";

    try (Connection conn = DriverManager.getConnection(url, dbUser, password);
        PreparedStatement ps = conn.prepareStatement(saveUsersCirclesSQL)) {
      ps.setString(2, circle.getId().getValue());
      ps.setString(4, circle.getId().getValue());

      for (User member : circle.getMembers()) {
        ps.setString(1, member.getId().getValue());
        ps.setString(3, member.getId().getValue());
        ps.executeUpdate();
      }
    } catch (SQLException e) {
      System.out.println(e.getErrorCode());
      System.out.println(e.getSQLState());
      System.out.println(e.getMessage());
      throw new RepositoryException(e);
    }
  }

  @Override
  public Circle find(CircleId id) throws RepositoryException {
    String findCircleSQL = "SELECT id, name, owner_id FROM circles WHERE id=?;";
    String findMembersSQL = "SELECT user_id FROM users_circles WHERE circle_id=?;";

    try (Connection conn = DriverManager.getConnection(url, dbUser, password);
        PreparedStatement findCirclePs = conn.prepareStatement(findCircleSQL);
        PreparedStatement findMembersPs = conn.prepareStatement(findMembersSQL)) {
      findCirclePs.setString(1, id.getValue());
      findMembersPs.setString(1, id.getValue());
      ResultSet findCircleRs = findCirclePs.executeQuery();
      ResultSet findMembersRs = findMembersPs.executeQuery();
      if (findCircleRs.next()) {
        CircleId circleId = new CircleId(findCircleRs.getString("id"));
        CircleName circleName = new CircleName(findCircleRs.getString("name"));

        User owner = userRepository.find(new UserId(findCircleRs.getString("owner_id")));

        List<User> members = new ArrayList<>();
        while (findMembersRs.next()) {
          User member = userRepository.find(new UserId(findCircleRs.getString("user_id")));
          members.add(member);
        }

        return new Circle(circleId, circleName, owner, members);
      } else {
        return null;
      }
    } catch (SQLException e) {
      System.out.println(e.getErrorCode());
      System.out.println(e.getSQLState());
      System.out.println(e.getMessage());
      throw new RepositoryException(e);
    }
  }

  @Override
  public Circle find(CircleName name) throws RepositoryException {
    String findCircleSQL = "SELECT id, name, owner_id FROM circles WHERE name=?;";
    String findMembersSQL = "SELECT user_id FROM users_circles WHERE circle_id=?;";

    try (Connection conn = DriverManager.getConnection(url, dbUser, password);
        PreparedStatement findCirclePs = conn.prepareStatement(findCircleSQL);
        PreparedStatement findMembersPs = conn.prepareStatement(findMembersSQL)) {
      findCirclePs.setString(1, name.getValue());

      ResultSet findCircleRs = findCirclePs.executeQuery();

      if (findCircleRs.next()) {
        String id = findCircleRs.getString("id");
        CircleId circleId = new CircleId(id);
        CircleName circleName = new CircleName(findCircleRs.getString("name"));

        User owner = userRepository.find(new UserId(findCircleRs.getString("owner_id")));

        findMembersPs.setString(1, id);
        ResultSet findMembersRs = findMembersPs.executeQuery();
        List<User> members = new ArrayList<>();
        while (findMembersRs.next()) {
          User member = userRepository.find(new UserId(findCircleRs.getString("user_id")));
          members.add(member);
        }

        return new Circle(circleId, circleName, owner, members);
      } else {
        return null;
      }
    } catch (SQLException e) {
      System.out.println(e.getErrorCode());
      System.out.println(e.getSQLState());
      System.out.println(e.getMessage());
      throw new RepositoryException(e);
    }
  }
}
