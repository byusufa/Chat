package uz.pdp.chat.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.chat.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {

}