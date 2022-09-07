package com.zemoso.systemdesignbootcamp.tinyurlapi;

import io.mongock.runner.springboot.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMongock
@SpringBootApplication
public class TinyUrlApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TinyUrlApiApplication.class, args);
    }

}
