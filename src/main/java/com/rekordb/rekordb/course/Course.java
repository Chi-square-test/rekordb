package com.rekordb.rekordb.course;

import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@EqualsAndHashCode(of = "courseId")
public class Course implements Comparable<Course> {

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


    @Override
    public int compareTo(Course o) {
        return Integer.compare(courseIdx,o.courseIdx);
    }
}
