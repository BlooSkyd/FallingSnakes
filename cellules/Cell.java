package FallingSnake.cellules;

import FallingSnake.Jeux.Game;

/**
 * Classe des cellules du jeu, gérant leur contenu et leurs coordonnées.
 */
public class Cell {

    private CONTENU content;
    private Obstacles obstacle;
    private int x;
    private int y;
    private Facing facing;
    private Game game;


    /**
     * Les cellules sont initialement vides.
     */
    public Cell(){
        this.content= CONTENU.EMPTY;
        this.obstacle=new Air();
    }

    public Cell(Obstacles obs){
        this.content= CONTENU.EMPTY;
        this.obstacle=obs;
    }

    /**
     * @return renvoie le contenu de la cellule.
     */
    public CONTENU getContent() {
        return content;
    }

    public Obstacles getObstacle() {
        return obstacle;
    }

    /**
     * @param content Définit le contenu de la cellule.
     */
    public void setContent(CONTENU content){
        this.content=content;
    }

    public void setObstacle(Obstacles obs){
        this.obstacle=obs;
    }

    /**
     * @return Renvoie la coordonnée x de la cellule.
     */
    public int getX() {
        return x;
    }

    /**
     * @param x Définit la valeur x de la cellule.
     */
    public void setX(int x) {
        this.x = x;
    }
    /**
     * @return Renvoi la coordonnée y de la cellule.
     */
    public int getY() {
        return y;
    }
    /**
     * @param y Définit la valeur y de la cellule.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return Renvoi l'orientation de la cellule (utile que pour le serpent).
     */
    public Facing getFacing() {
        return facing;
    }

    /**
     * @param facing permet de changer l'orientation de la cellule (utile que pour le serpent).
     */
    public void setFacing(Facing facing) {
        this.facing = facing;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
