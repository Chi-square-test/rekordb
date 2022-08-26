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

    public static RouteId newRouteId(){
        return new RouteId(NanoIdUtils.randomNanoId());
    }

    @Override
    public String toString(){
        return id;
    }
}
