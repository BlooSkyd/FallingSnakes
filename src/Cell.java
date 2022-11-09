public class Cell {

    private type content;
    public enum type {
        EMPTY,
        STRAWBERRY,
        BLACKBERRY,
        COIN,
        WOOD,
        SNAKE,
        PLAYER
    }
    private int x;
    private int y;

    // Getters
    public int getX() {return x;}
    public int getY() {return y;}

    /**
     * Constructeur par défaut qui définit content à VOID
     */
    public Cell(int x, int y) {
        this.content = type.EMPTY;
        this.x = x;
        this.y = y;
    }

    /**
     * @return true si la Cell est VIDE, false sinon
     */
    boolean isFree() {
        return this.content == type.EMPTY;
    }

    /**
     * @return true si la Cell ne contient pas de bois, false sinon
     */
    boolean isNotWood() {return this.content != type.WOOD; }

    /**
     * Méthode qui définit le type de la Cell
     * @param content enum de type
     */
    public void setContent(type content) {
        this.content = content;
    }

    /**
     * Fonction qui permet d'afficher le contenu de la Cell
     * @return char correspondant au type de la Cell
     */
    String draw() {
        return switch (content) {
            case EMPTY -> ".";
            case STRAWBERRY -> "\u001B[31mS\u001B[37m"; // S en rouge
            case BLACKBERRY -> "\u001B[34mB\u001B[37m"; // B en bleu
            case COIN -> "\u001B[33mC\u001B[37m"; // C en jaune
            case WOOD -> "\u001B[35mW\u001B[37m"; // W en violet
            case SNAKE -> "\u001B[32m*\u001B[37m"; // * en vert
            case PLAYER -> "\u001B[97m^\u001B[37m"; // ^ en blanc
        };
    }
    @Override
    public String toString() {
        //return "content : "+this.content+"("+this.draw()+")";
        return "("+this.x+","+this.y+") ";
    }

}
