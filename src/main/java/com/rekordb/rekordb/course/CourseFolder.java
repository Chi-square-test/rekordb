package com.rekordb.rekordb.course;

import com.rekordb.rekordb.user.domain.userInfo.UserId;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Document(collection = "course")
public class CourseFolder {

    @Id
    private FolderId folderId;
    @Setter
    @TextIndexed
    private String folderName;

    private UserId userId;

    @Setter
    private List<Course> courseList;

    @Setter(AccessLevel.PRIVATE)
    private boolean rootFolder; //이게 true면 폴더 따로 없이 메인에 있는거임


    public static CourseFolder makeRootFolder(UserId userId){
        CourseFolder courseFolder= makeFolder(userId,"");
        courseFolder.setRootFolder(true);
        return courseFolder;
    }

    public static CourseFolder makeFolder(UserId userId , String name){
        return CourseFolder.builder()
                .folderId(FolderId.newFolderId())
                .folderName(name)
                .userId(userId)
                .rootFolder(false)
                .courseList(new ArrayList<>())
                .build();
    }

    public int getCourseListCount(){
        return courseList.size();
    }

    public void addCourse(Course course){
        courseList.add(course);
    }

    public void deleteCourse(Course course){
        courseList.remove(course);
    }

    public void moveCourseIdx(int start, int dest){
        Course course = courseList.remove(start);
        courseList.add(dest,course);
    }



}
