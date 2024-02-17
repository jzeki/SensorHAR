package activity;


import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;


import viewmodel.SensorViewModel;

public class ActivityHolder {
    private static FragmentActivity ctx;
    private static SensorViewModel accModel;



    public static FragmentActivity getCtx() {
        return ctx;
    }

    public static void setCtx(FragmentActivity ctx) {
        ActivityHolder.ctx = ctx;
    }

    public static SensorViewModel getAccModel() {
        return accModel;
    }

    public static void setAccModel(SensorViewModel accModel) {
        ActivityHolder.accModel = accModel;
    }
    public static void createAccModel(){
        accModel = ViewModelProviders.of(ctx).get(SensorViewModel.class);
    }
    public static void accModeRemoveObserver(){
        accModel.getLinearAccelerationSensorLiveData().removeObservers(ctx);
    }


}
