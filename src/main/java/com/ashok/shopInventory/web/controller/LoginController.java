package com.ashok.shopInventory.web.controller;


import com.ashok.shopInventory.web.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(LoginController.PATH)
public class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private IUserService userService;

    static final String PATH = "/";

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String Home(Model model) {
        model.addAttribute("home", "home");
        return "home";
    }
    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public String xxyz(Model model) {
        model.addAttribute("home", "home");
        return "redirect:/Eedu/";
    }
    @RequestMapping(value = {"/pidhi"}, method = RequestMethod.GET)
    public String pidhi(Model model) {
        model.addAttribute("home", "home");
        return "genology";
    }
}
