package com.teqtrue.sitlink.dao;

import com.teqtrue.sitlink.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
  public User findByNick(String nick);
}
