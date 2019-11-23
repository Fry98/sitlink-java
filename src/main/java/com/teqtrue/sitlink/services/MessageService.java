package com.teqtrue.sitlink.services;

import com.teqtrue.sitlink.dao.ChannelDao;
import com.teqtrue.sitlink.dao.MessageDao;
import com.teqtrue.sitlink.dao.UserDao;
import com.teqtrue.sitlink.model.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

  @Autowired
  private MessageDao msgDao;

  @Autowired
  private ChannelDao chanDao;

  @Autowired
  private UserDao userDao;

  public void addNewMessage(Message message) {
    msgDao.save(message);
    chanDao.save(message.getChannel());
    userDao.save(message.getUser());
  }
}
