package com.example.smart_contact_manager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.smart_contact_manager.Dao.UserRepository;

import com.example.smart_contact_manager.entities.User;


import jakarta.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("title", "Home Page");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model){
        model.addAttribute("title", "About Page");
        return "about";
    }

    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("title", "SignUp Page");
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/do_register")
    public String registerUsr(@Valid @ModelAttribute("user") User user , 
    BindingResult bindingResult,@RequestParam(value = "aggreement",defaultValue = "false") 
    boolean aggreement , Model model){
        
        try {
            if(!aggreement){
                System.out.println("T & C Are Not Aggreed");
                throw new Exception();
            }

            if (bindingResult.hasErrors()) {
                System.out.println("Error"+bindingResult.toString());
                model.addAttribute("user", user);
                return "signup";
            }
    
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImageUrl("abc.png");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
    
            System.out.println("Agreement = "+aggreement);
            System.out.println(user);
    
            User result = this.userRepository.save(user);
            model.addAttribute("user", new User());

            return "signup";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);
            return "signup";
        }
      

    }

    @GetMapping("/signin")
    public String customLogin(Model model){
        model.addAttribute("title", "Login Page");
        return "login";
    }

}
