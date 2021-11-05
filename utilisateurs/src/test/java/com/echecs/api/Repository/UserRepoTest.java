package com.echecs.api.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.echecs.api.model.Player;
import com.echecs.api.model.User;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
   
@DataJpaTest  
public class UserRepoTest {

    // @Autowired
    // private UserRepo underTest;
    // @Test
    // public void itShouldFindByEmail() {
    //     //given
    //     Player player = new Player("atyidir","juba","yuba@tiferdoud.fr","yuba@cher12",1,false);
    //     //when
    //     //User result=underTest.save(player);
      
    //     //then
    //     assertEquals(0,player.getId());
    //     assertEquals("atyidir",player.getNom());
    //     assertEquals("yuba@cher12",player.getMdp());
    // }
}
    