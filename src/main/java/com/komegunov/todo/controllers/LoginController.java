package com.komegunov.todo.controllers;

import com.komegunov.todo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new UserRepository());
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute("user") @Valid UserRepository userRepository,
                                  BindingResult result) {

        logger.info("Now user {}", userRepository);
        if (result.hasErrors()) {
            return "register";
        }

        if (userRepository.getPassword().equals(userRepository.getMatchingPassword())) {
            result.rejectValue("password", "", "Password not matching");
            return "register";
        }

        return "redirect:/login";
    }

}
