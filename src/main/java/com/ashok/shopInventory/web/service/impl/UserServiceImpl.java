package com.ashok.shopInventory.web.service.impl;


import com.ashok.shopInventory.web.Repository.UserRepository;
import com.ashok.shopInventory.web.entity.User;
import com.ashok.shopInventory.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service("userServiceImpl")
public class UserServiceImpl implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public void save(User user) {
        User userInDB = getUserByEmail(user.getEmail());
        if(userInDB == null){
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }


    @Override
    public User getUserById(long userId) {
        return userRepository.getUserById(userId);
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.getUserByName(name);
    }
}
