package com.echecs.api.model;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor

/** 
 * @return int
 */

/** 
 * @return List<String>
 */

/** 
 * @return HashMap<String, Square>
 */

/** 
 * @return State
 */

/** 
 * @return int
 */
@Getter
@Setter

/** 
 * @return boolean
 */

/** 
 * @return boolean
 */

/** 
 * @return int
 */

/** 
 * @return String
 */
@Data
@Document(collection = "chess")
public class Chess {
    @Transient
    public static final String SEQUENCE_NAME = "chess_sequence";

    @Id
    private int idChess;
    @Field(value = "listMouvement")
    private List<String> moveHistory;

    @Field(value = "squares")
    private HashMap<String, Square> squares;
    @Field(value = "state")
    private State state;
    @Field(value = "time")
    private int time;

    public Chess(State state) {

        this.squares = new ChessBoard().getSquares();
        this.state = state;

        this.time = 0;
        this.moveHistory = new ArrayList<String>();

    }

    // @JsonIgnore
    /*
     * public String getLastMoveHistory(){ return moveHistory.get(moveHistory.size()
     * - 1); }
     * 
     * public void afficheChess(){ //System.out.println(this.idChess);
     * System.out.println(this.newPart.getSquares().get("a1").getPiece().getFamily()
     * +" "+this.newPart.getSquares().get("a1").getPiece().getColor()+" "+this.
     * newPart.getSquares().get("a1").isEmpty());
     * System.out.println(this.newPart.getSquares().get("b2").getPiece().getFamily()
     * +" "+this.newPart.getSquares().get("b2").getPiece().getColor());
     * System.out.println(this.newPart.getSquares().get("c3").getPiece()+" "+this.
     * newPart.getSquares().get("c3").getPiece()+" "+this.newPart.getSquares().get(
     * "c3").isEmpty());
     * System.out.println(this.newPart.getSquares().get("d4").getPiece()
     * +" "+this.newPart.getSquares().get("d4").getPiece()+" "+this.newPart.
     * getSquares().get("d4").isEmpty());
     * System.out.println(this.newPart.getSquares().get("e5").getPiece()
     * +" "+this.newPart.getSquares().get("e5").getPiece());
     * System.out.println(this.newPart.getSquares().get("f6").getPiece()
     * +" "+this.newPart.getSquares().get("f6").getPiece()+" "+this.newPart.
     * getSquares().get("f6").isEmpty());
     * System.out.println(this.newPart.getSquares().get("g7").getPiece().getFamily()
     * +" "+this.newPart.getSquares().get("g7").getPiece().getColor());
     * System.out.println(this.newPart.getSquares().get("h8").getPiece().getFamily()
     * +" "+this.newPart.getSquares().get("h8").getPiece().getColor()+" "+this.
     * newPart.getSquares().get("h8").isEmpty());
     * 
     * 
     * System.out.println(this.state); System.out.println(this.time + " seconde");
     * System.out.println(this.moveHistory);
     * 
     * }
     */

}
