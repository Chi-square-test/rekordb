package com.rekordb.rekordb.user.domain.userInfo;

import com.rekordb.rekordb.user.domain.userInfo.Gender;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class GenderAttributeConverter implements AttributeConverter<Gender, Integer> {


    @Override
    public Integer convertToDatabaseColumn(Gender attribute) {
        if(attribute == null) return null;
        if(attribute.equals(Gender.Female)) return 0;
        else return 1;
    }

    @Override
    public Gender convertToEntityAttribute(Integer dbData) {
        if(dbData == null) return null;
        if(dbData==0) return Gender.Female;
        else return Gender.Male;
    }
}
