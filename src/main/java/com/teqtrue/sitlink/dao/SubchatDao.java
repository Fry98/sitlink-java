package com.teqtrue.sitlink.dao;

import com.teqtrue.sitlink.model.Subchat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubchatDao extends JpaRepository<Subchat, Integer> {
  public Subchat findByUrl(String url);
}
