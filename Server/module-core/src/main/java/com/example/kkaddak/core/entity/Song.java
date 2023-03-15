package com.example.kkaddak.core.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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

    private String mood;

    private Long uploadDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @ToString.Exclude
    private Member member;

    @OneToMany(mappedBy = "song")
    @ToString.Exclude
    private List<PlayList> playList = new ArrayList<>();

    @Builder
    public Song(String title, String songPath, String coverPath, String genre, String mood, Member member) {
        this.songUuid = UUID.randomUUID();
        this.title = title;
        this.songPath = songPath;
        this.coverPath = coverPath;
        this.genre = genre;
        this.mood = mood;
        this.uploadDate = System.currentTimeMillis();
        this.member = member;
    }
}
