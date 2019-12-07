package com.teqtrue.sitlink.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Image extends BaseEntity {

  @Column(nullable = false)
  private String url;

  @Column
  private Integer width;

  @Column
  private Integer height;

  @Column
  private String description;

  public Image() {}

  public Image(String url, Integer width, Integer height) {
    this.url = url;
    this.width = width;
    this.height = height;
  }

  public String getUrl() {
    return url;
  }
}
