package com.echecs.api.shared;

import com.echecs.api.model.Chess;
import com.echecs.api.model.ChessBoard;
import com.echecs.api.model.Session;
import com.echecs.api.model.Square;

import org.bson.json.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
        // JsonObject response = new JsonObject(new String("{'message':'ces pieces ne
        // vous appartiennent pas ! '}"));
        int idBlackPlayer = session.getIdBlackPlayer();
        int idWhitePlayer = session.getIdWhitePlayer();
        // System.out.println(from);
        // System.out.println(chess.getSquares().get(from).getPiece().getColor());

        // piece is present in case from
        if (chess.getSquares().get(from).isEmpty()) {
            return new JsonObject(new String("{'aucune pièce à déplacer, la case depart est vide! '}"));
        }

        // System.out.println("destination "+to);
        // System.out.println("chessboard positions "+ChessBoard.positions);
        // System.out.println("liste cases "+liste_cases);
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
                    : (new JsonObject(new String("{'message':'deplacement du pion refuse'}")));
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
        // System.out.println(" fc "+from_col+" tc "+to_col);
        Boolean isMoved = false;

        if (from_col.equals(to_col)) {
            // deplacement de 1 ou 2 cases seulement
            if ((to_line - from_line) == 1 || (to_line - from_line) == 2) {
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
            } else if ((to_line - from_line) == -1 || (to_line - from_line) == -2) {
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

            } else {
                isMoved = false;
            }
        } else {

            if (chess.getSquares().get(to).isEmpty()) {
                isMoved = false;
            } else {// *si ligne_to=lign_from+ 1 ET col_to=col_from.nextLetter
                    // System.out.println(to_col.charAt(0)+" ?= "+from_col.charAt(0)+1);

                if ((to_line == from_line + 1) && ((to_col.charAt(0) == from_col.charAt(0) + 1)
                        || (to_col.charAt(0) == from_col.charAt(0) - 1))) {
                    chess.getSquares().replace(to, chess.getSquares().get(from));
                    chess.getSquares().get(from).setEmpty(true);
                    isMoved = true;
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

                    // System.out.println("pos col" + pos_col);
                    // System.out.println("mon lettre from" + (char) pos_col);
                    // System.out.println("nb deplacement " + nb_col_deplacement);

                    String key = ((char) (pos_col)) + String.valueOf(from_line);
                    // System.out.println("key : " + key);

                    if (!chess.getSquares().get(key).isEmpty()) {
                        // System.out.println("if not empty return ...");
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
            return false;

        }

        return isMoved;
    }

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
            return isMoved;
        }

        return isMoved;
    }

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
            return isMoved;
        }

        return isMoved;
    }

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

        return isMoved;
    }

    public static boolean moveKing(String from, String to, Chess chess) {
        // traitement de la query de l'url pour récupérer les lignes et colonnes.
        int from_line = Integer.parseInt(from.substring(1, 2));
        //String from_col = from.substring(0, 1);
        int to_line = Integer.parseInt(to.substring(1, 2));
        //String to_col = to.substring(0, 1);

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

        return isMoved;
    }

}
