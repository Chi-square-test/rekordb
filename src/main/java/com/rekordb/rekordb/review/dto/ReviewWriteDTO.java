package com.rekordb.rekordb.review.dto;

import com.rekordb.rekordb.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewWriteDTO {
    @NotNull(message = "관광지")
    private String spotId;
    @NotNull(message =  "평점")
    private Integer rating;
    private String text;
    private List<String> tagList;
}
