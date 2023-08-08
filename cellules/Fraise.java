package FallingSnake.cellules;

import FallingSnake.Jeux.Game;
import java.awt.*;

public class Fraise implements Obstacles{

    private CONTENU content;
    private Game game;

    /**
     * Constructeur de l'obstacle de type Fraise
     * @param game le jeu en cours
     */
    public Fraise( Game game) {
        this.content=CONTENU.STRAWBERRY;
        this.game=game;
    }

    /**
     * Fonction qui fait grandir le serpent et fait perdre un point de score
     */
    public void action() {
        game.getSerpent().supprObs(game.getPlateau().getBoard()[game.getSerpent().getTete().getY()][game.getSerpent().getTete().getX()]);
        game.getSerpent().augmenterTaille();
        game.setScore(-1);
    }

    public Color getColor(){
        return new Color(208, 35, 47);
    }
}
