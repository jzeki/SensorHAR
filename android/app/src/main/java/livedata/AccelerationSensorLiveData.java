package livedata;

import android.content.Context;

import androidx.lifecycle.LiveData;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import sensor.AccelerationSensor;
import data.SensorSample;

public class AccelerationSensorLiveData extends LiveData<SensorSample> {
    private final AccelerationSensor sensor;
    private final Context context;
    private CompositeDisposable compositeDisposable;
    public AccelerationSensorLiveData(Context context) {
        this.context = context;
        this.sensor = new AccelerationSensor(context);
    }
    @Override
    protected void onActive() {
        this.compositeDisposable = new CompositeDisposable();
        SensorObserver so = new SensorObserver();
        this.sensor.getPublishSubject().subscribe(so);
        this.sensor.onStart();
    }
    @Override
    protected void onInactive() {
        this.compositeDisposable.dispose();
        this.sensor.onStop();
    }
    private class SensorObserver implements Observer<SensorSample> {
        private final CompositeDisposable compositeDisposable;
        public SensorObserver() {
            this.compositeDisposable = new CompositeDisposable();
        }
        @Override
        public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
        }
        @Override
        public void onNext(SensorSample sample) {
            setValue(sample);
        }
        @Override
        public void onError(Throwable e) {
        }
        @Override
        public void onComplete() {
        }
    }
}
