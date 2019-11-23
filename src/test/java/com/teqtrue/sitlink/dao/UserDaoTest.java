package com.teqtrue.sitlink.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.teqtrue.sitlink.App;
import com.teqtrue.sitlink.model.User;

// For explanatory comments, see ProductDaoTest
@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackageClasses = App.class)
public class UserDaoTest {

  @PersistenceContext
  private EntityManager em;

  @Autowired
  private UserDao dao;

  @Test
  public void persistCreatesNewUserWithMatchingData() {
    User newUser = new User(
      "test",
      "te@s.t",
      "superduperurl",
      "hashedgarbage"
    );
    dao.save(newUser);
    
    User userFromDb = em.find(User.class, newUser.getId());
    assertTrue(userFromDb != null);
    assertTrue(userFromDb.getNick().equals("test"));
    assertTrue(userFromDb.getMail().equals("te@s.t"));
    assertTrue(userFromDb.getImg().equals("superduperurl"));
    assertTrue(userFromDb.getPassword().equals("hashedgarbage"));
  }

  @Test
  public void findByNickReturnsPersonWithMatchingNickname() {
    em.persist(new User(
      "test",
      "te@s.t",
      "superduperurl",
      "hashedgarbage"
    ));
    User userFromDb = dao.findByNick("test");
    assertTrue(userFromDb != null);
    assertTrue(userFromDb.getNick().equals("test"));
    assertTrue(userFromDb.getMail().equals("te@s.t"));
    assertTrue(userFromDb.getImg().equals("superduperurl"));
    assertTrue(userFromDb.getPassword().equals("hashedgarbage"));
  }

  @Test
  public void findByNickReturnsNullForUnknownNickname() {
    User userFromDb = dao.findByNick("nonexistent");
    assertTrue(userFromDb == null);
  }
}
