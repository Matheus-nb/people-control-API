package com.api.peoplecontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PeopleControlApplication {

    public static void main(String[] args) {
        SpringApplication.run(PeopleControlApplication.class, args);
    }

    @GetMapping("/")
    public String index() {
        return "1° Atividade SpringBoot SPC";
    }
}

