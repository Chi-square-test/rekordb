package com.rekordb.rekordb.route;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.rekordb.rekordb.tag.TagId;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RouteId {

    @Column(name = "route_id")
    private String id;

    public static TagId newRouteId(){
        return new TagId(NanoIdUtils.randomNanoId());
    }
}
