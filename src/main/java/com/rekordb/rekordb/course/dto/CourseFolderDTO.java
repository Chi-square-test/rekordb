package com.rekordb.rekordb.course.dto;

import com.rekordb.rekordb.course.Course;
import com.rekordb.rekordb.course.CourseFolder;
import com.rekordb.rekordb.course.FolderId;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseFolderDTO {
    private FolderId folderId;
    private String folderName;
    private List<CourseDTO> courseList;

    public static CourseFolderDTO convertToDTO(CourseFolder folder){
        return CourseFolderDTO.builder()
                .folderId(folder.getFolderId())
                .folderName(folder.getFolderName())
                .courseList(folder.getCourseList().stream().map(CourseDTO::convertToDTO).collect(Collectors.toList()))
                .build();
    }
}
