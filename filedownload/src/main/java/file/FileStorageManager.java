package file;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

import utils.Md5Utils;

/**
 * Created by CTAS on 2017/8/27.
 */
public class FileStorageManager {



    private static final FileStorageManager sManager = new FileStorageManager();
    private FileStorageManager(){}

    private Context context;
    public static FileStorageManager getInstance(){

        return sManager;
    }

    public void init(Context context){
        this.context = context;
    }

    public File getFileByName(String url){
        File parent;

//        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//            parent = context.getExternalCacheDir();
//
//        }else {
            parent = context.getCacheDir();
//        }
        String fileName = Md5Utils.generateCode(url+"5555");
        File file = new File(parent,fileName);
        if(!file.exists()){
            try {
                //这个是创建文件  并不是创建文件夹
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                file = null;
            }
        }
        return file;

    }
}
