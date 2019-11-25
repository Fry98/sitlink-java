package com.teqtrue.sitlink.dao;

import com.teqtrue.sitlink.App;
import com.teqtrue.sitlink.model.Channel;
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
public class ChannelDaoTest {

  @PersistenceContext
  private EntityManager em;

  @Autowired
  private ChannelDao dao;

  private Channel prepareChannel() {
    return new Channel(
        "test channel"
    );
  }

  private Subchat prepareSubchat() {
    User admin = new User(
        "admin",
        "ad@m.in",
        null,
        "hashedpwd"
    );
    em.persist(admin);

    return new Subchat(
        "test subchat",
        "test description",
        admin
    );
  }

  @Test
  public void saveCreatesNewChannelWithMatchingData() {
    Channel testChannel = prepareChannel();

    dao.save(testChannel);

    Channel channelFromDb = em.find(Channel.class, testChannel.getId());

    assertNotEquals(null, channelFromDb);
    assertEquals("test channel", channelFromDb.getName());
  }

  @Test
  public void findByNameAndSubchatTitleReturnsChannelWithMatchingNameAndSubchat() {
    Channel testChannel = prepareChannel();
    Subchat testSubchat = prepareSubchat();

    testSubchat.addChannel(testChannel);

    em.persist(testChannel);
    em.persist(testSubchat);

    Channel channelFromDb = dao.findByNameAndSubchatTitle("test channel", "test subchat");

    assertNotEquals(null, channelFromDb);
    assertEquals("test channel", channelFromDb.getName());
  }
}
