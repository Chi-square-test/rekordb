package com.rekordb.rekordb.tourspot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckItem {
    private boolean isReviewed;
    private boolean isWished;
}
