package dadm.scaffold.engine;

import java.util.List;

public class Collider {

    public enum CollideLayer{
        Player,
        Enemy,
        Scenario,
        Bullet,
        Meteorite
    }

    public List<CollideLayer> collideLayers;
    public float radius;
    double  xpos;
    double  ypos;
    public Sprite Owner;

    public Collider(float radius,Sprite owner,List<CollideLayer> trueLayers,double offsetX,double offsetY){

        this.radius = radius;
        Owner = owner;
        collideLayers = trueLayers;
        xpos = offsetX;
        ypos = offsetY;

    }

    public void CheckCollide(Collider otherCollider){
        if(collideLayers.contains(otherCollider.Owner.getLayer())) {
            double newx = getX()-otherCollider.getX();
            double newy = getY()-otherCollider.getY();
            double distance = Math.sqrt(newx*newx+newy*newy);
            if (otherCollider.radius + this.radius >= distance){
               this.Owner.OnCollision(otherCollider);
            }
        }
    }

    public double getX(){
        return Owner.getPositionX()+xpos;
    }

    public double getY(){
        return Owner.getPositionY()+ypos;
    }


}
