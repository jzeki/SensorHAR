package viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import livedata.AccelerationSensorLiveData;

public class SensorViewModel extends AndroidViewModel {
    private AccelerationSensorLiveData accelerationSensorLiveData;

    public SensorViewModel(@NonNull Application application) {
        super(application);
        this.accelerationSensorLiveData = new AccelerationSensorLiveData(application);
    }
    public AccelerationSensorLiveData getLinearAccelerationSensorLiveData() {
        return accelerationSensorLiveData;
    }

}
