package com.echecs.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor

/** 
 * @return Piece
 */

/** 
 * @return boolean
 */
@Getter
@Setter
public class Square {
    private Piece piece;
    private boolean empty;

}
