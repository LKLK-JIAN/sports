package sport.com.example.android.rbase.base;

import android.util.ArrayMap;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by android on 2018/3/27.
 */

public class RBasePresenter<T extends IRaseView> implements IRBasePresenter {

    protected T mView;
    //管理事件订阅
    protected CompositeDisposable  compositeDisposable;
    protected ArrayMap<String,Disposable>  disposableMap;

    @Override
    public void onDestory() {

    }

    @Override
    public void onStop() {

    }

    /**
    *添加事件监听处理到事件处理类
    * @param disposable
    */
    protected void addSubscription(Disposable disposable){
        if(compositeDisposable==null){
            compositeDisposable=new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }
    /**
     * 添加事件监听处理到事件处理类
     * @param tag    标识符
     * @parem disposable  上流事件
     */
    protected void addSubscription(String tag,Disposable disposable){
        if(compositeDisposable==null) {
            compositeDisposable=new CompositeDisposable();
        }
        if(disposableMap==null){
            disposableMap=new ArrayMap<>();
        }
        disposableMap.put(tag,disposable);
        compositeDisposable.add(disposable);
    }
    /**
     * 根据标识符移除Disposable
     * @param tags
     */
     protected void removeDisposableByTag(String...tags){
         if(disposableMap!=null&&disposableMap.size()>0){
             for(String tag:tags){
                 if(disposableMap.containsKey(tag)){
                     disposableMap.remove(tag);
                     compositeDisposable.remove(disposableMap.get(tag));
                     
                 }
             }
         }
     }
}
