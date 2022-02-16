package com.example.applestore.service.impl;

import com.example.applestore.model.CustomUserDetail;
import com.example.applestore.model.User;
import com.example.applestore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findUserByEmail((email));
        user.orElseThrow(() -> new UsernameNotFoundException("User not found"));

//        return user.map(CustomUserDetail::new).get();
        return new CustomUserDetail(user.get());

    }
}