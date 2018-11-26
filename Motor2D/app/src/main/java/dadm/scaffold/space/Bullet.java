package dadm.scaffold.space;

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
    private int damage;

    public Bullet(GameEngine gameEngine,float xVelocity,float yVelocity,int drawResource,float radius , float colloffx, float colloffy, int bulletDamage){
        super(gameEngine, drawResource,false);
        this.setLayer(Collider.CollideLayer.Bullet);
        speedFactor = gameEngine.pixelFactor * -400d / 1000d;
        this.xVelocity =xVelocity;
        this.yVelocity = yVelocity;
        this.damage = bulletDamage;
        CreateNewCollider(radius,null,colloffx* pixelFactor,colloffy*pixelFactor);
    }

    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionY += speedFactor * elapsedMillis*yVelocity;
        positionX += speedFactor*elapsedMillis*xVelocity;
        if (positionX > gameEngine.width || positionX < -this.imageWidth) {
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
    public void OnCollision(Collider otherCollider, GameEngine gameEngine) {

        switch (otherCollider.Owner.getLayer()){
            case Player:
                SpaceShipPlayer p = (SpaceShipPlayer)otherCollider.Owner;
                p.DealDamage(1);
                break;
            case Enemy:
                Enemy e = (Enemy)otherCollider.Owner;
                e.life -= damage;
                break;
            case Meteorite:
                Meteorito m = (Meteorito)otherCollider.Owner;
                m.life -= damage;
                break;
            case Scenario:
                break;
            case Bullet:
                break;
        }
        GameFragment.theGameEngine.removeGameObject(this);
        parent.releaseBullet(this);
    }

    public void initVel(float xVelocity,float yVelocity){
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }
}
