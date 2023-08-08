package FallingSnake.Controle;
//Interface pour gerer la mise en place d'etat.
//on ne gere ici que le fait d'aller a droite ou a gauche,c'est la seule chose dont on a besoin pour le joueur.
public interface Controller {

    /**
     * @return Boolean true si le joueur demande a aller a gauche.
     */
    boolean isRequestingLeft();

    /**
     * @return Boolean true si le joueur demande a aller a droite.
     */
    boolean isRequestingRight();
    /**
     * @return Boolean true si le joueur demande d'appuyer sur espace
     */
    boolean isRequestingSpace();
}
