package com.example.userservice;

import com.example.userservice.model.AppUser;
import com.example.userservice.model.Role;
import com.example.userservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class UserserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserserviceApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_MANAGER"));
            userService.saveRole(new Role(null, "Role_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

            userService.saveUser(new AppUser(null, "John", "john", "1234", new ArrayList<>()));
            userService.saveUser(new AppUser(null, "Will", "will", "1234", new ArrayList<>()));
            userService.saveUser(new AppUser(null, "Jim", "jim", "1234", new ArrayList<>()));
            userService.saveUser(new AppUser(null, "Arnold", "arnold", "1234", new ArrayList<>()));

            userService.addRoleToAppUSer("john", "ROLE_USER");
            userService.addRoleToAppUSer("john", "ROLE_MANAGER");
            userService.addRoleToAppUSer("will", "ROLE_MANAGER");
            userService.addRoleToAppUSer("jim", "ROLE_ADMIN");
            userService.addRoleToAppUSer("arnold", "ROLE_SUPER_ADMIN");
            userService.addRoleToAppUSer("arnold", "ROLE_ADMIN");
            userService.addRoleToAppUSer("arnold", "ROLE_USER");
        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
