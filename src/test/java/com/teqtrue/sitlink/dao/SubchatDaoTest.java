package com.teqtrue.sitlink.dao;

import com.teqtrue.sitlink.App;
import com.teqtrue.sitlink.model.Subchat;
import com.teqtrue.sitlink.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackageClasses = App.class)
public class SubchatDaoTest {

  @PersistenceContext
  private EntityManager em;

  @Autowired
  private SubchatDao dao;

  @Autowired
  private UserDao userDao;

  private Subchat prepareSubchat(User admin) {
    return new Subchat(
        "test subchat",
        "This is a test subchat",
        admin
    );
  }

  private User prepareUser() {
    return new User(
        "test",
        "te@s.t",
        null,
        "hashedgarbage"
    );
  }

  @Test
  public void saveCreatesNewSubchatWithMatchingData() {
    User testUser = prepareUser();
    Subchat testSubchat = prepareSubchat(testUser);

    userDao.save(testUser);
    dao.save(testSubchat);

    Subchat subchatFromDb = em.find(Subchat.class, testSubchat.getId());

    assertNotEquals(null, subchatFromDb);
    assertEquals("test subchat", subchatFromDb.getTitle());
    assertEquals("This is a test subchat", subchatFromDb.getDesc());
    assertEquals(testUser.getId(), subchatFromDb.getAdmin().getId());
  }

  @Test
  public void findByTitleReturnsSubchatWithMatchingTitle() {
    User admin = prepareUser();
    Subchat testSubchat = prepareSubchat(admin);

    em.persist(admin);
    em.persist(testSubchat);

    Subchat subchatFromDb = dao.findByTitle("test subchat");

    assertNotEquals(null, subchatFromDb);
    assertEquals("test subchat", subchatFromDb.getTitle());
    assertEquals("This is a test subchat", subchatFromDb.getDesc());
    assertEquals(admin.getId(), subchatFromDb.getAdmin().getId());
  }
}
