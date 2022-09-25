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
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of ="spotId")
@Document(collection = "TourSpotDetail")
@Setter(AccessLevel.PRIVATE)
public class TourSpotDetail {

    @Id
    protected SpotId spotId;
    protected CommonItem commonItem; //detailCommon
    protected List<DetailItem> detailItems; //detailInfo
    protected Map<String,String> detailIntro; //detailIntro
    protected List<ImageItem> imageItems;  //detailImage

    //이하는 optional로 처리할것. 있으면 제공, 없으면 한글로
    @Setter
    protected CommonItem engCommonItem; //detailCommon
    @Setter
    protected List<DetailItem> engDetailItems; //detailInfo
    @Setter
    protected Map<String,String> engDetailIntro; //detailIntro


    @Setter
    protected String engOverview; //일단 여기다가도 저장할것. 나중에 완전 이전 필요


    protected boolean detailCommonFin;
    protected boolean detailIntroFin;
    protected boolean detailInfoFin;
    protected boolean detailImageFin;
    @Setter
    protected boolean hasEngOverview;



    public static String replaceTag(String s){
        return s.replaceAll("<([^>]+)>", "");
    }

    public static TourSpotDetail emptyDetail(SpotId spotId){
        return new TourSpotDetail(spotId,null,null,null,null,null,null,null,null,false,false,false,false,false);
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

    public void correctOverview(){
        if(engOverview != null && !engOverview.isBlank()){
            hasEngOverview = true;
        }
    }


}
