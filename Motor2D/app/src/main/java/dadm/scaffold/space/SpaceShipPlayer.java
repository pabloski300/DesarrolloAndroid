package dadm.scaffold.space;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dadm.scaffold.R;
import dadm.scaffold.counter.GameFragment;
import dadm.scaffold.engine.BulletHandeler;
import dadm.scaffold.engine.Collider;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameManager;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.input.InputController;

public class SpaceShipPlayer extends Sprite implements BulletHandeler {

    private static final int INITIAL_BULLET_POOL_AMOUNT = 6;
    private static final long TIME_BETWEEN_BULLETS = 250;
    List<Bullet> bullets = new ArrayList<Bullet>();
    private long timeSinceLastFire;
    protected int life;
    private int maxX;
    private int maxY;
    private double speedFactor;
    private float invencibleTime;
    private final float maxInvencible;


    public SpaceShipPlayer(GameEngine gameEngine){
        super(gameEngine, R.drawable.nave64x64smooth);
        speedFactor = pixelFactor * 100d / 1000d; // We want to move at 100px per second on a 400px tall screen
        maxX = gameEngine.width - imageWidth;
        maxY = gameEngine.height - imageHeight;
        layer = Collider.CollideLayer.Player;
        List<Collider.CollideLayer> Layers = new ArrayList<>();
        Layers.add(Collider.CollideLayer.Enemy);
        this.CreateNewCollider(10.5, Layers,23.5 * pixelFactor,26*pixelFactor);
        initBulletPool(gameEngine);
        life = 1;
        maxInvencible = 0.5f;
    }

    public void initBulletPool(GameEngine gameEngine) {
        List<Collider.CollideLayer> l = new ArrayList<>();
        l.add(Collider.CollideLayer.Enemy);
        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++) {
            Bullet v = new Bullet(gameEngine,-1,0,R.drawable.proyectiljugador64x64smooth);

            v.getCollider().collideLayers = new ArrayList<>();
            v.getCollider().collideLayers.add(Collider.CollideLayer.Meteorite);
            v.getCollider().collideLayers.add(Collider.CollideLayer.Enemy);
            bullets.add(v);
        }
    }
    @Override
     public Bullet getBullet() {
        if (bullets.isEmpty()) {
            return null;
        }
        return bullets.remove(0);
    }

    @Override
    public void releaseBullet(Bullet bullet) {
        bullets.add(bullet);
    }


    @Override
    public void startGame() {
        positionX =0;
        positionY = maxY / 2;
        rotation = 0;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        // Get the info from the inputController
        updatePosition(elapsedMillis, gameEngine.theInputController);
        checkFiring(elapsedMillis, gameEngine);

        invencibleTime += elapsedMillis;


    }

    private void updatePosition(long elapsedMillis, InputController inputController) {
        positionX += speedFactor * inputController.horizontalFactor * elapsedMillis;
        if (positionX < 0) {
            positionX = 0;
        }
        if (positionX > maxX) {
            positionX = maxX;
        }
        positionY += speedFactor * inputController.verticalFactor * elapsedMillis;
        if (positionY < 0) {
            positionY = 0;
        }
        if (positionY > maxY) {
            positionY = maxY;
        }
    }

    private void checkFiring(long elapsedMillis, GameEngine gameEngine) {
        if (gameEngine.theInputController.isFiring && timeSinceLastFire > TIME_BETWEEN_BULLETS) {
            Bullet bullet = getBullet();
            if (bullet == null) {
                return;
            }
            bullet.init(this, positionX + 23.5*pixelFactor, positionY +26*pixelFactor);
            gameEngine.addGameObject(bullet);
            timeSinceLastFire = 0;
        }
        else {
            timeSinceLastFire += elapsedMillis;
        }
    }

    @Override
    public void OnCollision(Collider otherCollider) {
        if(otherCollider.Owner.getLayer() == Collider.CollideLayer.Enemy && invencibleTime > maxInvencible){
            invencibleTime = 0;
            DealDamage(1);

        }
    }

    public void DealDamage (int damage){
        life -= damage;
        if(life <= 0){
            GameFragment.theGameEngine.getMainActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    GameManager.ActualManager.Lose();
                }
            });
        }
    }


}
