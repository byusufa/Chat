package uz.pdp.chat.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.chat.entity.Attachment;
import uz.pdp.chat.entity.AttachmentContent;

import java.util.Optional;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, Integer> {

    Optional<AttachmentContent> findByAttachment(Attachment attachment);
}