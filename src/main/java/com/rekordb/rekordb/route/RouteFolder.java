package com.rekordb.rekordb.route;

import com.rekordb.rekordb.tourspot.domain.TourSpotDocument;
import com.rekordb.rekordb.user.domain.userInfo.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Document(collection = "route")
public class RouteFolder {

    @Id
    private FolderId folderId;

    private String folderName; //이 값이 없으면 폴더 없는거.

    private RouteId routeId;

    private UserId userId;

    private List<TourSpotDocument> spotList;

    private String routeName;

    private int idx; //폴더 내 순서(혹은 메인에서 순서)

    public void changeFolderName(String name){
        this.folderName=name;
    }

    public void changeRouteName(String name){
        this.routeName=name;
    }
}
