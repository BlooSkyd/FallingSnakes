import java.lang.Math;

public class Arena {
    private final int height;
    private final int width;
    private static Cell[][] board;

    /**
     * Constructeur par défaut pour Arena, représentant le plateau de jeu
     * @param height hauteur du plateau
     * @param width largeur du plateau
     */
    public Arena(int height, int width) {
        this.height = height;
        this.width = width;
        board = new Cell[height][width];
        boardInit();
        genObstacle(0.1);
    }

    /**
     * Fonction initialisant les cellules de board
     */
    private void boardInit(){
        for(int h = 0; h < this.height; h++) {
            for(int w = 0; w < this.width; w++) {
                Arena.board[h][w] = new Cell();
            }
        }
    }

    /**
     * Fonction renvoyant une position aléatoire dans l'intervale [min;max[
     * @param min borne inférieure inclue de l'intervale
     * @param max borne supérieur exclue de l'intervale
     * @return valeur "aléatoire" comprise entre min et max
     */
    private int randomPos(int min, int max) {
        return (int)(Math.random()*(max-min+1)+min);
    }

    /**
     * Fonction garantissant la sélection d'une cellule vide
     * La ligne la plus bas n'est pas atteignable afin que le joueur ne soit pas sur un obstacle
     * @return Une cellule de type EMPTY
     */
    private Cell getRdmFreeCell() {
        int h = randomPos(0,this.height-2);
        int w = randomPos(0, this.width-1);
        while(!Arena.board[h][w].isFree()) {
            h = randomPos(0,this.height-2);
            w = randomPos(0, this.width-1);
        }
        return Arena.board[h][w];
    }

    /**
     * Génère et place une série d'obstacle dans le plateau.
     * @param percent doit être compris entre 0 et 1
     */
    private void genObstacle(double percent) {
        int nbObst = (int) (this.height * this.width * percent);
        while(nbObst > 0) {
            Cell c = getRdmFreeCell();
            c.setContent(Cell.type.WOOD);
            nbObst--;
        }
    }

    /**
     * Fonction d'affichage du plateau de jeu
     * @param arena Le plateau de jeu utilisé pourla partie
     */
    public static void printScreen(Arena arena) {
        for(int i = 0; i< arena.width +2; i++) {System.out.print("#");}
        System.out.println();
        for(int h = 0; h< arena.height; h++) {
            System.out.print("#");
            for(int w = 0; w< arena.width; w++) {
                System.out.print(Arena.board[h][w].draw());
            }
            System.out.println("#");
        }
        for(int i = 0; i< arena.width +2; i++) {System.out.print("#");}
    }


}
