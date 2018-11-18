package dadm.scaffold.space;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.counter.GameFragment;
import dadm.scaffold.engine.Collider;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameManager;
import dadm.scaffold.engine.Sprite;

public abstract class Enemy extends Sprite {


    protected double xSpeed;
    protected double ySpeed;
    protected int life;
    protected double speedFactor;
    public Enemy(GameEngine gameEngine, int drawableRes, double colliderHold, float xSpeed, float ySpeed, int life){
        super(gameEngine,drawableRes);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.life = life;
        speedFactor = pixelFactor * 100d / 1000d;
        this.layer = Collider.CollideLayer.Enemy;
        List<Collider.CollideLayer> layers = new ArrayList<>();
        layers.add(Collider.CollideLayer.Bullet);
        layers.add(Collider.CollideLayer.Player);
        this.CreateNewCollider(26-(colliderHold*pixelFactor),layers,28*pixelFactor,32*pixelFactor);
    }

    @Override
    public void OnCollision(Collider otherCollider) {
        if(otherCollider.Owner.getLayer() == Collider.CollideLayer.Player){
            this.life= 0;
        }

    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionY += speedFactor*elapsedMillis*ySpeed;
        positionX += speedFactor*elapsedMillis*xSpeed;
        if(positionX <= -this.imageWidth){
            GameFragment.theGameEngine.removeGameObject(this);
            GameManager.ActualManager.RestoreEnemy(this);
        }

        if(life <= 0){
            GameFragment.theGameEngine.removeGameObject(this);
            GameManager.ActualManager.score+=10;
            GameManager.ActualManager.RestoreEnemy(this);

        }

    }

    public abstract void Init(int xPos, int yPos);
}
