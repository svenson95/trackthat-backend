package com.svenson95.track_e_backend.database.controller;

import com.svenson95.track_e_backend.database.dto.UserDTO;
import com.svenson95.track_e_backend.database.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired private UserService userService;

  @GetMapping("/")
  public List<UserDTO> getAllUsers() {
    return userService.findAll();
  }

  @PostMapping("/add")
  public UserDTO postAddUser(@RequestBody UserDTO user) {
    return userService.createUser(user);
  }
}
