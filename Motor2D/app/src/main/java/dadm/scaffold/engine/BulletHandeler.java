package dadm.scaffold.engine;

import dadm.scaffold.space.Bullet;

public interface BulletHandeler {

    void initBulletPool(GameEngine gameEngine);
    Bullet getBullet();
    void releaseBullet(Bullet bullet);



}
