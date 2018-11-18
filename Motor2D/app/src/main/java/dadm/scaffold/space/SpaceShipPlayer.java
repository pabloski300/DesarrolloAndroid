package dadm.scaffold.space;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dadm.scaffold.R;
import dadm.scaffold.counter.GameFragment;
import dadm.scaffold.engine.Collider;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameManager;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.input.InputController;

public class SpaceShipPlayer extends Sprite {

    private static final int INITIAL_BULLET_POOL_AMOUNT = 6;
    private static final long TIME_BETWEEN_BULLETS = 250;
    List<Bullet> bullets = new ArrayList<Bullet>();
    private long timeSinceLastFire;
    protected int life;
    private int maxX;
    private int maxY;
    private double speedFactor;


    public SpaceShipPlayer(GameEngine gameEngine){
        super(gameEngine, R.drawable.ship);
        speedFactor = pixelFactor * 100d / 1000d; // We want to move at 100px per second on a 400px tall screen
        maxX = gameEngine.width - imageWidth;
        maxY = gameEngine.height - imageHeight;
        layer = Collider.CollideLayer.Player;
        List<Collider.CollideLayer> Layers = new ArrayList<>();
        Layers.add(Collider.CollideLayer.Enemy);
        this.CreateNewCollider(((this.imageHeight-100)/pixelFactor),Layers,this.imageWidth/2,this.imageHeight/2);
        initBulletPool(gameEngine);
        life = 1;
    }

    private void initBulletPool(GameEngine gameEngine) {
        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++) {
            Bullet v = new Bullet(gameEngine,-1,0);
            List<Collider.CollideLayer> l = new ArrayList<>();
            l.add(Collider.CollideLayer.Enemy);
            v.CreateNewCollider(v.getImageWidth()/pixelFactor,l,v.getImageWidth()/2,v.getImageHeight()/2);
            bullets.add(v);
        }
    }

    private Bullet getBullet() {
        if (bullets.isEmpty()) {
            return null;
        }
        return bullets.remove(0);
    }

    void releaseBullet(Bullet bullet) {
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
            bullet.init(this, positionX + imageWidth/2, positionY);
            gameEngine.addGameObject(bullet);
            timeSinceLastFire = 0;
        }
        else {
            timeSinceLastFire += elapsedMillis;
        }
    }

    @Override
    public void OnCollision(Collider otherCollider) {
        if(otherCollider.Owner.getLayer() == Collider.CollideLayer.Enemy){
            life--;
            if(life == 0){
               GameFragment.theGameEngine.getMainActivity().runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       GameManager.ActualManager.Lose();
                   }
               });
            }
        }
    }



}
