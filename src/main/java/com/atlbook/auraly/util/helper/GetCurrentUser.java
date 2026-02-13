package com.atlbook.auraly.util.helper;

import com.atlbook.auraly.dao.entity.UserEntity;
import com.atlbook.auraly.dao.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetCurrentUser {
  private final UserRepository userRepository;


  public UserEntity getCurrentUser(){
      System.out.println("test");
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    assert auth != null;
    if(!auth.isAuthenticated()){
      throw new RuntimeException("Not authentication");
    }

    String username = auth.getName();
      System.out.println(username);
    return userRepository.findByUsername(username).orElseThrow(
            () -> new UsernameNotFoundException("User Not Found")
    );

  }
}
