package com.zhenikhov;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.zhenikhov.repository")
@PropertySource("application.properties")
public class JpaConfig {

}
