package com.rekordb.rekordb.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

    @EmbeddedId
    private UserEmail userEmail;

    private String nickName;

    @Convert(converter = GenderAttributeConverter.class)
    private Gender gender;

    private LocalDateTime birth;

    private String country;

    @Convert(converter =LanguageAttributeConverter.class)
    private Language language;






}
