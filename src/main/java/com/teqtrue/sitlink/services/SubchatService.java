package com.teqtrue.sitlink.services;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

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
  private final EntityManager em;

  @Autowired
  public SubchatService(SubchatDao subDao, ChannelDao chanDao, EntityManager em) {
    this.subDao = subDao;
    this.chanDao = chanDao;
    this.em = em;
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

  @Transactional
  public void removeSubchat(Subchat sub) {
    Query q = em.createNativeQuery("DELETE FROM user_subchat WHERE subchat_id = ?");
    q.setParameter(1, sub.getId());
    q.executeUpdate();
    subDao.delete(sub);
    sub.getAdmin().getSubs().remove(sub);
  }
}
