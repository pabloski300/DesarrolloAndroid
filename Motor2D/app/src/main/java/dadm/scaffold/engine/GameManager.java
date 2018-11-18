package dadm.scaffold.engine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;
import dadm.scaffold.counter.GameFragment;
import dadm.scaffold.space.Enemy;
import dadm.scaffold.space.EnemyEye;

public class GameManager extends GameObject {

    protected List<Enemy> EnemyInLevel;
    protected long TimeBetwenEnemies;
    protected long ActualTime;
    protected Random r;
    public static GameManager ActualManager;
    public int score;
    Paint paint;
    public GameManager(GameEngine engine){
        paint = new Paint();
        score = 0;
        r = new Random();
        EnemyInLevel = new ArrayList<>();
        EnemyInLevel.add(new EnemyEye(engine,0,0,0,1));
        EnemyInLevel.add(new EnemyEye(engine,0,-1,0,1));
        TimeBetwenEnemies = 2000;
        ActualTime = 0;
        ActualManager = this;
    }


    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        if(ActualTime >= TimeBetwenEnemies && !EnemyInLevel.isEmpty()){
           Enemy e = EnemyInLevel.remove(r.nextInt(EnemyInLevel.size()));
           e.Init(GameFragment.theGameEngine.width-200,r.nextInt(GameFragment.theGameEngine.height-20));
           GameFragment.theGameEngine.addGameObject(e);
           ActualTime = 0;

        }else {
            ActualTime+=elapsedMillis;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        canvas.drawText("Puntos :" + score,0,100,paint);
    }

    public void RestoreEnemy(Enemy e){
        EnemyInLevel.add(e);
    }

    public void Lose(){
        GameFragment.theGameEngine.pauseGame();
        new AlertDialog.Builder(GameFragment.theGameEngine.getMainActivity())
                .setTitle(R.string.pause_dialog_title)
                .setMessage(R.string.pause_dialog_message)
                .setPositiveButton(R.string.resume, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        GameFragment.theGameEngine.stopGame();
                        ((ScaffoldActivity) GameFragment.theGameEngine.getMainActivity()).startGame();
                    }
                })
                .setNegativeButton(R.string.stop, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        GameFragment.theGameEngine.stopGame();
                        ((ScaffoldActivity) GameFragment.theGameEngine.getMainActivity()).navigateBack();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        GameFragment.theGameEngine.resumeGame();
                    }
                })
                .create()
                .show();
    }

    public void Win(){

    }
}
