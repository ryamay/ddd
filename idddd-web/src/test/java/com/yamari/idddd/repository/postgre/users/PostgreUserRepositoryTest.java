package com.yamari.idddd.repository.postgre.users;

import static org.assertj.core.api.Assertions.assertThat;

import com.yamari.idddd.domain.models.users.MailAddress;
import com.yamari.idddd.domain.models.users.User;
import com.yamari.idddd.domain.models.users.UserId;
import com.yamari.idddd.domain.models.users.UserName;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PostgreUserRepositoryTest {

  // 検証用の接続情報
  private static String url;
  private static String dbUser;
  private static String password;

  private PostgreUserRepository targetRepository = new PostgreUserRepository();

  /**
   * DB接続
   */
  @BeforeClass
  public static void setupDb() {
    try {
      Properties dbConfig = new Properties();
      InputStream inputStream =
          PostgreUserRepositoryTest.class.getResourceAsStream("/db.properties");
      dbConfig.load(inputStream);

      url = dbConfig.get("jdbc.url").toString();
      dbUser = dbConfig.get("jdbc.username").toString();
      password = dbConfig.get("jdbc.password").toString();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * usersテーブルの全レコード削除
   */
  private void truncateDb() {
    try (Connection conn = DriverManager.getConnection(url, dbUser, password);
        PreparedStatement ps = conn.prepareStatement("TRUNCATE users")) {
      ps.execute();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * テスト時のレコードを挿入
   */
  private void insertDefaultUser() {
    try (Connection conn = DriverManager.getConnection(url, dbUser, password);
        PreparedStatement ps =
            conn.prepareStatement(
                "INSERT INTO users (id, name, mail_address) VALUES ('000000', 'exists', 'exists@example.com');");) {
      ps.execute();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /** テスト時のDBレコードを設定 */
  @Before
  public void setDefaultUsers() {
    truncateDb();
    insertDefaultUser();
  }

  @Test
  public void testFindByUserId_targetingExistingUser() {
    // DBに存在するユーザが取得できるか確認
    User fetchedUser = targetRepository.find(new UserId("000000"));
    assertThat(fetchedUser.id.getValue()).isEqualTo("000000");
    assertThat(fetchedUser.name.getValue()).isEqualTo("exists");
    assertThat(fetchedUser.mailAddress.getValue()).isEqualTo("exists@example.com");
  }

  @Test
  public void testFindByUserId_targetingAbsentUser() {
    // DBに存在しないユーザの場合、nullを返す
    User fetchedUser = targetRepository.find(new UserId("absent"));
    assertThat(fetchedUser).isNull();
  }

  @Test
  public void testFindByUserName_targetingExistingUser() {
    // DBに存在するユーザが取得できるか確認
    User fetchedUser = targetRepository.find(new UserName("exists"));
    assertThat(fetchedUser.id.getValue()).isEqualTo("000000");
    assertThat(fetchedUser.name.getValue()).isEqualTo("exists");
    assertThat(fetchedUser.mailAddress.getValue()).isEqualTo("exists@example.com");
  }

  @Test
  public void testFindByUserName_targetingAbsentUser() {
    // DBに存在しないユーザの場合、nullを返す
    User fetchedUser = targetRepository.find(new UserName("absent"));
    assertThat(fetchedUser).isNull();
  }

  @Test
  public void testSave_targetingAbsentUser() {
    String id = "save";
    String name = "saveUser";
    String address = "save@example.com";
    User saveUser = new User(new UserId(id), new UserName(name), new MailAddress(address));
    targetRepository.save(saveUser);

    // DBに存在しないのでINSERTする
    User fetchedUser = targetRepository.find(new UserId(id));

    assertThat(fetchedUser.id.getValue()).isEqualTo(id);
    assertThat(fetchedUser.name.getValue()).isEqualTo(name);
    assertThat(fetchedUser.mailAddress.getValue()).isEqualTo(address);

    // 更新前のUserNameでfindしても取得できる
    User fetchedUserByUserNameBeforeUpdate = targetRepository.find(new UserName("exists"));
    assertThat(fetchedUserByUserNameBeforeUpdate).isNotNull();
  }

  @Test
  public void testSave_targetingExistingUser() {
    String id = "000000";
    String name = "saveUser";
    String address = "save@example.com";
    User saveUser = new User(new UserId(id), new UserName(name), new MailAddress(address));
    targetRepository.save(saveUser);

    // DBに存在するのでUPDATEする
    User fetchedUser = targetRepository.find(new UserName("saveUser"));

    assertThat(fetchedUser.id.getValue()).isEqualTo(id);
    assertThat(fetchedUser.name.getValue()).isEqualTo(name);
    assertThat(fetchedUser.mailAddress.getValue()).isEqualTo(address);

    // 更新前のUserNameでfindするとnullが返る
    User fetchedUserByUserNameBeforeUpdate = targetRepository.find(new UserName("exists"));
    assertThat(fetchedUserByUserNameBeforeUpdate).isNull();
  }

  @Test
  public void testDelete() {
    String id = "000000";
    User fetchedUser = targetRepository.find(new UserId(id));
    assertThat(fetchedUser).isNotNull();

    User targetUser =
        new User(new UserId(id), new UserName("test"), new MailAddress("address@example.com"));
    targetRepository.delete(targetUser);

    User fetchedUserAfterDelete = targetRepository.find(new UserId(id));
    assertThat(fetchedUserAfterDelete).isNull();
  }
}
