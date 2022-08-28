package com.rekordb.rekordb.user;

import com.rekordb.rekordb.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/useradmin")
@RequiredArgsConstructor
public class UserAdminCotroller {

    private final UserAdminService userAdminService;

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUser(@RequestBody String userid) {
        userAdminService.removeUser(userid);
        log.info("유저 삭제 요청 "+userid);
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("/spot")
    public ResponseEntity<?> checkSpot(@RequestBody String spotid) {
        userAdminService.updateWishCount(spotid);
        log.info("관광지 좋아요 재점검 "+spotid);
        return ResponseEntity.ok().body(null);
    }


}
