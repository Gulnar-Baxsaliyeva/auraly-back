package com.atlbook.auraly.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "auraly_zodiac")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ZodiacEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String zodiac;
    @Column(columnDefinition = "text")
    String description;
}
