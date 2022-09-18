package com.rekordb.rekordb.course;

import com.rekordb.rekordb.ApiStatus;
import com.rekordb.rekordb.ResponseDTO;
import com.rekordb.rekordb.course.dto.*;
import com.rekordb.rekordb.course.exception.FolderException;
import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import com.rekordb.rekordb.tourspot.domain.TourSpotService;
import com.rekordb.rekordb.tourspot.dto.SpotListDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@Slf4j
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final TourSpotService tourSpotService;

    @GetMapping("/recommend")
    public ResponseEntity<ResponseDTO<SpotListDTO>> getRecommendSpot(){
        ResponseDTO<SpotListDTO> dto = ResponseDTO.<SpotListDTO>builder()
                .status(ApiStatus.SUCCESS)
                .data(tourSpotService.getPureRandomSpot())
                .build();
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping//목록 조회
    public ResponseEntity<ResponseDTO<CourseFolderDTO>> getFolder(@AuthenticationPrincipal User user){
        ResponseDTO<CourseFolderDTO> dto = ResponseDTO.<CourseFolderDTO>builder()
                .status(ApiStatus.SUCCESS)
                .data(courseService.getCourseFolder(user.getUsername()))
                .build();
        return ResponseEntity.ok().body(dto);

    }
    @PostMapping//새 코스추가
    public ResponseEntity<ResponseDTO<CourseFolderDTO>> addNewCourse(@AuthenticationPrincipal User user, @RequestBody @Valid NewCourseDTO spots){
        CourseFolderDTO dto = courseService.createCourse(user.getUsername(),spots.getName(),spots.getSpots());
        ResponseDTO<CourseFolderDTO> res = ResponseDTO.<CourseFolderDTO>builder()
                .status(ApiStatus.SUCCESS)
                .data(Collections.singletonList(dto))
                .build();
        return ResponseEntity.ok().body(res);
    }
    @PostMapping("/folder")
    public ResponseEntity<ResponseDTO<CourseFolderDTO>> addNewFolder(@AuthenticationPrincipal User user, @RequestBody @Valid NewFolderDTO name){
        CourseFolderDTO dto = courseService.createFolder(user.getUsername(), name.getFolderName());
        ResponseDTO<CourseFolderDTO> res = ResponseDTO.<CourseFolderDTO>builder()
                .status(ApiStatus.SUCCESS)
                .data(Collections.singletonList(dto))
                .build();
        return ResponseEntity.ok().body(res);
    }
    @PutMapping("/name")
    public ResponseEntity<ResponseDTO<Object>> renameCourse(@AuthenticationPrincipal User user, @RequestBody @Valid RenameDTO renameDTO){
        courseService.renameCourse(user.getUsername(), renameDTO);
        return successAndReturnNull();
    }
    @PutMapping("/folder/name")
    public ResponseEntity<ResponseDTO<Object>> renameFolder(@AuthenticationPrincipal User user, @RequestBody @Valid RenameDTO renameDTO){
        courseService.renameFolder(user.getUsername(), renameDTO);
        return successAndReturnNull();
    }

    @PutMapping//폴더내 코스들 순서 변경
    public ResponseEntity<ResponseDTO<Object>> changeCourseIdx(@AuthenticationPrincipal User user, @RequestBody CourseChangeDTO dto){
        courseService.changeCourseIdx(user.getUsername(), dto.getFolderId(), dto.getStart(),dto.getDest());
        return successAndReturnNull();

    }
    @PutMapping("/move")//다른 폴더로 코스 이동
    public ResponseEntity<ResponseDTO<Object>> moveCourseToOther(@AuthenticationPrincipal User user,@RequestBody FolderChangeDTO dto){
        courseService.moveToAnotherFolder(user.getUsername(), dto.getCourseId(), dto.getStart(),dto.getDest());
        return successAndReturnNull();
    }
    @DeleteMapping//코스 제거
    public ResponseEntity<ResponseDTO<Object>> removeCourse(@AuthenticationPrincipal User user, @RequestBody DeleteCourseDTO dto){
        courseService.deleteCourse(user.getUsername(),dto.getFolderId(),dto.getCourseId());
        return successAndReturnNull();
    }
    @DeleteMapping("/folder")//폴더 제거거
    public ResponseEntity<ResponseDTO<Object>> removeFolder(@AuthenticationPrincipal User user,@RequestBody DeleteFolderDTO folderId){
        try {
            courseService.deleteFolder(user.getUsername(),folderId.getFolderId());
            return successAndReturnNull();
        }catch (FolderException e){
            ResponseDTO<Object> dto = ResponseDTO.builder()
                    .status(ApiStatus.FAIL)
                    .error(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(dto);
        }
    }

    private static ResponseEntity<ResponseDTO<Object>> successAndReturnNull(){
        ResponseDTO<Object> dto = ResponseDTO.builder()
                .status(ApiStatus.SUCCESS)
                .build();
        return ResponseEntity.ok().body(dto);
    }
}
