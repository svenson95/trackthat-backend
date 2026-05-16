package com.svenson95.track_e_backend.auth.service;

import com.svenson95.track_e_backend.database.model.User;
import com.svenson95.track_e_backend.database.repository.UserRepository;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {
  @Autowired private UserRepository userRepository;

  public User findByUserId(String googleId) {
    return userRepository.findByGoogleId(googleId).get();
  }

  public User findOrCreateUser(Map<String, Object> userInfo) {
    String id = (String) userInfo.get("userId");
    return userRepository.findByGoogleId(id).orElseGet(() -> this.createNewUser(userInfo));
  }

  public User createNewUser(Map<String, Object> userInfo) {
    User newUser = new User();
    newUser.setGoogleId((String) userInfo.get("userId"));
    newUser.setEmail((String) userInfo.get("email"));
    newUser.setName((String) userInfo.get("name"));
    newUser.setPicture((String) userInfo.get("picture"));
    newUser.setWeight(0);
    newUser.setHeight(0);
    return userRepository.save(newUser);
  }
}
