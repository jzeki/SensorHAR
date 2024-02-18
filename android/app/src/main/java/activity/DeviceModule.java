package activity;

import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.Timer;
import java.util.TimerTask;

import model.InterpreterStorage;

public class DeviceModule extends ReactContextBaseJavaModule {
    Timer timer;
    ReactApplicationContext reactContext;
    int i;
    long time;
    public DeviceModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "DeviceModule";
    }

    public void startTimer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sendStatusEvent();
            }
        }, 2000, 1000);
    }
    public void sendStatusEvent() {
        if (!this.reactContext.hasActiveCatalystInstance()) {
            return;
        }
        InterpreterStorage.getInstance().predict();
        WritableMap payload = Arguments.createMap();
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