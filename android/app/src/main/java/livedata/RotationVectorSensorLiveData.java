package livedata;

import android.content.Context;

import androidx.lifecycle.LiveData;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import sensor.RotationVectorSensor;
import data.SensorSample;

public class RotationVectorSensorLiveData extends LiveData<SensorSample> {
    private final RotationVectorSensor sensor;
    private CompositeDisposable compositeDisposable;
    private final Context context;
    public RotationVectorSensorLiveData(Context context) {
        this.context = context;
        //this.sensor = new SensorListener(context);
        this.sensor = new RotationVectorSensor(context);
    }
    @Override
    protected void onActive() {
        this.compositeDisposable = new CompositeDisposable();
        this.sensor.getPublishSubject().subscribe(new Observer<SensorSample>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }
            @Override
            public void onNext(SensorSample values) {
//                if(averagingFilter != null) {
//                    setValue(averagingFilter.filter(values));
//                } else {
                setValue(values);
//                }
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onComplete() {
            }
        });
        this.sensor.onStart();
    }
    @Override
    protected void onInactive() {
        this.compositeDisposable.dispose();
        this.sensor.onStop();
    }

}
