package dadm.scaffold.space;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.R;
import dadm.scaffold.counter.GameFragment;
import dadm.scaffold.engine.Collider;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.SoundManager;
import dadm.scaffold.engine.Sprite;

public class Bomb extends Sprite {

    private double speedFactor;
    private float xVelocity;
    private float yVelocity;
    private SpaceShipPlayer parent;
    ExplosionBomba e;

    public Bomb(GameEngine gameEngine,float xVelocity,float yVelocity,int drawableRes) {
        super(gameEngine, drawableRes,false);
        this.setLayer(Collider.CollideLayer.Bullet);
        speedFactor = gameEngine.pixelFactor * -200d / 1000d;
        this.xVelocity =xVelocity;
        this.yVelocity = yVelocity;
        CreateNewCollider(5,null,5* pixelFactor,5*pixelFactor);
        e = new ExplosionBomba(gameEngine, R.drawable.explosionbomba,false);
        e.getCollider().collideLayers = new ArrayList<>();
        e.getCollider().collideLayers.add(Collider.CollideLayer.Meteorite);
        e.getCollider().collideLayers.add(Collider.CollideLayer.Enemy);
    }

    public void init(SpaceShipPlayer parentPlayer, double initPositionX, double initPositionY) {
        positionX = initPositionX - imageWidth/2;
        positionY = initPositionY - imageHeight/2;
        parent = parentPlayer;
    }

    @Override
    public void OnCollision(Collider otherCollider, GameEngine gameEngine) {
        switch (otherCollider.Owner.getLayer()){
            case Player:
                break;
            case Enemy:
                Explosion(gameEngine);
                break;
            case Meteorite:
                Explosion(gameEngine);
                break;
            case Scenario:
                break;
            case Bullet:
                break;
        }
        GameFragment.theGameEngine.removeGameObject(this);
        parent.releaseBomb(this);
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionY += speedFactor * elapsedMillis*yVelocity;
        positionX += speedFactor* elapsedMillis*xVelocity;
        if (positionX > gameEngine.width || positionX < -this.imageWidth ) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            parent.releaseBomb(this);
        }
    }

    private void Explosion(GameEngine gameEngine) {
        e.init(positionX + 5* pixelFactor, positionY + 5*pixelFactor);
        gameEngine.PlaySound(SoundManager.FXSounds.Explosion);
        gameEngine.addGameObject(e);
    }
}
