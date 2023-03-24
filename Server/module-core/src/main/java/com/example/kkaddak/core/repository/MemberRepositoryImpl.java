package com.example.kkaddak.core.repository;

import com.example.kkaddak.core.dto.AuctionConditionReqDto;
import com.example.kkaddak.core.dto.AuctionConditionResDto;
import com.example.kkaddak.core.dto.MyFollowConditionDto;
import com.example.kkaddak.core.dto.MyFollowerResDto;
import com.example.kkaddak.core.entity.*;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
    private final QMember member = QMember.member;
    private final QFollow f1 = QFollow.follow;

    @Override
    public List<MyFollowerResDto> findMyFollowersByMember(MyFollowConditionDto c, int memberId) {
        QFollow f2 = new QFollow("f2");

        List<Integer> ids = jpaQueryFactory
                .select(member.id)
                .from(member)
                .where(ltAuctionId(c.getLastId()), f1.following.id.eq(memberId))
                .leftJoin(f1).on(f1.follower.id.eq(member.id))
                .orderBy(member.id.asc())
                .limit(c.getLimit())
                .fetch();

        if(CollectionUtils.isEmpty(ids))
            return new ArrayList<>();



        return jpaQueryFactory
                .select(Projections.constructor(
                        MyFollowerResDto.class,
                        member,
                        new CaseBuilder()
                                .when(f2.following.id.isNull())
                                .then(0)
                                .otherwise(1)
                                .as("isFollowing"))
                )
                .from(member)
                .leftJoin(f1).on(f1.follower.id.eq(member.id))
                .leftJoin(f2).on(f2.follower.id.eq(memberId).and(f2.following.id.eq(f1.follower.id)))
                .where(member.id.in(ids))
                .fetch();

    }
    private BooleanExpression ltAuctionId(int followId){
        if (followId == -1)
            return null;
        return member.id.lt(followId);
    }
}
