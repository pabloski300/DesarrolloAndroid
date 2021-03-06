package dadm.scaffold.space;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.R;
import dadm.scaffold.counter.GameFragment;
import dadm.scaffold.engine.BulletHandeler;
import dadm.scaffold.engine.Collider;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameManager;
import dadm.scaffold.engine.SoundManager;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.input.InputController;

public class SpaceShipPlayer extends Sprite implements BulletHandeler {

    private static final int INITIAL_BULLET_POOL_AMOUNT = 150;
    private static final int INITIAL_BOMB_POOL_AMOUNT = 6;
    private static long TIME_BETWEEN_BULLETS;
    private static final long TIME_BETWEEN_BOMBS = 2000;
    private static int BOMBS_NUM = 3;
    private static int currentBombAmount;
    List<Bullet> bullets = new ArrayList<Bullet>();
    List<Bomb> bombs = new ArrayList<Bomb>();
    private long timeSinceLastFire;
    private long timeSinceLastFiredBomb;
    protected int life;
    private int maxX;
    private int maxY;
    private double speedFactor;
    private float invencibleTime;
    private final float maxInvencible;
    protected int  bulletDrawableRes;
    public PoweUpInformation powerUp;
    protected int bulletDamage;

    public SpaceShipPlayer(GameEngine gameEngine,int player,int bullet, float speed, int  bulletDamage, long timeBetweenBullets,int bombNumber, int  life){
        super(gameEngine, player, false);
        bulletDrawableRes = bullet;
        speedFactor = pixelFactor * (100d / 1000d) * speed; // We want to move at 100px per second on a 400px tall screen
        BOMBS_NUM = bombNumber;
        currentBombAmount = BOMBS_NUM;
        maxX = gameEngine.width - imageWidth;
        maxY = gameEngine.height - imageHeight;
        layer = Collider.CollideLayer.Player;
        List<Collider.CollideLayer> Layers = new ArrayList<>();
        Layers.add(Collider.CollideLayer.Enemy);
        this.CreateNewCollider(10.5, Layers,23.5 * pixelFactor,26*pixelFactor);
        this.bulletDamage = bulletDamage;
        initBulletPool(gameEngine);
        initBombPool(gameEngine);
        this.life = life;
        maxInvencible = 1f;
        TIME_BETWEEN_BULLETS = timeBetweenBullets;
    }

    public void initBulletPool(GameEngine gameEngine) {
        List<Collider.CollideLayer> l = new ArrayList<>();
        l.add(Collider.CollideLayer.Enemy);
        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++) {
            Bullet v = new Bullet(gameEngine,-1,0,bulletDrawableRes,3,20,3, bulletDamage);

            v.getCollider().collideLayers = new ArrayList<>();
            v.getCollider().collideLayers.add(Collider.CollideLayer.Meteorite);
            v.getCollider().collideLayers.add(Collider.CollideLayer.Enemy);
            bullets.add(v);
        }
    }

    public void initBombPool(GameEngine gameEngine) {
        List<Collider.CollideLayer> l = new ArrayList<>();
        l.add(Collider.CollideLayer.Enemy);
        for (int i=0; i<INITIAL_BOMB_POOL_AMOUNT; i++) {
            Bomb b = new Bomb(gameEngine,-1,0, R.drawable.bomb);

            b.getCollider().collideLayers = new ArrayList<>();
            b.getCollider().collideLayers.add(Collider.CollideLayer.Meteorite);
            b.getCollider().collideLayers.add(Collider.CollideLayer.Enemy);
            bombs.add(b);
        }
    }

    @Override
     public Bullet getBullet() {
        if (bullets.isEmpty()) {
            return null;
        }
        return bullets.remove(0);
    }

    public Bomb getBomb() {
        if (bombs.isEmpty()) {
            return null;
        }
        return bombs.remove(0);
    }

    @Override
    public void releaseBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public void releaseBomb(Bomb bomb) { bombs.add(bomb); }


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
        checkBomb(elapsedMillis,gameEngine);

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
        GameManager.ActualManager.lifes = life;
        GameManager.ActualManager.bombs = currentBombAmount;
    }

    private void checkFiring(long elapsedMillis, GameEngine gameEngine) {
        if (timeSinceLastFire > TIME_BETWEEN_BULLETS) {
            gameEngine.PlaySound(SoundManager.FXSounds.Fire);
            if (powerUp == null) {
                Bullet bullet = getBullet();
                if (bullet == null) {
                    return;
                }
                bullet.init(this, positionX + 23.5 * pixelFactor, positionY + 26 * pixelFactor);
                gameEngine.addGameObject(bullet);
                bullet.initVel(-1,0);
                timeSinceLastFire = 0;
            }
            else {
                switch (powerUp.type) {
                    case PoweUpInformation.DOUBLE_SHOOT:
                        if(bullets.size() >1) {
                            Bullet b1 = getBullet();
                            Bullet b2 = getBullet();
                            b1.init(this, positionX + 23.5 * pixelFactor, positionY + 26 * pixelFactor);
                            b2.init(this, positionX + 23.5 * pixelFactor, positionY + 26 * pixelFactor);
                            b1.initVel(-1,-0.25f);
                            b2.initVel(-1,0.25f);
                            gameEngine.addGameObject(b1);
                            gameEngine.addGameObject(b2);
                        }
                        break;
                    case PoweUpInformation.TRIPLE_SHOOT:
                        if(bullets.size() >2) {
                            Bullet b1 = getBullet();
                            Bullet b2 = getBullet();
                            Bullet b3 = getBullet();
                            b1.init(this, positionX + 23.5 * pixelFactor, positionY + 26 * pixelFactor);
                            b2.init(this, positionX + 23.5 * pixelFactor, positionY + 26 * pixelFactor);
                            b3.init(this, positionX + 23.5 * pixelFactor, positionY + 26 * pixelFactor);
                            b3.initVel(-1,0);
                            b1.initVel(-1,-0.5f);
                            b2.initVel(-1,0.5f);

                            gameEngine.addGameObject(b1);
                            gameEngine.addGameObject(b2);
                            gameEngine.addGameObject(b3);
                        }
                        break;
                    case PoweUpInformation.BOMB:
                        if(currentBombAmount<BOMBS_NUM){
                            currentBombAmount ++;
                        }
                        break;
                }
                powerUp.time -= timeSinceLastFire;

                if(powerUp.time <= 0){
                    powerUp = null;
                }
                timeSinceLastFire = 0;

            }
        }
        else {
            timeSinceLastFire += elapsedMillis;
        }
    }

    private void checkBomb(long elapsedMillis, GameEngine gameEngine) {
        if (gameEngine.theInputController.isFiringBomb && timeSinceLastFiredBomb > TIME_BETWEEN_BOMBS && currentBombAmount > 0) {
            Bomb bomb = getBomb();
            if (bomb == null) {
                return;
            }
            currentBombAmount --;
            bomb.init(this, positionX + 23.5* pixelFactor, positionY +26*pixelFactor);
            gameEngine.addGameObject(bomb);
            timeSinceLastFiredBomb = 0;
        }
        else {
            timeSinceLastFiredBomb += elapsedMillis;
        }
    }

    @Override
    public void OnCollision(Collider otherCollider, GameEngine gameEngine) {
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
