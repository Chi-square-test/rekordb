package com.rekordb.rekordb.user;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LanguageAttributeConverter implements AttributeConverter<Language, Integer> {


    @Override
    public Integer convertToDatabaseColumn(Language attribute) {
        if(attribute.equals(Language.한국어)) return 0;
        else return 1;
    }

    @Override
    public Language convertToEntityAttribute(Integer dbData) {
        if(dbData==0) return Language.한국어;
        else return Language.English;
    }
}
