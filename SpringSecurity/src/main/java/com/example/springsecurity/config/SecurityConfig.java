package com.example.springsecurity.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){       //모든 비밀번호를 암호화하는 메서드

        return new BCryptPasswordEncoder();
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests( (auth)-> auth       //3.1.x 버전부터는 이런 형식으로 작성해야함.
                        //임시
                        .requestMatchers("/**").permitAll()
                        //
                        .requestMatchers("/","/login","/join","/joinProc","/board/**").permitAll()  //main페이지와 로그인 페이지는 모두가 접근 가능하게
                        .requestMatchers("/admin").hasRole("ADMIN")     //어드민페이지는 어드민인 사용자만 접근 가능하게
                        .requestMatchers("/board/**").hasAnyRole("ADMIN","USER")
                        .requestMatchers("/my/**").hasAnyRole("ADMIN","USER")
                        .anyRequest().authenticated()
                );

        http
                .formLogin( (auth)-> auth.loginPage("/login")       //이렇게 하면, admin페이지 접근 시, 오류가 아닌 로그인 페이지로 넘어가게 됨
                        .loginProcessingUrl("/loginProc")       //로그인한 데이터를 해당 경로로 보내주는 역할
                        .permitAll()
                );

        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                );


        http
                .sessionManagement( (auth)-> auth
                        .sessionFixation().changeSessionId()
                );



        http
                .logout( (auth) -> auth.logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                );

        http
                .csrf((auth) -> auth.disable());



        return http.build();
    }

}
