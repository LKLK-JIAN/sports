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

    public static BaseApplication getAppContext(){
        return mAppContext;
    }
    /**
     * 初始化Log打印配置
     */
    private void initLog(){


    }
}
