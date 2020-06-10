package com.baidu.aopcont.test;

public class NanoTest {
    public static void main(String[] args) throws Exception{
        long start = System.nanoTime();

        Thread.sleep(1000);

        long end = System.nanoTime();

        System.out.println(start +"--" +end);
        System.out.println(end-start);
    }
}
