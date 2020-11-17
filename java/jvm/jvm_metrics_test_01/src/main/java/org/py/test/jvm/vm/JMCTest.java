package org.py.test.jvm.vm;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Random;

@Slf4j
public class JMCTest {

    @Test
    public void helloJMC() {
        while (true) {
            log.info("hello");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test() {
        Runnable t = new Runnable() {
            int a = 10;

            Object lock = new Object();


            @Override
            public void run() {
                while (true) {
                    synchronized (lock) {
                        if (a > 0) {
                            a--;
                            System.out.println(Thread.currentThread().getName() + " : " + (10 - a));
                        }
                    }
                }
            }
        };
        new Thread(t, "1").start();
        new Thread(t, "2").start();
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        Ticket t1 = new Ticket();
        Thread thread1 = new Thread(t1,"窗口1");
        Thread thread2 = new Thread(t1,"窗口2");
        Thread thread3 = new Thread(t1,"窗口3");

        thread1.start();
        thread2.start();
        thread3.start();
        try {
            Thread.sleep(100000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class Ticket implements Runnable {

        private int ticket = 30;

        private Object object = new Object();

        @Override
        public void run() {

            while (true) {
                synchronized (this) {

                    if (ticket > 0) {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + "卖票，票号为：" + (ticket--));

                    }
                }
                try {
                    Thread.sleep(Math.abs(new Random().nextInt()) % 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


    }

}
