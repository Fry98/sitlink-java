package com.teqtrue.sitlink.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(indexes = {
  @Index(name = "nick", columnList = "nick", unique = true)
})
public class User extends BaseEntity {

  @Column(nullable = false)
  private String nick;

  @Column(nullable = false)
  private String mail;

  @OneToOne(cascade = CascadeType.REMOVE)
  private Image img;

  @Column(nullable = false)
  private String password;

  @OneToMany(mappedBy = "admin", cascade = CascadeType.REMOVE)
  private Set<Subchat> subs = new HashSet<>();

  @OneToMany(mappedBy = "sender", cascade = CascadeType.REMOVE)
  private List<Message> messages = new ArrayList<>();

  @ManyToMany
  private Set<Subchat> followed = new HashSet<>();

  public User() {}

  public User(String nick, String mail, Image img, String password) {
    this.nick = nick;
    this.mail = mail;
    this.img = img;
    this.password = password;
  }

  public String getNick() {
    return nick;
  }

  public String getMail() {
    return mail;
  }

  public Image getImg() {
    return img;
  }

  public String getPassword() {
    return password;
  }

  public void setNick(String nick) {
    this.nick = nick;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public void setImg(Image img) {
    this.img = img;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void addMessage(Message message) {
    messages.add(message);
  }

  public void addFollow(Subchat sub) {
    followed.add(sub);
    sub.getFollowers().add(this);
  }

  public void removeFollow(Subchat sub) {
    followed.remove(sub);
    sub.getFollowers().remove(this);
  }

  public Set<Subchat> getFollows() {
    return followed;
  }

  public Set<Subchat> getSubs() {
    return subs;
  }
}
