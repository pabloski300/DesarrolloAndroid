package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.counter.GameFragment;
import dadm.scaffold.engine.Collider;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public class Bullet extends Sprite {

    private double speedFactor;
    private float xVelocity;
    private float yVelocity;
    private SpaceShipPlayer parent;

    public Bullet(GameEngine gameEngine,float xVelocity,float yVelocity){
        super(gameEngine, R.drawable.bullet);
        this.setLayer(Collider.CollideLayer.Bullet);
        speedFactor = gameEngine.pixelFactor * -300d / 1000d;
        this.xVelocity =xVelocity;
        this.yVelocity = yVelocity;


    }

    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionY += speedFactor * elapsedMillis*yVelocity;
        positionX += speedFactor*elapsedMillis*xVelocity;
        if (positionX > gameEngine.width) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            parent.releaseBullet(this);
        }
    }


    public void init(SpaceShipPlayer parentPlayer, double initPositionX, double initPositionY) {
        positionX = initPositionX - imageWidth/2;
        positionY = initPositionY+(parentPlayer.getImageHeight()/2) - imageHeight/2;
        parent = parentPlayer;
    }

    @Override
    public void OnCollision(Collider otherCollider) {
        GameFragment.theGameEngine.removeGameObject(this);
        parent.releaseBullet(this);
    }
}
