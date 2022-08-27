package com.rekordb.rekordb.user.domain.userInfo;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.rekordb.rekordb.user.dto.RekorCreateDTO;
import com.rekordb.rekordb.user.dto.RekorJoinInformDTO;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
@Builder(access = AccessLevel.PRIVATE)
public class User {

    @EmbeddedId
    private UserId userId;

    @Column(unique = true)
    private String nickName;

    @Column
    private String Oauth2Id;

    @Embedded
    private Password password;

    @Column
    @Convert(converter = GenderAttributeConverter.class)
    private Gender gender;

    @Column
    private LocalDate birth;

    @Column
    private String country;

    @Embedded
    private PhoneNumber phoneNumber;

    @Column
    @Convert(converter = LanguageAttributeConverter.class)
    private Language language;

    @Column(name = "profile_img_url", length = 512)
    @Size(max = 512)
    private String profileImageUrl;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(updatable = false)
    private LocalDateTime updateAt;

    @Column(name = "PROVIDER_TYPE", length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ProviderType providerType;

    @Column(name = "ROLE_TYPE", length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleType roleType;

    private boolean isJoined;

    public static User updateJoinData(User user, RekorJoinInformDTO dto){
        return User.builder()
                .userId(user.getUserId())
                .phoneNumber(user.getPhoneNumber())
                .password(user.getPassword())
                .providerType(user.getProviderType())
                .roleType(user.getRoleType())

                .nickName(dto.getName()) //이 메서드 전에 검증 필요
                .birth(dto.getBirth())
                .gender(Gender.values()[dto.getGender()])
                .language(Language.values()[dto.getLanguage()])
                .country(dto.getCountry())
                .isJoined(true)
                .build();
    }



    public static User createFromDTO(RekorCreateDTO dto){
        return User.builder()
                .nickName(NanoIdUtils.randomNanoId())
                .userId(UserId.createUserId())
                .password(dto.getEncPassword())
                .phoneNumber(PhoneNumber.of(dto.getPhone()))
                .providerType(ProviderType.REKOR)
                .roleType(RoleType.USER)
                .isJoined(false)
                .build();
    }

    public String getEncPassword(){
        return this.password.getEncPassword();
    }






}
