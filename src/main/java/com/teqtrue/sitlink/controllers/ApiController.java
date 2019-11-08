package com.teqtrue.sitlink.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.teqtrue.sitlink.exceptions.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
  
  @PostMapping("/user")
  public void createUser(
    @RequestParam(name = "nick", required = false) String nick,
    @RequestParam(name = "mail", required = false) String mail,
    @RequestParam(name = "pic", required = false) String pic,
    @RequestParam(name = "pwd", required = false) String pwd
  ) {
    if (nick == null) throw new CustomException("Invalid nickname!", HttpStatus.BAD_REQUEST);
  }
}
