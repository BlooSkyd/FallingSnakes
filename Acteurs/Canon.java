package FallingSnake.Acteurs;

import FallingSnake.Controle.Controller;
import FallingSnake.cellules.Air;
import FallingSnake.cellules.CONTENU;
import FallingSnake.Jeux.Game;
import FallingSnake.cellules.Obstacles;

import java.awt.*;

/**
 * Classe gérant le canon, donc le joueur.
 * Ses déplacements ansi que les tirs sont gérés ici.
 */
public class Canon implements Obstacles {
    private int x;
    private final Game game;
    private final Controller controller;
    private final int y;
    private boolean dead=false;

    /**
     * @param controller Un objet de classe Controller, ici on donnera un PlayerController (implémentant Controller)
     *                   il sert à récupérer les entrées clavier et à les transformer en état.
     *
     * @param x Notre canon ne se déplaçant jamais en hauteur, seul sont x sera modifier, on préférera le placer au milieu mais ce n'est pas une obligation.
     *
     * @param game un Objet de classe Game, hébergeant le jeu ainsi que le plateau ou évoluera notre canon.
     */
    public Canon(Controller controller,int x,Game game) {
        this.x=x;
        this.controller=controller;
        this.game=game;
        this.y=game.getPlateau().getHeight()-1;
        game.getPlateau().getBoard()[y][x].setContent(CONTENU.PLAYER);
        game.getPlateau().getBoard()[y][x].setObstacle(this);
    }

    /**
     * Fonction qui va mettre à jour notre Canon, selon les états renvoyés par notre PlayerController.
     * Si l'état est "on veut aller à gauche" on se déplacera vers la gauche, etc.
     */
    public boolean checkMove(){
        if(controller.isRequestingLeft() && this.x>0 && !controller.isRequestingRight() && checkSnake()){
            return true;
        }
        return controller.isRequestingRight() && (this.x < game.getPlateau().getWidth()-1) && !controller.isRequestingLeft() && checkSnake();
    }

    /**
     * @return Boolean false - si le serpent est sur les coter du canon et que l'on ne peut pas bouger vers lui, car il prend deja la place.
     *                 true - s'il n'y est pas et que l'on peut bouger
     */
    public boolean checkSnake(){
        if(this.x>0 && this.x<game.getPlateau().getWidth()-1){
            return game.getPlateau().getBoard()[this.y][this.x + 1].getContent() != CONTENU.SNAKE || game.getPlateau().getBoard()[this.y][this.x + 1].getContent() != CONTENU.SNAKE;
        }
        return true;
    }

    /**
     * Fonction qui gere le mouvement du canon.
     */
    public void move(){
        if(controller.isRequestingLeft()){
            game.getPlateau().getBoard()[y][x].setContent(CONTENU.EMPTY);
            game.getPlateau().getBoard()[y][x].setObstacle(new Air());
            game.getPlateau().getBoard()[y][x-1].setObstacle(this);
            game.getPlateau().getBoard()[y][x - 1].setContent(CONTENU.PLAYER);
            this.x--;
        } else { // = isRequestingRight()
            game.getPlateau().getBoard()[y][x].setContent(CONTENU.EMPTY);
            game.getPlateau().getBoard()[y][x].setObstacle(new Air());
            game.getPlateau().getBoard()[y][x+1].setObstacle(this);
            game.getPlateau().getBoard()[y][x + 1].setContent(CONTENU.PLAYER);
            this.x++;
        }
    }

    /**
     * Fonction qui gere la creation des boulets et leurs apparitions.
     */
    public void createBoulet(){
        game.getPlateau().getBoard()[y-1][x].setContent(CONTENU.BOULET);
        game.getPlateau().getBoard()[y-1][x].setObstacle(new Boulet(y-1,x,game));
        game.getBoulets().add( new Boulet(y-1,x,game));
    }
    /**
     * Fonction qui vérifie s'il n'y a pas une partie du serpent là où on va tirer.
     */
    public void checkAbove(){
        if(game.getPlateau().getBoard()[y-1][x].getContent()==CONTENU.SNAKE){
            game.getSerpent().killQueue();
        } else if (game.getPlateau().getBoard()[y-1][x].getContent() != CONTENU.EMPTY) {
            game.getPlateau().getObstacles().remove(game.getPlateau().getBoard()[y-1][x]);
        }
    }

    /**
     * Fonction qui vérifie si on demande à tirer.
     */
    public void checkShoot(){
        if(controller.isRequestingSpace()){
            checkAbove();
            createBoulet();
        }
    }

    /**
     * Fonction qui met à jour le canon, gérant si on peut se déplacer, si oui on se déplace et on peut tirer dans tous les cas.
     */
    public void update(){
        if(checkMove()){ move(); }
        checkShoot();
    }

    /**
     * @return true si le Canon est mort A.K.A si le serpent l'a toucher.
     */
    public boolean isDead() { return dead; }

    /**
     * @param dead change l'état de vie ou de mort du canon.
     */
    public void setDead(boolean dead) { this.dead = dead; }

    /**
     * Tue le joueur/canon lorsque appelé
     */
    public void action() { setDead(true); }

    public Color getColor() { return new Color(30,30,30); }
}
