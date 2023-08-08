package FallingSnake.cellules;

import java.awt.*;

public interface Obstacles {

    /**
     * Lance l'action associée à l'objet
     */
    public void action();

    /**
     * Fonction retournant la couleur de l'objet
     * @return Color couleur de l'objet
     */
    public Color getColor();

}
