package com.teqtrue.sitlink.services;

import com.teqtrue.sitlink.dao.ChannelDao;
import com.teqtrue.sitlink.model.Channel;
import com.teqtrue.sitlink.model.Subchat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelService {

  private final ChannelDao chanDao;

  @Autowired
  public ChannelService(ChannelDao chanDao) {
    this.chanDao = chanDao;
  }

  public void persist(Channel... channels) {
    for (Channel chan : channels) {
      chanDao.save(chan);
    }
  }

  public boolean exists(String name, Subchat sub) {
    return chanDao.findByNameAndSubchat(name, sub) != null;
  }
}
