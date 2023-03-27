package com.example.kkaddak.core.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeMarket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @ToString.Exclude
    private Member liker;

    @ManyToOne
    @ToString.Exclude
    private Market market;

    @Builder
    public LikeMarket(Member liker, Market market) {
        this.liker = liker;
        this.market = market;
    }
}