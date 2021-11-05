package com.echecs.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.HashMap;

import com.echecs.api.model.family.Rook;

import org.junit.jupiter.api.*;

public class ChessBoardTest {
    
    ChessBoard chessBoard;
    @BeforeEach
    public void setup() {
        chessBoard = new ChessBoard();

    }
    /**
     * test Constructor chessBoard(HashMap)
     */
    @Test
    public void testCosntuctorWithHashMap() {
        // given : 
        ChessBoard myChessBoard= new ChessBoard(chessBoard.getSquares());

        // when
        HashMap<String, Square> mySquares = myChessBoard.getSquares();
        HashMap<String, Square> expected = chessBoard.getSquares();
        // then
        assertEquals(expected.getClass(), mySquares.getClass());

    }
    

    /**
     * getter and setter color
     */

    @Test
    public void getColors() {
        // given : on the setup

        // when
        String white = ChessBoard.colors.get("W");
        String black = ChessBoard.colors.get("B");
        // then
        assertEquals("WHITE", white);
        assertEquals("BLACK", black);
    }

    @Test
    public void setColors() {
        // given : on the setup

        // when
        String red = ChessBoard.colors.put("R", "RED");
        String black = ChessBoard.colors.put("B", "BLACK");
        // then
        
        assertNull(red);
        assertEquals("BLACK", black);
    }

    /**
     * getter and setter positions
     */

    @Test
    public void getPositions() {
        // given : on the setup

        // when
        String pos_1 = ChessBoard.positions.get(0);
        String pos_2 = ChessBoard.positions.get(40);
        // then
        assertEquals("a1", pos_1);
        assertEquals("a6", pos_2);
    }

    @Test
    public void setPositions() {
        // given : on the setup

        // when
        ChessBoard.positions.addAll(Arrays.asList("a1", "b1", "c1", "f1", "g1", "h1", "a2", "b2", "c2", "d2", "e2",
                "f2", "g2", "h2", "a3", "h3", "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4", "a5", "b5", "c5", "d5",
                "e5", "f5", "g5", "c8", "d8", "e8", "f8", "g8", "h8"));

        // when
        String pos_1 = ChessBoard.positions.get(5);
        String pos_2 = ChessBoard.positions.get(15);
        // then
        assertEquals("f1", pos_1);
        assertEquals("h2", pos_2);
    }

     /**
     * getter and setter Squares
     */

    @Test
    public void getSquares() {
        // given : on the setup

        // when
        HashMap<String, Square> squares = chessBoard.getSquares();
        Square square_3= squares.get(ChessBoard.positions.get(2));

        // then
        assertEquals( new Square(new Rook(ChessBoard.colors.get("W")), false).getClass() , square_3.getClass());
     
    }

    @Test
    public void setSquares() {
        // given : on the setup

        // when
        chessBoard.setSquares(chessBoard.getSquares());

        // when
        
        // then
        assertEquals( new Square(new Rook(ChessBoard.colors.get("W")), false).getClass() , chessBoard.getSquares().get("a1").getClass());
     
    }
}
