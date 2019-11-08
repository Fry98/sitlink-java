package com.teqtrue.sitlink.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/signup")
  public String signup() {
    return "signup";
  }
}
