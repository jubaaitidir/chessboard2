package com.echecs.api.model.family;

import java.util.ArrayList;

import com.echecs.api.model.Piece;
import lombok.Getter;

import lombok.Setter;

//@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
public class Knight extends Piece{
    public Knight(String color){
        super("KNIGHT",color,new ArrayList<String>());
    }
}
