package com.teqtrue.sitlink.dao;

import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import com.teqtrue.sitlink.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends BaseDao<User> {

  @PersistenceContext
  private EntityManager em;

  public User findByNick(String nick) {
    Objects.requireNonNull(nick);
    try {
      return em.createNamedQuery("User.findByNick", User.class).setParameter("nick", nick).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }
}
