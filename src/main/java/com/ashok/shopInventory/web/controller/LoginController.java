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

//    @GetMapping(value = {"/", "/home"})
//    public String index() {
//        return "redirect:/admin/index";
//    }
//

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

//    @RequestMapping(path = {"/submit"}, method = RequestMethod.POST)
//    public String submit(Model model, @RequestParam String email, @RequestParam String password) {
//        if(email.equals("Ashok081999@gmail.com") && password.equals("admin"))
//        {
//            return "redirect:/Admin/";
//        }
//        User userInDb = userService.getUserByEmail(email);
//        if(userInDb == null){
//            model.addAttribute("message","User "+email+" does not exist . please register first.");
//            return "SignUpForm";
//        }
//        if(password.equals(userInDb.getPassword())&&email.equals(userInDb.getEmail()))
//        {
//            return "redirect:/Eedu/"+userInDb.getId()+"/";
//        }
//model.addAttribute("message","your password is not Matched.");
//        return "loginPage";
//    }
//
//    @RequestMapping(path = {"/signUp"}, method = RequestMethod.GET)
//    public String signUP(Model model) {
//        return "SignUpForm";
//    }
//
//    @RequestMapping(path = {"/signUpSubmit"}, method = RequestMethod.POST)
//    public String signUPsubmit(Model model,@RequestParam String name, @RequestParam String email, @RequestParam String password) {
//        try {
//            User user = new User();
//            user.setPassword(password);
//            user.setEnabled(true);
//            user.setName(name);
//            user.setEmail(email);
//            User userInDb = userService.getUserByEmail(user.getEmail());
//            User user1= userService.getUserByName(user.getName());
//            if(userInDb != null){
//                model.addAttribute("message","User " + email + " already exist");
//                return "SignUpForm";
//            }
//            if(user1 != null){
//                model.addAttribute("message","User " + name+ " already exist");
//                return "SignUpForm";
//            }
//            LOG.info("creating User {}", user);
//            userService.save(user);
//            model.addAttribute("message", "User created successfully");
//        } catch (Exception e){
//            LOG.error("Exception occurred while creating user ",e);
//            model.addAttribute("message", "Exception occurred while creating user");
//        }
//        return "loginPage";
//    }
}
