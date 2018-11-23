package dadm.scaffold.space;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dadm.scaffold.R;
import dadm.scaffold.counter.GameFragment;
import dadm.scaffold.engine.Collider;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameManager;
import dadm.scaffold.engine.Sprite;

public class Meteorito extends Sprite {

    protected double xSpeed;
    protected double ySpeed;
    protected int life;
    protected double speedFactor;
    ExplosionEnemigo e;

    public Meteorito(GameEngine gameEngine, float colliderRadius, float xSpeed, float ySpeed, int life) {
        super(gameEngine, R.drawable.meterito64x64smooth,false);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.life = life;
        speedFactor = pixelFactor * 100d / 1000d;
        this.layer = Collider.CollideLayer.Meteorite;
        List<Collider.CollideLayer> layers = new ArrayList<>();
        layers.add(Collider.CollideLayer.Bullet);
        layers.add(Collider.CollideLayer.Player);
        this.CreateNewCollider(30-(colliderRadius* pixelFactor),layers,32* pixelFactor,32*pixelFactor);
        e = new ExplosionEnemigo(gameEngine, R.drawable.explosionmeterorito,false);
    }

    @Override
    public void OnCollision(Collider otherCollider, GameEngine gameEngine) {
        if(otherCollider.Owner.getLayer() == Collider.CollideLayer.Player){
            this.life= 0;
        }
    }

    @Override
    public void startGame() {

    }

    public void Init(int xPos, int yPos) {
        this.positionX = xPos;
        this.positionY = yPos;
        life = 1;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionY += speedFactor*elapsedMillis*ySpeed;
        positionX += speedFactor*elapsedMillis*xSpeed;

        if(positionX <= -this.imageWidth){
            GameFragment.theGameEngine.removeGameObject(this);
            GameManager.ActualManager.RestoreMeteorite(this);
        }

        if(life <= 0){
            Explosion(gameEngine);
            GameFragment.theGameEngine.removeGameObject(this);
            GameManager.ActualManager.score+=10;
            GameManager.ActualManager.RestoreMeteorite(this);
            Random r = new Random();
            switch (r.nextInt(3)){
                case 0:
                    PowerUp p = new PowerUp(gameEngine,R.drawable.powerupdoble,false,0);
                    p.Init((float)(positionX + 32* pixelFactor), (float)(positionY + 32*pixelFactor),-1,0);
                    gameEngine.addGameObject(p);
                    break;
                case 1:
                    PowerUp powerUp = new PowerUp(gameEngine,R.drawable.poweruptriple,false,1);
                    powerUp.Init((float)(positionX + 32* pixelFactor), (float)(positionY + 32*pixelFactor),-1,0);
                    gameEngine.addGameObject(powerUp);
                    break;
                case 2:
                    PowerUp powerUpBomb = new PowerUp(gameEngine,R.drawable.powerupbomba,false,2);
                    powerUpBomb.Init((float)(positionX + 32* pixelFactor), (float)(positionY + 32*pixelFactor),-1,0);
                    gameEngine.addGameObject(powerUpBomb);
                    break;
                case 3:
                    break;
            }
        }
    }

    private void Explosion(GameEngine gameEngine) {
        e.init(positionX + 32* pixelFactor, positionY + 32*pixelFactor);
        gameEngine.addGameObject(e);
    }
}
