

package com.ashok.shopInventory.web.controller;

import com.ashok.shopInventory.web.entity.Role;
import com.ashok.shopInventory.web.entity.User;
import com.ashok.shopInventory.web.service.IRoleService;
import com.ashok.shopInventory.web.service.IUserRoleServiceImpl;
import com.ashok.shopInventory.web.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping({"/user"})
public class UserManagementController {
    private static final Logger LOG = LoggerFactory.getLogger(UserManagementController.class);
    static final String PATH = "/user";
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserRoleServiceImpl userRoleService;

    public UserManagementController() {
    }

    @RequestMapping(
            value = {"/create"},
            method = {RequestMethod.GET}
    )
    public String userManagement(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("fieldTypeMap", this.getFieldTypeMap(User.class));
        return "createUser";
    }

    @RequestMapping(
            value = {"/create/role"},
            method = {RequestMethod.GET}
    )
    public String createRole(Model model) {
        model.addAttribute("Role", new Role());
        model.addAttribute("fieldTypeMap", this.getFieldTypeMap(Role.class));
        return "createRole";
    }

    @RequestMapping(
            value = {"/view"},
            method = {RequestMethod.GET}
    )
    public String viewAssignRole(Model model) {
        return "assignRole";
    }

    @RequestMapping(
            value = {"/search"},
            method = {RequestMethod.POST}
    )
    public String searchUser(Model model, String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            model.addAttribute("message", "No user found");
            return "assignRole";
        } else {
            model.addAttribute("user", user);
            model.addAttribute("fieldTypeMap", this.getFieldTypeMap(User.class));
            model.addAttribute("allRoles", this.roleService.getAllRoles());
            return "assignRole";
        }
    }

    @RequestMapping(
            value = {"/save"},
            method = {RequestMethod.POST}
    )
    public String saveUser(Model model, User user) {
        try {
            model.addAttribute("user", new User());
            model.addAttribute("fieldTypeMap", this.getFieldTypeMap(User.class));
            User userIn = userService.getUserByName(user.getName());
            if (userIn != null) {
                model.addAttribute("message", "User " + user.getName() + " already exist please provide different username");
                return "createUser";
            } else {
                User userInDb = userService.getUserByEmail(user.getEmail());
                if (userInDb != null) {
                    model.addAttribute("message", "User " + user.getEmail() + " already exist");
                    return "createUser";
                } else {
                    LOG.info("creating User {}", user);
                    this.userService.save(user);
                    model.addAttribute("message", "User created successfully");
                    return "redirect:/user/view";
                }
            }
        } catch (Exception var5) {
            LOG.error("Exception occurred while creating user ", var5);
            model.addAttribute("message", "Exception occurred while creating user");
            return "createUser";
        }
    }

    @RequestMapping(
            value = {"/assign/role"},
            method = {RequestMethod.POST}
    )
    public String assignRole(Model model, User user) {
        try {
            User userInDb = this.userService.getUserByEmail(user.getEmail());
            userInDb.setRoles(user.getRoles());
            LOG.info("updating User {}", user);
            model.addAttribute("message", "Role assigned successfully");
            this.userService.save(userInDb);
            return "redirect:/login";
        } catch (Exception var4) {
            model.addAttribute("message", "Exception occurred while assigning role");
            LOG.error("Exception occurred while assigning role {} ", var4, user.getEmail());
            return "redirect:/user/view";
        }
    }

    @RequestMapping(
            value = {"/save/role"},
            method = {RequestMethod.POST}
    )
    public String saveRole(Model model, String roleName) {
        try {
            model.addAttribute("Role", new Role());
            model.addAttribute("fieldTypeMap", this.getFieldTypeMap(Role.class));
            Role roleInDB = this.roleService.getRoleByName(roleName);
            if (roleInDB != null) {
                model.addAttribute("message", "Role " + roleName + " already exist");
                return "createRole";
            }

            LOG.info("creating Role {}", roleName);
            model.addAttribute("message", "Role created successfully");
            this.roleService.save(roleName);
        } catch (Exception var4) {
            model.addAttribute("message", "Exception occurred while creating role");
            LOG.error("Exception occurred while creating role {} ", var4, roleName);
        }

        return "createRole";
    }

    private Map<String, String> getFieldTypeMap(Class clazz) {
        Map<String, String> fieldNameTypeMap = new LinkedHashMap();
        if (clazz == null) {
            return fieldNameTypeMap;
        } else {
            Field[] declaredFields = clazz.getDeclaredFields();
            Field[] var4 = declaredFields;
            int var5 = declaredFields.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Field field = var4[var6];
                fieldNameTypeMap.put(field.getName(), String.valueOf(field.getType()));
            }

            return fieldNameTypeMap;
        }
    }
}
