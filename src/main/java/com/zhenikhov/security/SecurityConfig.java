package com.zhenikhov.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final RestAuthenticationEntryPoint entryPoint;
    private final AuthSuccessHandler successHandler;
    private final SimpleUrlAuthenticationFailureHandler failureHandler;
    private final PasswordEncoder encoder;
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    public SecurityConfig(RestAuthenticationEntryPoint entryPoint,
                          AuthSuccessHandler authSuccessHandler,
                          SimpleUrlAuthenticationFailureHandler failureHandler,
                          PasswordEncoder encoder,
                          AuthenticationProvider provider) {
        this.entryPoint = entryPoint;
        this.successHandler = authSuccessHandler;
        this.failureHandler = failureHandler;
        this.encoder = encoder;
        this.authenticationProvider = provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/client/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                .formLogin()
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .and()
                .logout();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }
}
