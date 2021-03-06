package com.komegunov.todo.controllers;

import com.komegunov.todo.repr.UserRepr;
import com.komegunov.todo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new UserRepr());
        return "login";
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute("user") @Valid UserRepr userRepr,
                                  BindingResult result) {

        logger.info("Now user {}", userRepr);
        if (result.hasErrors()) {
            return "login";
        }

        if (!userRepr.getPassword().equals(userRepr.getMatchingPassword())) {
            result.rejectValue("password", "", "Password not matching");
            return "login";
        }

        userService.create(userRepr);
        return "login";
    }

}
