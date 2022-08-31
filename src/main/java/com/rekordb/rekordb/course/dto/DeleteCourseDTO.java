package com.rekordb.rekordb.course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeleteCourseDTO {
    private String folderId;
    private String courseId;
}
