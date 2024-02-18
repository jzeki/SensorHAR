package activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.firebaseproject.BuildConfig;

import java.util.Arrays;
import java.util.List;

import model.InterpreterStorage;
import services.SensorService;

public class ReactActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {
    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;
    private final DevicePackage pkg = new DevicePackage();
    private ReactActivityDelegate mDelegate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SoLoader.init(this, false);
        mReactRootView = new ReactRootView(this);
        List<ReactPackage> packages = getPackages();
        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setCurrentActivity(this)
                .setBundleAssetName("index.android.bundle")
                .setJSMainModulePath("index")
                .addPackages(packages)
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();
        mReactRootView.startReactApplication(mReactInstanceManager, "SensorHAR", null);
        setContentView(mReactRootView);
        mDelegate = createReactActivityDelegate();
        //this.startService(new Intent(this, SensorService.class));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(this, SensorService.class));
        } else {
            startService(new Intent(this, SensorService.class));
        }
        InterpreterStorage.getInstance().setContext(this);
    }
    /**
     * Called at construction time, override if you have a custom delegate implementation.
     */
    protected ReactActivityDelegate createReactActivityDelegate() {
        return new ReactActivityDelegate(this, getMainComponentName());
    }
    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     * e.g. "MoviesApp"
     */
    protected @Nullable String getMainComponentName() {
        return null;
    }
    // @Override
    protected List<ReactPackage> getPackages() {
        return Arrays.asList(
                new MainReactPackage(),
                pkg
        );
    }
    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostPause(this);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        //update();
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostResume(this, this);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostDestroy(this);
        }
        if (mReactRootView != null) {
            mReactRootView.unmountReactApplication();
        }
    }
    @Override
    public void onBackPressed() {
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
            mReactInstanceManager.showDevOptionsDialog();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mReactInstanceManager.onActivityResult(this, requestCode, resultCode, data);
    }
}