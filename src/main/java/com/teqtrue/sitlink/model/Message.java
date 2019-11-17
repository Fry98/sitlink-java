package com.teqtrue.sitlink.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Message {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(nullable = false)
  private User sender;

  @Column(nullable = false)
  private Boolean img;

  @Column(nullable = false)
  private String content;
}
