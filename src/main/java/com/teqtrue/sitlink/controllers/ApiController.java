package com.teqtrue.sitlink.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class ApiController {
  
  @PostMapping("/user")
  public void createUser(
    @RequestBody String req
  ) {
    System.out.println(req);
  }
}
