package com.echecs.api.model.family;

import java.util.ArrayList;

import com.echecs.api.model.Piece;
import lombok.Getter;

import lombok.Setter;

//@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
public class King extends Piece {
    public King(String color){
        super("KING", color,new ArrayList<String>());
    }
}
