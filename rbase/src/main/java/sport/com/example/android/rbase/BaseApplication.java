package sport.com.example.android.rbase;

import android.app.Application;

/**
 * Created by android on 2018/3/27.
 */

public class BaseApplication extends Application {

    protected static BaseApplication mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        onFastInit();
        mAppContext=this;

    }

    protected void onFastInit(){

    }
}
