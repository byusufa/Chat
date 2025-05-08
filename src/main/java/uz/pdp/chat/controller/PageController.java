package uz.pdp.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import uz.pdp.chat.entity.Message;
import uz.pdp.chat.entity.User;
import uz.pdp.chat.repo.MessageRepository;
import uz.pdp.chat.repo.UserRepository;
import uz.pdp.chat.service.NotificationService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final NotificationService notificationService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/chat")
    public String chatPage(Model model, @RequestParam(required = false) Integer userId, @SessionAttribute User currentUser) {
        List<User> all = userRepository.findAll();
        all.removeIf(item -> item.getId().equals(currentUser.getId()));
        notificationService.manage(all, currentUser);
        model.addAttribute("users", all);
        if (userId != null) {
            model.addAttribute("selectedUser",
                    all.stream().filter(item -> item.getId().equals(userId)).findFirst().get());
            List<Message> chatHistory = messageRepository.getChatHistory(currentUser.getId(), userId);
            for (Message message : chatHistory) {
                if (!message.getFromUser().getId().equals(currentUser.getId())) {
                    message.setHasRead(true);
                }
            }
            messageRepository.saveAll(chatHistory);

            model.addAttribute("messages", chatHistory);
            model.addAttribute("currentUser", currentUser);
        }
        return "chat";
    }
}
