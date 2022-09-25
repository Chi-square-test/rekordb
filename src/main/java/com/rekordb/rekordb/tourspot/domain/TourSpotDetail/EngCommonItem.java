package com.rekordb.rekordb.tourspot.domain.TourSpotDetail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Embeddable;
import java.util.StringTokenizer;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class EngCommonItem extends CommonItem{

    private String title;

    public CommonItem exceptTitle(){
        return new CommonItem(getHomepage(),getTel(),getOverview());
    }

}
