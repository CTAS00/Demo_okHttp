import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import http.DownCallBack;
import http.HttpManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by CTAS on 2017/8/27.
 * 下载管理
 */
public class DownLoadManager {
    public final static int MAX_THREAD = 2;
    public final static int LOCAL_PROGRESS_SIZE = 1;
    private static DownLoadManager sManager;
    private Context context;
    private HashSet<DownloadTask> mHashSet = new HashSet<>();
    private DownLoadManager(){


    }
    public void init(Context context){
        this.context=context;
    }

    private static ThreadPoolExecutor sThreadPool = new ThreadPoolExecutor(MAX_THREAD, MAX_THREAD, 60, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(), new ThreadFactory() {
        private AtomicInteger mInteger = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "download thread #" + mInteger.getAndIncrement());
            return thread;
        }
    });
    private void finish(DownloadTask task) {
        mHashSet.remove(task);
    }

    public void download(final String url, final DownCallBack callback){
        final DownloadTask downloadTask =new DownloadTask(url,callback);
        if(mHashSet.contains(downloadTask)){
            return ;
        }
        mHashSet.add(downloadTask);



        HttpManager.getInstance().asyncRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                finish(downloadTask);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()&&callback!=null){
                    callback.fail(1,"错误");
                    return ;
                }
               long content_length = response.body().contentLength();
                if(content_length ==-1){
                    //错误解决

                }
                processDownLoad(url,content_length,callback);
                finish(downloadTask);

            }
        });




    }

    private void processDownLoad(String url,long length,DownCallBack callback) {
     long threadDownloadSize = length/MAX_THREAD;
        for(int i=0;i<MAX_THREAD;i++){
            long startSize= i*threadDownloadSize;
            long endSize= (i+1)*threadDownloadSize-1;
            sThreadPool.execute(new DownloadRunnable(startSize,callback,endSize,url));

        }

    }


}
