package com.example.kkaddak.core.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString(callSuper = true)
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "char(36)")
    @Type(type = "uuid-char")
    private UUID songUuid;

    private String title;

    private String songPath;

    private String coverPath;

    private String genre;

    private Long uploadDate;

    private Long views;

    @ManyToOne
    @JoinColumn(name = "member_id")
//    @ToString.Exclude
    private Member member;

    @OneToOne
    private Mood moods;

    @Builder
    public Song(String title, String songPath, String coverPath, String genre, Mood moods, Member member) {
        this.songUuid = UUID.randomUUID();
        this.title = title;
        this.songPath = songPath;
        this.coverPath = coverPath;
        this.genre = genre;
        this.uploadDate = System.currentTimeMillis();
        this.member = member;
        this.moods = moods;
        this.views = 0L;
    }
}
