package com.rekordb.rekordb.course;

import com.rekordb.rekordb.course.dto.CourseFolderDTO;
import com.rekordb.rekordb.course.dto.CourseSpotDTO;
import com.rekordb.rekordb.course.dto.RenameDTO;
import com.rekordb.rekordb.course.dto.SpotWithCheck;
import com.rekordb.rekordb.course.exception.FolderException;
import com.rekordb.rekordb.course.query.CourseFolderRepository;
import com.rekordb.rekordb.tourspot.domain.SpotId;
import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import com.rekordb.rekordb.tourspot.query.TourSpotDocumentRepository;
import com.rekordb.rekordb.user.domain.userInfo.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseFolderRepository courseFolderRepository;
    private final TourSpotDocumentRepository tourSpotDocumentRepository;

    public List<CourseFolderDTO> getCourseFolder(String user){
        UserId userId = UserId.of(user);
        return  courseFolderRepository.findByUserId(userId).stream()
                .map(CourseFolderDTO::convertToDTO)
                .collect(Collectors.toList());
    }

    public CourseFolderDTO createCourse(String user,String name, List<CourseSpotDTO> spots){
        UserId userId = UserId.of(user);
        List<TourSpotDocument> spotList = spots.stream()
                .map(CourseSpotDTO::getSpotId)
                .map(SpotId::of)
                .map(tourSpotDocumentRepository::findById)
                .map(Optional::orElseThrow)
                .collect(Collectors.toList());
        CourseFolder root = getRootFolder(userId);

        List<SpotWithCheck> withCheckList = new ArrayList<>();
        for (int i = 0; i < spots.size(); i++) {
            withCheckList.add(spots.get(i).convertToValue(spotList.get(i)));
        }

        Course course = Course.makeNewCourse(name,withCheckList);
        root.addCourse(course);
        return CourseFolderDTO.convertToDTO(courseFolderRepository.save(root));
    }

    public CourseFolderDTO createFolder(String user,String name){
        UserId userId = UserId.of(user);
        return CourseFolderDTO.convertToDTO(courseFolderRepository.save(CourseFolder.makeFolder(userId,name)));
    }//name ?????? ??????!! ?????????????????? valid ??????

    public void renameCourse(String user,RenameDTO dto){
        UserId userId = UserId.of(user);
        CourseId courseId = CourseId.of(dto.getId());
        CourseFolder courseFolder = courseFolderRepository.findByUserIdAndCourseListCourseId(userId,courseId).orElseThrow();
        Course renameCourse = courseFolder.findCourse(courseId);
        renameCourse.setCourseName(dto.getName());
        courseFolderRepository.save(courseFolder);
    }

    public void renameFolder(String user,RenameDTO dto){
        UserId userId = UserId.of(user);
        FolderId folderId = FolderId.of(dto.getId());
        CourseFolder courseFolder = courseFolderRepository.findByUserIdAndFolderId(userId,folderId).orElseThrow();
        if(courseFolder.isRootFolder()) throw new FolderException("?????? ????????? ????????? ??? ????????????.");
        courseFolder.setFolderName(dto.getName());
        courseFolderRepository.save(courseFolder);
    }

    public void changeCourseIdx(String user, String folder, int start, int dest){
        FolderId folderId = FolderId.of(folder);
        UserId userId = UserId.of(user);
        CourseFolder courseFolder =courseFolderRepository.findByUserIdAndFolderId(userId,folderId).orElseThrow();
        courseFolder.moveCourseIdx(start, dest);
        courseFolderRepository.save(courseFolder);
    }

    public CourseFolderDTO changeCourseSpot(String user,String course, List<CourseSpotDTO> spots){
        UserId userId = UserId.of(user);
        CourseId courseId = CourseId.of(course);
        CourseFolder courseFolder = courseFolderRepository.findByUserIdAndCourseListCourseId(userId,courseId).orElseThrow();
        List<TourSpotDocument> spotList = spots.stream()
                .map(CourseSpotDTO::getSpotId)
                .map(SpotId::of)
                .map(tourSpotDocumentRepository::findById)
                .map(Optional::orElseThrow)
                .collect(Collectors.toList());
        Course changedCourse = courseFolder.findCourse(courseId);

        List<SpotWithCheck> withCheckList = new ArrayList<>();
        for (int i = 0; i < spots.size(); i++) {
            withCheckList.add(spots.get(i).convertToValue(spotList.get(i)));
        }
        changedCourse.changeSpotList(withCheckList);
        return CourseFolderDTO.convertToDTO( courseFolderRepository.save(courseFolder));



    }

    public void moveToAnotherFolder(String user,String course,String startF, String destF){
        UserId userId = UserId.of(user);
        FolderId startId = FolderId.of(startF);
        FolderId destId = FolderId.of(destF);
        CourseId courseId = CourseId.of(course);
        CourseFolder destFolder =courseFolderRepository.findByUserIdAndFolderId(userId,destId).orElseThrow();
        destFolder.addCourse(deleteCourse(userId,startId,courseId));
        courseFolderRepository.save(destFolder);
    }//name ?????? ??????!! ?????????????????? valid ??????


    public void deleteFolder(String user,String folder){
        UserId userId = UserId.of(user);
        FolderId folderId = FolderId.of(folder);
        CourseFolder courseFolder = courseFolderRepository.findByUserIdAndFolderId(userId,folderId).orElseThrow();
        if(courseFolder.isRootFolder()) throw new FolderException("?????? ????????? ????????? ??? ????????????");
        courseFolderRepository.delete(courseFolder);
    }



    private CourseFolder getRootFolder(UserId userId){
        return courseFolderRepository.findByUserIdAndRootFolderTrue(userId)
                .orElseGet(()->courseFolderRepository.save(CourseFolder.makeRootFolder(userId)));

    }

    public void deleteCourse(String userId, String folderId, String courseId){
        deleteCourse(UserId.of(userId), FolderId.of(folderId), CourseId.of(courseId));
    }

    private Course deleteCourse(UserId userId, FolderId folderId, CourseId courseId){
        CourseFolder folder =courseFolderRepository.findByUserIdAndFolderId(userId,folderId).orElseThrow();
        Course movingCourse =folder.findCourse(courseId);
        folder.deleteCourse(movingCourse);
        courseFolderRepository.save(folder);
        return movingCourse;
    }

//    public void changeCourseIdx(String user, String folder, List<Integer> newOrder){
//        FolderId folderId = FolderId.of(folder);
//        UserId userId = UserId.of(user);
//        CourseFolder courseFolder =courseFolderRepository.findByUserIdAndFolderId(userId,folderId).orElseThrow();
//        List<Course> courseList = courseFolder.getCourseList();
//        for (int i = 0; i <courseList.size() ; i++) {
//            courseList.get(i).setCourseIdx(newOrder.get(i));
//        }
//        Collections.sort(courseList);
//        courseFolder.setCourseList(courseList);
//        courseFolderRepository.save(courseFolder);
//    }
}
