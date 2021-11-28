package edu.cn.WuKongadminister;

import android.app.Application;

import com.ubtrobot.mini.SDKInit;
import com.ubtrobot.mini.properties.sdk.Path;
import com.ubtrobot.mini.properties.sdk.PropertiesApi;


public class App extends Application {

    public static final String DEBUG_TAG = "API_TAG";
    @Override
    public void onCreate() {
        super.onCreate();
        PropertiesApi.setRootPath(Path.DIR_MINI_FILES_SDCARD_ROOT);
        SDKInit.initialize(this);
    }


}

