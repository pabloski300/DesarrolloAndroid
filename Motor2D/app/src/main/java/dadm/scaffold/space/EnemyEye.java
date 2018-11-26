package dadm.scaffold.space;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.R;
import dadm.scaffold.engine.BulletHandeler;
import dadm.scaffold.engine.Collider;
import dadm.scaffold.engine.GameEngine;

public class EnemyEye extends Enemy implements BulletHandeler {


    public List<Bullet> bullets;
    public final int MAX_BULLETS_EYE = 5;
    public float timeBullets;
    public float timeBetweenBullets;


    public EnemyEye(GameEngine gameEngine, float colliderRadius, float xSpeed, float ySpeed, int life) {
        super(gameEngine, R.drawable.enemigo164x64smooth, colliderRadius, xSpeed, ySpeed, life);
        timeBullets = 0;
        initBulletPool(gameEngine);
    }



    @Override
    public void Init(int xPos, int yPos, int life, float speed) {
        this.positionX = xPos;
        this.positionY = yPos;
        this.life = life;
        this.xSpeed = speed;
    }

    @Override
    public void initBulletPool(GameEngine gameEngine) {
        bullets = new ArrayList<>();
        List<Collider.CollideLayer> l = new ArrayList<>();
        l.add(Collider.CollideLayer.Player);
        for(int i = 0; i<MAX_BULLETS_EYE;i++){
            Bullet b = new Bullet(gameEngine,1,0,R.drawable.proyectilenemigo64x64smooth,4,3,4,1);
            b.getCollider().collideLayers = l;
            bullets.add(b);
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
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        super.onUpdate(elapsedMillis,gameEngine);
        if(timeBullets == 0){
            Bullet c = getBullet();
            c.init(this,this.positionX+ 3* pixelFactor,this.positionY+32*pixelFactor);
            gameEngine.addGameObject(c);
            timeBullets = 3000;

        }else  {
            timeBullets-=elapsedMillis;
        }
    }
}
