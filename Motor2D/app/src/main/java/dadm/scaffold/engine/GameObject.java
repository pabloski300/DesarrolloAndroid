package dadm.scaffold.engine;

import android.graphics.Canvas;

import dadm.scaffold.space.PowerUp;

public abstract class GameObject {

    protected Collider collider = null;

    protected Collider.CollideLayer layer;

    public Collider.CollideLayer getLayer() {
        return layer;
    }

    public void setLayer(Collider.CollideLayer layer) {
        this.layer = layer;
    }

    public Collider getCollider() {
        return collider;
    }

    public void setCollider(Collider collider) {
        this.collider = collider;
    }







    public abstract void startGame();

    public abstract void onUpdate(long elapsedMillis, GameEngine gameEngine);

    public abstract void onDraw(Canvas canvas);

    public final Runnable onAddedRunnable = new Runnable() {
        @Override
        public void run() {
            onAddedToGameUiThread();
        }
    };

    public void onAddedToGameUiThread(){
    }

    public final Runnable onRemovedRunnable = new Runnable() {
        @Override
        public void run() {
            onRemovedFromGameUiThread();
        }
    };

    public void onRemovedFromGameUiThread(){
    }


}
