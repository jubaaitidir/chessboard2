package com.echecs.api.model.family;

import com.echecs.api.model.Chess;

import com.echecs.api.model.Piece;

import lombok.Getter;

import lombok.Setter;

//@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
public class Pown extends Piece {
    public Pown(String color){
        super("POWN", color);
    }

    
    /** 
     * @param from
     * @param to
     * @param chess
     * @return boolean
     */
    public boolean movePown(String from, String to, Chess chess){
        int from_line= Integer.parseInt(from.substring(1,2));
        String from_col= from.substring(0,1);
        int to_line= Integer.parseInt(to.substring(1,2));
        String to_col= to.substring(0,1);
        System.out.println(" fc "+from_col+" tc "+to_col);
        Boolean isMoved= false;

        if(from_col.equals(to_col)) {
            for (int i = from_line+1; i <= to_line; i++) {
                String key = from_col + String.valueOf(i);
                System.out.println(key);
                if (!chess.getSquares().get(key).isEmpty()) {
                    System.out.println("if empty");
                    return false;
                }
            }
            //déplacer la piece de la case from vers la case to
            chess.getSquares().get(to).setPiece(chess.getSquares().get(from).getPiece());
            //vider la case from
            chess.getSquares().get(from).setEmpty(true);
            chess.getSquares().get(from).setPiece(null);
            System.out.println("deplacement....");
            isMoved=true;
        }else{

            if(chess.getSquares().get(to).isEmpty()){
                isMoved= false;
            }else{//*si ligne_to=lign_from+ 1 ET col_to=col_from.nextLetter
                System.out.println(to_col.charAt(0)+" ?= "+from_col.charAt(0)+1);
                System.out.println(to_col.charAt(0)+" ?= "+from_col.charAt(0)+1);
                if((to_line==from_line+1) &&((to_col.charAt(0)==from_col.charAt(0)+1)||(to_col.charAt(0)==from_col.charAt(0)-1))){
                    chess.getSquares().replace(to,chess.getSquares().get(from));
                    chess.getSquares().get(from).setEmpty(true);
                    isMoved=true;
                }


            }
        }


    return isMoved;

    }

}
