/**
 * Created by CTAS on 2017/8/27.
 */
public class myRunnable implements Runnable{

        public boolean flag =true;

        @Override
        public void run() {
            while(flag){

                try {
                    System.out.println("running");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }

        }
}
