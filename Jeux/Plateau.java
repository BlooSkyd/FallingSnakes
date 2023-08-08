package FallingSnake.Jeux;

import FallingSnake.cellules.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Classe gérant le plateau de notre jeu, stockant les cellules et leur contenu dans un tableau a deux dimensions.
 */
public class Plateau {
    //champs
    private final int height;
    private final int width;
    private final Cell[][] board;
    private final ArrayList<Cell> obstacles = new ArrayList<Cell>();
    Game game;

    public Plateau(int height, int width,Game game) {
        this.height = height;
        this.width = width;
        this.game=game;
        board = new Cell[height][width];
        init();
    }

    /**
     * Fonction remplissant notre tableau de cellule.
     */
    public void init(){
        for(int i=0; i<this.height;i++){
            for(int j =0; j<this.width;j++){
                this.board[i][j] = new Cell(new Air());
            }
        }

    }

    /**
     * Fonction qui sélectionne une cellule aléatoirement
     * Appelant une autre fonction gérant le contenu de la cellule.
     */
    public void randomFill(){
        Random w = new Random();
        for(int i =0;i<((this.width)*(this.height))*0.4;i++){
            int y = w.nextInt(this.height-1);
            int x = w.nextInt(this.width);
            while(x ==0 && y==0){
                 x = w.nextInt(this.width);
            }
            randomObstacle(x,y);
        }
    }

    /**
     * @param x int coordonne x de la cellule
     * @param y int coordonne y de la cellule
     */
    public void randomObstacle(int x,int y){
        Random r = new Random();
        int chose= r.nextInt(4);
        switch(chose){
            case 0 ->{
                if ((x != 0 && x != getWidth() - 1)) {
                    if (checkNeighbors(x, y)) {
                        setRandomCell(x,y, CONTENU.WOOD,new Bois(game));
                        obstacles.add(board[y][x]);
                    }
                }
            }
            case 1 -> setRandomCell(x,y,CONTENU.STRAWBERRY,new Fraise(game));
            case 2 -> setRandomCell(x,y,CONTENU.BLACKBERRY,new Myrtille(game));
            case 3 -> setRandomCell(x,y,CONTENU.COIN,new Piece(game));
        }
    }

    /**
     * @param x int coordonnée x de la cellule.
     * @param y int coordonnée y de la cellule.
     * @param c contenu de la cellule.
     */
    public void setRandomCell(int x,int y,CONTENU c,Obstacles o){
        board[y][x].setX(x);
        board[y][x].setY(y);
        board[y][x].setContent(c);
        board[y][x].setObstacle(o);
        obstacles.add(board[y][x]);
    }

    /**
     * @param x int coordonne x de la cellule que l'on veut sécuriser.
     * @param y int coordonne y de la cellule que l'on veut sécuriser.
     * @return Boolean true - si les quatre diagonales de notre cellule d'origine ne contiennent pas de bois (pour éviter les serpents bloquer).
     *                 false - sinon
     */
    public boolean checkNeighbors(int x,int y) {
        // On vérifie les diagonale selon la ou on est, pour éviter les erreur d'out of Bounds
        if(x==0 && y==0){ // Angle supérieur gauche
            return board[y + 1][x + 1].getContent() != CONTENU.WOOD;

        } else if(x==0 && y<height-1){ // Bord gauche
            return (board[y + 1][x + 1].getContent() != CONTENU.WOOD) && (board[y - 1][x + 1].getContent() != CONTENU.WOOD);

        } else if(x==0 && y==height-1){ // Angle inférieur gauche
            return board[y - 1][x + 1].getContent() != CONTENU.WOOD;

        } else if(y==0 && x<width-1){ // Bord supérieur
            return (board[y + 1][x - 1].getContent() != CONTENU.WOOD) && (board[y + 1][x + 1].getContent() != CONTENU.WOOD);

        } else if(x==width-1 && y ==0){ // Angle supérieur droit
            return board[y + 1][x - 1].getContent() != CONTENU.WOOD;

        } else if(y==height-1 && x<width-1){ // Bord inférieur
            return board[y - 1][x - 1].getContent() != CONTENU.WOOD;

        } else if(x==width-1 && y<height-1){ // Bord droit
            return (board[y - 1][x - 1].getContent() != CONTENU.WOOD) && (board[y + 1][x - 1].getContent() != CONTENU.WOOD);

        } else if(x==width-1 && y ==height-1){ // Angle inférieur droit
            return board[y - 1][x - 1].getContent() != CONTENU.WOOD;

            // ici on vérifie les quatre diagonales en meme temps.
        } else if((x>0 && y>0) && (x<width-1 && y<height)){
            return (board[y - 1][x - 1].getContent() != CONTENU.WOOD) && (board[y - 1][x + 1].getContent() != CONTENU.WOOD) && (board[y + 1][x - 1].getContent() != CONTENU.WOOD) && (board[y + 1][x + 1].getContent() != CONTENU.WOOD);
        }
        return false;
    }
    /**
     * Fonction qui modifie tous les obstacles aléatoirement, modifiant uniquement leur contenu
     * on revérifie aussi que le bois réponde au meme critère que précédemment.
     */
    public void shuffleObs(){
        for (Cell cell:obstacles) {
            if((cell.getContent()!=CONTENU.EMPTY)&&cell.getContent()!=CONTENU.SNAKE){
                Random r = new Random();
                int chose= r.nextInt(4);
                switch(chose){
                    case 0 ->{
                        if ((cell.getX()!= 0 && cell.getX() != getWidth() - 1)) {
                            if (checkNeighbors(cell.getX(), cell.getY())) {
                                board[cell.getY()][cell.getX()].setContent(CONTENU.WOOD);
                                board[cell.getY()][cell.getX()].setObstacle(new Bois(game));
                            }
                        }
                    }
                    case 1 -> {
                        board[cell.getY()][cell.getX()].setContent(CONTENU.BLACKBERRY);
                        board[cell.getY()][cell.getX()].setObstacle(new Myrtille(game));
                    }
                    case 2 ->{
                        board[cell.getY()][cell.getX()].setContent(CONTENU.STRAWBERRY);
                        board[cell.getY()][cell.getX()].setObstacle(new Fraise(game));
                    }
                    case 3 -> {
                        board[cell.getY()][cell.getX()].setContent(CONTENU.COIN);
                        board[cell.getY()][cell.getX()].setObstacle(new Piece(game));
                    }
                }
            }
        }
    }

    /**
     * @return Renvoi le tableau où sont les cellules.
     */
    public Cell[][] getBoard() {
        return board;
    }

    /**
     * @return renvoi la hauteur du tableau
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return renvoi la largeur du tableau
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return Renvoi la liste des obstacles du tableau.
     */
    public ArrayList<Cell> getObstacles() {
        return obstacles;
    }
}
