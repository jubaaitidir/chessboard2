package com.echecs.api.shared;

import com.echecs.api.model.Chess;
import com.echecs.api.model.ChessBoard;
import com.echecs.api.model.Piece;
import com.echecs.api.model.Session;
import com.echecs.api.model.Square;

import org.bson.json.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MovementPieces {

    // récupérer la liste des cases
    public static List<String> liste_cases = Arrays.asList("a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1", "a2", "b2",
            "c2", "d2", "e2", "f2", "g2", "h2", "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3", "a4", "b4", "c4", "d4",
            "e4", "f4", "g4", "h4", "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5", "a6", "b6", "c6", "d6", "e6", "f6",
            "g6", "h6", "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7", "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8");

    /**
     * @param from
     * @param to
     * @param chess
     * @param idPlayer
     * @param session
     * @return JsonObject
     */
    public static JsonObject movePiece(String from, String to, Chess chess, int idPlayer, Session session) {

        int idBlackPlayer = session.getIdBlackPlayer();
        int idWhitePlayer = session.getIdWhitePlayer();

        // piece is present in case from
        if (chess.getSquares().get(from).isEmpty()) {
            return new JsonObject(new String("{'aucune pièce à déplacer, la case depart est vide! '}"));
        }

        if (liste_cases.contains(from) == false || liste_cases.contains(to) == false) {
            return new JsonObject(new String("{'la case départ/destination n'appartient à l'échequier! '}"));
        }
        // couleur de la pièce à déplacer
        String color = chess.getSquares().get(from).getPiece().getColor();
        System.out.println("idPlayer: " + idPlayer + "idPlayerBlack: " + idBlackPlayer + " color :" + color);
        System.out.println(
                "is good color: " + (color.equals("BLACK")) + "is good player: " + (idPlayer == idBlackPlayer));

        // si la couleur des pièces sont différente de la couleur du player
        if ((color.equals("BLACK") && (idPlayer != idBlackPlayer))
                || (color.equals("WHITE") && (idPlayer != idWhitePlayer))) {
            return new JsonObject(new String("{'message':'ces pieces ne vous appartiennent pas ! '}"));
        } else {

            Square square_from = chess.getSquares().get(from);
            Square square_to = chess.getSquares().get(to);
            if (!square_to.isEmpty()) {
                if (Objects.equals(square_from.getPiece().getColor().toString(),
                        square_to.getPiece().getColor().toString())) {
                    return new JsonObject(
                            new String("{'message':'case destination occupée, par une de vos pieces! '}"));
                }
            }
        }

        switch (chess.getSquares().get(from).getPiece().getFamily()) {

        case "POWN":
            return (movePown(from, to, chess)) ? (new JsonObject(new String("{'message':'move executed'}")))
                    : (new JsonObject(new String("{'message':'deplacement du pion refuse'}")));
        case "ROOK":
            return (moveRook(from, to, chess)) ? (new JsonObject(new String("{'message':'move executed'}")))
                    : (new JsonObject(new String("{'message':'deplacement de la tour refuse'}")));
        case "KNIGHT":
            return (moveKnight(from, to, chess)) ? (new JsonObject(new String("{'message':'move executed'}")))
                    : (new JsonObject(new String("{message:deplacement du pion refuse}")));
        case "BISHOP":
            return (moveBishop(from, to, chess)) ? (new JsonObject(new String("{'message':'move executed'}")))
                    : (new JsonObject(new String("{'message':'deplacement du pion refuse'}")));
        case "KING":
            return (moveKing(from, to, chess)) ? (new JsonObject(new String("{'message':'move executed'}")))
                    : (new JsonObject(new String("{'message':'deplacement du pion refuse'}")));
        case "QUEEN":
            return (moveQueen(from, to, chess)) ? (new JsonObject(new String("{'message':'move executed'}")))
                    : (new JsonObject(new String("{'message':'deplacement du pion refuse'}")));

        default:
            return new JsonObject(new String("{'message':'famille ou couleur de la piece incorrecte'}"));

        }

    }

    /**
     * @param from
     * @param to
     * @param chess
     * @return boolean
     */
    public static boolean movePown(String from, String to, Chess chess) {
        // traitement de la query de l'url pour récupérer les lignes et colonnes.
        int from_line = Integer.parseInt(from.substring(1, 2));
        String from_col = from.substring(0, 1);
        int to_line = Integer.parseInt(to.substring(1, 2));
        String to_col = to.substring(0, 1);

        int nb_to_col = (int) to.substring(0, 1).charAt(0);

        int nb_from_col = (int) from.substring(0, 1).charAt(0);
        String key_from = ((char) (nb_from_col)) + String.valueOf(from_line);
        String key_to = ((char) (nb_to_col)) + String.valueOf(to_line);
        String key_locked_right_up = ((char) (nb_to_col + 1)) + String.valueOf(to_line + 1);
        String key_locked_left_up = ((char) (nb_to_col - 1)) + String.valueOf(to_line + 1);
        String key_locked_rd = ((char) (nb_to_col + 1)) + String.valueOf(to_line - 1);
        String key_locked_ld = ((char) (nb_to_col - 1)) + String.valueOf(to_line - 1);
        Boolean isMoved = false;

        if (from_col.equals(to_col)) {
            // deplacement de 1 ou 2 cases seulement
            if (((to_line - from_line) == 1 || (to_line - from_line) == 2)
                    && chess.getSquares().get(from).getPiece().getColor().equals("WHITE")) {
                // vérifier que les cases se trouvant entre la case de départ et destination
                // sont vides
                for (int i = from_line + 1; i <= to_line; i++) {
                    String key = from_col + String.valueOf(i);
                    System.out.println(key);
                    if (!chess.getSquares().get(key).isEmpty()) {
                        // System.out.println("if empty");
                        return false;
                    }
                }

                // déplacer la piece de la case from vers la case to
                chess.getSquares().get(to).setPiece(chess.getSquares().get(from).getPiece());
                chess.getSquares().get(to).setEmpty(false);
                // vider la case from
                chess.getSquares().get(from).setEmpty(true);
                chess.getSquares().get(from).setPiece(null);
                System.out.println("deplacement....");
                isMoved = true;

                // ajouter les cases bloqués pour le roi noir
                List<String> list_locked_up = new ArrayList<String>();
                if (to_col.equals("h")) {
                    list_locked_up.add(key_locked_left_up);
                } else if (to_col.equals("a")) {
                    list_locked_up.add(key_locked_right_up);
                } else {
                    list_locked_up.addAll(Arrays.asList(key_locked_left_up, key_locked_right_up));
                }
                chess.getList_locked_black_king().put(key_to, list_locked_up);
                chess.getList_locked_black_king().remove(key_from);
                chess.getSquares().get(key_to).getPiece().getList_locked().clear();
                chess.getSquares().get(key_to).getPiece().getList_locked().addAll(list_locked_up);
                System.out.println(chess.getList_locked_black_king());

            } else if (((to_line - from_line) == -1 || (to_line - from_line) == -2)
                    && chess.getSquares().get(from).getPiece().getColor().equals("BLACK")) {
                // vérifier que les cases se trouvant entre la case de départ et destination
                // sont vides
                for (int i = from_line - 1; i >= to_line; i--) {
                    String key = from_col + String.valueOf(i);
                    System.out.println(key);
                    if (!chess.getSquares().get(key).isEmpty()) {
                        // System.out.println("if empty");
                        return false;
                    }
                }

                // déplacer la piece de la case from vers la case to
                chess.getSquares().get(to).setPiece(chess.getSquares().get(from).getPiece());
                chess.getSquares().get(to).setEmpty(false);
                // vider la case from
                chess.getSquares().get(from).setEmpty(true);
                chess.getSquares().get(from).setPiece(null);
                System.out.println("deplacement....");
                isMoved = true;

                List<String> list_locked_down = new ArrayList<String>();
                if (to_col.equals("h")) {
                    list_locked_down.add(key_locked_ld);
                } else if (to_col.equals("a")) {
                    list_locked_down.add(key_locked_rd);
                } else {
                    list_locked_down.addAll(Arrays.asList(key_locked_ld, key_locked_rd));
                }

                chess.getList_locked_white_king().put(key_to, list_locked_down);
                chess.getList_locked_white_king().remove(key_from);
                chess.getSquares().get(key_to).getPiece().getList_locked().clear();
                chess.getSquares().get(key_to).getPiece().getList_locked().addAll(list_locked_down);

            } else {
                isMoved = false;
            }
            // ajouter le controle de déplacement car le pion ne se déplace pas en
            // horizontal
        } else {

            if (chess.getSquares().get(to).isEmpty()) {
                isMoved = false;
            } else {// *si ligne_to=lign_from+ 1 ET col_to=col_from.nextLetter
                    // System.out.println(to_col.charAt(0)+" ?= "+from_col.charAt(0)+1);

                if ((to_line == from_line + 1)
                        && ((to_col.charAt(0) == from_col.charAt(0) + 1)
                                || (to_col.charAt(0) == from_col.charAt(0) - 1))
                        && chess.getSquares().get(from).getPiece().getColor().equals("WHITE")) {

                    chess.getSquares().get(to).setPiece(chess.getSquares().get(from).getPiece());
                    chess.getSquares().get(to).setEmpty(false);
                    // vider la case from
                    chess.getSquares().get(from).setEmpty(true);
                    chess.getSquares().get(from).setPiece(null);
                    isMoved = true;

                    // ajouter les cases bloqués pour le roi noir
                    List<String> list_locked_up = new ArrayList<String>();
                    if (to_col.equals("h")) {
                        list_locked_up.add(key_locked_left_up);
                    } else if (to_col.equals("a")) {
                        list_locked_up.add(key_locked_right_up);
                    } else {
                        list_locked_up.addAll(Arrays.asList(key_locked_left_up, key_locked_right_up));
                    }
                    chess.getList_locked_black_king().put(key_to, list_locked_up);
                    chess.getList_locked_black_king().remove(key_from);
                    chess.getSquares().get(key_to).getPiece().getList_locked().clear();
                    chess.getSquares().get(key_to).getPiece().getList_locked().addAll(list_locked_up);

                } else if ((to_line == from_line - 1)
                        && ((to_col.charAt(0) == from_col.charAt(0) + 1)
                                || (to_col.charAt(0) == from_col.charAt(0) - 1))
                        && chess.getSquares().get(from).getPiece().getColor().equals("BLACK")) {
                    // emplir la case to
                    chess.getSquares().get(to).setPiece(chess.getSquares().get(from).getPiece());
                    chess.getSquares().get(to).setEmpty(false);
                    // vider la case from
                    chess.getSquares().get(from).setEmpty(true);
                    chess.getSquares().get(from).setPiece(null);
                    isMoved = true;

                    List<String> list_locked_down = new ArrayList<String>();
                    if (to_col.equals("h")) {
                        list_locked_down.add(key_locked_ld);
                    } else if (to_col.equals("a")) {
                        list_locked_down.add(key_locked_rd);
                    } else {
                        list_locked_down.addAll(Arrays.asList(key_locked_ld, key_locked_rd));
                    }

                    chess.getList_locked_white_king().put(key_to, list_locked_down);
                    chess.getList_locked_white_king().remove(key_from);
                    chess.getSquares().get(key_to).getPiece().getList_locked().clear();
                    chess.getSquares().get(key_to).getPiece().getList_locked().addAll(list_locked_down);

                }

            }
        }

        return isMoved;

    }

    /**
     * @param from
     * @param to
     * @param chess
     * @return boolean
     */
    public static boolean moveRook(String from, String to, Chess chess) {
        // traitement de la query de l'url pour récupérer les lignes et colonnes.
        int from_line = Integer.parseInt(from.substring(1, 2));
        String from_col = from.substring(0, 1);
        int to_line = Integer.parseInt(to.substring(1, 2));
        String to_col = to.substring(0, 1);
        // System.out.println(" fc "+from_col+" tc "+to_col);

        Boolean isMoved = false;

        if (from_col.equals(to_col)) {

            // vérifier que les cases de la meme colonne se trouvant entre la case de départ
            // et destination sont vides

            for (int i = from_line + 1; i < to_line; i++) {
                String key = from_col + String.valueOf(i);
                System.out.println(key);
                if (!chess.getSquares().get(key).isEmpty()) {
                    // System.out.println("if empty");
                    return false;
                }
            }

            // déplacer la piece de la case from vers la case to
            chess.getSquares().get(to).setPiece(chess.getSquares().get(from).getPiece());
            chess.getSquares().get(to).setEmpty(false);
            // vider la case from
            chess.getSquares().get(from).setEmpty(true);
            chess.getSquares().get(from).setPiece(null);
            System.out.println("deplacement....");
            isMoved = true;

        } else if (from_line == to_line) {
            // vérifier que les cases de la meme ligne se trouvant entre la case de départ
            // et destination sont vides

            int nb_to_col = (int) to.substring(0, 1).charAt(0);
            System.out.println(nb_to_col);
            int nb_from_col = (int) from.substring(0, 1).charAt(0);
            System.out.println(nb_from_col);
            // int nb_col_deplacement = nb_to_col - nb_from_col;
            if (nb_from_col > nb_to_col) {
                // System.out.println(nb_col_deplacement);
                int pos_depart = nb_from_col - 1;
                for (int pos_col = pos_depart; pos_col > nb_to_col; pos_col--) {

                    String key = ((char) (pos_col)) + String.valueOf(from_line);

                    if (!chess.getSquares().get(key).isEmpty()) {

                        return false;
                    }
                }

            } else {

                for (int pos_col = nb_from_col + 1; pos_col < nb_to_col; pos_col++) {

                    String key = ((char) (pos_col)) + String.valueOf(from_line);
                    // System.out.println("key : " + key);

                    if (!chess.getSquares().get(key).isEmpty()) {
                        // System.out.println("if not empty return ...");
                        return false;
                    }
                }

            }
            // déplacer la piece de la case from vers la case to
            chess.getSquares().get(to).setPiece(chess.getSquares().get(from).getPiece());
            chess.getSquares().get(to).setEmpty(false);
            // vider la case from
            chess.getSquares().get(from).setEmpty(true);
            chess.getSquares().get(from).setPiece(null);
            System.out.println("deplacement....");
            isMoved = true;

        } else {
            isMoved = false;

        }

        if (isMoved) {
            update_list_locked_linear(from, to, chess);
        }

        return isMoved;
    }

    /**
     * @param from
     * @param to
     * @param chess
     * @return boolean
     */
    public static boolean moveBishop(String from, String to, Chess chess) {
        Boolean isMoved = false;

        // traitement de la query de l'url pour récupérer les lignes et colonnes.
        int from_line = Integer.parseInt(from.substring(1, 2));
        // String from_col= from.substring(0,1);
        int to_line = Integer.parseInt(to.substring(1, 2));
        // String to_col= to.substring(0,1);

        int nb_to_col = (int) to.substring(0, 1).charAt(0);
        // System.out.println(nb_to_col);
        int nb_from_col = (int) from.substring(0, 1).charAt(0);
        // System.out.println(nb_from_col);
        if (Math.abs(to_line - from_line) == Math.abs(nb_to_col - nb_from_col)) {
            if (to_line > from_line) {
                if (nb_to_col > nb_from_col) {
                    // se_deplacer en haut à droite
                    int pos_depart = nb_from_col + 1;

                    int pos_col = pos_depart;
                    for (int pos_line = from_line + 1; pos_line < to_line; pos_line++) {
                        String key = ((char) (pos_col)) + String.valueOf(pos_line);
                        System.out.println("key : " + key);
                        pos_col++;
                        if (!chess.getSquares().get(key).isEmpty()) {
                            // System.out.println("if not empty return ...");
                            return isMoved;
                        }
                    }

                } else {
                    // se_deplacer en haut à gauche
                    int pos_depart = nb_from_col - 1;
                    int pos_col = pos_depart;
                    for (int pos_line = from_line + 1; pos_line < to_line; pos_line++) {
                        String key = ((char) (pos_col)) + String.valueOf(pos_line);
                        System.out.println("key : " + key);
                        pos_col--;
                        if (!chess.getSquares().get(key).isEmpty()) {
                            // System.out.println("if not empty return ...");
                            return isMoved;
                        }
                    }

                }

            } else {
                System.out.print("se déplacer en bas à droite");
                if (nb_to_col > nb_from_col) {

                    int pos_depart = nb_from_col + 1;

                    int pos_col = pos_depart;
                    for (int pos_line = from_line - 1; pos_line > to_line; pos_line--) {
                        String key = ((char) (pos_col)) + String.valueOf(pos_line);
                        System.out.println("key : " + key);
                        pos_col++;
                        if (!chess.getSquares().get(key).isEmpty()) {
                            // System.out.println("if not empty return ...");
                            return isMoved;
                        }
                    }

                } else {
                    System.out.print("se déplacer en bas à gauche");

                    int pos_depart = nb_from_col - 1;
                    int pos_col = pos_depart;
                    for (int pos_line = from_line - 1; pos_line > to_line; pos_line--) {
                        String key = ((char) (pos_col)) + String.valueOf(pos_line);
                        System.out.println("key : " + key);
                        pos_col--;
                        if (!chess.getSquares().get(key).isEmpty()) {
                            // System.out.println("if not empty return ...");
                            return isMoved;
                        }
                    }

                }

            }

            // déplacer la piece de la case from vers la case to
            chess.getSquares().get(to).setPiece(chess.getSquares().get(from).getPiece());
            chess.getSquares().get(to).setEmpty(false);
            // vider la case from
            chess.getSquares().get(from).setEmpty(true);
            chess.getSquares().get(from).setPiece(null);
            System.out.println("deplacement....");
            isMoved = true;
        } else {
            isMoved = false;
        }
        if (isMoved) {
            update_list_locked_diagonal(from, to, chess);
        }

        return isMoved;
    }

    /**
     * @param from
     * @param to
     * @param chess
     * @return boolean
     */
    public static boolean moveKnight(String from, String to, Chess chess) {
        // traitement de la query de l'url pour récupérer les lignes et colonnes.
        int from_line = Integer.parseInt(from.substring(1, 2));
        // String from_col= from.substring(0,1);
        int to_line = Integer.parseInt(to.substring(1, 2));
        // String to_col= to.substring(0,1);

        int nb_to_col = (int) to.substring(0, 1).charAt(0);
        // System.out.println(nb_to_col);
        int nb_from_col = (int) from.substring(0, 1).charAt(0);
        // System.out.println(nb_from_col);
        Boolean isMoved = false;
        // HashMap list_mouvements= new HashMap<String, Integer>();

        // list_mouvements.put()

        int dist_line = Math.abs(to_line - from_line);
        int dist_col = Math.abs(nb_to_col - nb_from_col);
        int pythagore = (int) (Math.pow(dist_line, 2) + Math.pow(dist_col, 2));
        if (pythagore == 5) {
            // déplacer la piece de la case from vers la case to
            chess.getSquares().get(to).setPiece(chess.getSquares().get(from).getPiece());
            chess.getSquares().get(to).setEmpty(false);
            // vider la case from
            chess.getSquares().get(from).setEmpty(true);
            chess.getSquares().get(from).setPiece(null);
            System.out.println("deplacement....");
            isMoved = true;

        } else {
            isMoved = false;
        }

        if (isMoved) {
            update_list_locked_by_knight(from, to, chess);
        }
        return isMoved;
    }

    /**
     * @param from
     * @param to
     * @param chess
     * @return boolean
     */
    public static boolean moveQueen(String from, String to, Chess chess) {
        // traitement de la query de l'url pour récupérer les lignes et colonnes.
        int from_line = Integer.parseInt(from.substring(1, 2));
        String from_col = from.substring(0, 1);
        int to_line = Integer.parseInt(to.substring(1, 2));
        String to_col = to.substring(0, 1);

        int nb_to_col = (int) to.substring(0, 1).charAt(0);
        // System.out.println(nb_to_col);
        int nb_from_col = (int) from.substring(0, 1).charAt(0);
        // System.out.println(nb_from_col);
        Boolean isMoved = false;

        if (from_col.equals(to_col)) {

            // vérifier que les cases de la meme colonne se trouvant entre la case de départ
            // et destination sont vides
            for (int i = from_line + 1; i < to_line; i++) {
                String key = from_col + String.valueOf(i);
                System.out.println(key);
                if (!chess.getSquares().get(key).isEmpty()) {
                    // System.out.println("if empty");
                    return false;
                }
            }

            // déplacer la piece de la case from vers la case to
            chess.getSquares().get(to).setPiece(chess.getSquares().get(from).getPiece());
            chess.getSquares().get(to).setEmpty(false);
            // vider la case from
            chess.getSquares().get(from).setEmpty(true);
            chess.getSquares().get(from).setPiece(null);
            System.out.println("deplacement....");
            isMoved = true;

        } else if (from_line == to_line) {
            // vérifier que les cases de la meme ligne se trouvant entre la case de départ
            // et destination sont vides

            // int nb_to_col = (int) to.substring(0, 1).charAt(0);

            // int nb_from_col = (int) from.substring(0, 1).charAt(0);

            if (nb_from_col > nb_to_col) {

                int pos_depart = nb_from_col - 1;
                for (int pos_col = pos_depart; pos_col > nb_to_col; pos_col--) {

                    String key = ((char) (pos_col)) + String.valueOf(from_line);
                    // System.out.println("key : " + key);

                    if (!chess.getSquares().get(key).isEmpty()) {

                        return false;
                    }
                }

            } else {

                for (int pos_col = nb_from_col + 1; pos_col < nb_to_col; pos_col++) {

                    String key = ((char) (pos_col)) + String.valueOf(from_line);
                    // System.out.println("key : " + key);

                    if (!chess.getSquares().get(key).isEmpty()) {
                        // System.out.println("if not empty return ...");
                        return isMoved;
                    }
                }

            }
            // déplacer la piece de la case from vers la case to
            chess.getSquares().get(to).setPiece(chess.getSquares().get(from).getPiece());
            chess.getSquares().get(to).setEmpty(false);
            // vider la case from
            chess.getSquares().get(from).setEmpty(true);
            chess.getSquares().get(from).setPiece(null);
            System.out.println("deplacement....");
            isMoved = true;

        } else {

            if (Math.abs(to_line - from_line) == Math.abs(nb_to_col - nb_from_col)) {
                if (to_line > from_line) {
                    if (nb_to_col > nb_from_col) {
                        // se_deplacer en haut à droite
                        int pos_depart = nb_from_col + 1;

                        int pos_col = pos_depart;
                        for (int pos_line = from_line + 1; pos_line < to_line; pos_line++) {
                            String key = ((char) (pos_col)) + String.valueOf(pos_line);
                            System.out.println("key : " + key);
                            pos_col++;
                            if (!chess.getSquares().get(key).isEmpty()) {
                                // System.out.println("if not empty return ...");
                                return isMoved;
                            }
                        }

                    } else {
                        // se_deplacer en haut à gauche
                        int pos_depart = nb_from_col - 1;
                        int pos_col = pos_depart;
                        for (int pos_line = from_line + 1; pos_line < to_line; pos_line++) {
                            String key = ((char) (pos_col)) + String.valueOf(pos_line);
                            System.out.println("key : " + key);
                            pos_col--;
                            if (!chess.getSquares().get(key).isEmpty()) {
                                // System.out.println("if not empty return ...");
                                return isMoved;
                            }
                        }

                    }

                } else {
                    System.out.print("se déplacer en bas à droite");
                    if (nb_to_col > nb_from_col) {

                        int pos_depart = nb_from_col + 1;

                        int pos_col = pos_depart;
                        for (int pos_line = from_line - 1; pos_line > to_line; pos_line--) {
                            String key = ((char) (pos_col)) + String.valueOf(pos_line);
                            System.out.println("key : " + key);
                            pos_col++;
                            if (!chess.getSquares().get(key).isEmpty()) {
                                // System.out.println("if not empty return ...");
                                return isMoved;
                            }
                        }

                    } else {
                        System.out.print("se déplacer en bas à gauche");

                        int pos_depart = nb_from_col - 1;
                        int pos_col = pos_depart;
                        for (int pos_line = from_line - 1; pos_line > to_line; pos_line--) {
                            String key = ((char) (pos_col)) + String.valueOf(pos_line);
                            System.out.println("key : " + key);
                            pos_col--;
                            if (!chess.getSquares().get(key).isEmpty()) {
                                // System.out.println("if not empty return ...");
                                return isMoved;
                            }
                        }

                    }

                }

                // déplacer la piece de la case from vers la case to
                chess.getSquares().get(to).setPiece(chess.getSquares().get(from).getPiece());
                chess.getSquares().get(to).setEmpty(false);
                // vider la case from
                chess.getSquares().get(from).setEmpty(true);
                chess.getSquares().get(from).setPiece(null);
                System.out.println("deplacement....");
                isMoved = true;
            }
        }
        if (isMoved) {
            update_list_locked_linear(from, to, chess);
            update_list_locked_diagonal(from, to, chess);
        }

        return isMoved;
    }

    /**
     * @param from
     * @param to
     * @param chess
     * @return boolean
     */
    public static boolean moveKing(String from, String to, Chess chess) {
        // traitement de la query de l'url pour récupérer les lignes et colonnes.
        int from_line = Integer.parseInt(from.substring(1, 2));
        // String from_col = from.substring(0, 1);
        int to_line = Integer.parseInt(to.substring(1, 2));
        // String to_col = to.substring(0, 1);

        int nb_to_col = (int) to.substring(0, 1).charAt(0);
        // System.out.println(nb_to_col);
        int nb_from_col = (int) from.substring(0, 1).charAt(0);
        // System.out.println(nb_from_col);
        Boolean isMoved = false;

        if (Math.abs(to_line - from_line) == 1 || Math.abs(nb_to_col - nb_from_col) == 1) {

            // déplacer la piece de la case from vers la case to
            chess.getSquares().get(to).setPiece(chess.getSquares().get(from).getPiece());
            chess.getSquares().get(to).setEmpty(false);
            // vider la case from
            chess.getSquares().get(from).setEmpty(true);
            chess.getSquares().get(from).setPiece(null);
            System.out.println("deplacement....");
            isMoved = true;

        }
        if (isMoved) {
            // update_list_locked_linear(from, to, chess);
            // update_list_locked_diagonal(from, to, chess);
            update_list_locked_by_king(from, to, chess);
        }

        return isMoved;
    }

    /**
     * @param from
     * @param to
     * @param chess
     * @return void
     */
    public static void update_list_locked_linear(String from, String to, Chess chess) {

        int from_line = Integer.parseInt(from.substring(1, 2));
        String from_col = from.substring(0, 1);
        int to_line = Integer.parseInt(to.substring(1, 2));
        String to_col = to.substring(0, 1);
        int nb_from_col = (int) from.substring(0, 1).charAt(0);
        int nb_to_col = (int) to.substring(0, 1).charAt(0);

        // list de toutes les cases blockées
        List<String> list_locked = new ArrayList<String>();

        // search and add cases up locked
        List<String> list_locked_up = new ArrayList<String>();
        int next_line = to_line + 1;

        String key = to_col + String.valueOf(next_line);
        System.out.print("la cle de la prochaine case: " + key);
        while (liste_cases.contains(key) && chess.getSquares().get(key).isEmpty()) {
            list_locked_up.add(key);

            next_line += 1;
            key = to_col + String.valueOf(next_line);
            System.out.print("la cle de la prochaine case: " + key);

        }
        System.out.println("list locked up for rook" + list_locked_up);
        System.out.println("list locked  for rook" + list_locked);
        list_locked.addAll(list_locked_up);

        String key_to = to_col + String.valueOf(to_line);
        String key_from = from_col + String.valueOf(from_line);

        // search and add cases down locked

        List<String> list_locked_down = new ArrayList<String>();
        next_line = to_line - 1;

        String key_down = to_col + String.valueOf(next_line);
        System.out.print("la cle de la prochaine case: " + key_down);
        while (liste_cases.contains(key_down) && chess.getSquares().get(key_down).isEmpty()) {
            list_locked_down.add(key_down);

            next_line -= 1;
            key_down = to_col + String.valueOf(next_line);
            System.out.print("la cle de la prochaine case: " + key_down);

        }
        System.out.println("list locked down for rook" + list_locked_down);
        System.out.println("list locked for rook" + list_locked);
        list_locked.addAll(list_locked_down);

        // search and add cases left locked

        List<String> list_locked_left = new ArrayList<String>();
        int next_nb_col = nb_to_col - 1;

        String key_left = ((char) next_nb_col) + String.valueOf(to_line);
        // System.out.print("la cle de la prochaine case: " + key_down);
        while (liste_cases.contains(key_left) && chess.getSquares().get(key_left).isEmpty()) {
            list_locked_left.add(key_left);

            next_nb_col -= 1;
            key_left = ((char) next_nb_col) + String.valueOf(to_line);

        }
        System.out.println("list locked down for rook" + list_locked_left);
        System.out.println("list locked for rook" + list_locked);
        list_locked.addAll(list_locked_left);

        // search and add cases right locked
        List<String> list_locked_right = new ArrayList<String>();
        next_nb_col = nb_to_col + 1;

        String key_right = ((char) next_nb_col) + String.valueOf(to_line);
        // System.out.print("la cle de la prochaine case: " + key_down);
        while (liste_cases.contains(key_right) && chess.getSquares().get(key_right).isEmpty()) {
            list_locked_right.add(key_right);

            next_nb_col += 1;
            key_right = ((char) next_nb_col) + String.valueOf(to_line);

        }
        System.out.println("list locked right for rook" + list_locked_right);
        System.out.println("list locked for rook" + list_locked);
        list_locked.addAll(list_locked_right);

        // add list locked = up+down+left+right
        if (chess.getSquares().get(to).getPiece().getColor().equals("WHITE")) {
            chess.getList_locked_black_king().put(key_to, list_locked);
            chess.getList_locked_black_king().remove(key_from);
        } else {
            chess.getList_locked_white_king().put(key_to, list_locked);
            chess.getList_locked_white_king().remove(key_from);
        }

        chess.getSquares().get(key_to).getPiece().getList_locked().clear();
        chess.getSquares().get(key_to).getPiece().getList_locked().addAll(list_locked);

    }

    /**
     * @param from
     * @param to
     * @param chess
     * @return void
     */

    public static void update_list_locked_diagonal(String from, String to, Chess chess) {

        int from_line = Integer.parseInt(from.substring(1, 2));
        String from_col = from.substring(0, 1);
        int to_line = Integer.parseInt(to.substring(1, 2));
        String to_col = to.substring(0, 1);
        int nb_from_col = (int) from.substring(0, 1).charAt(0);
        int nb_to_col = (int) to.substring(0, 1).charAt(0);

        String key_to = to_col + String.valueOf(to_line);
        String key_from = from_col + String.valueOf(from_line);

        // list de toutes les cases blockées
        List<String> list_locked = new ArrayList<String>();

        // search and add cases up right locked
        List<String> list_locked_up_r = new ArrayList<String>();

        int next_line = to_line + 1;
        int next_nb_col = nb_to_col + 1;

        String key_up_r = ((char) next_nb_col) + String.valueOf(next_line);
        System.out.print("la cle de la prochaine case: " + key_up_r);
        while (liste_cases.contains(key_up_r) && chess.getSquares().get(key_up_r).isEmpty()) {
            list_locked_up_r.add(key_up_r);

            next_line += 1;
            next_nb_col += 1;

            key_up_r = ((char) next_nb_col) + String.valueOf(next_line);

            System.out.print("la cle de la prochaine case: " + key_up_r);

        }
        System.out.println("list locked up for rook" + list_locked_up_r);
        System.out.println("list locked  for rook" + list_locked);
        list_locked.addAll(list_locked_up_r);

        // search and add cases up left locked
        List<String> list_locked_up_l = new ArrayList<String>();

        next_line = to_line + 1;
        next_nb_col = nb_to_col - 1;

        String key_up_l = ((char) next_nb_col) + String.valueOf(next_line);
        System.out.print("la cle de la prochaine case: " + key_up_l);
        while (liste_cases.contains(key_up_l) && chess.getSquares().get(key_up_l).isEmpty()) {
            list_locked_up_l.add(key_up_l);

            next_line += 1;
            next_nb_col -= 1;

            key_up_l = ((char) next_nb_col) + String.valueOf(next_line);

            System.out.print("la cle de la prochaine case: " + key_up_l);

        }
        System.out.println("list locked up for rook" + list_locked_up_l);
        System.out.println("list locked  for rook" + list_locked);
        list_locked.addAll(list_locked_up_l);

        // search and add cases down left locked
        List<String> list_locked_down_l = new ArrayList<String>();

        next_line = to_line - 1;
        next_nb_col = nb_to_col - 1;

        String key_down_l = ((char) next_nb_col) + String.valueOf(next_line);
        System.out.print("la cle de la prochaine case: " + key_down_l);
        while (liste_cases.contains(key_down_l) && chess.getSquares().get(key_down_l).isEmpty()) {
            list_locked_down_l.add(key_down_l);

            next_line -= 1;
            next_nb_col -= 1;

            key_down_l = ((char) next_nb_col) + String.valueOf(next_line);

            System.out.print("la cle de la prochaine case: " + key_down_l);

        }
        System.out.println("list locked up for rook" + list_locked_down_l);
        System.out.println("list locked  for rook" + list_locked);
        list_locked.addAll(list_locked_down_l);

        // search and add cases down right locked
        List<String> list_locked_down_r = new ArrayList<String>();

        next_line = to_line - 1;
        next_nb_col = nb_to_col + 1;

        String key_down_r = ((char) next_nb_col) + String.valueOf(next_line);
        System.out.print("la cle de la prochaine case: " + key_down_r);
        while (liste_cases.contains(key_down_r) && chess.getSquares().get(key_down_r).isEmpty()) {
            list_locked_down_r.add(key_down_r);

            next_line -= 1;
            next_nb_col += 1;

            key_down_r = ((char) next_nb_col) + String.valueOf(next_line);

            System.out.print("la cle de la prochaine case: " + key_down_r);

        }
        System.out.println("list locked up for rook" + list_locked_down_r);
        System.out.println("list locked  for rook" + list_locked);
        list_locked.addAll(list_locked_down_r);

        // add list locked = up+down+left+right
        if (chess.getSquares().get(to).getPiece().getColor().equals("WHITE")) {
            chess.getList_locked_black_king().put(key_to, list_locked);
            chess.getList_locked_black_king().remove(key_from);
        } else {
            chess.getList_locked_white_king().put(key_to, list_locked);
            chess.getList_locked_white_king().remove(key_from);
        }

        chess.getSquares().get(key_to).getPiece().getList_locked().clear();
        chess.getSquares().get(key_to).getPiece().getList_locked().addAll(list_locked);
    }

    /**
     * @param from
     * @param to
     * @param chess
     * @return void
     */

    public static void update_list_locked_by_king(String from, String to, Chess chess) {
        // traitement de la query de l'url pour récupérer les lignes et colonnes.
        int from_line = Integer.parseInt(from.substring(1, 2));
        String from_col = from.substring(0, 1);
        int to_line = Integer.parseInt(to.substring(1, 2));
        String to_col = to.substring(0, 1);

        int nb_to_col = (int) to.substring(0, 1).charAt(0);
        // System.out.println(nb_to_col);
        int nb_from_col = (int) from.substring(0, 1).charAt(0);
        // System.out.println(nb_from_col);
        Boolean isMoved = false;

        String key_to = to_col + String.valueOf(to_line);
        String key_from = from_col + String.valueOf(from_line);

        // list de toutes les cases blockées
        List<String> list_locked = new ArrayList<String>();

        int next_line;
        int next_nb_col;
        String key;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                next_line = to_line + i;
                next_nb_col = nb_to_col + j;
                key = ((char) next_nb_col) + String.valueOf(next_line);
                System.out.println(key);
                if (liste_cases.contains(key) && chess.getSquares().get(key).isEmpty()) {
                    list_locked.add(key);
                    System.out.println(list_locked);
                }

            }

        }

        // add list locked = up+down+left+right
        if (chess.getSquares().get(to).getPiece().getColor().equals("WHITE")) {
            chess.getList_locked_black_king().put(key_to, list_locked);
            chess.getList_locked_black_king().remove(key_from);
        } else {
            chess.getList_locked_white_king().put(key_to, list_locked);
            chess.getList_locked_white_king().remove(key_from);
        }

        chess.getSquares().get(key_to).getPiece().getList_locked().clear();
        chess.getSquares().get(key_to).getPiece().getList_locked().addAll(list_locked);
    }

    /**
     * @param from
     * @param to
     * @param chess
     * @return void
     */

    public static void update_list_locked_by_knight(String from, String to, Chess chess) {
        // traitement de la query de l'url pour récupérer les lignes et colonnes.
        int from_line = Integer.parseInt(from.substring(1, 2));
        String from_col = from.substring(0, 1);
        int to_line = Integer.parseInt(to.substring(1, 2));
        String to_col = to.substring(0, 1);

        int nb_to_col = (int) to.substring(0, 1).charAt(0);
        // System.out.println(nb_to_col);
        int nb_from_col = (int) from.substring(0, 1).charAt(0);
        // System.out.println(nb_from_col);

        String key_to = to_col + String.valueOf(to_line);
        String key_from = from_col + String.valueOf(from_line);

        Boolean isMoved = false;

        // liste de cases possibles pour le knight
        List<String> list_cases_knight = new ArrayList<String>();
        List<String> list_locked = new ArrayList<String>();

        int next_nb_col;
        int next_line;

        next_nb_col = nb_to_col + 1;
        next_line = to_line + 2;

        String key1 = ((char) next_nb_col) + String.valueOf(next_line);
        list_cases_knight.add(key1);

        next_nb_col = nb_to_col + 2;
        next_line = to_line + 1;

        String key2 = ((char) next_nb_col) + String.valueOf(next_line);
        list_cases_knight.add(key2);

        // 3eme case
        next_nb_col = nb_to_col - 1;
        next_line = to_line + 2;

        String key3 = ((char) next_nb_col) + String.valueOf(next_line);
        list_cases_knight.add(key3);

        // 4 eme case

        next_nb_col = nb_to_col - 2;
        next_line = to_line + 1;

        String key4 = ((char) next_nb_col) + String.valueOf(next_line);
        list_cases_knight.add(key4);

        // 5eme case

        next_nb_col = nb_to_col - 2;
        next_line = to_line - 1;

        String key5 = ((char) next_nb_col) + String.valueOf(next_line);
        list_cases_knight.add(key5);

        // 6eme case

        next_nb_col = nb_to_col - 1;
        next_line = to_line - 2;

        String key6 = ((char) next_nb_col) + String.valueOf(next_line);
        list_cases_knight.add(key6);

        // 7eme case

        next_nb_col = nb_to_col + 1;
        next_line = to_line - 2;

        String key7 = ((char) next_nb_col) + String.valueOf(next_line);
        list_cases_knight.add(key7);

        // 8eme case

        next_nb_col = nb_to_col +2;
        next_line = to_line -1;

        String key8 = ((char) next_nb_col) + String.valueOf(next_line);
        list_cases_knight.add(key8);
        System.out.println(list_cases_knight);

        for ( String key : list_cases_knight) {
            if (liste_cases.contains(key) && chess.getSquares().get(key).isEmpty()) {
                list_locked.add(key);
            }
            
        }
        System.out.println(list_locked);

        // add list locked = up+down+left+right
        if (chess.getSquares().get(to).getPiece().getColor().equals("WHITE")) {
            chess.getList_locked_black_king().put(key_to, list_locked);
            chess.getList_locked_black_king().remove(key_from);
        } else {
            chess.getList_locked_white_king().put(key_to, list_locked);
            chess.getList_locked_white_king().remove(key_from);
        }

        chess.getSquares().get(key_to).getPiece().getList_locked().clear();
        chess.getSquares().get(key_to).getPiece().getList_locked().addAll(list_locked);

       

    }
}
