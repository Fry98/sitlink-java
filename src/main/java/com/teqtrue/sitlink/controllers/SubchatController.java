package com.teqtrue.sitlink.controllers;

import javax.servlet.http.HttpServletRequest;

import com.teqtrue.sitlink.dao.UserDao;
import com.teqtrue.sitlink.exceptions.RequestException;
import com.teqtrue.sitlink.services.SubchatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subchat")
public class SubchatController {
  private final SubchatService subService;
  private final UserDao userDao;

  @Autowired
  public SubchatController(SubchatService subService, UserDao userDao) {
    this.subService = subService;
    this.userDao = userDao;
  }

  @PostMapping
  public void addSubchat(
    @RequestParam(name = "url", required = false) String url,
    @RequestParam(name = "title", required = false) String title,
    @RequestParam(name = "desc", required = false) String desc,
    HttpServletRequest req
  ) {
    if (req.getSession().getAttribute("id") == null) throw new RequestException("API Access Forbidden", HttpStatus.FORBIDDEN);
    if (
      url == null ||
      title == null ||
      desc == null ||
      url.length() < 3 ||
      url.length() > 30 ||
      title.length() < 3 ||
      title.length() > 50 ||
      desc.length() < 10 ||
      desc.length() > 100 ||
      !title.matches("^[a-z0-9\\-_]*$")
    ) {
      throw new RequestException("Invalid API Request", HttpStatus.BAD_REQUEST);
    }
    if (subService.exists(url)) {
      throw new RequestException("Subchat URL is already taken!", HttpStatus.CONFLICT);
    }
    subService.createNewSubchat(
      url, 
      title,
      desc,
      userDao.findById(
        (Integer) req.getSession().getAttribute("id")
      ).get()
    );
  }

  @DeleteMapping
  public void removeSubchat(
    @RequestParam(name = "sub", required = false) String sub
  ) {
    // TODO: Write method
  }
}
