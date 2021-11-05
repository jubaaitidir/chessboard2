package com.echecs.api.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.echecs.api.model.Player;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PlayerRepoTest {

    // @Autowired
    // private PlayerRepo underTest;
    // @Test
    // public void itShouldFindByEmail() {
    //     //given
    //     Player player = new Player("atyidir","juba","yuba@tiferdoud.fr","yuba@cher12",1,false);
    //     //when
    //     underTest.save(player);
    //     Player result=underTest.findByEmail("yuba@tiferdoud.fr");
    //     //then
    //     assertEquals(player.getId(),result.getId());
    //     assertEquals(player.getNom(),result.getNom());
    //     assertEquals(player.getMdp(),result.getMdp());
    // }
}
    