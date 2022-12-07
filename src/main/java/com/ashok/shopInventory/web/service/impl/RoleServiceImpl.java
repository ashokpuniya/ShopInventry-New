package com.ashok.shopInventory.web.service.impl;


import com.ashok.shopInventory.web.Repository.RoleRepository;
import com.ashok.shopInventory.web.entity.Role;
import com.ashok.shopInventory.web.service.IRoleService;
import com.ashok.shopInventory.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("roleServiceImpl")
public class RoleServiceImpl implements IRoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    IUserService userService;

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.getRoleByName(name);
    }

    @Override
    public void save(String roleName) {
        Role role = new Role();
        role.setName(roleName);
        roleRepository.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

}
