import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import file.FileStorageManager;
import http.DownCallBack;
import http.HttpManager;
import okhttp3.Response;

/**
 * Created by CTAS on 2017/8/27.
 */
public class DownloadRunnable implements  Runnable {

    private long statrt;
    private long end;
    private String url;
    private DownCallBack callBack;

    public DownloadRunnable(long statrt, DownCallBack callBack, long end, String url) {
        this.statrt = statrt;
        this.callBack = callBack;
        this.end = end;
        this.url = url;
    }

    @Override
    public void run() {
      Response response =  HttpManager.getInstance().syncRequest(url, statrt, end);
        if(response==null && callBack!=null){
            callBack.fail(HttpManager.NETWORK_ERROR_CODE,"请求失败");
            return ;
        }
        File file= FileStorageManager.getInstance().getFileByName(url);
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rwd");
            randomAccessFile.seek(statrt);
            byte[] buffer = new byte[1025 *500];
            InputStream inputStream = response.body().byteStream();
            int length ;
            while ((length = inputStream.read(buffer, 0, buffer.length)) != -1) {
                randomAccessFile.write(buffer,0,length);
            }
            if(callBack!=null){
                callBack.success(file);
            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
