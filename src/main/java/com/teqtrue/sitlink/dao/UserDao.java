package com.teqtrue.sitlink.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import com.teqtrue.sitlink.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

  @PersistenceContext
  private EntityManager em;

  @Transactional
  public void persist(User user) {
    em.persist(user);
  }
}
