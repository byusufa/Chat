package uz.pdp.chat.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.pdp.chat.entity.User;
import uz.pdp.chat.repo.UserRepository;

@Component
@RequiredArgsConstructor
public class Runnable implements CommandLineRunner {
    public final UserRepository userRepository;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String update;

    @Override
    public void run(String... args) throws Exception {
        if (update.equals("create")) {
            User user1 = new User("1", "1", "Eshmat", "Toshmatov", null);
            userRepository.save(user1);
        }

    }
}
