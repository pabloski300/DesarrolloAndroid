package dadm.scaffold.space;

public class PoweUpInformation {

    public static final int DOUBLE_SHOOT = 0;
    public static final int TRIPLE_SHOOT = 1;
    public static final int BOMB = 2;

    public float time;
    public int type;


    PoweUpInformation(float time,int type){
        this.time = time;
        this.type = type;
    }
}
