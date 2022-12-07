package com.ashok.shopInventory.web.service.impl;


import com.ashok.shopInventory.web.Repository.IUserRoleRepository;
import com.ashok.shopInventory.web.entity.UserRole;
import com.ashok.shopInventory.web.service.IUserRoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userRoleServices")
public class UserRoleService implements IUserRoleServiceImpl {
    @Autowired
    IUserRoleRepository userRoleRepository;


    @Override
    public void save(UserRole user) {
        userRoleRepository.save(user);
    }
}
