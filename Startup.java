package FallingSnake;

import FallingSnake.Jeux.Game;
import FallingSnake.Jeux.GameLoop;

/**
 * Fonction qui lance l'application.
 */
public class Startup {
    public static final int TAILLETUILE =25;
    public static final int HAUTEUR =20;
    public static final int LARGEUR =20;


    /**
     * Fonction qui lance tout le jeu, creant un plateau avant de creer un jeux avec ce dit plateau.
     * et de lancer la Gameloop gerant le raffraichissement du rendu et des mises a jours.
     */
    public static void main(String[] args) {
        Game game=new Game(LARGEUR, HAUTEUR, TAILLETUILE);
        GameLoop gameloop=new GameLoop(game);
        new Thread(gameloop).start();
    }
}
