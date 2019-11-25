package com.teqtrue.sitlink.services;

import com.teqtrue.sitlink.dao.ImageDao;
import com.teqtrue.sitlink.dao.UserDao;
import com.teqtrue.sitlink.model.Image;
import com.teqtrue.sitlink.model.User;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserDao userDao;
  private final ImageDao imageDao;
  private final StrongPasswordEncryptor passEnc;

  @Autowired
  public UserService(UserDao userDao, StrongPasswordEncryptor passEnc, ImageDao imageDao) {
    this.userDao = userDao;
    this.passEnc = passEnc;
    this.imageDao = imageDao;
  }

  public boolean isNicknameTaken(String nickname) {
    return userDao.findByNick(nickname) != null;
  }

  public User createUser(String nick, String mail, Image img, String password) {
    String pwdHash = passEnc.encryptPassword(password);
    User newUser = new User(
      nick,
      mail,
      img,
      pwdHash
    );
    if (img != null) imageDao.save(img);
    userDao.save(newUser);
    return newUser;
  }

  public boolean authUser(User user, String password) {
    try {
      return passEnc.checkPassword(password, user.getPassword());
    } catch (EncryptionOperationNotPossibleException e) {
      return false;
    }
  }

  public User createRoot() {
    User root = new User(
      "root",
      "root",
      null,
      "root"
    );
    userDao.save(root);
    return root;
  }
}
