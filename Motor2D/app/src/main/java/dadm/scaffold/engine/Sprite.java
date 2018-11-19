package dadm.scaffold.engine;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.List;

public abstract class Sprite extends GameObject {

    protected double positionX;
    protected double positionY;
    protected double rotation;

    protected double pixelFactor;
    protected double pixelFactory;

    private final Bitmap bitmap;
    protected final int imageHeight;
    protected int imageWidth;


    public double getPositionX() {

        return positionX;
    }


    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    private final Matrix matrix = new Matrix();

    protected Sprite (GameEngine gameEngine, int drawableRes, boolean resizable) {
        Resources r = gameEngine.getContext().getResources();
        Drawable spriteDrawable = r.getDrawable(drawableRes);

        this.pixelFactor = gameEngine.pixelFactor;

        this.imageHeight = (int) (spriteDrawable.getIntrinsicHeight() * this.pixelFactor);
        this.imageWidth = (int) (spriteDrawable.getIntrinsicWidth() * this.pixelFactor);

        double factor = ((double)gameEngine.height/(double)gameEngine.width)/(9d/16d);
        if(resizable) {
            this.pixelFactory = this.pixelFactor * factor;
        }else{
            this.pixelFactory = this.pixelFactor;
        }

        this.bitmap = ((BitmapDrawable) spriteDrawable).getBitmap();
    }

    public abstract void OnCollision(Collider otherCollider);

    @Override
    public void onDraw(Canvas canvas) {
        if (positionX > canvas.getWidth()
                || positionY > canvas.getHeight()
                || positionX < - imageWidth
                || positionY < - imageHeight) {
            return;
        }
        matrix.reset();
        matrix.postScale((float) pixelFactor, (float) pixelFactory);
        matrix.postTranslate((float) positionX, (float) positionY);
        matrix.postRotate((float) rotation, (float) (positionX + imageWidth/2), (float) (positionY + imageHeight/2));
        canvas.drawBitmap(bitmap, matrix, null);

        if(collider != null){
                Paint p = new Paint();
                p.setColor(Color.GREEN);
                canvas.drawCircle((float)collider.getX(),(float)collider.getY(),collider.radius,p);
            }
    }

    public void CreateNewCollider(double radius, List<Collider.CollideLayer> r ,double offsetx,double offsety){

        this.collider = new Collider((float) (radius*this.pixelFactor),this,r,offsetx,offsety);

    }

}
