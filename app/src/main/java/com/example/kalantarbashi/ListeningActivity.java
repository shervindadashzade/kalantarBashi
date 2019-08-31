package com.example.kalantarbashi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kalantarbashi.Core.Account;
import com.example.kalantarbashi.Core.GameResult;
import com.example.kalantarbashi.Core.ListeningThread;
import com.example.kalantarbashi.Core.ShootInfo;

import java.io.DataOutputStream;
import java.util.Random;

public class ListeningActivity extends AppCompatActivity {
    public static Account account=new Account("","",null);
    public static boolean isShooted = false;
    public static boolean isRecieved = false;
    int playcount = 1;
    MediaPlayer mediaPlayer;
    MediaPlayer shoot;
    TextView pitchT;
    SensorManager sensorManager;
    ShootInfo shootInfo = new ShootInfo();
    boolean shootPhase = false;

   SensorListener sensorListener = new SensorListener() {
       @Override
       public void onSensorChanged(int i, float[] floats) {
           float pitch  = floats[2];
           shootInfo.setPitch(pitch);
           pitchT.setText(Float.toString(pitch));
       }

       @Override
       public void onAccuracyChanged(int i, int i1) {

       }
   };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(shootPhase){
            if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
                try {
                    shootInfo.stop();
                    LandPageActivity.myAccount.setShootInfo(shootInfo);
                    isShooted = true;
                    Toast.makeText(this,LandPageActivity.myAccount.toString(),Toast.LENGTH_SHORT).show();
                    DataOutputStream dos = new DataOutputStream(account.getSocket().getOutputStream());
                    dos.writeUTF(LandPageActivity.myAccount.toString());
                    dos.flush();
                    dos.close();
                    if(isRecieved){
                        GoToResult();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening);
        isShooted = false;
        isRecieved = false;

        ListeningThread listeningThread = new ListeningThread(this,account.getSocket());
        listeningThread.execute();
        pitchT = (TextView)findViewById(R.id.pitch);
        shoot = MediaPlayer.create(ListeningActivity.this,R.raw.shoot);
        mediaPlayer = MediaPlayer.create(this,R.raw.ready);
        final Random rnd = new Random();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.start();
            }
        }, 1000);


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                    int random = rnd.nextInt(45)+2;
                    float randomtime = random/10;
                    shootPhase = true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            shoot.start();
                            sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
                            sensorManager.registerListener(sensorListener,SensorManager.SENSOR_ORIENTATION,SensorManager.SENSOR_DELAY_NORMAL);
                        }
                    }, (int) randomtime * 1000);
                    playcount++;
            }
        });


    }

    public void GoToResult(){
        ResultActivity.rival = account;
        ResultActivity.me = LandPageActivity.myAccount;
        startActivity(new Intent(ListeningActivity.this,ResultActivity.class));
    }

}
