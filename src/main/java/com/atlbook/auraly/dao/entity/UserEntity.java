package com.atlbook.auraly.dao.entity;

import com.atlbook.auraly.enums.Gender;
import com.atlbook.auraly.enums.UserRole;
import com.atlbook.auraly.enums.ZodiacSign;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table (name="auraly_users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name="firstname", nullable = false)
    String firstName;
    @Column(name="lastname", nullable = false)
    String lastName;
    @Column(name="username", nullable = false, unique = true)
    String username;
    @Column(name="password", nullable = false)
    String password;
    @Column(name="email", nullable = false,unique = true)
    String email;
    @Column(name="phone", nullable = false)
    String phone;
    LocalDate birthDate;
    LocalTime birthTime;
    @Column(name="created_at", nullable = false)
    @CreationTimestamp
    LocalDateTime createdAt;
    @Column(name="updated_at", nullable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;
    @Enumerated(EnumType.STRING)
    ZodiacSign zodiacSign;
    UserRole role;
    @Enumerated(EnumType.STRING)
    Gender Other;


}
