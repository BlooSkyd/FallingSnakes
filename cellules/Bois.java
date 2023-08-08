package FallingSnake.cellules;

import FallingSnake.Acteurs.Serpent;
import FallingSnake.Jeux.Game;

import java.awt.*;

public class Bois implements  Obstacles{
    private CONTENU content;
    private Game game;
    private Serpent serpent;
    public Bois( Game game) {
        this.content=CONTENU.WOOD;
        this.game=game;
        this.serpent=game.getSerpent();
    }
    public void action() {}

    public Color getColor(){
        return new Color(136, 65, 73);
    }
}
