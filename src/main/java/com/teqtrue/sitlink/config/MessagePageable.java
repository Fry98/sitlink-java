package com.teqtrue.sitlink.config;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class MessagePageable implements Pageable {
  private final long offset;
  private final PageRequest req;

  public MessagePageable(int offset, int limit) {
    this.offset = offset;
    req = PageRequest.of(offset, limit, Sort.by("id").descending());
  }

  @Override
  public int getPageNumber() {
    return req.getPageNumber();
  }

  @Override
  public int getPageSize() {
    return req.getPageSize();
  }

  @Override
  public long getOffset() {
    return offset;
  }

  @Override
  public Sort getSort() {
    return req.getSort();
  }

  @Override
  public Pageable next() {
    return req.next();
  }

  @Override
  public Pageable previousOrFirst() {
    return req.previousOrFirst();
  }

  @Override
  public Pageable first() {
    return req.first();
  }

  @Override
  public boolean hasPrevious() {
    return req.hasPrevious();
  }
}
