package com.rekordb.rekordb.route.query;

import com.rekordb.rekordb.route.Route;
import com.rekordb.rekordb.route.RouteId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RouteRepository extends MongoRepository<Route, RouteId> {
}
