package com.teqtrue.sitlink.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

  @GetMapping("/")
  public String index(HttpServletRequest req) {
    if (req.getSession().getAttribute("id") != null) {
      return "redirect:/c/nexus";
    }

    return "index";
  }

  @GetMapping("/signup")
  public String signup() {
    return "signup";
  }

  @GetMapping("/c/{sub}")
  public String chat(
    @PathVariable("sub") String sub,
    Model model,
    HttpServletRequest req
  ) {
    if (req.getSession().getAttribute("id") == null) {
      return "redirect:/";
    }

    model.addAttribute("sub", sub);
    return "chat";
  }

  @GetMapping("/logout")
  public String logout(HttpServletRequest req) {
    req.getSession().invalidate();
    return "redirect:/";
  }

  @GetMapping("/404")
  public String fallback() {
    return "redirect:/";
  }
}
