package com.echecs.api.shared;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "database_sequences")
public class DatabaseSequence {

    @Id
    private String id;

    private int seq;

    public DatabaseSequence() {}

    
    /** 
     * @return String
     */
    public String getId() {
        return id;
    }

    
    /** 
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    
    /** 
     * @return int
     */
    public int getSeq() {
        return seq;
    }

    
    /** 
     * @param seq
     */
    public void setSeq(int seq) {
        this.seq = seq;
    }
}
