package com.zhenikhov.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final RestAuthenticationEntryPoint entryPoint;
    private final AuthSuccessHandler successHandler;
    private final SimpleUrlAuthenticationFailureHandler failureHandler;
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    public SecurityConfig(RestAuthenticationEntryPoint entryPoint,
                          AuthSuccessHandler authSuccessHandler,
                          SimpleUrlAuthenticationFailureHandler failureHandler,
                          AuthenticationProvider provider) {
        this.entryPoint = entryPoint;
        this.successHandler = authSuccessHandler;
        this.failureHandler = failureHandler;
        this.authenticationProvider = provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .antMatchers("actuator/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .and()
                .logout().permitAll();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }
}
