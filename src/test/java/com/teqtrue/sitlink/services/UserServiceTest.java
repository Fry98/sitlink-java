package com.teqtrue.sitlink.services;

import com.teqtrue.sitlink.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceTest {

  @PersistenceContext
  private EntityManager em;

  @Autowired
  private UserService userService;

  @Test
  public void createUserCreatesNewUserInDatabase() {
    final User user = userService.createUser(
        "test user",
        "test@user.org",
        null,
        "plainPassword"
    );

    final User userFromDb = em.find(User.class, user.getId());

    assertNotEquals(null, userFromDb);
    assertEquals("test user", userFromDb.getNick());
    assertEquals("test@user.org", userFromDb.getMail());
    assertNull(userFromDb.getImg());
  }

  @Test
  public void createdUsersPasswordIsNotStoredAsPlainText() {
    final String plainPassword = "plainPassword";
    final User user = userService.createUser(
        "test user",
        "test@user.org",
        null,
        plainPassword
    );

    final User result = em.find(User.class, user.getId());

    assertNotEquals(plainPassword, result.getPassword());
  }

  @Test
  public void authUserReturnsTrueToCorrectPassword() {
    final String plainPassword = "plainPassword";
    final User user = userService.createUser(
        "test user",
        "test@user.org",
        null,
        plainPassword
    );

    assertTrue(userService.authUser(user, plainPassword));
  }

  @Test
  public void authUserReturnsFalseToIncorrectPassword() {
    final String plainPassword = "plainPassword";
    final User user = userService.createUser(
        "test user",
        "test@user.org",
        null,
        plainPassword
    );

    assertFalse(userService.authUser(user, "incorrectPassword"));
  }

  @Test
  public void createRootCreatesRootUserWithAllPropertiesSetToRoot() {
    User root = userService.createRoot();

    assertEquals("root", root.getNick());
    assertEquals("root", root.getMail());
    assertNull(root.getImg());
    assertEquals("root", root.getPassword());
  }
}
