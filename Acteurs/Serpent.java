package FallingSnake.Acteurs;
import FallingSnake.cellules.*;
import FallingSnake.Jeux.Game;

import java.awt.*;
import java.util.ArrayList;

import static FallingSnake.cellules.CONTENU.*;
import static FallingSnake.cellules.Facing.EAST;
import static FallingSnake.cellules.Facing.WEST;
/**
 * Classe gérant le serpent, principal sujet de ce projet.
 * Ici on déclare ses couleurs, ses actions ses déplacements.
 */
public class Serpent{
    private Facing facing;
    private Game game;
    private Boolean snakeDead = false;
    private Boolean playerDead =false;
    private Cell[][] board;
    private boolean isSuper;
    private ArrayList<Cell> queue = new ArrayList<>();
    private final Cell tete;
    private ArrayList<Cell> listObstacles;
    private Color couleur;
    private final Color STANDARD = new Color(213,80,133);
    private final Color SPECIAL = new Color(247, 143, 186);
    private int tempsInvincible =0;


    /** Constructeur de l'objet Serpent
     * @param xpos int position horizontale de départ du serpent (spawn)
     * @param ypos int position verticale de départ du serpent (spawn)
     * @param facing Facing orientation du serpent
     * @param game Game variable du jeu
     */
    public Serpent(int xpos, int ypos, Facing facing, Game game) {
        this.game = game;
        this.facing = facing;
        this.board = game.getPlateau().getBoard();
        game.getPlateau().getBoard()[ypos][xpos].setContent(SNAKE);
        queue.add(game.getPlateau().getBoard()[ypos][xpos]);
        tete = queue.get(0);
        tete.setFacing(this.facing);
        listObstacles = game.getPlateau().getObstacles();
        this.couleur = STANDARD;
    }

    /**
     * Change simplement la ou "regarde" le serpent.
     */
    public void switchFacing() {
        switch (this.facing) {
            case WEST -> this.facing = EAST;
            case EAST -> this.facing = WEST;
        }
    }

    /**
     * Fonction qui permet a la queue du serpent de suivre la tete.
     */
    public void follow() {
        for (int i = queue.size()-1; i >0; i--) {
            if (i == queue.size() - 1){
                board[queue.get(i).getY()][queue.get(i).getX()].setContent(EMPTY);
                board[queue.get(i).getY()][queue.get(i).getX()].setObstacle(new Air());
            }
            queue.get(i).setY(queue.get(i - 1).getY());
            queue.get(i).setX(queue.get(i - 1).getX());
            board[queue.get(i).getY()][queue.get(i).getX()].setContent(CONTENU.SNAKE);
        }
    }

    /**
     * Augmente la taille du serpent
     */
    public void augmenterTaille() {
        Cell cell = new Cell();
        cell.setY(tete.getY());
        cell.setX(tete.getX());
        cell.setContent(CONTENU.SNAKE);
        queue.add(cell);
    }

    /**
     * Fonction qui fait l'inverse de AugmenterTaille, qui réduit la taille du serpent.
     * On récupère la dernière cellule du serpent, modifie son contenu et on la retire de la liste représentant la queue du serpent.
     */
    public void killQueue(){
        if(!this.isSuper) {
            Cell q = queue.get(queue.size() - 1);
            q.setContent(EMPTY);
            q.setObstacle(new Air());
            board[q.getY()][q.getX()].setContent(EMPTY);
            board[q.getY()][q.getX()].setObstacle(new Air());
            queue.remove(q);
        }
    }

    /**
     * Fonction qui vérifie si le serpent est mort, si oui il agit en consequence
     */
    public void verifDead(){
        if(queue.size()==0){
            board[tete.getY()][tete.getX()].setContent(EMPTY);
            board[tete.getY()][tete.getX()].setObstacle(new Air());
            snakeDead = true;
        }
    }

    /**
     * Fonction qui si le serpent n'est constitué que de sa tete nettoie derriere elle.
     */
    public void cleanBehindHead(){
        switch (this.facing){
            case EAST :
                if(queue.size()==1){
                    game.getPlateau().getBoard()[tete.getY()][tete.getX()-1].setContent(EMPTY);
                }
                break;
            case WEST:
                if(queue.size()==1){
                    game.getPlateau().getBoard()[tete.getY()][tete.getX()+1].setContent(EMPTY);
                }
                break;
        }
    }

    /**
     * Fonction qui si le serpent n'est constitué que de sa tete nettoie au-dessus d'elle.
     */
    public void cleanAboveHead(){
        if(queue.size()==1) {
            board[tete.getY() - 1][tete.getX()].setContent(EMPTY);
        }
    }

