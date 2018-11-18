package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;

public class EnemyEye extends Enemy {

    public EnemyEye(GameEngine gameEngine, float colliderRadius, float xSpeed, float ySpeed, int life) {
        super(gameEngine, R.drawable.enemigo1, colliderRadius, xSpeed, ySpeed, life);
    }

    @Override
    public void Init(int xPos, int yPos) {
        this.positionX = xPos;
        this.positionY = yPos;
        life =1;
    }
}
