package com.echecs.api.web.controller;

import com.echecs.api.Repository.ChessRepo;
import com.echecs.api.model.Chess;

import com.echecs.api.shared.SequenceGeneratorService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RequestMapping("Chess")
@NoArgsConstructor
@RestController

public class ChessController {
    @Autowired
    public  ChessRepo chessRepo;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    public ChessController(ChessRepo chessRepo){
        this.chessRepo=chessRepo;
    }


    
    /** 
     * @return ResponseEntity<?>
     */
    @GetMapping(value = "/All")
    public ResponseEntity<?> listChess() {
        HttpHeaders headers = new HttpHeaders();
        ServletUriComponentsBuilder location = ServletUriComponentsBuilder
                .fromCurrentRequest();
        headers.setLocation(URI.create(location.toUriString()));
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers).body(chessRepo.findAll());

    }
    
    /** 
     * @param id
     * @return Optional<Chess>
     */
    @GetMapping(value = "/{id}")
    public Optional<Chess> chessById(@PathVariable int id) {

        return chessRepo.findById(id);
    }

    
    /** 
     * @param chess
     * @return ResponseEntity<?>
     */
    @PostMapping(value="")
    public ResponseEntity<?> saveChess(@RequestBody Chess chess) {

        if (chess == null) {
            return ResponseEntity.noContent().build();
        }

        if(chessRepo.findById(chess.getIdChess()).isPresent()) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("un chess avec cet id exite déja");

        }


        chess.setIdChess(sequenceGeneratorService.generateSequence(Chess.SEQUENCE_NAME));

        Chess chessAded = chessRepo.save(chess);

        if (chessAded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(chessAded.getIdChess())
                .toUri();

        return ResponseEntity.created(location).build();

    }

    
    /** 
     * @param chess
     * @return ResponseEntity<?>
     */
    @PutMapping(value="")
    public ResponseEntity<?> updateChess(@RequestBody Chess chess) {

        if (chess == null) {
            return ResponseEntity.noContent().build();
        }

        if(!chessRepo.findById(chess.getIdChess()).isPresent()) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ce chess n'existe pas");

        }


        //chess.setIdChess(sequenceGeneratorService.generateSequence(Chess.SEQUENCE_NAME));

        Chess chessAded = chessRepo.save(chess);

        if (chessAded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(chessAded.getIdChess())
                .toUri();

        return ResponseEntity.created(location).build();

    }






}
