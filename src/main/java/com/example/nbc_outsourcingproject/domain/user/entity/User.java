package com.example.nbc_outsourcingproject.domain.user.entity;

import com.example.nbc_outsourcingproject.domain.auth.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
@SQLDelete(sql = "UPDATE user SET is_deleted = true WHERE id = ?")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    private String nickName;
    private String address;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private Boolean isDeleted = Boolean.FALSE;

    public User(String email, String password, String nickName, String address, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.address = address;
        this.userRole = userRole;
    }

    public User(Long id, String email, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.userRole = userRole;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
