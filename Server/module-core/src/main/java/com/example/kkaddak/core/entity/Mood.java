package com.example.kkaddak.core.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class Mood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String mood1;
    private String mood2;
    private String mood3;
    @Builder
    public Mood(String mood1, String mood2, String mood3) {
        this.mood1 = mood1;
        this.mood2 = mood2;
        this.mood3 = mood3;
    }
}
