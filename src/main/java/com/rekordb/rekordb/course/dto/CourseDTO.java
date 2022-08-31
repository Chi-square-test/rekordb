package com.rekordb.rekordb.course.dto;


import com.rekordb.rekordb.course.Course;
import com.rekordb.rekordb.course.CourseId;
import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import com.rekordb.rekordb.tourspot.dto.SpotListDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseDTO {
    private CourseId courseId;
    private String courseName;
    private List<SpotListDTO> spotList;


    public static CourseDTO convertToDTO(Course course){
        return CourseDTO.builder()
                .courseId(course.getCourseId())
                .courseName(course.getCourseName())
                .spotList(course.getSpotList().stream().map(SpotListDTO::new).collect(Collectors.toList()))
                .build();

    }
}
