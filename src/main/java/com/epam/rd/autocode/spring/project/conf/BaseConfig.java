package com.epam.rd.autocode.spring.project.conf;

import com.epam.rd.autocode.spring.project.security.AccessTokenProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableConfigurationProperties(AccessTokenProperties.class)
public class BaseConfig{
    // Place your code here
}
