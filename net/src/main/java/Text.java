/**
 * Created by CTAS on 2017/8/27.
 */
public class Text {


    public static void main(String args) throws InterruptedException {

        myRunnable myRunnable = new myRunnable();
        new Thread(myRunnable).start();
        Thread.sleep(1000);
        myRunnable.flag = false;



    }
}
