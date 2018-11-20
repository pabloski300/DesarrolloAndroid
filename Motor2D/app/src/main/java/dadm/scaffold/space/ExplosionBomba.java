package dadm.scaffold.space;

import dadm.scaffold.engine.BulletHandeler;
import dadm.scaffold.engine.Collider;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public class ExplosionBomba extends Sprite {

    protected final long DURATION = 1000;
    protected long currentDuration = 0;

    public ExplosionBomba(GameEngine gameEngine, int drawableRes, boolean resizable) {
        super(gameEngine, drawableRes, resizable);
        this.setLayer(Collider.CollideLayer.Bullet);
        CreateNewCollider(26,null,32* pixelFactor,32*pixelFactor);
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

    public void init(double initPositionX, double initPositionY) {
        positionX = initPositionX - imageWidth/2;
        positionY = initPositionY - imageHeight/2;
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        if(currentDuration > DURATION){
            gameEngine.removeGameObject(this);
            currentDuration = 0;
        }else{
            currentDuration += elapsedMillis;
        }
    }
}
