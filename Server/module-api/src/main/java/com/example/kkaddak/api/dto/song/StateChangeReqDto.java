package com.example.kkaddak.api.dto.song;

import com.example.kkaddak.core.entity.SongStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class StateChangeReqDto {

    @ApiModelProperty(
            value = "음악 ID",
            required = true,
            dataType = "String"
    )
    @NotNull(message = "음악 ID는 필수입니다.")
    UUID songUUID;

    @ApiModelProperty(
            value = "음악 상태 정보",
            required = true,
            dataType = "String"
    )
    @NotNull(message = "음악 상태 정보는 필수입니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    SongStatus songStatus;
}
