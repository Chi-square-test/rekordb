package com.rekordb.rekordb.tourspot.domain;

import javax.persistence.AttributeConverter;

public class RekorCategoryAttributeConverter implements AttributeConverter<RekorCategory,Integer> {
    @Override
    public Integer convertToDatabaseColumn(RekorCategory attribute) {
        return attribute.idx();
    }

    @Override
    public RekorCategory convertToEntityAttribute(Integer dbData) {
        return RekorCategory.valueOfIndex(dbData);
    }
}
