package com.echecs.api.model;

import com.echecs.api.model.family.*;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

//@NoArgsConstructor
//@AllArgsConstructor

/** 
 * @return HashMap<String, Square>
 */
@Getter
@Setter
public class ChessBoard {
    // HashMap pour les deux couleurs White and Black
    public static HashMap<String, String> colors = new HashMap<>();
    // positions des pieces a1,b1,...h8
    public static List<String> positions = new ArrayList<>();
    // HashMap pour les cases cle:a1, value:Square(case)
    private HashMap<String, Square> squares = new HashMap<>();
    // HashMap pour les cases bloques pour le king white
    private HashMap<String,List<String>> list_locked_white_king= new HashMap<>();
    // HashMap pour les cases bloques pour le king black
    private HashMap<String,List<String>> list_locked_black_king= new HashMap<>();

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

        for(int pos=8;pos<=15;pos++){
            squares.put(positions.get(pos), new Square(new Pown(colors.get("W")), false));
        }

       


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
        for(int i=0;i<=7;i++){
            squares.put(positions.get(pos_firstPB + i), new Square(new Pown(colors.get("B")), false));
        }
  

        squares.put(positions.get(pos_firstPB + 8), new Square(new Rook(colors.get("B")), false));
        squares.put(positions.get(pos_firstPB + 9), new Square(new Knight(colors.get("B")), false));
        squares.put(positions.get(pos_firstPB + 10), new Square(new Bishop(colors.get("B")), false));
        squares.put(positions.get(pos_firstPB + 11), new Square(new Queen(colors.get("B")), false));
        squares.put(positions.get(pos_firstPB + 12), new Square(new King(colors.get("B")), false));
        squares.put(positions.get(pos_firstPB + 13), new Square(new Bishop(colors.get("B")), false));
        squares.put(positions.get(pos_firstPB + 14), new Square(new Knight(colors.get("B")), false));
        squares.put(positions.get(pos_firstPB + 15), new Square(new Rook(colors.get("B")), false));

        /**
         * initialisation des cases locked for kings
         */
        /**
         * cases locked for black king
         */
        
        List<String> list_locked_up= squares.get(positions.get(8)).getPiece().getList_locked();
        
        list_locked_up.add(positions.get(17));
        list_locked_black_king.put(positions.get(8),list_locked_up);

        for(int pos=9;pos<=14;pos++){
            
            list_locked_up= squares.get(positions.get(pos)).getPiece().getList_locked();
             
            list_locked_up.addAll(Arrays.asList(positions.get(pos + 8 - 1),positions.get(pos + 8 + 1)));
            System.out.println(list_locked_up);
            list_locked_black_king.put(positions.get(pos), list_locked_up);
           
            System.out.println(list_locked_black_king);
        }
        list_locked_up= squares.get(positions.get(15)).getPiece().getList_locked();
        list_locked_up.add(positions.get(22));
        list_locked_black_king.put(positions.get(15),list_locked_up);
        System.out.println(list_locked_black_king);

          /**
         * cases locked for black king
         */
        
        List<String> list_locked_down= squares.get(positions.get(pos_firstPB)).getPiece().getList_locked();
        
        list_locked_down.add(positions.get(pos_firstPB-8+1));
        list_locked_white_king.put(positions.get(pos_firstPB),list_locked_down);

       
        for(int pos=1;pos<=6;pos++){
            
            list_locked_down= squares.get(positions.get(pos+pos_firstPB)).getPiece().getList_locked();
             
            list_locked_down.addAll(Arrays.asList(positions.get(pos +pos_firstPB- 8 - 1),positions.get(pos +pos_firstPB- 8 + 1)));
            System.out.println(list_locked_down);
            list_locked_white_king.put(positions.get(pos+pos_firstPB), list_locked_down);
           
            System.out.println(list_locked_black_king);
        }
        list_locked_down= squares.get(positions.get(pos_firstPB+7)).getPiece().getList_locked();
        list_locked_down.add(positions.get(pos_firstPB-2));
        list_locked_white_king.put(positions.get(pos_firstPB+7),list_locked_down);
        System.out.println(list_locked_white_king);
    }

}
