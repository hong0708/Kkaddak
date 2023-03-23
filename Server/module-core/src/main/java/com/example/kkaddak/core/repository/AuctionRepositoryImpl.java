package com.example.kkaddak.core.repository;

import com.example.kkaddak.core.dto.AuctionConditionReqDto;
import com.example.kkaddak.core.dto.AuctionConditionResDto;
import com.example.kkaddak.core.entity.QAuction;
import com.example.kkaddak.core.entity.QLikeAuction;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuctionRepositoryImpl implements AuctionRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
    private final QAuction auction = QAuction.auction;
    private final QLikeAuction likeAuction = QLikeAuction.likeAuction;
    @Override
    public List<AuctionConditionResDto> findAuctionsByCondition(AuctionConditionReqDto c, int memberId) {
        List<Integer> ids = jpaQueryFactory
                .select(auction.id)
                .from(auction)
                .where(
                        ltAuctionId(c.getLastId()),
                        onlySelling(c.isOnlySelling())
                )
                .orderBy(auction.id.desc())
                .limit(c.getLimit())
                .fetch();

        if(CollectionUtils.isEmpty(ids)){
            return new ArrayList<>();
        }
        QLikeAuction memberLikeAuction = new QLikeAuction("memberLikeAuction");

        return jpaQueryFactory
                .select(Projections.constructor(
                        AuctionConditionResDto.class,
                        auction,
                        likeAuction.id.count().as("cntLikeAuctions"),
                        memberLikeAuction.id.isNotNull().as("isLike"))
                )
                .from(auction)
                .where(auction.id.in(ids))
                .leftJoin(likeAuction).on(auction.id.eq(likeAuction.auction.id))
                .leftJoin(memberLikeAuction).on(
                        auction.id.eq(memberLikeAuction.auction.id).and(memberLikeAuction.liker.id.eq(memberId))
                )
                .orderBy(auction.id.desc())
                .groupBy(auction.id)
                .fetch();
    }

    @Override
    public List<AuctionConditionResDto> findAuctionsByMyLike(AuctionConditionReqDto c, int memberId) {
        List<Integer> ids = jpaQueryFactory
                .select(auction.id)
                .from(auction)
                .where(
                        ltAuctionId(c.getLastId()),
                        onlySelling(c.isOnlySelling())
                )
                .orderBy(auction.id.desc())
                .limit(c.getLimit())
                .fetch();

        if(CollectionUtils.isEmpty(ids)){
            return new ArrayList<>();
        }
        QLikeAuction memberLikeAuction = new QLikeAuction("memberLikeAuction");

        return jpaQueryFactory
                .select(Projections.constructor(
                        AuctionConditionResDto.class,
                        auction,
                        likeAuction.id.count().as("cntLikeAuctions"),
                        memberLikeAuction.id.isNotNull().as("isLike"))
                )
                .from(auction)
                .where(auction.id.in(ids).and(memberLikeAuction.liker.id.eq(memberId)))
                .leftJoin(likeAuction).on(auction.id.eq(likeAuction.auction.id))
                .leftJoin(memberLikeAuction).on(
                        auction.id.eq(memberLikeAuction.auction.id).and(memberLikeAuction.liker.id.eq(memberId))
                )
                .orderBy(auction.id.desc())
                .groupBy(auction.id)
                .fetch();
    }

    private BooleanExpression ltAuctionId(int auctionId){
        if (auctionId == -1)
            return null;
        return auction.id.lt(auctionId);
    }
    private BooleanExpression onlySelling(boolean onlySelling){
        if (onlySelling)
            return auction.isClose.eq(false);
        return null;
    }
}
