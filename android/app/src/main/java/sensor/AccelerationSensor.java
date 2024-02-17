package sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import io.reactivex.subjects.PublishSubject;

public class AccelerationSensor {
    private SensorManager sensorManager;
    private SimpleSensorListener listener;
    private PublishSubject<SensorSample> publishSubject;
    private long[] acceleration = new long[5];
    private SensorSample output = new SensorSample();
    private float startTime = 0;
    private int count = 0;

    public AccelerationSensor(Context context){
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.publishSubject = PublishSubject.create();
        this.listener = new SimpleSensorListener();


    }
    public void onStart() {
        startTime = 0;
        count = 0;
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
        sensorManager.registerListener(listener, sensorManager
                        .getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);

//        // Register for sensor updates.
//        sensorManager.registerListener(listener, sensorManager
//                        .getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
//                SensorManager.SENSOR_DELAY_FASTEST);
//
//        // Register for sensor updates.
//        sensorManager.registerListener(listener,
//                sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
//                SensorManager.SENSOR_DELAY_FASTEST);
//
//        // Register for sensor updates.
//        sensorManager.registerListener(listener,
//                sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
//                SensorManager.SENSOR_DELAY_FASTEST);
    }
    public PublishSubject<SensorSample> getPublishSubject() {
        return publishSubject;
    }
    private class SimpleSensorListener implements SensorEventListener {
        int i =0;
        private float CONVERSIONCONSTANT = SensorManager.GRAVITY_EARTH;
        private float radToDeg = 57.2958f;

//        HttpRequest httprequest = new HttpRequest();
//        Gson gson = new Gson();
//        protected final MatrixF4x4 currentOrientationRotationMatrix = new MatrixF4x4();
//        long tripId;

        @Override
        public void onSensorChanged(SensorEvent event) {
            System.out.println("acc update..");

            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    //processAcceleration(event.values);
                    output.setX(event.values[0]);
                    output.setY(event.values[1]);
                    output.setZ(event.values[2]);
                    output.setT(event.timestamp);
                    output.setFrequency(calculateSensorFrequency());
                    publish();
                    //setOutput(acceleration);


//                    Log.i("x: ", String.valueOf(event.values[0]));
//                    Log.i("y: ", String.valueOf(event.values[1]));
//                    Log.i("z: ", String.valueOf(event.values[2]));
//                    Log.i("time: ", String.valueOf(event.timestamp));

                    i++;
                    break;



            }



        }

        @Override
        public void onAccuracyChanged (Sensor sensor, int accuracy){
        }

    }


    private void publish(){
        publishSubject.onNext(output);
    }

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
