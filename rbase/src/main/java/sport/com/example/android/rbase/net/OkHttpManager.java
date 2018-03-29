package sport.com.example.android.rbase.net;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sport.com.example.android.rbase.net.download.ProgressListener;

/**
 * Created by android on 2018/3/28.
 * OkHttpClient
 */

public class OkHttpManager {

    private OkHttpManager(){

    }
    public static OkHttpClient create(){
        return create(null);
    }
    public static OkHttpClient create(ProgressListener progressListener){

    }

    static class HttpCacheInterceptor implements Interceptor{
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request=chain.request();
            if(){}
            return null;
        }
    }
}
