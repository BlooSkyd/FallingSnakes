package FallingSnake.cellules;

import FallingSnake.Acteurs.Serpent;

import java.awt.*;

public class Air implements Obstacles{

    private CONTENU content;
    private Serpent serpent;


    /**
     * Constructeur d'une cellule de type Air
     */
    public Air() {
        this.content=(CONTENU.EMPTY);
    }

    public void action() {}

    public Color getColor() {
        return null;
    }
}
