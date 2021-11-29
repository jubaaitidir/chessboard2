package com.echecs.api.model.family;

import java.util.ArrayList;

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
        super("POWN", color,new ArrayList<String>());
    }

    

}
