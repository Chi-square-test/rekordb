package com.rekordb.rekordb.course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FolderChangeDTO {
    private String courseId;
    private String start;
    private String dest;
}
