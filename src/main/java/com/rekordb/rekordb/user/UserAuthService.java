package com.rekordb.rekordb.user;

import com.rekordb.rekordb.security.Jwt.AuthToken;
import com.rekordb.rekordb.security.Jwt.TokenProvider;
import com.rekordb.rekordb.security.Jwt.RefreshToken;
import com.rekordb.rekordb.security.query.RefreshTokenRepository;
import com.rekordb.rekordb.tag.UserAndTag;
import com.rekordb.rekordb.tag.query.UserTagRepository;
import com.rekordb.rekordb.user.Execption.DuplicateUserInfoException;
import com.rekordb.rekordb.user.Execption.NeedLoginException;
import com.rekordb.rekordb.user.Execption.WrongLoginException;
import com.rekordb.rekordb.user.domain.userInfo.*;
import com.rekordb.rekordb.user.domain.userWithSpot.UserWishList;
import com.rekordb.rekordb.user.dto.RekorCreateDTO;
import com.rekordb.rekordb.user.dto.RekorLoginDTO;
import com.rekordb.rekordb.user.dto.TokenSet;
import com.rekordb.rekordb.user.query.UserRepository;
import com.rekordb.rekordb.user.query.UserWishListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;
    private final UserWishListRepository userWishListRepository;
    private final UserTagRepository userTagRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public TokenSet createFromRekor(RekorCreateDTO dto){
        validatePhone(dto.getPhone());
        dto.setEncPassword(Password.encryptPassword(passwordEncoder,dto.getPassword()));
        User newUser = User.createFromDTO(dto);
        userWishListRepository.save(new UserWishList(newUser.getUserId(), new LinkedHashSet<>()));
        userTagRepository.save(new UserAndTag(newUser.getUserId(),new ArrayList<>()));
        return makeNewAllToken(userRepository.save(newUser));
    }


    public TokenSet loginFromRekor(RekorLoginDTO dto) throws WrongLoginException {
        User user = getByCredentials(PhoneNumber.of(dto.getPhone()), dto.getPassword());
        return TokenSet.addNickNameFactory(makeNewAllToken(user), user.getNickName());
    }

    public TokenSet tokenRefresh(String access,String refresh) throws NeedLoginException{ //???????????? ???????????????.
        AuthToken refreshToken = tokenProvider.convertAuthToken(refresh);
        AuthToken accessToken = tokenProvider.convertAuthToken(access);
        try{
            if(!accessToken.getExpiredTokenClaims()) throw new NeedLoginException(); //?????? ???????????? ???????????? ?????????
            String userid = refreshToken.getTokenClaims().getSubject();
            Optional<RefreshToken> opt = refreshTokenRepository.findByUserIdAndRefreshTokenAndAccessToken(UserId.of(userid),refresh,access);
            return makeNewAllToken(opt.orElseThrow(NeedLoginException::new));
        }catch (Exception e){
            refreshTokenRepository.findByRefreshToken(refresh).ifPresent(refreshTokenRepository::delete);
            refreshTokenRepository.findByAccessToken(access).ifPresent(refreshTokenRepository::delete);
            throw new NeedLoginException();
        }

    }

    public void validatePhone(String phone) throws DuplicateUserInfoException {
        if(userRepository.existsByPhoneNumber(PhoneNumber.of(phone))){
            log.warn("?????? ???????????? ??????"+ phone);
            throw new DuplicateUserInfoException("?????? ???????????? ???????????????.");
        }
    }


    private AuthToken makeAccessToken(User user) {
        if(user.getRoleType().equals(RoleType.ADMIN)) {
            log.warn("???????????? ????????? ???????????????.");
            return tokenProvider.createDevToken(user.getUserId(), user.getRoleType().getName());
        }
        else
            return tokenProvider.createAccessToken(user.getUserId(),user.getRoleType().getName());
    }

    private TokenSet makeNewAllToken(User user){
        AuthToken accessToken = makeAccessToken(user);
        AuthToken refreshToken = tokenProvider.createRefreshToken(user.getUserId());
        RefreshToken rt = new RefreshToken(user.getUserId(),refreshToken.getToken(), accessToken.getToken());
        refreshTokenRepository.save(rt);
        return new TokenSet(accessToken.getToken(),refreshToken.getToken());
    }

    private TokenSet makeNewAllToken(RefreshToken token){
        User user = userRepository.getReferenceById(token.getUserId());
        return makeNewAllToken(user);
    }

    private User getByCredentials(final PhoneNumber num, final String password) throws WrongLoginException{
        final User orginUser = userRepository.findByPhoneNumber(num);
        log.info("????????? ?????? id : "+ num.getPhone());
        if(orginUser != null && passwordEncoder.matches(password,orginUser.getEncPassword())){
            return orginUser;
        }
        else throw new WrongLoginException("????????? ????????? ???????????????.");
    }
}
