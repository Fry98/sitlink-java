package com.teqtrue.sitlink.dao;

import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import com.teqtrue.sitlink.model.Subchat;
import org.springframework.stereotype.Repository;

@Repository
public class SubchatDao extends BaseDao<Subchat> {

  @PersistenceContext
  private EntityManager em;

  public Subchat findByTitle(String title) {
    Objects.requireNonNull(title);
    try {
      return em.createNamedQuery("Subchat.findByTitle", Subchat.class).setParameter("title", title).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }
}
