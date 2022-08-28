package com.rekordb.rekordb.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RekorJoinInformDTO {
    @NotNull(message = "이름")
    private String name;
    @NotNull(message = "생년월일")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birth;
    @NotNull(message = "성별정보")
    private int gender; //여자 0 남자 1
    @NotNull(message = "국가정보")
    private String country;
    @NotNull(message = "언어정보")
    private int language; //한글 0 영어 1


}
