package com.teqtrue.sitlink.config;

import javax.annotation.PostConstruct;
import com.teqtrue.sitlink.model.Channel;
import com.teqtrue.sitlink.model.Subchat;
import com.teqtrue.sitlink.model.User;
import com.teqtrue.sitlink.services.ChannelService;
import com.teqtrue.sitlink.services.SubchatService;
import com.teqtrue.sitlink.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSetup {

  @Autowired
  private UserService userService;

  @Autowired
  private SubchatService subService;

  @Autowired
  private ChannelService chanService;

  @PostConstruct
  public void initDb() {
    if (subService.exists("nexus")) {
      System.out.println("RUNNING DB SETUP!");
      User root = userService.createRoot();
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

      chanService.persist(general, videogames, tvShows, coding, anime);
      subService.persist(nexus);
    }
  }
}
