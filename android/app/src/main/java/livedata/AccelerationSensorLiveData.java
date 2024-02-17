package livedata;

import android.content.Context;

import androidx.lifecycle.LiveData;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import sensor.AccelerationSensor;
import sensor.SensorSample;

;

public class AccelerationSensorLiveData extends LiveData<SensorSample> {
    private AccelerationSensor sensor;
    private CompositeDisposable compositeDisposable;
    private Context context;
    public AccelerationSensorLiveData(Context context) {
        this.context = context;
        //this.sensor = new SensorListener(context);
        this.sensor = new AccelerationSensor(context);
    }

    @Override
    protected void onActive() {
        this.compositeDisposable = new CompositeDisposable();
        SensorObserver so = new SensorObserver();
        this.sensor.getPublishSubject().subscribe(so);
//        this.sensor.getPublishSubject().subscribe(new Observer<float[]>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                compositeDisposable.add(d);
//            }
//
//            @Override
//            public void onNext(float[] values) {
////                if(averagingFilter != null) {
////                    setValue(averagingFilter.filter(values));
////                } else {
//                    setValue(values);
////                }
//            }
//
//            @Override
//            public void onError(Throwable e) {}
//
//            @Override
//            public void onComplete() {}
//        });
        this.sensor.onStart();
    }
    @Override
    protected void onInactive() {
        this.compositeDisposable.dispose();
        this.sensor.onStop();
    }

    private class SensorObserver  implements Observer<SensorSample>{
        private CompositeDisposable compositeDisposable;

        public SensorObserver(){
            this.compositeDisposable = new CompositeDisposable();
        }
        @Override
        public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
        }

        @Override
        public void onNext(SensorSample sample) {
//                if(averagingFilter != null) {
//                    setValue(averagingFilter.filter(values));
//                } else {
            setValue(sample);
//                }
        }

        @Override
        public void onError(Throwable e) {}

        @Override
        public void onComplete() {}

    }
}
