package FallingSnake.Affichage;

import FallingSnake.Controle.Input;
import FallingSnake.Jeux.Game;
import FallingSnake.Jeux.Plateau;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

import static FallingSnake.cellules.CONTENU.EMPTY;
import static FallingSnake.cellules.CONTENU.SNAKE;
import static java.awt.Font.PLAIN;

/**
 * Classe du système d'affichage, héritant de tous les composants de la classe JFrame
 *
 */
public class Affichage extends JFrame {
    private int cellSize=25;
    private Canvas canvas;
    private final Plateau plateau;
    private final JPanel panel;
    private final Game game;
    private Graphics graphics;
    private BufferStrategy bufferStrategy;

    /**
     * Constructeur de système d'affichage
     * @param width La largeur de la fenêtre.
     * @param height  La hauteur de la fenêtre.
     * @param input Un objet Input pour gérer le clavier.
     * @param game Le jeu que nous allons afficher.
     */
    public Affichage(int width, int height, Input input, Game game){
        // Ici on definit les parametre de notre Jframe
        setTitle("Falling Snake");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        this.game=game;
        this.plateau = game.getPlateau();
        canvas=new Canvas();
        canvas.setPreferredSize(new Dimension(width,height));
        canvas.setFocusable(false);
        JLabel controle = new JLabel("On utilise les fleches pour se deplacer et on reste appuyé sur la barre espace pour tirer");
        this.panel = new JPanel();
        panel.add(canvas);
        panel.add(controle);
        panel.setPreferredSize(new Dimension(width, height+50));
        panel.setLocation(width+1,height+1);
        panel.setVisible(true);
        this.add(panel);
        addKeyListener(input);
        pack();
        //Ici on precise que l'on veux dessiner 3 image d'avance. Avant de les affichers.
        canvas.createBufferStrategy(3);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Fonction qui s'occupe de dessiner la grille de notre aire de jeu
     * @param i int Position x dans le tableau
     * @param j int position y dans le tableau
     * @param graphics Graphics de notre Frame
     */
    public void dessinGrille(int i, int j, Graphics graphics){
        if((i+j)%2==0){
            graphics.setColor(new Color(61,172,93));
        }else{
            graphics.setColor(new Color(82,182,86));
        }
        graphics.fillRect(j*25,i*25,25,25);
    }

    /**
     *
     * Fonction qui s'occupe de dessiner les obstacles selon des methodes predefinis
     * @param i int Position x dans le tableau
     * @param j int position y dans le tableau
     * @param c Graphics de notre Frame
     */
    public void dessinObs(Graphics c,int i,int j){
            c.setColor(plateau.getBoard()[i][j].getObstacle().getColor());
            switch(plateau.getBoard()[i][j].getContent()){
                default-> drawCloud(j,i);
                case WOOD -> drawWood(j,i);
                case COIN -> drawCircle(graphics,j*cellSize+cellSize/2, i*cellSize+cellSize/2,cellSize);
                case BOULET-> drawCircle(graphics,j*cellSize+cellSize/2, i*cellSize+cellSize/2,cellSize/3*2);
                case PLAYER -> drawSquare(graphics,j*cellSize+cellSize/2, i*cellSize+cellSize/2, cellSize/3*2);
            }
    }

    /**
     * Fonction qui Dessine le snake.
     * @param i int Position x dans le tableau
     * @param j int position y dans le tableau
     * @param c Graphics de notre Frame
     */
    public void dessinSnake(Graphics c,int i,int j){
        c.setColor(game.getSerpent().getCouleur());
        c.fillOval(j * 25+2, i * 25+2, 20, 20);
        c.setColor(Color.BLACK);
        c.drawOval(j * 25+2, i*25+2, 20, 20);
    }

    /**
     * Fonction qui lance les fonctions de dessin de notre aire de jeu
     * @param graphics Graphics de notre Frame
     */
    public void draw(Graphics graphics){
        for(int i=0;i<20;i++){
            for(int j=0;j<20;j++){
                dessinGrille(i,j,graphics);
                if(plateau.getBoard()[i][j].getContent()!=EMPTY && plateau.getBoard()[i][j].getContent()!=SNAKE) {
                    dessinObs(graphics,i,j);
                }
                if(plateau.getBoard()[i][j].getContent()==SNAKE){
                    dessinSnake(graphics,i,j);
                }
            }
        }
    }

    /**
     * Fonction qui gere le rendu du plateau de jeux, replaçant sur une interface 2D le contenu du tableau 2D
     * @param game Un objet Game contenant tous les projectiles ainsi que le plateau de jeu.
     */
    public void render(Game game){
        if ((game.getSerpent().getSnakeDead()) || (game.getCanon().isDead())) {
            this.panel.setVisible(false);
        }
        this.bufferStrategy = this.canvas.getBufferStrategy();
        this.graphics= bufferStrategy.getDrawGraphics();
        draw(graphics);
        graphics.dispose();
        bufferStrategy.show();
    }

    /**
     * @param choix int 1 = ( vous gagnez la partie) 0 = (vous perdez la partie)
     * @return JPanel de fin selon le choix fournit
     */
    public JPanel contenuFin(int choix){
        canvas.setVisible(false);
        JPanel panneauFin = new JPanel();
        JLabel TexteFin = new JLabel();
        if(choix==1){
            TexteFin.setText("Fin de la partie ! vous avez Gagnez !");
        } else {
            TexteFin.setText("Fin de la partie ! vous avez Perdu !");
        }
        JLabel Score = new JLabel("Score : "+game.getScore());
        Score.setFont(new Font("Verdana",PLAIN,20));
        TexteFin.setFont(new Font("Verdana",PLAIN,20));
        panneauFin.add(TexteFin,new GridBagConstraints());
        panneauFin.add(Score);
        panneauFin.setBounds(40,50,150,150);
        panneauFin.setBackground(new Color(180,180,180));
        return panneauFin;
    }

    /**
     * Cette fonction sert a afficher le panneau de fin si le joueur gagne.
     * AKA si le serpent meurt avant de toucher le canon.
     */
    public void winEnd(){
        JPanel panneau=contenuFin(1);
        this.remove(this.panel);
        this.add(panneau);
        this.setVisible(true);
        panneau.setVisible(true);
    }

    /**
     * Cette fonction sert à afficher le panneau de fin si le joueur perd.
     * AKA si le serpent touche le canon.
     */
    public void loseEnd(){
        JPanel panneau=contenuFin(0);
        this.add(panneau);
        panneau.setVisible(true );
        this.setVisible(true);
    }

    /**
     * fonction qui dessine des "nuage" pour certain obstacle
     * @param i index x du tableau
     * @param j index y du tableau
     */
    public void drawCloud(int i, int j){
        int cellSize=25;
        int x = i*cellSize;
        int y = j*cellSize;
        drawCircle(graphics, x+cellSize/2, y+cellSize/4, cellSize/2);
        drawCircle(graphics, x+(cellSize/4)*3, y+cellSize/2, cellSize/2);
        drawCircle(graphics, x+cellSize/2, y+(cellSize/4)*3, cellSize/2);
        drawCircle(graphics, x+cellSize/4, y+cellSize/2, cellSize/2);
    }

    /**
     * Fonction qui dessine le bois
     * @param i index x du tableau
     * @param j index y du tableau
     */
    public void drawWood(int i,int j) {
        int cellSize=25;
        int x = i*cellSize;
        int y = j*cellSize;
        drawCircle(graphics, x+cellSize/4, y+cellSize/4, cellSize/2);
        drawCircle(graphics, x+(cellSize/4)*3, y+cellSize/4, cellSize/2);
        drawCircle(graphics, x+cellSize/4, y+(cellSize/4)*3, cellSize/2);
        drawCircle(graphics, x+cellSize/4*3, y+cellSize/4*3, cellSize/2);
        drawCircle(graphics, x+cellSize/2, y+cellSize/2, cellSize/2);
    }

    /**
     * @param g Graphics graphics de notre frame
     * @param x int index x du tableau
     * @param y int index y du tableau
     * @param diameter int diametre du cercle
     */
    public void drawCircle(Graphics g, int x, int y, int diameter) {
        g.fillOval(x-(diameter/2), y-(diameter/2), diameter, diameter);
    }

    /**
     * @param g Graphics graphics de notre frame
     * @param x int index x du tableau
     * @param y int index y du tableau
     * @param size int taille du cercle
     */
    public void drawSquare(Graphics g,int x,int y,int size){
        g.fillRect(x-(size/2),y-(size/2),size,size);
    }
}
