package com.echecs.api.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

//import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
@Data
@Document(collection = "session")
public class Session {
    @Transient
    public static final String SEQUENCE_NAME = "sessions_sequence";

    @Id
    private int idSession;
    @Field(name = "list_id_chess")
    private List<Integer> listIdChess;
    @Field("idWhitePlayer")
    private int idWhitePlayer;
    @Field("idBlackPlayer")
    private int idBlackPlayer;
    //@Column(name="state_chess")
    //private int state;

    public Session(){
        listIdChess= new ArrayList<>();
    }

    public void addIdChess(int id){
        listIdChess.add(id);
    }

}