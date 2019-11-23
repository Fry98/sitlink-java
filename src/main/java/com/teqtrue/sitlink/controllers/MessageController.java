package com.teqtrue.sitlink.controllers;

import javax.servlet.http.HttpServletRequest;

import com.teqtrue.sitlink.dao.ChannelDao;
import com.teqtrue.sitlink.dao.UserDao;
import com.teqtrue.sitlink.exceptions.RequestException;
import com.teqtrue.sitlink.lib.ImageUploader;
import com.teqtrue.sitlink.model.Channel;
import com.teqtrue.sitlink.model.Message;
import com.teqtrue.sitlink.model.User;
import com.teqtrue.sitlink.services.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MessageController {

  @Autowired
  private ChannelDao chanDao;

  @Autowired
  private UserDao userDao;

  @Autowired
  private MessageService msgService;
  
  @PostMapping("/message")
  public void addMessage(
    @RequestParam(name = "sid", required = false) String sub,
    @RequestParam(name = "chan", required = false) String chanName,
    @RequestParam(name = "img", required = false) Boolean img,
    @RequestParam(name = "content", required = false) String content,
    HttpServletRequest req
  ) {
    if (req.getSession().getAttribute("id") == null) throw new RequestException("API Access Forbidden", HttpStatus.FORBIDDEN);
    Integer uid = (Integer) req.getSession().getAttribute("id");
    User user = userDao.findById(uid).get();
    
    Channel chan = chanDao.findByNameAndSubchatTitle(chanName, sub);
    if (chan == null) throw new RequestException("Invalid Subchat and/or channel!", HttpStatus.BAD_REQUEST);

    Message newMsg = new Message();
    newMsg.setUser(user);
    newMsg.setImage(img);

    String msgContent = content;
    if (img) {
      msgContent = ImageUploader.upload(content);
      if (msgContent == null) throw new RequestException("Invalid image file!", HttpStatus.BAD_REQUEST);
    }

    newMsg.setContent(msgContent);
    newMsg.setChannel(chan);

    msgService.addNewMessage(newMsg);
  }
}
