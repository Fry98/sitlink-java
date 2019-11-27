package com.teqtrue.sitlink.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(indexes = {
  @Index(name = "name", columnList = "name", unique = false)
})
public class Channel extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "channel")
  private List<Message> messages = new ArrayList<>();

  @ManyToOne
  private Subchat subchat;

  public Channel() {}

  public Channel(String name) {
    this.name = name;
  }

  public Channel(String name, Subchat sub) {
    this.name = name;
    subchat = sub;
  }

  public String getName() {
    return name;
  }

  public void setSubchat(Subchat sub) {
    subchat = sub;
  }

  public void addMessage(Message msg) {
    messages.add(msg);
  } 
}
