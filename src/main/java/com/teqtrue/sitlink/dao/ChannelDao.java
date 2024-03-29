package com.teqtrue.sitlink.dao;

import com.teqtrue.sitlink.model.Channel;
import com.teqtrue.sitlink.model.Subchat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelDao extends JpaRepository<Channel, Integer> {
  public Channel findByNameAndSubchatUrl(String name, String url);
  public Channel findByNameAndSubchat(String name, Subchat subchat);
}
