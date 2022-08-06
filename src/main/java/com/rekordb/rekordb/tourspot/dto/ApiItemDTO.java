package com.rekordb.rekordb.tourspot.dto;

import com.rekordb.rekordb.tourspot.Address;
import com.rekordb.rekordb.tourspot.SpotCategory;
import com.rekordb.rekordb.tourspot.SpotId;
import com.rekordb.rekordb.tourspot.TourSpot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiItemDTO {

    private String contentid;
    private String title;
    private String addr1;
    private String addr2;
    private String cat1;
    private String cat2;
    private String cat3;
    private String firstimage;
    private String firstimage2;
    private String mapx;
    private String mapy;
    private int sigungucode;
    private int readcount;
    private int contenttypeid;

    public static TourSpot apiConvertEntity(ApiItemDTO dto){
        SpotId id = new SpotId(dto.contentid);
        Address address = new Address(dto.getSigungucode(), dto.getAddr1(), dto.getAddr2(), dto.getMapx(), dto.getMapy());
        SpotCategory category = new SpotCategory(dto.getCat1(), dto.getCat2(), dto.getCat3());
        List<String> list= new ArrayList<>();
        list.add(dto.getFirstimage());
        list.add(dto.getFirstimage2());

        return TourSpot.builder()
                .spotId(id)
                .title(dto.getTitle())
                .addr(address)
                .cat(category)
                .imgs(list)
                .readCount(dto.getReadcount())
                .typeid(dto.getContenttypeid())
                .build();
    }



}
