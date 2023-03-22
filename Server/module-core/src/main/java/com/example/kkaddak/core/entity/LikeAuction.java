package com.example.kkaddak.core.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeAuction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @ToString.Exclude
    private Member liker;

    @ManyToOne
    @ToString.Exclude
    private Auction auction;

    @Builder
    public LikeAuction(Member liker, Auction auction) {
        this.liker = liker;
        this.auction = auction;
    }
}