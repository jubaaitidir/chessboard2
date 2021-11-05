package com.echecs.api.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;
  
    
public class ChessTest {


     /**
     * getter and setter IdChess
     */
 
    @Test
    public void getIdChess() {
        //given
        Chess mychess= new Chess(State.NEW);

        //when
        int id= mychess.getIdChess();
        //then
        assertEquals(0, id);
        
    }

    @Test
    public void setIdChess() {
        //given
        Chess mychess= new Chess(State.NEW);

        //when
        mychess.setIdChess(10);
        //then
        assertEquals(10, mychess.getIdChess());
        
    }
    
     /**
     * getter and setter time
     */

    @Test
    public void setTime() {
        //given
        Chess mychess= new Chess(State.NEW);

        //when
        mychess.setTime(10);
        //then
        assertEquals(10, mychess.getTime());
        
    }

    @Test
    public void getTime() {
        //given
        Chess mychess= new Chess(State.NEW);

        //when
        int id= mychess.getTime();
        //then
        assertEquals(0, id);
        
    }

    /**
     * getter and setter state
     */
    @Test
    public void setState() {
        //given
        Chess mychess= new Chess(State.NEW);

        //when
        mychess.setState(State.RUNNING);
        //then
        assertEquals(State.RUNNING, mychess.getState());
        
    }

    @Test
    public void getState() {
        //given
        Chess mychess= new Chess(State.NEW);

        //when
        State state=mychess.getState();
        //then
        assertEquals(State.NEW, state);
        
    }
}
    