package com.teqtrue.sitlink.config;

import javax.annotation.PostConstruct;
import com.teqtrue.sitlink.dao.ChannelDao;
import com.teqtrue.sitlink.dao.SubchatDao;
import com.teqtrue.sitlink.dao.UserDao;
import com.teqtrue.sitlink.model.Channel;
import com.teqtrue.sitlink.model.Subchat;
import com.teqtrue.sitlink.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSetup {

  @Autowired
  private SubchatDao subDao;

  @Autowired
  private UserDao userDao;

  @Autowired
  private ChannelDao chanDao;

  @PostConstruct
  public void initDb() {
    if (subDao.findByTitle("nexus") == null) {
      System.out.println("RUNNING DB SETUP!");
      User root = new User(
        "root",
        "root",
        "root",
        "root"
      );
      Subchat nexus = new Subchat(
        "Nexus",
        "The default subchat on SITLINK.",
        root
      );
      Channel general = new Channel("general");
      Channel videogames = new Channel("videogames");
      Channel tvShows = new Channel("tv_shows");
      Channel coding = new Channel("coding");
      Channel anime = new Channel("anime");
      nexus.addChannel(general, videogames, tvShows, coding, anime);

      userDao.persist(root);
      chanDao.persist(general, videogames, tvShows, coding, anime);
      subDao.persist(nexus);
    }
  }
}
