package sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import io.reactivex.subjects.PublishSubject;

//import pas.HttpRequest;

public class RotationVectorSensor {
    private SensorManager sensorManager;
    private SimpleSensorListener listener;
    private PublishSubject<SensorSample> publishSubject;
    private SensorSample output = new SensorSample();
    //private float[] rotation = new float[3];
    private float startTime = 0;
    private int count = 0;

    //private float[] angles = new float[3];

    public RotationVectorSensor(Context context){
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
                sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
                SensorManager.SENSOR_DELAY_FASTEST);

    }
    public PublishSubject<SensorSample> getPublishSubject() {
        return publishSubject;
    }
//    private void processRotationVector(float[] rotation) {
//        System.arraycopy(rotation, 0, this.angles, 0, this.angles.length);
//    }
    private class SimpleSensorListener implements SensorEventListener {
        int i =0;
        private float CONVERSIONCONSTANT = SensorManager.GRAVITY_EARTH;
        private float radToDeg = 57.2958f;

        @Override
        public void onSensorChanged(SensorEvent event) {
            //Log.i("update...", String.valueOf("gdrfgg"));
            System.out.println("rotation update..");
            switch (event.sensor.getType()) {

                case Sensor.TYPE_ROTATION_VECTOR:
                    float[] angles = new float[3];

                    output.setX(event.values[0]);
                    output.setY(event.values[1]);
                    output.setZ(event.values[2]);
                    output.setT(event.timestamp);
                    output.setFrequency(calculateSensorFrequency());
                    publish();
                    break;

//                    processRotationVector(event.values);
//                    setOutput(angles);
                    //When it returns, the array values are as follows:

//                    values[0]: Azimuth, angle of rotation about the -z axis. This value represents the angle between the device's y axis and the magnetic north pole. When facing north, this angle is 0, when facing south, this angle is π. Likewise, when facing east, this angle is π/2, and when facing west, this angle is -π/2. The range of values is -π to π.
//                    values[1]: Pitch, angle of rotation about the x axis. This value represents the angle between a plane parallel to the device's screen and a plane parallel to the ground. Assuming that the bottom edge of the device faces the user and that the screen is face-up, tilting the top edge of the device toward the ground creates a positive pitch angle. The range of values is -π/2 to π/2.
//                    values[2]: Roll, angle of rotation about the y axis. This value represents the angle between a plane perpendicular to the device's screen and a plane perpendicular to the ground. Assuming that the bottom edge of the device faces the user and that the screen is face-up, tilting the left edge of the device toward the ground creates a positive roll angle. The range of values is -π to π.
                    //break;


            }



        }

        @Override
        public void onAccuracyChanged (Sensor sensor, int accuracy){
        }

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

    private void publish(){
        publishSubject.onNext(output);
    }
}
