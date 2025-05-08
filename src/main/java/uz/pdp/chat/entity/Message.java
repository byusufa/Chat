package uz.pdp.chat.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class Message extends BaseEntity {
    private String text;
    @ManyToOne
    private Attachment file;
    @ManyToOne
    private User fromUser;
    @ManyToOne
    private User toUser;

    @CreationTimestamp
    private LocalDateTime dateTime;

    public String getDateTime() {
        return dateTime.format(
                DateTimeFormatter.ofPattern("HH:mm")
        );
    }

    private boolean hasRead;
}
