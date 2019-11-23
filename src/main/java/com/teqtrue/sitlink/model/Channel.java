package com.teqtrue.sitlink.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Channel extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @OneToMany(cascade = CascadeType.REMOVE)
  private List<Message> messages = new ArrayList<>();

  public Channel() {}

  public Channel(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
