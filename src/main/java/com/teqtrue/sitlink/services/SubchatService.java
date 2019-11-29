package com.teqtrue.sitlink.services;

import com.teqtrue.sitlink.dao.ChannelDao;
import com.teqtrue.sitlink.dao.SubchatDao;
import com.teqtrue.sitlink.model.Channel;
import com.teqtrue.sitlink.model.Subchat;
import com.teqtrue.sitlink.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubchatService {
  private final SubchatDao subDao;
  private final ChannelDao chanDao;

  @Autowired
  public SubchatService(SubchatDao subDao, ChannelDao chanDao) {
    this.subDao = subDao;
    this.chanDao = chanDao;
  }

  public boolean exists(String url) {
    return subDao.findByUrl(url) != null;
  }

  public void persist(Subchat sub) {
    subDao.save(sub);
  }

  public void createNewSubchat(String url, String title, String desc, User admin) {
    Subchat newSub = new Subchat(url, title, desc, admin);
    Channel defaultChan = new Channel("general", newSub);
    admin.getSubs().add(newSub);
    subDao.save(newSub);
    chanDao.save(defaultChan);
  }

  public void removeSubchat(Subchat sub) {
    sub.getAdmin().getSubs().remove(sub);
    subDao.delete(sub);
  }
}
