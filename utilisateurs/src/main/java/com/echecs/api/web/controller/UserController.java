package com.echecs.api.web.controller;

import com.echecs.api.Repository.UserRepo;
import com.echecs.api.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;




@CrossOrigin(origins = "*")
@RestController
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @RequestMapping(value = "/Users", method = RequestMethod.GET)
    public ResponseEntity<?> listUsers() {
        HttpHeaders headers = new HttpHeaders();
        ServletUriComponentsBuilder location = ServletUriComponentsBuilder
                .fromCurrentRequest();
        headers.setLocation(URI.create(location.toUriString()));
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers).body(userRepo.findAll());

    }

    @GetMapping(value = "/Users/{id}")
    public Optional<User> userById(@PathVariable int id) {

        return userRepo.findById(id);
    }

    @PostMapping(value = "/Users/post")
    public ResponseEntity<?> saveUser(@RequestBody User user) {

        if (user == null) {
            return ResponseEntity.noContent().build();
        }
        //si il existe deja un user avec le meme email, deja inscrit
        if(userRepo.findByEmail(user.getEmail()) != null) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("utilisateur (adresse e-mail) déjà existant");

        }

        //enregistrer le user et récupérer le résultat du save
        User userAded = userRepo.save(user);
        //si le save return null
        if (userAded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userAded.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping(value = "/Users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id ,@RequestBody User user) {

        if (user == null)
            return ResponseEntity.noContent().build();

        //si le user existe pas
        if (userRepo.findByEmail(user.getEmail())==null) {
            System.out.println(userRepo.findByEmail(user.getEmail()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("aucun utilisateur avec cet identifiant/email");
        }

        //mise à jour du user
        User userAded = userRepo.findByEmail(user.getEmail());
        userAded.setNom(user.getNom());
        userAded.setPrenom(user.getPrenom());
        userAded.setEmail(user.getEmail());
        userAded.setMdp(user.getMdp());
        userRepo.save(userAded);


        return ResponseEntity.status(HttpStatus.ACCEPTED).body("utilisateur mis à jour");

    }
// fonction à revoir supprime mais ne renvoie pas le bon status
    @DeleteMapping(value="/Users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id){
        if(!userRepo.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("aucun utilisateur avec cet identifiant");
        }else{
            userRepo.deleteById(id);

            if(userRepo.findById(id).isPresent()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Probleme lors de la suppression, contacter l'admin du serveur API");
            }else{
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("utilisateur supprimé");

            }
        }

    }

}