package com.teqtrue.sitlink.services;

import com.teqtrue.sitlink.dao.ChannelDao;
import com.teqtrue.sitlink.dao.MessageDao;
import com.teqtrue.sitlink.dao.UserDao;
import com.teqtrue.sitlink.model.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

  private final MessageDao msgDao;

  private final ChannelDao chanDao;

  private final UserDao userDao;

  @Autowired
  public MessageService(MessageDao msgDao, ChannelDao chanDao, UserDao userDao) {
    this.msgDao = msgDao;
    this.chanDao = chanDao;
    this.userDao = userDao;
  }

  public void addNewMessage(Message message) {
    msgDao.save(message);
    chanDao.save(message.getChannel());
    userDao.save(message.getUser());
  }
}
