package com.echecs.api.model;

import com.echecs.api.shared.BaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;



/** 
 * @return boolean
 */

/** 
 * @return boolean
 */

/** 
 * @return int
 */
@EqualsAndHashCode(callSuper = true)

/** 
 * @return String
 */

/** 
 * @return Instant
 */

/** 
 * @return String
 */
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
