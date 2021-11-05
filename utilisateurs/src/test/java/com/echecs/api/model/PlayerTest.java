package com.echecs.api.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class PlayerTest {
    Player player;

    @BeforeEach
    void setup(){
         player = new Player("atyidir", "juba", "yuba@tiferdoud.fr", "yuba@cher12", 1, false);
    }

    @Test
    void getSessionId() {
        int id = player.getIdSession();
        assertEquals(1,id);
    }

    @Test
    void getUserId() {
        int id = player.getId();
        assertEquals(0,id);
        
    }

    @Test
    void isRematchSent() {
        boolean isRematchSent = player.isRematchSent();
        assertEquals(false, isRematchSent);
    }

    @Test
    void setRematchSent() {
        player.setRematchSent(true);
        boolean isRematchSent = player.isRematchSent();
        assertEquals(true, isRematchSent);
    }

    @Test
    void testToString() {
        player.toString();
    }

    @Test
    void testEquals() {
        player.equals(player);
    }

    @Test
    void canEqual() {
        player.canEqual(player);
    }

    @Test
    void testHashCode() {
        player.hashCode();
    }
}