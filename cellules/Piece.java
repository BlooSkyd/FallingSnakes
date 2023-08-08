package FallingSnake.cellules;

import FallingSnake.Acteurs.Serpent;
import FallingSnake.Jeux.Game;

import java.awt.*;

public class Piece  implements Obstacles{
    private CONTENU content;
    private Game game;
    private Serpent serpent;


    /**
     * Constructeur de l'obstacle de type Piece
     * @param game le jeu en cours
     */
    public Piece(Game game) {
        this.content=(CONTENU.COIN);
        this.game=(game);
        this.serpent=game.getSerpent();
    }

    /**
     * Fonction qui melange le tableau quand elle est activer.
     */
    public void action() {
        serpent.supprObs(game.getPlateau().getBoard()[serpent.getTete().getY()][serpent.getTete().getX()]);
        game.getPlateau().shuffleObs();
    }

    public Color getColor() {
        return new Color(253, 222, 6);
    }
}
