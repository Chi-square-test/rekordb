package com.rekordb.rekordb.user.domain;

import com.rekordb.rekordb.security.query.RefreshTokenRepository;
import com.rekordb.rekordb.user.Execption.DuplicateUserInfoException;
import com.rekordb.rekordb.user.dto.RekorJoinInformDTO;
import com.rekordb.rekordb.user.dto.TokenSet;
import com.rekordb.rekordb.user.query.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean isUserJoined(String userId){
        return userRepository.getReferenceById(UserId.of(userId)).isJoined();
    }

    @Transactional
    public void signUpFromRekor(String userId,RekorJoinInformDTO dto){
        User user = userRepository.getReferenceById(UserId.of(userId));
        validateName(dto.getName());
        userRepository.save(User.updateJoinData(user,dto));
    }

    public void validateName(String name) throws DuplicateUserInfoException {
        if(userRepository.existsByNickName(name)){
            log.warn("이미 존재하는 이름"+ name);
            throw new DuplicateUserInfoException("이미 존재하는 이름입니다.");
        }
    }
}
