package http;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import file.FileStorageManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by CTAS on 2017/8/27.
 */
public class HttpManager {
    private static  final HttpManager sManager=new HttpManager();
    private HttpManager(){
        client = new OkHttpClient();
    }
    public static HttpManager getInstance(){
        return sManager;
    }
    public static final int NETWORK_ERROR_CODE = 1;
    private OkHttpClient client;

    private Context context;
    public void init(Context context){
        this.context = context;
    }

    public Response syncRequest(String url){
        Request request = new Request.Builder().url(url).build();


        try {
            return  client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;


    }

    public Response syncRequest(String url,long start, long end){
        Request request = new Request.Builder().url(url).
                addHeader("Range", "bytes=" + start + "-" + end)
                .build();


        try {
            return  client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;


    }

    public void asyncRequest(final String url, final DownCallBack callBack) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful() && callBack != null) {
                    callBack.fail(NETWORK_ERROR_CODE, "网络出错了");
                    return;
                }
                try {
                    File file = FileStorageManager.getInstance().getFileByName(url);
                    byte[] buffer = new byte[1024 * 500];
                    int len;
                    FileOutputStream fileOut = new FileOutputStream(file);
                    InputStream inStream = response.body().byteStream();
                    while ((len = inStream.read(buffer, 0, buffer.length)) != -1) {
                        fileOut.write(buffer, 0, len);
                        fileOut.flush();
                    }
                    callBack.success(file);
                } catch (Exception e) {
                    if (callBack != null) {
                        callBack.fail(NETWORK_ERROR_CODE, "网络出错了");
                    }
                }

            }
        });
    }



    public void asyncRequest(final String url, Callback callBack) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callBack);

    }
}
