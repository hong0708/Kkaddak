package com.example.kkaddak.core.repository;

import com.example.kkaddak.core.entity.QSong;
import com.example.kkaddak.core.entity.Song;
import com.example.kkaddak.core.entity.SongStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SearchRepository {
    @PersistenceContext
    private final EntityManager em;

    public List<Song> searchSong(String keyWord, String genre) {
        QSong qSong = QSong.song;
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        BooleanBuilder builder = new BooleanBuilder();

        String[] genres = genre.split(",");
        List<String> genreList = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            if (i < genres.length) {
                genreList.add(genres[i].trim());
            } else {
                genreList.add("");
            }
        }

        // songStatus가 COMPLETE 또는 APPROVE인 조건
        BooleanExpression statusCondition = qSong.songStatus.in(SongStatus.COMPLETE, SongStatus.APPROVE);

        // keyword 조건
        BooleanExpression keywordCondition = null;
        if (!keyWord.equals("")) {
            keywordCondition = qSong.member.nickname.containsIgnoreCase(keyWord.trim())
                    .or(qSong.title.containsIgnoreCase(keyWord.trim()));
        }

        // genre 조건
        BooleanExpression genreCondition = null;
        if (!genre.equals("")) {
            genreCondition = qSong.genre.eq(genreList.get(0)).or(qSong.genre.eq(genreList.get(1))).or(qSong.genre.eq(genreList.get(2)))
                    .or(qSong.genre.eq(genreList.get(3))).or(qSong.genre.eq(genreList.get(4))).or(qSong.genre.eq(genreList.get(5)))
                    .or(qSong.genre.eq(genreList.get(6)));
        }

        // 조건들을 결합합니다.
        builder.and(statusCondition);
        if (keywordCondition != null) {
            builder.and(keywordCondition);
        }
        if (genreCondition != null) {
            builder.and(genreCondition);
        }

        List<Song> songs = queryFactory
                .selectFrom(qSong)
                .where(builder)
                .fetch();

        return songs;
    }
}
