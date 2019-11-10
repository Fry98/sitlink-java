package com.teqtrue.sitlink.dao;

import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
    Objects.requireNonNull(user);
    em.persist(user);
  }

  public User findByNick(String nick) {
    Objects.requireNonNull(nick);
    try {
      return em.createNamedQuery("User.findByNick", User.class).setParameter("nick", nick).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }
}
