package com.rekordb.rekordb.course;

import com.rekordb.rekordb.course.dto.SpotWithCheck;
import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.util.LinkedList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@EqualsAndHashCode(of = "courseId")
public class Course {

    private CourseId courseId;

    private List<SpotWithCheck> spotList;

    @Setter
    private String courseName;

    public static Course makeNewCourse(String name, List<SpotWithCheck> spotList) {
        return Course.builder()
                .courseId(CourseId.newCourseId())
                .spotList(new LinkedList<>(spotList))
                .courseName(name)
                .build();
    }
    public void moveSpotIdx(int start, int dest){
        SpotWithCheck document = spotList.remove(start);
        spotList.set(dest,document);
    }

    public void changeSpotList(List<SpotWithCheck> spots){
        this.spotList=spots;
    }
}



