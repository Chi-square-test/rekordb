package com.rekordb.rekordb.user.domain;

import com.rekordb.rekordb.user.domain.Language;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LanguageAttributeConverter implements AttributeConverter<Language, Integer> {


    @Override
    public Integer convertToDatabaseColumn(Language attribute) {
        if(attribute == null) return null;
        if(attribute.equals(Language.한국어)) return 0;
        else return 1;
    }

    @Override
    public Language convertToEntityAttribute(Integer dbData) {
        if(dbData == null) return null;
        if(dbData==0) return Language.한국어;
        else return Language.English;
    }
}
