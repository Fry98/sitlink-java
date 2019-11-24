package com.teqtrue.sitlink.controllers;

import javax.servlet.http.HttpServletRequest;

import com.teqtrue.sitlink.dao.SubchatDao;
import com.teqtrue.sitlink.model.Subchat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

  private final SubchatDao subDao;

  public PageController(SubchatDao subDao) {
    this.subDao = subDao;
  }

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

    Subchat subObj = subDao.findByTitle(sub);
    if (subObj == null) return "redirect:/c/nexus";

    model.addAttribute("sub", subObj.getTitle());
    model.addAttribute("chans", subObj.getChannels());
    model.addAttribute("admin", subObj.getAdmin().getId() == req.getSession().getAttribute("id"));
    model.addAttribute("chanArr", subObj.getChannels().stream().map(x -> x.getName()).toArray());
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
