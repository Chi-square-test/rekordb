package com.rekordb.rekordb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class ResponsePageDTO<T> {
    private ApiStatus status;
    private String error;
    private int currentPage;
    private int allPage;
    private List<T> data;
}
