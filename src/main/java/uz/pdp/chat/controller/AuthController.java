package uz.pdp.chat.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.chat.entity.User;
import uz.pdp.chat.payload.LoginReq;
import uz.pdp.chat.repo.UserRepository;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public String authenticate(@ModelAttribute LoginReq loginReq, HttpServletRequest request, Model model) {
        Optional<User> userOptional = userRepository.findByPhoneAndPassword(loginReq.getPhone(), loginReq.getPassword());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            request.getSession().setAttribute("currentUser", user);
            model.addAttribute("currentUser", user);
            return "redirect:/chat";
        }else{
            return "redirect:/login";
        }

    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login";
    }

    @GetMapping("/resetPassword")
    public String showResetPasswordForm() {
        return "resetPassword";
    }


    @PostMapping("/reset-password")
    public String password(@RequestParam String phone, @RequestParam String newPassword, Model model) {
        Optional<User> userOptional = userRepository.findByPhone(phone);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setPassword(newPassword);
            userRepository.save(user);
            model.addAttribute("message", "Password has been reset");
            return "login";
        }else {
            model.addAttribute("error", "User not found!");
            return "resetPassword";
        }

    }


}
