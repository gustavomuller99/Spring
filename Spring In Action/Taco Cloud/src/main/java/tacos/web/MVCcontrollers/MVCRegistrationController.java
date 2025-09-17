package tacos.web.MVCcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.web.DTOs.RegistrationForm;
import tacos.web.repository.UserRepository;

@Controller
@RequestMapping("/MVCregister")
public class MVCRegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MVCRegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm(Model model) {
        return "registration";
    }

    @PostMapping
    public String processRegistration(@ModelAttribute("user")RegistrationForm form, Model model) {
        userRepository.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }
}
