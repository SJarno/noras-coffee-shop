package com.sjarno.norascoffeeshop.security;

import com.sjarno.norascoffeeshop.services.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Profile("test")
@EnableWebSecurity
public class TestSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        String[] putMethods = new String[] {
                "/update-username", "/update-password"
        };

        String[] getMethods = new String[] {
                "/", "/greet"
        };
        String[] publicStaticContent = new String[] {
                "/", "/index.html", "/main*.js", "/polyfills*.js",
                "/runtime*.js", "/vendor*.js", "/styles*.css", "/assets/**",
                "/favicon.ico"
        };

        http.authorizeRequests()
                .mvcMatchers(HttpMethod.PUT, putMethods).authenticated()
                .mvcMatchers(HttpMethod.GET, publicStaticContent).permitAll()
                .mvcMatchers(getMethods).permitAll()
                .anyRequest().authenticated();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
