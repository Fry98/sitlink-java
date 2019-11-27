package com.teqtrue.sitlink.controllers;

import javax.servlet.http.HttpServletRequest;
import com.teqtrue.sitlink.dao.UserDao;
import com.teqtrue.sitlink.exceptions.RequestException;
import com.teqtrue.sitlink.lib.ImageUploader;
import com.teqtrue.sitlink.model.Image;
import com.teqtrue.sitlink.model.User;
import com.teqtrue.sitlink.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
  
  private final UserDao userDao;
  private final UserService userService;

  @Autowired
  public AuthController(UserDao userDao, UserService userService) {
    this.userDao = userDao;
    this.userService = userService;
  }

  @PostMapping("/user")
  public void createUser(
    @RequestParam(name = "nick", required = false) String nick,
    @RequestParam(name = "mail", required = false) String mail,
    @RequestParam(name = "pic", required = false) String pic,
    @RequestParam(name = "pwd", required = false) String pwd
  ) {
    if (nick == null || nick.length() < 3 || nick.length() > 30) {
      throw new RequestException("Invalid nickname!", HttpStatus.BAD_REQUEST);
    }
    if (userService.isNicknameTaken(nick)) {
      throw new RequestException("Nickname is already taken!", HttpStatus.CONFLICT);
    }
    if (pwd == null || pwd.length() < 6 || pwd.length() > 30) {
      throw new RequestException("Invalid password!", HttpStatus.BAD_REQUEST);
    }

    if (mail == null || mail.length() > 40 || !mail.matches("^(.+)@(.+)\\.(.+)$")) {
      throw new RequestException("Invalid e-mail address!", HttpStatus.BAD_REQUEST);
    }

    Image profilePic = ImageUploader.upload(pic);
    if (profilePic == null) {
      throw new RequestException("Invalid image file!", HttpStatus.BAD_REQUEST);
    }

    userService.createUser(nick, mail, profilePic, pwd);
  }

  @PostMapping("/login")
  public void login(
    @RequestParam(name = "nick", required = false) String nick,
    @RequestParam(name = "pwd", required = false) String pwd,
    HttpServletRequest req
  ) {
    if (nick == null || pwd == null) {
      throw new RequestException("Invalid user data!", HttpStatus.BAD_REQUEST); 
    }

    User user = userDao.findByNick(nick);
    if (user == null || !userService.authUser(user, pwd)) {
      throw new RequestException("Incorrect nickname or password!", HttpStatus.UNAUTHORIZED);
    }

    req.getSession().setAttribute("id", user.getId());
  }
}
