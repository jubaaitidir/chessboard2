package com.echecs.api.web.controller;

import com.echecs.api.Repository.PlayerRepo;
import com.echecs.api.Repository.UserRepo;
import com.echecs.api.model.Player;
import com.echecs.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class PlayerController {
    @Autowired
    private PlayerRepo playerRepo;
    @Autowired
    private UserRepo userRepo;

    
    /** 
     * @return List<User>
     */
    @GetMapping(value= "/Players")
    public List<User> getAllPlayers() {
        List<User> listPlayers = new ArrayList<>();
        for (User user: playerRepo.findAll()) {
            if(user.getClass().equals(Player.class)){
                listPlayers.add(user);
            }
        }

        return listPlayers;
    }

    
    /** 
     * @param id
     * @return ResponseEntity<?>
     */
    @GetMapping(value = "/Players/{id}")
    public ResponseEntity<?> userById(@PathVariable int id) {
        Optional <User> user = userRepo.findById(id);
        if(!user.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("aucun utilisateur identifié avec cet id");
        }
        if(!user.get().getClass().equals(Player.class)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("le user identifié n'est pas un joueur");

        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
    }

    
    /** 
     * @param player
     * @return ResponseEntity<?>
     */
    @PostMapping(value= "/Players")
    public ResponseEntity<?> savePlayer(@RequestBody Player player) {

        if (player== null) {
            return ResponseEntity.noContent().build();
        }
        //si il existe deja un user avec le meme email, deja inscrit
        if(playerRepo.findByEmail(player.getEmail()) != null) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("utilisateur (adresse e-mail) déjà existant");

        }
        //enregistrer le user et récupérer le résultat du save
         User playerAded = userRepo.save(player);
        //si le save return null
        if (playerAded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(playerAded.getId())
                .toUri();
        return ResponseEntity.created(location).build();

    }

    
    /** 
     * @param id
     * @return ResponseEntity<?>
     */
    @DeleteMapping(value="/Players/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable int id){
        Optional<User> user = playerRepo.findById(id);
        if(!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("aucun utilisateur avec cet identifiant");
        }else{
            if(!user.get().getClass().equals(Player.class)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("le user identifié n'est pas un joueur");
            }else{
                playerRepo.deleteById(id);
                if(playerRepo.findById(id).isPresent()){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Probleme lors de la suppression, contacter l'admin du serveur API");
                }else{
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body("utilisateur supprimé");

                }
            }
        }

    }

}
