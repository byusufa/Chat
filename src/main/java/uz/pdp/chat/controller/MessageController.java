package uz.pdp.chat.controller;

import jakarta.servlet.annotation.MultipartConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.chat.entity.Attachment;
import uz.pdp.chat.entity.AttachmentContent;
import uz.pdp.chat.entity.Message;
import uz.pdp.chat.entity.User;
import uz.pdp.chat.repo.AttachmentContentRepository;
import uz.pdp.chat.repo.AttachmentRepository;
import uz.pdp.chat.repo.MessageRepository;
import uz.pdp.chat.repo.UserRepository;

import java.io.IOException;

@Controller
@Transactional
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;

    @PostMapping("/send")
    public String sendMessage(@RequestParam String text, @RequestParam Integer to,
                              @SessionAttribute User currentUser, @RequestParam(required = false) MultipartFile file) throws IOException {


        Message message = new Message(
                text,
                saveAttachment(file),
                currentUser,
                userRepository.findById(to).get(),
                null,
                false
        );
        messageRepository.save(message);
        return "redirect:/chat?userId=" + to;

    }

    public Attachment saveAttachment(MultipartFile file) throws IOException {
        if (file != null) {
            Attachment attachment = new Attachment(
                    file.getOriginalFilename());
            attachmentRepository.save(attachment);
            AttachmentContent attachmentContent = new AttachmentContent(
                    file.getBytes(), attachment
            );
            attachmentContentRepository.save(attachmentContent);
            return attachment;
        }else{
            return null;
        }
    }


}
