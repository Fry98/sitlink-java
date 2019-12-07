package com.teqtrue.sitlink.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.teqtrue.sitlink.dao.ChannelDao;
import com.teqtrue.sitlink.dao.UserDao;
import com.teqtrue.sitlink.exceptions.RequestException;
import com.teqtrue.sitlink.lib.ImageUploader;
import com.teqtrue.sitlink.model.Channel;
import com.teqtrue.sitlink.model.Image;
import com.teqtrue.sitlink.model.Message;
import com.teqtrue.sitlink.model.User;
import com.teqtrue.sitlink.services.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MessageController {
  private final ChannelDao chanDao;
  private final UserDao userDao;
  private final MessageService msgService;

  @Autowired
  public MessageController(ChannelDao chanDao, UserDao userDao, MessageService msgService) {
    this.chanDao = chanDao;
    this.userDao = userDao;
    this.msgService = msgService;
  }

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
    
    Channel chan = chanDao.findByNameAndSubchatUrl(chanName, sub);
    if (chan == null) throw new RequestException("Invalid Subchat and/or channel!", HttpStatus.BAD_REQUEST);

    Message newMsg = new Message();
    newMsg.setUser(user);

    if (img) {
      Image imgObj = ImageUploader.upload(content);
      if (imgObj == null) throw new RequestException("Invalid image file!", HttpStatus.BAD_REQUEST);
      newMsg.setImage(imgObj);
      newMsg.setContent(null);
    } else {
      newMsg.setContent(content);
    }

    newMsg.setChannel(chan);
    msgService.addNewMessage(newMsg);
  }

  @GetMapping("/message")
  public List<Object> getMessages(
    @RequestParam(name = "sub", required = false) String sub,
    @RequestParam(name = "chan", required = false) String chan,
    @RequestParam(name = "lim", required = false) String lim,
    @RequestParam(name = "skip", required = false) String skip,
    HttpServletRequest req
  ) {
    if (req.getSession().getAttribute("id") == null) throw new RequestException("API Access Forbidden", HttpStatus.FORBIDDEN);

    int skipNum;
    int limNum;
    try {
      skipNum = Integer.parseInt(skip);
      limNum = Integer.parseInt(lim);
    } catch (NumberFormatException e) {
      throw new RequestException("Invalid limit and/or offset!", HttpStatus.BAD_REQUEST);
    }
    if (skipNum < 0 || limNum < 0 || limNum > 200) {
      throw new RequestException("Invalid limit and/or offset!", HttpStatus.BAD_REQUEST);
    }

    Channel chanObj = chanDao.findByNameAndSubchatUrl(chan, sub);
    if (chanObj == null) throw new RequestException("Channel doesn't exist!", HttpStatus.BAD_REQUEST);
    List<Message> messages = msgService.getMessages(chanObj, skipNum, limNum);

    List<Object> res = new ArrayList<>();
    for (Message msg : messages) {
      Map<String, Object> msgObj = new HashMap<>();
      msgObj.put("id", msg.getId());
      msgObj.put("nick", msg.getUser().getNick());
      msgObj.put("upic", msg.getUser().getImg().getUrl());
      msgObj.put("img", msg.getImage() != null);
      msgObj.put("content", msg.getImage() == null ? msg.getContent() : msg.getImage().getUrl());
      msgObj.put("owned", msg.getUser().getId().equals(req.getSession().getAttribute("id")));
      res.add(msgObj);
    }
    Collections.reverse(res);
    return res;
  }

  @GetMapping("/update")
  public void getUpdate(HttpServletRequest req) {
    if (req.getSession().getAttribute("id") == null) throw new RequestException("API Access Forbidden", HttpStatus.FORBIDDEN);
    // TODO: Write method
  }
}
