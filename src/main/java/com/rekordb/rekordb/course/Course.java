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
public class Course {

    private CourseId courseId;

    private List<TourSpotDocument> spotList;

    @Setter
    private String courseName;

    public static Course makeNewCourse(String name, List<TourSpotDocument> spotList) {
        return Course.builder()
                .courseId(CourseId.newCourseId())
                .spotList(spotList)
                .courseName(name)
                .build();
    }
    public void moveSpotIdx(int start, int dest){
        TourSpotDocument document = spotList.remove(start);
        spotList.set(dest,document);
    }
}



