package com.teqtrue.sitlink.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class Message extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  private User sender;

  @Column(nullable = false)
  private Boolean img;

  @Column(nullable = false)
  private String content;
}
