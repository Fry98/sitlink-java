package com.teqtrue.sitlink.services;

import com.teqtrue.sitlink.dao.ChannelDao;
import com.teqtrue.sitlink.model.Channel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelService {

  private final ChannelDao chanDao;

  public ChannelService(ChannelDao chanDao) {
    this.chanDao = chanDao;
  }

  public void persist(Channel... channels) {
    for (Channel chan : channels) {
      chanDao.save(chan);
    }
  }
}
