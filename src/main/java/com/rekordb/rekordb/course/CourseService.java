package com.rekordb.rekordb.course;

import com.rekordb.rekordb.course.query.Course;
import com.rekordb.rekordb.course.query.CourseFolderRepository;
import com.rekordb.rekordb.tourspot.domain.SpotId;
import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import com.rekordb.rekordb.tourspot.query.TourSpotDocumentRepository;
import com.rekordb.rekordb.user.domain.userInfo.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseFolderRepository courseFolderRepository;
    private final TourSpotDocumentRepository tourSpotDocumentRepository;

    public void createCourse(String user,String name, List<String> spots){
        UserId userId = UserId.of(user);
        List<TourSpotDocument> spotList = spots.stream()
                .map(SpotId::of)
                .map(tourSpotDocumentRepository::findById)
                .map(Optional::orElseThrow)
                .collect(Collectors.toList());

        List<TourSpotDocument> spotList2 = new ArrayList<>();
        for (String s: spots) {
            SpotId id = SpotId.of(s);
            TourSpotDocument document = tourSpotDocumentRepository.findById(id).orElseThrow();
            spotList2.add(document);
        }

        CourseFolder root = getRootFolder(userId);
        Course course = Course.makeNewCourse(name,spotList,root.getCourseListCount());
        root.addCourse(course);
        courseFolderRepository.save(root);
    }

    public void createFolder(String user,String name){
        UserId userId = UserId.of(user);
        courseFolderRepository.save(CourseFolder.makeFolder(userId,name));
    }//name 공백 금지!! 컨트롤러에서 valid 예정

//    public void changeIdx(String folder, Map<String,Integer> newOrder)

    public void moveToAnotherFolder(String user,String course,String startF, String destF){
        UserId userId = UserId.of(user);
        FolderId startId = FolderId.of(startF);
        FolderId destId = FolderId.of(destF);
        CourseId courseId = CourseId.of(course);
        CourseFolder startFolder =courseFolderRepository.findByUserIdAndFolderId(userId,startId).orElseThrow();
        CourseFolder destFolder =courseFolderRepository.findByUserIdAndFolderId(userId,destId).orElseThrow();
        Course movingCourse = startFolder.getCourseList().stream()
                .filter(c -> c.getCourseId().equals(courseId))
                .findAny()
                .orElseThrow();
        startFolder.deleteCourse(movingCourse);
        destFolder.addCourse(movingCourse);




    }//name 공백 금지!! 컨트롤러에서 valid 예정

    public void deleteFolder(String user,String folder){
        UserId userId = UserId.of(user);
        FolderId folderId = FolderId.of(folder);
        CourseFolder courseFolder = courseFolderRepository.findByUserIdAndFolderId(userId,folderId).orElseThrow();
        courseFolderRepository.delete(courseFolder);
    }


    private CourseFolder getRootFolder(UserId userId){
        return courseFolderRepository.findByUserIdAndRootFolderTrue(userId)
                .orElseGet(()->courseFolderRepository.save(CourseFolder.makeRootFolder(userId)));

    }

}
