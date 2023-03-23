package com.example.kkaddak.core.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString(callSuper = true)
public class PlayList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @ToString.Exclude
    private Member member;

    @ManyToOne(cascade = CascadeType.MERGE)
    @ToString.Exclude
    private Song song;

    private Long addedDate;

    @Builder
    public PlayList(Member member, Song song) {
        this.member = member;
        this.song = song;
        this.addedDate = System.currentTimeMillis();
    }
}
