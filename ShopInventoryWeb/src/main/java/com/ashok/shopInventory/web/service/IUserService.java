package com.ashok.shopInventory.web.service;


import com.ashok.shopInventory.web.entity.User;

public interface IUserService {
    User getUserByEmail(String email);
    void save(User user);

    User getUserById(long userId);

    User getUserByName(String name);
}
