package FallingSnake.Controle;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Fonction en charge d'écouter le clavier, et stocke l'information de quelle touche est pressée ou non !
 */
public class Input implements KeyListener{
    private final boolean[] pressed;
    public Input(){
        pressed=new boolean[255];
    }
    /**
     * @param keycode Le Keycode de la touche que l'on veut verifier.
     * @return true si la touche est actuellement pressée
     */
    public boolean isPressed(int keycode){
        return pressed[keycode];
    }
    /**
     * Fonction imposé par l'interface jamais utiliser ici.
     * @param e the event to be processed
     */
    public void keyTyped(KeyEvent e) {}

    /**
     * fonction Imposer par l'interface, elle permet de preciser dans le tableau des touches pressées
     * quand la touche correspondante a e.getKeyCode est pressée
     * @param e the event to be processed
     */
    public void keyPressed(KeyEvent e) {
        pressed[e.getKeyCode()]=true;
    }
    /**
     * fonction Imposer par l'interface, elle permet de preciser dans le tableau des touches pressées
     * quand la touche correspondante a e.getKeyCode n'est plus pressée.
     * @param e the event to be processed
     */
    public void keyReleased(KeyEvent e) {
        pressed[e.getKeyCode()]=false;
    }
}
