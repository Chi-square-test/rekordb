package com.rekordb.rekordb.user.domain;

import com.rekordb.rekordb.user.dto.RekorSignUpDTO;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private String Oauth2Id;

    @Embedded
    private Password password;

    @Convert(converter = GenderAttributeConverter.class)
    private Gender gender;

    private LocalDate birth;

    private String country;

    @Embedded
    private PhoneNumber phoneNumber;

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



    public static User signupFromDTO(RekorSignUpDTO dto){
        return User.builder()
                .userId(UserId.createUserId())
                .nickName(dto.getName()) //이 메서드 전에 검증 필요
                .birth(dto.getBirth())
                .password(dto.getEncPassword())
                .gender(Gender.values()[dto.getGender()])
                .language(Language.values()[dto.getLanguage()])
                .phoneNumber(PhoneNumber.of(dto.getPhone()))
                .providerType(ProviderType.REKOR)
                .roleType(RoleType.USER)
                .build();

    }

    public String getEncPassword(){
        return this.password.getEncPassword();
    }






}
