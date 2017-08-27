package ctas.demo_okhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;

import file.FileStorageManager;
import http.DownCallBack;
import http.HttpManager;

public class MainActivity extends AppCompatActivity {
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.iv);
        HttpManager.getInstance().init(this);
        FileStorageManager.getInstance().init(this);
        HttpManager.getInstance().asyncRequest("http://img0.bdstatic.com/static/searchdetail/img/logo-2X_b99594a.png", new DownCallBack() {
            @Override
            public void success(File file) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                iv.setImageBitmap(bitmap);

            }

            @Override
            public void fail(int errorCode, String message) {

            }

            @Override
            public void progress(int progress) {

            }
        });
    }
}
