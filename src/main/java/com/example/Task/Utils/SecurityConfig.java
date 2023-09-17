package com.example.Task.Utils;

import com.example.Task.Services.CreateUserService;
import com.example.Task.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.
                httpBasic().and().
                csrf().disable().
                authorizeRequests()
                .antMatchers(HttpMethod.GET, "/user/**").hasAuthority(Constants.USER_INFO_AUTHORITY)
                .antMatchers(HttpMethod.GET, "/user/id/**").hasAuthority(Constants.USER_INFO_AUTHORITY)
                .antMatchers(HttpMethod.POST, "/list/add/**").hasAuthority(Constants.ADD_ITEM_AUTHORITY)
                .antMatchers(HttpMethod.DELETE, "/list/delete/**").hasAuthority(Constants.DELETE_ITEM_AUTHORITY)
                .antMatchers("/**").permitAll()
                .and()
                .formLogin().loginPage("/login") // Specify your login page URL
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }
}