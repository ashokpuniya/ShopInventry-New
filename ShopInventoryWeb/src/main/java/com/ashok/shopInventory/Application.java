package com.ashok.shopInventory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan(basePackages = {"com.ashok.shopInventory.web"})
@EntityScan(basePackages = {"com.ashok.shopInventory.web.entity"})
@EnableJpaRepositories({"com.ashok.shopInventory.web.repository"})
@PropertySource({"classpath:application.properties", "classpath:db.properties"})
public class Application {

    public static final String COMPONENT_NAME = "shopInventory";
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        try {
            SpringApplication.run(Application.class, args);
        }catch (Exception e){
            System.out.println(e);
        }
    }


}

