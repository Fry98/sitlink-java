package com.teqtrue.sitlink.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

  @GetMapping
  public void getFollows() {
    // TODO: Write method
  }

  @PostMapping
  public void addFollow() {
    // TODO: Write method
  }
}
