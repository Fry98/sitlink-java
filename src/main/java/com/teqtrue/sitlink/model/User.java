package com.teqtrue.sitlink.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(indexes = {
  @Index(name = "nick", columnList = "nick", unique = true)
})
@NamedQueries({
  @NamedQuery(name = "User.findByNick", query = "SELECT u from User u WHERE u.nick = :nick")
})
public class User extends BaseEntity {

  @Column(nullable = false)
  private String nick;

  @Column(nullable = false)
  private String mail;

  @Column(nullable = false)
  private String img;

  @Column(nullable = false)
  private String password;

  @OneToMany(mappedBy = "admin", cascade = CascadeType.REMOVE)
  private List<Subchat> subs = new ArrayList<>();

  @OneToMany(mappedBy = "sender", cascade = CascadeType.REMOVE)
  private List<Message> messages = new ArrayList<>();

  @ManyToMany(mappedBy = "followers")
  private List<Subchat> followed = new ArrayList<>();

  public User() {}

  public User(String nick, String mail, String img, String password) {
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

  public String getImg() {
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

  public void setImg(String img) {
    this.img = img;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
