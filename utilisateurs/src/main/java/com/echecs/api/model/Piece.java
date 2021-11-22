package com.echecs.api.model;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;


//@NoArgsConstructor
@AllArgsConstructor

/** 
 * @return String
 */

/** 
 * @return String
 */
@Getter
@Setter
public class Piece {
    private String family;
    private String color;
    private List<String> list_locked;
}
