package com.teqtrue.sitlink.services;

import com.teqtrue.sitlink.dao.SubchatDao;
import com.teqtrue.sitlink.model.Subchat;
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
public class SubchatServiceTest {

  @PersistenceContext
  private EntityManager em;

  @Autowired
  private SubchatService subchatService;

  @Autowired
  private SubchatDao subchatDao;

  @Test
  public void testCreateNewSubchatCreatesASubchatInDb() {
    User rootUser = em.find(User.class, 1);

    subchatService.createNewSubchat(
        "test-url",
        "Test Subchat",
        "This is a test subchat",
        rootUser
    );

    Subchat subchatFromDb = subchatDao.findByUrl("test-url");
    assertNotNull(subchatFromDb);
  }

  @Test
  public void testRemoveSubchatRemovesSubchatFromDb() {
    createTestSubchat();
    Subchat subchatFromDb = subchatDao.findByUrl("test-url");

    subchatService.removeSubchat(subchatFromDb);
    assertNull(subchatDao.findByUrl("test-url"));
  }

  @Test
  public void testExistsreturnsTrueIfRequestedSubchatIsInDb() {
    createTestSubchat();

    assertTrue(subchatService.exists("test-url"));
  }

  private void createTestSubchat() {
    User rootUser = em.find(User.class, 1);
    subchatService.createNewSubchat(
        "test-url",
        "Test Subchat",
        "This is a test subchat",
        rootUser
    );
  }
}
