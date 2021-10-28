package com.echecs.api.model;

import com.echecs.api.model.family.*;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

//@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
public class ChessBoard {
    // HashMap pour les deux couleurs White and Black
    public static HashMap<String, String> colors = new HashMap<>();
    // positions des pieces a1,b1,...h8
    public static List<String> positions = new ArrayList<>();
    // HashMap pour les cases cle:a1, value:Square(case)
    private HashMap<String, Square> squares = new HashMap<>();

    public ChessBoard() {

        create();

    }

    public ChessBoard(HashMap<String, Square> squares) {
        this.squares.putAll(squares);

    }

    private void create() {
        // si state de chess est egale à NEW
        colors.put("W", "WHITE");
        colors.put("B", "BLACK");
        positions.addAll(Arrays.asList("a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1", "a2", "b2", "c2", "d2", "e2",
                "f2", "g2", "h2", "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3", "a4", "b4", "c4", "d4", "e4", "f4",
                "g4", "h4", "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5", "a6", "b6", "c6", "d6", "e6", "f6", "g6",
                "h6", "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7", "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8"));

        /*
         * Initiation des pieces blanches
         */
        squares.put(positions.get(0), new Square(new Rook(colors.get("W")), false));
        squares.put(positions.get(1), new Square(new Knight(colors.get("W")), false));
        squares.put(positions.get(2), new Square(new Bishop(colors.get("W")), false));
        squares.put(positions.get(3), new Square(new Queen(colors.get("W")), false));
        squares.put(positions.get(4), new Square(new King(colors.get("W")), false));
        squares.put(positions.get(5), new Square(new Bishop(colors.get("W")), false));
        squares.put(positions.get(6), new Square(new Knight(colors.get("W")), false));
        squares.put(positions.get(7), new Square(new Rook(colors.get("W")), false));

        squares.put(positions.get(8), new Square(new Pown(colors.get("W")), false));
        squares.put(positions.get(9), new Square(new Pown(colors.get("W")), false));
        squares.put(positions.get(10), new Square(new Pown(colors.get("W")), false));
        squares.put(positions.get(12), new Square(new Pown(colors.get("W")), false));
        squares.put(positions.get(13), new Square(new Pown(colors.get("W")), false));
        squares.put(positions.get(14), new Square(new Pown(colors.get("W")), false));
        squares.put(positions.get(15), new Square(new Pown(colors.get("W")), false));
        squares.put(positions.get(16), new Square(new Pown(colors.get("W")), false));

        /*
         * Initiation des cases vides
         */

        for (int ligne = 2; ligne < 6; ligne++) {
            int pos_firstPB = Math.multiplyExact(8, ligne);
            for (int col = 0; col < 8; col++) {
                squares.put(positions.get(pos_firstPB + col), new Square(null, true));
            }
        }

        /*
         * Initiation des pieces Noires
         */
        int pos_firstPB = Math.multiplyExact(8, 6);
        squares.put(positions.get(pos_firstPB), new Square(new Pown(colors.get("B")), false));
        squares.put(positions.get(pos_firstPB + 1), new Square(new Pown(colors.get("B")), false));
        squares.put(positions.get(pos_firstPB + 2), new Square(new Pown(colors.get("B")), false));
        squares.put(positions.get(pos_firstPB + 3), new Square(new Pown(colors.get("B")), false));
        squares.put(positions.get(pos_firstPB + 4), new Square(new Pown(colors.get("B")), false));
        squares.put(positions.get(pos_firstPB + 5), new Square(new Pown(colors.get("B")), false));
        squares.put(positions.get(pos_firstPB + 6), new Square(new Pown(colors.get("B")), false));
        squares.put(positions.get(pos_firstPB + 7), new Square(new Pown(colors.get("B")), false));

        squares.put(positions.get(pos_firstPB + 8), new Square(new Rook(colors.get("B")), false));
        squares.put(positions.get(pos_firstPB + 9), new Square(new Knight(colors.get("B")), false));
        squares.put(positions.get(pos_firstPB + 10), new Square(new Bishop(colors.get("B")), false));
        squares.put(positions.get(pos_firstPB + 11), new Square(new Queen(colors.get("B")), false));
        squares.put(positions.get(pos_firstPB + 12), new Square(new King(colors.get("B")), false));
        squares.put(positions.get(pos_firstPB + 13), new Square(new Bishop(colors.get("B")), false));
        squares.put(positions.get(pos_firstPB + 14), new Square(new Knight(colors.get("B")), false));
        squares.put(positions.get(pos_firstPB + 15), new Square(new Rook(colors.get("B")), false));

    }

}
