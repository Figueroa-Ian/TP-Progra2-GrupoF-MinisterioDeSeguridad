package com.PROGRA2.BACKEND;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TpBackendSpringApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
        return application.sources(TpBackendSpringApplication.class);
    }
            
    public static void main(String[] args) {
        SpringApplication.run(TpBackendSpringApplication.class, args);
    }

}
