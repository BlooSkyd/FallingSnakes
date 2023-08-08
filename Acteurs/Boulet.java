package FallingSnake.Acteurs;
import FallingSnake.Jeux.Game;
import FallingSnake.cellules.Air;
import FallingSnake.cellules.CONTENU;
import FallingSnake.cellules.Cell;
import FallingSnake.cellules.Obstacles;

import java.awt.*;

import static FallingSnake.cellules.CONTENU.*;

/**
 * Classe gérant les boulets de canon, leur déplacement et leur reaction aux obstacles.
 */
public class Boulet implements Obstacles {
    private int y;
    private final int x;
    private final Game game;
    private final Cell[][] board;
    private boolean isTouched =false;
    /**
     * Constructeur des obstacles de type Boulet
     * @param y la coordonnée y d'origine.
     * @param x la coordonnée x d'origine
     * @param game le jeu où l'on va ajouter notre boulet.
     */
    public Boulet(int y,int x,Game game){
        this.y=y;
        this.x=x;
        this.game=game;
        this.board=game.getPlateau().getBoard();
    }

    /**
     * Fonction qui déplace le boulet vers le haut lors de l'appel d'update()
     * Modifie le tableau en conséquence
     */
    public void moveUp(){
        game.getPlateau().getBoard()[y][x].setObstacle(new Air());
        board[y][x].setContent(CONTENU.EMPTY);
        this.y--;
        game.getPlateau().getBoard()[y][x].setObstacle(this);
        board[y][x].setContent(CONTENU.BOULET);
    }

    /**
     * Fonction supprimant le corps du serpent s'il n'est pas de taille nulle
     * Càd s'il n'est pas mort
     */
    public void deleteSerpent(){
        if(game.getSerpent().getCorp().size()!=0) {
            game.getSerpent().killQueue();
            if(!game.getSerpent().isSuper()){
                game.setScore(1);
            }
        }
    }

    /**
     * Fonction assurant la suppression du boulet lorsqu'il arrive tout en haut du tableau
     */
    public void cleanBoulet(){
        if(this.y==0){
            game.getBouletsDead().add(this);
            board[y][x].setContent(CONTENU.EMPTY);
            board[y][x].setObstacle(new Air());
        }
    }

    /**
     * Fonction s'occupant de mettre à jour le boulet en vérifiant ses états
     */
    public void update() {
        cleanBoulet();
        if (!isTouched){
            if (this.y>0){
                if (board[y-1][x].getContent()!=EMPTY){
                    if (board[y-1][x].getContent()==SNAKE){
                        deleteSerpent();
                    }
                    isTouched =true;
                }
                moveUp();
            }
        } else { removeBoulet(); }
    }

    /**
     * Fonction supprimant le boulet lorsque le boulet à rencontrer une cellule non-vide
     */
    public void removeBoulet(){
        game.getPlateau().getObstacles().remove(game.getPlateau().getBoard()[this.y][this.x]);
        game.getBouletsDead().add(this);
        game.getPlateau().getBoard()[this.y][this.x].setContent(EMPTY);
        game.getPlateau().getBoard()[this.y][this.x].setObstacle(new Air());
    }

    /**
     * Fonction demandée par l'interface mais non nécessaire ici.
     *  L'utilisation de l'interface Obstacles permet de simplifier les démarches
     */
    public void action() {
    }

    public Color getColor() {
        return new Color(80,80,80);
    }
}