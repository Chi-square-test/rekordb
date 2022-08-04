package com.rekordb.rekordb.domain.route;

import com.rekordb.rekordb.domain.tourspot.SpotId;
import com.rekordb.rekordb.domain.user.UserId;
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
@Document(collation = "route")
public class Route {

    @EmbeddedId
    private RouteId routeId;

    @Embedded
    private UserId userId;

    private List<SpotId> spotList;

    private FolderId folderId;



}
