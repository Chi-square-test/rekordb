package com.rekordb.rekordb.tourspot.domain.TourSpotDetail;

import com.rekordb.rekordb.tourspot.Exception.SpotDetailAPIErrorException;
import com.rekordb.rekordb.tourspot.domain.SpotId;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of ="spotId")
@Document(collection = "TourSpotDetail")
@Setter(AccessLevel.PRIVATE)
public class TourSpotDetail {

    @Id
    private SpotId spotId;
    private CommonItem commonItem; //detailCommon
    private List<DetailItem> detailItems; //detailInfo
    private Map<String,String> detailIntro; //detailIntro
    private List<ImageItem> imageItems;  //detailImage
    @Setter
    private String engOverview;


    private boolean detailCommonFin;
    private boolean detailIntroFin;
    private boolean detailInfoFin;
    private boolean detailImageFin;
    @Setter
    private boolean hasEngOverview;

    public static String replaceTag(String s){
        return s.replaceAll("<([^>]+)>", "");
    }

    public static TourSpotDetail emptyDetail(SpotId spotId){
        return new TourSpotDetail(spotId,null,null,null,null,null,false,false,false,false,false);
    }

    public void saveDetailCommon(CommonItem commonItem){
        this.setCommonItem(commonItem);
        this.setDetailCommonFin(true);
    }

    public void saveDetailInfo(List<DetailItem> detailItems){
        this.setDetailItems(detailItems);
        this.setDetailInfoFin(true);
    }

    public void saveDetailIntro(Map<String,String> detailIntro){
        this.setDetailIntro(detailIntro);
        this.setDetailIntroFin(true);
    }

    public void saveDetailImage(List<ImageItem> imageItems){
        this.setImageItems(imageItems);
        this.setDetailImageFin(true);
    }

    public boolean checkInformContain(){
        return detailCommonFin||detailImageFin||detailIntroFin||detailInfoFin;
    }


}
