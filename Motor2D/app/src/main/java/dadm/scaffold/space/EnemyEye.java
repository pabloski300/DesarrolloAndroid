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


    public EnemyEye(GameEngine gameEngine, float colliderRadius, float xSpeed, float ySpeed, int life) {
        super(gameEngine, R.drawable.enemigo1, colliderRadius, xSpeed, ySpeed, life);
        timeBullets = 0;
        initBulletPool(gameEngine);
    }



    @Override
    public void Init(int xPos, int yPos) {
        this.positionX = xPos;
        this.positionY = yPos;
        life = 1;
    }

    @Override
    public void initBulletPool(GameEngine gameEngine) {
        bullets = new ArrayList<>();
        List<Collider.CollideLayer> l = new ArrayList<>();
        l.add(Collider.CollideLayer.Player);
        for(int i = 0; i<MAX_BULLETS_EYE;i++){
            Bullet b = new Bullet(gameEngine,1,0,R.drawable.proyectilenemigo64x64smooth);
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
            c.init(this,this.positionX+ this.imageWidth/2,this.positionY+this.imageHeight/2);
            gameEngine.addGameObject(c);
            timeBullets = 3000;

        }else  {
            timeBullets-=elapsedMillis;
        }
    }
}
