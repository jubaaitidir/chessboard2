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
    @Field(value = "listLockedWhiteKing")
    private HashMap<String,List<String>> list_locked_white_king;
    @Field(value = "list_locked_black_king")
    private HashMap<String,List<String>> list_locked_black_king;
  

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
        this.list_locked_white_king=new ChessBoard().getList_locked_white_king();
        this.list_locked_black_king=new ChessBoard().getList_locked_black_king();
    }



}
