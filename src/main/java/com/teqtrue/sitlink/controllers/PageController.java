package com.teqtrue.sitlink.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

  @GetMapping("/c/{sub}")
  public String chat(@PathVariable("sub") String sub) {
    return "chat";
  }

  @GetMapping("/404")
  public String fallback() {
    return "redirect:/";
  }
}
