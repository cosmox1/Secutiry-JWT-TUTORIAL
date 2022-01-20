package com.example.userservice.service;

import com.example.userservice.model.AppUser;
import com.example.userservice.model.Role;

import java.util.List;

public interface UserService {
    AppUser saveUser(AppUser appUser);
    Role saveRole(Role role);
    //username is unique in db
    void addRoleToAppUSer(String userName, String roleName);
    AppUser getUser(String userName);
    List<AppUser> getAppUsers();
}
