package com.multithreading.CreatingLocks;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by anurag on 01/03/19.
 */


class PrintOddEvenUsingAtomicInteger {
//static int i=0;
//static int j=0;

   static int i=0;
   static int j=1;
   static int k=2;
   static int p=3;
    static AtomicInteger atomicInteger = new AtomicInteger();
/*
* 1-(0,1)
* 2-(1,2)
* 3-(2,3)
* 4-(3,4)
*
* */
public void testEvenOdd(){
    atomicInteger.set(0);

    Thread odd = new Thread(() -> {
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(atomicInteger.compareAndSet(i,j)){
                System.out.println("odd : " + atomicInteger.get());
//                j++;
//                i++;
               // System.out.println(i+","+j+","+k);
            }
        }
    });

    Thread even = new Thread(() -> {
        while (true) {
           // System.out.println("second thread");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(atomicInteger.compareAndSet(j,k)){
                System.out.println("even : " + atomicInteger.get());
               // i++;
                //increment(i);
//               i=k;
//               j=i+1;

            }

        }
    });

    Thread third = new Thread(() -> {
        while (true) {
            // System.out.println("second thread");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(atomicInteger.compareAndSet(k,p)){
                System.out.println("third : " + atomicInteger.get());
                // i++;
                //increment(i);
                i=p;
                j=i+1;
                k=j+1;
                p=k+1 ;
            }

        }
    });

    odd.start();
    even.start();
    third.start();

}
    private static void increment(int i){

        k=i+2;
    }
}

class PrintAlternateNumberWithNThreads implements Runnable{

    static int threadName;
    static int NumberOfThreads;
    static int arr[] = new int[NumberOfThreads+2];
    private static AtomicInteger atomicInteger = new AtomicInteger();

    public PrintAlternateNumberWithNThreads(int NumberOfThreads) {
      //  this.threadName = threadName;
        this.NumberOfThreads = NumberOfThreads;
    }

    public PrintAlternateNumberWithNThreads(int threadName, int NumberOfThreads) {
        atomicInteger.set(0);
        this.threadName = threadName;
        this.NumberOfThreads = NumberOfThreads;
    }
    {
        for (int i = 1; i <=NumberOfThreads+1; i++) {
            arr[i]=i-1;
        }
    }
    /*
    *
    * 1 -
    *
    *
    * */
    public void printNumber() {


        while (true) {
            try {
                Thread.sleep(threadName*500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (atomicInteger.compareAndSet(arr[threadName], arr[threadName+1])) {
                System.out.println(threadName + ": " + atomicInteger.get());

                if(threadName==NumberOfThreads){
                    arr[1]=arr[NumberOfThreads+1];
                    for (int i = 2; i <=NumberOfThreads+1; i++) {
                        arr[i]=arr[i-1]+1;
                    }

                }
            }
        }
    }



    public void run(){
        printNumber();
    }


    public void test(){
        Thread t = new Thread(new PrintAlternateNumberWithNThreads(1,2));
        Thread t1 = new Thread(new PrintAlternateNumberWithNThreads(2,2));
        t.start();
        t1.start();
    }

}
class PrintOddEven
{
static int number=1;
static Object monitor = new Object();

public void test(){

    Thread odd = new Thread(()->{
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (monitor) {
                if (number % 2 != 0) {
                    System.out.println("Odd Thread : " + number++);
                    monitor.notify();
                } else {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    });

    Thread even = new Thread(()->{
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (monitor) {
                if (number % 2 == 0) {
                    System.out.println("even Thread : " + number++);
                    monitor.notify();
                } else {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    });

    odd.start();
    even.start();
}



}

public class BasicLock implements Runnable{


    int threadName;
      static int i=1;
      static int j=2;
    static int temp =1;
    static String obj = "null";
    static boolean flagone =true;
    static  boolean flagTwo =false;
    static  boolean flagThree =false;
    public BasicLock(int threadName) {
        this.threadName = threadName;
    }

    private  void oddEvenProgram() throws InterruptedException {

        synchronized (obj){
        while(true) {

            Thread.sleep(1000);
            if (threadName == 1) {
                if (flagone == false) {
                    obj.wait();
                } else {

                    System.out.println(threadName + " " + i);

                     i=i+1;
                    flagone = false;
                    flagThree=false;
                    flagTwo = true;
                    obj.notifyAll();
                }
            }
            if (threadName == 2) {
                if (flagTwo == false) {
                    obj.wait();
                } else {

                    System.out.println(threadName + " " + i);
                 //   Thread.sleep(1000);
                    i=i+1;
                    flagTwo = false;
                    flagone = false;
                    flagThree=true;
                    obj.notifyAll();
                }
            }
            if (threadName == 3) {
                if (flagThree == false) {
                    obj.wait();
                } else {

                    System.out.println(threadName + " " + i);
                    //   Thread.sleep(1000);
                    i=i+1;
                    flagTwo = false;
                    flagone = true;
                    flagThree=false;
                    obj.notifyAll();
                }
            }

        }


        }



    }
    @Override
    public void run() {

        try {
            oddEvenProgram();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String args[]){

BasicLock basicLock = new BasicLock(1);

        BasicLock basicLock2 = new BasicLock(2);
        BasicLock basicLock3 = new BasicLock(3);
        Thread thread = new Thread(basicLock);
        Thread thread1 = new Thread(basicLock2);
        Thread thread2 = new Thread(basicLock3);
//        thread.start();
//        thread1.start();
//        thread2.start();

        PrintOddEven printOddEven = new PrintOddEven();
        //printOddEven.test();
        PrintOddEvenUsingAtomicInteger printOddEvenUsingAtomicInteger = new PrintOddEvenUsingAtomicInteger();
     //   printOddEvenUsingAtomicInteger.testEvenOdd();

        PrintAlternateNumberWithNThreads printAlternateNumberWithNThreads = new PrintAlternateNumberWithNThreads(2);
        printAlternateNumberWithNThreads.test();
    }


}
