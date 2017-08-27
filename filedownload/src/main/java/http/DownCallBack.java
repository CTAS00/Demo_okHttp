package http;

import java.io.File;

/**
 * Created by CTAS on 2017/8/27.
 */
public interface DownCallBack {

    void success(File file);

    void fail(int errorCode,String message);
    void progress(int progress);
}
