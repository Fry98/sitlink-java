package com.teqtrue.sitlink.services;

import java.util.List;

import com.teqtrue.sitlink.config.MessagePageable;
import com.teqtrue.sitlink.dao.ImageDao;
import com.teqtrue.sitlink.dao.MessageDao;
import com.teqtrue.sitlink.model.Channel;
import com.teqtrue.sitlink.model.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
  private final MessageDao msgDao;
  private final ImageDao imageDao;

  @Autowired
  public MessageService(MessageDao msgDao, ImageDao imageDao) {
    this.msgDao = msgDao;
    this.imageDao = imageDao;
  }

  public void addNewMessage(Message message) {
    if (message.getImage() != null) imageDao.save(message.getImage());
    msgDao.save(message);
  }

  public List<Message> getMessages(Channel chan, int skip, int limit) {
    return msgDao.findByChannel(chan, new MessagePageable(skip, limit));
  }

  public List<Message> getUpdate(Integer lastId) {
    return msgDao.findByIdGreaterThanOrderByIdAsc(lastId);
  }
}
