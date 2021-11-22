package com.echecs.api.model.family;

import java.util.ArrayList;

import com.echecs.api.model.Piece;
import lombok.Getter;

import lombok.Setter;

//@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
public class Bishop extends Piece {
    public Bishop(String color){
        super("BISHOP",color,new ArrayList<String>());
    }
}
