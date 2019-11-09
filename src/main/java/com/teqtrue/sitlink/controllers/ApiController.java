package com.teqtrue.sitlink.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.teqtrue.sitlink.dao.UserDao;
import com.teqtrue.sitlink.exceptions.RequestException;
import com.teqtrue.sitlink.lib.ImageUploader;
import com.teqtrue.sitlink.model.User;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

  @Autowired
  private StrongPasswordEncryptor passEnc;

  @Autowired
  private UserDao userDao;
  
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
    if (userDao.findByNick(nick) != null) {
      throw new RequestException("Nickname is already taken!", HttpStatus.CONFLICT);
    }
    if (pwd == null || pwd.length() < 6 || pwd.length() > 30) {
      throw new RequestException("Invalid password!", HttpStatus.BAD_REQUEST);
    }
    if (mail == null || mail.length() > 40 || mail.matches("/^(.+)@(.+)\\.(.+)$/")) {
      throw new RequestException("Invalid e-mail address!", HttpStatus.BAD_REQUEST);
    }

    String picUrl = ImageUploader.upload(pic);
    if (picUrl == null) {
      throw new RequestException("Invalid image file!", HttpStatus.BAD_REQUEST);
    }

    String pwdHash = passEnc.encryptPassword(pwd);    
    userDao.persist(new User(
      nick,
      mail,
      picUrl,
      pwdHash
    ));
  }
}
