package com.example.kkaddak.core.repository;

import com.example.kkaddak.core.dto.MyFollowConditionDto;
import com.example.kkaddak.core.dto.MyFollowerResDto;
import com.example.kkaddak.core.entity.*;
import com.example.kkaddak.core.exception.NoContentException;
import com.example.kkaddak.core.utils.ErrorMessageEnum;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
    private final QMember member = QMember.member;
    private final QFollow f1 = QFollow.follow;
    private final QFollow f2 = new QFollow("f2");


    @Override
    public List<MyFollowerResDto> findMyFollowersByMember(MyFollowConditionDto c, int memberId) throws NoContentException {

        List<Integer> ids = jpaQueryFactory
                .select(member.id)
                .from(member)
                .where(gtMemberId(c.getLastId()), f1.following.id.eq(memberId))
                .leftJoin(f1).on(f1.follower.id.eq(member.id))
                .orderBy(member.id.asc())
                .limit(c.getLimit())
                .fetch();

        if(CollectionUtils.isEmpty(ids))
            throw new NoContentException(ErrorMessageEnum.NO_MORE_FOLLOWER.getMessage());

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
                .where(member.id.in(ids))
                .leftJoin(f2).on(f2.follower.id.eq(memberId).and(f2.following.id.eq(member.id)))
                .fetch();
    }

    @Override
    public List<MyFollowerResDto> findMyFollowingsByMember(MyFollowConditionDto c, int memberId) throws NoContentException {

        List<Integer> ids = jpaQueryFactory
                .select(member.id)
                .from(member)
                .where(gtMemberId(c.getLastId()), f1.follower.id.eq(memberId))
                .leftJoin(f1).on(f1.following.id.eq(member.id))
                .orderBy(member.id.asc())
                .limit(c.getLimit())
                .fetch();

        if(CollectionUtils.isEmpty(ids))
            throw new NoContentException(ErrorMessageEnum.NO_MORE_FOLLOWER.getMessage());

        return jpaQueryFactory
                .select(Projections.constructor(MyFollowerResDto.class, member, Expressions.asNumber(1).as("isFollowing")))
                .from(member)
                .where(member.id.in(ids))
                .fetch();
    }

    private BooleanExpression gtMemberId(int followId){
        if (followId == -1)
            return null;
        return member.id.gt(followId);
    }
}
