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

    /**
     * Constructeur par défaut qui définit content à VOID
     */
    public Cell() {
        this.content = type.EMPTY;
    }

    /**
     * @return true si la Cell est VIDE, false sinon
     */
    boolean isFree() {
        return this.content == type.EMPTY;
    }

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
    char draw() {
        return switch (content) {
            case EMPTY -> ' ';
            case STRAWBERRY -> 'S';
            case BLACKBERRY -> 'B';
            case COIN -> 'C';
            case WOOD -> 'W';
            case SNAKE -> '*';
            case PLAYER -> '^';
        };
    }
    @Override
    public String toString() {
        return "content : "+this.content+"("+this.draw()+")";
    }

}
