package com.echecs.api.model.family;
import java.util.ArrayList;

import com.echecs.api.model.Piece;
import lombok.Getter;

import lombok.Setter;

//@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
public class Queen extends Piece {
    public Queen(String color){
        super("QUEEN",color,new ArrayList<String>());
    }
}
