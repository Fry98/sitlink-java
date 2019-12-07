package com.teqtrue.sitlink.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Message extends BaseEntity {

  @ManyToOne
  private User sender;

  @OneToOne
  private Image img;

  @Column
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

  public void setImage(Image image) {
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

  public Image getImage() {
    return img;
  }

  public String getContent() {
    return content;
  }
}
