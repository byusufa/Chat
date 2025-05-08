package uz.pdp.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.pdp.chat.entity.User;
import uz.pdp.chat.repo.UserRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationService {

    private final UserRepository userRepository;

    public void manage(List<User> all, User currentUser) {
        for (User user : all) {
            int count = userRepository.getNotificationCount(currentUser.getId(),user.getId());
            user.setUnread(count);
        }

    }
}
