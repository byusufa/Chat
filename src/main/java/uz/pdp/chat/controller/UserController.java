package uz.pdp.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.chat.entity.Attachment;
import uz.pdp.chat.entity.AttachmentContent;
import uz.pdp.chat.entity.User;
import uz.pdp.chat.repo.AttachmentContentRepository;
import uz.pdp.chat.repo.AttachmentRepository;
import uz.pdp.chat.repo.UserRepository;

import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;

    public UserController(UserRepository userRepository, AttachmentRepository attachmentRepository, AttachmentContentRepository attachmentContentRepository) {
        this.userRepository = userRepository;
        this.attachmentRepository = attachmentRepository;
        this.attachmentContentRepository = attachmentContentRepository;
    }

    @GetMapping("/add")
    public String add() {
        return "addUser";
    }

    @PostMapping("/add")
    public String addUser(@RequestParam String phone, @RequestParam String password,
                          @RequestParam String firstName, @RequestParam String lastName,
                          @RequestParam MultipartFile personalPhoto) throws IOException {

        Attachment attachment = new Attachment(personalPhoto.getOriginalFilename());
        attachmentRepository.save(attachment);
        AttachmentContent attachmentContent = new AttachmentContent(personalPhoto.getBytes(), attachment);
        attachmentContentRepository.save(attachmentContent);
        User user = new User(
                phone, password, firstName, lastName, attachment
        );
        userRepository.save(user);
        return "redirect:/chat";

    }

    @GetMapping("/update/{id}")
    public String updateUser(@PathVariable Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow();
        model.addAttribute("user", user);
        return "updateUser";
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam Integer id, @RequestParam String phone,
                             @RequestParam String password, @RequestParam String firstName,
                             @RequestParam String lastName, @RequestParam(required = false) MultipartFile personalPhoto,
                             Model model) throws IOException {
        User user = userRepository.findById(id).orElseThrow();
        user.setPhone(phone);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        if (personalPhoto != null && !personalPhoto.isEmpty()) {
            Attachment attachment = new Attachment();
            attachment.setFileName(personalPhoto.getOriginalFilename());
            attachmentRepository.save(attachment);

            AttachmentContent attachmentContent = new AttachmentContent();
            attachmentContent.setContent(personalPhoto.getBytes());
            attachmentContentRepository.save(attachmentContent);

            user.setPersonalPhoto(attachment);
        }
        userRepository.save(user);
        return "redirect:/chat";

    }


}
