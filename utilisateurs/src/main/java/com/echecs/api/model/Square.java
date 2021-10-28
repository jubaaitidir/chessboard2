package com.echecs.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Square {
    private Piece piece;
    private boolean empty;
/*
    public Square(Piece piece) {
        this.piece =piece;
        this.empty=true;
    }*/
}
