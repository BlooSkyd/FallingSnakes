import java.util.random.RandomGenerator;

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
        init();
        genObstacle(0.1);
    }

    private void init(){
        for(int h = 0; h < this.height-1; h++) {
            for(int w = 0; w < this.width-1; w++) {
                Arena.board[h][w] = new Cell();
                Arena.board[h][w].setContent(Cell.type.EMPTY);
            }
        }
    }

    /**
     * Fonction garantissant la sélection d'une cellule vide
     * La ligne la plus bas n'est pas atteignable afin que le joueur ne soit pas sur un obstacle
     * @return Une cellule de type EMPTY
     */
    private Cell getRdmFreeCell() {
        int h = RandomGenerator.getDefault().nextInt(0,this.height -1);
        int w = RandomGenerator.getDefault().nextInt(0, this.width);
        while(!Arena.board[h][w].isFree()) {
            h = RandomGenerator.getDefault().nextInt(0,this.height -1);
            w = RandomGenerator.getDefault().nextInt(0, this.width);
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
