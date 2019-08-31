package com.example.kalantarbashi;

import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SensorChecker extends AppCompatActivity {
    TextView angel;
    ImageView gun;
    private final SensorListener sl = new SensorListener() {
        @Override
        public void onSensorChanged(int i, float[] floats) {
            float pitch  = floats[2];
            angel.setText(Float.toString(pitch));
        }

        @Override
        public void onAccuracyChanged(int i, int i1) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_checker);
        gun = (ImageView)findViewById(R.id.gun);
        angel = (TextView)findViewById(R.id.angel);
        SensorManager sm;
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        sm.registerListener(sl,SensorManager.SENSOR_ORIENTATION,SensorManager.SENSOR_DELAY_NORMAL);
    }
}
