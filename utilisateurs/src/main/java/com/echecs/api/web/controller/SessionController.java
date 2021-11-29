package com.echecs.api.web.controller;

import com.echecs.api.model.*;

import com.echecs.api.shared.MovementPieces;
import com.echecs.api.shared.SequenceGeneratorService;


import com.echecs.api.Repository.SessionRepo;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class SessionController {

    @Autowired
    private SessionRepo sessionRepo;
    @Autowired
    private ChessController chessController;
    //@Autowired
    //private ChessRepo chessRepo;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;


    
    /** 
     * @return ResponseEntity<?>
     */
    @RequestMapping(value = "/Sessions", method = RequestMethod.GET)
    public ResponseEntity<?> listSessions() {
        HttpHeaders headers = new HttpHeaders();
        ServletUriComponentsBuilder location = ServletUriComponentsBuilder
                .fromCurrentRequest();
        headers.setLocation(URI.create(location.toUriString()));
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers).body(sessionRepo.findAll());

    }

    
    /** 
     * @param id
     * @return Optional<Session>
     */
    @GetMapping(value = "/Sessions/{id}")
    public Optional<Session> sessionById(@PathVariable int id) {

        return sessionRepo.findById(id);
    }

    
    /** 
     * @param session
     * @return ResponseEntity<?>
     */
    @PostMapping(value = "/Sessions")
    public ResponseEntity<?> saveSession(@RequestBody Session session) {

        //System.out.println(session);
        if (session== null) {
            return ResponseEntity.noContent().build();
        }
        //si il existe deja un user avec le meme email, deja inscrit
        if(sessionRepo.findById((session.getIdSession())).isPresent()) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("une session est déja existante avec cet id");

        }
        /*
         incrementer idChess
         créer un chess
         */
        /*si le playerBlack et le playerWhite n'ont pas déjà une session existante , créer une nouvelle session
            sinon en créer reprendredre la session en incrémentant le idChess*/
        boolean new_players=true;
        int idChess;
        Session exist_session=new Session();
        Session sessionAdded=new Session();
        //faire un filtre, existe t'il une session déjà par les deux players, si oui la récupérer
        List<Session> listSession= sessionRepo.findAll();
        for (Session value : listSession) {
            if (((session.getIdBlackPlayer() == value.getIdBlackPlayer()) & (session.getIdWhitePlayer() == value.getIdWhitePlayer())) || ((session.getIdBlackPlayer() == value.getIdWhitePlayer()) & (session.getIdWhitePlayer() == value.getIdBlackPlayer()))) {
                new_players = false;
                exist_session = value;
            }
        }


        if(new_players){
            //dans ce cas c'est le premier chess pour les deux players, donc nouvelle session

            Chess chess = new Chess(State.NEW);


            //appel controleur pour incrément id et save chess
            chessController.saveChess(chess);

            /*
             enregistrer la nouvelle session et récupérer le résultat du save
             */
            //incrémenter l'id de la session, car c'est une nouvelle
            session.setIdSession(sequenceGeneratorService.generateSequence(Session.SEQUENCE_NAME));
            //modifier la liste_chess (vide à l'inistialisation) de la session en ajoutant le nouveau chess
            session.getListIdChess().add(chess.getIdChess());
            System.out.println("je crée une nouvelle session id : " +session.getIdSession());
            // save la nouvelle session
            sessionAdded = sessionRepo.save(session);

        }else{

            Chess chess = new Chess(State.NEW);
            //appel controleur pour incément id et save chess
            chessController.saveChess(chess);
            //ajouter l'id du nouvel chess à la liste de chess de la session

            idChess = chess.getIdChess();
            exist_session.getListIdChess().add(idChess);
            sessionAdded=sessionRepo.save(exist_session);

        }


        /*
          creation de l'uri a retourner en cas de succes du save
         */

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(sessionAdded.getIdSession())
                .toUri();


        return ResponseEntity.created(location).build();

    }

    
    /** 
     * @param idSession
     * @param idChess
     * @param idPlayer
     * @param from
     * @param to
     * @return ResponseEntity<?>
     */
    @PostMapping(value="Sessions/{idSession}/{idChess}/move")
    public ResponseEntity<?> movePiece(@PathVariable int idSession,@PathVariable int idChess,@RequestParam Optional<Integer> idPlayer,@RequestParam Optional<String> from,@RequestParam Optional<String> to) {

        Session session= sessionById(idSession).get();
        JsonObject response;
        if(session.getListIdChess().contains(idChess) && ((session.getIdWhitePlayer()==idPlayer.get())||(session.getIdBlackPlayer()==idPlayer.get()))){
            Chess chess = chessController.chessById(idChess).get();


           // System.out.println(chess.getSquares().get(from.get()).getPiece().getFamily());
            response = MovementPieces.movePiece(from.get(),to.get(),chess,idPlayer.get(),session);
            // save a new chess after modif
            chessController.updateChess(chess);


        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("le chess n'appartient pas à cette session ou le player ne joue pas dans cette session");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response.toString());
    }





}
