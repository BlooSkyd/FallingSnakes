package FallingSnake.cellules;

import FallingSnake.Acteurs.Serpent;
import FallingSnake.Jeux.Game;

import java.awt.*;

public class Myrtille implements  Obstacles{

    private CONTENU content;
    private Game game;
    private Serpent serpent;

    /**
     * Constructeur de l'objet Myrtille
     * @param game jeu de la partie en cours
     */
    public Myrtille( Game game) {
        this.content = (CONTENU.BLACKBERRY);
        this.game = (game);
        this.serpent=game.getSerpent();
    }

    /**
     * Fonction qui rend le serpent invincible et fait perdre un de score
     */
    public void action() {
        serpent.supprObs(game.getPlateau().getBoard()[serpent.getTete().getY()][serpent.getTete().getX()]);
        serpent.toggleSuper();
        game.setScore(-1);
        serpent.resetTempsInvicible();
    }

    public Color getColor() {
        return new Color(16, 42, 93);
    }
}
