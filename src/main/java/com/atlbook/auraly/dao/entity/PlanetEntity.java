package com.atlbook.auraly.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name="auraly_planet")
public class PlanetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String zodiac;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
