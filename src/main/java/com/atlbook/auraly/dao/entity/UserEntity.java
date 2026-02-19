package com.atlbook.auraly.dao.entity;

import com.atlbook.auraly.util.enums.Gender;
import com.atlbook.auraly.util.enums.UserRole;
import com.atlbook.auraly.util.enums.WorkStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table (name="auraly_user2")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name="firstname", nullable = false)
    String firstName;
    @Column(name="lastname", nullable = true)
    String lastName;
    @Column(name="username", nullable = true)
    String username;
    @Column(name="password", nullable = false)
    String password;
    @Column(name="email",unique = true)
    String email;
    @Column(name="phone", nullable = true)
    String phone;
    @Column(name="birthDate")
    LocalDate birthDate;
    @Column(name="birthTime")
    LocalTime birthTime;
    @Column(name="created_at", nullable = false)
    @CreationTimestamp
    LocalDateTime createdAt;
    @Column(name="updated_at", nullable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;
    Double latitude;
    Double longitude;
    String location;


    @OneToMany(mappedBy = "userEntity",cascade = CascadeType.ALL,orphanRemoval = true)
    List<PlanetEntity> planetEntities;

    @Enumerated(EnumType.STRING)
    UserRole role;
    @Enumerated(EnumType.STRING)
    Gender gender;
    @Enumerated(EnumType.STRING)
    WorkStatus workStatus;



}
