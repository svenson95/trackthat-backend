package com.svenson95.track_e_backend.database.service;

import com.svenson95.track_e_backend.database.dto.UserDTO;
import com.svenson95.track_e_backend.database.mapper.UserMapper;
import com.svenson95.track_e_backend.database.model.User;
import com.svenson95.track_e_backend.database.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public UserService(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  public List<UserDTO> findAll() {
    return userRepository.findAll().stream().map(userMapper::toDto).toList();
  }

  public UserDTO createUser(UserDTO dto) {
    User user = userMapper.toEntity(dto);
    User saved = userRepository.save(user);
    return userMapper.toDto(saved);
  }
}
