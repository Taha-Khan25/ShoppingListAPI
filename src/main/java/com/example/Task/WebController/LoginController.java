package com.example.Task.WebController;

import com.example.Task.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/dashboard")
    public String Home() {
        return "dashboard";
    }

    @GetMapping("/welcome")
    public String greeting() {
        return "welcome";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            UserDetails userDetails = userService.loadUserByUsername(username);
            if(userDetails==null)
            {
                model.addAttribute("loginError", true);
                return "login";
            }
            if (passwordEncoder.matches(password, userDetails.getPassword())) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            } else {
                model.addAttribute("loginError", true);
                return "login";
            }
            return "redirect:/dashboard";

        } catch (UsernameNotFoundException ex) {
            model.addAttribute("loginError", true);
            return "login"; // Redirect back to the login page with an error message
        }
    }
}