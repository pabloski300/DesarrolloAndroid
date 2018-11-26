package dadm.scaffold.engine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.math.MathUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dadm.scaffold.ScaffoldActivity;
import dadm.scaffold.counter.GameFragment;
import dadm.scaffold.counter.ScoreFragment;
import dadm.scaffold.space.Enemy;
import dadm.scaffold.space.EnemyEye;
import dadm.scaffold.space.EnemyMouth;
import dadm.scaffold.space.Meteorito;
import dadm.scaffold.space.PowerUp;

public class GameManager extends GameObject {

    protected int Increments;
    protected long TimeBetweenIncrements;
    protected long ActualTimeToIncrement;
    protected List<Enemy> EnemyInLevelEyes;
    protected List<Enemy> EnemyInLevelMouths;
    protected List<Meteorito> MeteorInLevel;
    protected long TimeBetwenEnemieEyes;
    protected long ActualTimeBetwenEnemieEyes;
    protected int enemyEyeHealth;
    protected int numEnemieEyesAtTime;
    protected float enemyEyeSpeed;
    protected long TimeBetwenEnemieMouths;
    protected long ActualTimeBetwenEnemieMouths;
    protected int enemyMouthHealth;
    protected int numEnemieMouthsAtTime;
    protected float enemyMouthSpeed;
    protected long TimeBetwenMeteors;
    protected long ActualTimeBetwenMeteors;
    protected Random r;
    public static GameManager ActualManager;
    public static int score;
    public static int lifes;
    public static int bombs;
    public static int powerTime;
    Paint paint;
    public GameManager(GameEngine engine){
        paint = new Paint();
        score = 0;
        lifes = 0;
        bombs = 0;
        r = new Random();
        EnemyInLevelEyes = new ArrayList<>();
        EnemyInLevelMouths = new ArrayList<>();
        for(int i=0;i<40; i++) {
            EnemyInLevelEyes.add(new EnemyEye(engine, 0, 0, 0, 1));
        }
        for(int i=0;i<40; i++) {
            EnemyInLevelMouths.add(new EnemyMouth(engine, 0, -1, 0, 1));
        }
        MeteorInLevel = new ArrayList<>();
        MeteorInLevel.add(new Meteorito(engine,0,-1,0,1));
        TimeBetwenEnemieEyes = 2000;
        enemyEyeHealth = 2;
        numEnemieEyesAtTime = 2;
        enemyEyeSpeed = -1;
        ActualTimeBetwenEnemieEyes = 0;
        TimeBetwenEnemieMouths = 3000;
        enemyMouthHealth = 3;
        numEnemieMouthsAtTime = 1;
        enemyMouthSpeed = -2;
        ActualTimeBetwenEnemieMouths = 0;
        TimeBetwenMeteors = 10000;
        ActualTimeBetwenMeteors = 0;
        TimeBetweenIncrements = 15000;
        Increments = 5;
        ActualTimeToIncrement = 0;
        ActualManager = this;
    }


    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        if(ActualTimeBetwenEnemieEyes >= TimeBetwenEnemieEyes && !EnemyInLevelEyes.isEmpty()){
            for(int i= 0; i< numEnemieEyesAtTime; i++) {
                Enemy e = EnemyInLevelEyes.remove(r.nextInt(EnemyInLevelEyes.size()));
                int h = GameFragment.theGameEngine.height;
                int posy = MathUtils.clamp(r.nextInt(h), 64 * (int) gameEngine.pixelFactor, h - (64 * (int) gameEngine.pixelFactor));
                int posx = GameFragment.theGameEngine.width + (128 * (int) gameEngine.pixelFactor);
                e.Init(posx, posy, enemyEyeHealth,enemyEyeSpeed);
                GameFragment.theGameEngine.addGameObject(e);
                ActualTimeBetwenEnemieEyes = 0;
            }
        }else {
            ActualTimeBetwenEnemieEyes+=elapsedMillis;
        }

        if(ActualTimeBetwenEnemieMouths >= TimeBetwenEnemieMouths && !EnemyInLevelMouths.isEmpty()){
            for(int i= 0; i< numEnemieMouthsAtTime; i++) {
                Enemy e = EnemyInLevelMouths.remove(r.nextInt(EnemyInLevelMouths.size()));
                int h = GameFragment.theGameEngine.height;
                int posy = MathUtils.clamp(r.nextInt(h), 210 * (int) gameEngine.pixelFactor, h - (210 * (int) gameEngine.pixelFactor));
                int posx = GameFragment.theGameEngine.width + (128 * (int) gameEngine.pixelFactor);
                e.Init(posx, posy, enemyMouthHealth,enemyMouthSpeed);
                GameFragment.theGameEngine.addGameObject(e);
                ActualTimeBetwenEnemieMouths = 0;
            }
        }else {
            ActualTimeBetwenEnemieMouths+=elapsedMillis;
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

        if(powerTime > 0){
            powerTime -= elapsedMillis;
        }else{
            powerTime = 0;
        }

        if(ActualTimeToIncrement >= TimeBetweenIncrements && Increments>0){
            Increments--;
            enemyMouthHealth ++;
            enemyEyeHealth++;
            enemyMouthSpeed --;
            enemyEyeSpeed --;
            TimeBetwenEnemieMouths -= 250;
            TimeBetwenEnemieEyes -= 250;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.WHITE);
        paint.setTextSize(45);
        paint.setFakeBoldText(true);
        canvas.drawText("Puntos :" + score,800,80,paint);
        canvas.drawText("x" + lifes,100,80,paint);
        canvas.drawText("x" + bombs,250,80,paint);
        canvas.drawText("Power Time:" + powerTime,400,80,paint);
    }

    public void RestoreEnemy(Enemy e){
        EnemyInLevelEyes.add(e);
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
