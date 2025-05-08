package uz.pdp.chat.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.chat.entity.Attachment;
import uz.pdp.chat.entity.AttachmentContent;
import uz.pdp.chat.repo.AttachmentContentRepository;
import uz.pdp.chat.repo.AttachmentRepository;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final AttachmentContentRepository attachmentContentRepository;
    private final AttachmentRepository attachmentRepository;

    @GetMapping("/download/{attachmentId}")
    public void download(@PathVariable Integer attachmentId, HttpServletResponse response) throws IOException {
        Attachment attachment = attachmentRepository.findById(attachmentId).orElseThrow(() -> new RuntimeException("file not found"));
        AttachmentContent attachmentContent = attachmentContentRepository.findByAttachment(attachment).orElseThrow(() -> new RuntimeException("file not found"));
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + attachment.getFileName() + "\"");
        response.getOutputStream().write(attachmentContent.getContent());
    }

    @GetMapping("/get/{personalPhotoId}")
    public void getImage(@PathVariable Integer personalPhotoId, HttpServletResponse response) throws IOException {
        Optional<AttachmentContent> attachmentContent = attachmentContentRepository.findById(personalPhotoId);
        if (attachmentContent.isPresent()) {
            AttachmentContent attachmentContent1 = attachmentContent.get();
            response.getOutputStream().write(attachmentContent1.getContent());
        }
    }

}
