package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.counter.GameFragment;
import dadm.scaffold.engine.BulletHandeler;
import dadm.scaffold.engine.Collider;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public class Bullet extends Sprite {

    private double speedFactor;
    private float xVelocity;
    private float yVelocity;
    private BulletHandeler parent;

    public Bullet(GameEngine gameEngine,float xVelocity,float yVelocity,int drawResource){
        super(gameEngine, drawResource);
        this.setLayer(Collider.CollideLayer.Bullet);
        speedFactor = gameEngine.pixelFactor * -300d / 1000d;
        this.xVelocity =xVelocity;
        this.yVelocity = yVelocity;
        CreateNewCollider(3,null,20*pixelFactor,3*pixelFactor);
    }

    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionY += speedFactor * elapsedMillis*yVelocity;
        positionX += speedFactor*elapsedMillis*xVelocity;
        if (positionX > gameEngine.width || positionX <0 ) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            parent.releaseBullet(this);
        }
    }


    public void init(BulletHandeler parentPlayer, double initPositionX, double initPositionY) {
        positionX = initPositionX - imageWidth/2;
        positionY = initPositionY - imageHeight/2;
        parent = parentPlayer;
    }

    @Override
    public void OnCollision(Collider otherCollider) {

        switch (otherCollider.Owner.getLayer()){
            case Player:
                SpaceShipPlayer p = (SpaceShipPlayer)otherCollider.Owner;
                p.DealDamage(1);
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
        GameFragment.theGameEngine.removeGameObject(this);
        parent.releaseBullet(this);
    }
}
