package FallingSnake.Controle;

import java.awt.event.KeyEvent;

/**
 * Classe en charge de transformer les entree clavier en "demande utilisateur"
 *
 * Selon le KEYCODE de la touche actuellement pressée, on n'envoie pas la meme demande à notre code.
 * Cette classe est principalement utilisée par le canon.
 */
public class PlayerController implements Controller {

    private Input input;

    public PlayerController(Input input){
        this.input=input;
    }
    /**
     * @return Boolean true si la touche FlecheGauche est pressée
     */
    public boolean isRequestingLeft() {
        return input.isPressed(KeyEvent.VK_LEFT);
    }
    /**
     * @return Boolean true si la touche FlecheDroite est pressée
     */
    public boolean isRequestingRight() {
        return input.isPressed(KeyEvent.VK_RIGHT);
    }

    /**
     * @return Boolean true si la touche Espace est pressée
     */
    public boolean isRequestingSpace(){
        return input.isPressed(KeyEvent.VK_SPACE);
    }
}
