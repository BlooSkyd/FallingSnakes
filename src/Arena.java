import java.lang.Math;
import java.util.ArrayList;

public class Arena {
    private final int height;
    private final int width;
    private static Cell[][] board;
    private static ArrayList<Cell> emptyCells;

    // Paramètres globaux
    // DEBUG Les pourcentages sont en réalité des proportions et peuvent dépasser un total de 1
    private final float percentSTRAWBERRY = 0.15f;
    private final float percentBLACKBERRY = 0.15f;
    private final float percentCOIN = 0.10f;
    private final float percentWOOD = 0.60f;

    /**
     * @return height value
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return width value
     */
    public int getWidth() {
        return width;
    }

    /**
     * Constructeur par défaut pour Arena, représentant le plateau de jeu
     * @param height hauteur du plateau
     * @param width largeur du plateau
     */
    public Arena(int height, int width) {
        this.height = height;
        this.width = width;
        emptyCells = new ArrayList<Cell>();
        board = new Cell[height][width];
        boardInit();
        genObstacle(0.25);//(float) (height*width/2000));
    }

    /**
     * Fonction initialisant les cellules de board
     */
    private void boardInit(){
        for(int h = 0; h < this.height; h++) {
            for(int w = 0; w < this.width; w++) {
                board[h][w] = new Cell(h, w);
                if(h!=this.height-1) {emptyCells.add(board[h][w]);}
            }
        }
        board[this.height-1][Math.round(this.width/2f)].setContent(Cell.type.PLAYER);
    }

    /**
     * Fonction garantissant la sélection d'une cellule vide
     * La ligne la plus bas n'est pas atteignable afin que le joueur ne soit pas sur un obstacle
     * @return Une cellule de type EMPTY
     */
    private Cell getRdmFreeCell() {
        Cell c = emptyCells.remove( (int) (Math.random()*emptyCells.size()) );
        // Possibilité de faire un mélange de liste via Collections.shuffle(Arena.emptyCells)
        return board[c.getX()][c.getY()];
    }

    /**
     * Fonction générant un type de cellule aléatoire en fonction des probabilité d'appararition de chaque type
     * NE GÉNÈRE PAS DE Cell.type.WOOD
     * @return Cell.type a générer
     */
    private Cell.type generatedType() { // WARN possiblement à supprimer dans le futur
        float d = (float) Math.random();
        Cell.type t;
        float totalPercentage = percentSTRAWBERRY + percentBLACKBERRY + percentCOIN + percentWOOD;
        if (d < percentSTRAWBERRY/(totalPercentage -percentWOOD))  { // n% STRAWBERRY
            t = Cell.type.STRAWBERRY;
        } else if ( d < percentBLACKBERRY/(totalPercentage -percentWOOD) +(percentSTRAWBERRY/(totalPercentage -percentWOOD)) ) { // n% BLACKBERRY
            t = Cell.type.BLACKBERRY;
        } else { // if (d < percentCOIN/(1-percentWOOD)) { // n% COIN
            t = Cell.type.COIN;
        }
        // DEBUG if(t == Cell.type.WOOD) {System.out.println("\u001B[41m\u001B[30mCellule de type WOOD générée\u001B[0m");}
        return t;
    }

    /**
     * Fonction vérifiant que les voisins en diagonale d'une cellule ne sont pas du bois avant d'y placer une cellule de type WOOD
     * On identifie 6 cas possibles : (0,0) (n,0) (max,0) (0,m) (n,m) (n,max)
     * Les cas collés au bord inférieur sont inaténiables par sécurité dans le programme
     */
    private void generateWood() { // Sujet à problème d'index, mais semble bon
        boolean isOk = false;
        while(!isOk) {
            Cell c = getRdmFreeCell(); // WARN Penser à remettre la cellule dans la liste des cellules vides si elle n'est pas utilisée
            int cx = c.getX();
            int cy = c.getY();
            //System.out.println("cx: "+cx+", cy: "+cy);
            // On commence par vérifier dans quel cas on se situe
            if(cx==0 && cy==0) { // Angle supérieur gauche OK
                if (board[cx+1][cy+1].isNotWood()) {c.setContent(Cell.type.WOOD); }
            } else if ( (cx > 0 && cx < width-1) && cy==0) { // Bord supérieur OK
                if (board[cx-1][cy+1].isNotWood() && board[cx+1][cy+1].isNotWood()) {c.setContent(Cell.type.WOOD); }
            } else if (cx==width-1 && cy==0) { // Angle supérieur droit OK
                if (board[cx-1][cy+1].isNotWood()) {c.setContent(Cell.type.WOOD); }
            } else if (cx==0 && (cy > 0 && cy < height-1) ) { // Bord gauche OK
                if (board[cx+1][cy-1].isNotWood() && board[cx+1][cy+1].isNotWood()) {c.setContent(Cell.type.WOOD); }
            } else if (cx==width-1 && (cy>0 && cy<height-1) ) { // Bord droit OK
                if (board[cx-1][cy+1].isNotWood() && board[cx-1][cy-1].isNotWood()) {c.setContent(Cell.type.WOOD); }
            } else if ( (cx>0 && cx<width-1) && cy<height-1) { // Coeur OK
                if (board[cx+1][cy-1].isNotWood() && board[cx+1][cy+1].isNotWood() && board[cx-1][cy+1].isNotWood() && board[cx-1][cy-1].isNotWood()) {c.setContent(Cell.type.WOOD); }
            }
            if (!c.isNotWood()) {isOk = true;}
            else {emptyCells.add(c);} // Si la case en question ne change pas de type (et donc qu'elle ne correspond pas aux critères), on la remet dans la liste emptyCells
        }
    }

    /**
     * Génère et place une série d'obstacle dans le plateau.
     * Commence systématiquement par la génération du bois pour optimiser ses possibilités de placement
     * @param percent doit être compris entre 0 et 1, pourcentage d'obstacles présents
     */
    private void genObstacle(double percent) {
        int nbObst = (int) (this.height * this.width * percent);
        int nbWood = (int) (percentWOOD*nbObst);

        while(nbWood > 0) {
            generateWood();
            nbWood--;
            nbObst--;
        }

        while(nbObst > 0) {
            Cell.type t = generatedType();
            Cell c = getRdmFreeCell();
            c.setContent(t);
            nbObst--;
        }
    }

    /**
     * Fonction d'affichage du plateau de jeu
     * @param arena Le plateau de jeu utilisé pourla partie
     */
    public static void printScreen(Arena arena) {
        for(int i = 0; i< arena.width +2; i++) {System.out.print("\u001B[37m#\u001B[37m");}
        System.out.println();
        for(int h = 0; h< arena.height; h++) {
            System.out.print("\u001B[37m#\u001B[37m");
            for(int w = 0; w< arena.width; w++) {
                System.out.print(Arena.board[h][w].draw());
            }
            System.out.println("\u001B[37m#\u001B[37m");
        }
        for(int i = 0; i< arena.width +2; i++) {System.out.print("#");}
        System.out.println();
    }

    /**
     * Fonction debug
     * @param arena L'arene de la partie
     */
    public static void printBoard(Arena arena) {
        for(int h=0; h<arena.height; h++) {
            for(int w=0; w<arena.width; w++) {
                System.out.print(Arena.board[h][w].toString());
            }
            System.out.println();
        }
    }

}
