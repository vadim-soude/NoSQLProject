package fr.vadimsoude;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestApplication {
    //http://localhost:8080/input

    public static void main(String[] args) {
        init();
        SpringApplication.run(RestApplication.class, args);
    }

    public static void init(){
    }

}