package com.example.kkaddak.api.dto;

import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.entity.Mood;
import com.example.kkaddak.core.entity.PlayList;
import com.example.kkaddak.core.entity.Song;
import com.example.kkaddak.core.repository.MemberRepository;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class SongResDto {
    @ApiModelProperty(example = "음악 식별 아이디")
    UUID songId;
    @ApiModelProperty(example = "음악 제목")
    String songTitle;
    @ApiModelProperty(example = "음악 파일 경로")
    String songPath;
    @ApiModelProperty(example = "음악 커버 이미지 경로")
    String coverPath;
    @ApiModelProperty(example = "음악 장르")
    String genre;
    @ApiModelProperty(example = "음악 분위기")
    Mood moods;
    @ApiModelProperty(example = "음악 업로드 날짜")
    Long uploadDate;
    @ApiModelProperty(example = "음악 생성자")
    String nickname;
    @ApiModelProperty(example = "음악 좋아요")
    boolean like = false;


    @Builder
    public SongResDto(Song song) {
        this.songId = song.getSongUuid();
        this.songTitle = song.getTitle();
        this.songPath = song.getSongPath();
        this.coverPath = song.getCoverPath();
        this.genre = song.getGenre();
        this.moods = song.getMoods();
        this.uploadDate = song.getUploadDate();
        this.nickname = song.getMember().getNickname();
    }

    @Builder
    public SongResDto(Song song, boolean like) {
        this.songId = song.getSongUuid();
        this.songTitle = song.getTitle();
        this.songPath = song.getSongPath();
        this.coverPath = song.getCoverPath();
        this.genre = song.getGenre();
        this.moods = song.getMoods();
        this.uploadDate = song.getUploadDate();
        this.nickname = song.getMember().getNickname();
        this.like = like;
    }
}
