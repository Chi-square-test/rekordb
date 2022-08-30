package com.rekordb.rekordb.course.query;

import com.rekordb.rekordb.course.CourseId;
import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import lombok.*;

import javax.persistence.Embeddable;
import java.util.List;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@EqualsAndHashCode(of = "courseId")
public class Course {

    private CourseId courseId;

    private List<TourSpotDocument> spotList;
    @Setter
    private String courseName;

    @Setter
    private int courseIdx;


    public static Course makeNewCourse(String name, List<TourSpotDocument> spotList, int idx){
        return Course.builder()
                .courseId(CourseId.newCourseId())
                .spotList(spotList)
                .courseName("")
                .courseIdx(idx)
                .build();
    }




}
