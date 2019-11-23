package com.teqtrue.sitlink.dao;

import java.util.Objects;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDao<T> {

  @Autowired
  private EntityManager em;

  @Transactional
  public void persist(T obj) {
    Objects.requireNonNull(obj);
    em.persist(obj);
  }
}
