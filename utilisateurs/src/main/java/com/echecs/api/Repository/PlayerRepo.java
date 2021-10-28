package com.echecs.api.Repository;

import com.echecs.api.model.Player;
import com.echecs.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PlayerRepo extends JpaRepository<User, Integer> {

    Player findByEmail(String email);
}
