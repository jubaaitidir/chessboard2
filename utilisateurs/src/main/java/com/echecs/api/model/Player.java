package com.echecs.api.model;

import lombok.AllArgsConstructor;
//import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;



/** 
 * @return boolean
 */

/** 
 * @return boolean
 */

/** 
 * @return int
 */
@EqualsAndHashCode(callSuper = true)
//@Data
@AllArgsConstructor
@NoArgsConstructor

/** 
 * @return int
 */

/** 
 * @return boolean
 */
@Getter
@Setter
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

    public Player( String nom, String prenom, String email, String mdp, int sessionId, boolean rematchSent){
        super( nom, prenom, email, mdp);
        this.rematchSent=rematchSent;
        this.idSession=sessionId;       
    }
}
