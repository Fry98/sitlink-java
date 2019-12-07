package com.teqtrue.sitlink.dao;

import java.util.List;

import com.teqtrue.sitlink.model.Channel;
import com.teqtrue.sitlink.model.Message;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageDao extends JpaRepository<Message, Integer> {
  public List<Message> findByChannelNameAndChannelSubchatUrl(String name, String url, Pageable pageable);
  public List<Message> findByChannel(Channel channel, Pageable pageable);
  public List<Message> findByChannelAndIdGreaterThanOrderByIdAsc(Channel channel, Integer id);
}
