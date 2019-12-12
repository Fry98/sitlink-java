package com.teqtrue.sitlink.controllers;

import javax.servlet.http.HttpServletRequest;

import com.teqtrue.sitlink.dao.ChannelDao;
import com.teqtrue.sitlink.dao.SubchatDao;
import com.teqtrue.sitlink.exceptions.RequestException;
import com.teqtrue.sitlink.model.Channel;
import com.teqtrue.sitlink.model.Subchat;
import com.teqtrue.sitlink.services.ChannelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/channel")
public class ChannelController {
  private final ChannelService chanService;
  private final SubchatDao subDao;
  private final ChannelDao chanDao;
  private final SimpMessagingTemplate ws;

  @Autowired
  public ChannelController(ChannelService chanService, SubchatDao subDao, ChannelDao chanDao, SimpMessagingTemplate ws) {
    this.chanService = chanService;
    this.subDao = subDao;
    this.chanDao = chanDao;
    this.ws = ws;
  }

  @PostMapping
  public void addChannel(
    @RequestParam(name = "sub", required = false) String sid,
    @RequestParam(name = "chan", required = false) String chan,
    HttpServletRequest req
  ) {
    if (req.getSession().getAttribute("id") == null) throw new RequestException("API Access Forbidden", HttpStatus.FORBIDDEN);
    if (sid == null || chan == null || chan.length() < 3 || chan.length() > 20 || !chan.matches("^[a-z0-9\\-_]*$")) {
      throw new RequestException("Invalid API Request", HttpStatus.BAD_REQUEST);
    }

    Subchat sub = subDao.findByUrl(sid);
    if (sub == null) throw new RequestException("Subchat doesn't exist!", HttpStatus.NOT_FOUND);
    if (sub.getAdmin().getId() != (Integer) req.getSession().getAttribute("id")) {
      throw new RequestException("Insufficient User Role", HttpStatus.FORBIDDEN);
    }

    if (chanService.exists(chan, sub)) {
      throw new RequestException("Channel name is already used!", HttpStatus.CONFLICT);
    }

    Channel newChan = new Channel(chan);
    sub.addChannel(newChan);
    chanService.persist(newChan);
  }

  @DeleteMapping
  public void removeChannel(
    @RequestParam(name = "sub", required = false) String sid,
    @RequestParam(name = "chan", required = false) String chan,
    HttpServletRequest req
  ) {
    if (req.getSession().getAttribute("id") == null) throw new RequestException("API Access Forbidden", HttpStatus.FORBIDDEN);
    if (sid == null || chan == null) {
      throw new RequestException("Invalid API Request", HttpStatus.BAD_REQUEST);
    }

    Channel chanObj = chanDao.findByNameAndSubchatUrl(chan, sid);
    if (chanObj == null) throw new RequestException("Channel doesn't exist!", HttpStatus.NOT_FOUND);
    if (chanObj.getSubchat().getAdmin().getId() != (Integer) req.getSession().getAttribute("id")) {
      throw new RequestException("Insufficient User Role", HttpStatus.FORBIDDEN);
    }
    if (chanObj.getSubchat().getChannels().size() < 2) {
      throw new RequestException("Subchat has to contain at least one channel!", HttpStatus.FORBIDDEN);
    }

    chanDao.delete(chanObj);
    ws.convertAndSend("/" + chanObj.getSubchat().getUrl() + "/" + chanObj.getName(), "refresh");
  }
}
