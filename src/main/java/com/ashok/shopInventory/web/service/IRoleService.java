package com.ashok.shopInventory.web.service;


import com.ashok.shopInventory.web.entity.Role;

import java.util.List;

public interface IRoleService {

    Role getRoleByName(String email);
    void save(String roleName);
    List<Role> getAllRoles();
}
