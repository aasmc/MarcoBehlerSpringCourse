package ru.aasmc.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.aasmc.web.forms.LoginForm;

import javax.validation.Valid;
import java.util.Date;

@Controller
public class WebsiteController {

    @GetMapping("/")
    public String homePage(
            Model model,
            @RequestParam(required = false, defaultValue = "Stranger") String username
    ) {
        model.addAttribute("username", username);
        model.addAttribute("currentDate", new Date());
        return "index.html";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login.html";
    }

    @PostMapping("/login")
    public String login(
            @ModelAttribute @Valid LoginForm loginForm,
            // basically, this is a container for all validation errors.
            BindingResult bindingResult, // MUST add directly after LoginForm!!!
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "login.html";
        }
        if (loginForm.getUsername().equals(loginForm.getPassword())) {
            return "redirect:/";
        }
        model.addAttribute("invalidCredentials", "true");
        return "login.html";
    }
}





















