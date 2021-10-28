package com.echecs.api.Repository;


import com.echecs.api.model.Session;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SessionRepo extends MongoRepository<Session, Integer> {

}
