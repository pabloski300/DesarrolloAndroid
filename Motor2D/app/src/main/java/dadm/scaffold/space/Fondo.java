package dadm.scaffold.space;

import dadm.scaffold.engine.Collider;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public class Fondo extends Sprite {

    private float speed;
    private double speedFactor;

    public Fondo(GameEngine gameEngine, int drawableRes, float speed) {
        super(gameEngine, drawableRes, true);
        this.speed = speed;
        this.speedFactor = 25d/1000d;
        pixelFactor *= 2;
        pixelFactory *= 2;
    }

    @Override
    public void OnCollision(Collider otherCollider, GameEngine gameEngine) {

    }

    @Override
    public void startGame() {

    }

    public void init(float x, float y){
        this.positionX = x;
        this.positionY = y;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        this.positionX += speed*speedFactor*elapsedMillis;
        if(positionX+this.imageWidth*2 <= 1){
            this.positionX=gameEngine.width;
        }
    }
}
