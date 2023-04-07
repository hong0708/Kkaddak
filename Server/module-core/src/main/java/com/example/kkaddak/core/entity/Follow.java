package com.example.kkaddak.core.entity;

import lombok.*;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@ToString(callSuper = true)
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @ToString.Exclude
    private Member follower;
    @ManyToOne
    @ToString.Exclude
    private Member following;

    @Builder
    public Follow(Member follower, Member following) {
        this.follower = follower;
        this.following = following;
    }
}
