package FallingSnake.Jeux;

public class GameLoop implements Runnable {
    private Game game;
    private boolean running;
    /**
     * Definitions du taux de rafraîchissement
     */
    private final double updateRate = 1.0d/3d;
    private long nextStatTime;

    public GameLoop(Game game){
        this.game=game;
    }

    /**
     * La fonction run lance l'application,elle gere le taux de raffraichissement  et donc l'appel des fonctions
     * de mise a jour ainsi que celle de l'affichage.
     *
     */
    @Override
    public void run() {
        running=true;
        double accumulator=0;
        long currentTime,lastUpdate=System.currentTimeMillis();
        nextStatTime=System.currentTimeMillis()+1000;

        while(running){
            currentTime=System.currentTimeMillis();
            double lastRenderTimeInSeconds = (currentTime-lastUpdate)/1000d;
            accumulator+= lastRenderTimeInSeconds;
            lastUpdate=currentTime;

            while(accumulator>updateRate){
                update();
                accumulator-=updateRate;
            }
            render();
        }
    }

    /**
     * La fonction update ici, vérifie quand est-ce que l'on actualise ou non.
     * Si la partie est finie on veut arrêter de demander à notre CPU de consommer des ressources à calculer
     * les nouvelles positions du serpent alors que tout est fini.
     *
     * Selon quelle victoire est réalisée :
     *
     * Le serpent meurt -> 0 -> On affiche que le joueur à gagner !
     * Le canon meurt -> 1 -> On affiche que le joueur à perdu !
     *
     * Si aucun des deux n'est mort, on continue d'actualiser le jeu.
     */
    private void update() {
        if((!game.getSerpent().getSnakeDead()) && (!game.getCanon().isDead())){
            game.update();
        }else{
            if(game.getSerpent().getSnakeDead()) {
                resolveEnd(1);
            }else {
                if (game.getCanon().isDead()) {
                    resolveEnd(0);
                }
            }
        }
    }

    /**
     *La fonction render réagit de la meme facon que la fonction update mais avec l'affichage.
     *
     *Ici on arrête d'afficher l'ancien plateau quand le jeu est terminé.
     *
     *
     */
    private void render() {
        if(!game.getSerpent().getSnakeDead()){
            game.render();
        }else{
            if(game.getSerpent().getSnakeDead()) {
                resolveEnd(1);
            }
            if(game.getSerpent().getPlayerDead()){
                resolveEnd(0);
            }
        }
    }
    /**
     * Cette fonction lance un certain affichage selon si on a gagner ou perdu.
     * @param winType Le type de victoire 0 ou 1
     *
     */
    public void resolveEnd(int winType){
        if (winType==1) {
            game.getAffichage().winEnd();
        } else {
            game.getAffichage().loseEnd();
        }
    }

}
