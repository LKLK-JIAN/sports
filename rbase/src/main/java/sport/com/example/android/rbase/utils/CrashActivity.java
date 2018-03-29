package sport.com.example.android.rbase.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import sport.com.example.android.rbase.base.ActivityCollector;
import sport.com.example.android.rbase.base.IRBasePresenter;
import sport.com.example.android.rbase.base.RBaseActivity;

/**
 * Created by android on 2018/3/28.
 */

public class CrashActivity extends RBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @Override
    protected IRBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return 0;
    }

    public void crashClick(View v) {
        ActivityCollector.finishAll();
    }

    @Override
    protected int setFragmentContainerResId() {
        return 0;
    }
}
