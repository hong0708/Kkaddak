package com.example.kkaddak.core.repository;

import com.example.kkaddak.core.entity.QSong;
import com.example.kkaddak.core.entity.Song;
import com.querydsl.core.BooleanBuilder;
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

    public List<Song> searchSong(String nickname, String title, String genre) {
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

        if (!nickname.equals("")) {
            builder.and(qSong.member.nickname.containsIgnoreCase(nickname.trim()));
        }
        if (!title.equals("")) {
            builder.and(qSong.title.containsIgnoreCase(title.trim()));
        }
        if (!genre.equals("")) {
            builder.and(qSong.genre.eq(genreList.get(0)).or(qSong.genre.eq(genreList.get(1))).or(qSong.genre.eq(genreList.get(2)))
                    .or(qSong.genre.eq(genreList.get(3))).or(qSong.genre.eq(genreList.get(4))).or(qSong.genre.eq(genreList.get(5)))
                    .or(qSong.genre.eq(genreList.get(6))));
        }

        List<Song> songs = queryFactory
                .selectFrom(qSong)
                .where(builder)
                .fetch();

        return songs;
    }
}
