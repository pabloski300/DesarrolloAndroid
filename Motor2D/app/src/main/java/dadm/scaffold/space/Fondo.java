package dadm.scaffold.space;

import dadm.scaffold.engine.Collider;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public class Fondo extends Sprite {


    public Fondo(GameEngine gameEngine, int drawableRes) {
        super(gameEngine, drawableRes);
        pixelFactor *=2;
    }

    @Override
    public void OnCollision(Collider otherCollider) {

    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

    }
}
