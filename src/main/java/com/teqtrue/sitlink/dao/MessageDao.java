package com.teqtrue.sitlink.dao;

import com.teqtrue.sitlink.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageDao extends JpaRepository<Message, Integer> {
  
}