    /**
     * @return  Boolean - true s'il y avait du bois et que l'on a agi en consequence.
     *                  - false sinon.
     */
    public boolean verifBois(){
        switch (this.facing){
            case EAST -> {
                if (board[tete.getY()][tete.getX()+1].getContent() == WOOD) {
                    descendre();
                    cleanAboveHead();
                    return true;
                }
            }
            case WEST-> {
                if (board[tete.getY()][tete.getX()-1].getContent() == WOOD) {
                    descendre();
                    cleanAboveHead();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param f Facing direction dans laquelle se deplacer. La fonction agit et verifie selon ca.
     */
    public void move(Facing f){
        switch (f) {
            case EAST:
                if(verifBois()){ break; }
                tete.setX(tete.getX() + 1);
                board[tete.getY()][tete.getX()].setContent(CONTENU.SNAKE);
                cleanBehindHead();
                break;

            case WEST :
                if(verifBois()){ break; }
                tete.setX(tete.getX() - 1);
                board[tete.getY()][tete.getX()].setContent(CONTENU.SNAKE);
                cleanBehindHead();
                break;
        }
    }

    /**
     * Fonction qui lance le fait que le serpent est invincible.
     */
    public void toggleSuper(){
        this.isSuper =true;
    }

    /**
     * Fonction qui gere le deplacement vertical du serpent.
     */
    public void descendre(){
        if( board[tete.getY()+1][tete.getX() ].getContent() != WOOD ){
            board[tete.getY()+1][tete.getX()].getObstacle().action();
        }
        board[tete.getY()+1][tete.getX()].setContent(CONTENU.SNAKE);
        tete.setY(tete.getY()+1);
        switchFacing();
        cleanAboveHead();
    }

    /**
     * Fonction qui resoud l'action de l'obstacle selon la direction du serpent.
     */
    public void resolve(){
        verifCote();
        switch(this.facing){
            case EAST :
                if( board[tete.getY()][tete.getX()+1].getContent() != WOOD ){
                    board[tete.getY()][tete.getX()+1].getObstacle().action();
                    break;
                }
                break;

            case WEST:
                if (board[tete.getY()][tete.getX()-1].getContent() != WOOD) {
                    board[tete.getY()][tete.getX()-1].getObstacle().action();
                    break;
                }
                break;
        }
    }

    /**
     * Fonction qui verifie si le serpent a atteind la limite droite ou gauche,si c'est le cas
     * il agit en consequence en descendant.
     *
     * Nous avons aussi le cas de la derniere ligne,qui si le serpent l'atteint,et ne recontre pas le canon avant un mur.
     * Se retournera pour aller trouver le canon.
     */
    public void verifCote(){
        switch(this.facing) {
            case EAST -> {
                if (tete.getY() == game.getPlateau().getHeight() - 1 && tete.getX() == game.getPlateau().getWidth() - 1) {
                    switchFacing();
                    break;
                }
                if (tete.getX() == game.getPlateau().getWidth() - 1) {
                    descendre();
                    follow();
                }
            }
            case WEST -> {
                if (tete.getY() == game.getPlateau().getHeight() - 1 && tete.getX() == 0) {
                    switchFacing();
                    break;
                }
                if (tete.getX() == 0) {
                    descendre();
                    follow();
                }
            }
        }
    }

    /**
     * Fonction qui met a jour le serpent,lancant ses comportements.
     */
    public void update(){
        verifDead();
        follow();
        resolve();
        move(this.facing);
        tempsInvincible++;
        gestionInvincible();
    }

    /**
     * Fonction qui gere les fonctionnalitées de l'invincibilitée du serpent.
     */
    public void gestionInvincible(){
        if(!this.isSuper) {
            this.couleur = STANDARD;
        }
        if((this.isSuper)&&(tempsInvincible <=7)) {
            couleur = SPECIAL;
        }
        if ((this.isSuper)&& (tempsInvincible >7)) {
            couleur = STANDARD;
            this.isSuper = false;
            tempsInvincible = 0;
        }
    }

    /**
     * Dans notre projet nous avons pris la decision de considerer que l'invincibilite du serpent se cumulais.
     * Ce qui est un risque pour le joueur,le forcant donc a eliminer le plus de myrtille possible.
     */
    public void resetTempsInvicible(){
        if(tempsInvincible !=0){
            tempsInvincible =0;
        }
    }

    /**
     * @return Renvoi la couleur actuelle du serpent
     */
    public Color getCouleur() {
        return couleur;
    }

    /** Cette fonction supprime un obstacle de la liste des obstacles,si la cellule n'est pas un obstacle,il ne se passe rien. <br>
     * @param cc Cellule dont on veux savoir si elle est dans la liste des obstacles.
     *
     */
    public void supprObs(Cell cc){
        if(game.getPlateau().getObstacles().indexOf(cc)>0){
            listObstacles.remove(listObstacles.indexOf(board[tete.getY()][tete.getX()]));;
        }
    }

    /**
     * @return Renvoi le corp du serpent
     */
    public ArrayList<Cell> getCorp() {
        return queue;
    }

    /**
     * @return Boolean true - si le serpent est invincible. <br>
     *                 false - sinon.
     */
    public boolean isSuper() {
        return isSuper;
    }

    /**
     * @return Boolean true - si le serpent est mort, C.A.D si il a ete complétement détruit.<br>
     *                 false - sinon.
     */
    public Boolean getSnakeDead() {
        return snakeDead;
    }
    /**
     * @return true - si le joueur est mort, C.A.D si le canon a ete toucher par le serpent.<br>
     * false - sinon.
     */
    public Boolean getPlayerDead() {
        return playerDead;
    }

    /**
     * @return Renvoi la tete du serpent
     */
    public Cell getTete() {
        return tete;
    }
}