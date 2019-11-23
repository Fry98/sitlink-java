package com.teqtrue.sitlink.services;

import com.teqtrue.sitlink.dao.UserDao;
import com.teqtrue.sitlink.model.User;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserDao userDao;

  @Autowired
  private StrongPasswordEncryptor passEnc;

  public boolean isNicknameTaken(String nickname) {
    return userDao.findByNick(nickname) != null;
  }

  public User createUser(String nick, String mail, String img, String password) {
    String pwdHash = passEnc.encryptPassword(password);
    User newUser = new User(
      nick,
      mail,
      img,
      pwdHash
    );
    userDao.persist(newUser);
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
      "root",
      "root"
    );
    userDao.persist(root);
    return root;
  }
}
