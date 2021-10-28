package com.echecs.api.Repository;

import com.echecs.api.model.Chess;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChessRepo extends MongoRepository<Chess, Integer> {

}
