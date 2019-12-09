package com.teqtrue.sitlink.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.teqtrue.sitlink.dao.SubchatDao;
import com.teqtrue.sitlink.dao.UserDao;
import com.teqtrue.sitlink.exceptions.RequestException;
import com.teqtrue.sitlink.model.Subchat;
import com.teqtrue.sitlink.model.User;
import com.teqtrue.sitlink.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/follow")
public class FollowController {
  private final SubchatDao subDao;
  private final UserService userService;
  private final UserDao userDao;

  @Autowired
  public FollowController(SubchatDao subDao, UserService userService, UserDao userDao) {
    this.subDao = subDao;
    this.userService = userService;
    this.userDao = userDao;
  }

  @GetMapping
  public Map<String, Object> getFollows(HttpServletRequest req) {
    if (req.getSession().getAttribute("id") == null) throw new RequestException("API Access Forbidden", HttpStatus.FORBIDDEN);
    User user = userDao.findById((Integer) req.getSession().getAttribute("id")).get();
    
    Map<String, Object> res = new HashMap<>();
    res.put("owned", getSubList(user.getSubs()));
    res.put("followed", getSubList(user.getFollows()));
    return res;
  }

  @PostMapping
  public void addFollow(
    @RequestParam(name = "sub", required = false) String sid,
    HttpServletRequest req
  ) {
    if (req.getSession().getAttribute("id") == null) throw new RequestException("API Access Forbidden", HttpStatus.FORBIDDEN);
    if (sid == null) throw new RequestException("Invalid API Request", HttpStatus.BAD_REQUEST);

    Subchat sub = subDao.findByUrl(sid);
    if (sub == null) throw new RequestException("Subchat doesn't exist!", HttpStatus.NOT_FOUND);
    if (sub.getAdmin().getId() == (Integer) req.getSession().getAttribute("id")) {
      throw new RequestException("Admin can't follow his own Subcaht!", HttpStatus.CONFLICT);
    }

    userService.toggleFollow((Integer) req.getSession().getAttribute("id"), sub);
  }

  private List<Object> getSubList(List<Subchat> subs) {
    List<Object> res = new ArrayList<>();
    for (Subchat sub : subs) {
      Map<String, String> subObj = new HashMap<>();
      subObj.put("id", sub.getUrl());
      subObj.put("title", sub.getTitle());
      subObj.put("desc", sub.getDesc());
      res.add(subObj);
    }
    return res;
  }
}
