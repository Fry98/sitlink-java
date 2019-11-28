package com.teqtrue.sitlink.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(indexes = {
  @Index(name = "url", columnList = "url", unique = true)
})
public class Subchat extends BaseEntity {

  @Column(nullable = false)
  private String url;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  private User admin;

  @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "subchat")
  private List<Channel> channels = new ArrayList<>();

  @ManyToMany(cascade = CascadeType.REMOVE)
  private List<User> followers = new ArrayList<>();

  public Subchat() {}

  public Subchat(String url, String title, String description, User admin) {
    this.url = url;
    this.title = title;
    this.description = description;
    this.admin = admin;
  }

  public void addChannel(Channel... chans) {
    for (Channel chan : chans) {
      channels.add(chan);
      chan.setSubchat(this);
    }
  }

  public String getUrl() {
    return url;
  }

  public String getTitle() {
    return title;
  }

  public String getDesc() {
    return description;
  }

  public List<Channel> getChannels() {
    return channels;
  }

  public User getAdmin() {
    return admin;
  }
}
