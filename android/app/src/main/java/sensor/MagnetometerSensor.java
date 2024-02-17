package sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import io.reactivex.subjects.PublishSubject;

//import pas.HttpRequest;

public class MagnetometerSensor {
    private SensorManager sensorManager;
    private SimpleSensorListener listener;
    private PublishSubject<SensorSample> publishSubject;
    private SensorSample output = new SensorSample();
    private float[] rotation = new float[3];
    private float startTime = 0;
    private int count = 0;

    private float[] magnetic = new float[3];

    public MagnetometerSensor(Context context){
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.publishSubject = PublishSubject.create();
        this.listener = new SimpleSensorListener();


    }
    public void onStart() {
//        startTime = 0;
//        count = 0;
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
//    private void processMagnetic(float[] magnetic) {
//        System.arraycopy(magnetic, 0, this.magnetic, 0, this.magnetic.length);
//    }
    private class SimpleSensorListener implements SensorEventListener {
        int i =0;
        private float CONVERSIONCONSTANT = SensorManager.GRAVITY_EARTH;
        private float radToDeg = 57.2958f;


        @Override
        public void onSensorChanged(SensorEvent event) {
            //Log.i("update...", String.valueOf("gdrfgg"));
            System.out.println("magnet update..");
            switch (event.sensor.getType()) {

                case Sensor.TYPE_MAGNETIC_FIELD:
                    output.setX(event.values[0]);
                    output.setY(event.values[1]);
                    output.setZ(event.values[2]);
                    output.setT(event.timestamp);
                    output.setFrequency(calculateSensorFrequency());
                    publish();
                    break;



            }



        }

        @Override
        public void onAccuracyChanged (Sensor sensor, int accuracy){
        }

        private void publish(){
            publishSubject.onNext(output);
        }
    }

//
//    private void setOutput(float[] value) {
//        System.arraycopy(value, 0, output, 0, value.length);
//        output[3] = calculateSensorFrequency();
//        publishSubject.onNext(output);
//    }
    private float calculateSensorFrequency() {
        // Initialize the start time.
        if (startTime == 0) {
            startTime = System.nanoTime();
        }

        long timestamp = System.nanoTime();

        // Find the sample period (between updates) and convert from
        // nanoseconds to seconds. Note that the sensor delivery rates can
        // individually vary by a relatively large time frame, so we use an
        // averaging technique with the number of sensor updates to
        // determine the delivery rate.

        return (timestamp - startTime)==0?0:(count++ / ((timestamp - startTime) / 1000000000.0f));
    }
}
