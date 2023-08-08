package FallingSnake.Jeux;

import FallingSnake.Affichage.Affichage;
import FallingSnake.Acteurs.Canon;
import FallingSnake.Controle.Input;
import FallingSnake.Controle.PlayerController;
import FallingSnake.cellules.Facing;
import FallingSnake.Acteurs.Serpent;
import FallingSnake.Acteurs.Boulet;

import java.util.ArrayList;

/**
 * Classe gérant le jeu, tous ses composants, dont le serpent, les boulets et le canon.
 */
public class Game {
    private final Affichage affichage;
    private final Plateau plateau;
    private final Serpent serpent;
    private int score;

    private final Canon canon;
    private final ArrayList<Boulet> boulets= new ArrayList<>();
    private final ArrayList<Boulet> bouletsDead=new ArrayList<>();

    /**
     * Constructeur de l'objet Game
     */
    public Game(int LARGEUR, int HAUTEUR,int TAILLETUILE){
        this.score=0;
        Input input = new Input();
        this.plateau=new Plateau(HAUTEUR,LARGEUR,this);
        this.serpent=new Serpent(0,0, Facing.EAST,this);
        this.canon= new Canon(new PlayerController(input),0,this);
        //On rempli le tableau aleatoirement.
        plateau.randomFill();
        affichage=new Affichage(LARGEUR*TAILLETUILE,HAUTEUR*TAILLETUILE, input,this);
    }

    /**
     * Ici on lance la mise a jour du serpent,qui en meme temps actualise les boulets et le canon.
     */
    public void update(){
            updateBoulets();
            serpent.update();
            canon.update();
    }

    /**
     * Fonction utiliser pour actualiser chaque boulet.
     */
    public void updateBoulets(){
        for (Boulet boulet:boulets) {
            if(!bouletsDead.contains(boulet)){
                boulet.update();
            }
        }
    }
    /**
     * On demande ici a l'affichage d'afficher le jeu plus précisément le plateau qu'il contient.
     */
    public void render(){
        affichage.render(this);
    }


    /**
     * @return le Plateau ou le jeu se déroule.
     */
    public Plateau getPlateau() {
        return plateau;
    }

    /**
     * @return le Serpent qui joue dans notre jeu.
     */
    public Serpent getSerpent() {
        return serpent;
    }

    /**
     * @return le Canon qui joue dans notre jeu
     */
    public Canon getCanon() {
        return canon;
    }

    /**
     * @return Renvoie la Liste des boulets actuellement en jeux.
     */
    public ArrayList<Boulet> getBoulets() {
        return boulets;
    }

    /**
     * @return Renvoie l'affichage utilisé par le jeu.
     */
    public Affichage getAffichage() {
        return affichage;
    }

    public void setScore(int value) {
        this.score+=value;
    }

    public int getScore(){
        return this.score;
    }

    public ArrayList<Boulet> getBouletsDead() {
        return bouletsDead;
    }
}
