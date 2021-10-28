package com.echecs.api.model.family;
import com.echecs.api.model.Piece;
import lombok.Getter;

import lombok.Setter;

//@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
public class Rook extends Piece{
    public Rook(String color){
        super("ROOK",color);
    }
}
