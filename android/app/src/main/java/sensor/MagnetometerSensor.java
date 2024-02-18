package sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import data.SensorSample;
import io.reactivex.subjects.PublishSubject;

public class MagnetometerSensor {
    private final SensorManager sensorManager;
    private final SimpleSensorListener listener;
    private final PublishSubject<SensorSample> publishSubject;
    private final SensorSample output = new SensorSample();
    private float startTime = 0;
    private int count = 0;
    public MagnetometerSensor(Context context) {
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.publishSubject = PublishSubject.create();
        this.listener = new SimpleSensorListener();

    }
    public void onStart() {
        registerSensors();
    }
    public void onStop() {
        unregisterSensors();
    }
    private void unregisterSensors() {
        sensorManager.unregisterListener(listener);
    }
    private void registerSensors() {
        // Register for sensor updates.
        sensorManager.registerListener(listener,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_FASTEST);

    }
    public PublishSubject<SensorSample> getPublishSubject() {
        return publishSubject;
    }

    private float calculateSensorFrequency() {
        // Initialize the start time.
        if (startTime == 0) {
            startTime = System.nanoTime();
        }
        long timestamp = System.nanoTime();
        return (timestamp - startTime) == 0 ? 0 : (count++ / ((timestamp - startTime) / 1000000000.0f));
    }

    private class SimpleSensorListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                output.setX(event.values[0]);
                output.setY(event.values[1]);
                output.setZ(event.values[2]);
                output.setT(event.timestamp);
                output.setFrequency(calculateSensorFrequency());
                publish();
            }

        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
        private void publish() {
            publishSubject.onNext(output);
        }
    }
}
