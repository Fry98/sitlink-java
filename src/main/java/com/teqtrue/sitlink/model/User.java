package com.teqtrue.sitlink.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;
  private String nick;
  private String mail;
  private String img;
  private String password;

  public Integer getId() {
    return id;
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
