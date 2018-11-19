package dadm.scaffold.space;

import dadm.scaffold.engine.Collider;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public class Bomb extends Sprite {

    private double speedFactor;
    private float xVelocity;
    private float yVelocity;
    private SpaceShipPlayer parent;

    public Bomb(GameEngine gameEngine,float xVelocity,float yVelocity,int drawableRes) {
        super(gameEngine, drawableRes,false);
        this.setLayer(Collider.CollideLayer.Bullet);
        speedFactor = gameEngine.pixelFactor * -200d / 1000d;
        this.xVelocity =xVelocity;
        this.yVelocity = yVelocity;
        CreateNewCollider(5,null,5* pixelFactor,5*pixelFactor);
    }

    public void init(SpaceShipPlayer parentPlayer, double initPositionX, double initPositionY) {
        positionX = initPositionX - imageWidth/2;
        positionY = initPositionY - imageHeight/2;
        parent = parentPlayer;
    }

    @Override
    public void OnCollision(Collider otherCollider) {
        switch (otherCollider.Owner.getLayer()){
            case Player:
                break;
            case Enemy:
                Enemy e = (Enemy)otherCollider.Owner;
                e.life--;
                break;
            case Meteorite:
                Meteorito m = (Meteorito)otherCollider.Owner;
                m.life--;
                break;
            case Scenario:
                break;
            case Bullet:
                break;
        }
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionY += speedFactor * elapsedMillis*yVelocity;
        positionX += speedFactor*elapsedMillis*xVelocity;
        if (positionX > gameEngine.width || positionX < -this.imageWidth ) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            parent.releaseBomb(this);
        }
    }
}
