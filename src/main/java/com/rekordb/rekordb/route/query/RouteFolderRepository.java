package com.rekordb.rekordb.route.query;

import com.rekordb.rekordb.route.RouteFolder;
import com.rekordb.rekordb.route.RouteId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RouteFolderRepository extends MongoRepository<RouteFolder, RouteId> {
}
