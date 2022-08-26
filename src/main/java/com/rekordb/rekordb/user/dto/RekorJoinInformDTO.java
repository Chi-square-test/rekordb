package com.rekordb.rekordb.user.dto;

import com.rekordb.rekordb.user.domain.Password;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RekorJoinInformDTO {
    private String name;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birth;
    private String phone;
    private int gender; //여자 0 남자 1
    private String country;
    private int language; //한글 0 영어 1


}
