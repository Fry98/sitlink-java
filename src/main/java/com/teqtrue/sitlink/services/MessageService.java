package com.teqtrue.sitlink.services;

import com.teqtrue.sitlink.dao.ChannelDao;
import com.teqtrue.sitlink.dao.ImageDao;
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
  private final ImageDao imageDao;

  @Autowired
  public MessageService(MessageDao msgDao, ChannelDao chanDao, UserDao userDao, ImageDao imageDao) {
    this.msgDao = msgDao;
    this.chanDao = chanDao;
    this.userDao = userDao;
    this.imageDao = imageDao;
  }

  public void addNewMessage(Message message) {
    if (message.getImage() != null) imageDao.save(message.getImage());
    msgDao.save(message);
    chanDao.save(message.getChannel());
    userDao.save(message.getUser());
  }
}
