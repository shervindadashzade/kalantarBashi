package com.example.kalantarbashi.Core;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class ShootInfo {
  float pitch;
  long time;

  private long millis1;
  private long millis2;
  public ShootInfo(){
      long millis1= Calendar.getInstance().getTimeInMillis();
      pitch = 0f;
  }

    public long getTime() {
        return time;
    }

    public void stop(){
        long millis2= Calendar.getInstance().getTimeInMillis();
        time = millis2-millis1;
    }
    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}
