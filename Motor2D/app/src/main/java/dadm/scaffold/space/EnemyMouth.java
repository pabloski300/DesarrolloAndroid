package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.counter.GameFragment;
import dadm.scaffold.engine.GameEngine;

public class EnemyMouth extends Enemy {

    public double time;
    public EnemyMouth(GameEngine gameEngine, double colliderHold, float xSpeed, float ySpeed, int life) {
        super(gameEngine, R.drawable.enemigo264x64smooth, colliderHold, xSpeed, ySpeed, life);
    }

    @Override
    public void Init(int xPos, int yPos) {
        this.positionX = xPos;
        this.positionY = yPos;
        life = 2;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        time += (float)elapsedMillis/1000;
        ySpeed = Math.sin(time);
        super.onUpdate(elapsedMillis,gameEngine);
    }
}
