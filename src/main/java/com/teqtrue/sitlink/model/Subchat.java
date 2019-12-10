package com.teqtrue.sitlink.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
  @OrderBy("id ASC")
  private List<Channel> channels = new ArrayList<>();

  @ManyToMany(mappedBy = "followed")
  private Set<User> followers = new HashSet<>();

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

  public Set<User> getFollowers() {
    return followers;
  }
}
