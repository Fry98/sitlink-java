package com.teqtrue.sitlink.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Message extends BaseEntity {

  @ManyToOne
  private User sender;

  @Column(nullable = false)
  private Boolean img;

  @Column(nullable = false)
  private String content;

  @ManyToOne
  private Channel channel;

  public Message() {}

  public void setUser(User user) {
    sender = user;
    user.addMessage(this);
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setImage(Boolean image) {
    img = image;
  }

  public void setChannel(Channel channel) {
    this.channel = channel;
    channel.addMessage(this);
  }

  public Channel getChannel() {
    return channel;
  }

  public User getUser() {
    return sender;
  }
}
