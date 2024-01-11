package com.example.springsecurity.user.service;

import com.example.springsecurity.user.dto.CustomUserDetails;
import com.example.springsecurity.user.dto.UserDTO;
import com.example.springsecurity.user.entity.UserEntity;
import com.example.springsecurity.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;





    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        UserDTO userDTO = UserDTO.toUserDTO(userEntity);
        if (userDTO != null){
            return new CustomUserDetails(userDTO);
        }

        return null;
    }

    public UserDTO findByUserName(String writer) {
        UserEntity userEntity = userRepository.findByUsername(writer);
        return UserDTO.toUserDTO(userEntity);
    }
}
