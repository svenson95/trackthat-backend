package com.svenson95.track_e_backend.health.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class HealthController {
  @GetMapping("/health")
  public ResponseEntity<Void> health() {
    return ResponseEntity.ok().build();
  }
}
