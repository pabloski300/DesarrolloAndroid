package dadm.scaffold.space;

import dadm.scaffold.engine.Collider;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public class UITextControls extends Sprite {

    private final long TIME_TO_DISSAPEAR = 2000;
    private  long currentTime = 0;

    public UITextControls(GameEngine gameEngine, int drawableRes) {
        super(gameEngine, drawableRes, true);

        pixelFactor *= 2;
        pixelFactory *= 2;

    }

    @Override
    public void OnCollision(Collider otherCollider, GameEngine gameEngine) {

    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        if(currentTime >= TIME_TO_DISSAPEAR){
            gameEngine.removeGameObject(this);
        }else{
            currentTime += elapsedMillis;
        }
    }
}
