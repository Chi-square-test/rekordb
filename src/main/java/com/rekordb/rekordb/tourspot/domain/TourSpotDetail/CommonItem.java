package com.rekordb.rekordb.tourspot.domain.TourSpotDetail;

import lombok.*;

import javax.persistence.Embeddable;
import java.util.StringTokenizer;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class CommonItem {

    private String homepage;

    private String tel;

    private String overview;

    public void parsingUrl(){
        if(!homepage.isBlank()){
            StringTokenizer tokenizer = new StringTokenizer(homepage,"\"");
            tokenizer.nextToken();
            homepage = tokenizer.nextToken();
        }
    }
}
