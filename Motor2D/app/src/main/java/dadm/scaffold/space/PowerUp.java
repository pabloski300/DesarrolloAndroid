package dadm.scaffold.space;

import java.util.ArrayList;

import dadm.scaffold.engine.Collider;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameManager;
import dadm.scaffold.engine.Sprite;

public class PowerUp extends Sprite {

    private float xvel;
    private float yvel;
    protected double speedFactor;
    PoweUpInformation powerUp;

    protected PowerUp(GameEngine gameEngine, int drawableRes, boolean resizable,int type) {
        super(gameEngine, drawableRes, resizable);
        speedFactor = pixelFactor * 50d / 1000d;
        switch (type){
            case PoweUpInformation.DOUBLE_SHOOT:
                powerUp = new PoweUpInformation(3000,0);
                break;
            case PoweUpInformation.TRIPLE_SHOOT:
                powerUp = new PoweUpInformation(3000,1);
                break;
            case PoweUpInformation.BOMB:
                powerUp = new PoweUpInformation(0,2);
                break;

        }
        ArrayList<Collider.CollideLayer> l = new ArrayList<Collider.CollideLayer>();
        l.add(Collider.CollideLayer.Player);
        this.CreateNewCollider(8,l,8*pixelFactor,7*pixelFactor);
    }

    @Override
    public void OnCollision(Collider otherCollider, GameEngine gameEngine) {
        if(otherCollider.Owner.getLayer() == Collider.CollideLayer.Player){
            gameEngine.removeGameObject(this);
            ((SpaceShipPlayer)otherCollider.Owner).powerUp = powerUp;
            GameManager.powerTime = (int)powerUp.time;
        }
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionY += speedFactor*elapsedMillis*yvel;
        positionX += speedFactor*elapsedMillis*xvel;
    }

    public void Init(float posx, float posy,float vecx, float vecy){
        positionX = posx - imageWidth/2;
        positionY = posy - imageHeight/2;
        xvel = vecx;
        yvel = vecy;
    }
}
