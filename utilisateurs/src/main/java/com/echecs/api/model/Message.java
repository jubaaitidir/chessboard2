package com.echecs.api.model;

import com.echecs.api.shared.BaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Message extends BaseMessage {

    private String authorSession;
    private Instant date;

    public Message( String message, String authorSession, Instant date){
        super(message);
        this.authorSession = authorSession;
        this.date = date;
    }



}
