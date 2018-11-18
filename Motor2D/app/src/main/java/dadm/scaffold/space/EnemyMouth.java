package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;

public class EnemyMouth extends Enemy {


    public EnemyMouth(GameEngine gameEngine, double colliderHold, float xSpeed, float ySpeed, int life) {
        super(gameEngine, R.drawable.enemigo264x64smooth, colliderHold, xSpeed, ySpeed, life);
    }

    @Override
    public void Init(int xPos, int yPos) {

    }
}
