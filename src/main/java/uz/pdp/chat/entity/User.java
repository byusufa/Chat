package uz.pdp.chat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User extends BaseEntity{
    @Column(unique = true, nullable = false)
    private String phone;
    private String password;
    private String firstName;
    private String lastName;
    @Transient
    private Integer unread;
    @ManyToOne
    private Attachment personalPhoto;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public User(String phone, String password, String firstName, String lastName, Attachment personalPhoto) {
        this.phone = phone;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalPhoto = personalPhoto;
    }

}
