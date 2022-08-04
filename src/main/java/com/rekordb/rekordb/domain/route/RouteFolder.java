package com.rekordb.rekordb.domain.route;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EmbeddedId;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Document(collation = "folder")
public class RouteFolder {

    @EmbeddedId
    private FolderId folderId;

    private String folderName;

    public void changeFolderName(String name){
        this.folderName=name;
    }


}
