package com.sdlab.sdlab.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
        authenticationMgr.inMemoryAuthentication()
                .withUser("admin").password("{noop}123").roles("ADMIN")
                .and()
                .withUser("student").password("{noop}123").roles("STUDENT");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().httpBasic().and().authorizeRequests()
                .antMatchers("/students/**").permitAll()
                .antMatchers("/labs/**").permitAll()
                .antMatchers("/assignments/**").permitAll()
                ;
                //.antMatchers("/students").hasRole("ADMIN")
                //.antMatchers("/labs").hasAnyRole("ADMIN", "STUDENT");
//                .and()
//                .formLogin().loginPage("/loginPage")
//                .defaultSuccessUrl("/homePage")
//                .failureUrl("/loginPage?error")
//                .usernameParameter("username").passwordParameter("password")
//                .and()
//                .logout().logoutSuccessUrl("/loginPage?logout");

    }
}

