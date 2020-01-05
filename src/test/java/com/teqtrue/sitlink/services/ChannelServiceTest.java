package com.teqtrue.sitlink.services;

import com.teqtrue.sitlink.model.Subchat;
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
public class ChannelServiceTest {

  @PersistenceContext
  private EntityManager em;

  @Autowired
  private ChannelService channelService;

  @Test
  public void testExistsReturnsTrueIfChannelItExistsInDb() {
    Subchat nexus = em.find(Subchat.class, 1);
    assertTrue(channelService.exists("general", nexus));
  }
}
