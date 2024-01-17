package com.example.springsecurity.user.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final UserDTO userDTO;

    public UserDTO getUserDTO(){
        return userDTO;
    }



    public CustomUserDetails(UserDTO userDTO){
        this.userDTO = userDTO;
    }

    public Long getClubId() {
        return userDTO.getClubId();
    }
    public int getUserId() { return userDTO.getId();}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {    //role값을 리턴해주는 메서드

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return userDTO.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return userDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return userDTO.getUsername();
    }


    ///사용자의 아이디가 만료가 됐는지 등등...
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
