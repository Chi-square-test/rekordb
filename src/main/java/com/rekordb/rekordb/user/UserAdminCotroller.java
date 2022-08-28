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
        return ResponseEntity.ok().body(null);
    }


}
