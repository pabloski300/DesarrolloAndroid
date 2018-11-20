package dadm.scaffold.space;

import dadm.scaffold.engine.Collider;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public class ExplosionEnemigo extends Sprite {

    protected final long DURATION = 1000;
    protected long currentDuration = 0;

    public ExplosionEnemigo(GameEngine gameEngine, int drawableRes, boolean resizable) {
        super(gameEngine, drawableRes, resizable);
    }

    @Override
    public void OnCollision(Collider otherCollider, GameEngine gameEngine) {

    }

    @Override
    public void startGame() {

    }

    public void init(double initPositionX, double initPositionY) {
        positionX = initPositionX - imageWidth/2;
        positionY = initPositionY - imageHeight/2;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        if(currentDuration > DURATION){
            gameEngine.removeGameObject(this);
            currentDuration  = 0;
        }else{
            currentDuration += elapsedMillis;
        }
    }
}
