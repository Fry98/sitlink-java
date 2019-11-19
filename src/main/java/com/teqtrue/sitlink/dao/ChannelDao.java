package com.teqtrue.sitlink.dao;

import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import com.teqtrue.sitlink.model.Channel;
import org.springframework.stereotype.Repository;

@Repository
public class ChannelDao {

  @PersistenceContext
  private EntityManager em;

  @Transactional
  public void persist(Channel... channels) {
    Objects.requireNonNull(channels);
    for (Channel chan : channels) {
      em.persist(chan);
    }
  }
}
