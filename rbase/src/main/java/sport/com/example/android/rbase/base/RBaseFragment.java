package sport.com.example.android.rbase.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by android on 2018/3/27.
 */

public abstract class RBaseFragment<T extends IRBasePresenter,V extends ViewDataBinding>
        extends Fragment {

    protected View mView;
    protected Bundle saveInstanceState;
    private boolean isPrepare;
    private boolean isVisible;
    protected T mPresenter;
    //管理事件订阅
    protected CompositeDisposable compositeDisposable;
    protected ArrayMap<String,Disposable>  disposableMap;
    protected ConcurrentHashMap<String,String> mValueMap=new ConcurrentHashMap<>();
    protected V mBinding;
    protected ArrayMap<String ,String > mThemeColorMap;
    private boolean isFirstInit;
    protected RBaseActivity mContext;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mContext=(RBaseActivity) context;
        }catch(Throwable  e){
            throw new IllegalArgumentException("这个fragment的父Activity必须继承RBaseAcitivity");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.saveInstanceState=savedInstanceState;
        if(mBinding==null){
            mView=inflater.inflate(setFragmentLayoutRes(),null,false);
            mBinding= DataBindingUtil.inflate(inflater,setFragmentLayoutRes(),container,false);
            mPresenter=getPresenter();
            isPrepare=true;
            isFirstInit=true;
            isVisible=true;
            onVisible();

        }
        return mBinding.getRoot();
    }

    /**
     * 在这里实现Fragment数据的懒加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
         if(getUserVisibleHint()){
             isVisible=true;
             onVisible();
         }
         else{
             isVisible=false;
             onInvisible();
         }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void onVisible(){
        if(!isPrepare||!isVisible){
            return;
        }
        if(isFirstInit){
            initValueFromPrePage();
            initThemeAttrs();
            initView(saveInstanceState);
            isFirstInit=false;
        }else{
            onRepeatVisible();
        }
    }

    /**
     * 重复可见调用方法
     */
    private void onRepeatVisible() {

    }

    /**
     * 不可见的时候
     */
    private void onInvisible() {

    }


    /**
     * 设置当前Fragment的布局文件资源
     *
     * @return
     */
    protected abstract int setFragmentLayoutRes();

    /**
     * 初始化页面
     *
     * @param savedInstanceState
     */
    public abstract void initView(Bundle savedInstanceState);

    protected abstract T getPresenter();
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        onUnsubscribe();
        if (mPresenter != null) {
            mPresenter.onDestory();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 获取当前主题中的颜色
     *
     * @return
     */
    protected void initThemeAttrs() {
    }

    /**
     * 添加事件监听处理到 事件管理类
     *
     * @param disposable 上流事件
     */
    protected void addSubscription(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    /**
     * 添加事件监听处理到 事件管理类
     *
     * @param tag        标识符
     * @param disposable 上流事件
     */
    protected void addSubscription(String tag, Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        if (disposableMap == null) {
            disposableMap = new ArrayMap<>();
        }
        disposableMap.put(tag, disposable);
        compositeDisposable.add(disposable);
    }


    /**
     * RxJava取消注册，避免内存泄露
     * 取消以后就只能重新新建一个了
     */
    protected void onUnsubscribe() {
        if (compositeDisposable != null) {
            // Using clear will clear all, but can accept new disposable
//            compositeDisposable.clear();
            // Using dispose will clear all and set isDisposed = true, so it will not accept any new disposable
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
        if (disposableMap != null && disposableMap.size() > 0) {
            disposableMap.clear();
        }
    }

    /**
     * 根据标识符移除Disposable
     *
     * @param tags 标识符
     */
    protected void removeDisposableByTag(String... tags) {
        if (disposableMap != null && disposableMap.size() > 0) {
            for (String tag : tags) {
                if (disposableMap.containsKey(tag)) {
                    compositeDisposable.remove(disposableMap.get(tag));
                    disposableMap.remove(tag);
                }
            }
        }
    }

    /**
     * 从上个页面取得传递参数的值
     *
     * @param name
     * @return
     */
    public String getValueFromPrePage(String name) {
        return mValueMap.get(name);
    }

    /**
     * 将上个页面传递过来的参数值全部放到valueMap 中
     */
    public void initValueFromPrePage() {
        Bundle extBundle = getArguments() != null ? getArguments() : null;
        if (extBundle != null) {
            Iterator<String> it = extBundle.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                String value = extBundle.getString(key);
                mValueMap.put(key, value);
            }
        }

    }
}
