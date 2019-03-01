package com.multithreading.deadlocks;

/**
 * Created by anurag on 01/03/19.
 */
public class DeadlockScenarios {

    public void testDeadLock(){
        String obj1="null";
        String obj2="null1";
        String obj3="null2";
        Thread thread1 = new Thread(()->{
            while(true){
            synchronized (obj1)
            {
                System.out.println("Thread 1 inside Lock 1 waiting for Lock2 ");

                synchronized (obj2){
                //    obj1.notify();
                    System.out.println("Thread 1 inside lock2");
                }

            }}});
        Thread thread2 = new Thread(()->{
           while(true){
            synchronized (obj2){

            System.out.println("Thread 2 inside Lock 2 waiting for Lock1 ");

            synchronized (obj1){
         //       obj2.notify();
                System.out.println("Thread 2 inside lock1");
            }}
        }});

        thread1.start();
        thread2.start();
    }

    public void createNestedMonitorException(){


    }
  public static  void main(String args[]){


      DeadlockScenarios deadlockScenarios = new DeadlockScenarios();
       deadlockScenarios.testDeadLock();





  }

}
