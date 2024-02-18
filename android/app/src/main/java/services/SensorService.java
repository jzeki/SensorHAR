package services;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.Observer;

import java.util.concurrent.atomic.AtomicLong;

import livedata.AccelerationSensorLiveData;
import livedata.GyroscopeSensorLiveData;
import livedata.MagnetometerSensorLiveData;
import livedata.RotationVectorSensorLiveData;
import model.InterpreterStorage;
import data.SensorSample;
import data.SensorsSample;

public class SensorService extends LifecycleService {
    private static final String TAG = "Sensor Service";
    //private MessageSent messageSent;
    private static final AtomicLong idCounter = new AtomicLong();
    //private SensorsSample firstSample = new SensorsSample();
    AccelerationSensorLiveData accelerationSensorLiveData;
    GyroscopeSensorLiveData gyroscopeSensorLiveData;
    MagnetometerSensorLiveData magnetometerSensorLiveData;
    RotationVectorSensorLiveData rotationVectorSensorLiveData;
    int i = 0;
    SensorsSample.Builder sensorsBuilder = new SensorsSample.Builder();
    private SensorsSample sensorsSample; // = new SensorsSample();
    public SensorService() {
        sensorsSample = sensorsBuilder.build();

    }
    @Override
    public void onDestroy() {
        // The service is no longer used and is being destroyed
        accelerationSensorLiveData.removeObservers(this);
        gyroscopeSensorLiveData.removeObservers(this);
        magnetometerSensorLiveData.removeObservers(this);
        rotationVectorSensorLiveData.removeObservers(this);
        super.onDestroy();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("start command..");
        super.onStartCommand(intent, flags, startId);
        accelerationSensorLiveData = new AccelerationSensorLiveData(this);
        gyroscopeSensorLiveData = new GyroscopeSensorLiveData(this);
        magnetometerSensorLiveData = new MagnetometerSensorLiveData(this);
        rotationVectorSensorLiveData = new RotationVectorSensorLiveData(this);
        accelerationSensorLiveData.observe(this, new Observer<SensorSample>() {
            @Override
            public void onChanged(SensorSample sample) {
                System.out.println("accel service..");
                newAccSensorsSample(sample);
                InterpreterStorage.getInstance().addSample(sensorsSample);
            }

        });
        gyroscopeSensorLiveData.observe(this, new Observer<SensorSample>() {
            @Override
            public void onChanged(@Nullable SensorSample sample) {
                newGyroSensorsSample(sample);
            }

        });
        magnetometerSensorLiveData.observe(this, new Observer<SensorSample>() {
            @Override
            public void onChanged(@Nullable SensorSample sample) {
                //Log.i("magnet data service: ", String.valueOf("send"));
                newMagnetSensorsSample(sample);

            }
        });
        rotationVectorSensorLiveData.observe(this, new Observer<SensorSample>() {
            @Override
            public void onChanged(@Nullable SensorSample sample) {
                //Log.i("rotation vector data  service: ", String.valueOf("send"));
                newOrientationSensorsSample(sample);

            }
        });
        return START_STICKY;
    }
    public SensorSample cloneSensorSample(SensorSample sample) {
        SensorSample new_sample = new SensorSample();
        new_sample.setX(sample.getX());
        new_sample.setY(sample.getY());
        new_sample.setZ(sample.getZ());
        new_sample.setT(sample.getT());
        //new_sample.setFrequency(sample.getFrequency());
        return new_sample;
    }
    public SensorsSample cloneSensorsSample(SensorsSample sample) {
        SensorsSample new_sample = sensorsBuilder.build();
        new_sample.setA(sample.getA());
        new_sample.setG(sample.getG());
        new_sample.setM(sample.getM());
        new_sample.setT(sample.getT());
        return new_sample;
    }
    public void newAccSensorsSample(SensorSample sample) {
        if (sensorsSample.getA() == null) {
            sensorsSample = sensorsBuilder.a(cloneSensorSample(sample)).build();
        } else {
            if (Float.compare(sensorsSample.getA().getX(), sample.getX()) != 0) {
                sensorsSample.setT(System.currentTimeMillis());
                sensorsSample = sensorsBuilder.a(cloneSensorSample(sample)).g(null).m(null).o(null).build();
                // sensorsSample = sensorsBuilder.a(cloneSensorSample(sample)).build();
            }

        }
        if ((sensorsSample.getA() != null) && (sensorsSample.getG() != null && (sensorsSample.getM() != null) && (sensorsSample.getO() != null))) {
            sensorsSample.setT(System.currentTimeMillis());
            sensorsSample = sensorsBuilder.a(null).g(null).m(null).o(null).build();
        }

    }
    public void newGyroSensorsSample(SensorSample sample) {
        if (sensorsSample.getG() == null) {
            sensorsSample = sensorsBuilder.g(cloneSensorSample(sample)).build();
        } else {
            if (Float.compare(sensorsSample.getG().getX(), sample.getX()) != 0) {
                sensorsSample.setT(System.currentTimeMillis());
                sensorsSample = sensorsBuilder.a(null).g(cloneSensorSample(sample)).m(null).o(null).build();
                //sensorsSample = sensorsBuilder.g(cloneSensorSample(sample)).build();
                // return;
            }

        }
        if ((sensorsSample.getA() != null) && (sensorsSample.getG() != null && (sensorsSample.getM() != null) && (sensorsSample.getO() != null))) {
            sensorsSample.setT(System.currentTimeMillis());
            sensorsSample = sensorsBuilder.a(null).g(null).m(null).o(null).build();
        }
    }
    public void newMagnetSensorsSample(SensorSample sample) {
        if (sensorsSample.getM() == null) {
            sensorsSample = sensorsBuilder.m(cloneSensorSample(sample)).build();
        } else {
            if (Float.compare(sensorsSample.getM().getX(), sample.getX()) != 0) {
                sensorsSample.setT(System.currentTimeMillis());
                sensorsSample = sensorsBuilder.a(null).g(null).m(cloneSensorSample(sample)).o(null).build();
                //sensorsSample = sensorsBuilder.m(cloneSensorSample(sample)).build();
            }

        }
        if ((sensorsSample.getA() != null) && (sensorsSample.getG() != null && (sensorsSample.getM() != null) && (sensorsSample.getO() != null))) {
            sensorsSample.setT(System.currentTimeMillis());
            sensorsSample = sensorsBuilder.a(null).g(null).m(null).o(null).build();
        }
    }
    public void newOrientationSensorsSample(SensorSample sample) {
        if (sensorsSample.getO() == null) {
            sensorsSample = sensorsBuilder.o(cloneSensorSample(sample)).build();
        } else {
            if (Float.compare(sensorsSample.getO().getX(), sample.getX()) != 0) {
                sensorsSample.setT(System.currentTimeMillis());
                sensorsSample = sensorsBuilder.a(null).g(null).m(null).o(cloneSensorSample(sample)).build();
                //sensorsSample = sensorsBuilder.o(cloneSensorSample(sample)).build();
            }

        }
        if ((sensorsSample.getA() != null) && (sensorsSample.getG() != null && (sensorsSample.getM() != null) && (sensorsSample.getO() != null))) {
            sensorsSample.setT(System.currentTimeMillis());
            sensorsSample = sensorsBuilder.a(null).g(null).m(null).o(null).build();
        }
    }

}
