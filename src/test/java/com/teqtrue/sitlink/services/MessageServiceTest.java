package com.teqtrue.sitlink.services;

import com.teqtrue.sitlink.config.MessagePageable;
import com.teqtrue.sitlink.dao.MessageDao;
import com.teqtrue.sitlink.model.Channel;
import com.teqtrue.sitlink.model.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class MessageServiceTest {

  @PersistenceContext
  private EntityManager em;

  @Autowired
  private MessageService messageService;

  @Autowired
  private MessageDao messageDao;

  @Test
  public void addNewMessageCreatesAMessageInDb() {
    Channel channel = em.find(Channel.class, 1);
    final Message message = new Message();
    message.setContent("Test Message");
    message.setChannel(channel);
    int messageCountBefore = messageDao.findByChannel(channel, new MessagePageable(0, 100)).size();

    messageService.addNewMessage(message);

    int messageCountAfter = messageDao.findByChannel(channel, new MessagePageable(0, 100)).size();
    assertEquals(messageCountBefore + 1, messageCountAfter);
  }

  @Test
  public void getMessagesReturnsListOfMessagesFromDb() {
    Channel channel = em.find(Channel.class, 1);
    for (int i = 0; i < 10; i++) {
      Message message = new Message();
      message.setContent("Test Message " + i);
      message.setChannel(channel);
      messageService.addNewMessage(message);
    }

    List<Message> fiveMessages = messageService.getMessages(channel, 0, 5);
    List<Message> tenMessages = messageService.getMessages(channel, 0, 10);

    assertEquals(5, fiveMessages.size());
    assertEquals(10, tenMessages.size());
  }
}
