package com.example.sarafan.configuration;

import com.example.sarafan.models.User;
import com.example.sarafan.repositories.UserRepository;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/login**", "/", "/js/**", "/error**").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and()
                .csrf().disable();
//        http
//                .antMatcher("/**")
//                .authorizeHttpRequests()
//                .anyRequest().authenticated()
//                .and()
//                .csrf().disable();
    }

    @Bean
    public PrincipalExtractor principalExtractor(UserRepository userRepository) {
        return map -> {
            String id = map.get("sub") + "";
            User user = userRepository.findById(id).orElseGet(() -> {
                return new User(
                        id,
                        map.get("name") + "",
                        map.get("picture") + "",
                        map.get("email") + "",
                        map.get("gender") + "",
                        map.get("locale") + "",
                        null
                );
            });
            user.setLastVisit(LocalDateTime.now());

            return userRepository.save(user);
        };
    }
}
