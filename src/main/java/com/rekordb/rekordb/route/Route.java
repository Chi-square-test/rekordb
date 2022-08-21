package com.rekordb.rekordb.route;

import com.rekordb.rekordb.tourspot.SpotId;
import com.rekordb.rekordb.user.domain.UserId;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Document(collection = "route")
public class Route {

    @EmbeddedId
    private RouteId routeId;

    @Embedded
    private UserId userId;

    private List<SpotId> spotList;

    private String routeName;

    private FolderId folderId;



}
