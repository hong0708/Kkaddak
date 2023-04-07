package com.example.kkaddak.core.repository;

import com.example.kkaddak.core.dto.MarketConditionReqDto;
import com.example.kkaddak.core.dto.MarketConditionResDto;
import com.example.kkaddak.core.entity.QLikeMarket;
import com.example.kkaddak.core.entity.QMarket;
import com.example.kkaddak.core.exception.NoContentException;
import com.example.kkaddak.core.utils.ErrorMessageEnum;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MarketRepositoryImpl implements MarketRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QMarket market = QMarket.market;
    private final QLikeMarket likeMarket = QLikeMarket.likeMarket;
    @Override
    public List<MarketConditionResDto> findMarketsByCondition(MarketConditionReqDto c, int memberId) throws NoContentException {
        List<Integer> ids = jpaQueryFactory
                .select(market.id)
                .from(market)
                .where(
                        ltMarketId(c.getLastId()),
                        onlySelling(c.isOnlySelling())
                )
                .orderBy(market.id.desc())
                .limit(c.getLimit())
                .fetch();

        if(CollectionUtils.isEmpty(ids)){
            throw new NoContentException(ErrorMessageEnum.NO_MORE_MARKET.getMessage());
        }
        QLikeMarket memberLikeMarket = new QLikeMarket("memberLikeMarket");

        return jpaQueryFactory
                .select(Projections.constructor(
                        MarketConditionResDto.class,
                        market,
                        likeMarket.id.count().as("cntLikeMarket"),
                        memberLikeMarket.id.isNotNull().as("isLike"))
                )
                .from(market)
                .where(market.id.in(ids))
                .leftJoin(likeMarket).on(market.id.eq(likeMarket.market.id))
                .leftJoin(memberLikeMarket).on(
                        market.id.eq(memberLikeMarket.market.id).and(memberLikeMarket.liker.id.eq(memberId))
                )
                .orderBy(market.id.desc())
                .groupBy(market.id)
                .fetch();
    }

    @Override
    public List<MarketConditionResDto> findMarketsByMyLike(MarketConditionReqDto c, int memberId) throws NoContentException {
        List<Integer> ids = jpaQueryFactory
                .select(market.id)
                .from(market)
                .where(
                        ltMarketId(c.getLastId()),
                        onlySelling(c.isOnlySelling())
                )
                .orderBy(market.id.desc())
                .limit(c.getLimit())
                .fetch();

        if(CollectionUtils.isEmpty(ids)){
            throw new NoContentException(ErrorMessageEnum.NO_MORE_MARKET.getMessage());
        }
        QLikeMarket memberLikeMarket = new QLikeMarket("memberLikeMarket");

        return jpaQueryFactory
                .select(Projections.constructor(
                        MarketConditionResDto.class,
                        market,
                        likeMarket.id.count().as("cntLikeMarket"),
                        memberLikeMarket.id.isNotNull().as("isLike"))
                )
                .from(market)
                .where(market.id.in(ids).and(memberLikeMarket.liker.id.eq(memberId)))
                .leftJoin(likeMarket).on(market.id.eq(likeMarket.market.id))
                .leftJoin(memberLikeMarket).on(
                        market.id.eq(memberLikeMarket.market.id).and(memberLikeMarket.liker.id.eq(memberId))
                )
                .orderBy(market.id.desc())
                .groupBy(market.id)
                .fetch();
    }

    private BooleanExpression ltMarketId(int marketId){
        if (marketId == -1)
            return null;
        return market.id.lt(marketId);
    }
    private BooleanExpression onlySelling(boolean onlySelling){
        if (onlySelling)
            return market.isClose.eq(false);
        return null;
    }
}
