package com.baidu.aopcont.test;

import java.util.concurrent.CountDownLatch;

public class NanoTest {
    public static void main1(String[] args) throws Exception{
        long start = System.nanoTime();

        Thread.sleep(1000);

        long end = System.nanoTime();

        System.out.println(start +"--" +end);
        System.out.println(end-start);
    }

    public static void main2(String[] args) throws Exception{
        CountDownLatch cdl = new CountDownLatch(2);

        cdl.await();

        cdl.countDown();
    }

    public static void main(String[] args) throws Exception{
        String str1 = new String("abc") + new String("abc");
//        String str2 = "abcabc";
        str1.intern();
        System.out.println(str1==str1.intern()); // false
    }
}
