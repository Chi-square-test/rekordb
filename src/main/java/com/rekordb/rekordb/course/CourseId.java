package com.rekordb.rekordb.course;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CourseId {

    @Column(name = "route_id")
    private String id;

    public static CourseId newCourseId(){
        return new CourseId(NanoIdUtils.randomNanoId());
    }

    @Override
    public String toString(){
        return id;
    }

    public static CourseId of(String id){
        return new CourseId(id);
    }
}
