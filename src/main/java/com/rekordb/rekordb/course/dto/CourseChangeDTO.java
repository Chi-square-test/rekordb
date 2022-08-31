package com.rekordb.rekordb.course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseChangeDTO {
    private String folderId;
    private int start;
    private int dest;
}
