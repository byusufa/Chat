package uz.pdp.chat.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Attachment extends BaseEntity {
    private String fileName;

    public String getFileName() {
        return fileName.length()>10?fileName.substring(0,10)+"....":fileName;
    }
}


