package com.example.kkaddak.core.entity;

import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString(callSuper = true)
public class PlayList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "song_id")
    private Song song;

    private Long addedDate;

    @Builder
    public PlayList(Member member, Song song) {
        this.member = member;
        this.song = song;
        this.addedDate = System.currentTimeMillis();
    };
}
