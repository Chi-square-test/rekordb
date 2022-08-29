package com.rekordb.rekordb.review;

import com.rekordb.rekordb.ApiStatus;
import com.rekordb.rekordb.ResponseDTO;
import com.rekordb.rekordb.review.Exception.AlreadyExistReviewException;
import com.rekordb.rekordb.review.dto.ReviewDTO;
import com.rekordb.rekordb.review.dto.ReviewWriteDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

import static com.rekordb.rekordb.user.UserAuthController.checkNull;

@RestController
@Slf4j
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> writeReview(@AuthenticationPrincipal User user, @RequestBody ReviewWriteDTO dto, BindingResult bindingResult){
        checkNull(bindingResult);
        try{
            ResponseDTO<ReviewDTO> responseDTO = ResponseDTO.<ReviewDTO>builder()
                    .status(ApiStatus.SUCCESS)
                    .data(Collections.singletonList(reviewService.saveReviews(user.getUsername(),dto)))
                    .build();
            return ResponseEntity.ok().body(responseDTO);
        }catch (AlreadyExistReviewException e){
            ResponseDTO<Object> responseDTO = ResponseDTO.builder()
                    .status(ApiStatus.FAIL)
                    .error("이미 리뷰를 작성한 사용자입니다.")
                    .build();
            return ResponseEntity.ok().body(responseDTO);
        }

    }
}
