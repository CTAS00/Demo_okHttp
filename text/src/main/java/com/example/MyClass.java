package com.example;

import java.io.File;
import java.io.IOException;

public class MyClass {
    static class MyRunnable implements Runnable{
        public boolean flag =true;

        @Override
        public void run() {
            while(!Thread.interrupted()){

                try {
                    System.out.println("running");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return ;
                }


            }

        }
    }

    public static void main(String[] args) throws InterruptedException {

//        MyRunnable myRunnable = new MyRunnable();
//        Thread thread = new Thread(myRunnable);
//        thread.start();
//        Thread.sleep(2000);
//        thread.interrupt();
//        myRunnable.flag = false;



            File file = new File("d:\\ctasqwer");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        file.mkdir();


    }
}
