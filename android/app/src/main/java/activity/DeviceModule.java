package activity;

import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.Timer;
import java.util.TimerTask;

import model.InterpreterStorage;
import sensor.SensorDataStorage;
import sensor.SensorSample;
import sensor.SensorsSample;;

public class DeviceModule extends ReactContextBaseJavaModule{
    SensorSample acc;
    Timer timer;
    ReactApplicationContext reactContext;
    int i;
    long time;




    public DeviceModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    //Mandatory function getName that specifies the module name
    @Override
    public String getName() {
        return "DeviceModule";
    }

    //Custom function that we are going to export to JS
    @ReactMethod
    public void getDeviceName(Callback cb) {
        try {
            ///cb.invoke(null, android.os.Build.MODEL);
           //cb.invoke(null, String.valueOf(acc.getX()));
            //sendEvent();
        } catch (Exception e) {
            cb.invoke(e.toString(), null);
        }


    }
    @ReactMethod
    public void startSending(Callback cb) {
        System.out.println("start...");
        timer = new Timer();
        startTimer();
        try {
            ///cb.invoke(null, android.os.Build.MODEL);
            //cb.invoke(null, String.valueOf(acc.getX()));
            //sendEvent();
        } catch (Exception e) {
            cb.invoke(e.toString(), null);
        }


    }
    public void startTimer(){
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //System.out.println("speak..." + results);
                sendStatusEvent();
            }
        }, 2000, 1000);
    }
    public void sendStatusEvent(){
        if(!this.reactContext.hasActiveCatalystInstance()) {
            return;
        }
        InterpreterStorage.getInstance().predict();
        WritableMap payload = Arguments.createMap();
        // Put data to map
        //String[] array = new String[] {"John", "Mary", "Bob"};
        payload.putString("status", InterpreterStorage.getInstance().getStatus());

        this.reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("onStatus", payload);
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public void startEvent(String name, String location) {
        Log.i("CalendarModule", "Create event called with name: " + name
                + " and location: " + location);
    }

}