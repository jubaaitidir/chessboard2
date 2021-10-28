package com.echecs.api.model;

import lombok.AllArgsConstructor;
//import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import javax.persistence.*;


@EqualsAndHashCode(callSuper = true)
//@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Player extends User{

    //private final UUID idPlayer = UUID.randomUUID();

    @Column(name="id_session")
    private int idSession;

    private boolean rematchSent;



    public Player(int sessionId, int userId, boolean rematchSent){
        super(userId);
        this.rematchSent = rematchSent;
        this.idSession = sessionId;
    }
}
