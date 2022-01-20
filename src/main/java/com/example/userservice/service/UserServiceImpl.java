package com.example.userservice.service;

import com.example.userservice.model.AppUser;
import com.example.userservice.model.Role;
import com.example.userservice.repository.RoleRepo;
import com.example.userservice.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser user= userRepo.findByUserName(username);
    if (user == null){
        log.error("User not found in the db");
        throw new UsernameNotFoundException("USer not found in the db");
    } else {
        log.info("User found in the db: {}", username);
    }
        Collection<SimpleGrantedAuthority> authorities= new ArrayList<>();
    user.getRoles().forEach(role -> {authorities.add(new SimpleGrantedAuthority(role.getName()));});
        return new org.springframework.security.core.userdetails.User(user.getName(),user.getPassword(),authorities);
    }

    @Override
    public AppUser saveUser(AppUser appUser) {
        log.info("Saving new user {} to the database", appUser.getName());
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return userRepo.save(appUser);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role to the database" + role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToAppUSer(String userName, String roleName) {
        log.info("Adding role {} to user {}",roleName,userName);
        AppUser user= userRepo.findByUserName(userName);
        Role role= roleRepo.findByName(roleName);
        user.getRoles().add(role);

    }

    @Override
    public AppUser getUser(String userName) {
        log.info("Fetching user{}",userName);
        return userRepo.findByUserName(userName);
    }

    @Override
    public List<AppUser> getAppUsers() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }


}
