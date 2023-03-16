package com.example.kkaddak.api.dto;

import com.example.kkaddak.core.entity.PlayList;
import com.example.kkaddak.core.entity.Song;
import com.example.kkaddak.core.repository.MemberRepository;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class SongResDto {

    @ApiModelProperty(example = "음악 식별 아이디")
    UUID songUuid;
    @ApiModelProperty(example = "음악 제목")
    String songTitle;
    @ApiModelProperty(example = "음악 파일 경로")
    String songPath;
    @ApiModelProperty(example = "음악 커버 이미지 경로")
    String coverPath;
    @ApiModelProperty(example = "음악 장르")
    String genre;
    @ApiModelProperty(example = "음악 분위기")
    String mood;
    @ApiModelProperty(example = "음악 업로드 날짜")
    Long uploadDate;
    @ApiModelProperty(example = "음악 생성자")
    String nickname;

    @Builder
    public SongResDto(Song song) {
        this.songUuid = song.getSongUuid();
        this.songTitle = song.getTitle();
        this.songPath = song.getSongPath();
        this.coverPath = song.getCoverPath();
        this.genre = song.getGenre();
        this.mood = song.getMood();
        this.uploadDate = song.getUploadDate();
        this.nickname = song.getMember().getNickname();
    }
}
