package com.item.itemshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.item.itemshop", "com.querydsl.jpa"})
public class ItemShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemShopApplication.class, args);
    }

}
