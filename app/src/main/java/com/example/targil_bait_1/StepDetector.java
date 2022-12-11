package com.example.targil_bait_1;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;

public class StepDetector
{
    private SensorManager mSensorManager;
    private Sensor sensor;
    private GameManager gameManager;
    private ShapeableImageView[] game_IMG_space;

    int stepCounter = 2;
    long timeStamp = 0;

    public StepDetector(Context context/*, CallBack_steps _callBack_steps*/, GameManager gameManager, ShapeableImageView[] game_IMG_space) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.gameManager = gameManager;
        this.game_IMG_space = game_IMG_space;

        //this.callBack_steps = _callBack_steps;
    }

    public int getStepCounter() {
        return stepCounter;
    }

    public void startX() {
        mSensorManager.registerListener(sensorEventListenerForX, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopX() {
        mSensorManager.unregisterListener(sensorEventListenerForX);
    }

    public void startY() {
        mSensorManager.registerListener(sensorEventListenerForY, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopY() {
        mSensorManager.unregisterListener(sensorEventListenerForY);
    }

    private SensorEventListener sensorEventListenerForX = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            calculateX(x);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private SensorEventListener sensorEventListenerForY = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float y = event.values[1];
            calculateY(y);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void calculateX(float x)
    {
        if (x > 6.0)
        {
            if (System.currentTimeMillis() - timeStamp > 500)
            {
                timeStamp = System.currentTimeMillis();
                gameManager.moveShip(-1, game_IMG_space);
            }
        }

       if (x < -6.0)
       {
           if (System.currentTimeMillis() - timeStamp > 500)
           {
               timeStamp = System.currentTimeMillis();
               gameManager.moveShip(1, game_IMG_space);
           }
       }

    }

    private void calculateY(float y)
    {
        if (y < 0.0)
        {
            gameManager.setDelay(500);
            gameManager.displayToast("FAST MODE", Toast.LENGTH_SHORT);
            if (System.currentTimeMillis() - timeStamp > 500) {
                timeStamp = System.currentTimeMillis();
            }
        }

        if (y > 9.0)
        {
            gameManager.setDelay(1000);
            gameManager.displayToast("SLOW MODE", Toast.LENGTH_SHORT);
            if (System.currentTimeMillis() - timeStamp > 500) {
                timeStamp = System.currentTimeMillis();
            }
        }
    }
}
