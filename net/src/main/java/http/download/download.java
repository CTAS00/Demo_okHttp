package http.download;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by CTAS on 2017/8/27.
 */
public class download {

    public static void  main(String args[]){

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://www.imooc.com").addHeader("Range","bytes=0-2")

                .build();

        try {
         Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                Headers headers = response.headers();
                for(int i=0 ;i <headers.size();i++){
                    System.out.println(headers.name(i)+":"+headers.value(i));

                }

            }
        }catch (Exception e){

        }






    }
}
