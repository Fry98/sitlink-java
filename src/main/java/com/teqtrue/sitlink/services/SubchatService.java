package com.teqtrue.sitlink.services;

import com.teqtrue.sitlink.dao.SubchatDao;
import com.teqtrue.sitlink.model.Subchat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubchatService {

  private final SubchatDao subDao;

  @Autowired
  public SubchatService(SubchatDao subDao) {
    this.subDao = subDao;
  }

  public boolean exists(String subTitle) {
    return subDao.findByTitle(subTitle) == null;
  }

  public void persist(Subchat sub) {
    subDao.save(sub);
  }
}
