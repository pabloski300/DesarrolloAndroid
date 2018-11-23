package dadm.scaffold.engine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.math.MathUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;
import dadm.scaffold.counter.GameFragment;
import dadm.scaffold.counter.ScoreFragment;
import dadm.scaffold.space.Enemy;
import dadm.scaffold.space.EnemyEye;
import dadm.scaffold.space.Meteorito;
import dadm.scaffold.space.PowerUp;

public class GameManager extends GameObject {

    protected List<Enemy> EnemyInLevel;
    protected List<Meteorito> MeteorInLevel;
    protected long TimeBetwenEnemies;
    protected long ActualTimeBetwenEnemies;
    protected long TimeBetwenMeteors;
    protected long ActualTimeBetwenMeteors;
    protected Random r;
    public static GameManager ActualManager;
    public static int score;
    Paint paint;
    public GameManager(GameEngine engine){
        paint = new Paint();
        score = 0;
        r = new Random();
        EnemyInLevel = new ArrayList<>();
        EnemyInLevel.add(new EnemyEye(engine,0,0,0,1));
        EnemyInLevel.add(new EnemyEye(engine,0,-1,0,1));
        MeteorInLevel = new ArrayList<>();
        MeteorInLevel.add(new Meteorito(engine,0,-1,0,1));
        TimeBetwenEnemies = 2000;
        ActualTimeBetwenEnemies = 0;
        TimeBetwenMeteors = 10000;
        ActualTimeBetwenMeteors = 0;
        ActualManager = this;
    }


    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        if(ActualTimeBetwenEnemies >= TimeBetwenEnemies && !EnemyInLevel.isEmpty()){
           Enemy e = EnemyInLevel.remove(r.nextInt(EnemyInLevel.size()));
           int h = GameFragment.theGameEngine.height;
           int posy = MathUtils.clamp(r.nextInt(h),128*(int)gameEngine.pixelFactor, h-(128*(int)gameEngine.pixelFactor));
           int posx = GameFragment.theGameEngine.width+(128*(int)gameEngine.pixelFactor);
           e.Init(posx,posy);
           GameFragment.theGameEngine.addGameObject(e);
           ActualTimeBetwenEnemies = 0;

        }else {
            ActualTimeBetwenEnemies+=elapsedMillis;
        }

        if(ActualTimeBetwenMeteors >= TimeBetwenMeteors && !MeteorInLevel.isEmpty()){
            Meteorito m = MeteorInLevel.remove(r.nextInt(MeteorInLevel.size()));
            int h = GameFragment.theGameEngine.height;
            int posy = MathUtils.clamp(r.nextInt(h),128*(int)gameEngine.pixelFactor, h-(128*(int)gameEngine.pixelFactor));
            int posx = GameFragment.theGameEngine.width+(256*(int)gameEngine.pixelFactor);
            m.Init(posx,posy);
            GameFragment.theGameEngine.addGameObject(m);
            ActualTimeBetwenMeteors = 0;

        }else {
            ActualTimeBetwenMeteors+=elapsedMillis;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.WHITE);
        paint.setTextSize(100);
        canvas.drawText("Puntos :" + score,0,100,paint);
    }

    public void RestoreEnemy(Enemy e){
        EnemyInLevel.add(e);
    }

    public void RestoreMeteorite(Meteorito m){
        MeteorInLevel.add(m);
    }

    public void Lose(){
        GameFragment.theGameEngine.stopGame();
        ((ScaffoldActivity)GameFragment.actualGameFragment.getActivity()).navigateToFragment(new ScoreFragment());

    }

    public void Win(){

    }


    public void RestorePowerUp(PowerUp powerUp) {
    }
}
