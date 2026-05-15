package com.svenson95.track_e_backend.database.repository;

import com.svenson95.track_e_backend.database.model.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
  boolean existsByGoogleId(String googleId);

  Optional<User> findByGoogleId(String googleId);
}
